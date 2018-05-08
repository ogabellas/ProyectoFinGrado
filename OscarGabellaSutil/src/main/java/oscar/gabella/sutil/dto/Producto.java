package oscar.gabella.sutil.dto;

import java.io.Serializable;

public class Producto implements Serializable{
	
	private int codProducto;
	private String nombre;
	private int stock;
	private double precio;
	private int provedor;
	public Producto() {
		super();
	}
	public int getCodProducto() {
		return codProducto;
	}
	public void setCodProducto(int codProducto) {
		this.codProducto = codProducto;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public int getProvedor() {
		return provedor;
	}
	public void setProvedor(int provedor) {
		this.provedor = provedor;
	}
	
	

}
