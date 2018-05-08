package oscar.gabella.sutil.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import oscar.gabella.sutil.dto.Cliente;
import oscar.gabella.sutil.dto.Compra;
import oscar.gabella.sutil.dto.Empleado;
import oscar.gabella.sutil.dto.LineaCompra;
import oscar.gabella.sutil.dto.Pedido;
import oscar.gabella.sutil.dto.Producto;

public interface DaoEmpleado {
	
	public List<Empleado> getEmpleados();
	
	public List<Empleado> getEmpleado(Empleado empleado);
	
	public int insertarCliente(Cliente cliente);
	
	public List<Producto> getProductos();
	
	public List<Producto> getProducto(Producto producto);
	
	public void insertarLinea(LineaCompra linea);

	public void insertCompra(Compra compra);
	
	public Compra getUltimaCompra(Compra compra);
	
	public Cliente buscarDNI(Cliente cliente);

	public List<LineaCompra> buscarPedido(Compra compra);
	
	public List<LineaCompra> buscarLinea(LineaCompra linea);
	
	public int modificarProducto(Producto producto);
	
	public int modificarLinea(LineaCompra linea);
	
	public Compra getCompra(Compra compra);
	
	public int modificarCompra(Compra compra);	
	
	public void insertPedido(Pedido pedido);	
}