package cpp.VNCreator.Node;

public abstract class TextSceneBack {
	
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

	public void setTextX(double textXOffset) {
		this.textXOffset = textXOffset;
	}
	
	public double getTrueTextX() {
		return textX + textXOffset;
	}
	
	public double getTextX() {
		return textXOffset;
	}

	public void setTextY(double textYOffset) {
		this.textYOffset = textYOffset;
	}
	
	public double getTrueTextY() {
		return textY + textYOffset;
	}
	
	public double getTextY() {
		return textYOffset;
	}
	
	public void setSceneX(double sceneX){
		textX = sceneX;
	}
	
	public double getSceneX() {
		return textX;
	}
	
	public void setSceneY(double sceneY){
		textY = sceneY;
	}
	

	public double getSceneY() {
		return textY;
	}	
}
