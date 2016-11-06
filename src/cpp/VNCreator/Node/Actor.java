package cpp.VNCreator.Node;

public class Actor {
	
	String imageName;
	double startX;
	double startY;
	double endX;
	double endY;
	long duration;
	long delay;
	boolean flip;
	
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
	
	public String getName(){
		return imageName;
	}
	
	public boolean isFlipped(){
		return flip;
	}
	
}
