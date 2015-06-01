package crud.vaadin.window;

import org.vaadin.openesignforms.ckeditor.CKEditorConfig;
import org.vaadin.openesignforms.ckeditor.CKEditorTextField;

import pl.exsio.plupload.Plupload;
import pl.exsio.plupload.PluploadError;
import pl.exsio.plupload.PluploadFile;
import pl.exsio.plupload.handler.memory.ByteArrayChunkHandlerFactory;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;

import crud.backend.entity.Festival;
import crud.utils.Utils;
import crud.vaadin.LanguageEnum;
import crud.vaadin.component.StageListComp;

public class FestivalWindow extends BaseWindow {
	
	private static final String MAX_FILE_SITE = "20mb";
	
	private Festival festival;
	private boolean exist = false;
	private LanguageEnum lang;
	private StageListComp stageData;
	
	private Plupload plUpload;
	private Label infoLabel;
	
	private Plupload mapPlUpload;
	private Label mapInfoLabel;
	
	private TextField name;
	private CheckBox isFestival;
	private TextField email;
	private TextField phone;
	private TextField website;
	private TextField country;
	private TextField city;
	private TextField address;
	private TextField postcode;
	private CheckBox priority;
	private DateField startDate;
	private DateField endDate;
	private CKEditorTextField description;
	//private TextField map;
	
	public FestivalWindow(String title, boolean exist, LanguageEnum lang){
		super(title, 800);
		this.exist = exist;
		if(!exist){
			festival = new Festival();
			this.lang = lang;
			buildUpload();
			createCkEditor();
			//initStage();
			initButton();
		}
	}
	
	public FestivalWindow(String title, Festival festival, LanguageEnum lang){
		this(title, true, lang);
		this.festival = festival;
		this.lang = lang;
		setFormData();
		buildUpload();
		createCkEditor();
		updateCkEditor();
		initStage();
		initButton();
	}
	
	private void buildUpload(){
		this.infoLabel = new Label();
		this.plUpload = this.createUpload(infoLabel, "Fesztivál kép feltöltése");
		
		this.mapInfoLabel = new Label();
		this.mapPlUpload = this.createUpload(mapInfoLabel, "Térkép feltöltése");
		
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.setHeight(50, Unit.PIXELS);
		horizontalLayout.addComponent(this.plUpload);
		horizontalLayout.addComponent(this.infoLabel);
		horizontalLayout.addComponent(this.mapPlUpload);
		horizontalLayout.addComponent(this.mapInfoLabel);
		mainLayout.addComponent(horizontalLayout);
	}
	
	private Plupload createUpload(Label infoLabel, String uploadText) {
		final Plupload upload = new Plupload(uploadText, FontAwesome.FILES_O);
		upload.setChunkHandlerFactory(new ByteArrayChunkHandlerFactory());

		upload.setMaxFileSize(MAX_FILE_SITE);
		upload.setMultiSelection(false);

		upload.addFileUploadedListener(new Plupload.FileUploadedListener() {
		    @Override
		    public void onFileUploaded(PluploadFile file) {
		    	saveDoc(file);
		    }
		});

		upload.addUploadProgressListener(new Plupload.UploadProgressListener() {
		    @Override
		    public void onUploadProgress(PluploadFile file) {
			infoLabel.setValue(file.getName() + " feltöltése folyamatban:" + file.getPercent() + "%");
		    }
		});

		upload.addFilesAddedListener(new Plupload.FilesAddedListener() {
		    @Override
		    public void onFilesAdded(PluploadFile[] files) {
			infoLabel.setValue(files[0].getName());
		    }
		});

		upload.addUploadCompleteListener(new Plupload.UploadCompleteListener() {
		    @Override
		    public void onUploadComplete() {
			infoLabel.setValue("Feltöltés befejeződött.");
		    }
		});

		upload.addErrorListener(new Plupload.ErrorListener() {
		    @Override
		    public void onError(PluploadError error) {
			Notification.show("Hiba történt a feltöltés során: " + error.getMessage() + " (" + error.getType() + ")", Notification.Type.ERROR_MESSAGE);
		    }
		});

		upload.setChunkSize("2mb");

		upload.setWidthUndefined();

		return upload;
	}
	
	private void saveDoc(PluploadFile file) {
		try {
		    byte[] data = file.getUploadedFileAs(byte[].class);
		    festival.setImage(file.getName(), data);
		    saveData();
		} catch (Exception e) {
		    Notification.show("Feltöltés NEM sikerült!");
		}
	}
	
	private void setFormData(){
		Utils.setValue(name, festival.getName());
		Utils.setValue(isFestival, festival.getFestival());
		Utils.setValue(email, festival.getEmail());
		Utils.setValue(phone, festival.getPhone());
		Utils.setValue(website, festival.getWebsite());
		Utils.setValue(country, festival.getCountry());
		Utils.setValue(city, festival.getCity());
		Utils.setValue(address, festival.getAddress());
		Utils.setValue(postcode, festival.getPostcode());
		Utils.setValue(priority, festival.getPriority());
		Utils.setValue(startDate, festival.getStartDate());
		Utils.setValue(endDate, festival.getEndDate());

		//Utils.setValue(map, festival.getMap());
	}
	
	private void createCkEditor(){
		CKEditorConfig config = new CKEditorConfig();
        config.useCompactTags();
        config.disableElementsPath();
        config.disableSpellChecker();
        config.enableVaadinSavePlugin();
        config.setHeight("400px");
        
        description = new CKEditorTextField(config);
        mainLayout.addComponent(description);

	}
	
	private void updateCkEditor(){
		Utils.setValue(description, festival.getDescriptionValue());
	}
	
	@Override 
	protected void buildForm(){
		
		isFestival = new CheckBox("Fesztivál");
		form.addComponent(isFestival);
		
		priority = new CheckBox("Prioritás");
		form2.addComponent(priority);
		
		name = new TextField("Név");
		form.addComponent(name);
		
		phone = new TextField("Telefon");
		form2.addComponent(phone);
		
		website = new TextField("Weboldal");
		form.addComponent(website);
		
		country = new TextField("Ország");
		form2.addComponent(country);
		
		city = new TextField("Város");
		form.addComponent(city);
		
		address = new TextField("Cím");
		form2.addComponent(address);
		
		postcode = new TextField("Irányítószám");
		form.addComponent(postcode);
		
		email = new TextField("Email");
		form2.addComponent(email);
		
		startDate = new DateField("Kezdődik");
		form.addComponent(startDate);
		
		endDate = new DateField("Vége");
		form2.addComponent(endDate);
		
//		map = new TextField("Térkép");
//		form.addComponent(map);

	}
	
	private void initStage(){
		HorizontalLayout addStageLayout = new HorizontalLayout();
		addStageLayout.setWidth(100, Unit.PERCENTAGE);
		addStageLayout.setHeight(100, Unit.PIXELS);
		TextField stageName = new TextField("Új színpad");
		FormLayout form = new FormLayout();
		form.addComponent(stageName);
		addStageLayout.addComponent(form);
		Button addStageButton = new Button("Hozzáadás");
		addStageLayout.addComponent(addStageButton);
		addStageLayout.setComponentAlignment(addStageButton, Alignment.MIDDLE_LEFT);
		
		addStageButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if(stageName.getValue() != null && !"".equals(stageName.getValue())){
					festival.addStage(stageName.getValue());
					stageName.clear();
					stageData.refresh(festival.getStageList());
				}
			}
		});
		
		mainLayout.addComponent(addStageLayout);

		stageData = new StageListComp(festival.getStageList());
		stageData.setWidth(100, Unit.PERCENTAGE);
		//stageData.setHeight(300, Unit.PIXELS);
		mainLayout.addComponent(stageData);
	}

	@Override
	protected void save() {
		if(!exist)
			festival.create(lang);
		
		if ((this.plUpload.getQueuedFiles() != null) && (this.plUpload.getQueuedFiles().length > 0)) {
			this.plUpload.start();
		}else{
			saveData();
		}

	}
	
	private void saveData(){
		festival.setName(Utils.getValue(name));
		festival.setFestival(Utils.getValue(isFestival));
		festival.setEmail(Utils.getValue(email));
		festival.setPhone(Utils.getValue(phone));
		festival.setWebsite(Utils.getValue(website));
		festival.setCountry(Utils.getValue(country));
		festival.setCity(Utils.getValue(city));
		festival.setAddress(Utils.getValue(address));
		festival.setPostcode(Utils.getValue(postcode));
		festival.setPriority(Utils.getValue(priority));
		festival.setStartDate(Utils.getValue(startDate));
		festival.setEndDate(Utils.getValue(endDate));
		festival.setDescriptionValue(Utils.getValue(description));
		//festival.setMap(Utils.getValue(map));
		
		festival.save();
		close();
	}

	@Override
	protected void delete() {
		festival.delete();
		close();
	}
}
