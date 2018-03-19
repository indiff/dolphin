#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.smartgwt.client.core.KeyIdentifier;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.util.KeyCallback;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.VLayout;
import ${package}.shared.datasources.Order;

public final class ${module}EntryPoint implements EntryPoint {

	private static Order orderDS = GWT.create(Order.class);
	
	public void onModuleLoad() {
		
		ListGrid grid = new ListGrid();
		
		grid.setDataSource(DataSource.get(orderDS.dataSourceId()));
		grid.setAutoFetchData(true);
		grid.setCanEdit(true);

		VLayout layout = new VLayout();
		layout.setWidth100();
		layout.setHeight100();
		
		layout.addMember(grid);
		
		layout.draw();

		/*
		 * enable the sgwt debug console in development mode
		 */
		if (!GWT.isScript()) {
			KeyIdentifier debugKey = new KeyIdentifier();
			debugKey.setCtrlKey(true);
			debugKey.setAltKey(true);
			debugKey.setKeyName("D");
			Page.registerKey(debugKey, new KeyCallback() {
				public void execute(String keyName) {
					SC.showConsole();
				}
			});
		}

	}

}