package oscar.gabella.sutil.controlador;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

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

import oscar.gabella.sutil.dto.Empleado;
import oscar.gabella.sutil.dto.EmpleadoSesion;
import oscar.gabella.sutil.dto.Producto;
import oscar.gabella.sutil.dto.ProductoEstrella;
import oscar.gabella.sutil.dto.Proveedor;
import oscar.gabella.sutil.servicio.ServicioEmpleado;

@Controller
@SessionAttributes(types = { EmpleadoSesion.class })
public class GerenteController {
	
	private static final Logger logger = LoggerFactory.getLogger(GerenteController.class);
	
	@Autowired
	private ServicioEmpleado servicioEmpleado;
	
	private boolean loging;
	
	private Empleado empleado;
	
	private String accion;
	
	@RequestMapping(value = "/empleadoAlta", method = RequestMethod.GET)
	public String empleadoAltaGET( Model model,HttpSession sesion) {
		logger.info("Inicio alta empleado GET");
		EmpleadoSesion empleadoSesion = (EmpleadoSesion) sesion.getAttribute("empleadoSesion");
		if(null!=empleadoSesion) {
			empleado = empleadoSesion.getEmpleado();			
		} else {
			return"home";
		}
		model.addAttribute("empleado", empleado);
		model.addAttribute("mesaje", "");
		model.addAttribute("pantalla", "empleadoAlta");
		return"gerente";
	}
	
	@RequestMapping(value = "/empleadoBaja", method = RequestMethod.GET)
	public String empleadoBajaGET( Model model,HttpSession sesion) {
		logger.info("Inicio baja empleado GET");
		EmpleadoSesion empleadoSesion = (EmpleadoSesion) sesion.getAttribute("empleadoSesion");
		if(null!=empleadoSesion) {
			empleado = empleadoSesion.getEmpleado();			
		} else {
			return"home";
		}
		model.addAttribute("empleadosExistentes",servicioEmpleado.getDaoEmpleado().getEmpleados());
		model.addAttribute("empleado", empleado);
		model.addAttribute("mesaje", "");
		model.addAttribute("pantalla", "empleadoBaja");
		return"gerente";
	}
	
	@RequestMapping(value = "/balance", method = RequestMethod.GET)
	public String balanceMensualGET( Model model,HttpSession sesion) {
		logger.info("Inicio balance Mensual GET");
		EmpleadoSesion empleadoSesion = (EmpleadoSesion) sesion.getAttribute("empleadoSesion");
		if(null!=empleadoSesion) {
			empleado = empleadoSesion.getEmpleado();			
		} else {
			return"home";
		}
		Calendar calendario=new GregorianCalendar();
		calendario.getInstance();
		Double ingresosUltimoMes = servicioEmpleado.getDaoEmpleado().balance(calendario.get(Calendar.YEAR)+"-"+(calendario.get(Calendar.MONTH))+"-"+calendario.get(Calendar.DAY_OF_MONTH));
		Double ingresosMesActual = servicioEmpleado.getDaoEmpleado().balance(calendario.get(Calendar.YEAR)+"-"+(calendario.get(Calendar.MONTH)+1)+"-01");
		model.addAttribute("empleadosExistentes",servicioEmpleado.getDaoEmpleado().getEmpleados());
		model.addAttribute("empleado", empleado);
		model.addAttribute("mesaje", "");
		model.addAttribute("balance", "Ingresos �ltimo mes(desde " + calendario.get(Calendar.DAY_OF_MONTH)
				+ " del mes pasado): " + ingresosUltimoMes + "�");
		model.addAttribute("balanceMes", "Ingresos de lo que va de mes actual: "+ingresosMesActual+"�");
		model.addAttribute("pantalla", "balance");
		return"gerente";
	}
	
	@RequestMapping(value = "/productoEstrella", method = RequestMethod.GET)
	public String productoEstrellaGET( Model model,HttpSession sesion) {
		logger.info("Inicio producto estrella GET");
		EmpleadoSesion empleadoSesion = (EmpleadoSesion) sesion.getAttribute("empleadoSesion");
		if(null!=empleadoSesion) {
			empleado = empleadoSesion.getEmpleado();			
		} else {
			return"home";
		}
		List<ProductoEstrella> productosEstrella = new ArrayList<ProductoEstrella>();
		productosEstrella = servicioEmpleado.getDaoEmpleado().productosEstrellas();
		for(ProductoEstrella prod:productosEstrella) {
			Producto p = new Producto();
			p.setCodProducto(prod.getCodProducto());
			p=servicioEmpleado.recuperarProducto(p);
			prod.setNomProducto(p.getNombre());
		}
		model.addAttribute("empleadosExistentes",servicioEmpleado.getDaoEmpleado().getEmpleados());
		model.addAttribute("empleado", empleado);
		model.addAttribute("productoEstrella", productosEstrella);
		model.addAttribute("mesaje", "");
		model.addAttribute("pantalla", "estrella");
		return"gerente";
	}
	
	@RequestMapping(value = "/altaEmpleado", method = RequestMethod.POST)
	public String altaEmpleado(Model model, HttpSession sesion, @RequestParam("dni") String dni,@RequestParam("nombre") String nombre,
			@RequestParam("nomina") String nomina, @RequestParam("pass") String pass, @RequestParam("cargo") String cargo) {
		String resultado = "gerente";
		logger.info("alta de empleado POST");
		EmpleadoSesion empleadoSesion = (EmpleadoSesion) sesion.getAttribute("empleadoSesion");
		if(null!=empleadoSesion) {
			empleado = empleadoSesion.getEmpleado();			
		} else {
			return"home";
		}
		model.addAttribute("empleado", empleado);
		Empleado empleado = new Empleado();
		empleado.setCargo(Integer.parseInt(cargo));
		empleado.setContrasena(pass);
		empleado.setDni(dni);
		empleado.setNombre(nombre);
		empleado.setNomina(Double.parseDouble(nomina));
		Boolean existe = servicioEmpleado.empleadoExiste(empleado);
		if(existe) {
			model.addAttribute("mesaje", "El dni de empleado ya existe");
		}else {
			servicioEmpleado.insertarEmpleado(empleado);
			model.addAttribute("mesaje", "Empleado dado de alta correctamente");
		}

		model.addAttribute("pantalla", "empleadoAlta");
		return resultado;
	}
	
	@RequestMapping(value = "/bajaEmpleado", method = RequestMethod.POST)
	public String bajaEmpleado(Model model, HttpSession sesion, @RequestParam("empleadoBaja") String dni) {
		String resultado = "gerente";
		logger.info("baja de empleado POST");
		EmpleadoSesion empleadoSesion = (EmpleadoSesion) sesion.getAttribute("empleadoSesion");
		if(null!=empleadoSesion) {
			empleado = empleadoSesion.getEmpleado();			
		} else {
			return"home";
		}
		model.addAttribute("empleado", empleado);
		Empleado empleado = new Empleado();
		empleado.setDni(dni);
		Boolean existe = servicioEmpleado.empleadoExiste(empleado);
		if(existe) {
			servicioEmpleado.getDaoEmpleado().eliminarEmpleado(empleado);
			model.addAttribute("empleadosExistentes",servicioEmpleado.getDaoEmpleado().getEmpleados());
			model.addAttribute("mesaje", "Empleado dado de baja correctamente");
			
		}else {
			model.addAttribute("mesaje", "Error dar de baja empleado");
		}
		
		model.addAttribute("pantalla", "empleadoBaja");
		return resultado;
	}

}
