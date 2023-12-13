package net.javaguides.springboot.springsecurity.web;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import net.javaguides.springboot.springsecurity.model.ProductoVendido;
import net.javaguides.springboot.springsecurity.model.Role;
import net.javaguides.springboot.springsecurity.model.User;
import net.javaguides.springboot.springsecurity.model.Venta;
import net.javaguides.springboot.springsecurity.repository.ProductosVendidosRepository;
import net.javaguides.springboot.springsecurity.repository.VentasRepository;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
@RequestMapping(path = "/reportes")
public class ExportController {

    @Autowired
    ApplicationContext context;

    @Autowired
    private ProductosVendidosRepository repository;
    @Autowired
    private VentasRepository repoo;
    
    @GetMapping("/generar_reporte_fecha")
    public String reportess(Model model) { 
    	return "generar_reporte_fecha";
    
    }
    
    @GetMapping("/generando")
    public String generateReport(@RequestParam(value = "fechainicio") String fechainical, @RequestParam(value = "fechafinal") String fechafinal,Model model) {      
    	List<ProductoVendido> cars = repository.findByAndVentaFechaYHoraBetween(fechainical , fechafinal+1);
    	
    	//antes era void
    /////	Resource resource = context.getResource("classpath:jasperreports/rpt_users.jrxml");
    /////	InputStream inputStream = resource.getInputStream();
    /////	JasperReport report = JasperCompileManager.compileReport(inputStream);
    /////	Map<String, Object> params = new HashMap<>();

    /////	JRDataSource dataSource = new JRBeanCollectionDataSource(cars);
    /////	params.put("datasource", dataSource);
    /////	JasperPrint jasperPrint = JasperFillManager.fillReport(report, params, dataSource);
     
    /////	response.setContentType(MediaType.APPLICATION_PDF_VALUE);
    /////  	JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());

    	model.addAttribute("ventas", repoo.findProductosByAndFechaYHoraBetween(fechainical, fechafinal+1));
    	model.addAttribute("fechainical", fechainical);
    	model.addAttribute("fechafinal", fechafinal);
    	System.out.print("ok");
    	return "reporte";
    	
    }
    
}
