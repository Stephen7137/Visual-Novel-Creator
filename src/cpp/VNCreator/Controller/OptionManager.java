package cpp.VNCreator.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import cpp.VNCreator.Node.OptionText;
import cpp.VNCreator.View.Main;
import cpp.VNCreator.View.OptionBox;
import javafx.fxml.FXMLLoader;
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
	Controller controller;
	ArrayList<OptionText> children;
	ArrayList<OptionBox> optionBox;
	
	public OptionManager(VBox option, Controller controller){
		this.controller = controller;
		this.option = option;
	}
	
	/**
	 * Creates the options panel and adds a activation listener
	 * to tell what box is active. Sets the text to oText of each
	 * node and sets the id to be returned latter.
	 * @param text all options
	 * @throws IOException 
	 */
	public void setOption(ArrayList<OptionText> arrayList){
		this.children = arrayList;
		buildOption();
	}
	
	private void buildOption(){
		reset();
		optionBox = new ArrayList<OptionBox>();
		for(int i = 0; i < children.size(); i++){
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("SimConsole.fxml"));
			OptionBox console = loader.getController();
			optionBox.add(console);
			console.controller(this, children.get(i).getText(), i);
			if(i == 0) console.dissableUp();
			if(i == children.size()-1) console.dissableDown();
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

	public ArrayList<OptionText> save() {
		for(OptionBox box : optionBox){
			children.get(box.getID()).setText(box.getText());
		}
		return children;
	}

	public void highlight(int id) {
		controller.highlight(children.get(id).getNode().getID());
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
		Collections.swap(children, i, j);
	}

	public void delete(int id) {		
		children.remove(children.get(id));
		buildOption();
	}
}
