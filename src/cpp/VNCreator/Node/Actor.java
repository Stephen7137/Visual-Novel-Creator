package cpp.VNCreator.Node;

import java.io.Serializable;

public class Actor implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4208159977759802893L;
	String imageName;
	double startX;
	double startY;
	double endX;
	double endY;
	long duration;
	long delay;
	boolean flip;
	
	public Actor(){
		imageName = "";
	}
	
	public double getStartX(){
		return startX;
	}
	
	public void setStartX(double value){
		startX = value;
	}
	
	public double getStartY(){
		return startY;
	}
	
	public void setStartY(double value){
		startY = value;
	}
	
	public double getEndX(){
		return endX;
	}
	
	public void setEndX(double value){
		endX = value;
	}
	
	public double getEndY(){
		return endY;
	}
	
	public void setEndY(double value){
		endY = value;
	}
	
	public long getDuration(){
		return duration;
	}
	
	public void setDuration( long value){
		duration = value;
	}
	
	public long getDelay(){
		return delay;
	}
	
	public void setDelay( long value){
		delay = value;
	}
	
	public String getName(){
		return imageName;
	}
	
	public void setName(String name){
		imageName = name;
	}
	
	public boolean isFlipped(){
		return flip;
	}
	
	public void setFlip(Boolean value){
		flip = value;
	}
}
