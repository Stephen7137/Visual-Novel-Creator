package cpp.VNCreator.Node;

import java.io.Serializable;
import java.util.ArrayList;

import cpp.VNCreator.Model.Sprite;
import javafx.geometry.Point2D;

public class Scene implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6765865246349845321L;
	String background;
	ArrayList<Actor> layers;
	
	public Scene(){
		background = "";
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
