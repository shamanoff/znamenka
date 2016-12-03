package ru.click.cabinet.service;

import lombok.val;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.click.core.model.Client;
import ru.click.core.model.LkUser;
import ru.click.core.model.QClient;
import ru.click.core.repository.domain.ClientRepository;
import ru.click.core.repository.domain.LkUserRepository;

import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;

public class SignUpService {

    private SmsService smsService;

    private LkUserRepository repository;

    private ClientRepository clientRepository;

    private PasswordEncoder passwordEncoder;

    public void sendSms(String phone) {
        Integer code = ThreadLocalRandom.current().nextInt();
        SmsCodeHolder.put(phone, code);
        smsService.send(phone, String.valueOf(code));
    }

    // TODO: 03.12.2016 replace boolean to exception
    public boolean signUp(String phone, int code) {
        if (!checkCode(phone, code)) {
            this.sendSms(phone);
            return false;
        }

        Client client = clientRepository.findOne(QClient.client.phone.eq(phone));
        if (client == null) {
            return false;
        }
        String randomPassword = new BigInteger(130, ThreadLocalRandom.current()).toString(32);
        smsService.send(phone, "password: " + randomPassword);
        val user = new LkUser();
        user.setUsername(phone);
        user.setPassword(passwordEncoder.encode(randomPassword));
        user.setClient(client);
        // TODO: 03.12.2016 add role
        repository.save(user);
        return true;
    }


    public boolean checkCode(String phone, int code) {
        int earlyCode = SmsCodeHolder.getCode(phone);
        return earlyCode == code;
    }
}
