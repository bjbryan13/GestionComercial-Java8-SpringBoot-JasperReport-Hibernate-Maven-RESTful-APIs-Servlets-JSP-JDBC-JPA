package net.javaguides.springboot.springsecurity.service;

import java.util.List;

import org.springframework.data.domain.Page;

import net.javaguides.springboot.springsecurity.model.ProductoVendido;


public interface ProductoVendidoService {
	List<ProductoVendido> getAllProducto();
	void saveProducto(ProductoVendido personaa);
	ProductoVendido getProductoById(long id);
	void deleteProductoById(long id);
	Page<ProductoVendido> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
