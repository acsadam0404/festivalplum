package crud.vaadin.component;

import java.util.List;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Table;
import crud.backend.entity.Stage;

public class StageListComp  extends CustomComponent {
	private Table table;
	
	public StageListComp(List<Stage> stageList) {
		setSizeFull();
		setCompositionRoot(build());
		refresh(stageList);
	}

	private Component build() {
		table = new Table();
		table.setContainerDataSource(new BeanItemContainer(Stage.class));
		table.setVisibleColumns("name");
		table.setColumnHeader("name", "Név");

		return table;
	}

	public void refresh(List<Stage> stageList) {
		BeanItemContainer container = (BeanItemContainer) table.getContainerDataSource();
		container.removeAllItems();
		container.addAll(stageList);
	}
	
	public Table getTable(){
		return table;
	}

}
