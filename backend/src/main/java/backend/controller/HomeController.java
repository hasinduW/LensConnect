package backend.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class HomeController {
    @RequestMapping("/")
    public String home(){
        return "home";
    }
    @GetMapping("/login")
    public String Login(){
        return "Login";
    }
    @GetMapping("/Logout")
    public String Logout(HttpServletRequest request, HttpServletResponse response)throws ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            new SecurityContextLogoutHandler().logout(request,response,authentication);
        }
        return "redirect:https://accounts.google.com/logout";
    }

    @GetMapping("/userInfo")
    public String userInfo(OAuth2AuthenticationToken token, Model model){
        model.addAttribute("name",token.getPrincipal().getAttribute("name"));
        model.addAttribute("email",token.getPrincipal().getAttribute("email"));
        return "home";
    }
}