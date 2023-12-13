package net.javaguides.springboot.springsecurity.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import 
javax.persistence.Id
;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class ProductoVendido {
	@Id
	    @GeneratedValue(strategy = 
GenerationType.AUTO
)
	    private int id;
	    private Float cantidad, precio;
	    private String nombre,codigo;
	    private Boolean estado;
	    @ManyToOne
	    @JoinColumn
	    private Venta venta;

	    @ManyToOne
	    @JoinColumn
	    private User users;

	    public ProductoVendido(Float cantidad, Float precio, String nombre, String codigo, Boolean estado, Venta venta, User users) {
	        this.cantidad = cantidad;
	        this.precio = precio;
	        this.nombre = nombre;
	        this.codigo = codigo;
	        this.venta = venta;
	        this.estado = estado;
	        this.users = users;

	    }
	    
	    

		 public int getId() {
			return id;
		}



		public void setId(int id) {
		this.id = id;
		}



		public Boolean getEstado() {
			return estado;
		}



		public void setEstado(Boolean estado) {
			this.estado = estado;
		}

	    public User getUsers() {
			return users;
		}



		public void setUsers(User users) {
			this.users = users;
		}



		public ProductoVendido() {
	    }

	   


		public Float getTotal() {
	        return this.cantidad * this.precio;
	    }

	    public Venta getVenta() {
	        return venta;
	    }

	    public void setVenta(Venta venta) {
	        this.venta = venta;
	    }

	    public Float getPrecio() {
	        return precio;
	    }

	    public void setPrecio(Float precio) {
	        this.precio = precio;
	    }

	    public Float getCantidad() {
	        return cantidad;
	    }

	    public void setCantidad(Float cantidad) {
	        this.cantidad = cantidad;
	    }

	    public String getNombre() {
	        return nombre;
	    }

	    public void setNombre(String nombre) {
	        this.nombre = nombre;
	    }

	    public String getCodigo() {
	        return codigo;
	    }

	    public void setCodigo(String codigo) {
	        this.codigo = codigo;
	    }
}