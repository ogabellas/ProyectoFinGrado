<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Tienda Informática Óscar Gabella Sutil</title>
	 <link href="<c:url value="/resources/css/login.css" />" rel="stylesheet">
</head>
<body>
	<div id="todo">
		
		<form action="login" method="POST" id="login">
		<h1>LOGIN</h1>
			<label>USUARIO: </label><input type="text" name="usuario" value="">
			<label>CONTRASEÑA: </label><input type="password" name="contrasena" value="">
			<center><input id="boton" type="submit" value="Entrar"></center><br>
			<div id="mensaje">${login}</div>
		</form>
		
		
	</div>

</body>
</html>
