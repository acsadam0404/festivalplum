package crud.vaadin.component;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Table;
import crud.backend.entity.Concert;

public class ConcertListComp extends CustomComponent {
	private Table table;
	
	public ConcertListComp() {
		setSizeFull();
		setCompositionRoot(build());
		refresh();
	}

	private Component build() {
		table = new Table();
		table.setSizeFull();
		table.setContainerDataSource(new BeanItemContainer(Concert.class));
		return table;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void refresh() {
		BeanItemContainer container = (BeanItemContainer) table.getContainerDataSource();
		container.removeAllItems();
		container.addAll(Concert.findAll());
	}

	public Table getTable(){
		return table;
	}
}
