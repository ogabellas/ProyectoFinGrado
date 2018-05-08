package oscar.gabella.sutil.controlador;

import java.util.ArrayList;
import java.util.List;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import oscar.gabella.sutil.servicio.ServicioEmpleado;

@Controller
@SessionAttributes(types = { EmpleadoSesion.class })
public class GerenteController {
	
	@Autowired
	private ServicioEmpleado servicioEmpleado;
	
	private boolean loging;
	
	private Empleado empleado;
	
	private String accion;
	
	@RequestMapping(value = "/aprobarPedido", method = RequestMethod.GET)
	public String aprobarPedidoGET( Model model) {
		System.out.println("Inicio aprobar Pedido GET");
		
		return"gerente";
	}

}
