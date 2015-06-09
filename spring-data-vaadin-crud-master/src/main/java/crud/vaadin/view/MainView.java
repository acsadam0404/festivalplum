package crud.vaadin.view;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.Action;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.CloseListener;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClick;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClickHandler;
import com.vaadin.ui.components.calendar.CalendarDateRange;
import com.vaadin.ui.components.calendar.event.BasicEvent;
import com.vaadin.ui.components.calendar.event.CalendarEvent;
import com.vaadin.ui.themes.ValoTheme;

import crud.backend.entity.Band;
import crud.backend.entity.Festival;
import crud.vaadin.LanguageEnum;
import crud.vaadin.MenuEnum;
import crud.vaadin.calendar.ConcertCalendar;
import crud.vaadin.calendar.ConcertEvent;
import crud.vaadin.component.BandListComp;
import crud.vaadin.component.FestivalListComp;
import crud.vaadin.window.BandWindow;
import crud.vaadin.window.FestivalWindow;

public class MainView extends VerticalLayout implements View {
	
	private VerticalLayout content;
	private LanguageEnum lang = LanguageEnum.HU;
	private MenuEnum menu = MenuEnum.BAND;
	
	public MainView() {
		setSizeFull();
		setSpacing(true);
		setMargin(true);
		buildMenu();
		buildContent();
	}
	
	private void buildMenu() {
		HorizontalLayout menuLayout = new HorizontalLayout();
		menuLayout.setWidth(100, Unit.PERCENTAGE);
		menuLayout.setSpacing(true);
		MenuBar menu = new MenuBar();
		menu.addItem("Fellépő", null, bandCommand());
		menu.addItem("Fesztivál", null, festivalCommand());
		menu.addItem("Koncert", null, concertCommand());

		menuLayout.addComponent(menu);
		menuLayout.setComponentAlignment(menu, Alignment.MIDDLE_LEFT);
		
		ComboBox combo = new ComboBox("");
		combo.addItems(LanguageEnum.values());
		combo.setValue(lang);
		combo.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				if(combo.getValue() != null && lang != (LanguageEnum) combo.getValue()){
					lang = (LanguageEnum) combo.getValue();
					switchLanguage();
				}else{
					combo.setValue(lang);
				}
			}
		});
		menuLayout.addComponent(combo);
		menuLayout.setComponentAlignment(combo, Alignment.MIDDLE_RIGHT);

		addComponent(menuLayout);
    }
	
	private void switchLanguage(){
		switch (menu) {
		case BAND:
			loadBand();
			break;
		case FESTIVAL:
			loadFestival();
			break;
		case CONCERT:
			loadConcert();
			break;
		default:
			break;
		}
	}
	
	private void buildContent(){
		content = new VerticalLayout();
		content.setSizeFull();
		content.setSpacing(true);
		loadBand();
		addComponent(content);
		setExpandRatio(content, 1.0f);
		setComponentAlignment(content, Alignment.TOP_LEFT);
	}
	
	private Command bandCommand(){
		return new Command() {

		    @Override
		    public void menuSelected(MenuItem selectedItem) {
		    	loadBand();
		    }
		};
	}

	private Command festivalCommand(){
		return new Command() {

		    @Override
		    public void menuSelected(MenuItem selectedItem) {
		    	loadFestival();	
		    }
		};
	}
	
	private Command concertCommand(){
		return new Command() {

		    @Override
		    public void menuSelected(MenuItem selectedItem) {
		    	loadConcert();
		    }
		};
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	private void loadFestival(){
		menu = MenuEnum.FESTIVAL;
		content.removeAllComponents();
    	Button add = new Button("Hozzáad");
    	add.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().addWindow(new FestivalWindow("Fesztivál Létrehozása", false, lang));
			}
		});
		content.addComponent(add);
		FestivalListComp data = new FestivalListComp(lang);
		data.getTable().addItemClickListener(new ItemClickListener() {

		    @Override
		    public void itemClick(ItemClickEvent event) {
		    	BeanItem<Festival> item = (BeanItem) event.getItem();
		    	Festival festival = item.getBean();
		    	FestivalWindow festivalWindow = new FestivalWindow("Fesztivál Módosítása", festival, lang);
		    	festivalWindow.addCloseListener(new CloseListener() {
					
					@Override
					public void windowClose(CloseEvent e) {
						loadFestival();
						
					}
				});
		    	UI.getCurrent().addWindow(festivalWindow);
		    }
		});
		content.addComponent(data);
		content.setExpandRatio(data, 1.0f);
	}
	
	private void loadBand(){
		menu = MenuEnum.BAND;
		content.removeAllComponents();
    	Button add = new Button("Hozzáad");
    	add.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().addWindow(new BandWindow("Fellépő Létrehozása", false, lang));
			}
		});
		content.addComponent(add);
		BandListComp data = new BandListComp(lang);
		data.getTable().addItemClickListener(new ItemClickListener() {

		    @Override
		    public void itemClick(ItemClickEvent event) {
		    	BeanItem<Band> item = (BeanItem) event.getItem();
		    	Band band = item.getBean();
		    	BandWindow bandWindow = new BandWindow("Fellépő Módosítása", band, lang);
		    	bandWindow.addCloseListener(new CloseListener() {
					
					@Override
					public void windowClose(CloseEvent e) {
						loadBand();
						
					}
				});
		    	UI.getCurrent().addWindow(bandWindow);
		    }
		});
		content.addComponent(data);
		content.setExpandRatio(data, 1.0f);
	}
	
	private void loadConcert(){
		menu = MenuEnum.CONCERT;
    	content.removeAllComponents();
    	new ConcertCalendar(content, lang);
	}

}
