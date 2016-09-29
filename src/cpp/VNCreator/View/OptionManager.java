package cpp.VNCreator.View;

import java.io.IOException;
import cpp.VNCreator.Controller.CanvasManager;
import cpp.VNCreator.Node.OptionText;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * Controls the Option text from each node and keeps track of
 * what node the text is pointing to. Clears panel when there is
 * no options.
 * 
 * @author Stephen Jackson
 *
 */
public class OptionManager {

	VBox option;
	GridPane grid;
	OptionText[] children;
	CanvasManager canvas;
	
	public OptionManager(VBox option, CanvasManager canvas){
		this.canvas = canvas;
		this.option = option;
	}
	
	/**
	 * Creates the options panel and adds a activation listener
	 * to tell what box is active. Sets the text to oText of each
	 * node and sets the id to be returned latter.
	 * @param text all options
	 * @throws IOException 
	 */
	public void setOption(OptionText[] children){
		this.children = children;
		buildOption();
	}
	
	private void buildOption(){
		reset();
		for(int i = 0; i < children.length; i++){
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("SimConsole.fxml"));
			OptionBox console = loader.getController();
			console.controller(this, children[i].text, i);
			if(i == 0) console.dissableUp();
			if(i == children.length-1) console.dissableDown();
			try {
				option.getChildren().add(loader.load());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void reset() {
		option.getChildren().clear();
	}

	public OptionText[] getOText() {
		//TODO
		return children;
	}

	public void highlight(int id) {
		canvas.highlight(children[id].node.getID());
	}

	public void shiftUp(int id) {
		swap(id, id-1);
		buildOption();
	}

	public void shiftDown(int id) {
		swap(id, id+1);
		buildOption();
	}
	
	private void swap(int i, int j){
		OptionText tmp = children[i];
		children[i] = children[j];
		children[j] = tmp;
	}

	public void delete(int id) {
		OptionText[] tmp = new OptionText[children.length-1];
		for(int i = 0; i < tmp.length; i++){
			if(i != id){
				tmp[i] = children[i];
			}else{
				i--;
			}
		}
		children = tmp;
		buildOption();
	}
}
