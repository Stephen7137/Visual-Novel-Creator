package cpp.VNCreator.Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.DoublePropertyBase;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class Sprite {

	private double startX;
	private double startY;
	private DoubleProperty curX;
	private DoubleProperty curY;
	private boolean flip;
	
	private Image image;
	
	
	public Sprite(Image image, double startX, double startY, boolean flip) {
		this.image = image;
		this.startX = startX;
		this.startY = startY;
		this.flip = flip;
		setStartPos();
	}
	
	public Image getImage(){
		return image;
	}

	public void setStartPos() {
		setCurX(startX);
		setCurY(startY);
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
