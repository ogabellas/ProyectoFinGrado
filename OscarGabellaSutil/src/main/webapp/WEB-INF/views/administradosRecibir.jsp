<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8" />
<title>Tienda Informática Oscar Gabella Sutil</title>
<link
	href="<c:url value="/resources/js/ext-js/resources/css/ext-all.css"/>"
	rel="stylesheet">
<script type="text/javascript"
	src="<c:url value="/resources/js/ext-js/adapter/ext/ext-base.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/ext-js/ext-all-debug.js" />"></script>
<link href="<c:url value="/resources/css/administrador.css" />"
	rel="stylesheet">
<script src="http://code.jquery.com/jquery-3.2.1.min.js"
	integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
	crossorigin="anonymous"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/cajero.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/recibirPedido.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/ext-js/ux-all.js" />"></script>

</head>
<body>
	<div id="todo">
	<br>
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
				<td><br><div id="componentes"></div></td>
			</tr>
		</table>
		
	</div>


</body>
</html>