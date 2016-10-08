package cpp.VNCreator.View;

import java.util.Optional;

import cpp.VNCreator.Controller.Controller;
import cpp.VNCreator.Controller.OptionManager;
import cpp.VNCreator.Model.NodeType.nodeType;
import cpp.VNCreator.Node.Node;
import cpp.VNCreator.Node.Option;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * Controls the canvas input and the input for the
 * node's text. Uses mouse input and button press to
 * update the canvas and the text editor.
 * 
 * @author Stephen
 *
 */
public class Editor {
	
	@FXML
	private Canvas canvas;
	
	@FXML
	private AnchorPane canvasPane;
	
	@FXML
	private TextField title;
	
	@FXML
	private TextArea text;
	
	@FXML
	private TabPane option;

	private Node selected;
	private Controller controller;
	private OptionManager optionManager;
	ContextMenu menu;
	
	/**
	 * Takes all the text from the text editor and
	 * applies the new text to the selected node.
	 * updates the text if the Selected node is
	 * current.
	 */
	@FXML
	private void save(){
		saveNode();
		controller.updateSel();
	}
	
	/**
	 * Deletes the node if the node is empty else throw error
	 * box. User input will allow to continue to delete the node.
	 */
	@FXML
	private void delete(){
		if(!selected.isEmpty()){
			controller.delete(selected.getID());
		}
	}
	
	/**
	 * When the mouse is pressed the mouse location is verified on 
	 * what the mouse is over. If over a node set node to selected,
	 * if over tools create new node, else set selected nod to null.
	 * @param e
	 */
	@FXML
	private void onPress(MouseEvent e){
		
		if(e.isPrimaryButtonDown()){
			if(menu.isShowing()) menu.hide();
			controller.mousePress(e);
		}else if(e.isSecondaryButtonDown()){
			controller.setLocation(e.getX(), e.getY());
			menu.show(canvas, e.getScreenX(), e.getScreenY());
		}
	}
	
	/**
	 * When mouse release check if connecting nodes connect
	 * selected to the node that cursor is over. If already
	 * connected, disconnect the nodes.
	 * reset booleans to default value.
	 * @param e
	 */
	@FXML
	private void onRelease(MouseEvent e){
		controller.moseRelease(e);
	}
	
	/**
	 * Follows mouse cursor and allows for node to be drawn at current location
	 * else draws a line to cursors location.
	 * @param e
	 */
	@FXML
	private void onDrag(MouseEvent e){
		controller.onDrag(e);
	}
	
	@FXML
	private void onMove(MouseEvent e){
		controller.onMove(e);
	}
		
	/**
	 * updates the text editor with the text from the selected node.
	 * Enable text areas if there is a selected node.
	 */
	public void update(Node node) {
		if(node != null){
			if(selected != null) saveNode();
			selected = node;
			title.setDisable(false);
			text.setDisable(false);
			title.setText(node.getTitle());
			text.setText(node.getText());
			if(node.getType() == nodeType.Option){
				option.setDisable(false);
				optionManager.setOption((((Option)node).getChildren()));
			}else{
				option.setDisable(true);
				optionManager.reset();
			}
		}else{
			disable();
		}
	}
	
	private void saveNode(){
		selected.setTitle(title.getText());
		selected.setText(text.getText());
		if(selected.getType() == nodeType.Option){
			((Option)selected).setChildren(optionManager.save());
		}
	}

	/**
	 * Sets default values and adds listener to the canvas to be resized.
	 * Disables text if select is null and create a starting node.
	 * @param canvasManager
	 * @param cHeditor
	 */
	public void setCanvasManger(Controller controller) {
		this.controller = controller;
		optionManager = new OptionManager(option, controller);
		
		canvasPane.heightProperty().addListener( observable -> updateCanvas());
		canvasPane.widthProperty().addListener( observable -> updateCanvas());
		buildContextMenu();
	}
	
	private void updateCanvas(){
		canvas.heightProperty().set(canvasPane.getHeight());
		canvas.widthProperty().set(canvasPane.getWidth());
		controller.reDraw();
	}
	
	public Canvas getCanvas(){
		return canvas;
	}
	
	private void disable(){
		clear();
		title.setDisable(true);
		text.setDisable(true);
	}

	public void clear() {
		if(selected != null) saveNode();
		title.clear();
		text.clear();
		selected = null;
	}
	
	private void buildContextMenu(){
		menu = new ContextMenu();
		MenuItem text = new MenuItem("Create Text");
		text.setOnAction( handle -> {
			controller.createText();
		});
		
		MenuItem option = new MenuItem("Create Option");
		option.setOnAction( handle -> {
			controller.createOption();
		});
		
		MenuItem delete = new MenuItem("Delete Node");
		delete.setOnAction( handle -> {
			controller.delete();
		});
		
		menu.getItems().addAll(text, option,new SeparatorMenuItem(), delete);	
	}
}
