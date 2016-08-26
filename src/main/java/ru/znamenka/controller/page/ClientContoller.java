package ru.znamenka.controller.page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.znamenka.service.page.clients.ClientBalanceService;

/**
 * Created by Сережа on 12.08.2016.
 */
public class ClientContoller {

    @Autowired
    private ClientBalanceService service;

    @GetMapping("/client")
    public String index(Model model) {

        return "client";
    }

}
