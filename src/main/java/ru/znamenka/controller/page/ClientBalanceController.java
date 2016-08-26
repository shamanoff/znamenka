package ru.znamenka.controller.page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.znamenka.api.page.ClientBalance;
import ru.znamenka.service.page.clients.ClientBalanceService;

import java.util.List;

/**
 * <p>
 * <p>
 * Создан 03.08.2016
 * <p>
 * Изменения:
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Controller
public class ClientBalanceController {

    @Autowired
    private ClientBalanceService service;

    @GetMapping("/balance")
    public String index(Model model) {
        List<ClientBalance> clients = service.getClients();
        model.addAttribute("allClients", clients);
        return "clients";
    }
}
