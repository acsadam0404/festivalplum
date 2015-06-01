package crud.vaadin.window;

import org.vaadin.openesignforms.ckeditor.CKEditorConfig;
import org.vaadin.openesignforms.ckeditor.CKEditorTextField;

import pl.exsio.plupload.Plupload;
import pl.exsio.plupload.PluploadError;
import pl.exsio.plupload.PluploadFile;
import pl.exsio.plupload.handler.memory.ByteArrayChunkHandlerFactory;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;

import crud.backend.entity.Band;
import crud.utils.Utils;
import crud.vaadin.LanguageEnum;


public class BandWindow extends BaseWindow {
	
	private static final String MAX_FILE_SITE = "20mb";
	
	private Band band;
	private boolean exist = false;
	private LanguageEnum lang;
	
	private Plupload plUpload;
	private Label infoLabel;
	
	private TextField name;
	private TextField nationality;
	private TextField style;
	private CKEditorTextField description;

	public BandWindow(String title, boolean exist, LanguageEnum lang) {
		super(title, 800);
		this.exist = exist;
		if(!exist){
			band = new Band();
			this.lang = lang;
			buildUpload();
			createCkEditor();
			initButton();
		}
	}
	
	public BandWindow(String title, Band band, LanguageEnum lang){
		this(title, true, lang);
		this.band = band;
		this.lang = lang;
		setFormData();
		buildUpload();
		createCkEditor();
		updateCkEditor();
		initButton();
	}
	
	private void setFormData(){
		Utils.setValue(name, band.getName());
		Utils.setValue(nationality, band.getNationality());
		Utils.setValue(style, band.getStyle());
	}
	
	@Override
	protected void buildForm() {
		
		name = new TextField("Név");
		form.addComponent(name);
		
		nationality = new TextField("Nemzetiség");
		form.addComponent(nationality);
		
		style = new TextField("Stílus");
		form.addComponent(style);
		
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
		Utils.setValue(description, band.getDescriptionValue());
	}
	
	private void buildUpload(){
		this.infoLabel = new Label();
		this.plUpload = this.createUpload();
		
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.setHeight(50, Unit.PIXELS);
		horizontalLayout.addComponent(this.plUpload);
		horizontalLayout.addComponent(this.infoLabel);
		mainLayout.addComponent(horizontalLayout);
	}
	
	private void saveDoc(PluploadFile file) {
		try {
		    byte[] data = file.getUploadedFileAs(byte[].class);
		    band.setImage(file.getName(), data);
		    saveData();
		} catch (Exception e) {
		    Notification.show("Feltöltés NEM sikerült!");
		}
	}
	
	private Plupload createUpload() {
		final Plupload upload = new Plupload("Előadó feltöltése", FontAwesome.USER);
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
	


	@Override
	protected void save() {
		if(!exist)
			band.create(lang);

		if ((this.plUpload.getQueuedFiles() != null) && (this.plUpload.getQueuedFiles().length > 0)) {
			this.plUpload.start();
		}else{
			saveData();
		}

	}

	private void saveData(){
		band.setName(Utils.getValue(name));
		band.setStyle(Utils.getValue(style));
		band.setNationality(Utils.getValue(nationality));
		band.setDescriptionValue(Utils.getValue(description));
		
		band.save();
		close();
	}

	@Override
	protected void delete() {
		band.delete();
		close();
	}

}
