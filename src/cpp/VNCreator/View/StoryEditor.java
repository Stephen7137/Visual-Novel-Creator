package cpp.VNCreator.View;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

public class StoryEditor {
	
	@FXML
	private TabPane textArea;
	
	@FXML
	private TabPane canvas;
	
	public void loadOverview( AnchorPane overview ){
		addTab( overview, "Overview");
				
	}
	
	public void loadScene(AnchorPane scene){
		addTab(scene, "Scene Editor");
	}
	
	private void addTab(AnchorPane pane, String name){
		Tab tab = new Tab(name);
		tab.setContent(pane);
		canvas.getTabs().add(tab);
	}
	
	public TabPane getTextArea(){
		return textArea;
	}
}
