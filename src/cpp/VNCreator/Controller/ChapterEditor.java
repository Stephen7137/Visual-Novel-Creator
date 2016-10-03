package cpp.VNCreator.Controller;

import java.util.ArrayList;
import java.util.Random;

import cpp.VNCreator.Model.NodeType.nodeType;
import cpp.VNCreator.Node.Node;
import cpp.VNCreator.Node.Option;
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
	private ArrayList<Node> tree;
	//TODO simplify name
	private Node currentNode;
	private Node selectedNode;
	private Stack memory;
	Random keyGen;
	
	private Node start;
	
	/**
	 * Sets up the default variable and updates the text.
	 * @param chapterID
	 * @param tree
	 */
	public ChapterEditor(ArrayList<Node> tree){
		memory = new Stack(20);
		keyGen = new Random();
		this.tree = tree;
		currentNode = null;
		selectedNode = null;
		bookmark = new ArrayList<Node>();
		noParent = new ArrayList<Node>();
		noChild = new ArrayList<Node>();
	}
		//TODO
	public int connect(int id){
		Node node = null;
		if(selectedNode.getType() == nodeType.Text){
			node = searchTree(id);
			((Text)selectedNode).setChild(node);
			node.addParent(selectedNode);
		}
		
		return node.getID();
	}
	
	/**
	 * delete removes selected node from the tree and removes the node from the
	 * parent list from child nodes and removes itself from child list in 
	 * parent nodes.
	 */
	public void delete(){
	//TODO	
//		Text[] child = selectedNode.getChild();
//		for(int i = 0; i < child.length; i++){
//			child[i].removeParent(selectedNode);
//			if(child[i].getParentSize() == 0){
//				noParent.add(child[i]);
//			}
//		}
//		
//		ArrayList<Text> parent = selectedNode.getParent();
//		for(int i = 0; i < parent.size(); i++){
//			parent.get(i).removeChild(selectedNode);
//			if(parent.get(i).getChildSize() == 0){
//				noChild.add(parent.get(i));
//			}
//		}		
//		
//		selectedNode.setChild(null);
//		selectedNode.setParent(null);
//		
//		tree.remove(selectedNode);
//		selectedNode = null;
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
		bookmark.add(selectedNode);
	}
	
	public void removeBookmark(){
		bookmark.remove(selectedNode);
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
			if(currentNode.getType() == nodeType.Option){
				Option option = (Option)currentNode;
				if(option.numChildren() < input){
					
					next = true;
				}
			}else{
				Text text = (Text)currentNode;
				currentNode = text.getChild();
				memory.push(currentNode);
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
			currentNode = memory.pop();
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
		if(tree.size() == 0){
			start = newText;
		}else{
			noParent.add(newText);
		}		
		noChild.add(newText);
		tree.add(newText);
		selectedNode = newText;
		return newText;
	}
	
	private int createKey(){
		return keyGen.nextInt(10000);
	}
	
	public int getKey(){
		if(currentNode == null) return -1;
		return currentNode.getID();
	}
	
	/**
	 * addChild takes text and adds it to the child
	 * list of selectedNode.
	 * @param text
	 * @return key of the text
	 */
	//TODO
//	public int addChild(Text text){
//		if(text != null){
//			
//			text.addParent(selectedNode);
//			if(noParent.contains(text)){
//				noParent.remove(text);
//			}
//			
//			selectedNode.addChild(text);
//			if(noChild.contains(selectedNode)){
//				noChild.remove(selectedNode);
//			}	
//			
//			return text.getID();
//		}
//		return -1;
//	}
	
	/**
	 * searchTree searches the tree for nodes that have the ID key.
	 * @param key
	 * @return text with id key
	 */
	private Node searchTree(int key){
		if(key >= 0){
			for(int i = 0; i < tree.size(); i++){
				if(tree.get(i).getID() == key)return tree.get(i);
			}
		}
		return null;
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
		return createList(tree);
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
		if(currentNode == null) return -1;
		return currentNode.getID();
	}

	public void setSelected(int key) {
		selectedNode = searchTree(key);
	}

	public Node getSelected() {
		return selectedNode;
	}

	public void setCurrent(int selected) {
		currentNode = selectedNode; //TODO check when called and update text.
	}

	public boolean isCurrent() {
		return currentNode == selectedNode;
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
		this.currentNode = currentNode;
		this.noParent = noParent;
		this.noChild = noChild;
		this.bookmark = bookmark;//TODO find out when called and update text.
	}

	public Node saveCurrent() {
		return currentNode;
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

	public void loadTree(ArrayList<Node> tree, Node text) {
		this.start = text;
		this.tree = tree;
	}

	/**
	 * isChild checks node with id key is in selectedNodes
	 * list of child.
	 * @param key
	 * @return true if text with key is child.
	 */
	//TODO
//	public boolean isChild(int key) {
//		Text text = searchTree(key);
//		return selectedNode.isChild(text);
//	}
	
	public boolean currentBookmark() {
		return bookmark.contains(selectedNode);
	}

	public boolean isNull() {
		return selectedNode == null;
	}
	
	public int getSelectedID() {
		if(selectedNode != null){
			return selectedNode.getID();
		}
		return -1;
	}
	
	public void setStart() {
		start = selectedNode;
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
		return selectedNode.getTitle();
	}
	
	public void setSelTitle(String title) {
		selectedNode.setTitle(title);
	}

	public String getSelText() {
		return selectedNode.getText();
	}
	
	public void setSelText(String text) {
		selectedNode.setText(text);
	}
//TODO
//	public boolean selHasChildren() {
//		return selectedNode.hasChildren();
//	}
	
	public Node getCurrentNode(){
		return currentNode;
	}

	public boolean isSelect(int id) {
		return selectedNode.getID() == id;
	}
}
