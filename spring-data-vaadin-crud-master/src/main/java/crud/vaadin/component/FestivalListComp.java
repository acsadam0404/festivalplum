package crud.vaadin.component;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Table;

import crud.backend.entity.Festival;

public class FestivalListComp extends CustomComponent {
	private Table table;
	
	public FestivalListComp() {
		setSizeFull();
		setCompositionRoot(build());
		refresh();
	}

	private Component build() {
		table = new Table();
		table.setSizeFull();
		table.setContainerDataSource(new BeanItemContainer(Festival.class));
		table.setVisibleColumns("name", "festival", "email", "phone", "website", "country", "city", "address", "postcode", "priority", "startDate", "endDate");
		table.setColumnHeader("name", "Név");
		return table;
	}
	
	private void refresh() {
		BeanItemContainer container = (BeanItemContainer) table.getContainerDataSource();
		container.removeAllItems();
		container.addAll(Festival.findAll());
	}

	public Table getTable(){
		return table;
	}
}
