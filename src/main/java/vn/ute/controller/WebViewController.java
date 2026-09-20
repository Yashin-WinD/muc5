package vn.ute.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebViewController {

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        model.addAttribute("activePage", "home");
        return "home";
    }

    @GetMapping("/products")
    public String products(Model model) {
        model.addAttribute("activePage", "products");
        return "products";
    }

    @GetMapping("/categories")
    public String categories(Model model) {
        model.addAttribute("activePage", "categories");
        return "categories";
    }

    @GetMapping("/graphiql")
    public String graphiql() {
        return "graphiql";
    }
}
