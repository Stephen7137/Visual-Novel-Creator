package cpp.VNCreator.View;

import java.io.File;

import cpp.VNCreator.Controller.ProjectManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class NewProject {
	
	File file;
	Stage stage;
	ProjectManager manger;
	
	@FXML
	private TextField directory;
	
	@FXML
	private TextField title;
	
	@FXML
	private void getDirectory(){
		DirectoryChooser directory = new DirectoryChooser();
		
		directory.setInitialDirectory(file);
		file = directory.showDialog(stage);
		updateText();
	}
	
	@FXML
	private void ok(){
		manger.setDirectory(new File(file + "\\" + title.getText()));
		cancel();
	}
	
	@FXML
	private void cancel(){
		stage.close();
	}
	
	private void updateText(){
		directory.setText(file.toString());
	}
	
	public void showAndWait(Stage stage, ProjectManager manger){
		this.stage = stage;
		this.manger = manger;
		file = new File(System.getProperty("user.home"));
		updateText();
	}
}
