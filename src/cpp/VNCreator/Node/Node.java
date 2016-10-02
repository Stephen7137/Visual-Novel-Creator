package cpp.VNCreator.Node;

import java.io.Serializable;
import java.util.Enumeration;
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
		
		public void addParent(Node node){
			parent.put(node.getID(), node);
		}
		
		public Enumeration<Node> getParents(){
			return parent.elements();
		}
		
		public void removeParent(int id){
			parent.remove(id);
		}
		
		public void removeParent(Node node){
			removeParent(node.getID());
		}

		public Scene getScene() {
			return scene;
		}
		
		public boolean isEmpty() {
			if(title.equals("")) return false;
			if(text.equals("")) return false;
			return true;
		}
		
		public abstract boolean hasChild();
}
