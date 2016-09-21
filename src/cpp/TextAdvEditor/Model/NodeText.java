package cpp.TextAdvEditor.Model;

/**
 * Carry for text and text id to be used
 * else wear and returned. Used for changing
 * text in the nodes.
 * 
 * @author Stephen Jackson
 *
 */
public class NodeText {
	
	private String text;
	private int ID;
	
	public NodeText(String text,int  ID){
		this.text = text;
		this.ID = ID;
	}
	
	public String getText(){
		return text;
	}
	
	public void setText(String text){
		this.text = text;
	}
	
	public int getID(){
		return ID;
	}
}
