package cpp.VNCreator.Model;

import java.io.Serializable;
import java.util.ArrayList;

import cpp.VNCreator.Node.Node;

/**
 * Chapter holds all of the text nodes and the
 * starting node.
 * 
 * @author Stephen Jackson
 *
 */
public class Chapter implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8684899390329736855L;
	private String chName;
	private int ID;
	private Node start;
	private ArrayList<Node> tree;
	
	public Chapter(int ID){
		tree = new ArrayList<Node>();
		this.ID = ID;
	}
	
	public void setCHName(String chName){
		this.chName = chName;
	}
	
	public String getCHName(){
		return chName;
	}
	
	public int getID(){
		return ID;
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
