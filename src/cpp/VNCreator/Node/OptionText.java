package cpp.VNCreator.Node;

public class OptionText {
	private String title;
	private String text;
	private Node node;
	
	public OptionText(){
		this("","");
	}
	
	public OptionText(String title, String text){
		this.title = title;
		this.text = text;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public String getTitle(){
		return title;
	}
	
	public void setText(String title, String text){
		this.title = title;
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
		return node != null ? node.getID() : -1;
	}
}
