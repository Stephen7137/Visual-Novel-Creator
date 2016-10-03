package cpp.VNCreator.View;

import java.util.Optional;

import cpp.VNCreator.Controller.Controller;
import cpp.VNCreator.Controller.OptionManager;
import cpp.VNCreator.Model.NodeType.nodeType;
import cpp.VNCreator.Node.Node;
import cpp.VNCreator.Node.Option;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

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
	private VBox option;

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
		selected.setTitle(title.getText());
		selected.setText(text.getText());
		if(selected.getType() == nodeType.Option){
			((Option)selected).setChildren(optionManager.save());
		}
		controller.updateSel();
	}
	
	/**
	 * Deletes the node if the node is empty else throw error
	 * box. User input will allow to continue to delete the node.
	 */
	@FXML
	private void delete(){
		if(!selected.isEmpty()){
			if(!deteleError()){
				controller.delete(selected);
			}
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
	 * Create a alert box if user is trying to delete a node that has
	 * text in it.
	 * @return user input.
	 */
	private boolean deteleError(){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete Node");
		alert.setHeaderText(null);
		alert.setContentText("Node contains Text!\nAre you sure "
				+ "you want to delte node?");
		
		ButtonType buttonYes = new ButtonType("Yes");
		ButtonType buttonNo = new ButtonType("No");
		
		alert.getButtonTypes().setAll(buttonYes, buttonNo);
		
		Optional<ButtonType> results = alert.showAndWait();
		if(results.get() == buttonYes) return true;
		return false;
	}
	
	/**
	 * updates the text editor with the text from the selected node.
	 * Enable text areas if there is a selected node.
	 */
	public void update(Node node) {
		if(node != null){
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
		title.clear();
		text.clear();
	}
	
	private void buildContextMenu(){
		menu = new ContextMenu();
		MenuItem text = new MenuItem("CreateText");
		text.setOnAction(new EventHandler<ActionEvent>() {
			
		    public void handle(ActionEvent t) {
		        controller.createText();
		    }
		});
		
		menu.getItems().addAll(text);		
	}
	
}
