package crud.vaadin.window;

import java.util.Date;

import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;



import crud.backend.entity.Concert;
import crud.backend.entity.Festival;
import crud.backend.entity.ParseCache;
import crud.utils.Utils;
import crud.vaadin.calendar.ComboBoxItem;

public class ConcertWindow extends BaseWindow {
	
	private ParseCache parseCache;
	private Concert concert;
	private Date sDate;
	private Date eDate;
	private ComboBox bandCombo;
	private ComboBox stageCombo;
	private ComboBox eventCombo;
	
	
	public ConcertWindow(String title, Date startDate, Date endDate, ParseCache parseCache, Concert concert){
		super(title, 800);
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
		stageCombo.select(parseCache.getSelectedStage(concert.getStageId()));
		eventCombo.select(parseCache.getSelectedEvent(concert.getEventId()));
	}
	
	private void setFormData(){
		
		BeanItemContainer<ComboBoxItem> bandItems = new BeanItemContainer<ComboBoxItem>(ComboBoxItem.class, parseCache.getBandItemList());
		bandCombo.setContainerDataSource(bandItems);
		
		
		BeanItemContainer<ComboBoxItem> stageItems = new BeanItemContainer<ComboBoxItem>(ComboBoxItem.class, parseCache.getStageItemList());
		stageCombo.setContainerDataSource(stageItems);
		
		
		BeanItemContainer<ComboBoxItem> eventItems = new BeanItemContainer<ComboBoxItem>(ComboBoxItem.class, parseCache.getEventItemList());
		eventCombo.setContainerDataSource(eventItems);
		
		
	}
	
	@Override 
	protected void buildForm(){

		
		bandCombo = new ComboBox("Fellépő:");
		bandCombo.setItemCaptionMode(ItemCaptionMode.PROPERTY);
		bandCombo.setItemCaptionPropertyId("description");
		form.addComponent(bandCombo);
		
		stageCombo = new ComboBox("Színpad");
		stageCombo.setItemCaptionMode(ItemCaptionMode.PROPERTY);
		stageCombo.setItemCaptionPropertyId("description");
		form.addComponent(stageCombo);
		
		eventCombo = new ComboBox("Esemény");
		eventCombo.setItemCaptionMode(ItemCaptionMode.PROPERTY);
		eventCombo.setItemCaptionPropertyId("description");
		form.addComponent(eventCombo);
		
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
		
		concert.setStartDate(sDate);
		concert.setEndDate(eDate);
		concert.save();
		close();
	}

	@Override
	protected void delete() {
		concert.delete();
		close();
	}

}
