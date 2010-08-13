/*!
 * Ext JS Library 3.2.0
 * Copyright(c) 2006-2010 Ext JS, Inc.
 * licensing@extjs.com
 * http://www.extjs.com/license
 */
Ext.onReady(function(){
    // shorthand
    var Tree = Ext.tree;
    var tree = new Tree.TreePanel({
    	useArrows: true,
        autoScroll: true,
        animate: true,
        enableDD: true,
        containerScroll: true,
        border: false,
        // auto create TreeLoader
        dataUrl: 'get-node.jsp',
        root: {
            nodeType: 'async',
            text: 'Src',
            draggable: false,
            id: 'src'
        },
        listeners: {
            'click': function(node) {
          		if (node.isLeaf()) {
	            		Ext.Ajax.request({
						   url: 'get-node.jsp?id=' + node.id,
						   success: function(response, opts) {
						      var obj = Ext.decode(response.responseText);
						      alert(obj.id);
						   },
						   failure: function(response, opts) {
						      alert('failure');
						   }
						})
          		}
         	 }
        }
    });

    // render the tree
    tree.render('tree-div');
    tree.getRootNode().expand();
    
   var p = new Ext.Panel({
    title: 'Console',
    width: 250,
    height: 200,
    renderTo: document.body,
    html: '<h2>Some introduction for the console!</h2>',
    tbar: [{
        xtype: 'buttongroup',
        title: 'Operate',
        items: [{
            text: 'Operate1',
            rowspan: 3, iconCls: 'add',
            iconAlign: 'top',
            cls: 'x-btn-as-arrow',
            listeners: {
            	'click': function(b) {
            		alert(tree);
            	}
            }
        },{
            text: 'Operate2',
            rowspan: 3, iconCls: 'add',
            iconAlign: 'top',
            cls: 'x-btn-as-arrow',
            listeners: {
            	'click': function(b) {
            		alert(tree);
            	}
            }
        },{
            text: 'Operate3',
            rowspan: 3, iconCls: 'add',
            iconAlign: 'top',
            cls: 'x-btn-as-arrow',
            listeners: {
            	'click': function(b) {
            		alert(tree);
            	}
            }
        },{
            text: 'Operate4',
            rowspan: 3, iconCls: 'add',
            iconAlign: 'top',
            cls: 'x-btn-as-arrow',
            listeners: {
            	'click': function(b) {
            		alert(tree);
            	}
            }
        }]
    }]
});
    
});