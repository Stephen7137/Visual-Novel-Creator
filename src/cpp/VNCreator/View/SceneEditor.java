package cpp.VNCreator.View;

import cpp.VNCreator.Controller.ImageLoader;
import cpp.VNCreator.Controller.ImageLoader.ImageStorage;

import java.util.Hashtable;
import java.util.Map.Entry;

import cpp.VNCreator.Controller.Controller;
import cpp.VNCreator.Node.Node;
import cpp.VNCreator.Node.Scene;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class SceneEditor {

	Controller cotroller;
	ImageLoader loader;
	Image bckgrndImage;
	GraphicsContext gc;
	Hashtable<String, ImageStorage> bckgrndTable;
	
	@FXML
	private Canvas canvas;
	
	@FXML
	private VBox background;
	
	@FXML
	private VBox cast;
	
	@FXML
	private AnchorPane canvasPane;
	
	public void update(Node node) {
		loadScene(node.getScene());
	}
	
	public void loadScene(Scene scene){
		bckgrndImage = loader.getBackground("blq8V6V.jpg");//scene.getBackground());
	}
	
	public void drawScene(){
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		gc.drawImage(bckgrndImage, 0, 0);
	}

	public void clear() {
		//TODO
	}

	public void controller(Controller cotroller) {
		gc = canvas.getGraphicsContext2D();
		this.cotroller = cotroller;
		canvasPane.heightProperty().addListener( observable -> updateCanvas());
		canvasPane.widthProperty().addListener( observable -> updateCanvas());
	}

	private void updateCanvas(){
		canvas.heightProperty().set(canvasPane.getHeight());
		canvas.widthProperty().set(canvasPane.getWidth());
		//controller.reDraw();
	}

	public void loadBackIcon(Hashtable<String, ImageStorage> bckgrndTable) {
		this.bckgrndTable = bckgrndTable;
		for(Entry<String, ImageStorage> entry : bckgrndTable.entrySet() ){
			ImageView view = new ImageView(entry.getValue().getImage());
			view.setFitHeight(100);
			view.setPreserveRatio(true);
			view.setOnMousePressed(event -> {
				setBackground(entry.getValue().getName());
			});
			background.getChildren().add(view);
		}
	}
	
	public void setBackground(String name){
		bckgrndImage = bckgrndTable.get(name).getImage();
		drawScene();
	}	
}
