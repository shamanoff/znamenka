package ru.znamenka.controller.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.znamenka.api.domain.ClientBalance;
import ru.znamenka.jpa.repository.EntityRepository;

import java.util.List;

import static org.springframework.util.Assert.notNull;

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
public class IndexController {

    private final EntityRepository service;

    @Autowired
    public IndexController(@Qualifier("convertService") EntityRepository service) {
        notNull(service);
        this.service = service;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<ClientBalance> clients = service.findAll(ClientBalance.class);
        model.addAttribute("allClients", clients);
        return "index";
    }
}
