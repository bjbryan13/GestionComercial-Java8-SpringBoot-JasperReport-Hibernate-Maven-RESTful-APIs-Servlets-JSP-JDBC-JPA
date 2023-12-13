package net.javaguides.springboot.springsecurity.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import net.javaguides.springboot.springsecurity.model.poducto;
import net.javaguides.springboot.springsecurity.repository.ProductoRepository;

@Service
public class ProductoServiceImpl implements ProductoService{

	@Autowired
	private ProductoRepository daoProd;
	
	@Override
	public List<poducto> getAllProducto() {
		return  daoProd.findAll();		
	}

	@Override
	public void saveProducto(poducto personaa) {
		this.daoProd.save(personaa);		
	}

	@Override
	public poducto getProductoById(long id) {
		Optional<poducto> optional = daoProd.findById(id);
		poducto producto = null;
		if (optional.isPresent()) {
			producto = optional.get();
		} else {
			throw new RuntimeException(" producto no encontrado para id :: " + id);
		}
		return producto;
	}

	@Override
	public void deleteProductoById(long id) {
		this.daoProd.deleteById(id);
		
	}

	@Override
	public Page<poducto> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
			Sort.by(sortField).descending();
		
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.daoProd.findAll(pageable);
	}

	@Override
	public List<poducto> listaProducto() {
		return daoProd.findAll();
	}
	
	

}
