package cpp.VNCreator.Node;

import cpp.VNCreator.Model.NodeType.nodeType;

/**
 * The main structure of each Text node. Keeps track of text and the
 * children and the parents they are connected to.
 * 
 * @author Stephen Jackson
 *
 */
public class Text extends Node{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2593210407261116915L;
	
	private Node child;
		
	public Text(int id){
		super(id, nodeType.Text);
	}
	
	public Node getChild() {
		return child;
	}
	
	public void setChild(Node node) {
		child = node;		
	}
	
	public void deleteChild(){
		child = null;
	}

	@Override
	public boolean hasChild() {
		return child != null;
	}
}
