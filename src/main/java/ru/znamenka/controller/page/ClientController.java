package ru.znamenka.controller.page;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ru.znamenka.annotation.ActionLogged;
import ru.znamenka.represent.domain.ClientApi;
import ru.znamenka.represent.domain.TrainingApi;
import ru.znamenka.represent.page.client.ClientPurchaseApi;
import ru.znamenka.service.ApiStore;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import static ru.znamenka.jpa.model.QClient.client;
import static ru.znamenka.jpa.model.QPurchase.purchase;
import static ru.znamenka.jpa.model.QTraining.training;

/**
 * <p>
 * <p>
 * Создан 12.08.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Controller
@RequestMapping("/client")
@Validated
@Slf4j
public class ClientController {


    @Autowired
    @Qualifier("dataService")
    private ApiStore service;

    @GetMapping
    public ModelAndView index() {

        ModelAndView mv = new ModelAndView("client");
        mv.addObject("clientNew", new ClientApi());
        mv.addObject("clients", service.findAll(ClientApi.class));
        return mv;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ClientApi clientApi(@PathVariable Long id) {
        return service.findOne(ClientApi.class, id);
    }

    @RequestMapping(value = "/{id}", method = PUT)
    @ResponseBody
    @ActionLogged(action = "обновил информацию о клиенте")
    public ClientApi updateClient(@Valid ClientApi client, BindingResult bindingResult, @PathVariable Long id) {
        ClientApi clientApi = service.findOne(ClientApi.class, id);
        if (!bindingResult.hasErrors()) {
            clientApi.setFname(client.getFname());
            clientApi.setSname(client.getSname());
            clientApi.setBirthDate(client.getBirthDate());
            clientApi.setPhone(client.getPhone());
            clientApi.setEmail(client.getEmail());
            clientApi.setComment(client.getComment());
            clientApi.setMale(client.getMale());
            service.update(ClientApi.class, clientApi);
        }
        return clientApi;
    }

    @RequestMapping(method = POST)
    public ModelAndView createClient(@Valid ClientApi clientNew, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            service.save(ClientApi.class, clientNew);
        }
        return new ModelAndView(new RedirectView("/client"));
    }


    @GetMapping("/{id}/trainings")
    @ResponseBody
    public List<TrainingApi> getTrainingsByClient(@PathVariable Long id) {
        return service.findAll(TrainingApi.class, training.clientId.eq(id));
    }

    @GetMapping("/{id}/purchases")
    @ResponseBody
    public List<ClientPurchaseApi> getPurchasesByClient(@PathVariable Long id) {
        return service.findAll(ClientPurchaseApi.class, purchase.client.id.eq(id));
    }

    @GetMapping("/search/phone")
    @ResponseBody
    public ResponseEntity<?> searchClientByPhone(@Pattern(regexp = "^7[0-9]{10}") String phone
    ) {
        ClientApi clientApi = service.findOne(ClientApi.class, client.phone.eq(phone));
        if (clientApi == null) {
            return notFound().build();
        }
        return ok(clientApi);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handleValidationEx(ConstraintViolationException e) {
        return badRequest().build();
    }


}
