/**
 *  Java script para controlar los eventos de la vista cajero
 */
$(function(){
	$(document).ready(function(){
		var idProd=$("#desplegableProductos").val();
		if(idProd!=='undefined'){
			
			$.ajax({
				url: 'http://localhost:8080/Tienda/recuperarProducto',
				dataType: 'json',
				data: { id: idProd },
				type: 'POST',
				success:function(data){
					
					$("#precioProducto").val(data.precio);
					$("#cantidadProducto").prop("max", data.stock);
				}
			});
		}

		
	});
	
	$("#desplegableProductos").change(function(){
		var idProd=$("#desplegableProductos").val();
        $.ajax({
            url: 'http://localhost:8080/Tienda/recuperarProducto',
            dataType: 'json',
            data: { id: idProd },
            type: 'POST',
			success:function(data){
				$("#precioProducto").val(data.precio);
				$("#cantidadProducto").prop("max", data.stock);
				$("#cantidadProducto").val(1);
				
			}
        });
	});
	
	
	$("#anadirLineaPedido").click(function(){
		var idProd=$("#desplegableProductos").val();
		var cantidad=$("#cantidadProducto").val();
		if(idProd.length==0 ||cantidad.length==0){
			alert("Rellene los campos");
		}
		
        $.ajax({
            url: 'http://localhost:8080/Tienda/anadirLineaProducto',
            dataType: 'json',
            data: { id: idProd, cant: cantidad},
            type: 'POST',
			success:function(reponse){
					if (reponse[0].codProducto!==0) {
					$("#listaLineas").html("");
					var listalineas=reponse;	
					var total = 0;
					for(var a in listalineas){
						$("#listaLineas").append(
								"<tr>" + "<td >"
								+ listalineas[a].codProducto
								+ "</td>" + "<td >"
								+ listalineas[a].nombreProducto
								+ "</td>" + "<td >"
								+ listalineas[a].cantidad
								+ "</td>" + "<td >"
								+ listalineas[a].precio
								+ "</td>" + "<td >"
								+ listalineas[a].total
								+ "</td>" + "</tr>");
						total = total + listalineas[a].total;
					}
					$("#listaLineas").append("<tr><td colspan='4'>TOTAL</td><td>"+total+"</td></tr>");
					$("#mensajeCompra").html("Producto añadido");
				} else {
					$("#mensajeCompra").html("Sin stock. Producto no añadido");
				}
			}
        });
	});

	$("#actualizarLineaPedido").click(function(){
		$.ajax({
			url: 'http://localhost:8080/Tienda/actualizarLineaProducto',
			dataType: 'json',
			type: 'POST',
			success:function(data){
				$("#listaLineas").html("");
				var total = 0;
				var listalineas=data;		
				for(var a in listalineas){
					$("#listaLineas").append(
							"<tr>" + "<td >"
							+ listalineas[a].codProducto
							+ "</td>" + "<td >"
							+ listalineas[a].nombreProducto
							+ "</td>" + "<td >"
							+ listalineas[a].cantidad
							+ "</td>" + "<td >"
							+ listalineas[a].precio
							+ "</td>" + "<td >"
							+ listalineas[a].total
							+ "</td>" + "</tr>");
					total = total + listalineas[a].total;
				}
				$("#listaLineas").append("<tr><td colspan='4'>TOTAL</td><td>"+total+"</td></tr>");
				$("#mensajeCompra").html("");
			}
		});
	});
	$("#vaciarLineaPedido").click(function(){
		$.ajax({
			url: 'http://localhost:8080/Tienda/vaciarLineaProducto',
			dataType: 'json',
			type: 'POST',
			success:function(){
				$("#listaLineas").html("");
				$("#mensajeCompra").html("");
			}
		});
	});
	$("#realizarCompra").click(function(){
		var client=dni.value;
		//se comprueba que este informado el dni
		if(client.length!=0){
			$.ajax({
				url: 'http://localhost:8080/Tienda/realizarCompra',
				data: {  cliente: client},
				dataType: 'json',
				type: 'POST',
				success:function(data){
						$("#listaLineas").html("");
						$("#mensajeCompra").html("Pedido realizado correctamente");
						alert("Pedido realizado correctamente");				
				},
				error : function(data) {
					var respuesta = data.responseText;
					if(respuesta !=="error"){
						$("#listaLineas").html("");
						$("#mensajeCompra").html("Pedido realizaco correctamente");
						dni.value="";
						$("#mensajeDNI").html("Introduzca DNI del cliente");
						alert("Pedido realizado correctamente");						
					} else {
						alert("Dni no válido");
					}
				}
			});		
		} else {
			alert("Introduzca el dni del cliente")
		}
	});
	
	
	$("#buscarCliente").click(function(){
		$.ajax({
			url: 'http://localhost:8080/Tienda/buscarDni',
            data: {  dni: dni.value},
			dataType: 'json',
			type: 'POST',
			success:function(response){
				 var marker = JSON.stringify(response);
				if(marker==='mal'){
					alert("mal");
				} else {
					$("#mensajeDNI").html("DNI encontrado : "+response);
				}
			},
			error : function() {
				$("#mensajeDNI").html("DNI no encontrado");
			}
		});
	});

	$("#dni").keypress(function(event){
		var texto = dni.value;
		var longitud = dni.value.length;
		if(longitud>7){
			event.preventDefault();		
		}
		if(isNaN(texto)){
			event.preventDefault();
		}
	});
	$("#devolucionLineasPedido").click(function(event){
		var salida="";
		var lineas = [];
		var codigo = [];
		var cantidad = [];
		var concatenado="";
		lineas =  document.getElementsByClassName("lineasDevolverClase");
		$.each(lineas, function( index, value ) {
			salida = salida+"\nData:"+$(value).data("producto")+"-Cantidad:"+$(value).val();
			codigo.push($(value).data("producto"));
			cantidad.push($(value).val());
			concatenado=concatenado+$(value).data("producto")+":"+$(value).val()+";";
			});
		if(concatenado ===""){
			alert("Introduzca un pedido");
		}else{
			var enviar =concatenado.substr(0, concatenado.length-1);
			$.ajax({
				url: 'http://localhost:8080/Tienda/pedidoDevolver',
				data: { 'valores' : enviar},
				dataType: 'json',
				type: 'POST',
				success:function(response){
					var texto = document.getElementById("pedidosMostrar");
					texto.innerHTML="";
					alert("Pedido devuelto");
				},
				error:function(response){
					var texto = document.getElementById("pedidosMostrar");
					texto.innerHTML="";
					alert("Pedido devuelto");
				}
			});	
		}

	});
	

	
	
	
	
	
	
	
	
	
});