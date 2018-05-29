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
							<li id="enlaceVenta"><a href="venta">Venta de productos</a></li>
							<li><a href="devolucion">Devolucion de productos</a></li>
							<li><a href="alta">Alta clientes</a></li>
							<li><a href="producto">Solicitar producto</a></li>
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
<!-------------     si es venta    --------------->
					<c:if test="${pantalla=='venta'}">
						<h1>Venta de productos</h1>
						<form  action="envio" method="post" id="formularCompra">
							<fieldset id="formulario">
								<legend> Producto a comprar</legend>
								<div class="inputs">
									<label>Producto: </label> <select name="productoVenta"
										id="desplegableProductos">
										<c:forEach items="${productos }" var="producto">
											<option name="op"
												value="<c:out value="${producto.getCodProducto()}"></c:out>"><c:out
													value="${producto.getNombre()}"></c:out></option>
										</c:forEach>

									</select>
								</div>

								<div class="inputs">
									<label>Precio: </label><input type="text" id="precioProducto"
										name="precio" size="25" disabled><br>
								</div>
								<div class="inputs">
									<label>Cantidad: </label><input type="number"
										id="cantidadProducto" min="0" max="1" name="cantidad"
										size="25" autofocus required><br>
								</div>
								<table>
									<tr>
											<td class="boton" id="anadirLineaPedido">Añadir Producto</td>
											<td class="boton" id="actualizarLineaPedido">Actualizar</td>
									</tr>
									<tr>
											<td class="boton" id="realizarCompra">Finalizar Compra</td>
											<td class="boton" id="vaciarLineaPedido">Vaciar Lineas</td>
									</tr>
									<tr id="ocultar">
											<td colspan="2"><input type="submit" value="Enviar"></></td>
									</tr>
								</table>
							</fieldset>
						</form >
						<div id="#formularCliente">
							<form action="" method="post" class="imagenesInternas"">
								<fieldset>
								<legend> Cliente</legend>
									<label>DNI: </label><input type="number" id="dni" name="dni"
										maxlength="8" placeholder="DNI sin letra" max="99999999" required><br><br>
									<center>
										<button type="button"  id="buscarCliente">Buscar
											Cliente</button>
									</center>
									<p id="mensajeDNI">Introduzca DNI del cliente</p>
								</fieldset>
							</form>
							<br> 
						</div>
						
						<table class="tabla" id="listaProd">
							<thead>
								<th>Codigo</th>
								<th>Producto</th>
								<th>Cantidad</th>
								<th>Precio</th>
								<th>Total</th>
							</thead>
							<tbody id="listaLineas">
								<c:forEach items="${lineas }" var="linea">
									<tr>
										<td><c:out value="${linea.codProducto}"></c:out></td>
									<tr>
										<td><c:out value="${linea.nombreProducto}"></c:out></td>
									<tr>
										<td><c:out value="${linea.cantidad}"></c:out></td>
									<tr>
										<td><c:out value="${linea.precio}"></c:out></td>
									<tr>
										<td><c:out value="${linea.total}"></c:out></td>
									</tr>
								</c:forEach>
							</tbody>
						</table><br>
						<div id="mensajeCompra"></div>
					</c:if>
<!-------------     si es devolucion    --------------->
					<c:if test="${pantalla=='devolucion'}">
						<h1>Devolución de productos</h1>
						<form action="devolucion" method="post" id="formular">
							<fieldset id="formulario">
							<legend> Venta a buscar</legend>
								<div class="inputs">
									<label>VENTA: </label><input type="number" name="pedido" size="25"
										autofocus required><br>
								</div>
							<center><button name="submit" value="submit" type="submit"  id="enviarValor">Buscar Producto</button></center><br>							
							</fieldset>
						</form>
						<div id="imagenAlta">
							<img id="imagen" alt="logo"	src="<c:url value="/resources/imagenes/devolucion.png" />" width="250px" height="170px">
						</div>
						<form action="pedidoDevolver" method="post" id="formulari">
							<fieldset id="formulario">
							<legend> Productos a devolver</legend>
							<div id="pedidosMostrar">
								<c:forEach items="${lineasDevolver}" var="linea">
								<div class="inputs">
									<label><c:out value="${linea.nombreProducto}"></c:out>: </label><input class="lineasDevolverClase" type="number" data-producto="<c:out value="${linea.codLinea}"></c:out>" name="pedido" size="25" value="<c:out value="${linea.cantidad}"></c:out>" min="0" max="<c:out value="${linea.cantidad}"></c:out>"><br>
								</div>
								</c:forEach>
							</div>
							<center><button id="devolucionLineasPedido" name="reset" value="reset" type="reset">Devolver producto</button></center>
							</fieldset>
						</form>
					</c:if>
<!-------------     si es alta de clientes    --------------->
					<c:if test="${pantalla=='alta'}">
						<h1>Alta de clientes</h1>
						<form action="alta" method="post" id="formular">
							<fieldset id="formulario">
								<legend> Cliente a registrar </legend>
								<div class="inputs">
									<label>DNI: </label><input id="dni" type="number" pattern="[0-9]{8}"name="dni" maxlength="8" placeholder="DNI sin letra" max="99999999"
										autofocus required><br>
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
							<button name="submit" value="submit" type="submit">Alta	Cliente</button>
							<button name="reset" value="reset" type="reset">Borrar</button>
						</form>
						<div id="imagenAlta">
							<img id="imagen" alt="logo"	src="<c:url value="/resources/imagenes/altaCliente.png" />" width="250px">
						</div>
					</c:if>
<!-------------     si es solicitar productos    --------------->
					<c:if test="${pantalla=='producto'}">
						<h1>Solicitar productos</h1>
						<form action="producto" method="post" id="formular">
							<fieldset id="formulario">
								<legend> Producto a pedir</legend>
								<div class="inputs">
									<label>Producto: </label> 
									<select name="producto" id="desplegable">
										<c:forEach items="${productos }" var="producto">
											<option name="op" value="<c:out value="${producto.getCodProducto()}"></c:out>"><c:out
													value="${producto.getNombre()}"></c:out></option>
										</c:forEach>

									</select>
								</div>

								<div class="inputs">
									<label>Cantidad: </label><input type="text"
										pattern="[0-9]{1,5}" name="cantidad" size="25" autofocus
										required><br>
								</div>
							</fieldset>
							<button name="submit" value="submit" type="submit">Solicitar producto</button>
						</form>
						<div id="imagenProducto" class="imagenes">
							<img id="imagenP" alt="logo" class="imagenesInternas"	src="<c:url value="/resources/imagenes/solicitarProd.png" />">
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
