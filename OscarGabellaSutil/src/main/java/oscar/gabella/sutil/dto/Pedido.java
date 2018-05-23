package oscar.gabella.sutil.dto;

import java.io.Serializable;
import java.util.Date;

public class Pedido implements Serializable{
	
	private int codPedido;
	private int codProducto;
	private int estado;
	private int cantidad;
	private int solicitado;
	private int aprobado;
	private int recibido;
	private Date fecha;
	private String nombreProducto;
	public Pedido() {
		super();
		fecha = new Date();
	}
	public int getCodPedido() {
		return codPedido;
	}
	public void setCodPedido(int codPedido) {
		this.codPedido = codPedido;
	}
	public int getCodProducto() {
		return codProducto;
	}
	public void setCodProducto(int codProducto) {
		this.codProducto = codProducto;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public int getSolicitado() {
		return solicitado;
	}
	public void setSolicitado(int solicitado) {
		this.solicitado = solicitado;
	}
	public int getAprobado() {
		return aprobado;
	}
	public void setAprobado(int aprobado) {
		this.aprobado = aprobado;
	}
	public int getRecibido() {
		return recibido;
	}
	public void setRecibido(int recibido) {
		this.recibido = recibido;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public String getNombreProducto() {
		return nombreProducto;
	}
	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}
	
	

}
