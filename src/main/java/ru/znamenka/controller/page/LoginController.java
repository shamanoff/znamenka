package ru.znamenka.controller.page;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.znamenka.security.GoogleKey;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Сережа on 14.08.2016.
 */
@Controller
@Slf4j
public class LoginController {

    @Value("${calendar.private_key.filename}")
    private String privateKey;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/index")
    public String index(Model model) {
        Resource resource = new ClassPathResource(privateKey);
        try {
            GoogleKey key = objectMapper.readValue(resource.getInputStream(), GoogleKey.class);
            model.addAttribute("serviceEmail", key.getClientEmail());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return "index";
    }

    @GetMapping(value="/logout")
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";//You can redirect wherever you want, but generally it's a good practice to show login screen again.
    }
}
