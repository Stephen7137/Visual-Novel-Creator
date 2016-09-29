package cpp.VNCreator.View;

import cpp.VNCreator.Controller.ImageLoader;
import cpp.VNCreator.Controller.Controller;
import cpp.VNCreator.Node.Node;
import cpp.VNCreator.Node.Scene;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

public class SceneEditor {

	Controller cotroller;
	ImageLoader loader;
	Image background;
	GraphicsContext gc;
	
	@FXML
	private Canvas canvas;
	
	@FXML
	private VBox cast;
	
	public void update(Node node) {
		loadScene(node.getScene());
	}
	
	public void loadScene(Scene scene){
		background = loader.getBackground("blq8V6V.jpg");//scene.getBackground());
	}
	
	public void drawScene(){
		gc.drawImage(background, 0, 0);
	}

	public void clear() {
		//TODO
	}

	public void controller(Controller cotroller) {
		gc = canvas.getGraphicsContext2D();
		this.cotroller = cotroller;
	}
	
	
}
