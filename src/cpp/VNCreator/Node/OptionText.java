package cpp.VNCreator.Node;

public class OptionText {
	private String text;
	private Node node;
	
	public OptionText(String text){
		this.text = text;
	}
	
	public void setText(String text){
		this.text = text;
	}
	
	public String getText(){
		return text;
	}
	
	public void setNode(Node node){
		this.node = node;
	}
	
	public Node getNode(){
		return node;
	}
	
	public boolean isEmpty() {
		if(text.equals("")) return false;
		return true;
	}
	
	public int getID(){
		return node.getID();
	}
}
