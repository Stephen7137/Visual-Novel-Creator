package cpp.VNCreator.Node;

import java.util.ArrayList;

import cpp.VNCreator.Model.NodeType.nodeType;

public class Option extends Node{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6521169399420534835L;
	
	private ArrayList<OptionText> child = null;

	public Option(int id) {
		super(id, nodeType.Option);
		child = new ArrayList<OptionText>();
	}
	
	public void addChild(){
		child.add(new OptionText());
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

	public void deleteChild(int id) {
		for(OptionText node : child){
			if(node.getID() == id) node.setNode(null);
		}
	}

	public void setChild(Node node, int id) {
		child.get(id).setNode(node);		
	}
}
