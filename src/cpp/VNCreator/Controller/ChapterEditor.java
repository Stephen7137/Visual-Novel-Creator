package cpp.VNCreator.Controller;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;
import java.util.Stack;

import cpp.VNCreator.Model.NodeType.nodeType;
import cpp.VNCreator.Node.Node;
import cpp.VNCreator.Node.Option;
import cpp.VNCreator.Node.OptionText;
import cpp.VNCreator.Node.Text;

/**
 * ChapterEditor manages all of the nodes and how they
 * interact with each other. Nodes text are able to be
 * changed and be add as child or parent to other node.
 * nodes are also able to be created or deleted.
 * 
 * @author Stephen Jackson
 *
 */
public class ChapterEditor{
	
	private ArrayList<Node> noParent;
	private ArrayList<Node>	noChild;
	private ArrayList<Node>	bookmark;
	private Hashtable<Integer,Node> tree;
	private Node current;
	private Node selected;
	private Stack<Node> memory;
	private Stack<Node> selectMemory;
	private Stack<Node> forwardMemory;
	Random keyGen;
	
	private Node start;
	
	/**
	 * Sets up the default variable and updates the text.
	 * @param chapterID
	 * @param tree
	 */
	public ChapterEditor(Hashtable<Integer,Node> tree){
		memory = new Stack<Node>();
		selectMemory = new Stack<Node>();
		forwardMemory = new Stack<Node>();
		keyGen = new Random();
		this.tree = tree;
		current = null;
		selected = null;
		bookmark = new ArrayList<Node>();
		noParent = new ArrayList<Node>();
		noChild = new ArrayList<Node>();
	}
	
	public int connect(int id, int child){
		if(id == -1) return id;
		Node node = tree.get(id);
		if(selected.getType() == nodeType.Option){
			((Option)selected).setChild(node, child);
			node.addParent(selected);
		}else{
			((Text)selected).setChild(node);
			node.addParent(selected);
		}
		
		return node.getID();
	}

	public void disconnect(int id, int index) {
		selected = tree.get(id);
		if(selected.getType() == nodeType.Option){
			((Option)selected).getChild(index).removeParent(selected.getID());
			((Option)selected).removeConnection(index);
		}else{
			((Text)selected).getChild().removeParent(selected.getID());
			((Text)selected).deleteChild();
		}		
	}
	
	/**
	 * delete removes selected node from the tree and removes the node from the
	 * parent list from child nodes and removes itself from child list in 
	 * parent nodes.
	 * @param id 
	 */
	public void delete(int id){
		if(selected != null && selected.getID() == id) selected = null;
		Node node = tree.get(id);
		if(node != null){
			if(node.hasChild()){
				if(node.getType() == nodeType.Option){
					for( OptionText text : ((Option)node).getChildren()){
						if(text.getNode() != null)text.getNode().removeParent(node.getID());
					}
				}else{
					((Text)node).getChild().removeParent(node.getID());;
				}
			}else{
				noChild.remove(node);
			}
			
			if(node.hasParents()){
				ArrayList<Node> parent = node.getParents();
				for(Node tmp : parent){
					if(tmp.getType() == nodeType.Option){
						((Option)tmp).deleteChild(node.getID());
					}else{
						((Text)tmp).deleteChild();
					}
				}
			}else{
				noParent.remove(node);
			}
			
			tree.remove(id);
		}
	}
	
	public void addBookmark(){
		bookmark.add(selected);
	}
	
	public void removeBookmark(){
		bookmark.remove(selected);
	}
	
	/**
	 * next continues down the tree with the given input and then
	 * update Text. Only update if input is a valid entry.
	 * @param input
	 * @return true if input was valid
	 */
	public boolean next(int input){
		boolean next = false;
		if(current != null) current = start;
		if(current != null && input >= 0){
			if(current.getType() == nodeType.Option){
				Option option = (Option)current;
				if(option.numChildren() < input){
					
					next = true;
				}
			}else{
				Text text = (Text)current;
				current = text.getChild();
				memory.push(current);
				next = true;
			}
		}		
		return next;
	}
	
	public boolean nextSelect(int input){
		forwardMemory = new Stack<Node>();
		if(selected != null && selected.hasChild()){
			selectMemory.push(selected);
			if(selected.getType() == nodeType.Option){
				selected = ((Option)selected).getChild(input);
			}else{
				selected = ((Text)selected).getChild();
			}
			return true;
		}
		return false;
	}
	
	public boolean backSelect(){
		if(!selectMemory.isEmpty()){
			forwardMemory.push(selected);
			selected = selectMemory.pop();
			return true;
		}
		return false;
	}
	
	public boolean forwardSelect(){
		if(!forwardMemory.isEmpty()){
			selectMemory.push(selected);
			selected = forwardMemory.pop();
			return true;
		}
		return false;
	}
	
	/*
	 * back reverts the call next up to a predetermined limit.
	 */
	public boolean back(){
		
		if(memory.peek() != null){
			current = memory.pop();
		}		
		return true;
	}

	/**
	 * createText creates a new node and then adds it to
	 * arrayList tree, noParents, and noChild
	 * @return key of the new node.
	 */
	public Node createText() {
		Text newText = new Text(createKey());
		createNode(newText);
		return newText;
	}
	
	public Node createOption() {
		Option option = new Option(createKey());
		createNode(option);
		return option;
	}
	
	private void createNode(Node node){
		if(tree.size() == 0){
			start = node;
		}else{
			noParent.add(node);
		}
		noChild.add(node);
		tree.put(node.getID(), node);
		selected = node;
	}
	
	private int createKey(){
		return keyGen.nextInt(10000);
	}
	
	public int getKey(){
		if(current == null) return -1;
		return current.getID();
	}
	
	public ArrayList<Node> getBookMark() {
		return createList(bookmark);
	}

	public ArrayList<Node> getNoChildNode() {
		return createList(noChild);
	}

	public ArrayList<Node> getNoParentNode() {
		return createList(noParent);
	}
	
	public ArrayList<Node> getAllNode() {
		return new ArrayList<Node>(tree.values());
	}
	
	/**
	 * createsList collects all titles and id in arrayList and
	 * and returns a arrayList
	 * @param noParent2
	 * @return arrayList of NodeText
	 */
	private ArrayList<Node> createList(ArrayList<Node> noParent2){
		ArrayList<Node> textList = new ArrayList<Node>();
		//TODO
//		for(Text text : list){
//			textList.add(new Node(text.getTitle(),text.getID()));
//		}
		return textList;
	}

	public boolean hasNodes() {
		return tree.size() > 0;
	}

	public int currentKey() {
		if(current == null) return -1;
		return current.getID();
	}

	public void setSelected(int id) {
		selected = tree.get(id);
	}

	public Node getSelected() {
		return selected;
	}

	public void setCurrent() {
		current = selected; //TODO check when called and update text.
	}

	public boolean isCurrent() {
		return current == selected;
	}

	public Node saveCurrent() {
		return current;
	}

	public ArrayList<Node> saveNoParent() {
		return noParent;
	}

	public ArrayList<Node> saveNoChild() {
		return noChild;
	}

	public ArrayList<Node> saveBookmark() {
		return bookmark;
	}
	
	public boolean currentBookmark() {
		return bookmark.contains(selected);
	}

	public boolean isNull() {
		return selected == null;
	}
	
	public int getSelectedID() {
		if(selected != null){
			return selected.getID();
		}
		return -1;
	}
	
	public void setStart() {
		start = selected;
		current = start;
	}
	
	public Node getStart() {
		return start;
	}

	/**
	 * validate checks to see if the last text in noParent
	 * is start of if noParent is empty that start is not null.
	 * @return
	 */
	public boolean validate() {
		return ((noParent.size()==1 && noParent.contains(start)) 
				|| (noParent.size()==0 && start!=null));
	}
	
	public Node getCurrentNode(){
		return current;
	}

	public boolean isSelect(int id) {
		if(selected == null) return false;
		return selected.getID() == id;
	}
	public boolean isEmpty(int id) {
		return tree.get(id).isEmpty();
	}
	public void load(Node start, Hashtable<Integer, Node> tree, 
			ArrayList<Node> bookmark) {
		this.start = start;
		this.tree = tree;
		this.bookmark = bookmark;		
	}
}
