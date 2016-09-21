package cpp.TextAdvEditor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import cpp.TextAdvEditor.Model.Story;
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
	
	public ProjectManager(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	/**
	 * Checks to see if {@link #file} is null, if not then calls 
	 * {@link #saveChapter()} If file is not null then {@link #editor} has 
	 * been saved before or has been loaded from a file.
	 */
	public void save(){
		if(file == null){
			saveAs();
		}else{
			saveProject();
		}
	}

	/**
	 * Ask the user for location of where to save the project and saves the 
	 * location to {@link #file} for later uses.
	 */
	public void saveAs(){
		file = fileChooser.showSaveDialog(primaryStage);
        if (file != null) {
        	saveProject();
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

	/**
	 * setStory is Fist time setup so that the fileChooser is correct
	 * and all the story, ChapterEditor, and CanvasManager are properly
	 * formated to be saved to a file. 
	 * @param story
	 * @param cnvsManager
	 * @param editor
	 */
	public void setStory(Story story, CanvasManager cnvsManager,
			ChapterEditor editor) {
		this.editor = editor;
		this.cnvsManager = cnvsManager;
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
		fileChooser.getExtensionFilters().add(
				new FileChooser.ExtensionFilter("Project file", "*.project"));
	}
}
