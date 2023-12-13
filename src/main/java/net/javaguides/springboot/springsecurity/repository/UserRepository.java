package net.javaguides.springboot.springsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.javaguides.springboot.springsecurity.model.User;


public interface UserRepository extends CrudRepository<User, Long> {
	User findByEmail(String email);
	
	    @Query("SELECT c FROM User c WHERE c.email = ?1")
	    public User findByEmailReset(String email); 
	     
	    public User findByResetPasswordToken(String token);
}
