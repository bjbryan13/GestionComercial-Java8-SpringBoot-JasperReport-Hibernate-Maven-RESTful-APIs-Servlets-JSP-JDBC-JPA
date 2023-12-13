package net.javaguides.springboot.springsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.javaguides.springboot.springsecurity.model.ProductoVendido;

@Repository
public interface productovendidorepo extends JpaRepository<ProductoVendido, Integer>{
	
	
}
