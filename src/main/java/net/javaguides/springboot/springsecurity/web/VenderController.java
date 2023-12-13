package net.javaguides.springboot.springsecurity.web;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import net.javaguides.springboot.springsecurity.model.ProductoParaVender;
import net.javaguides.springboot.springsecurity.model.ProductoVendido;
import net.javaguides.springboot.springsecurity.model.Role;
import net.javaguides.springboot.springsecurity.model.User;
import net.javaguides.springboot.springsecurity.model.Utiles;
import net.javaguides.springboot.springsecurity.model.Venta;
import net.javaguides.springboot.springsecurity.model.poducto;
import net.javaguides.springboot.springsecurity.repository.ProductoRepository;
import net.javaguides.springboot.springsecurity.repository.ProductosVendidosRepository;
import net.javaguides.springboot.springsecurity.repository.UserRepository;
import net.javaguides.springboot.springsecurity.repository.VentasRepository;
import net.javaguides.springboot.springsecurity.service.UserNotFoundException;
import net.javaguides.springboot.springsecurity.utilities.Utility;

@Controller
@RequestMapping(path = "/vender")
public class VenderController {

	  @Autowired
	    private ProductoRepository productosRepository;
	    @Autowired
	    private VentasRepository ventasRepository;
	    @Autowired
	    private UserRepository userRepository;
	    @Autowired
	    private ProductosVendidosRepository productosVendidosRepository;
		
	    @PostMapping(value = "/quitar/{indice}")
	    public String quitarDelCarrito(@PathVariable int indice, HttpServletRequest request) {
	        ArrayList<ProductoParaVender> carrito = this.obtenerCarrito(request);
	        if (carrito != null && carrito.size() > 0 && carrito.get(indice) != null) {
	            carrito.remove(indice);
	            this.guardarCarrito(carrito, request);
	        }
	        return "redirect:/vender/";
	    }

	    private void limpiarCarrito(HttpServletRequest request) {
	        this.guardarCarrito(new ArrayList<>(), request);
	    }

	    @GetMapping(value = "/limpiar")
	    public String cancelarVenta(HttpServletRequest request, RedirectAttributes redirectAttrs) {
	        this.limpiarCarrito(request);
	        redirectAttrs
	                .addFlashAttribute("mensaje", "Pedido cancelado")
	                .addFlashAttribute("clase", "info");
	        return "redirect:/vender/";
	    }
	    
	    @PostMapping(value = "/terminar")
	    public String terminarVenta(@ModelAttribute("venta") Venta venta, BindingResult bindingResult, HttpServletRequest request, RedirectAttributes redirectAttrs) throws UnsupportedEncodingException, MessagingException {
	        ArrayList<ProductoParaVender> carrito = this.obtenerCarrito(request);
	        // Si no hay carrito o está vacío, regresamos inmediatamente
	        if (carrito == null || carrito.size() <= 0) {
	            return "redirect:/vender/"; }
			  if (bindingResult.hasErrors()) {
				   return "redirect:/vender/";}
		        
        	    String listaprodcs = "";
	    	    String name_cliente="";
	    	    String doc_cliente="";
	    	    String correo_cliente="";
        	    Integer num_pedido = null;
        	    String tt_pedido_str = "";
        	    double tt_pedido;
        	    tt_pedido=0.0;
        	    //////////////////////recordar modifique html model venta y controleer desde el terminar y el inicio///////////
        	    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        	    UserDetails userDetails = null;
        	    if (principal instanceof UserDetails) {
        	    userDetails = (UserDetails) principal;}	
        	    String a = userDetails.getUsername();
        	    User u = userRepository.findByEmail(a);
        	    name_cliente = u.getFirstName();
        	    doc_cliente =  u.getLastName();
        	    correo_cliente = u.getEmail();
        	    ////////////////////////////////////////////////
 
			  	String fechahora=Utiles.obtenerFechaYHoraActual();
			  	Venta venta1 = new Venta(fechahora,venta.getFormaDePago(),venta.getTipoDeEnvio(), u);
				Venta v = ventasRepository.save(venta1);

		        String strformaDePago =venta.getFormaDePago();
		   	    String strtipoDeEnvio=venta.getTipoDeEnvio();
		   	    
        	// Recorrer el carrito
        	for (ProductoParaVender productoParaVender : carrito) {
	            // Obtener el producto fresco desde la base de datos
        	
	            poducto p = productosRepository.findById(productoParaVender.getId()).orElse(null);
	            if (p == null) continue; // Si es nulo o no existe, ignoramos el siguiente código con continue
	            // Le restamos existencia
	            p.restarExistencia(productoParaVender.getCantidad());
	            // Lo guardamos con la existencia ya restada
	            productosRepository.save(p);
	            
		        boolean estado = false ;         
	            ProductoVendido productoVendidos = null;
	            // Creamos un nuevo producto que será el que se guarda junto con la venta
	            ProductoVendido productoVendido = new ProductoVendido(productoParaVender.getCantidad(), productoParaVender.getPrecio(), productoParaVender.getNombre(), productoParaVender.getCodigo(), estado,v,u);
		        
	            // data para email
	        	listaprodcs =listaprodcs+ 
		                "<tr style=\"background-color:rgb(250,250,250);\">"+
		                "  <td>"+productoParaVender.getCantidad()+"</td>"+
		                "  <td>metro cubico</td>"+
    	                "  <td>"+productoParaVender.getNombre()+"</td>"+
    					"  <td>"+productoParaVender.getCodigo()+"</td>"+
		    	                "  <td>"+productoParaVender.getPrecio()+"</td>"+
		    	                "  <td>"+productoParaVender.getTotal().toString() +"</td>"+
		    	                "</tr>";    
	        	/////////////
	        	num_pedido =productoVendido.getVenta().getId();
	        	tt_pedido_str = productoVendido.getTotal().toString();
	        	double doble = Double.parseDouble(tt_pedido_str);
	        	tt_pedido = doble+ tt_pedido;
	        	/////////
	        	
	        	
	        	//se guarda
	            productosVendidosRepository.save(productoVendido); 
	        }
        	
	       //////////email sent/////////////
	        String email = correo_cliente; 
			sendEmail(email,listaprodcs,num_pedido,tt_pedido, name_cliente, doc_cliente, correo_cliente, strformaDePago, strtipoDeEnvio, fechahora);
			
			
			for (User i : userRepository.findAll()) {
	      	    for (Role role : i.getRoles()) {
	      	    if(role.getName().equals("ROLE_ADMIN")) {
	      	    	String emailadmin=i.getEmail();
	      	    	sendEmail(emailadmin,listaprodcs,num_pedido,tt_pedido, name_cliente, doc_cliente, correo_cliente, strformaDePago, strtipoDeEnvio, fechahora);
	    			
	    			
	}}}
			
	    	//////////////////////////////////
	        // Al final limpiamos el carrito
	        this.limpiarCarrito(request);
	        // e indicamos una venta exitosa
	        redirectAttrs
	                .addFlashAttribute("mensaje", "Pedido realizado correctamente")
	                .addFlashAttribute("clase", "success");
	        return "redirect:/vender/";
	    }

	    
	    @GetMapping("/")
	    public String interfazVender(Model model, HttpServletRequest request) {
	        model.addAttribute("producto", new poducto());
	        float total = 0;
	        ArrayList<ProductoParaVender> carrito = this.obtenerCarrito(request);
	        for (ProductoParaVender p: carrito) total += p.getTotal();
	        model.addAttribute("total", total);
	        model.addAttribute("venta", new Venta(null,null,null,null));
	        return "vender";
	    }

	    private ArrayList<ProductoParaVender> obtenerCarrito(HttpServletRequest request) {
	        ArrayList<ProductoParaVender> carrito = (ArrayList<ProductoParaVender>) request.getSession().getAttribute("carrito");
	        if (carrito == null) {
	            carrito = new ArrayList<>();
	        }
	        return carrito;
	    }

	    private void guardarCarrito(ArrayList<ProductoParaVender> carrito, HttpServletRequest request) {
	        request.getSession().setAttribute("carrito", carrito);
	    
	     	    }

	    @PostMapping(value = "/agregar")
	    public String agregarAlCarrito(@ModelAttribute poducto producto, HttpServletRequest request, RedirectAttributes redirectAttrs) {
	        ArrayList<ProductoParaVender> carrito = this.obtenerCarrito(request);
	        
	        poducto productoBuscadoPorCodigo = productosRepository.findFirstByNombre(producto.getNombre());
	        if (productoBuscadoPorCodigo == null) {
	            redirectAttrs
	                    .addFlashAttribute("mensaje", "El producto con el código " + producto.getCodigo() + " no existe")
	                    .addFlashAttribute("clase", "warning");
	            return "redirect:/vender/";
	        }
	        if (productoBuscadoPorCodigo.sinExistencia()) {
	            redirectAttrs
	                    .addFlashAttribute("mensaje", "El producto está agotado")
	                    .addFlashAttribute("clase", "warning");
	            return "redirect:/vender/";
	        }
	        
	        boolean encontrado = false;
	        for (ProductoParaVender productoParaVenderActual : carrito) {
	            if (productoParaVenderActual.getCodigo().equals(productoBuscadoPorCodigo.getCodigo())) {
	            	productoParaVenderActual.aumentarCantidad();
	                encontrado = true;
	                break;
	            }
	        }
	        if (!encontrado) {
	            carrito.add(new ProductoParaVender(productoBuscadoPorCodigo.getNombre(), productoBuscadoPorCodigo.getCodigo(), productoBuscadoPorCodigo.getPrecio(), productoBuscadoPorCodigo.getExistencia(), productoBuscadoPorCodigo.getId(), 1f));
	        }
	            
	        this.guardarCarrito(carrito, request);
	        return "redirect:/vender/";
	   }
	    
	    
	    @Autowired
		private JavaMailSender mailSender; 
	    
	    public void sendEmail(String recipientEmail, String listaprodcs, Integer num_pedido, 
	    		double tt_pedido, String name_cliente, String doc_cliente, String correo_cliente, String strformaDePago, String strtipoDeEnvio, String fechahora)
	            throws MessagingException, UnsupportedEncodingException {
	        MimeMessage message = mailSender.createMimeMessage();              
	        MimeMessageHelper helper = new MimeMessageHelper(message);
	        
	        String paleBlueRows = "'paleBlueRows'";
	        helper.setFrom("samucux1@gmail.com", "Pedidos Shopme");
	        helper.setTo(recipientEmail);
	        
	        String subject = "Solicitud de Pedido del Cliente:  "+name_cliente;
	       
	        String content =
	        		"<p>Hola</p>"
	                + "<p>Se ha generado el pedido #"+num_pedido+" del cliente: "+name_cliente+" con correo: "+correo_cliente+"</p>"
	                + "<p>Metodo de pago escogido: "
	                + strformaDePago
	                + "</p>" 
	                + "<p>Tipo de envio escogido: "
	    	        + strtipoDeEnvio
	    	        + "</p>"
	                + "<p>Detalle:</p>"
	                +"<div style=\"display:flex\">"
	                + "<div style=\"top:-40px; margin-top:-30%; \" >"
	    	        + "<a style=\"font-size:12px; font-family:arial black;\">Correo:</a><a> "+ correo_cliente+"</a><br>"
	    	        + "<a style=\"font-size:12px; font-family:arial black;\">Razon social / Nombres:</a><a> "+ name_cliente+"</a><br>"
	    	    	+ "<a style=\"font-size:12px; font-family:arial black;\">Ruc / Dni:</a><a> "+ doc_cliente+"</a></div></div>"
	                + "<div style=\"\">"
	    	        + "<a style=\"font-size:12px; font-family:arial black;\">Fecha y Hora:</a><a> "+ fechahora+"</a><br>"
	                + "<a style=\"font-size:12px; font-family:arial black;\">Número de pedido:</a><a> "+ num_pedido+"</a>"
	                +"</div>"
	    	        
	                +"<table style=\" width:100%;\" ><tr style=\" background-color:black; color:white; \" >"+
	                  "<th>Cantidad</th>"+
	                  "<th>Unidad Medida</th>"+
	                  "<th>Producto</th>"+
	                  "<th>Codigo</th>"+
	                  "<th>Precio</th>"+
	                  "<th>Total</th>"+
	                  "</tr>"+
	                listaprodcs+
	                  "</table>"
	                  + "<br>"+ "<br>"+
	                  "<p style=\"background: #bdf0fa; color: #0c92ac; font-weight: bold; padding: 15px; border: 2px solid #abecf9; border-radius: 6px;\">El importe total del pedido es: S/."+
	                  tt_pedido+
	                  "</p>";
	         
	        helper.setSubject(subject);
	        helper.setText(content, true); 
	        mailSender.send(message);	    }	    

}
