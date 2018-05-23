<%@ page language="java" contentType="text/html; UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Tienda Informática Oscar Gabella Sutil</title>
	<meta charset="UTF-8"/>
	<link href="<c:url value="/resources/css/cajero.css" />" rel="stylesheet">
	<script src="http://code.jquery.com/jquery-3.2.1.min.js"
		integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
		crossorigin="anonymous"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/cajero.js" />"></script>
</head>
<body>
	<div id="todo">
		<table>
			<tr>
				<td id="superior">
					<div id="menu">
						<nav>
						<ul>
							<img id="imagen" alt="logo"
								src="<c:url value="/resources/imagenes/logo.png" />"
								width="100px">
							<li id="enlaceVenta"><a href="empleadoAlta">Alta empleado</a></li>
							<li><a href="empleadoBaja">Baja empleado</a></li>
							<li><a href="balance">Balance mensual</a></li>
							<li><a href="productoEstrella">Productos estrella</a></li>
							<li><a href="salir">Salir</a></li>
						</ul>
						</nav>
					</div>
					<div id="usuario">
						<c:out value="${empleado.getNombre()}" />
					</div>
				</td>
			</tr>
			<tr>
				<td id="cuerpo">
<!-------------     cuerpo de la pagina    --------------->
<!-------------     si es alta de clientes    ---------------> 
					<c:if
						test="${pantalla=='empleadoAlta'}">
						<h1>Alta de empleados</h1>
						<form action="altaEmpleado" method="post" id="formular">
							<fieldset id="formulario">
								<legend> Empleado a registrar </legend>
								<div class="inputs">
									<label>DNI: </label><input id="dni" type="number"
										pattern="[0-9]{8}" name="dni" maxlength="8"
										placeholder="DNI sin letra" max="99999999" autofocus required><br>
								</div>
								<div class="inputs">
									<label>Nombre: </label><input type="text" name="nombre"
										size="25" required><br>
								</div>
								<div class="inputs">
									<label>nomina: </label><input type="number" pattern="[0-9]{4}"
										name="nomina" size="25"><br>
								</div>
								<div class="inputs">
									<label>Contraseña: </label><input type="password" name="pass"
										size="25" required><br>
								</div>
								<div class="inputs">
									<label>Cargo: </label> 
									<select name="cargo" id="desplegable">
											<option name="op" value="0">Cajero</option>
											<option name="op" value="1">Administrador</option>
											<option name="op" value="2">Gerente</option>
									</select>
								</div>
							</fieldset>
							<button name="submit" value="submit" type="submit">Alta
								Empleado</button>
							<button name="reset" value="reset" type="reset">Borrar</button>
						</form>
						<div id="imagenAlta">
							<img id="imagen" alt="logo"
								src="<c:url value="/resources/imagenes/altaCliente.png" />"
								width="250px">
						</div>
					</c:if>
<!-------------     si es baja de clientes    ---------------> 
					<c:if
						test="${pantalla=='empleadoBaja'}">
						<h1>Baja de empleados</h1>
						<form action="bajaEmpleado" method="post" id="formular">
							<fieldset id="formulario">
								<legend> Empleado a despedir </legend>
								<div class="inputs">
									<label>Producto: </label> 
									<select name="empleadoBaja" id="desplegable">
										<c:forEach items="${empleadosExistentes }" var="emple">
											<option name="op" value="<c:out value="${emple.getDni()}"></c:out>"><c:out
													value="${emple.getNombre()}"></c:out></option>
										</c:forEach>

									</select>
								</div>
							</fieldset>
							<button name="submit" value="submit" type="submit">Baja
								Empleado</button>
							<button name="reset" value="reset" type="reset">Borrar</button>
						</form>
						<div id="imagenAlta">
							<img id="imagen" alt="logo"
								src="<c:url value="/resources/imagenes/altaCliente.png" />"
								width="250px">
						</div>
					</c:if>

				</td>
			</tr>
			<tr>
				<td><c:out value="${mesaje}" /></td>
			</tr>
		</table>
	</div>

</body>
</html>
