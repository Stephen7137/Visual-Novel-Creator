package cpp.VNCreator.Model;

import javafx.geometry.Point2D;

public class CVNode {
	
	public String title;
	public String text;
	public int id;
	public Point2D point;
	
	public CVNode(String title, String text, int id){
		this.title = title;
		this.text = text;
		this.id = id;		
	}
}
