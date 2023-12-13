package net.javaguides.springboot.springsecurity.service;

import java.util.List;

import org.springframework.data.domain.Page;

import net.javaguides.springboot.springsecurity.model.poducto;


public interface ProductoService {

	List<poducto> getAllProducto();
	void saveProducto(poducto personaa);
	poducto getProductoById(long id);
	void deleteProductoById(long id);
	Page<poducto> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
	 List<poducto> listaProducto();
}
