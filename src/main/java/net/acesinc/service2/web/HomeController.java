package net.acesinc.service2.web;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    
    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String getIndex(HttpServletRequest request, ModelMap model, @RequestParam(required=false) String name) {
    	model.addAttribute("pageName", "Home");
        model.addAttribute("name", name);
        return "index";
    }
    @RequestMapping(value = {"/home"}, method = RequestMethod.GET)
    public String getHome(HttpServletRequest request, ModelMap model, @RequestParam(required=false) String name) {
    	model.addAttribute("pageName", "Home");
        model.addAttribute("name", name);
        return "home";
    }
    
    
}
