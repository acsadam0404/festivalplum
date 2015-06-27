package crud.vaadin.view;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.Action;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
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
import crud.backend.entity.Concert;
import crud.backend.entity.Festival;
import crud.backend.entity.ParseCache;
import crud.backend.entity.ParseUtils;
import crud.vaadin.LanguageEnum;
import crud.vaadin.MenuEnum;
import crud.vaadin.calendar.ComboBoxItem;
import crud.vaadin.calendar.ConcertCalendar;
import crud.vaadin.calendar.ConcertEvent;
import crud.vaadin.component.BandListComp;
import crud.vaadin.component.FestivalListComp;
import crud.vaadin.window.BandWindow;
import crud.vaadin.window.ConcertWindow;
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
		combo.setNullSelectionAllowed(false);
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
	
	public void loadConcert(final String eventId, final String stageId){
		menu = MenuEnum.CONCERT;
    	content.removeAllComponents();
    	
    	ParseCache parseCache = new ParseCache(lang);
    	
    	concertFilter(parseCache, eventId, stageId);
    	
    	Button add = new Button("Hozzáad");
    	add.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				ConcertWindow concertWindow = new ConcertWindow("Koncert", new Date(), new Date(), parseCache, new Concert(), lang);
				concertWindow.addCloseListener(new CloseListener() {
					
					@Override
					public void windowClose(CloseEvent e) {
						loadConcert(eventId, stageId);
						
					}
				});
				UI.getCurrent().addWindow(concertWindow);
			}
		});

		content.addComponent(add);
		VerticalLayout calendarLayout = new VerticalLayout();
		content.addComponent(calendarLayout);
		content.setExpandRatio(calendarLayout, 1.0f);
		
    	new ConcertCalendar(calendarLayout, lang, parseCache, eventId, stageId, this);
	}
	
	private ComboBox stageCombo;
	private ComboBox eventCombo;
	private void concertFilter(final ParseCache parseCache, String eventId, String stageId){
		
		Button filterButton;
		HorizontalLayout filterLayout = new HorizontalLayout();
		filterLayout.setSpacing(true);
		
		eventCombo = new ComboBox("Esemény");
		eventCombo.setItemCaptionMode(ItemCaptionMode.PROPERTY);
		eventCombo.setItemCaptionPropertyId("description");
		eventCombo.setWidth(600, Unit.PIXELS);
		filterLayout.addComponent(eventCombo);
		
		stageCombo = new ComboBox("Színpad");
		stageCombo.setItemCaptionMode(ItemCaptionMode.PROPERTY);
		stageCombo.setItemCaptionPropertyId("description");
		stageCombo.setWidth(600, Unit.PIXELS);
		filterLayout.addComponent(stageCombo);
		
		filterButton = new Button("Keresés");
		filterLayout.addComponent(filterButton);
		filterLayout.setComponentAlignment(filterButton, Alignment.BOTTOM_LEFT);
		
		filterButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				loadConcert(eventCombo.getValue() != null ? ((ComboBoxItem)eventCombo.getValue()).getId() : null, stageCombo.getValue() != null ? ((ComboBoxItem)stageCombo.getValue()).getId() : null);
			}
		});
		
		BeanItemContainer<ComboBoxItem> eventItems = new BeanItemContainer<ComboBoxItem>(ComboBoxItem.class, parseCache.getEventItemList());
		eventCombo.setContainerDataSource(eventItems);
		
		BeanItemContainer<ComboBoxItem> stageItems = new BeanItemContainer<ComboBoxItem>(ComboBoxItem.class, parseCache.getStageItemList());
		stageCombo.setContainerDataSource(stageItems);
		
		if(eventId != null){
			eventCombo.select(parseCache.getSelectedEvent(eventId));
		}
		eventCombo.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				if(eventCombo.getValue() != null ){
					reloadStageCombo(((ComboBoxItem)eventCombo.getValue()).getId(), parseCache);
				}else{
					reloadStageCombo(null, parseCache);
				}
			}
		});
		if(stageId != null)
			stageCombo.select(parseCache.getSelectedStage(stageId));
		
		content.addComponent(filterLayout);
		
	}
	
	
	private void reloadStageCombo(String eventId, ParseCache parseCache){
		BeanItemContainer<ComboBoxItem> stageItems;
		if(eventId != null){
			stageItems = new BeanItemContainer<ComboBoxItem>(ComboBoxItem.class, ParseUtils.getStageList(parseCache.getEventMap().get(eventId), parseCache.getStageMap(), lang));
		}else{
			stageItems = new BeanItemContainer<ComboBoxItem>(ComboBoxItem.class, parseCache.getStageItemList());
		}
		stageCombo.setContainerDataSource(stageItems);
		
	}
	
	public void loadConcert(){
		loadConcert(null, null);
	}

}
