package ru.znamenka.controller.page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.znamenka.api.domain.ClientApi;
import ru.znamenka.api.domain.DiscountApi;
import ru.znamenka.api.domain.ProductApi;
import ru.znamenka.api.domain.TrainerApi;
import ru.znamenka.jpa.repository.EntityRepository;

import java.util.List;

import static org.springframework.util.Assert.notNull;

/**
 * <p>
 *
 * Создан 12.08.2016
 * <p>

 * @author Евгений Уткин (Eugene Utkin)
 */
@Controller
public class SaleController {

    private final EntityRepository service;

    @Autowired
    public SaleController(@Qualifier("dataService") EntityRepository service) {
        notNull(service);
        this.service = service;
    }

    @GetMapping("/sale")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("sale");
        List<ClientApi> clients = service.findAll(ClientApi.class);
        List<ProductApi> products = service.findAll(ProductApi.class);
        List<TrainerApi> trainers = service.findAll(TrainerApi.class);
        List<DiscountApi> discounts = service.findAll(DiscountApi.class);

        mv.addObject("clients", clients);
        mv.addObject("products", products);
        mv.addObject("trainers", trainers);
        mv.addObject("discounts", discounts);
        return mv;
    }

}
