package net.javaguides.springboot.springsecurity.repository;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.jupiter.api.Test;
import org.springframework.data.repository.CrudRepository;

import net.javaguides.springboot.springsecurity.model.ProductoVendido;
import net.javaguides.springboot.springsecurity.model.Venta;


public interface VentasRepository extends CrudRepository<Venta, Integer> {

	List<Venta> findByUsers_id(Long users_id);

	List<Venta> findProductosByAndFechaYHoraBetween(String fechainical, String fechafinal);
	
}
///SELECT * FROM `producto_vendido` INNER JOIN `venta` ON venta.id=producto_vendido.venta_id WHERE venta.fechayhora BETWEEN '2021-02-20' AND '2021-04-20'

