package oscar.gabella.sutil.controlador;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import oscar.gabella.sutil.dto.Empleado;
import oscar.gabella.sutil.dto.EmpleadoSesion;
import oscar.gabella.sutil.servicio.ServicioEmpleado;
import oscar.gabella.sutil.dto.Pedido;
import oscar.gabella.sutil.dto.Producto;
import oscar.gabella.sutil.dto.Proveedor;

@Controller
@SessionAttributes(types = { EmpleadoSesion.class })
public class AdministradorController {
	
	private static final Logger logger = LoggerFactory.getLogger(AdministradorController.class);
	
	@Autowired
	private ServicioEmpleado servicioEmpleado;
	
	private boolean loging;
	
	private Empleado empleado;
	
	private String accion;
	
	@RequestMapping(value = "/aprobarPedido", method = RequestMethod.GET)
	public String aprobarPedidoGET( Model model,HttpSession sesion) {
		logger.info("Inicio aprobar Pedido GET");
		EmpleadoSesion empleadoSesion = (EmpleadoSesion) sesion.getAttribute("empleadoSesion");
		if(null!=empleadoSesion) {
			empleado = empleadoSesion.getEmpleado();			
		} else {
			return"home";
		}
		model.addAttribute("empleado", empleado);
		model.addAttribute("mesaje", "");
		return"administradosJS";
	}
	
	@RequestMapping(value = "/recibirPedido", method = RequestMethod.GET)
	public String recibirPedidoGET( Model model,HttpSession sesion) {
		logger.info("Inicio recibir Pedido GET");
		EmpleadoSesion empleadoSesion = (EmpleadoSesion) sesion.getAttribute("empleadoSesion");
		if(null!=empleadoSesion) {
			empleado = empleadoSesion.getEmpleado();			
		} else {
			return"home";
		}
		model.addAttribute("empleado", empleado);
		model.addAttribute("mesaje", "");
		return"administradosRecibir";
	}
	
	@RequestMapping(value = "/productoAlta", method = RequestMethod.GET)
	public String nuevoProductoGET( Model model,HttpSession sesion) {
		logger.info("Inicio recibir Pedido GET");
		EmpleadoSesion empleadoSesion = (EmpleadoSesion) sesion.getAttribute("empleadoSesion");
		if(null!=empleadoSesion) {
			empleado = empleadoSesion.getEmpleado();	
			model.addAttribute("proveedores", servicioEmpleado.getProveedores());
		} else {
			return"home";
		}
		model.addAttribute("empleado", empleado);
		model.addAttribute("pantalla", "producto");
		model.addAttribute("mesaje", "");
		return"administrador";
	}
	
	@RequestMapping(value = "/provedorAlta", method = RequestMethod.GET)
	public String nuevoProveedorGET( Model model,HttpSession sesion) {
		logger.info("Inicio recibir Pedido GET");
		EmpleadoSesion empleadoSesion = (EmpleadoSesion) sesion.getAttribute("empleadoSesion");
		if(null!=empleadoSesion) {
			empleado = empleadoSesion.getEmpleado();	
			model.addAttribute("proveedores", servicioEmpleado.getProveedores());
		} else {
			return"home";
		}
		model.addAttribute("empleado", empleado);
		model.addAttribute("pantalla", "proveedor");
		model.addAttribute("mesaje", "");
		return"administrador";
	}
	
	@RequestMapping(value = "/buscarPedidosAprobar", method = RequestMethod.POST)
	@ResponseBody
	public List<Pedido> ajaxPedidosAprobar( Model model) {
		logger.info("buscarPedidosAprobar");
		
		return servicioEmpleado.buscarPedidos(0);
	}
	
	@RequestMapping(value = "/buscarPedidosRecibir", method = RequestMethod.POST)
	@ResponseBody
	public List<Pedido> ajaxPedidosRecibir( Model model) {
		logger.info("buscar Pedidos Recibir");
		
		return servicioEmpleado.buscarPedidos(1);
	}
	
	@RequestMapping(value = "/modificarPedido", method = RequestMethod.POST)
	@ResponseBody
	public void ajaxmodificarPedido( Model model,  @RequestParam("datos") String[] datos) {
		logger.info("modificarPedido");
		
		Pedido pedido = new Pedido();
		pedido.setCodPedido(Integer.parseInt(datos[0]));
		pedido.setCantidad(Integer.parseInt(datos[1]));
		servicioEmpleado.modificarCantidadPedido(pedido);

	}
	
	@RequestMapping(value = "/modificarPedidoRecibir", method = RequestMethod.POST)
	@ResponseBody
	public void ajaxmodificarPedidoRecibir( Model model,  @RequestParam("datos") String[] datos) {
		logger.info("modificarPedido Recibir");
		
		Pedido pedido = new Pedido();
		Pedido pedidoViejo = new Pedido();
		pedido.setCodPedido(Integer.parseInt(datos[0]));
		pedido.setCantidad(Integer.parseInt(datos[1]));
		//Se recupera el pedido original
		pedidoViejo=servicioEmpleado.getDaoEmpleado().consultarPedido(pedido).get(0);
		int aumentarStock = pedidoViejo.getCantidad()-pedido.getCantidad();
		//Se modifica el pedido
		servicioEmpleado.modificarCantidadPedido(pedido);
		//Se aumenta el stock con los productos recibidos
		Producto p = new Producto();
		p.setCodProducto(pedidoViejo.getCodProducto());
		p=servicioEmpleado.recuperarProducto(p);
		p.setStock(p.getStock()+aumentarStock);
		servicioEmpleado.getDaoEmpleado().modificarStockProducto(p);
		
	}
	
	@RequestMapping(value = "/aprobarPedidos", method = RequestMethod.POST)
	@ResponseBody
	public void ajaxAprobarPedidos( Model model,  @RequestParam("id") String ids, HttpSession sesion) {
		logger.info("ajaxAprobarPedidos");
		EmpleadoSesion empleadoSesion = (EmpleadoSesion) sesion.getAttribute("empleadoSesion");
		if(null!=empleadoSesion) {
			empleado = empleadoSesion.getEmpleado();			
			ids.substring(0, ids.length()-1);
			String [] id = ids.split(";");
			servicioEmpleado.modificarPedidos(1, id,empleado.getCodEmpleado());
		}
	}
	
	@RequestMapping(value = "/recibirPedidos", method = RequestMethod.POST)
	@ResponseBody
	public void ajaxRecibirPedidos( Model model,  @RequestParam("id") String ids, HttpSession sesion) {
		logger.info("ajaxAprobarPedidos");
		EmpleadoSesion empleadoSesion = (EmpleadoSesion) sesion.getAttribute("empleadoSesion");
		if(null!=empleadoSesion) {
			empleado = empleadoSesion.getEmpleado();			
			ids.substring(0, ids.length()-1);
			String [] id = ids.split(";");
//			servicioEmpleado.modificarPedidos(1, id,empleado.getCodEmpleado());
			servicioEmpleado.recibirPedido(empleadoSesion, id);
		}
	}
		
		@RequestMapping(value = "/rechazarPedidos", method = RequestMethod.POST)
		@ResponseBody
		public void ajaxRechazarPedidos( Model model,  @RequestParam("id") String ids, HttpSession sesion) {
			logger.info("ajaxAprobarPedidos");
			EmpleadoSesion empleadoSesion = (EmpleadoSesion) sesion.getAttribute("empleadoSesion");
			if(null!=empleadoSesion) {
				empleado = empleadoSesion.getEmpleado();			
				ids.substring(0, ids.length()-1);
				String [] id = ids.split(";");
				servicioEmpleado.modificarPedidos(3, id,empleado.getCodEmpleado());
			}	
	}

	@RequestMapping(value = "/altaProducto", method = RequestMethod.POST)
	public String altProducto(Model model, HttpSession sesion, @RequestParam("nombre") String nombre,
			@RequestParam("precio") String precio, @RequestParam("proveedor") String proveedor) {
		String resultado = "administrador";
		logger.info("alta de producto");
		Producto producto = new Producto();
		producto.setNombre(nombre);
		producto.setPrecio(Double.parseDouble(precio));
		producto.setProvedor(Integer.parseInt(proveedor));
		producto.setStock(0);
		servicioEmpleado.insertarProducto(producto);
		model.addAttribute("mesaje", "Producto dado de alta correctamente");
		model.addAttribute("pantalla", "producto");
		return resultado;
		}
	
	@RequestMapping(value = "/altaProveedor", method = RequestMethod.POST)
	public String altaProveedor(Model model, HttpSession sesion, @RequestParam("cif") String cif,@RequestParam("nombre") String nombre,
			@RequestParam("correo") String correo, @RequestParam("direccion") String direccion, @RequestParam("telefono") String telefono) {
		String resultado = "administrador";
		logger.info("alta de proveedor");

		Proveedor proveedor = new Proveedor();
		proveedor.setCif(cif);
		proveedor.setDireccion(direccion);
		proveedor.setEmail(correo);
		proveedor.setNombre(nombre);
		proveedor.setTelefono(Integer.parseInt(telefono));
		
		servicioEmpleado.insertarProveedor(proveedor);
	
		model.addAttribute("mesaje", "Proveedor dado de alta correctamente");
		model.addAttribute("pantalla", "proveedor");
		return resultado;
	}
}
