package oscar.gabella.sutil.controlador;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import oscar.gabella.sutil.dto.Empleado;
import oscar.gabella.sutil.dto.EmpleadoSesion;
import oscar.gabella.sutil.servicio.ServicioEmpleado;

@Controller
@SessionAttributes(types = { EmpleadoSesion.class })
public class AdministradorController {
	
	@Autowired
	private ServicioEmpleado servicioEmpleado;
	
	private boolean loging;
	
	private Empleado empleado;
	
	private String accion;
	
	@RequestMapping(value = "/aprobarPedido", method = RequestMethod.GET)
	public String aprobarPedidoGET( Model model,HttpSession sesion) {
		System.out.println("Inicio aprobar Pedido GET");
		EmpleadoSesion empleadoSesion = (EmpleadoSesion) sesion.getAttribute("empleadoSesion");
		if(null!=empleadoSesion) {
			empleado = empleadoSesion.getEmpleado();			
		}
		model.addAttribute("empleado", empleado);
		return"administradosJS";
	}

}
