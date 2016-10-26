package cpp.VNCreator.Model;

import java.io.Serializable;
import java.util.Hashtable;

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
	private Node start;
	private Hashtable<Integer, Node> tree;
	
	public Story(){
		tree = new Hashtable<Integer, Node>();
	}
	
	public Story(Node start, Hashtable<Integer, Node> tree ){
		this.start = start;
		this.tree = tree;
	}
	
	public void setTree(Hashtable<Integer, Node> newTree){
		tree = newTree;
	}
	
	public Hashtable<Integer, Node> getTree(){
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

	public Node getNode(int i) {
		return tree.get(i);
	}
}
