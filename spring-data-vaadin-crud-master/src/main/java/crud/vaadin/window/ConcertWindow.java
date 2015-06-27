package crud.vaadin.window;

import java.util.Date;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;



import com.vaadin.ui.PopupDateField;

import crud.backend.entity.Concert;
import crud.backend.entity.Festival;
import crud.backend.entity.ParseCache;
import crud.backend.entity.ParseUtils;
import crud.utils.Utils;
import crud.vaadin.LanguageEnum;
import crud.vaadin.calendar.ComboBoxItem;

public class ConcertWindow extends BaseWindow {
	
	private ParseCache parseCache;
	private Concert concert;
	private Date sDate;
	private Date eDate;
	private ComboBox bandCombo;
	private ComboBox stageCombo;
	private ComboBox eventCombo;
	private DateField startDate;
	private DateField endDate;
	private LanguageEnum lang;
	
	
	public ConcertWindow(String title, Date startDate, Date endDate, ParseCache parseCache, Concert concert, LanguageEnum lang){
		super(title);
		this.lang = lang;
		this.concert = concert;
		this.parseCache = parseCache;
		sDate = startDate;
		eDate = endDate;
		setFormData();
		if(concert.getConcert() != null){
			setFormComboData();
		}
		
		initButton();
	}
	
	private void setFormComboData(){
		bandCombo.select(parseCache.getSelectedBand(concert.getBandId()));
		eventCombo.select(parseCache.getSelectedEvent(concert.getEventId()));
		stageCombo.select(parseCache.getSelectedStage(concert.getStageId()));
		
		eventCombo.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				stageCombo.setEnabled(true);
				if(eventCombo.getValue() != null ){
					reloadStageCombo(((ComboBoxItem)eventCombo.getValue()).getId());
				}else{
					reloadStageCombo(null);
				}
			}
		});
	}
	
	private void setFormData(){
		
		BeanItemContainer<ComboBoxItem> bandItems = new BeanItemContainer<ComboBoxItem>(ComboBoxItem.class, parseCache.getBandItemList());
		bandCombo.setContainerDataSource(bandItems);

		BeanItemContainer<ComboBoxItem> eventItems = new BeanItemContainer<ComboBoxItem>(ComboBoxItem.class, parseCache.getEventItemList());
		eventCombo.setContainerDataSource(eventItems);
		
		BeanItemContainer<ComboBoxItem> stageItems = new BeanItemContainer<ComboBoxItem>(ComboBoxItem.class, parseCache.getStageItemList());
		stageCombo.setContainerDataSource(stageItems);
		
		Utils.setValue(startDate, sDate);
		Utils.setValue(endDate, eDate);
		
		
	}
	
	private void reloadStageCombo(String eventId){
		BeanItemContainer<ComboBoxItem> stageItems;
		if(eventId != null){
			stageItems = new BeanItemContainer<ComboBoxItem>(ComboBoxItem.class, ParseUtils.getStageList(parseCache.getEventMap().get(eventId), parseCache.getStageMap(), lang));
		}else{
			stageItems = new BeanItemContainer<ComboBoxItem>(ComboBoxItem.class, parseCache.getStageItemList());
		}
		stageCombo.setContainerDataSource(stageItems);
	}
	
	@Override 
	protected void buildForm(){

		bandCombo = new ComboBox("Fellépő:");
		bandCombo.setItemCaptionMode(ItemCaptionMode.PROPERTY);
		bandCombo.setItemCaptionPropertyId("description");
		bandCombo.setWidth(600, Unit.PIXELS);
		form.addComponent(bandCombo);
		
		eventCombo = new ComboBox("Esemény");
		eventCombo.setItemCaptionMode(ItemCaptionMode.PROPERTY);
		eventCombo.setItemCaptionPropertyId("description");
		eventCombo.setWidth(600, Unit.PIXELS);
		form.addComponent(eventCombo);
		
		
		stageCombo = new ComboBox("Színpad");
		stageCombo.setItemCaptionMode(ItemCaptionMode.PROPERTY);
		stageCombo.setItemCaptionPropertyId("description");
		stageCombo.setWidth(600, Unit.PIXELS);
		stageCombo.setEnabled(false);
		form.addComponent(stageCombo);
		
		startDate = new DateField("Kezdődik");
		startDate.setResolution(Resolution.MINUTE);
		form.addComponent(startDate);
		
		endDate = new DateField("Végetér");
		endDate.setResolution(Resolution.MINUTE);
		form.addComponent(endDate);

	}

	@Override
	protected void save() {
		if(concert.getConcert() == null){
			concert.create();
		}
		
		ComboBoxItem bandItem = (ComboBoxItem) bandCombo.getValue();
		concert.setBand(parseCache.getBandMap().get(bandItem.getId()));
		ComboBoxItem stageItem = (ComboBoxItem) stageCombo.getValue();
		concert.setStage(parseCache.getStageMap().get(stageItem.getId()));
		ComboBoxItem eventItem = (ComboBoxItem) eventCombo.getValue();
		concert.setEvent(parseCache.getEventMap().get(eventItem.getId()));
		
		concert.setStartDate(Utils.getValue(startDate));
		concert.setEndDate(Utils.getValue(endDate));
		concert.save();
		close();
	}

	@Override
	protected void delete() {
		concert.delete();
		close();
	}

}
