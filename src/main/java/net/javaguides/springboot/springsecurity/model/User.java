package net.javaguides.springboot.springsecurity.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String resetPasswordToken;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private Set<ProductoVendido> prodVenidos;
    
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private Set<Venta> ventas;
    
    public User() {
    }

    public User(String firstName, String lastName, String email, String password,String resetPasswordToken) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.resetPasswordToken = resetPasswordToken;
    }

    public User(String firstName, String lastName, String email, String password,String resetPasswordToken, Collection<Role> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.resetPasswordToken = resetPasswordToken;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }
    
    

    public String getResetPasswordToken() {
		return resetPasswordToken;
	}

	public void setResetPasswordToken(String resetPasswordToken) {
		this.resetPasswordToken = resetPasswordToken;
	}
	
	

	public Set<ProductoVendido> getProdVenidos() {
		return prodVenidos;
	}

	public void setProdVenidos(Set<ProductoVendido> prodVenidos) {
		this.prodVenidos = prodVenidos;
	}

	@Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + "*********" + '\'' +
                ", resetPasswordToken='" + "*********" + '\'' +
                ", roles=" + roles +
                '}';
    }
}
