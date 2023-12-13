package net.javaguides.springboot.springsecurity.web;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.javaguides.springboot.springsecurity.model.Role;
import net.javaguides.springboot.springsecurity.model.User;
import net.javaguides.springboot.springsecurity.model.Venta;
import net.javaguides.springboot.springsecurity.repository.ProductosVendidosRepository;
import net.javaguides.springboot.springsecurity.repository.UserRepository;
import net.javaguides.springboot.springsecurity.service.ReportService;

@Controller
public class MainController {

	@Autowired
	ProductosVendidosRepository productosVendidosRepository;
	@Autowired
	UserRepository userRepository;
	
    @GetMapping("/")
    public String root(Model model) {
    	int num_user = 0;
      	 for (User i : userRepository.findAll()) {
          	    for (Role role : i.getRoles()) {
          	    if(role.getName().equals("ROLE_USER")) {
          	    	num_user = (num_user + 1);}}}
     	model.addAttribute("num_user", num_user);
      	Boolean pendiente = false;
      	Boolean activos = true;
	    String name_cliente="";
   	 
    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    UserDetails userDetails = null;
	    if (principal instanceof UserDetails) {
	      userDetails = (UserDetails) principal;
	    }			    
	    String a = userDetails.getUsername();
       User u = userRepository.findByEmail(a);
       name_cliente = "Bienvenido: "+u.getFirstName()+" "+u.getLastName();

   	   model.addAttribute("data_user", name_cliente);
       for (Role i : u.getRoles()) {
    	    String role = i.getName();
    	    if(role.equals("ROLE_USER")) {
    	     	 model.addAttribute("num_pedidos", productosVendidosRepository.countByUsers_id(u.getId()));
    	     	 model.addAttribute("num_pedidos_pendientes", productosVendidosRepository.countByEstadoAndUsers_id(pendiente, u.getId()));
           	   	 model.addAttribute("num_pedidos_realizados", productosVendidosRepository.countByEstadoAndUsers_id(activos, u.getId()));

    	    }
    	    else {
    	     	 model.addAttribute("num_pedidos", productosVendidosRepository.count());
    	     	 model.addAttribute("num_pedidos_pendientes", productosVendidosRepository.countByEstado(pendiente));
    	     	 model.addAttribute("num_pedidos_realizados", productosVendidosRepository.countByEstado(activos));
    	     	 }}	
    	
       return "index";
    }

	

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    
    @GetMapping("/user")
    public String userIndex() {
        return "user/index";
    }
  

}
