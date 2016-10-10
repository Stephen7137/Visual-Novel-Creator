package cpp.VNCreator.Model;

import java.util.ArrayList;

import cpp.VNCreator.Model.NodeType.nodeType;
import cpp.VNCreator.Node.Node;
import cpp.VNCreator.Node.Option;
import cpp.VNCreator.Node.OptionText;
import cpp.VNCreator.Node.Text;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
/**
 * A simple pointer that keeps track of the location and the
 * points that it is connected to. 
 * 
 * @author Stephen Jackson
 *
 */
public class TreePoint {

	private final int radius = 10;
	private Point2D point;
	private double height;
	private double width;
	private Color color;
	private Node node;
	private ArrayList<CVNode> children;
	
	public TreePoint(double x, double y, double height, double width,
			Color color, Node node){
		this.height = height;
		this.width = width;
		this.color = color;
		point = new Point2D(x,y);
		this.node = node;
	}
	
	public int getID(){
		return node.getID();
	}
	
	public double getWidth(){
		return width;
	}
	
	public double getHeight(){
		return height;
	}
	
	public Color getColor(){
		return color;
	}
	
	public double getX(){
		return point.getX();
	}
	
	public double getY(){
		return point.getY();
	}
	
	public double getBottom(){
		return point.getY() + height;
	}
	
	public double getRight(){
		return point.getX() + width;
	}
	
	public double getHalfTop(){
		return point.getX() + width/2;
	}
	
	public double getHalfLeft(){
		return point.getY() + height/2;
	}
	
	public int getRadious(){
		return radius;
	}
	
	public ArrayList<CVNode> getChildren(){
		ArrayList<CVNode> children = new ArrayList<CVNode>();
		//TODO look into not repeating.
		if(node.hasChild()){
			if(node.getType() == nodeType.Option){
				ArrayList<OptionText> oText = ((Option)node).getChildren();
				for(OptionText text : oText){
					children.add(new CVNode(text.getTitle(),
							text.getText(), text.getID()));
				}
			}else{
				children.add(new CVNode("", "", ((Text)node).getChild().getID()));
			}
		}else{
			if(node.getType() == nodeType.Option){
				ArrayList<OptionText> oText = ((Option)node).getChildren();
				for(OptionText text : oText){
					children.add(new CVNode(text.getTitle(), text.getText(), -1));
				}
			}else{
				children.add(new CVNode("", "", -1));
			}
		}
		this.children = children;
		return children;
	}

	public void setXY(double x, double y) {
		point = new Point2D(x, y);	
	}

	public double distance(double x, double y) {
		return point.distance(x, y);
	}
	
	public String getTitle(){
		return node.getTitle();
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public boolean collition(double x, double y){
		if(x >= point.getX() && y >= point.getY() 
				&& x <= width + point.getX() 
				&& y <= height + point.getY()) return true;
		return false;
	}

	public void subXY(double vecX, double vecY) {
		point = point.subtract(vecX, vecY);
	}

	public int onChild(double x, double y) {
		if(children != null){
			for(int i = 0; i < children.size(); i++){
				if(children.get(i).point == null) return -1;//TODO
				if(children.get(i).point.distance(x, y) < radius) return i;
			}
		}		
		return -1;
	}
}
