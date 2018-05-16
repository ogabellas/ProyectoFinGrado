/**
* For checkboxTree you have to change ext-all-debug.js line 23612
* add name and ID to the checkbox input:
*
* <input class="x-tree-node-cb" name="' + this.node.getOwnerTree().name + '[]" value="' + n.id + '" type="checkbox" ' + (a.checked ? 'checked="checked" /> 
*/
Ext.ux.checkboxTree = Ext.extend(Ext.BoxComponent, {
	
	
	isFormField: true,
    
	onRender: function(container, position) {
		Ext.ux.checkboxTree.superclass.onRender.call(this, container, position);
		if (this.el) {
			this.el = Ext.get(this.el);
			if (!this.target) {
				container.dom.appendChild(this.el.dom);
			}
		} 
		else {
			var cfg = { tag: 'div' };
			this.el = container.createChild(cfg, position);
		}

		this.tree = new Ext.tree.TreePanel({
			rootVisible: false,
			autoScroll: true,
			containerScroll: true,
			animCollapse: true,			
			animate: true,
			border: false,
			name: this.name,
			root: this.root,
			loader: new Ext.tree.TreeLoader({
				root : this.root,
				preloadChildren: true,
				baseAttrs: {
					checked: false
				}
			}),
		    listeners : {
				afterrender : function (panelTree) {
					panelTree.expandAll();
					panelTree.root.eachChild(function(n) {
					});
		    	}
		    }
		});
		if (this.bodyStyle){ 
			this.tree.bodyStyle = this.bodyStyle;
		}
		if (this.collapsed){ 
			this.tree.collapsed = this.collapsed;
		}
		this.tree.render(this.el.id);
		this.setChecked();  
	},

	clearInvalid: function() {
		if (this.tree.hasListener('checkchange')) {
			this.tree.removeListener('checkchange', setChecked);
		}
	},

	setChecked: function (node) {
		if (!this.tree.hasListener('checkchange')) {
			this.tree.addListener(
				'checkchange',
				setChecked = function (node) {
					if (node.attributes.checked) {
						if (node.attributes.children) {
							node.expand();

							var checked = false;
							node.eachChild(function(childrenNode) {
								if (childrenNode.attributes.checked == true) {
									checked = true;
								}
							});
							if (checked === false) {
								node.eachChild(function(childrenNode) {
									childrenNode.getUI().toggleCheck(true);
								});
							}
						}
						else {
							if (node.parentNode.id > 0 && node.parentNode.attributes.checked == false) {
								node.parentNode.getUI().toggleCheck(true);
							}
						}
					}
					else {
						if (node.attributes.children) {
							node.eachChild(function(childrenNode) {
								childrenNode.getUI().toggleCheck(false);
							});
						}
					}
					
				}
			);
		}
	},

	validate: function() {
	 	return true;
	},
	
	getName: function() {
		return this.name;
	},
	
	onResize: function(width, height) {
	 	this.tree.setHeight(height);
	},
	
	reset: function() {
		this.tree.getRootNode().cascade(
			function(node) {
				if (node.attributes.checked) {
					node.getUI().toggleCheck(false);
				}
			}
		);
		this.tree.root.collapse(true);
	},
	
	
	getHijosSeleccionados: function() {
	    var arrSeleccionados = new Array();
		this.tree.getRootNode().cascade(
			function(node) {
			   //solo los hijos se devuelven como seleccionados
			   if (!node.hasChildNodes() && node.attributes.checked) {			
					arrSeleccionados[arrSeleccionados.length] = node.id;			
			   }
			}
		);
		return arrSeleccionados;
	},
	
	getNodosSeleccionados: function() {
	    var arrSeleccionados = new Array();
		this.tree.getRootNode().cascade(
			function(node) {
			   if (node.attributes.checked) {			
					arrSeleccionados[arrSeleccionados.length] = node;			
			   }
			}
		);
		return arrSeleccionados;
	},

	setValue: function(value) {
		if (value != null) {
			var value = value + ',' + value;
			var checked = value.split(',');
			this.tree.root.cascade(function(node){
				if (checked.indexOf(node.id) != -1) {
					node.expand();
					node.getUI().toggleCheck(true);
				}
			});
		}
		this.setChecked(); 
	}
});
Ext.reg('checkboxTree', Ext.ux.checkboxTree);