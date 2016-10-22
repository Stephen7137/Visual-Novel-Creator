package cpp.VNCreator.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import cpp.VNCreator.Model.Story;
import cpp.VNCreator.View.Main;
import cpp.VNCreator.View.NewProject;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
//import javax.Json.Json;

/**
 * Saves and loads {@link ProjectManager#editor} from a file {@link #file}.
 * If {@link #file} is null then {@link #saveAs()} gets the file name from
 * from the user.
 * @author Stephen
 *
 */
public class ProjectManager {
	
	ChapterEditor editor;
	CanvasManager cnvsManager;
	SaveProject save;
	FileChooser fileChooser;
	File file;
	Stage primaryStage;
	
	public ProjectManager(Stage primaryStage, Story story, CanvasManager cnvsManager,
			ChapterEditor editor) {
		this.editor = editor;
		this.cnvsManager = cnvsManager;
		this.primaryStage = primaryStage;
		save = new SaveProject(story);
		save.addManagers(cnvsManager, editor);
		fileChooser = new FileChooser();
		setFileType();
		fileChooser.setInitialDirectory(new File(System.
				getProperty("user.home")));
	}
		
	/**
	 * setFileType sets fileChooser to be .project
	 */
	private void setFileType(){
		fileChooser.getExtensionFilters().clear();
	}

	/**	 * Checks to see if {@link #file} is null, if not then calls 
	 * {@link #saveChapter()} If file is not null then {@link #editor} has 
	 * been saved before or has been loaded from a file.
	 */
	public void save(){
		saveProject();
	}

	/**
	 * Ask the user for location of where to save the project and saves the 
	 * location to {@link #file} for later uses.
	 */
	public void newProject(){
		
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("NewProject.fxml"));
		Stage stage = null;
		try {
			stage = new Stage();
			stage.setScene(new Scene(loader.load()));
		} catch (IOException e) {}
		
		NewProject nwProj = loader.getController();
		nwProj.showAndWait(stage,this);
		try {
			
			stage.setTitle("New Project");
			stage.setResizable(false);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
        if (file != null) {
        	
        	System.out.println(file.toString());
        	System.out.println(file.mkdirs());
        	//saveProject();
        }
	}
	
	/**
	 * Creates SaveProject and saves the object to a file with the file name
	 * the same as the Chapter title.
	 */
	private void saveProject(){
		try {
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			save.update(cnvsManager, editor);
			oos.writeObject(save);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Export saveProject to a json to be used in external program.
	 */
	public void export(){
		//JsonObject jsonStory;
		fileChooser.getExtensionFilters().clear();
		fileChooser.getExtensionFilters().add(
				new FileChooser.ExtensionFilter("Story file", "*.story"));
		File exportfile = fileChooser.showSaveDialog(primaryStage);
		if(exportfile != null){
			
			try {
				FileOutputStream fos = new FileOutputStream(exportfile);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(save.getStory());
				oos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		setFileType();
	}
	
	/**
	 * load Opens a specified file and loads save info into
	 * CanvasManager and ChapterEditor.
	 */
	public void load(){
		file = fileChooser.showOpenDialog(new Stage());
		if(file != null){
			try {
				FileInputStream fos = new FileInputStream(file);
				ObjectInputStream oos = new ObjectInputStream(fos);
				save = (SaveProject) oos.readObject();
				save.loadProject(cnvsManager, editor);
				oos.close();
			} catch (IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public ChapterEditor getChapter(){
		return editor;
	}

	public void setDirectory(File file) {
		// TODO Auto-generated method stub
		if(file.mkdir()){
			File tmp = (new File(file + "\\Background"));
			tmp.mkdir();
			tmp = (new File(file + "\\Actors"));
			tmp.mkdir();
			
		}else{
			System.out.println("Directory not made.");
		}
		
	}
}
