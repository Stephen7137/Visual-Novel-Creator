package cpp.VNCreator.Node;

import java.io.Serializable;
import java.util.ArrayList;

import javafx.scene.paint.Color;

public class Scene extends TextSceneBack implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6765865246349845321L;
	String background;
	ArrayList<Actor> layers;
	
	Color mask;
	Color text;
	String font;
	double size;
		
	public Scene(){
		background = "";
		layers = new ArrayList<Actor>();
	}
	
	public String getFont(){
		return font;
	}
	
	public void setFont(String font){
		this.font = font;
	}
	
	public double getFontSize(){
		return size;
	}
	
	public void setFontSize(double size){
		this.size = size;
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
