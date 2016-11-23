package cpp.VNCreator.Node;

import java.io.Serializable;
import java.util.ArrayList;

public class Scene implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6765865246349845321L;
	String background;
	ArrayList<Actor> layers;
	double textX;
	double textY;
	
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

	public void setTextX(double textX) {
		this.textX = textX;
	}

	public void setTextY(double textY) {
		this.textY = textY;
	}
	
	public double getTextX() {
		return textX;
	}

	public double getTextY() {
		return textY;
	}
}
