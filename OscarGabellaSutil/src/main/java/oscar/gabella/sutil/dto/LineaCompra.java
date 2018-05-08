package oscar.gabella.sutil.dto;

import java.io.Serializable;

public class LineaCompra implements Serializable{
	
	private int codLinea;
	private int codProducto;
	private int cantidad;
	private int codCompra;
	private double precio;
	private double total;
	private String nombreProducto;
	public LineaCompra() {
		super();
	}
	public int getCodLinea() {
		return codLinea;
	}
	public void setCodLinea(int codLinea) {
		this.codLinea = codLinea;
	}
	public int getCodProducto() {
		return codProducto;
	}
	public void setCodProducto(int codProducto) {
		this.codProducto = codProducto;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public int getCodCompra() {
		return codCompra;
	}
	public void setCodCompra(int codCompra) {
		this.codCompra = codCompra;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public String getNombreProducto() {
		return nombreProducto;
	}
	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}
	
	
	
}
