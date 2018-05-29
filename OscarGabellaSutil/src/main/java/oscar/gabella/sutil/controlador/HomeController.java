package oscar.gabella.sutil.controlador;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import oscar.gabella.sutil.dao.DaoEmpleado;
import oscar.gabella.sutil.dto.Cliente;
import oscar.gabella.sutil.dto.Compra;
import oscar.gabella.sutil.dto.Empleado;
import oscar.gabella.sutil.dto.EmpleadoSesion;
import oscar.gabella.sutil.dto.Factura;
import oscar.gabella.sutil.dto.LineaCompra;
import oscar.gabella.sutil.dto.Pedido;
import oscar.gabella.sutil.dto.Producto;
import oscar.gabella.sutil.servicio.ServicioEmpleado;

/**
 * Handles requests for the application home page.
 */
@Controller
@SessionAttributes(types = { EmpleadoSesion.class })
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private ServicioEmpleado servicioEmpleado;
	
	private boolean loging;
	
	private Empleado empleado;
	
	private String accion;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		model.addAttribute("login", "" );
		return "home";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestParam("usuario")String usuario, @RequestParam("contrasena")String contrasena,
			 Model model,HttpSession sesion) {
		logger.info("Iniciando Login");
		loging=false;
		List<Empleado> empleados = new ArrayList<Empleado>();
		empleado = new Empleado();
		empleado.setContrasena(contrasena);
		empleado.setDni(usuario);
		empleados = servicioEmpleado.getDaoEmpleado().getEmpleado(empleado);
		empleado=empleados.get(0);
		int logueado=empleados.size();
		String redireccion="home";
		//TODO comprobar login
		if (logueado==1) {
			EmpleadoSesion empleadoSesion = new EmpleadoSesion();
			empleadoSesion.setEmpleado(empleado);
			sesion.setAttribute("empleadoSesion", empleadoSesion);
			model.addAttribute("empleado", empleado);
			switch(empleados.get(0).getCargo()) {
			case 0:
				loging = true;
				accion = "venta";
				venta(model);
				redireccion = "cajero";
				break;
			case 1: redireccion = "administrador";
					model.addAttribute("pantalla", "producto");
				break;
			case 2 : redireccion = "gerente";
					model.addAttribute("pantalla", "empleadoAlta");
					break;
			}
		}//TODO comprobar login del resto de opciones
		else {
			model.addAttribute("login", "Login incorrecto" );
		}
		logger.info("Fin Login");
		return redireccion;
	}
	@RequestMapping(value = "/venta", method = RequestMethod.GET)
	public String venta( Model model) {
		logger.info("Iniciando venta GET" );
		if(loging) {
			accion="venta";
			List<String> lineasPedidoActualizadas = new ArrayList<String>();
			model.addAttribute("lineas", lineasPedidoActualizadas);
			model.addAttribute("empleado", empleado);
			model.addAttribute("pantalla", accion);
			List <Producto> productos = servicioEmpleado.getDaoEmpleado().getProductos();
			model.addAttribute("productos", productos);
			return "cajero";
		}
		else {
			return "home";
		}

	}
	@RequestMapping(value = "/venta", method = RequestMethod.POST)
	public String ventaPOST( Model model) {
		logger.info("Iniciando venta");
		if(loging) {
			logger.info("Realizando venta");
//			accion="venta";
//			model.addAttribute("empleado", empleado);
//			model.addAttribute("pantalla", accion);
//			List <Producto> productos = servicioEmpleado.getDaoEmpleado().getProducto();
//			model.addAttribute("productos", productos);
			return "cajero";
		}
		else {
			return "home";
		}
		
	}
	@RequestMapping(value = "/devolucion", method = RequestMethod.GET)
	public String devolucion( Model model) {
		logger.info("Iniciando devolucion");
		if(loging) {
			accion="devolucion";
			model.addAttribute("empleado", empleado);
			model.addAttribute("pantalla", accion);
			return "cajero";
		}
		else {
			return "home";
		}

	}
	@RequestMapping(value = "/devolucion", method = RequestMethod.POST)
	public String devolucionPost( Model model, @RequestParam("pedido") String pedido) {
		logger.info("Iniciando devolucion POST");
			accion="devolucion";
			int numeroPedido=0;
			try {
				numeroPedido = Integer.parseInt(pedido);				
			}catch(Exception e) {};
			
			List<LineaCompra> lineasPrueba = new ArrayList<LineaCompra>();
			Compra compra = new Compra();
			compra.setCodCompra(numeroPedido);
			lineasPrueba = servicioEmpleado.buscarPedido(compra);
			
			model.addAttribute("empleado", empleado);
			model.addAttribute("pantalla", accion);
			model.addAttribute("lineasDevolver", lineasPrueba);
			return "cajero";
		
	}
	@RequestMapping(value = "/pedidoDevolver", method = RequestMethod.POST)
	public String devolucionPedido(Model model, /*@RequestParam("codigos") String[] codigos,*/
			@RequestParam("valores") String valores) {
		logger.info("Iniciando devolucion Pedido POST");
		accion="devolucion";
		String [] valoresRecibidos = valores.split(";");
		double total=0;
		LineaCompra lineaCompra = new LineaCompra();
		for(String lin:valoresRecibidos) {
			int codLinea = 0;
			int cantidad = 0;
			try {
				codLinea = Integer.parseInt(lin.split(":")[0]);	
				cantidad = Integer.parseInt(lin.split(":")[1]);	
			}catch(Exception e) {
			}
			lineaCompra.setCodLinea(codLinea);
			lineaCompra.setCantidad(cantidad);
			//llamar al servicio
			lineaCompra=servicioEmpleado.buscarlinea(lineaCompra);
			total+=lineaCompra.getTotal();
		}
		//modificar compra
		Compra compra= new Compra();
		compra.setCodCompra(lineaCompra.getCodCompra());
		compra = servicioEmpleado.buscarCompra(compra);
		compra.setTotal(total);
		servicioEmpleado.getDaoEmpleado().modificarCompra(compra);
		
		model.addAttribute("empleado", empleado);
		model.addAttribute("pantalla", accion);
		model.addAttribute("lineasDevolver", null);
		return "cajero";
		
	}
	@RequestMapping(value = "/alta", method = RequestMethod.GET)
	public String altaCliente( Model model) {
		logger.info("Iniciando altaCliente");
		if(loging) {
			accion="alta";
			model.addAttribute("empleado", empleado);
			model.addAttribute("pantalla", accion);
			model.addAttribute("mesaje", "");
			return "cajero";
		}
		else {
			return "home";
		}

	}
	@RequestMapping(value = "/alta", method = RequestMethod.POST)
	public String altaClientePost(Model model, @RequestParam("dni") String dni, @RequestParam("nombre") String nombre,
			@RequestParam("correo") String correo, @RequestParam("direccion") String direccion, @RequestParam("telefono") String telefono) {
		logger.info("Iniciando alta cliente por post");
		String mensaje;
		try {
			Cliente cliente = new Cliente();
			cliente.setCorreo(correo);
			cliente.setDireccion(direccion);
			cliente.setDni(dni);
			cliente.setNombre(nombre);
			cliente.setTelefono(Integer.parseInt(telefono));
			servicioEmpleado.insertarCliente(cliente);
			mensaje="Se ha insertado correctamente el cliente";
		} catch (Exception e) {
			mensaje="Error al insertar el cliente";
		}

		
		accion="alta";
		model.addAttribute("empleado", empleado);
		model.addAttribute("mesaje", mensaje);
		model.addAttribute("pantalla", accion);
		logger.info("Fin alta cliente post");
		return "cajero";
	}
	@RequestMapping(value = "/producto", method = RequestMethod.POST)
	public String solicitarPedido(Model model, @RequestParam("producto") String producto, @RequestParam("cantidad") String cantidad) {
		logger.info("Iniciando solicitar pedido post");
		String mensaje="";
		try {
			Pedido pedido = new Pedido();
			pedido.setCodProducto(Integer.parseInt(producto));
			pedido.setCantidad(Integer.parseInt(cantidad));
			pedido.setSolicitado(empleado.getCodEmpleado());
			servicioEmpleado.getDaoEmpleado().insertPedido(pedido);
			mensaje="Pedido solicitado";
		} catch (Exception e) {
			mensaje="Error al solicitar el pedido";
		}
		
		//TODO insertar pedido
//		accion="producto";
//		List <Producto> productos = servicioEmpleado.getDaoEmpleado().getProducto();
//		model.addAttribute("productos", productos);
//		model.addAttribute("empleado", empleado);
//		model.addAttribute("pantalla", accion);
		solicitarProducto(model);
		model.addAttribute("mesaje", mensaje);
		logger.info("fin solicitar pedido post");
		return "cajero";
	}
	@RequestMapping(value = "/producto", method = RequestMethod.GET)
	public String solicitarProducto( Model model) {
		logger.info("Inicio solicitar producto GET");
		if(loging) {
			accion="producto";
			List <Producto> productos = servicioEmpleado.getDaoEmpleado().getProductos();
			model.addAttribute("productos", productos);
			model.addAttribute("empleado", empleado);
			model.addAttribute("pantalla", accion);
			logger.info("Fin solicitar producto");
			return "cajero";
		}
		else {
			logger.info("Fin solicitar producto GET");
			return "home";
		}

	}
	@RequestMapping(value = "/salir", method = RequestMethod.GET)
	public String salir( Model model) {
		logger.info("Salir");
		loging=false;
		empleado=new Empleado();
		return "home";
	}
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginGet( Model model) {
		logger.info("Login");
		model.addAttribute("login", "" );
		if(loging) {
			model.addAttribute("empleado", empleado);
			return "cajero";
		}
		return "home";
	}
	
	@RequestMapping(value = "/recuperarProducto", method = RequestMethod.POST)
	@ResponseBody
	public Producto recuperarProducto( String id) {
		Producto producto = new Producto();
		if(null!=id) {
			producto.setCodProducto(Integer.parseInt(id));
			producto=servicioEmpleado.recuperarProducto(producto);
			
		}
		return producto;
	}
	
	@RequestMapping(value = "/anadirLineaProducto", method = RequestMethod.POST)
	@ResponseBody
	public List<LineaCompra> anadirLineaProducto(String id,String cant, Model model,HttpSession sesion) {
		boolean stock=true;
		EmpleadoSesion empleadoSesion = (EmpleadoSesion) sesion.getAttribute("empleadoSesion");
		List<LineaCompra> lineasPedidoActualizadas = new ArrayList<LineaCompra>();
		List<LineaCompra> lineasExistentes = new ArrayList<LineaCompra>();
		try {
			lineasPedidoActualizadas=empleadoSesion.getLineasCompra();			
			lineasExistentes=empleadoSesion.getLineasCompra();			
		}catch(Exception e) {
			System.out.println("Error al recuperar lineas");
		}
		LineaCompra lineaCompra = new LineaCompra();
		Producto producto = new Producto();

		//Se recorre todas las lineas de compra, se comprueba si existe o no
		boolean existe=false;
		int cont=0;
		int indice=0;
		for (int i=0;i<lineasExistentes.size();i++) {
			//Si está incluido en la lista de productos 
			if(lineasPedidoActualizadas.get(i).getCodProducto()==Integer.parseInt(id)) {
				existe=true;
				indice=cont;
				producto.setCodProducto(Integer.parseInt(id));
				producto=servicioEmpleado.recuperarProducto(producto);
				//se comprueba que se tenga stock
				if(producto.getStock()>=(lineasPedidoActualizadas.get(i).getCantidad()+Integer.parseInt(cant))) {
					lineasPedidoActualizadas.get(i).setCodProducto(lineasPedidoActualizadas.get(i).getCodProducto());
					lineasPedidoActualizadas.get(i).setNombreProducto(producto.getNombre());
					lineasPedidoActualizadas.get(i).setCantidad(lineasPedidoActualizadas.get(i).getCantidad()+Integer.parseInt(cant));
					lineasPedidoActualizadas.get(i).setPrecio(producto.getPrecio());
					lineasPedidoActualizadas.get(i).setTotal(lineasPedidoActualizadas.get(i).getCantidad()*lineasPedidoActualizadas.get(i).getPrecio());	
				}
				else {
					stock=false;
				}
				break;
			}
			cont++;
		}
		if(!existe) {
			//si no está incluido en la lista de productos
			producto.setCodProducto(Integer.parseInt(id));
			producto=servicioEmpleado.recuperarProducto(producto);
			//se comprueba que se tenga stock
			if(producto.getStock()>=Integer.parseInt(cant)) {
				lineaCompra.setCodProducto(producto.getCodProducto());
				lineaCompra.setNombreProducto(producto.getNombre());
				lineaCompra.setCantidad(Integer.parseInt(cant));
				lineaCompra.setPrecio(producto.getPrecio());
				lineaCompra.setTotal(lineaCompra.getCantidad()*lineaCompra.getPrecio());
				lineasPedidoActualizadas.add(lineaCompra);			
			} else {
				stock=false;
			}
			
		}
		if(stock) {
			model.addAttribute("lineas", lineasPedidoActualizadas);
			empleadoSesion.setLineasCompra(lineasPedidoActualizadas);
			sesion.setAttribute("empleadoSesion",empleadoSesion);
			return lineasPedidoActualizadas;
		} else {
			model.addAttribute("lineas", lineasExistentes);
			empleadoSesion.setLineasCompra(lineasExistentes);
			sesion.setAttribute("empleadoSesion",empleadoSesion);
			List<LineaCompra> lineavacia = new ArrayList<LineaCompra>();
			LineaCompra lin = new LineaCompra();
			lin.setCodCompra(0);
			lineavacia.add(lin);
			return lineavacia;
		}
		
	}
	@RequestMapping(value = "/actualizarLineaProducto", method = RequestMethod.POST)
	@ResponseBody
	public List<LineaCompra> actualizarLineaProducto( Model model,HttpSession sesion) {
		EmpleadoSesion empleadoSesion = (EmpleadoSesion) sesion.getAttribute("empleadoSesion");
		List<LineaCompra> lineasPedidoActualizadas = new ArrayList<LineaCompra>();
		lineasPedidoActualizadas=empleadoSesion.getLineasCompra();
		return lineasPedidoActualizadas;
	}
	
	@RequestMapping(value = "/vaciarLineaProducto", method = RequestMethod.POST)
	@ResponseBody
	public List<LineaCompra> vaciarLineaProducto( Model model,HttpSession sesion) {
		EmpleadoSesion empleadoSesion = (EmpleadoSesion) sesion.getAttribute("empleadoSesion");
		List<LineaCompra> lineasPedidoActualizadas = new ArrayList<LineaCompra>();
		model.addAttribute("lineas", lineasPedidoActualizadas);
		empleadoSesion.setLineasCompra(lineasPedidoActualizadas);
		sesion.setAttribute("empleadoSesion",empleadoSesion);
		
		return lineasPedidoActualizadas;
	}
	@RequestMapping(value = "/realizarCompra", method = RequestMethod.POST)
	@ResponseBody
	public String realizarCompra( Model model,HttpSession sesion,@RequestParam("cliente") String cliente,HttpServletResponse response) {
		
		EmpleadoSesion empleadoSesion = (EmpleadoSesion) sesion.getAttribute("empleadoSesion");
		List<LineaCompra> lineasExistentes = new ArrayList<LineaCompra>();
		lineasExistentes = empleadoSesion.getLineasCompra();;
		if ( null != lineasExistentes && !lineasExistentes.isEmpty()) {
			Cliente c = new Cliente();
			c.setCodCliente(Integer.parseInt(cliente));
			String resultado="mal";
			try {
				resultado = servicioEmpleado.buscarDNI(Integer.parseInt(cliente));
				
			} catch (Exception e) {
			}
			if(!resultado.equals("mal")) {
				empleadoSesion.setCliente(c);
				servicioEmpleado.realizarCompra(empleadoSesion);
				sesion.setAttribute("empleadoSesion",empleadoSesion);			
			}
			else {
				return "error";
			}
		} 
		return "OK";	
	}
	
	@RequestMapping(value = "/buscarDni", method = RequestMethod.POST)
	@ResponseBody
	public String buscarDNI( Model model,HttpSession sesion,@RequestParam("dni") String dni) {
		String resultado="mal";
		try {
			resultado = servicioEmpleado.buscarDNI(Integer.parseInt(dni));
			Cliente c = new Cliente();
			c.setDni(resultado);
			c = servicioEmpleado.getDaoEmpleado().buscarDNI(c);
			EmpleadoSesion empleadoSesion = (EmpleadoSesion) sesion.getAttribute("empleadoSesion");
			empleadoSesion.setCliente(c);
			sesion.setAttribute("empleadoSesion",empleadoSesion);
			
		} catch (Exception e) {
		}
		
		return resultado;
	}
	

	public ServicioEmpleado getServicioEmpleado() {
		return servicioEmpleado;
	}

	public void setServicioEmpleado(ServicioEmpleado servicioEmpleado) {
		this.servicioEmpleado = servicioEmpleado;
	}
	private void realizarPDF(HttpServletResponse response, Factura factura) {
		
		List<Factura> facturas= new ArrayList<Factura>();
		facturas.add(factura);
		
		//////////////////////////////////
		InputStream jasperReport = HomeController.class.getClassLoader().getResourceAsStream("/Jasper/Factura.jasper");
		JasperPrint jasperPrint;
		try {
			JRBeanCollectionDataSource data = new JRBeanCollectionDataSource(facturas);

			jasperPrint  = JasperFillManager.fillReport(jasperReport, null, data);
			jasperPrint.setProperty("net.sf.jasperreports.awt.ignore.missing.font",
					"true"); 
			JasperExportManager.exportReportToPdfFile(jasperPrint,"Factura.pdf");
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
			exporter.setParameter(JRExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "Factura.pdf");
			exporter.exportReport();
			JasperExportManager.exportReportToPdfFile(jasperPrint,"Factura.pdf");
//			org.apache.commons.io.IOUtils.write(baos.toByteArray(), response.getOutputStream());
	        response.setDateHeader("Expires", -1);
	        response.setContentType("application/pdf");
	        response.setContentLength(baos.toByteArray().length);
	        response.getOutputStream().write(baos.toByteArray());
//			response.flushBuffer();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/envio", method = RequestMethod.POST)
	public void pdf( Model model,HttpServletResponse response,HttpSession sesion) {
		logger.info("Inicio Realizar PDF");
		EmpleadoSesion empleadoSesion = (EmpleadoSesion) sesion.getAttribute("empleadoSesion");
		List<LineaCompra> lineas = new ArrayList<LineaCompra>();
		lineas=empleadoSesion.getLineasCompra();

		Factura factura = new Factura();
		factura.setLineas(lineas);
		factura.setCodCliente(empleadoSesion.getCliente().getDni());
		factura.setCodEmpleado(empleadoSesion.getEmpleado().getCodEmpleado()+"");
		factura.setFecha(new Date());
		factura.setNombreCliente(empleadoSesion.getCliente().getNombre());
		factura.setNombreEmpleado(empleadoSesion.getEmpleado().getNombre());
		Double total=0d;
		for(LineaCompra linea:empleadoSesion.getLineasCompra()) {
			total+=linea.getTotal();
		}
		factura.setTotal(total+"€");
		
		realizarPDF(response, factura);
		List<LineaCompra> lineasPedidoActualizadas = new ArrayList<LineaCompra>();
		model.addAttribute("lineas", lineasPedidoActualizadas);
		empleadoSesion.setLineasCompra(lineasPedidoActualizadas);
		sesion.setAttribute("empleadoSesion",empleadoSesion);
	}
	
}
