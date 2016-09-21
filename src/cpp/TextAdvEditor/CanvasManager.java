package cpp.TextAdvEditor;

import java.util.ArrayList;
import cpp.TextAdvEditor.Model.SimpPoint2D;
import cpp.TextAdvEditor.Model.TreePoint;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Controls the canvas, keep track of all nodes location
 * on the canvas and how they are connected and draws them
 * on the canvas. Also highlight selected node and start node.
 * 
 * @author Stephen Jackson
 *
 */
public class CanvasManager {
	
	private final int radius = 15;
	private final int distance = 10;
	private final int nodeDist = 100;
	private final int offset = 22;
		
	private int diameter;
	private int toolW;
	private int toolH;
	
	private Color background = Color.WHITE;
	
	private ArrayList<TreePoint> lookup;
	private Canvas canvas;
	private GraphicsContext gc;
	
	private int chapterID;
	private int selectedID;
	private TreePoint start;
	private TreePoint selected;

	private Point2D toolText;
	private SimpPoint2D connect;
	
	public CanvasManager(int chID){
		chapterID = chID;
	}
	
	/**
	 * Sets up the the canvas and the default values,
	 * as well as location of the tools.
	 * @param canvas
	 * @param key
	 */
	public void setCanvas(Canvas canvas, int key){
		diameter = radius*2;
		start = null;
		lookup = new ArrayList<TreePoint>();
		this.canvas = canvas;
		gc = canvas.getGraphicsContext2D();
		toolW = toolH = diameter + 8;
		toolText = new Point2D(toolW/2 + 2, toolH - radius - 2);
		resetSelected();
	}
	
	/**
	 * Draws the canvas with a highlighted the connection
	 * between the node tree and selectedNode.
	 * @param tree 
	 */
	public void updateWithHighlight(TreePoint tree){
		drawBackground();
		drawAllLines();
		gc.setStroke(Color.AQUA);
		gc.strokeLine(selected.getX(),selected.getY(), tree.getX(),
				tree.getY());
		drawAllNodes();
		drawSelectHighlight();
		drawAllTools();
	}
	
	/**
	 * Draws the canvas, layering the line connecting then
	 * draws nodes and then the tools.
	 */
	public void update(){
		drawBackground();
		drawAllLines();
		drawAllNodes();
		drawSelectHighlight();
		drawAllTools();
	}
	
	/**
	 * Draws the background with color {@link #background}
	 */
	private void drawBackground(){
		gc.setFill(background);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}
	
	/**
	 * Draws the tools in the a location on the canvas.
	 */
	private void drawAllTools(){
		gc.setFill(background);
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(3);
		gc.fillRect(2, 2, toolW, toolH);
		gc.strokeRect(2, 2, toolW, toolH);
		drawNode(toolText.getX(), toolText.getY(), Color.BLUE);
	}
	
	/**
	 * Draws the default lines between the nodes and highlights
	 * the child coming from the {@link #selected}.
	 */
	private void drawAllLines(){
		for(int i = 0; i < lookup.size(); i++){
			drawChild(lookup.get(i), Color.BLACK);
		}
		
		if(selected != null){
			drawChild(selected, Color.YELLOW);
		}
	}
	
	/**
	 * Draws the nodes of all them as a default color and 
	 * then draws the {@link #selected} with a unique color.
	 */
	private void drawAllNodes(){
		
		for(int i = 0; i < lookup.size(); i++){
			drawNode(lookup.get(i), Color.BLUE);
		}
		
		if(start != null){
			drawNode(start,Color.GREEN);
		}
		
		
	}
	
	/**
	 * Draws a highlight over {@link #selected} to show the
	 * node and draws a black dot for connection.
	 */
	private void drawSelectHighlight(){
		if(selected != null){
			gc.setStroke(Color.ORANGE);
			gc.setLineWidth(5);
			gc.strokeOval(selected.getX() - radius, selected.getY() - radius,
					diameter, diameter);
			gc.setFill(Color.BLACK);
			gc.fillOval(connect.getX(), connect.getY(), distance, distance);
		}
	}
	
	/**
	 * Draws a node with a color provided.
	 * @param node
	 * @param color
	 */
	private void drawNode(TreePoint node, Paint color){
		drawNode(node.getX(),node.getY(),color);
	}
	
	/**
	 * Draws the node at the location with the provided color.
	 * Uses {@link #radius} as default radius.
	 * @param x
	 * @param y
	 * @param color
	 */
	private void drawNode(double x, double y, Paint color){
		gc.setLineWidth(5);
		gc.setFill(color);
		gc.fillOval(x - radius, y - radius,
				diameter, diameter);
		gc.setLineWidth(2);
		gc.setStroke(Color.BLACK);
		gc.strokeOval(x - radius, y - radius,
				diameter, diameter);
	}
	
	/**
	 * Draws the child of the node connections to the
	 * child and use the color provided.
	 * @param point
	 * @param color
	 */
	private void drawChild(TreePoint point, Paint color){
		gc.setLineWidth(5);
		gc.setStroke(color);
		for(int j = 0; j < point.childsize(); j++){
			TreePoint point1 = point.getChild(j);
			gc.strokeLine(point.getX(), point.getY(), 
					point1.getX(), point1.getY());
		}	
	}
	
	/**
	 * Checks if the provided location is within a node.
	 * Able to set the node that is returned to selected.
	 * @param x
	 * @param y
	 * @param selected boolean to set selected.
	 * @return the id of the node the point is on.
	 */
	public int onNode(double x, double y, boolean selected) {
		for(int i = 0; i < lookup.size(); i++){
			if(lookup.get(i).distance(x, y) <= radius){
				if(selected){
					setSelected(lookup.get(i));
				}
				update();
				return lookup.get(i).getID();
			}
		}
		return -1;
	}

	public int getSelected() {
		return selectedID;
	}

	/**
	 * Creates a new node and adds it to selected node.
	 * If selected has children then space them out when
	 * add more.
	 * @param key Id of the node.
	 */
	public void addChild(int key) {
		if(key >= 0){
			TreePoint cNode = new TreePoint(0,0,key);
			selected.addChild(cNode);
			lookup.add(cNode);
			cNode.addParent(selected);
			if(!selected.childEmpty()){
				int n = selected.childsize();
				int y = (int)selected.getY() - 
						((n-1)*diameter + distance*(n-1))/2;
				int x = (int)selected.getX() + nodeDist;
				for(int i = 0; i < n; i++){
					selected.getChild().get(i).setXY(x,y);
					y+= (diameter + distance);
				}
			}
			update();
		}
	}

	public int getID() {
		return chapterID;
	}

	public ArrayList<TreePoint> saveCanvas() {
		return lookup;
	}

	/**
	 * Sets the variables to the new variables and resets
	 * all non important variables.
	 * @param chapterID
	 * @param lookup
	 */
	public void load(int chapterID, ArrayList<TreePoint> lookup) {
		this.chapterID = chapterID;
		this.lookup = lookup;
		resetSelected();
	}

	/**
	 * Sets all non important variables to default setting and then
	 * update the canvas.
	 */
	public void resetSelected() {
		selectedID = -1;
		selected = null;
		connect = null;
		update();
	}

	public int getChapterID() {
		return chapterID;
	}

	/**
	 * Changes the location of the nodes and updates the canvas.
	 * @param x
	 * @param y
	 */
	public void moveNode(double x, double y) {
		if(selected != null){
			selected.setXY(x, y);
			connect.setPoint(x - offset, y - offset);
			update();
		}
	}

	/**
	 * Checks to see if coordinates are on selected node.
	 * @param x
	 * @param y
	 * @return true if on selected node.
	 */
	public boolean onSelected(double x, double y) {
		if(selected == null) return false;
		return selected.distance(x, y) <= radius;
	}

	/**
	 * Checks to see if coordinates are on the tool section of the
	 * canvas.
	 * @param x
	 * @param y
	 * @return true if on tools.
	 */
	public int tools(double x, double y) {
		if(x >= 0 && x <= toolW && y >=0 && y <= toolH ){
			if(toolText.distance(x,y) <= radius) return 0;
		}
		return -1;
	}
	
	/**
	 * Checks to see if coordinates are on a node already connected
	 * to the selected node.
	 * @param x
	 * @param y
	 * @return true if on child node of selected node.
	 */
	public boolean onConnect(double x, double y){
		if(connect == null) return false;
		return connect.distance(x,y) <= distance;
		
	}

	/**
	 * Creates a new node at coordinates with id CreateNode and 
	 * adds node to the main ArrayList, then set node as selected.
	 * @param x
	 * @param y
	 * @param createNode
	 */
	public void createNode(double x, double y, int createNode) {
		TreePoint cNode = new TreePoint(x,y,createNode);
		lookup.add(cNode);
		setSelected(cNode);
	}
	
	/**
	 * Sets node with given id to selected node.
	 * @param ID
	 */
	public void setSelected(int ID){
		setSelected(searchTree(ID));
		update();
	}
	
	/**
	 * Sets node as the selected node and adds a connection point to the node.
	 * If the node is null then reset selected node.
	 * @param node
	 */
	private void setSelected(TreePoint node){
		selected = node;
		if(selected == null){
			resetSelected();
		}else{
			selectedID = selected.getID();
			connect = new SimpPoint2D(selected.getX() - offset, 
					selected.getY() - offset);
		}	
	}

	/**
	 * Draws the line from select to given coordinates and if given a boolean
	 * changes the color of the line.
	 * @param x
	 * @param y
	 * @param delete
	 */
	public void drawConnect(double x, double y, boolean delete) {
		update();
		gc.setLineWidth(5);
		if(delete){
			gc.setStroke(Color.RED);
		}else{
			gc.setStroke(Color.GREEN);	
		}
		gc.strokeLine(selected.getX(), selected.getY(), x, y);
	}

	/**
	 * Connects node with id to selected node and adds selected to parent
	 * of node and node to child of selected.
	 * @param ID
	 */
	public void connect(int ID) {
		if(ID >= 0){
			if(selected != null){
				TreePoint node = searchTree(ID);
				node.addParent(selected);
				selected.addChild(node);
			}
		}
		update();
	}

	/**
	 * Looks through {@link #lookup} for node with the id.
	 * @param ID
	 * @return
	 */
	private TreePoint searchTree(int ID) {
		for(int i = 0; i < lookup.size(); i++){
			if(lookup.get(i).getID() == ID) return lookup.get(i);
		}
		return null;
	}

	/**
	 * Disconnects node with id from selected node and removes selected to 
	 * parent of node removes node to child of selected.
	 * @param ID
	 */
	public void disconnect(int ID) {
		TreePoint point = searchTree(ID);
		selected.removeChild(point);
		point.removeParent(selected);
		update();
	}

	/**
	 * Deletes selected node and removes all child nodes and removes
	 * selected from {@link #lookup}.
	 */
	public void delete() {
		if(selected != null){
			ArrayList<TreePoint> child = selected.getChild();
			for(int i = 0; i < child.size(); i++){
				child.get(i).removeParent(selected);
			}
			ArrayList<TreePoint> parent = selected.getChild();
			for(int i = 0; i < parent.size(); i++){
				parent.get(i).removeChild(selected);
			}
			selected.delete();
			lookup.remove(selected);
			resetSelected();
		}
	}

	/**
	 * Creates the starting node with given id.
	 * @param ID
	 */
	public void StartNode(int ID) {
		createNode(diameter, canvas.getHeight()/2, ID);
		start = searchTree(ID);
	}
	
	/**
	 * Sets selected node to starting node.
	 */
	public void setStart(){
		start = selected;
		update();
	}
	
	/**
	 * Sets selected node to node with given ID.
	 * @param ID
	 */
	public void setStart(int ID){
		start = searchTree(ID);
		update();
	}

	/**
	 * Updates the canvas and highlights the connection between
	 * selected and node with id.
	 * @param ID
	 */
	public void highlight(int ID) {
		updateWithHighlight(searchTree(ID));
	}
}
