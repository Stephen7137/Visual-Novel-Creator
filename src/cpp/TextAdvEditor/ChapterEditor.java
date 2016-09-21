package cpp.TextAdvEditor;

import java.util.ArrayList;
import java.util.Random;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import cpp.TextAdvEditor.Model.*;

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
	
	private int chapterID;
	private ArrayList<Text> noParent;
	private ArrayList<Text>	noChild;
	private ArrayList<Text>	bookmark;
	private ArrayList<Text> tree;
	private Text currentNode;
	private Text selectedNode;
	private Stack memory;
	Random keyGen;
	
	private StringProperty story;
	private Text start;
	
	/**
	 * Sets up the default variable and updates the text.
	 * @param chapterID
	 * @param tree
	 */
	public ChapterEditor(int chapterID, ArrayList<Text> tree){
		this.chapterID = chapterID;
		memory = new Stack(20);
		keyGen = new Random();
		this.tree = tree;
		currentNode = null;
		selectedNode = null;
		bookmark = new ArrayList<Text>();
		noParent = new ArrayList<Text>();
		noChild = new ArrayList<Text>();
		story = new SimpleStringProperty();
		updateText();
	}
		
	public int connect(int id){
		return addChild(searchTree(id));
	}
	
	/**
	 * updateText updates story to contain the currentNode text
	 * and the options it contains.
	 */
	public void updateText(){
		if(currentNode != null){
			story.setValue(currentNode.getText());
			if(currentNode.hasChildren()){
				Text[] oText = currentNode.getChild();
				for(int i = 0; i < oText.length; i++ )
				story.setValue(story.get() + "\n" + (i+1) + ") " + 
				oText[i].getOptText());
			}
		}
	}
	
	public StringProperty getStory(){
		return story;
	}
	
	/**
	 * delete removes selected node from the tree and removes the node from the
	 * parent list from child nodes and removes itself from child list in 
	 * parent nodes.
	 */
	public void delete(){
		
		Text[] child = selectedNode.getChild();
		for(int i = 0; i < child.length; i++){
			child[i].removeParent(selectedNode);
			if(child[i].getParentSize() == 0){
				noParent.add(child[i]);
			}
		}
		
		ArrayList<Text> parent = selectedNode.getParent();
		for(int i = 0; i < parent.size(); i++){
			parent.get(i).removeChild(selectedNode);
			if(parent.get(i).getChildSize() == 0){
				noChild.add(parent.get(i));
			}
		}		
		
		selectedNode.setChild(null);
		selectedNode.setParent(null);
		
		tree.remove(selectedNode);
		selectedNode = null;
	}
	
	/**
	 * disconnect disconnects selected from node with provided key.
	 * checks to make sure if the node is in selected child list and
	 * then deletes it. If selectedNode has no children then it is
	 * add to arrayList noChild.
	 * @param key
	 * @return returns the key of the disconnected node.
	 */
	public int disconnect(int key){
		Text node = searchTree(key);
		selectedNode.removeChild(node);
		if(selectedNode.getChildSize() == 0 && !noChild.contains(selectedNode)){
			noChild.add(selectedNode);
		}
		node.removeParent(selectedNode);
		if(node.getParentSize() == 0 && !noParent.contains(node)){
			noParent.add(node);
		}
		return node.getKey();
	}
	
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
		if(input >= currentNode.getChildSize())return false;
		
		currentNode = currentNode.getChild(input);
		memory.push(currentNode);
		updateText();
		
		return true;
	}
	
	/*
	 * back reverts the call next up to a predetermined limit.
	 */
	public boolean back(){
		
		Text back = memory.pop();
		if(back != null){
			currentNode = back;
		}
		updateText();
		
		return true;
	}
	
	
	/**
	 * stack holds value Text to be recalled latter.
	 * 
	 * @author Stephen Jackson
	 *
	 */
	class Stack{
		
		Text[] stack;
		int size;
		int max;
		int pointer;
		
		public Stack(int max){
			stack = new Text[max];
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
		public void push(Text text){
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
		public Text pop(){
			Text pop = stack[pointer];
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
	}

	/**
	 * createText creates a new node and then adds it to
	 * arrayList tree, noParents, and noChild
	 * @return key of the new node.
	 */
	public int createText() {
		Text newText = new Text(createKey());
		noParent.add(newText);
		noChild.add(newText);
		tree.add(newText);
		selectedNode = newText;
		return newText.getKey();
	}
	
	private int createKey(){
		return keyGen.nextInt(10000);
	}
	
	public int getKey(){
		if(currentNode == null) return -1;
		return currentNode.getKey();
	}
	
	/**
	 * addChild takes text and adds it to the child
	 * list of selectedNode.
	 * @param text
	 * @return key of the text
	 */
	public int addChild(Text text){
		if(text != null){
			
			text.addParent(selectedNode);
			if(noParent.contains(text)){
				noParent.remove(text);
			}
			
			selectedNode.addChild(text);
			if(noChild.contains(selectedNode)){
				noChild.remove(selectedNode);
			}	
			
			return text.getKey();
		}
		return -1;
	}
	
	/**
	 * searchTree searches the tree for nodes that have the ID key.
	 * @param key
	 * @return text with id key
	 */
	private Text searchTree(int key){
		if(key >= 0){
			for(int i = 0; i < tree.size(); i++){
				if(tree.get(i).getKey() == key)return tree.get(i);
			}
		}
		return null;
	}
	
	public ArrayList<NodeText> getBookMark() {
		return createList(bookmark);
	}

	public ArrayList<NodeText> getNoChildNode() {
		return createList(noChild);
	}

	public ArrayList<NodeText> getNoParentNode() {
		return createList(noParent);
	}
	
	public ArrayList<NodeText> getAllNode() {
		return createList(tree);
	}
	
	/**
	 * createsList collects all titles and id in arrayList and
	 * and returns a arrayList
	 * @param list
	 * @return arrayList of NodeText
	 */
	private ArrayList<NodeText> createList(ArrayList<Text> list){
		ArrayList<NodeText> textList = new ArrayList<NodeText>();
		for(Text text : list){
			textList.add(new NodeText(text.getTitle(),text.getKey()));
		}
		return textList;
	}

	public boolean hasNodes() {
		return tree.size() > 0;
	}

	public int currentKey() {
		if(currentNode == null) return -1;
		return currentNode.getKey();
	}

	public void setSelected(int key) {
		selectedNode = searchTree(key);
	}

	public Text getSelected() {
		return selectedNode;
	}

	public void setCurrent(int selected) {
		currentNode = selectedNode;
		updateText();
	}

	public boolean isCurrent() {
		return currentNode == selectedNode;
	}

	public boolean curHasChildren() {
		return currentNode.getChildSize()>1;
	}

	/**
	 * load sets all values to the new values provided.
	 * @param chapterID
	 * @param currentNode
	 * @param noParent
	 * @param noChild
	 * @param bookmark
	 */
	public void load(int chapterID, Text currentNode,
			ArrayList<Text> noParent, ArrayList<Text> noChild,
			ArrayList<Text> bookmark) {
		this.chapterID = chapterID;
		this.currentNode = currentNode;
		this.noParent = noParent;
		this.noChild = noChild;
		this.bookmark = bookmark;
		updateText();
	}

	public int getCHID() {
		return chapterID;
	}

	public Text saveCurrent() {
		return currentNode;
	}

	public ArrayList<Text> saveNoParent() {
		return noParent;
	}

	public ArrayList<Text> saveNoChild() {
		return noChild;
	}

	public ArrayList<Text> saveBookmark() {
		return bookmark;
	}

	public void loadTree(ArrayList<Text> tree, Text text) {
		this.start = text;
		this.tree = tree;
	}

	/**
	 * isChild checks node with id key is in selectedNodes
	 * list of child.
	 * @param key
	 * @return true if text with key is child.
	 */
	public boolean isChild(int key) {
		Text text = searchTree(key);
		return selectedNode.isChild(text);
	}
	
	/**
	 * isEmpty checks selectedNode if it contains any text.
	 * @return
	 */
	public boolean isEmpty() {
		if(!selectedNode.getTitle().equals("")) return false;
		if(!selectedNode.getText().equals("")) return false;
		if(!selectedNode.getOptText().equals("")) return false;
		return true;
	}

	public boolean currentBookmark() {
		return bookmark.contains(selectedNode);
	}

	public boolean isNull() {
		return selectedNode == null;
	}
	
	public int getSelectedKey() {
		if(selectedNode != null){
			return selectedNode.getKey();
		}
		return -1;
	}
	
	/**
	 * createStart creates a new Text and sets it
	 * as the starting node for the chapter.
	 * @return key of the starting node
	 */
	public int createStart(){
		start = searchTree(createText());
		currentNode = start;
		return start.getKey();
	}
	
	public void setStart() {
		start = selectedNode;
	}
	
	public Text getStart() {
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

	public boolean selHasChildren() {
		return selectedNode.hasChildren();
	}

	/**
	 * getSelOText collects all the option Text from
	 * selectedNode and then creates a arrayList of
	 * TextNodes that have oText and Id.
	 * @return arrayList of TextNodes
	 */
	public ArrayList<NodeText> getSelOText() {
		ArrayList<NodeText> list = new ArrayList<NodeText>();
		for(Text text : selectedNode.getChild()){
			list.add(new NodeText(text.getOptText(), text.getKey()));
		}
		return list;
	}
	
	/**
	 * setSelOText sets the oText of all child of the
	 * selectedNode.
	 * @param oNode
	 */
	public void setSelOText( NodeText[] oNode) {
		Text[] newChild = new Text[oNode.length];
		
		for(int i = 0; i < newChild.length; i++){
			newChild[i] = searchTree(oNode[i].getID());
			newChild[i].setOptText(oNode[i].getText());
		}
		
		selectedNode.setChild(newChild);
	}
}
