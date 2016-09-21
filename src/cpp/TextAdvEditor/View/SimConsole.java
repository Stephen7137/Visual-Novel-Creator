package cpp.TextAdvEditor.View;

import cpp.TextAdvEditor.ChapterEditor;
import javafx.beans.property.StringProperty;
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
	
	ChapterEditor cHeditor;
	
	/**
	 * Gets the user input and changes the enter button
	 * based on if the node has options. clears the text
	 * field after each input.
	 */
	@FXML
	private void next(){
		String usrInput = input.getText();
		if(usrInput.length() > 0){
			cHeditor.next(Integer.parseInt(usrInput));
			input.clear();
		}else{
			cHeditor.next(0);
		}
		if(cHeditor.curHasChildren()){
			next.setText("Enter");
		}else{
			next.setText("Next");
		}
	}
	
	@FXML
	private void back(){
		cHeditor.back();
	}

	/**
	 * Binds the current Notes text so that the console
	 * Allays displays the current nodes text.
	 * @param textArea the text area of current node
	 */
	public void setStory(StringProperty textArea) {
		this.story.textProperty().bind(textArea);	
	}

	/**
	 * Sets the current editor so that the user can
	 * proceed through the story.
	 * @param cHeditor
	 */
	public void cHeditor(ChapterEditor cHeditor) {
		this.cHeditor = cHeditor;
		this.story.textProperty().bind(cHeditor.getStory());
	}
}
