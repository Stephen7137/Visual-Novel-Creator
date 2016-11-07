package cpp.VNCreator.Node;

import java.util.ArrayList;

import cpp.VNCreator.Model.Sprite;
import javafx.geometry.Point2D;

public class Scene {
	
	String background;
	ArrayList<Actor> layers;
	
	public Scene(){
		layers = new ArrayList<Actor>();
	}
	
	public String getBackground(){
		return background;
	}
	
	public void setBackground(String background){
		this.background = background;
	}
	
	public ArrayList<Actor> getLayers(){
		return layers;
	}
}
