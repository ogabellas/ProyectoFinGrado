/**
 * 
 */
var arrayPedidos = [];
Ext.onReady(function() {

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
			iconCls : 'icoEdit',
			id : 'btn_versionar',
			tooltip : "Editar",
			text : "Editar",
			handler : function() {
				//se comprueban los pedidos seleccionados
				var seleccionados = Ext.getCmp('gridPedidos').getSelectionModel().selections.items;
				if(seleccionados.length!=1){
					alert("Seleccione 1 pedido");
				} else {
					var seleccion= seleccionados[0];
					ventanaEditar(seleccion);
				}
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
	/**
	 * Campo TextField con límite efectivo de longitud
	 * (simula el comportamiento por defecto de los textfields de mantenimiento ágil)
	 */
	Ext.ux.form.InsaTextField = Ext.extend(Ext.form.TextField, {
	    initComponent	: function() {
	    	Ext.ux.form.InsaTextField.superclass.initComponent.call(this);
	    	// (si se ha especificado maxLength pero no autoCreate, prepara un autoCreate adecuado)
	    	if (this.maxLength && !this.autoCreate) {
				// (clona defaultAutoCreate como autoCreate)
	        	this.autoCreate = {};
	            for (var key in this.defaultAutoCreate) {
	            	this.autoCreate[key] = this.defaultAutoCreate[key];
	            }
	            // (asigna el maxLength del objeto)
	        	this.autoCreate.maxLength = this.maxLength;
	    	}
	    },
	    listeners	: {
	    	// Trim automático
	    	'blur'	: function(textfield) {
				var newValue = Ext.util.Format.trim(textfield.getValue());
				if (newValue !== textfield.getValue()) {
					this.setValue(newValue);
				}
	    	}
	    }
	});
	Ext.reg('textfield_Insa', Ext.ux.form.InsaTextField);
	
}

/**
 * M�todo que recarga el grid de cpmentarios propios usuario
 */
function handlerBuscar() {

	Ext.Ajax.request({
	 url		: String.format('http://localhost:8080/Tienda/buscarPedidosAprobar'),
	 method	: 'POST',
	 timeout : 220000,
	 success	: function(response){
	 arrayPedidos = [];
	 var pedidos= Ext.util.JSON.decode(response.responseText);
	 Ext.each(pedidos, function(pedido) {
		 var codPedido=pedido.codPedido;
//		 var codProducto=pedido.codProducto;
		 var codProducto=pedido.nombreProducto;
		 var cantidad=pedido.cantidad;
		 arrayPedidos.push({
				'Id' : codPedido,
				'codPedido' : codPedido,
				'codProducto' : codProducto,
				'cantidad' : cantidad
			});
		var grid = Ext.getCmp('gridPedidos');
		var storePedidosNuevo = new Ext.data.JsonStore({
			id : 'storePedidos',
			idProperty : 'codPedido',
			// data: arrayPedidos2,
			proxy : new Ext.ux.data.PagingMemoryProxy(arrayPedidos),
			sortInfo : {
				field : 'codPedido',
				direction : 'ASC'
			},
			remoteSort : true, // ORDENACI�N
			fields : [ 'Id', 'codPedido', 'codProducto', 'cantidad' ]
		});
	
		// Refrescamos la barra de paginaci�n
		Ext.getCmp('paginBarResultados').bindStore(storePedidosNuevo);
		Ext.getCmp('paginBarResultados').doLayout();
		Ext.getCmp('paginBarResultados').changePage(1);
	
		model = grid.getColumnModel();
		grid.reconfigure(storePedidosNuevo, model);
	 	});
	 }
	});

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
				//se comprueban los pedidos seleccionados
				var seleccionados = Ext.getCmp('gridPedidos').getSelectionModel().selections.items;
				if(seleccionados.length==0){
					alert("Seleccione pedidos");
				} else {
					 var enviar="";
					 Ext.each(seleccionados, function(seleccion) {
						 enviar=enviar+seleccion.data.Id+";";
					 });
					 //Se aprueban los pedidos
						Ext.Ajax.request({
							 url		: String.format('http://localhost:8080/Tienda/aprobarPedidos'),
							 params		: {id: enviar},
							 method		: 'POST',
							 timeout	: 220000,
							 success	: function(response){
								 handlerBuscar();
								 alert("Pedidos aprobados");
							 }
						});
					 
				}
				
			}
		}, {
			xtype : 'button',
			text : "Rechazar",
			tooltip : "Rechazar",
			width : 100,
			iconCls : 'icoSave',
			handler : function() {
				//se comprueban los pedidos seleccionados
				var seleccionados = Ext.getCmp('gridPedidos').getSelectionModel().selections.items;
				if(seleccionados.length==0){
					alert("Seleccione pedidos");
				} else {
					 var enviar="";
					 Ext.each(seleccionados, function(seleccion) {
						 enviar=enviar+seleccion.data.Id+";";
					 });
					 //Se aprueban los pedidos
						Ext.Ajax.request({
							 url		: String.format('http://localhost:8080/Tienda/rechazarPedidos'),
							 params		: {id: enviar},
							 method		: 'POST',
							 timeout	: 220000,
							 success	: function(response){
								 handlerBuscar();
								 alert("Pedidos rechazados");
							 }
						});
					 
				}
			}
		} ]
	});
	return panelBotones;
}

function ventanaEditar(seleccion) {
	
	var panelNuevo = new Ext.FormPanel({
		id				: 'formPanelNuevo',
		method			: 'POST',
		autoWidth		: true,
        style			: 'margin: 3 auto;',
		frame			: true,
		collapsible		: false,
		collapsed		: false,
		layout			: 'table',
		layoutConfig 	: { columns 	: 2},
		items			: 
						[
							{
					    	    xtype		: 'label',
					    	    name		: 'nombreDocumento',
					    	    style		: 'margin: 10px',
					    	    html		: "Pedido"
						    },
					        {
				                xtype		: 'textfield_Insa',
				                id			: 'pedido',
								name      	: 'pedido',
								value		: seleccion.data.Id,
				    			colspan		: 1,
				    			disabled	: true,
				                width		: 270,
				                maxLength	: 30
				            },
				            {
				            	xtype		: 'label',
				            	name		: 'codigoProductoLabel',
				            	style		: 'margin: 10px',
				            	html		: "Producto"
				            },
				            {
				            	xtype		: 'textfield_Insa',
				            	id			: 'producto',
				            	name      	: 'producto',
				            	value		: seleccion.data.codProducto,
				            	colspan		: 1,
				            	disabled	: true,
				            	width		: 270,
				            	maxLength	: 30
				            },
				            {
				            	xtype		: 'label',
				            	name		: 'labelCantidad',
				            	style		: 'margin: 10px',
				            	html		: "Cantidad"
				            },
				            {
				            	xtype		: 'textfield_Insa',
				            	id			: 'Cantidad',
				            	name      	: 'Cantidad',
				            	maskRe		: /[0-9.]/,
				            	colspan		: 1,
				            	value		: seleccion.data.cantidad,
				            	width		: 270,
				            	maxLength	: 30
				            },
				            {
				                xtype	: 'button',
				                id		: 'aceptarNuevo',
				            	text	: 'Modificar',
				                tooltip : 'Añadir',
				                iconCls	: 'icoOk',
				                width	: 200,
				                dock	: 'bottom',
				                style	: 'margin: 150 auto;',
			                	colspan	: 1,
			                	handler	: function () {
			                		var datosEnviar=[];
		                			datosEnviar.push (document.getElementById('pedido').value);
		                			datosEnviar.push (document.getElementById('Cantidad').value);
			                		Ext.Ajax.request({
			                			url		: String.format('http://localhost:8080/Tienda/modificarPedido'),
			                			params	: {datos: datosEnviar},
			                			method	: 'POST',
			                			success	: function(response){
			                				win.close();
			                				handlerBuscar();
			                			}
			                		});
			                	}
				            }
						]
	});
	var win = new Ext.Window({
	    id			: 'windowNuevoPedido',
	    width		: 500,
	    items		: [panelNuevo],
	    modal		: true,
	    border		: false,
	    resizable	: false,
	    constrain	: true,
	    background:"red",
		closeAction : 'close'

	});	
	
	win.show();	
}

