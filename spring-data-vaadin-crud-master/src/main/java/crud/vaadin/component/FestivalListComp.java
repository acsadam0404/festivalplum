package crud.vaadin.component;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Table;
import com.vaadin.data.Property;
import crud.backend.entity.Festival;
import crud.vaadin.LanguageEnum;

public class FestivalListComp extends CustomComponent {
	private Table table;
	
	public FestivalListComp(LanguageEnum lang) {
		setSizeFull();
		setCompositionRoot(build());
		refresh(lang);
	}

	private Component build() {
		table = new Table("Formatted Table") {
		    @Override
		    protected String formatPropertyValue(Object rowId, Object colId, Property property) {
		        // Format by property type
		        if (property.getType() == Date.class) {
		            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		            return df.format((Date)property.getValue());
		        }

		        return super.formatPropertyValue(rowId, colId, property);
		    }
		};

		table.setSizeFull();
		table.setContainerDataSource(new BeanItemContainer(Festival.class));
		table.setVisibleColumns("name", "festival", "country", "city", "address", "postcode", "priority", "startDate", "endDate");
		table.setColumnHeader("name", "Név");
		table.setColumnHeader("festival", "Fesztivál");
		table.setColumnHeader("country", "Ország");
		table.setColumnHeader("city", "Város");
		table.setColumnHeader("address", "Cím");
		table.setColumnHeader("postcode", "Irányítószám");
		table.setColumnHeader("priority", "Prioritásos");
		table.setColumnHeader("startDate", "Kezdődik");
		table.setColumnHeader("endDate", "Végetér");

		return table;
	}
	
	private void refresh(LanguageEnum lang) {
		BeanItemContainer container = (BeanItemContainer) table.getContainerDataSource();
		container.removeAllItems();
		container.addAll(Festival.findAll(lang));
	}

	public Table getTable(){
		return table;
	}
}
