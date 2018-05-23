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
							<li id="enlaceVenta"><a href="aprobarPedido">Aprobar pedido</a></li>
							<li><a href="recibirPedido">Recibir pedido</a></li>
							<li><a href="productoAlta">Producto nuevo</a></li>
							<li><a href="provedorAlta">Provedor nuevo</a></li>
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
<!-------------     Si es alta producto    --------------->
					<c:if
						test="${pantalla=='producto'}">
						<h1>Alta de producto</h1>
						<form action="altaProducto" method="post" id="formular">
							<fieldset id="formulario">
								<legend> Producto a dar de alta </legend>
								<div class="inputs">
									<label>Nombre: </label><input id="nombre" type="text"
										 name="nombre" maxlength="30" autofocus required><br>
								</div>
								<div class="inputs">
									<label>Precio: </label><input type="number" name="precio"
										size="25" required><br>
								</div>
								<div class="inputs">
									<label>Proveedor: </label> 
									<select name="proveedor" id="desplegable">
										<c:forEach items="${proveedores }" var="proveedor">
											<option name="op" value="<c:out value="${proveedor.getCodProvedor()}"></c:out>"><c:out
													value="${proveedor.getNombre()}"></c:out></option>
										</c:forEach>

									</select>
								</div>
							</fieldset>
							<button name="submit" value="submit" type="submit">Alta
								Producto</button>
							<button name="reset" value="reset" type="reset">Borrar</button>
						</form>
						<div id="imagenAlta">
							<img id="imagen" alt="logo"
								src="<c:url value="/resources/imagenes/altaCliente.png" />"
								width="250px">
						</div>
					</c:if>
<!-------------     Si es alta proveedor    --------------->
					<c:if
						test="${pantalla=='proveedor'}">
						<h1>Alta de proveedor</h1>
						<form action="altaProveedor" method="post" id="formular">
							<fieldset id="formulario">
								<legend> Proveedor a dar de alta </legend>
								<div class="inputs">
									<label>CIF: </label><input id="cif" type="text" name="cif" maxlength="12" autofocus required><br>
								</div>
								<div class="inputs">
									<label>Nombre: </label><input type="text" name="nombre"
										size="25" required><br>
								</div>
								<div class="inputs">
									<label>Correo: </label><input type="email" name="correo"
										size="25"><br>
								</div>
								<div class="inputs">
									<label>Dirección: </label><input type="text" name="direccion"
										size="25"><br>
								</div>
								<div class="inputs">
									<label>Teléfono: </label><input type="text" pattern="[0-9]{9}" name="telefono"
										size="25" ><br>
								</div>
							</fieldset>
							<button name="submit" value="submit" type="submit">Alta
								Proveedor</button>
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
