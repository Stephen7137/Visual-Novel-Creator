package cpp.VNCreator.View;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

public class StoryEditor {
	
	@FXML
	protected AnchorPane textArea;
	
	@FXML
	protected TabPane canvas;
	
	public void createStage( AnchorPane overview, AnchorPane scene, AnchorPane textArea ){
		Tab tab = new Tab("Overview");
		tab.setContent(overview);
		canvas.getTabs().add(tab);
		tab = new Tab("Scene Editor");
		tab.setContent(scene);
		canvas.getTabs().add(tab);
		
		textArea.getChildren().add(textArea);		
	}
}
