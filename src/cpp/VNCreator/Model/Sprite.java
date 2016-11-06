package cpp.VNCreator.Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.DoublePropertyBase;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class Sprite {

	private Point2D startingPos;
	private Point2D endingPos;
	private DoubleProperty curX;
	private DoubleProperty curY;
	private boolean flip;
	
	private Image image;
	
	
	public Sprite(Image image, Point2D startingPos, Point2D endingPos, boolean flip) {
		this.image = image;
		this.startingPos = startingPos;
		this.endingPos = endingPos;
		this.flip = flip;
		setStartPos();
	}
	
	public Image getImage(){
		return image;
	}

	public void setStartPos() {
		setCurX(startingPos.getX());
		setCurY(startingPos.getY());
	}
	
	public void setCurX(double value) {
		curXProperty().set(value);
	}

	public void setCurY(double value) {
		curYProperty().set(value);
	}

	public double getCurX() {
		return curX == null ? 0.0 : curX.get();
	}

	public double getCurY() {
		return curY == null ? 0.0 : curY.get();
	}

	public boolean isFlipped(){
		return flip;
	}
	
	public DoubleProperty curXProperty() {
		if(curX == null){
			curX = new DoublePropertyBase(){
								
				                @Override
				                public Object getBean() {
				                    return Sprite.this;
				                }
				
				                @Override
				                public String getName() {
				                    return "curX";
				                }
				            };
		}
		return curX;
	}

	public DoubleProperty curYProperty() {
		if(curY == null){
			curY = new DoublePropertyBase(){
								
				                @Override
				                public Object getBean() {
				                    return Sprite.this;
				                }
				
				                @Override
				                public String getName() {
				                    return "curY";
				                }
				            };
		}
		return curY;
	}
}
