package net.javaguides.springboot.springsecurity.repository;



import java.util.List;

import org.springframework.data.repository.CrudRepository;

import net.javaguides.springboot.springsecurity.model.ProductoVendido;


public interface ProductosVendidosRepository extends CrudRepository<ProductoVendido, Integer> {
		
	List<ProductoVendido> findByNombre(String nombre);
	int countByEstado(Boolean estado);
	int countByEstadoAndUsers_id(Boolean estado, Long users_id);
	int countByUsers_id(Long users_id);

	List<ProductoVendido> findByAndVentaFechaYHoraBetween(String fechainical, String fechafinal);
	
	///@Query("select u from producto_vendido u inner join venta on venta.id=u.id where venta.fechayhora Between ?1 and ?2")
	///ProductoVendido testQueryAnnotationParams1(String data1,String data2);

}
