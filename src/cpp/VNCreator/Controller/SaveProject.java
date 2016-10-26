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
	
	public List<SaveTreePoint> canvas;
	public List<SaveText> text;
	public List<SaveOption> option;
	public List<Integer> bookmark;	
	public int start;
	
	public SaveProject(int start, ArrayList<Node> tree, ArrayList<Node> node, ArrayList<TreePoint> treePoint){
		this.start = start;
		createTree(tree);
		createBookmark(node);
		createCanvas(treePoint);
	}
	
	public void createTree(ArrayList<Node> node){
		text =  new ArrayList<SaveText>();
		option = new ArrayList<SaveOption>();	
		
		for(Node tmp : node){
			if(tmp.getType() == nodeType.Option){
				option.add(new SaveOption(tmp.getTitle(), tmp.getText(), tmp.getType(),
						tmp.getID(), tmp.getParents(), ((Option)tmp).getChildren()));
			}else if(tmp.getType() == nodeType.Text){
				text.add(new SaveText(tmp.getTitle(), tmp.getText(), tmp.getType(),
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
		canvas = new ArrayList<SaveTreePoint>();
		for(TreePoint tmp : treePoint){
			canvas.add(new SaveTreePoint(tmp.getX(), tmp.getY(), tmp.getHeight(),
					tmp.getWidth(), tmp.getColor(), tmp.getID()));
		}	
	}
	
	public ArrayList<SaveNode> getTree(){
		ArrayList<SaveNode> tree = new ArrayList<SaveNode>();
		for(SaveText node : text){
			tree.add(node);
		}
		for(SaveOption node : option){
			tree.add(node);
		}
		return tree;
	}
	
	public class SaveNode implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public String title;
		public String text;
		public int id;
		public nodeType type;
		public List<Integer> parent;
		
		public SaveNode(String title, String text, nodeType type,
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
	
	public class SaveText extends SaveNode{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public int child;
		
		public SaveText(String title, String text, nodeType type, int id, ArrayList<Node> parent, int child) {
			super(title, text, type, id, parent);
			this.child = child;
		}	
	}
	
	public class SaveOption extends SaveNode{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public List<SaveOptionText> children;
		
		public SaveOption(String title, String text, nodeType type, int id, ArrayList<Node> parent, ArrayList<OptionText> child) {
			super(title, text, type, id, parent);
			children = new ArrayList<SaveOptionText>();
			for(OptionText tmp : child){
				children.add(new SaveOptionText(tmp.getTitle(), tmp.getTitle(), tmp.getID()));
			}	
		}
	}
	
	public class SaveOptionText implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public String title;
		public String text;
		public int id;
		
		public SaveOptionText(String title, String text, int id){
			this.title = title;
			this.text = text;
			this.id = id;
		}
	}
	
	class SaveTreePoint implements Serializable{
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
		
		public SaveTreePoint(double x, double y, double height, double width, Color color, int node){
			this.x = x;
			this.y = y;
			this.height = height;
			this.width = width;
			this.color = color;
			this. node = node;
		}
	}
}
