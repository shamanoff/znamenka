package ru.click.cabinet.service;

import lombok.val;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.click.cabinet.exception.NoExistsClientSignUpException;
import ru.click.cabinet.exception.WrongCodeSignUpException;
import ru.click.core.model.Client;
import ru.click.core.model.LkUser;
import ru.click.core.model.QClient;
import ru.click.core.repository.domain.ClientRepository;
import ru.click.core.repository.domain.LkUserRepository;

import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;

public class SignUpService {

    private SmsService smsService;

    private LkUserRepository userRepository;

    private ClientRepository clientRepository;

    private PasswordEncoder passwordEncoder;

    public void sendSms(String phone) {
        Integer code = ThreadLocalRandom.current().nextInt();
        SmsCodeHolder.put(phone, code);
        smsService.send(phone, String.valueOf(code));
    }

    // TODO: 03.12.2016 replace boolean to exception
    public void signUp(String phone, int code) {
        if (!checkCode(phone, code)) {
            this.sendSms(phone);
            throw new WrongCodeSignUpException();
        }

        Client client = clientRepository.findOne(QClient.client.phone.eq(phone));
        if (client == null) {
            throw new NoExistsClientSignUpException();
        }
        String randomPassword = new BigInteger(130, ThreadLocalRandom.current()).toString(32);
        smsService.send(phone, "password: " + randomPassword);
        val user = new LkUser();
        user.setUsername(phone);
        user.setPassword(passwordEncoder.encode(randomPassword));
        user.setClient(client);
        userRepository.save(user);
    }


    private boolean checkCode(String phone, int code) {
        int earlyCode = SmsCodeHolder.getCode(phone);
        return earlyCode == code;
    }
}
