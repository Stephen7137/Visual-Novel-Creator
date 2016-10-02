package cpp.VNCreator.Model;

import java.io.Serializable;
import java.util.ArrayList;

import cpp.VNCreator.Node.Node;

/**
 * Holds all chapters and the 
 * title of the story as well as
 * point to the starting chapter;
 * 
 * @author Stephen Jackson
 *
 */
public class Story implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1561701032116116746L;
	private String strName;
	private Node start;
	private ArrayList<Node> tree;
	
	public Story(){
		tree = new ArrayList<Node>();
	}
	
	public void setName(String strName){
		this.strName = strName;
	}
	
	public String getName(){
		return strName;
	}
	
	public void setTree(ArrayList<Node> newTree){
		tree = newTree;
	}
	
	public ArrayList<Node> getTree(){
		return tree;
	}
	
	public int numOfNode(){
		return tree.size();
	}
	
	public void setStart(Node start){
		this.start = start;
	}
	
	public Node getStart(){
		return start;
	}
}
