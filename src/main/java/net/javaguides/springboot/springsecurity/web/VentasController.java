package net.javaguides.springboot.springsecurity.web;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.javaguides.springboot.springsecurity.model.ProductoVendido;
import net.javaguides.springboot.springsecurity.model.Role;
import net.javaguides.springboot.springsecurity.model.User;
import net.javaguides.springboot.springsecurity.model.poducto;
import net.javaguides.springboot.springsecurity.repository.ProductosVendidosRepository;
import net.javaguides.springboot.springsecurity.repository.UserRepository;
import net.javaguides.springboot.springsecurity.repository.VentasRepository;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;


@Controller
@RequestMapping(path = "/ventas")
public class VentasController {

	@Autowired
    VentasRepository ventasRepository;
	@Autowired
	 ProductosVendidosRepository productosVendidosRepository;

    @Autowired
    private UserRepository userRepository;

 @GetMapping("/")
 public String mostrarVentas(Model model) { 
	 
	   Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    UserDetails userDetails = null;
	    if (principal instanceof UserDetails) {
	      userDetails = (UserDetails) principal;
	    }			    
	    String a = userDetails.getUsername();
       User u = userRepository.findByEmail(a);
       for (Role i : u.getRoles()) {
    	    String role = i.getName();
    	    if(role.equals("ROLE_USER")) {
    	    	model.addAttribute("ventas", ventasRepository.findByUsers_id(u.getId()));
    	    }
    	    else {
    	    	model.addAttribute("ventas", ventasRepository.findAll());}
    	}
     return "ver_ventas";
 }
 

	
	
}
