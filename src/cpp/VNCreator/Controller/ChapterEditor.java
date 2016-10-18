package cpp.VNCreator.Controller;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

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
	private Stack memory;
	Random keyGen;
	
	private Node start;
	
	/**
	 * Sets up the default variable and updates the text.
	 * @param chapterID
	 * @param tree
	 */
	public ChapterEditor(Hashtable<Integer,Node> tree){
		memory = new Stack(20);
		keyGen = new Random();
		this.tree = tree;
		current = null;
		selected = null;
		bookmark = new ArrayList<Node>();
		noParent = new ArrayList<Node>();
		noChild = new ArrayList<Node>();
	}
		//TODO
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
	
	/**
	 * disconnect disconnects selected from node with provided key.
	 * checks to make sure if the node is in selected child list and
	 * then deletes it. If selectedNode has no children then it is
	 * add to arrayList noChild.
	 * @param key
	 * @return returns the key of the disconnected node.
	 */
	//TODO
//	public int disconnect(int key){
//		Text node = searchTree(key);
//		selectedNode.removeChild(node);
//		if(selectedNode.getChildSize() == 0 && !noChild.contains(selectedNode)){
//			noChild.add(selectedNode);
//		}
//		node.removeParent(selectedNode);
//		if(node.getParentSize() == 0 && !noParent.contains(node)){
//			noParent.add(node);
//		}
//		return node.getKey();
//	}
	
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
		if(input >= 0){
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
	 * stack holds value Text to be recalled latter.
	 * 
	 * @author Stephen Jackson
	 *
	 */
	class Stack{
		
		Node[] stack;
		int size;
		int max;
		int pointer;
		
		public Stack(int max){
			stack = new Node[max];
			this.max = max;
			size = 0;
			pointer = 0;
		}
		
		/**
		 * push adds text to stack and updates size.
		 * disallows size to be lager than stack size,
		 * but allows text to over write values.
		 * @param text
		 */
		public void push(Node text){
			pointer = (pointer + 1)%stack.length;
			stack[pointer] = text;
			if(size < max){
				size++;
			}else{
				size = max;
			}
		}
		
		/**
		 * removes top of the stack and sets to null.
		 * size is reduced to 0 and only moves if size
		 * is greater than 0.
		 * @return
		 */
		public Node pop(){
			Node pop = stack[pointer];
			stack[pointer] = null;
			if(pointer == 0){
				pointer = max - 1;
			}else{
				pointer--;
			}
			if(size > 0){
				size--;
			}else{
				size = 0;
			}
			return pop;
		}
		
		public Node peek(){
			return size > 0 ? stack[pointer] : null;
		}
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

	/**
	 * load sets all values to the new values provided.
	 * @param chapterID
	 * @param currentNode2
	 * @param noParent
	 * @param noChild
	 * @param bookmark
	 */
	public void load( Node currentNode,
			ArrayList<Node> noParent, ArrayList<Node> noChild,
			ArrayList<Node> bookmark) {
		this.current = currentNode;
		this.noParent = noParent;
		this.noChild = noChild;
		this.bookmark = bookmark;//TODO find out when called and update text.
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

	public void loadTree(Hashtable<Integer, Node> tree, Node text) {
		this.start = text;
		this.tree = tree;
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

	public String getSelTitle() {
		return selected.getTitle();
	}
	
	public void setSelTitle(String title) {
		selected.setTitle(title);
	}

	public String getSelText() {
		return selected.getText();
	}
	
	public void setSelText(String text) {
		selected.setText(text);
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
}
