package oscar.gabella.sutil.servicio;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import oscar.gabella.sutil.dao.DaoEmpleado;
import oscar.gabella.sutil.dto.Cliente;
import oscar.gabella.sutil.dto.Compra;
import oscar.gabella.sutil.dto.Empleado;
import oscar.gabella.sutil.dto.EmpleadoSesion;
import oscar.gabella.sutil.dto.LineaCompra;
import oscar.gabella.sutil.dto.Pedido;
import oscar.gabella.sutil.dto.Producto;
import oscar.gabella.sutil.dto.Proveedor;

public class ServicioEmpleado {


	private DaoEmpleado daoEmpleado;
	



	
	public DaoEmpleado getDaoEmpleado() {
		return daoEmpleado;
	}

	public void setDaoEmpleado(DaoEmpleado daoEmpleado) {
		this.daoEmpleado = daoEmpleado;
	}
	
	public int insertarCliente(Cliente c) {
		int insertado=0;
		insertado=daoEmpleado.insertarCliente(c);
		return insertado;
	}
	
	public Producto recuperarProducto(Producto p) {
		List<Producto> productos = new ArrayList<Producto>();
		Producto producto = new Producto();
		productos = daoEmpleado.getProducto(p);
		if(productos.size() != 0)
			producto = productos.get(0);
		return producto;
	}
	public int realizarCompra(EmpleadoSesion empleadoSesion) {
		double total=0;
		int contador=0;
		for(LineaCompra linea:empleadoSesion.getLineasCompra()) {
			total+=linea.getTotal();
		}
		Compra compra= new Compra();
		compra.setCodCliente(empleadoSesion.getCliente().getCodCliente());
		compra.setCodEmpleado(empleadoSesion.getEmpleado().getCodEmpleado());
		compra.setTotal(total);
		daoEmpleado.insertCompra(compra);
		int codCompra = daoEmpleado.getUltimaCompra(compra).getCodCompra();
		List<LineaCompra> lineas = new ArrayList<LineaCompra>();
		for(LineaCompra linea:empleadoSesion.getLineasCompra()) {
			linea.setCodCompra(codCompra);
			daoEmpleado.insertarLinea(linea);
			contador++;
			//restar stock
			Producto producto = new Producto();
			producto.setCodProducto(linea.getCodProducto());
			producto=daoEmpleado.getProducto(producto).get(0);
			producto.setStock(producto.getStock()-linea.getCantidad());
			daoEmpleado.modificarProducto(producto);
		}
		return contador;
	}
	public String buscarDNI(int c) {
		String dni="";
		Cliente cliente = new Cliente();
		cliente.setCodCliente(c);
		cliente.setDni(c+"");
		cliente = daoEmpleado.buscarDNI(cliente);
		dni = cliente.getDni()+"";
		return dni;
	}
	
	public List<LineaCompra> buscarPedido(Compra compra){
		List<LineaCompra> lineas = new ArrayList<LineaCompra>();
		lineas= daoEmpleado.buscarPedido(compra);
		if(null!=lineas&&!lineas.isEmpty()) {
			for(LineaCompra linea:lineas) {
				Producto producto = new Producto();
				producto.setCodProducto(linea.getCodProducto());
				try {
					producto=daoEmpleado.getProducto(producto).get(0);
				} catch (Exception e) {
					// TODO: handle exception
				}
				linea.setNombreProducto(producto.getNombre());
			}
		}
		return lineas;
	}
	public LineaCompra buscarlinea(LineaCompra linea){

		int cantidad = linea.getCantidad();
		linea = daoEmpleado.buscarLinea(linea).get(0);
		int diferenciaStock=linea.getCantidad()-cantidad;
		linea.setCantidad(cantidad);
		linea.setTotal(cantidad*linea.getPrecio());
		daoEmpleado.modificarLinea(linea);
		//sumar stock
		Producto producto = new Producto();
		producto.setCodProducto(linea.getCodProducto());
		producto=daoEmpleado.getProducto(producto).get(0);
		producto.setStock(producto.getStock()+diferenciaStock);
		daoEmpleado.modificarProducto(producto);
		return linea;
	}
	
	public Compra buscarCompra(Compra compra) {
		compra = daoEmpleado.getCompra(compra);
		return compra;
	}
	public List<Pedido> buscarPedidos(int tipo){
		Pedido p = new Pedido();
		p.setEstado(tipo);
		List <Pedido> pedidos = new ArrayList<Pedido>();
		pedidos = daoEmpleado.recuperarPedidos(p);
		for(Pedido pedido:pedidos) {
			Producto producto = new Producto();
			producto.setCodProducto(pedido.getCodProducto());
			producto = daoEmpleado.getProducto(producto).get(0);
			pedido.setNombreProducto(producto.getNombre());
		}
		return pedidos;
	}
	public void modificarPedidos(int tipo,String[] pedidos, int empleado){
		
		for (int i = 0; i < pedidos.length; i++) {
			Pedido p = new Pedido();
			p.setEstado(tipo);
			p.setCodPedido(Integer.parseInt(pedidos[i]));
			p.setAprobado(empleado);
			daoEmpleado.modificarPedido(p);
		}
	}
	
	public void modificarCantidadPedido(Pedido pedido) {
		daoEmpleado.modificarCantidadPedido(pedido);
	}
	
	public void recibirPedido(EmpleadoSesion empleadoSesion, String [] pedidos) {
		
		//Se recorren todos los pedidos enviados
		for (int i = 0; i < pedidos.length; i++) {
			//se recupera el pedido completo
			Pedido pedido = new Pedido();
			pedido.setCodPedido(Integer.parseInt(pedidos[i]));
			pedido = daoEmpleado.consultarPedido(pedido).get(0);
			//se modifica el pedido
			pedido.setRecibido(empleadoSesion.getEmpleado().getCodEmpleado());
			daoEmpleado.recibirPedido(pedido);
			//Se modifica el stock del producto
			Producto producto = new Producto();
			producto.setCodProducto(pedido.getCodProducto());
			producto = daoEmpleado.getProducto(producto).get(0);
			producto.setStock(producto.getStock()+pedido.getCantidad());
			daoEmpleado.modificarProducto(producto);
		}
		
	}
	
	public ArrayList<Proveedor> getProveedores(){
		List <Proveedor> provedores = new ArrayList<Proveedor>();
		provedores=daoEmpleado.getProveedores();
		return (ArrayList<Proveedor>) provedores;
	}
	
	public void insertarProducto(Producto producto) {
		daoEmpleado.insertarProducto(producto);
	}
	
	public void insertarProveedor(Proveedor proveedor) {
		daoEmpleado.insertarProveedor(proveedor);
	}
	
	public boolean empleadoExiste(Empleado empleado) {
		boolean existe=false;
		List<Empleado> empleados = new ArrayList<Empleado>();
		empleados = daoEmpleado.getEmpleadoDNI(empleado);
		if(null!=empleados&&empleados.size()!=0) {
			existe=true;
		}
		return existe;
	}
	
	public void insertarEmpleado(Empleado empleado) {
		daoEmpleado.insertarEmpleado(empleado);
	}
	
}
