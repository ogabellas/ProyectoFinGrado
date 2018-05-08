package oscar.gabella.sutil.dto;
import java.util.ArrayList;
import java.util.List;
public class EmpleadoSesion {
	
	private List<LineaCompra> lineasCompra;
	private Empleado empleado;
	private Cliente cliente;
	public EmpleadoSesion() {
		super();
		lineasCompra = new ArrayList<LineaCompra>();
	}
	public List<LineaCompra> getLineasCompra() {
		return lineasCompra;
	}
	public void setLineasCompra(List<LineaCompra> lineasCompra) {
		this.lineasCompra = lineasCompra;
	}
	public Empleado getEmpleado() {
		return empleado;
	}
	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	

	
	

}
