package cpp.VNCreator.View;

import cpp.VNCreator.Controller.Controller;
import cpp.VNCreator.Controller.TextManager;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
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

	private Controller controller;
	ContextMenu menu;
	
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
		controller.mouseRelease(e);
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

	/**
	 * Sets default values and adds listener to the canvas to be resized.
	 * Disables text if select is null and create a starting node.
	 * @param canvasManager
	 * @param cHeditor
	 */
	public void setCanvasManger(Controller controller) {
		this.controller = controller;
		new TextManager(option, controller);
		
		canvasPane.heightProperty().addListener( observable -> updateCanvas());
		canvasPane.widthProperty().addListener( observable -> updateCanvas());
		buildContextMenu();

		canvas.setOnScroll(new EventHandler<ScrollEvent>(){
			
			@Override
			public void handle(ScrollEvent event) {
				
				if(event.getDeltaY() >= 0){
					canvas.setScaleX(.5);
					canvas.setScaleY(.5);
				}else if(event.getDeltaY() < 0 ){
					canvas.setScaleX(2);
					canvas.setScaleY(2);
				}
				controller.updateSel();
			}
			
		});
	}
	
	private void updateCanvas(){
		canvas.heightProperty().set(canvasPane.getHeight());
		canvas.widthProperty().set(canvasPane.getWidth());
		controller.reDraw();
	}
	
	public Canvas getCanvas(){
		return canvas;
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
