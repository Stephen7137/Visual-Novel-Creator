package cpp.VNCreator.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.Writer;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cpp.VNCreator.Model.Story;
import cpp.VNCreator.View.Main;
import cpp.VNCreator.View.NewProject;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
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
	
	Controller controller;
	FileChooser fileChooser;
	File file;
	Stage primaryStage;
	
	public ProjectManager(Stage primaryStage, Controller controller) {
		this.controller = controller;
		this.primaryStage = primaryStage;
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
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(primaryStage);
			stage.setTitle("New Project");
			stage.setResizable(false);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates SaveProject and saves the object to a file with the file name
	 * the same as the Chapter title.
	 */
	private void saveProject(){
		Gson gson = new GsonBuilder()
	             .disableHtmlEscaping()
	             .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
	             .setPrettyPrinting()
	             .serializeNulls()
	             .create();
		try(Writer writer = new FileWriter(file + ".json")){
			gson.toJson(new SaveProject(controller.getStartID(), controller.getTree(),
					controller.getBookmark(), controller.getTreePoint()), writer);
		} catch (IOException e) {}
	}
	
	/**
	 * Export saveProject to a json to be used in external program.
	 */
	public void export(){
		//JsonObject jsonStory;
//		fileChooser.getExtensionFilters().clear();
//		fileChooser.getExtensionFilters().add(
//				new FileChooser.ExtensionFilter("Story file", "*.story"));
//		File exportfile = fileChooser.showSaveDialog(primaryStage);
//		if(exportfile != null){
//			
//			try {
//				FileOutputStream fos = new FileOutputStream(exportfile);
//				ObjectOutputStream oos = new ObjectOutputStream(fos);
//				oos.writeObject(save.getStory());
//				oos.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		setFileType();
	}
	
	/**
	 * load Opens a specified file and loads save info into
	 * CanvasManager and ChapterEditor.
	 */
	public void load(){
		
		if(file == null ) file = new File(System.getProperty("user.home"));
		
		DirectoryChooser directory = new DirectoryChooser();
		
		directory.setInitialDirectory(file);
		file = directory.showDialog(primaryStage);
		Gson gson = new Gson();
		SaveProject save = null;
		try(Reader reader = new FileReader(new File(file.toString() + "\\" + file.getName() + ".json"))){
			save = gson.fromJson(reader, SaveProject.class);
		} catch (Exception e) {e.printStackTrace();} 
			System.out.println(save);
	}

	public void setDirectory(File file) {
		if(file.mkdir()){
			File tmp = (new File(file + "\\Background"));
			tmp.mkdir();
			tmp = (new File(file + "\\Actors"));
			tmp.mkdir();
			this.file = new File(file + "\\" + file.getName());
			controller.verfiedDir();
		}
	}
}
