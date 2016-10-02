package cpp.VNCreator.Model;

import java.util.ArrayList;

import cpp.VNCreator.Model.NodeType.nodeType;
import cpp.VNCreator.Node.Node;
import cpp.VNCreator.Node.Option;
import cpp.VNCreator.Node.OptionText;
import cpp.VNCreator.Node.Text;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
/**
 * A simple pointer that keeps track of the location and the
 * points that it is connected to. 
 * 
 * @author Stephen Jackson
 *
 */
public class TreePoint {

	private Point2D point;
	private Node node;
	
	public TreePoint(double x, double y, Node node){
		point = new Point2D(x,y);
		this.node = node;
	}
	
	public int getID(){
		return node.getID();
	}
	
	public double getX(){
		return point.getX();
	}
	
	public double getY(){
		return point.getY();
	}
	
	public ArrayList<CVNode> getChildren(){
		ArrayList<CVNode> children = null;
		if(node.hasChild()){
			children = new ArrayList<CVNode>();
			if(node.getType() == nodeType.Option){
				ArrayList<OptionText> oText = ((Option)node).getChildren();
				for(OptionText text : oText){
					children.add(new CVNode(text.getText(), text.getID()));
				}
			}else{
				children.add(new CVNode("", ((Text)node).getChild().getID()));
			}
		}
		
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
}
