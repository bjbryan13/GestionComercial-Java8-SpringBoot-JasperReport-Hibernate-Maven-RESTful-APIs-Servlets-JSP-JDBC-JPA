package net.javaguides.springboot.springsecurity.web;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import net.javaguides.springboot.springsecurity.model.User;
import net.javaguides.springboot.springsecurity.service.UserNotFoundException;
import net.javaguides.springboot.springsecurity.service.UserService;
import net.javaguides.springboot.springsecurity.service.UserServiceImpl;
import net.javaguides.springboot.springsecurity.utilities.RandomString;
import net.javaguides.springboot.springsecurity.utilities.Utility;

@Controller
public class ForgotPasswordController {

	 @Autowired
	    private JavaMailSender mailSender;
	 
	  @Autowired
	    private UserServiceImpl userService;
	     
	    @GetMapping("/forgot_password")
	    public String showForgotPasswordForm() {
	    	 return "forgot_password_form";
	 
	    }
	 
	    @PostMapping("/forgot_password")
	    public String processForgotPassword(HttpServletRequest request, Model model) {
	        String email = request.getParameter("email");
	        String token = RandomString.generate(30);
	         
	        try {
	        	userService.updateResetPasswordToken(token, email);
	            String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
	            sendEmail(email, resetPasswordLink);
	            model.addAttribute("message", "Hemos enviado un enlace  a su correo electrónico. Por favor, compruebe.");
	             
	        } catch (UserNotFoundException ex) {
	            model.addAttribute("error", ex.getMessage());
	        } catch (UnsupportedEncodingException | MessagingException e) {
	            model.addAttribute("error", "Error while sending email");
	        }
	             
	        return "forgot_password_form";
	    }
	     
	    public void sendEmail(String recipientEmail, String link)
	            throws MessagingException, UnsupportedEncodingException {
	        MimeMessage message = mailSender.createMimeMessage();              
	        MimeMessageHelper helper = new MimeMessageHelper(message);
	         
	        helper.setFrom("samucux1@gmail.com", "Soporte Shopme");
	        helper.setTo(recipientEmail);
	         
	        String subject = "Aquí está el enlace para restablecer su contraseña";
	         
	        String content = "<p>Hello,</p>"
	                + "<p>Ha solicitado restablecer su contraseña.</p>"
	                + "<p>Haga clic en el enlace de abajo para cambiar su contraseña:</p>"
	                + "<p><a href=\"" + link + "\">Cambiar mi contraseña</a></p>"
	                + "<br>"
	                + "<p>Ignore este correo electrónico si recuerda su contraseña, "
	                + "o no ha realizado esta solicitud</p>";
	         
	        helper.setSubject(subject);
	         
	        helper.setText(content, true);
	         
	        mailSender.send(message);
	    }
	     
	     
	    @GetMapping("/reset_password")
	    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
	        User customer = userService.getByResetPasswordToken(token);
	        model.addAttribute("token", token);
	         
	        if (customer == null) {
	            model.addAttribute("message", "Invalid Token");
	            return "message";
	        }
	         
	        return "reset_password_form";
	    }
	     
	    @PostMapping("/reset_password")
	    public String processResetPassword(HttpServletRequest request, Model model) {
	        String token = request.getParameter("token");
	        String password = request.getParameter("password");
	         
	        User customer = userService.getByResetPasswordToken(token);
	        model.addAttribute("title", "Reset your password");
	         
	        if (customer == null) {
	            model.addAttribute("message", "Invalid Token");
	            return "message";
	        } else {           
	        	userService.updatePassword(customer, password);
	             
	            model.addAttribute("message", "Has cambiado satisfactoriamente tu contraseña.");
	        }
	         
	        return "reset_password_form";
	    }
}
