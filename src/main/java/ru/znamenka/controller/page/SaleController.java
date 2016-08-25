package ru.znamenka.controller.page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.znamenka.api.domain.*;
import ru.znamenka.api.page.sale.PaymentsApi;
import ru.znamenka.service.IConvertService;
import ru.znamenka.service.page.sale.SaleService;

import javax.validation.Valid;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.springframework.util.Assert.notNull;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * <p>
 * <p>
 * Создан 12.08.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Controller
@RequestMapping("/sale")
public class SaleController {

    private final IConvertService service;

    @Autowired
    private SaleService saleService;

    @Autowired
    public SaleController(@Qualifier("dataService") IConvertService service) {
        notNull(service);
        this.service = service;
    }

    @RequestMapping(method = GET)
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
        mv.addObject("purchase", new PurchaseApi());
        return mv;
    }

    @RequestMapping(method = POST)
    public void addPurchase(@Valid PurchaseApi purchase, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            purchase.setPurchaseDate(Date.valueOf(LocalDate.now()));
            service.save(PurchaseApi.class, purchase);
        }
    }

    @RequestMapping(method = GET, path = "/purchases")
    public ModelAndView getPurchases(@RequestParam("clientId") Long clientId) {
        ModelAndView mv = new ModelAndView("sale :: purchases-table");
        if (clientId == null) {
            mv.addObject("payments", emptyList());
            return mv;
        }

        List<PaymentsApi> payments = service.findAll(PaymentsApi.class);
        mv.addObject("payments", payments);
        return mv;
    }


}
