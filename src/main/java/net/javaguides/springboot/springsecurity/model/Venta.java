package net.javaguides.springboot.springsecurity.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;



@Entity
public class Venta {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id;
	    private String fechaYHora;
	    private String tipoDeEnvio;
	    private String formaDePago;

	    @ManyToOne
	    @JoinColumn
	    private User users;

		@OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
	    private Set<ProductoVendido> productos;
		
	    public Venta() {
			super();
			// TODO Auto-generated constructor stub
		}

		public Venta(String fechaYHora, String formaDePago, String tipoDeEnvio, User users) {
	        this.fechaYHora = Utiles.obtenerFechaYHoraActual();
	        this.formaDePago = formaDePago;
	        this.tipoDeEnvio = tipoDeEnvio;
	        this.users = users;
	    }
		
		 public User getUsers() {
				return users;
			}



			public void setUsers(User users) {
				this.users = users;
			}





		
	    public String getTipoDeEnvio() {
			return tipoDeEnvio;
		}

		public void setTipoDeEnvio(String tipoDeEnvio) {
			this.tipoDeEnvio = tipoDeEnvio;
		}

		public String getFormaDePago() {
			return formaDePago;
		}

		public void setFormaDePago(String formaDePago) {
			this.formaDePago = formaDePago;
		}

		public Integer getId() {
	        return id;
	    }

	    
		public void setId(Integer id) {
	        this.id = id;
	    }

	    public Float getTotal() {
	        Float total = 0f;
	        for (ProductoVendido productoVendido : this.productos) {
	            total += productoVendido.getTotal();
	        }
	        return total;
	    }
	    
	    public String getFechaYHora() {
	        return fechaYHora;
	    }

	    public void setFechaYHora(String fechaYHora) {
	        this.fechaYHora = fechaYHora;
	    }

	    public Set<ProductoVendido> getProductos() {
	        return productos;
	    }

	    public void setProductos(Set<ProductoVendido> productos) {
	        this.productos = productos;
	    }

}
