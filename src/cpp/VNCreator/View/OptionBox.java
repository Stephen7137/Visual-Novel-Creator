package cpp.VNCreator.View;

import cpp.VNCreator.Controller.TextManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class OptionBox extends TextBox{
	
	@FXML
	private Button up;
	
	@FXML
	private Button down;
		
	@FXML
	private void moveUp(){
		option.shiftUp(id);
	}
	
	@FXML
	private void moveDown(){
		option.shiftDown(id);
	}
	
	@FXML
	private void delete(){
		option.delete(id);
	}
	
	public void dissableUp(){
		up.disableProperty().set(true);
	}
	
	public void dissableDown(){
		down.disableProperty().set(true);
	}
}
