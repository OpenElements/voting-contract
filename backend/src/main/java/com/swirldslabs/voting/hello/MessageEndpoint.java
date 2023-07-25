package com.swirldslabs.voting.hello;

import java.util.Objects;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MessageEndpoint {

    private final MessageService helloService;

    public MessageEndpoint(final MessageService helloService) {
        this.helloService = Objects.requireNonNull(helloService, "helloService must not be null");
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(final Model model) {
        Objects.requireNonNull(model);
        model.addAttribute("message", helloService.getMessage());
        return "hello";
    }

    @PostMapping("/onhello")
    public String onHelly(final String message, final Model model) {
        helloService.setMessage(message);
        return hello(model);
    }
}
