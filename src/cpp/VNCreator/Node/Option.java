package cpp.VNCreator.Node;

import java.util.ArrayList;

import cpp.VNCreator.Model.NodeType.nodeType;

public class Option extends Node{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6521169399420534835L;
	
	private ArrayList<OptionText> child = null;

	public Option(int id, nodeType type) {
		super(id, type);
		child = new ArrayList<OptionText>();
	}
	
	public void addChild(String text){
		child.add(new OptionText(text));
	}
	
	public void removeChild(OptionText node){
		child.remove(node);
	}
	
	public ArrayList<OptionText> getChildren(){
		return child;
	}
	
	public void setChildren(ArrayList<OptionText> children){
		child = children;
	}
	
	public int numChildren() {
		return child.size();
	}

	@Override
	public boolean hasChild() {
		for(OptionText text : child){
			if(text.getNode() != null) return true;
		}
		return false;
	}
}
