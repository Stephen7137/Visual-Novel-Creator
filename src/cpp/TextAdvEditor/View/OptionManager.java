package cpp.TextAdvEditor.View;

import java.util.ArrayList;

import cpp.TextAdvEditor.CanvasManager;
import cpp.TextAdvEditor.Model.NodeText;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
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
	Node[] optionNode;
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
	 */
	public void setOption(ArrayList<NodeText> text){
		reset();
		option.getChildren().clear();
		optionNode = new Node[text.size()];
		for(int i = 0; i < optionNode.length; i++){
			TextArea textDisplay = new TextArea(text.get(i).getText());
			textDisplay.setId(Integer.toString(text.get(i).getID()));
			option.getChildren().add(textDisplay);
			textDisplay.focusedProperty().addListener(
					new ChangeListener<Boolean>(){

				@Override
				public void changed(
						ObservableValue<? extends Boolean> observable,
						Boolean oldValue, Boolean newValue) {
					if(newValue){
						canvas.highlight(Integer.
								parseInt(textDisplay.getId()));
					}
				}
				
			});
		}
	}

	public void reset() {
		option.getChildren().clear();
	}

	/**
	 * Collects the list of all text and formats them in a new NodeText,
	 * contains the text and the id of the text field.
	 * @return array of NodeText, contains id and text.
	 */
	public NodeText[] getOText() {
		ObservableList<Node> textDisplay = option.getChildren();
		NodeText[] text = new NodeText[textDisplay.size()];
		for(int i = 0; i < text.length; i++){
			TextArea area = (TextArea) textDisplay.get(i);
			text[i] = new NodeText(area.getText(), Integer.
					parseInt(area.getId()));
		}
		return text;
	}
}
