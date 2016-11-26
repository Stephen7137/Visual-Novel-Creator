package cpp.VNCreator.Node;

import java.io.Serializable;
import java.util.ArrayList;

import javafx.scene.paint.Color;

public class Scene implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6765865246349845321L;
	String background;
	ArrayList<Actor> layers;
	
	Color mask;
	double alpha;
	Color text;
	String font;
	double size;
	
	String textBackground;
	double textX;
	double textY;
	double textXOffset;
	double textYOffset;
		
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
	
	public void setTextColor(Color color){
		text = color;
	}
	
	public Color getTextColor(){
		return text;
	}
	
	public void setMaskColor(Color color){
		mask = color;
	}
	
	public Color getMaskColor(){
		return mask;
	}
}
