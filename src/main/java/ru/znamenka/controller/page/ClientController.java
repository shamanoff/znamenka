package ru.znamenka.controller.page;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ru.znamenka.api.domain.ClientApi;
import ru.znamenka.api.domain.TrainingApi;
import ru.znamenka.service.ApiStore;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import static ru.znamenka.jpa.model.QTraining.training;

/**
 * <p>
 *
 * Создан 12.08.2016
 * <p>

 * @author Евгений Уткин (Eugene Utkin)
 */
@Controller
@RequestMapping("/client")
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
    public RedirectView updateClient(@Valid ClientApi client, BindingResult bindingResult, @PathVariable Long id) {
        if (!bindingResult.hasErrors()) {
            ClientApi clientApi = service.findOne(ClientApi.class, id);
            clientApi.setFname(client.getFname());
            clientApi.setSname(client.getSname());
            clientApi.setBirthDate(client.getBirthDate());
            clientApi.setPhone(client.getPhone());
            clientApi.setEmail(client.getEmail());
            service.save(ClientApi.class, clientApi);
        }
        return new RedirectView("/client");
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



}
