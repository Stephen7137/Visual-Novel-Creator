package cpp.VNCreator.View;

import cpp.VNCreator.Controller.OptionManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class OptionBox {
	
	@FXML
	private TextArea input;
	
	@FXML
	private Button up;
	
	@FXML
	private Button down;
	
	private OptionManager option;
	private int id;
	
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

	public void controller(OptionManager option, String text, int id) {
		this.id = id;
		this.option = option;
		input.setText(text);
		input.focusedProperty().addListener(
				new ChangeListener<Boolean>(){

			@Override
			public void changed(
					ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {
				if(newValue){
					option.highlight(id);
				}
			}
			
		});
	}
	
	public int getID(){
		return id;
	}
	
	public String getText(){
		return input.getText();
	}
	
}
