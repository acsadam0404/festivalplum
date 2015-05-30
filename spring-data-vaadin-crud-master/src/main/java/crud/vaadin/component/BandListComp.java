package crud.vaadin.component;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Table;

import crud.backend.entity.Band;

public class BandListComp extends CustomComponent {
	private Table table;
	
	public BandListComp() {
		setSizeFull();
		setCompositionRoot(build());
		refresh();
	}

	private Component build() {
		table = new Table();
		table.setSizeFull();
		table.setContainerDataSource(new BeanItemContainer(Band.class));
		table.setVisibleColumns("name", "style", "nationality");
		table.setColumnHeader("name", "Név");
		table.setColumnHeader("style", "Stílus");
		table.setColumnHeader("nationality", "Nemzetiség");
		return table;
	}

	private void refresh() {
		BeanItemContainer container = (BeanItemContainer) table.getContainerDataSource();
		container.removeAllItems();
		container.addAll(Band.findAll());
	}
	
	public Table getTable(){
		return table;
	}

}
