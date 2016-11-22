package cpp.VNCreator.Node;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

import cpp.VNCreator.Model.NodeType.nodeType;

public abstract class Node implements Serializable {
		
		private static final long serialVersionUID = 6064370901268149364L;
		private String title;
		private String text;
		private nodeType type;
		private Scene scene;
		
		private Hashtable<Integer,Node> parent;
		private int id;
			
		public Node(int id, nodeType type){
			title = "";
			text = "";
			this.id = id;
			this.type = type;
			scene = new Scene();
			parent = new Hashtable<Integer,Node>();
		}
		
		public int getID(){
			return id;
		}
		
		public nodeType getType(){
			return type;
		}
		
		public void setTitle(String title){
			this.title = title;
		}
		
		public String getTitle(){
			return title;
		}
		
		public void setText(String text){
			this.text = text;
		}
		
		public String getText(){
			return text;
		}
		
		public void setText(String title, String text){
			setTitle(title);
			setText(text);
		}
		
		public void addParent(Node node){
			parent.put(node.getID(), node);
		}
		
		public ArrayList<Node> getParents(){
			return new ArrayList<Node>(parent.values());
		}
		
		public void removeParent(int id){
			parent.remove(id);
		}
		
		public void removeParent(Node node){
			removeParent(node.getID());
		}
		
		public boolean hasParents(){
			return parent.size() != 0;
		}

		public Scene getScene() {
			return scene;
		}
		
		public void setScene(Scene scene) {
			this.scene = scene;
		}
		
		public boolean isEmpty() {
			if(!title.equals("") || !text.equals("")) return false;
			return true;
		}
		
		public abstract boolean hasChild();
}
