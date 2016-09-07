package ru.znamenka.controller.page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.znamenka.api.domain.ClientApi;
import ru.znamenka.service.ApiStore;
import ru.znamenka.service.page.clients.ClientBalanceService;

/**
 * Created by Сережа on 12.08.2016.
 */
@Controller
public class ClientContoller {



    @Autowired
    @Qualifier("dataService")
    private ApiStore service;

    @GetMapping("/client")
    public ModelAndView index(Model model) {

        ModelAndView mv = new ModelAndView("client2");
        mv.addObject("clients", service.findAll(ClientApi.class));

        return mv;
    }

}
