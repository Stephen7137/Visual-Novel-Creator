package cpp.VNCreator.Controller;

import java.io.File;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import javafx.scene.image.Image;

public class ImageLoader {
	
	Hashtable<String, ImageStorage> background;
	Hashtable<String, ImageStorage> actors;
	double aspRatio;
	
	public ImageLoader() {
		background = new Hashtable<String, ImageStorage>();
		actors = new Hashtable<String, ImageStorage>();
	}
		
	public void loadBackground(List<File> file){
		loadImage(file, background);
	}
	
	public void loadActor(List<File> file){
		loadImage(file, actors);
	}
	
	public void loadImage(List<File> list, Hashtable<String, ImageStorage> table){
    	for( File file : list){
    		if(!table.containsKey(file.getName())){
    			Image image = new Image(file.toURI().toString());
    			if(aspRatio == 0)aspRatio = image.getWidth() / image.getHeight();
    			table.put(file.getName(), new ImageStorage(file.getName(), image));
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
		ImageStorage image = actors.get(fileName);
		return image != null ? image.getImage() : null;
	}
	
	public Hashtable<String, ImageStorage> getSprite() {
		return actors;
	}

	public double getRatio() {
		// TODO Auto-generated method stub
		return 0;
	}
}
