package ru.znamenka.controller.page;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ru.znamenka.api.domain.ClientApi;
import ru.znamenka.service.ApiStore;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

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

        ModelAndView mv = new ModelAndView("client2");
        mv.addObject("clientNew", new ClientApi());
        mv.addObject("clients", service.findAll(ClientApi.class));
        return mv;
    }

    @RequestMapping(method = POST)
    public ModelAndView createClient(@Valid ClientApi clientNew, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            service.save(ClientApi.class, clientNew);
        }
        return new ModelAndView(new RedirectView("/client"));
    }

}
