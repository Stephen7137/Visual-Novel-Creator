package cpp.VNCreator.Node;

import java.util.ArrayList;

import cpp.VNCreator.Model.Sprite;
import javafx.geometry.Point2D;

public class Scene {
	
	String background;
	ArrayList<Sprite> sprites;
	
	public Scene(){
		sprites = new ArrayList<Sprite>();
	}
	
	public String getBackground(){
		return background;
	}
	
	public void setBackground(String background){
		this.background = background;
	}
	
	public ArrayList<Sprite> getSprites(){
		return sprites;
	}
	//TODO
//	}
//	
//	public void setSprites(String ){
//		this.background = background;
//	}
	
	public void addActor(String Filename, int x1, int y1,
			int x2,	int y2,	long start, long duration){
		//TODO
//		Sprite actor = new Sprite(Filename, new Point2D(x1, y1),
//				new Point2D(x2,	y2), start,	duration);
//		sprites.add(actor);
	}
}
