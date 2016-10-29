package cpp.VNCreator.Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map.Entry;

import cpp.VNCreator.Model.CVNode;
import cpp.VNCreator.Model.TreePoint;
import cpp.VNCreator.Node.Node;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;

/**
 * Controls the canvas, keep track of all nodes location
 * on the canvas and how they are connected and draws them
 * on the canvas. Also highlight selected node and start node.
 * 
 * @author Stephen Jackson
 *
 */
public class CanvasManager {
	
	private final int textOffset = 13;
	private final int startOffset = 5;
	private final int offset = 15;
	private final double defHeight = 75;
	private final double defWidth = 150;
	private final Color defColor = Color.LIGHTBLUE;
	private final Color background = Color.WHITE;
	
	private Font font;
	private Image arrow;
	
	private Hashtable<Integer,TreePoint> lookup;
	private Canvas canvas;
	private GraphicsContext gc;
	
	private TreePoint start;
	private TreePoint selected;
	private int onConnect;
	
	private Color lastColor;
	
	
	/**
	 * Sets up the the canvas and the default values,
	 * as well as location of the tools.
	 * @param canvas
	 */
	public CanvasManager(Canvas canvas){
		URL url = getClass().getClassLoader().getResource("Resources/Images/Arrow.png");
		arrow = new Image(url.toExternalForm());
		start = null;
		selected = null;
		lastColor = defColor;
		lookup = new Hashtable<Integer,TreePoint>();
		this.canvas = canvas;
		gc = canvas.getGraphicsContext2D();
		font = new Font("Times New Roman", 12);
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
		drawSelect();
	}
	
	/**
	 * Draws the canvas, layering the line connecting then
	 * draws nodes and then the tools.
	 */
	public void update(){
		drawBackground();
		drawAllLines();
		drawAllNodes();
		drawSelect();
	}
	
	/**
	 * Draws the background with color {@link #background}
	 */
	private void drawBackground(){
		gc.setFill(background);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}
	
	/**
	 * Draws the default lines between the nodes and highlights
	 * the child coming from the {@link #selected}.
	 */
	private void drawAllLines(){
		for(Entry<Integer, TreePoint> point : lookup.entrySet()){
			drawChild(point.getValue(), Color.BLACK);
		}
	}
	
	/**
	 * Draws the nodes of all them as a default color and 
	 * then draws the {@link #selected} with a unique color.
	 */
	private void drawAllNodes(){
		
		for(Entry<Integer, TreePoint> point : lookup.entrySet()){
			drawNode(point.getValue());
		}
		
		if(start != null){
			drawNode(start);
		}
		
		
	}
	
	/**
	 * Draws a highlight over {@link #selected} to show the
	 * node and draws a black dot for connection.
	 */
	private void drawSelect(){
		if(selected != null){
			drawNode(selected);
			gc.setLineWidth(5);
			gc.setStroke(Color.DARKORANGE);
			gc.strokeRect(selected.getX(), selected.getY(), selected.getWidth(), selected.getHeight());
			gc.setLineWidth(2);
			gc.setStroke(Color.ORANGE);
			gc.strokeRect(selected.getX(), selected.getY(), selected.getWidth(), selected.getHeight());
		}
	}
	
	/**
	 * Draws a node with a color provided.
	 * @param node
	 * @param color
	 */
	private void drawNode(TreePoint node){
		gc.setFill(node.getColor());
		gc.fillRect(node.getX(), node.getY(), node.getWidth(), node.getHeight());
		gc.setLineWidth(2);
		gc.setStroke(Color.BLACK);
		gc.setFill(Color.BLACK);
		gc.strokeRect(node.getX(), node.getY(), node.getWidth(), node.getHeight());
		gc.setFont(font);
		gc.fillText(node.getTitle(), node.getX() + 10, node.getY() + 15);
	}
	
	/**
	 * Draws the child of the node connections to the
	 * child and use the color provided.
	 * @param point
	 * @param color
	 */
	private void drawChild(TreePoint point, Paint color){
		if(point != null){
			gc.setLineWidth(5);
			gc.setStroke(color);
			ArrayList<CVNode> list = point.getChildren();
			int i = 0;
			for(CVNode node : list){
				if(node != null){
					if(node.id != -1){
						TreePoint point1 = searchTree(node.id);
						if(point.getBottom() < point1.getY()){
							drawArrow(point.getHalfTop(), point.getHalfLeft(),
								point1.getHalfTop(), point1.getY(), Color.BLACK);
						}else if(point.getY() > point1.getBottom()){
							drawArrow(point.getHalfTop(), point.getHalfLeft(),
								point1.getHalfTop(), point1.getBottom(), Color.BLACK);
						}else if(point.getX() < point1.getX()){
							drawArrow(point.getHalfTop(), point.getHalfLeft(),
								point1.getX(), point1.getHalfLeft(), Color.BLACK);
						}else{
							drawArrow(point.getHalfTop(), point.getHalfLeft(),
								point1.getRight(), point1.getHalfLeft(), Color.BLACK);
						}
					}else{
						//if( !(point == selected && node.id == onConnect) ){
							gc.setFill(color);
							node.setPoint(new Point2D(point.getHalfTop() + point.getRadious(), point.getBottom() + startOffset + i + point.getRadious()));
							gc.fillOval(point.getHalfTop(), point.getBottom() + startOffset + i , point.getRadious(), point.getRadious());
							gc.fillText(node.title, point.getHalfTop() + textOffset, point.getBottom() + textOffset + i );
							i += offset;
						//}						
					}
				}				
			}
		}		
	}
	
	private void drawArrow(double x1, double y1, double x2, double y2, Color color){
		gc.setStroke(color);
		gc.setLineWidth(2);
		gc.strokeLine(x1, y1, x2, y2);
		gc.save();
		double atan1 = Math.atan2(x2-x1, y2-y1) * -1;
		Rotate r = new Rotate(Math.toDegrees(atan1),x2,y2);
		gc.transform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
		gc.drawImage(arrow, x2 - 10, y2 - 15);
		gc.restore();
	}
	
	/**
	 * Checks if the provided location is within a node.
	 * Able to set the node that is returned to selected.
	 * @param x
	 * @param y
	 * @param selected boolean to set selected.
	 * @return the id of the node the point is on.
	 */
	public int onNode(double x, double y) {
		for(Entry<Integer, TreePoint> entry : lookup.entrySet()){
			TreePoint point = entry.getValue();
			if(point.collition(x, y)){
				return point.getID();		
			}
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
		for(Entry<Integer, TreePoint> point : lookup.entrySet()){
			onConnect = point.getValue().onChild(x, y);
			if(onConnect != -1){
				setSelected(point.getValue().getID());
				return true;
			}
		}
		return false;	
	}

	public int getSelected() {
		return selected.getID();
	}
	
	public Hashtable<Integer, TreePoint> saveCanvas() {
		return lookup;
	}

	/**
	 * Sets the variables to the new variables and resets
	 * all non important variables.
	 * @param chapterID
	 * @param lookup
	 */
	public void load( Hashtable<Integer, TreePoint> lookup) {
		this.lookup = lookup;
		selected = null;
		update();
	}

	/**
	 * Changes the location of the nodes and updates the canvas.
	 * @param x
	 * @param y
	 */
	public void moveNode(double vecX, double vecY) {
		if(selected != null){
			selected.subXY(vecX, vecY);
			update();
		}
	}
	
	public void moveScreen(double vecX, double vecY){
		for(Entry<Integer, TreePoint> point : lookup.entrySet()){
			point.getValue().subXY(vecX, vecY);
		}
		update();
	}

	/**
	 * Checks to see if coordinates are on selected node.
	 * @param x
	 * @param y
	 * @return true if on selected node.
	 */
	public boolean onSelected(double x, double y) {
		if(selected == null) return false;
		if(selected.collition(x, y)) return true;
		return false;
	}
	
	/**
	 * Creates a new node at coordinates with id CreateNode and 
	 * adds node to the main ArrayList, then set node as selected.
	 * @param x
	 * @param y
	 * @param createNode
	 */
	public void createNode(double x, double y, Node node) {
		TreePoint cNode = new TreePoint(x,y, defHeight, defWidth,
				lastColor, node);
		lookup.put(cNode.getID(),cNode);
		setSelected(cNode);
		update();
	}
	
	public void changeColor(Color color){
		selected.setColor(color);
		lastColor = color;
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
		update();
	}

	/**
	 * Draws the line from select to given coordinates and if given a boolean
	 * changes the color of the line.
	 * @param x
	 * @param y
	 * @param delete
	 */
	public void drawConnect(double x, double y) {
		update();
		drawArrow(selected.getHalfTop(), selected.getHalfLeft(), x, y, Color.GREEN);
		drawSelect();
	}

	/**
	 * Looks through {@link #lookup} for node with the id.
	 * @param ID
	 * @return
	 */
	private TreePoint searchTree(int ID) {
		return lookup.get(ID);
	}

	/**
	 * Deletes selected node and removes all child nodes and removes
	 * selected from {@link #lookup}.
	 */
	public void delete(int id) {
		//if(selected.getID() == id) selected = null;
		lookup.remove(id);
		update();
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

	public int getConnected() {
		return onConnect;
	}

	public void resetOnConnect() {
		onConnect = -1;
	}

	public void printChild(int id) {
		ArrayList<CVNode> nodes = searchTree(id).getChildren2();
		for(CVNode node : nodes){
			System.out.println(node.id + " - " + node.getPoint());
		}
	}

	public ArrayList<TreePoint> getTree() {
		return new ArrayList<TreePoint>(lookup.values());
	}
}
