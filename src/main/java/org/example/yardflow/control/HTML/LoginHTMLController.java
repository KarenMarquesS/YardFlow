package org.example.yardflow.control.HTML;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginHTMLController {

    @GetMapping("/login")
    public ModelAndView loginPage() {
        return new ModelAndView("index");
    }

    @GetMapping("/login/acesso_negado")
    public ModelAndView retornarAcessoNegado() {
        return new ModelAndView("acesso_negado");
    }

}
