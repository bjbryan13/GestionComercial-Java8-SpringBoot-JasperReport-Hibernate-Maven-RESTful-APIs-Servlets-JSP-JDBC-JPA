package net.javaguides.springboot.springsecurity.web;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.javaguides.springboot.springsecurity.model.ProductoVendido;
import net.javaguides.springboot.springsecurity.model.Role;
import net.javaguides.springboot.springsecurity.model.User;
import net.javaguides.springboot.springsecurity.repository.ProductosVendidosRepository;
import net.javaguides.springboot.springsecurity.repository.UserRepository;
import net.javaguides.springboot.springsecurity.service.ProductoVendidoService;

@Controller
public class Productovendidocontroller {

	@Autowired
	private ProductoVendidoService productoService;
	@Autowired
	private ProductosVendidosRepository productosVendidosRepository;
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/productovendido")
	public String viewHomePage(Model model) {
		return findPaginated(1, "id", "asc", model);		
	}

/////////////////////////////////////////
	@PostMapping("/saveProductoVendido")
	public String saveEmpresaa(@ModelAttribute("personaa") ProductoVendido personaa) throws UnsupportedEncodingException, MessagingException {
		// save employee to database

		productoService.saveProducto(personaa);
		//////////
		String name_cliente = personaa.getUsers().getFirstName()+" "+ personaa.getUsers().getLastName();
		String nom_codigo = personaa.getCodigo();
		String nom_producto = personaa.getNombre();
		String email = personaa.getUsers().getEmail();
		
		Float precio = personaa.getPrecio();
		Float cantidad = personaa.getCantidad();
		Float tt_pedido = personaa.getTotal();
		
		Boolean nom_estado = personaa.getEstado();
		String estado;
		if(nom_estado == true) {
			estado="Recibido";
		}else{
			estado="Pendiente";
		}
		
		Integer num_pedido = personaa.getVenta().getId();
		sendEmail(email, name_cliente, nom_codigo, nom_producto, precio, cantidad, tt_pedido, estado,num_pedido);
		
		for (User i : userRepository.findAll()) {
      	    for (Role role : i.getRoles()) {
      	    if(role.getName().equals("ROLE_ADMIN")) {
      	    	String emailadmin=i.getEmail();
      	    	sendEmail(emailadmin, name_cliente, nom_codigo, nom_producto, precio, cantidad, tt_pedido, estado,num_pedido);
      			
}}}
  	 
		
/////////////////////////////////////////
	
		return "redirect:/productovendido";
	}
	
	@GetMapping("/showFormForProductovendidoUpdate/{id}")
	public String showFormForEmpresaUpdate(@PathVariable ( value = "id") long id, Model model) {
		// get employee from the service
		ProductoVendido personaa = productoService.getProductoById(id);
		// set employee as a model attribute to pre-populate the form
		model.addAttribute("personaa", personaa);
		productosVendidosRepository.save(personaa);
        
		return "update_productosvendidos";
	}
	
	
	
	
	@GetMapping("/pageProductovendido/{pageNo}")
	public String findPaginated(@PathVariable (value = "pageNo") int pageNo, 
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {
		int pageSize = 10;
		
		Page<ProductoVendido> page = productoService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<ProductoVendido> listPersonaa = page.getContent();
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		
		model.addAttribute("listPersonaas", listPersonaa);
		return "index_productovendido";
	}
	
    @Autowired
	private JavaMailSender mailSender; 
    
    public void sendEmail(String recipientEmail, String name_cliente, String nom_codigo, String nom_producto, Float precio, Float cantidad, Float tt_pedido, String estado, Integer num_pedido)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();              
        MimeMessageHelper helper = new MimeMessageHelper(message);
        
        String paleBlueRows = "'paleBlueRows'";
        helper.setFrom("samucux1@gmail.com", "Notificaciones Shopme");
        helper.setTo(recipientEmail);
        
        String subject = "Estado de Pedido del Cliente:  "+name_cliente;
       
        String content =
        		"<style>.blacks{background:red;}</style><p>Hola</p>"
                + "<p>Se ha actualizado el estado del item del pedido con #"+num_pedido+" del cliente :"+name_cliente+"</p>"
                + "<p>Detalle:</p>" +
                "<table ><tr>"+
                  "<th>Codigo</th>"+
                  "<th>Producto</th>"+
                  "<th>Precio</th>"+
                  "<th>Cantidad</th>"+
                  "<th>Total</th>"+
                  "<th>Estado</th>"+
                  "</tr>"
                  + "<tr>"
                  + "<td>"+nom_codigo+"</td>"
                  + "<td>"+nom_producto+"</td>"
                  + "<td>"+precio+"</td>"
                  + "<td>"+cantidad+"</td>"
                  + "<td>"+tt_pedido+"</td>"
                  + "<td>"+estado+"</td>"

                  + "</tr>"+
                  "</table>"
                  + "<br> estado"+ "<br>"+
                  "<p style=\"background: #bdf0fa; color: #0c92ac; font-weight: bold; padding: 15px; border: 2px solid #abecf9; border-radius: 6px;\">El importe total del pedido es: S/."+
                  tt_pedido+
                  "</p>";
         
        helper.setSubject(subject);
        helper.setText(content, true); 
        mailSender.send(message);	    }	    


}
