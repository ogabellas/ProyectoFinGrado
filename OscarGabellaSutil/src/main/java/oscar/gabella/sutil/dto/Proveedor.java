package oscar.gabella.sutil.dto;

import java.io.Serializable;

public class Proveedor implements Serializable{
	
	private int codProvedor;
	private String cif;
	private String nombre;
	private String email;
	private String direccion;
	private int telefono;
	public Proveedor() {
		super();
	}
	public int getCodProvedor() {
		return codProvedor;
	}
	public void setCodProvedor(int codProvedor) {
		this.codProvedor = codProvedor;
	}
	public String getCif() {
		return cif;
	}
	public void setCif(String cif) {
		this.cif = cif;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public int getTelefono() {
		return telefono;
	}
	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}
	
	

}
