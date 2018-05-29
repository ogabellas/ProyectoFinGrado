package oscar.gabella.sutil.dto;

import java.io.Serializable;

public class ProductoEstrella implements Serializable{
	
	private int suma;
	private int codProducto;
	private String nomProducto;
	
	
	
	public ProductoEstrella() {
		super();
	}
	public int getSuma() {
		return suma;
	}
	public void setSuma(int suma) {
		this.suma = suma;
	}
	public int getCodProducto() {
		return codProducto;
	}
	public void setCodProducto(int codProducto) {
		this.codProducto = codProducto;
	}
	public String getNomProducto() {
		return nomProducto;
	}
	public void setNomProducto(String nomProducto) {
		this.nomProducto = nomProducto;
	}
	
	

}
