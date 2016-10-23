package cpp.VNCreator.Controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cpp.VNCreator.Model.TreePoint;
import cpp.VNCreator.Model.NodeType.nodeType;
import cpp.VNCreator.Node.Node;
import cpp.VNCreator.Node.Option;
import cpp.VNCreator.Node.OptionText;
import cpp.VNCreator.Node.Text;
import javafx.scene.paint.Color;

/**
 * SaveProject is used for saving the current
 * state that the canvas, story tree, and editor.
 * Saves info to a file .project and selected 
 * location.
 * @author Stephen Jackson
 *
 */
public class SaveProject implements Serializable{
	
	protected static final long serialVersionUID = 232323;
	
	public List<saveTreePoint> canvas;
	public List<saveNode> tree;
	public List<Integer> bookmark;	
	public int start;
	
	public SaveProject(int start, ArrayList<Node> tree, ArrayList<Node> node, ArrayList<TreePoint> treePoint){
		createStart(start);
		createTree(tree);
		createBookmark(node);
		createCanvas(treePoint);
	}
	
	public void createStart(int start){
		this.start = start;
	}
	
	public void createTree(ArrayList<Node> node){
		tree = new ArrayList<saveNode>();
		for(Node tmp : node){
			if(tmp.getType() == nodeType.Option){
				tree.add(new saveOption(tmp.getTitle(), tmp.getText(), tmp.getType(),
						tmp.getID(), tmp.getParents(), ((Option)tmp).getChildren()));
			}else if(tmp.getType() == nodeType.Text){
				tree.add(new saveText(tmp.getTitle(), tmp.getText(), tmp.getType(),
						tmp.getID(), tmp.getParents(), ((Text)tmp).getChildId()));
			}
			
		}
	}
	
	public void createBookmark(ArrayList<Node> node){
		bookmark = new ArrayList<Integer>();
		for(Node tmp : node){
			bookmark.add(tmp.getID());
		}	
	}
	
	public void createCanvas(ArrayList<TreePoint> treePoint){
		canvas = new ArrayList<saveTreePoint>();
		for(TreePoint tmp : treePoint){
			canvas.add(new saveTreePoint(tmp.getX(), tmp.getY(), tmp.getHeight(),
					tmp.getWidth(), tmp.getColor(), tmp.getID()));
		}	
	}
	
	class saveNode implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public String title;
		public String text;
		public int id;
		public nodeType type;
		public List<Integer> parent;
		
		public saveNode(String title, String text, nodeType type,
				int id, ArrayList<Node> parent){
			this.id = id;
			this.title = title;
			this.text = text;
			this.type = type;
			this.parent = new ArrayList<Integer>();
			for(Node tmp : parent){
				this.parent.add(tmp.getID());
			}			
		}
	}
	
	class saveText extends saveNode{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public int child;
		
		public saveText(String title, String text, nodeType type, int id, ArrayList<Node> parent, int child) {
			super(title, text, type, id, parent);
			this.child = child;
		}	
	}
	
	class saveOption extends saveNode{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public List<saveOptionText> children;
		
		public saveOption(String title, String text, nodeType type, int id, ArrayList<Node> parent, ArrayList<OptionText> child) {
			super(title, text, type, id, parent);
			children = new ArrayList<saveOptionText>();
			for(OptionText tmp : child){
				children.add(new saveOptionText(tmp.getTitle(), tmp.getTitle(), tmp.getID()));
			}	
		}
	}
	
	class saveOptionText implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public String title;
		public String text;
		public int id;
		
		public saveOptionText(String title, String text, int id){
			this.title = title;
			this.text = text;
			this.id = id;
		}
	}
	
	class saveTreePoint implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public double x;
		public double y;
		public double height;
		public double width;
		public Color color;
		public int node;
		
		public saveTreePoint(double x, double y, double height, double width, Color color, int node){
			this.x = x;
			this.y = y;
			this.height = height;
			this.width = width;
			this.color = color;
			this. node = node;
		}
	}
}
