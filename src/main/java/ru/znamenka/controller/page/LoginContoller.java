package ru.znamenka.controller.page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.znamenka.service.custom.ClientBalanceService;

/**
 * Created by Сережа on 14.08.2016.
 */
@Controller
public class LoginContoller {



    @GetMapping("/login")
    public String index(Model model) {

        return "login";
    }
}
