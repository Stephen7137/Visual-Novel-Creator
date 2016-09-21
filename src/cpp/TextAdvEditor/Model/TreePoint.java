package cpp.TextAdvEditor.Model;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * A simple pointer that keeps track of the location and the
 * points that it is connected to. 
 * 
 * @author Stephen Jackson
 *
 */
public class TreePoint implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6720937507684278878L;
	private int id;
	private SimpPoint2D point;
	private ArrayList<TreePoint> parent;
	private ArrayList<TreePoint> child;
	
	public TreePoint(double x, double y, int id){
		point = new SimpPoint2D(x,y);
		parent = new ArrayList<TreePoint>();
		child = new ArrayList<TreePoint>();
		this.id = id;
	}
	
	public int getID(){
		return id;
	}
	
	public double getX(){
		return point.getX();
	}
	
	public double getY(){
		return point.getY();
	}
	
	public ArrayList<TreePoint> getChild(){
		return child;
	}
	
	public TreePoint getChild(int j){
		return child.get(j);
	}
	
	public ArrayList<TreePoint> getParent(){
		return parent;
	}

	public void setXY(double x, double y) {
		point.setPoint(x, y);	
	}

	public int childsize() {
		return child.size();
	}

	public void addChild(TreePoint cNode) {
		child.add(cNode);
	}
	
	public void addParent(TreePoint pNode) {
		parent.add(pNode);
	}

	public boolean childEmpty() {
		return child.isEmpty();
	}

	public int getParentSize() {
		return parent.size();
	}

	public TreePoint getParent(int j) {
		return parent.get(j);
	}

	public double distance(double x, double y) {
		return point.distance(x, y);
	}

	public void removeChild(TreePoint cNode) {
		child.remove(cNode);
	}

	public void removeParent(TreePoint pNode) {
		parent.remove(pNode);
	}

	public void delete() {
		child = null;
		parent = null;		
	}
}
