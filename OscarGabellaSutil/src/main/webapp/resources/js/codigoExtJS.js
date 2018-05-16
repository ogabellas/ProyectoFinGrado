/**
 * 
 */
var arrayPedidos = [];
Ext.onReady(function() {

	arrayPedidos.push({
		'Id' : "1",
		'codPedido' : "1",
		'codProducto' : "1",
		'cantidad' : "5"
	});
	agregar();
	var panelCriterios = new Ext.FormPanel({
		id : 'formPanelCriteriosDocumentos',
		//		title			: "Prueba",
		method : 'POST',
		width : 752,
		//		autoWidth		: true,
		style : 'margin: 3 auto;',
		frame : true,
//		cls				: 'insa-tablelayout',
		collapsible : false,
		collapsed : false,
		layout : 'table',
		layoutConfig : {
			columns : 1
		},
		items : [ cargaListadoPedidos(), botones() ]
	});
	panelCriterios.render('componentes');
	handlerBuscar();
});

/**
 * Construye el panel del listado pedidos
 */
function cargaListadoPedidos() {

	var columnas = columnasPedidos();
	var maxRegistrosPagina = 10;
	// Creamos un store 'local' vacio
	var store = arrayStorePedidos();
	var columnModel = new Ext.grid.ColumnModel({
		defaults : {
			sortable : true,
			align : 'left'
		},
		columns : columnas
	});

	//	var dataArrayPaginacion = [
	//	    {	'valor': 10 ,  	'texto': 10},
	//	    {	'valor': 20 ,  	'texto': 20},
	//	    {	'valor': 50 ,  	'texto': 50}
	//	];

	var pagingBar = new Ext.PagingToolbar({
		id : 'paginBarResultados',
		name : 'paginBarResultados',
		pageSize : maxRegistrosPagina,
		store : arrayStorePedidos(),
		displayInfo : true,
		prependButtons : true,
		// Recargamos el grid por completo
		doRefresh : function() {
			if (this.store.data.items.length > 0) {
				handlerBuscar();
			}
		},
		buttons : [ "Pedidos máximo 10",
		{
			iconCls : 'icoAdd',
			id : 'btn_nuevo',
			tooltip : "Añadir",
			text : "Añadir",
			handler : function() {
				//                	handlerAnnadirComentario("nuevoComentario")
				alert("nuevo");
				handlerBuscar();
			}
		}, {
			iconCls : 'icoEdit',
			id : 'btn_versionar',
			tooltip : "Editar",
			text : "Editar",
			handler : function() {
				//  	        		handlerModificarComentario()
				alert("Editar");
			}
		}, {
			iconCls : 'icoRemove',
			id : 'btn_eliminar',
			tooltip : "Eliminar",
			text : "Eliminar",
			handler : function() {
				//	        		handlerModificarComentario()
				alert("Eliminar");
			}
		}, '->' ]
	});

	var grid = new Ext.ux.grid.InsaGridPanel({
		id : 'gridPedidos',
		name : 'gridPedidos',
		cls : 'grid',
		frame : true,
		collapsible : false,
		height : 275,
		viewConfig : {
			emptyText : "Sin pedidos",
			deferEmptyText : false,
			forceFit : true
		},
		store : arrayStorePedidos(),
		selModel : columnas[0],
		colModel : columnModel,
		bbar : pagingBar,
		remoteSort : true
	});
	// Workaround para que la barra de paginaci�n cambie de tama�o con el grid
	var panel = new Ext.Panel({
		layout : 'fit',
		border : false,
		monitorResize : true,
		width : 730,
		items : grid
	});
	return panel;
}

/**
 * M�todo que inserta las columnas fijas del listado de pedidos
 */

function columnasPedidos() {

	var items = [];
	var sm = new Ext.grid.CheckboxSelectionModel(
			{
				checkOnly : false,
				singleSelect : false,
				sortable : false,
				header : '<div class="x-grid3-hd-checker" id="headerAccesos" >&#160;</div>',
				grid : 'gridPedidos',
				//		header			: '<div class="x-grid3-hd-checker">&#160;</div>',
				dataIndex : 'Id',
				renderPropio : false
			});
	items.push(sm);

	columna = new Ext.grid.Column({
		header : "Pedido",
		dataIndex : 'codPedido',
		hideable : false,
		renderPropio : false
	});
	items.push(columna);
	columna = new Ext.grid.Column({
		header : "Producto",
		dataIndex : 'codProducto',
		hideable : false,
		renderPropio : false
	});
	items.push(columna);
	columna = new Ext.grid.Column({
		header : "Cantidad",
		dataIndex : 'cantidad',
		hideable : false,
		renderPropio : false
	});
	items.push(columna);

	return items;
}
/*
 * Funcion que devuelve el array con los datos
 */
function arrayStorePedidos() {
	var arrayPedidos2 = [];
	arrayPedidos2.push({
		'Id' : "1",
		'codPedido' : "1",
		'codProducto' : "1",
		'cantidad' : "5"
	});
	//	arrayPedidos2.push ({'codPedido': "2" , 'codProducto':"1", 'cantidad': "5"});
	//	arrayPedidos2.push ({'codPedido': "3" , 'codProducto':"1", 'cantidad': "5"});
	//	arrayPedidos2.push ({'codPedido': "4" , 'codProducto':"1", 'cantidad': "5"});
	//	arrayPedidos2.push ({'codPedido': "5" , 'codProducto':"1", 'cantidad': "5"});
	//	arrayPedidos2.push ({'codPedido': "6" , 'codProducto':"1", 'cantidad': "5"});
	//	arrayPedidos2.push ({'codPedido': "7" , 'codProducto':"1", 'cantidad': "5"});
	//	arrayPedidos2.push ({'codPedido': "8" , 'codProducto':"1", 'cantidad': "5"});
	//	arrayPedidos2.push ({'codPedido': "9" , 'codProducto':"1", 'cantidad': "5"});
	//	arrayPedidos2.push ({'codPedido': "10" , 'codProducto':"1", 'cantidad': "5"});
	//	arrayPedidos2.push ({'codPedido': "12" , 'codProducto':"1", 'cantidad': "5"});
	//	arrayPedidos2.push ({'codPedido': "13" , 'codProducto':"1", 'cantidad': "5"});
	//	arrayPedidos2.push ({'codPedido': "11" , 'codProducto':"1", 'cantidad': "5"});
	//	arrayPedidos2.push ({'codPedido': "22" , 'codProducto':"1", 'cantidad': "5"});
	//	arrayPedidos2.push ({'codPedido': "23" , 'codProducto':"1", 'cantidad': "5"});
	var storePropio = new Ext.data.JsonStore({
		id : 'storePedidos',
		idProperty : 'codPedido',
		//    	data: arrayPedidos2,
		proxy : new Ext.ux.data.PagingMemoryProxy(arrayPedidos),
		sortInfo : {
			field : 'codPedido',
			direction : 'ASC'
		},
		remoteSort : true, //ORDENACI�N
		fields : [ 'Id', 'codPedido', 'codProducto', 'cantidad' ]
	});

	return storePropio;
}

function agregar() {
	/**
	 * Extensión del componeneto GridPanel de Ext-js para adaptarlo y reutilizar comportamientos
	 * comunes en el proyecto
	 */
	Ext.ns('Ext.ux.grid');
	Ext.ux.grid.InsaGridPanel = Ext
			.extend(
					Ext.grid.GridPanel,
					{
						// RETORNA LOS NUMERO DE INDEX DE CADA FILA
						obtenerIdsSeleccionados : function() {
							var seleccionMultiple = this.selModel;
							var selectedKeys = [];
							seleccionMultiple.each(function(rec) {
								selectedKeys.push(rec.id);
							});
							return selectedKeys;
						},
						listeners : {
							//Añadimos Tooltips con el contenido de la celda para poder leer la información completa
							afterrender : function() {
								Ext.QuickTips.init();

								for (var i = 0; i < this.getColumnModel()
										.getColumnCount(); i++) {
									try {
										if (!this.getColumnModel()
												.getColumnById(i).renderPropio) {
											this
													.getColumnModel()
													.setRenderer(
															i,
															function(value,
																	metadata,
																	record,
																	rowIndex,
																	colIndex,
																	store) {
																var ret = value;
																ret = (ret != null && ret != undefined) ? ret
																		: '';
																metadata.attr = 'ext:qtip="'
																		+ Ext.util.Format
																				.stripTags(ret)
																		+ '"';
																if (ret === '') {
																	ret = '-';
																}
																return ret;
															});
										}
									} catch (e) {
									}
								}
							}
						}
					});
}

/**
 * M�todo que recarga el grid de cpmentarios propios usuario
 */
function handlerBuscar() {

	/*	Ext.Ajax.request({
	 url		: String.format('{0}/workflowCommentConsolidator/buscarComentariosUsuario.ajax?csrf={1}', context, $csrf),
	 params	: {usuario	: user},
	 method	: 'POST',
	 timeout : 220000,
	 success	: function(response){
	 arrayComentarios = [];
	 var comentarios= Ext.util.JSON.decode(response.responseText);
	 Ext.each(comentarios, function(comentario) {
	 var idActual=comentario.comentarioId;
	 var seccionActual=comentario.seccion;
	 var paginaActual=comentario.pagina;
	 var comentarioActual=comentario.comentario;
	 var usuario=comentario.usuario;
	 var documentoActual=enlaceDocumento(comentario);
	 var comentarioRechazoActual=comentario.comentarioRechazo;
	 var documento = comentario.documento;
	 arrayComentarios.push ({'Id': idActual , 'usuario':usuario, 'seccion': seccionActual , 'pagina': paginaActual , 'comentario': comentarioActual , 'documento': documentoActual, 'comentarioReject': comentarioRechazoActual, 'documentoDto':documento });
	 });
	 var grid = Ext.getCmp('gridComentarios');
	 var storeComentarios = new Ext.data.JsonStore({
	 id			: 'storeComentarios',
	 idProperty	: 'Id',
	 proxy: new Ext.ux.data.PagingMemoryProxy(arrayComentarios),
	 sortInfo	: {
	 field		: 'seccion',
	 direction	: 'ASC'
	 },
	 baseParams	: {
	 csrf	: $csrf
	 },
	 remoteSort		: true, //ORDENACI�N
	 fields		: [ 'Id', 'usuario', 'seccion' ,'pagina' ,'comentario' ,'documento','comentarioReject', 'documentoDto' ]
	 });

	 // Refrescamos la barra de paginaci�n
	 Ext.getCmp('paginBarResultados').bindStore(storeComentarios);
	 Ext.getCmp('paginBarResultados').doLayout();
	 Ext.getCmp('paginBarResultados').changePage(1);

	 model = grid.getColumnModel();
	 grid.reconfigure(storeComentarios, model);
	 }
	 });*/
	arrayPedidos.push({
		'Id' : "3",
		'codPedido' : "3",
		'codProducto' : "1",
		'cantidad' : "5"
	});
	var grid = Ext.getCmp('gridPedidos');
	var storePedidosNuevo = new Ext.data.JsonStore({
		id : 'storePedidos',
		idProperty : 'codPedido',
		//    	data: arrayPedidos2,
		proxy : new Ext.ux.data.PagingMemoryProxy(arrayPedidos),
		sortInfo : {
			field : 'codPedido',
			direction : 'ASC'
		},
		remoteSort : true, //ORDENACI�N
		fields : [ 'Id', 'codPedido', 'codProducto', 'cantidad' ]
	});

	// Refrescamos la barra de paginaci�n
	Ext.getCmp('paginBarResultados').bindStore(storePedidosNuevo);
	Ext.getCmp('paginBarResultados').doLayout();
	Ext.getCmp('paginBarResultados').changePage(1);

	model = grid.getColumnModel();
	grid.reconfigure(storePedidosNuevo, model);

}
/**
 * Método que inserta los botones al final la pantalla
 */
function botones() {

	var panelBotones = new Ext.Panel({
		id : 'panelBotones',
		border : false,
		colspan : 1,
		style : 'margin: 3 auto;',
		buttonAlign : 'center',
		buttons : [ {
			xtype : 'button',
			text : "Aceptar",
			tooltip : "Aceptar",
			iconCls : 'icoRechazar',
			width : 100,
			handler : function() {

			}
		}, {
			xtype : 'button',
			text : "Rechazar",
			tooltip : "Rechazar",
			width : 100,
			iconCls : 'icoSave',
			handler : function() {
			}
		} ]
	});
	return panelBotones;
}