package cpp.VNCreator.Node;

import cpp.VNCreator.Model.NodeType.nodeType;

public class Option extends Node{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6521169399420534835L;
	private OptionText[] child = null;

	public Option(int id, nodeType type) {
		super(id, type);
	}
	
	public void addChild(String text){
		if(child == null){
			child = new OptionText[1];
			child[0] = new OptionText("");
		}
	}
	
	public int numChildren() {
		return child.length;
	}
}
