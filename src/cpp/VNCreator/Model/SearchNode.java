package cpp.VNCreator.Model;

public class SearchNode {
	private String text;
	private int id;
	
	public SearchNode(String text,int id){
		this.id = id;
		this.text = text;
	}

	public String getText(){
		return text;
	}
	
	public int getID(){
		return id;
	}
}
