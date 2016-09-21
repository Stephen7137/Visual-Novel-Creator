package cpp.TextAdvEditor.Model;

import java.io.Serializable;
import java.util.ArrayList;

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
	private Text start;
	private ArrayList<Text> tree;
	
	public Chapter(int ID){
		tree = new ArrayList<Text>();
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
	
	public void setTree(ArrayList<Text> newTree){
		tree = newTree;
	}
	
	public ArrayList<Text> getTree(){
		return tree;
	}
	
	public int numOfNode(){
		return tree.size();
	}
	
	public void setStart(Text start){
		this.start = start;
	}
	
	public Text getStart(){
		return start;
	}
}
