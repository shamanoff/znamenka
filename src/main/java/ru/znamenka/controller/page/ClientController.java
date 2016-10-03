package ru.znamenka.controller.page;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import ru.znamenka.annotation.ActionLogged;
import ru.znamenka.jpa.model.User;
import ru.znamenka.represent.domain.ClientApi;
import ru.znamenka.represent.domain.TrainingApi;
import ru.znamenka.represent.page.client.ClientPurchaseApi;
import ru.znamenka.represent.page.schedule.SubscriptionApi;
import ru.znamenka.service.subsystem.client.ClientService;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.springframework.http.ResponseEntity.*;
import static org.springframework.util.Assert.notNull;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

/**
 * Контроллер клиента.
 * <p>
 * Адреса страниц:
 * /client - возвращает страницу Клиенты
 * API:
 * GET, /client/{id}                - возвращает клиента в формате json по его id
 * PUT, /client/                    - обновляет клиента.
 * POST, /client/                   - создает нового клиента
 * GET, /client/{id}/trainings      - возвращает тренировки по id клиента
 * GET, /client/{id}/purchases      - возвращает покупки клиента
 * GET, /client/search/phone        - возвращает клиента но номеру телефона
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

    /**
     * Сервис для операций с клиентами
     */
    private final ClientService clientService;

    /**
     * Конструктор для внедрения зависимостей
     *
     * @param clientService сервис для операций с клиентами
     */
    @Autowired
    public ClientController(ClientService clientService) {
        notNull(clientService);
        this.clientService = clientService;
    }

    /**
     * Метод для инициализации страницы для операций с клиентами
     *
     * @return страницу templates/client.html
     */
    @GetMapping
    public ModelAndView index() {
        Long trainerId = getTrainerIdIfExists();
        List<ClientApi> clients = clientService.clientsByTrainerId(trainerId);
        ModelAndView mv = new ModelAndView("client");
        mv.addObject("clientNew", new ClientApi());
        mv.addObject("clients", clients);
        return mv;
    }

    /**
     * Возвращает клиента по его уникальному идентификатору
     *
     * @param id уникальный идентификатор клиента
     * @return клиент
     */
    @GetMapping("/{id}")
    public ResponseEntity<ClientApi> clientApi(@PathVariable Long id) {
        val client = clientService.store().findOne(ClientApi.class, id);
        return ok(client);
    }

    /**
     * Обновляет информацию о клиенте
     *
     * @param client        обновленный клиент
     * @param bindingResult результат валидации
     * @return обновленный клиент, либо код ответа 400, при ошибки валидации
     */
    @RequestMapping(method = PUT)
    @ActionLogged(action = "обновил информацию о клиенте")
    public ResponseEntity<ClientApi> updateClient(@Valid ClientApi client, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return badRequest().body(client);
        }
        return ok(clientService.update(client));
    }

    /**
     * Сохраняет нового клиента
     *
     * @param clientNew     новый клиент
     * @param bindingResult результат валидации
     * @return редирект на страницу с клиентами
     */
    // TODO: 30.09.2016 если данные не валидны, то пользователю об этом не сообщается
    @RequestMapping(method = POST)
    public View createClient(@Valid ClientApi clientNew, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            clientService.store().save(ClientApi.class, clientNew);
        }
        return new RedirectView("/client");
    }

    /**
     * Возвращает список тренировок клиента.
     * Используется для вкладки Тренировки в карточке клиента
     *
     * @param id уникальный идентификатор клиента
     * @return список тренировок
     */
    @GetMapping("/{id}/trainings")
    public ResponseEntity<List<TrainingApi>> getTrainingsByClient(@PathVariable Long id) {
        List<TrainingApi> trainings = clientService.trainings(id);
        return ok(trainings);
    }

    /**
     * Возвращает список покупок клиентов и дополнительную информацию,
     * сколько осталось заплатить, сколько заплачено, etc.
     *
     * @param id уникальный идентификатор клиента
     * @return список покупок
     * @see ClientPurchaseApi
     */
    @GetMapping("/{id}/purchases")
    public ResponseEntity<List<ClientPurchaseApi>> getPurchasesByClient(@PathVariable Long id) {
        List<ClientPurchaseApi> purchases = clientService.purchases(id);
        return ok(purchases);
    }

    /**
     * Поиск клиента по номеру телефона
     *
     * @param phone номер телефона
     * @return код 200 (и клиента) если клиент найден и 204 если не найден
     */
    @GetMapping("/search/phone")
    public ResponseEntity<?> searchClientByPhone(@Pattern(regexp = "^7[0-9]{10}") String phone
    ) {
        ClientApi clientApi = clientService.clientByPhone(phone);
        if (clientApi == null) {
            return noContent().build();
        }
        return ok(clientApi);
    }

    /**
     * API для подгрузки абонементов клиента по его id
     *
     * @param clientId уникальный идентификатор клиента
     * @return список абонементов
     */
    @GetMapping("/subscriptions")
    public ResponseEntity<List<SubscriptionApi>> getSubscriptions(@RequestParam("clientId") Long clientId) {
        if (clientId == null) {
            return badRequest().body(emptyList());
        }
        List<SubscriptionApi> subscriptions = clientService.subscriptions(clientId);
        return ok(subscriptions);
    }

    /**
     * Метод для обработки ситуации, когда валидируемый параметр метод не валиден, например неверный
     * формат номера телефона
     *
     * @return код ответа 400
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handleValidationEx() {
        return badRequest().build();
    }

    private Long getTrainerIdIfExists() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getTrainer().getId();
    }

}
