package net.javaguides.springboot.springsecurity.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import net.javaguides.springboot.springsecurity.model.ProductoVendido;
import net.javaguides.springboot.springsecurity.model.poducto;
import net.javaguides.springboot.springsecurity.repository.ProductoRepository;
import net.javaguides.springboot.springsecurity.service.ProductoService;



@Controller
public class ProductoController {

	@Autowired
	private ProductoService productoService;
	
	
	
	 @Autowired
	    private ProductoRepository productosRepository;
	
	@GetMapping("/producto")
	public String viewHomePage(Model model) {
		return findPaginated(1, "nombre", "asc", model);		
	}
	
	@GetMapping("/showNewProductoForm")
	public String showNewEmpresaaForm(Model model) {
		// create model attribute to bind form data
		 
		poducto personaa = new poducto();
		model.addAttribute("personaa", personaa);
		return "new_producto";
	}
	
	@PostMapping("/saveProducto")
	public String saveEmpresaa(@ModelAttribute("personaa") poducto personaa,BindingResult bindingResult,RedirectAttributes redirectAttrs) {
		// save employee to database

		  if (bindingResult.hasErrors()) {
	            return "showNewProductoForm";
	        }
	        if (productosRepository.findFirstByNombre(personaa.getNombre()) != null) {
	            redirectAttrs
	                    .addFlashAttribute("mensaje", "Ya existe un producto con ese código")
	                    .addFlashAttribute("clase", "warning");
	            return "redirect:/showNewProductoForm";
	        }
	        productosRepository.save(personaa);
	        redirectAttrs
	                .addFlashAttribute("mensaje", "Agregado correctamente")
	                .addFlashAttribute("clase", "success");
	        return "redirect:/producto";

	    }
	


	
	@PostMapping(value = "/editar/{id}")
    public String actualizarProducto(@ModelAttribute @Valid poducto producto, BindingResult bindingResult, RedirectAttributes redirectAttrs) {
        if (bindingResult.hasErrors()) {
            if (producto.getId() != 0L) {
                return "update_producto";
            }
            return "redirect:/producto";
        }
        poducto posibleProductoExistente = productosRepository.findFirstByNombre(producto.getNombre());

        if (posibleProductoExistente != null && !(posibleProductoExistente.getId()==producto.getId())) {
            redirectAttrs
                    .addFlashAttribute("mensaje", "Ya existe un producto con ese código")
                    .addFlashAttribute("clase", "warning");
            return "redirect:/showNewProductoForm";
        }
        productosRepository.save(producto);
        redirectAttrs
                .addFlashAttribute("mensaje", "Editado correctamente")
                .addFlashAttribute("clase", "success");
        return "redirect:/producto";
    }
	
	 @GetMapping(value = "/editar/{id}")
	    public String mostrarFormularioEditar(@PathVariable int id, Model model) {
	        model.addAttribute("producto", productosRepository.findById((long) id).orElse(null));
	        return "update_producto";
	    }
	

	@GetMapping( "/deleteProducto/{id}")
	public String deletePersonaa(@PathVariable (value = "id") long id) {

		this.productoService.deleteProductoById(id);

		// call delete employee method 
		return "redirect:/producto";
	}
	
	@GetMapping("/pageProducto/{pageNo}")
	public String findPaginated(@PathVariable (value = "pageNo") int pageNo, 
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {
		int pageSize = 7000;
		
		Page<poducto> page = productoService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<poducto> listPersonaa = page.getContent();
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		
		model.addAttribute("listPersonaas", listPersonaa);
		return "index_producto";
	}
}
