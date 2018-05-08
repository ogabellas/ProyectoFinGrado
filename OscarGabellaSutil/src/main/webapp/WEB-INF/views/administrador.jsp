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
Administrador

				</td>
			</tr>
			<tr>
				<td><c:out value="${mesaje}" /></td>
			</tr>
		</table>
	</div>

</body>
</html>
