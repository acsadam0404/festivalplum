package crud.vaadin.component;

import java.util.List;

import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;

import crud.backend.entity.Band;
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
		table.setEditable(true);
		table.setSizeFull();
		table.setContainerDataSource(new BeanItemContainer(Stage.class));
		table.setVisibleColumns("name");
		table.setColumnHeader("name", "Név");
		
		table.addGeneratedColumn("", new ColumnGenerator() {
		@Override 
		public Object generateCell(final Table source, final Object itemId, Object columnId) {
		 
			Button deleteButton = new Button("Törlés");
			deleteButton.addClickListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					
					BeanItem<Stage> item = (BeanItem) source.getContainerDataSource().getItem(itemId);
			    	Stage stage = item.getBean();
			    	stage.delete();
					source.getContainerDataSource().removeItem(itemId);
				}
			});
		    
		    Button saveButton = new Button("Mentés");
		    saveButton.addClickListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					
					BeanItem<Stage> item = (BeanItem) source.getContainerDataSource().getItem(itemId);
			    	Stage stage = item.getBean();
			    	stage.saveName();
				}
			});
		    
		    HorizontalLayout actionLayout = new HorizontalLayout();
		    actionLayout.addComponent(saveButton);
		    actionLayout.addComponent(deleteButton);
		    actionLayout.setComponentAlignment(saveButton, Alignment.TOP_LEFT);
		    actionLayout.setComponentAlignment(saveButton, Alignment.TOP_RIGHT);
		    return actionLayout;
		  }
		});


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
