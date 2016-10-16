package cpp.VNCreator.Controller;

import java.io.File;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ImageLoader {
	
	FileChooser filechooser;
	Stage primaryStage;
	Hashtable<String, ImageStorage> background;
	Hashtable<String, ImageStorage> actors;
	
	public ImageLoader(Stage primaryStage) {
		this.primaryStage = primaryStage;
		background = new Hashtable<String, ImageStorage>();
		actors = new Hashtable<String, ImageStorage>();
		filechooser = new FileChooser();
		setFileType();
		filechooser.setInitialDirectory(new File(System.
				getProperty("user.home")));
	}
	
	/**
	 * setFileType sets fileChooser to be .project
	 */
	private void setFileType(){
		filechooser.getExtensionFilters().clear();
		//filechooser.getExtensionFilters().add(
		//		new FileChooser.ExtensionFilter("Project file", "*.project"));
		
	}
		
	public void loadBackground(){
		loadImage(background);
	}
	
	public void loadActor(){
		loadImage(actors);
	}
	
	public void loadImage(Hashtable<String, ImageStorage> table){
		List<File> fileList = filechooser.showOpenMultipleDialog(primaryStage);
        if (fileList != null) {
        	for( File file : fileList){
        		if(!table.containsKey(file.getName())){
        			Image image = new Image(file.toURI().toString());
        			table.put(file.getName(), new ImageStorage(file.getName(), image));
        		}
        	}
        }
	}
	
	public Set<String> getBackgroundKeys(){
		return background.keySet();		
	}
	
	public Set<String> getActorKeys(){
		return background.keySet();		
	}
	
	public class ImageStorage{
		private String name;
		private Image image;
		
		
		public ImageStorage(String name, Image image){
			this.name = name;
			this.image = image;
		}
		
		public String getName(){
			return name;
		}
		
		public Image getImage(){
			return image;
		}
	}

	public Image getBackground(String name) {
		return background.get(name).image;
	}

	public Hashtable<String, ImageStorage> getBackground() {
		return background;
	}

	public Image getSprite(String fileName) {
		return actors.get(fileName).getImage();
	}
	
	public Hashtable<String, ImageStorage> getSprite() {
		return actors;
	}
}
