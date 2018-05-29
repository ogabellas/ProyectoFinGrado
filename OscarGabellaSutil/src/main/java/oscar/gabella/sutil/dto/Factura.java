package oscar.gabella.sutil.dto;
import java.util.Date;
import java.util.List;

public class Factura {
	
	private List<LineaCompra> lineas;
	private String codEmpleado;
	private Date fecha;
	private String codCliente;
	private String nombreEmpleado;
	private String nombreCliente;
	private String total;
	
	
	public Factura() {
		super();
	}


	public List<LineaCompra> getLineas() {
		return lineas;
	}


	public void setLineas(List<LineaCompra> lineas) {
		this.lineas = lineas;
	}


	public String getCodEmpleado() {
		return codEmpleado;
	}


	public void setCodEmpleado(String codEmpleado) {
		this.codEmpleado = codEmpleado;
	}


	public Date getFecha() {
		return fecha;
	}


	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}


	public String getCodCliente() {
		return codCliente;
	}


	public void setCodCliente(String codCliente) {
		this.codCliente = codCliente;
	}


	public String getNombreEmpleado() {
		return nombreEmpleado;
	}


	public void setNombreEmpleado(String nombreEmpleado) {
		this.nombreEmpleado = nombreEmpleado;
	}


	public String getNombreCliente() {
		return nombreCliente;
	}


	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}


	public String getTotal() {
		return total;
	}


	public void setTotal(String total) {
		this.total = total;
	}
	


}
