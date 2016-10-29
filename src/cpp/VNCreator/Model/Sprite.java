package cpp.VNCreator.Model;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class Sprite {
	private String fileName;
	private long start;
	private long duration;
	private Point2D startingPos;
	private Point2D endingPos;
	private Point2D currentPos;
	private Image image;
	
	
	public Sprite(String fileName, Point2D startingPos, Point2D endingPos, 
			long start, long duration) {
		this.fileName = fileName;
		this.startingPos = startingPos;
		this.endingPos = endingPos;
		this.duration = duration;
		this.start = start;
		// TODO Auto-generated constructor stub
	}
	
	public void move(){
		currentPos = currentPos.add(getVelX(), getVelY());
	}
	
	public void setImage(Image image){
		this.image = image;
	}
	
	public Image getImage(){
		return image;
	}

	private double getVelX(){
		return  ((endingPos.getX() - startingPos.getX())/duration);
	}
	
	private double getVelY(){
		return  ((endingPos.getY() - startingPos.getY())/duration);
	}
	
	public long getStart(){
		return start;
	}
	
	public String getFileName(){
		return fileName;
	}

	public void setStartPos() {
		currentPos = new Point2D(startingPos.getX(), startingPos.getY());
	}

	public double getCurX() {
		return currentPos.getX();
	}

	public double getCurY() {
		return currentPos.getY();
	}
}
