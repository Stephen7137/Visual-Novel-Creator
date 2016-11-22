package cpp.VNCreator.View;

import cpp.VNCreator.Controller.TextManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class TextBox {
	
	@FXML
	protected TextField title;
	
	@FXML
	protected TextArea text;
	
	protected TextManager option;
	protected int id;
	
	public void controller(TextManager option, String titleText, String textText, int id) {
		this.id = id;
		this.option = option;
		title.setText(titleText);
		this.text.setText(textText);
		
		title.textProperty().addListener((Observable, oldValue, newValue) -> {
			option.updateText(id, title.getText(), text.getText());
		});
		
		text.textProperty().addListener((Observable, oldValue, newValue) -> {
			option.updateText(id, title.getText(), text.getText());
		});
	}

	public int getID(){
		return id;
	}
	
}
