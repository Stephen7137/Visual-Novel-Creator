package cpp.VNCreator.View;

import cpp.VNCreator.Controller;
import cpp.VNCreator.Model.NodeType.nodeType;
import cpp.VNCreator.Node.Node;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * Creates a console like experience while giving 
 * the user a cleaner text box that refresh each story
 * and allows for a more convent input.
 * 
 * @author Stephen Jackson
 *
 */
public class SimConsole {

	@FXML
	private TextField input;
	
	@FXML
	private Button next;
	
	@FXML
	private TextArea story;
	
	Controller controller;
	
	/**
	 * Gets the user input and changes the enter button
	 * based on if the node has options. clears the text
	 * field after each input.
	 */
	@FXML
	private void next(){
		String usrInput = input.getText();
		if(usrInput.length() > 0){
			controller.next(Integer.parseInt(usrInput));
			input.clear();
		}else{
			controller.next(0);
		}
	}
	
	@FXML
	private void back(){
		controller.back();
	}

	public void update(Node currentNode) {
		story.clear();
		
		if(currentNode.getType() == nodeType.Option){
			//TODO
//			Text[] oText = currentNode.getChild();
//			String oStory = "";
//			for(int i = 0; i < oText.length; i++ )
//			oStory = oStory + "\n" + (i+1) + ") " + 
//			oText[i].getOptText();
//			next.setText("Enter");
//			story.setText(currentNode.getText() + "\n" + oStory);
		}else{
			next.setText("Next");
			story.setText(currentNode.getText());
		}
	}

	public void clear() {
		story.clear();		
	}

	/**
	 * Sets the current editor so that the user can
	 * proceed through the story.
	 * @param cHeditor
	 */
	public void controller(Controller controller) {
		this.controller = controller;
	}
}
