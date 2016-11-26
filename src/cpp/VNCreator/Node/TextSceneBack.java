package cpp.VNCreator.Node;

public class TextSceneBack {
	
	String textBackground;
	double textX;
	double textY;
	double textXOffset;
	double textYOffset;
	
	public void setTextBackground(String textBackground){
		this.textBackground = textBackground;
	}
	
	public String getTextBackground(){
		return textBackground;
	}

	public void setTextX(double textX) {
		this.textX = textX;
	}
	
	public double getTextX() {
		return textX + textXOffset;
	}

	public void setTextY(double textY) {
		this.textY = textY;
	}
	
	public double getTextY() {
		return textY + textYOffset;
	}
	
	public void setSceneX(double sceneX){
		textX = sceneX;
	}
	
	public double getSceneX() {
		return textXOffset;
	}
	
	public void setSceneY(double sceneY){
		textY = sceneY;
	}
	

	public double getSceneY() {
		return textYOffset;
	}	
}
