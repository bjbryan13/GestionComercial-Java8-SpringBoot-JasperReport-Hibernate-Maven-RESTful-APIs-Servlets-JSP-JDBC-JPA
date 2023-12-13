package net.javaguides.springboot.springsecurity.model;

public class ProductoParaVender extends poducto {

	 private Float cantidad;

	    public ProductoParaVender(String nombre, String codigo, Float precio, Float existencia, long l, Float cantidad) {
	        super(nombre, codigo, precio, existencia, l);
	        this.cantidad = cantidad;
	    }

	    public ProductoParaVender(String nombre, String codigo, Float precio, Float existencia, Float cantidad) {
	        super(nombre, codigo, precio, existencia);
	        this.cantidad = cantidad;
	    }

	    public void aumentarCantidad() {
	        this.cantidad++;
	    }

	    public Float getCantidad() {
	        return cantidad;
	    }

	    public Float getTotal() {
	        return this.getPrecio() * this.cantidad;
	    }
}
