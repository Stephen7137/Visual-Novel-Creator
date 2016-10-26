package cpp.VNCreator.Controller;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import cpp.VNCreator.Controller.SaveProject.*;
import cpp.VNCreator.Model.ColorParser;
import cpp.VNCreator.Model.NodeType.nodeType;
import cpp.VNCreator.Model.Story;
import cpp.VNCreator.Model.TreePoint;
import cpp.VNCreator.Node.*;
import cpp.VNCreator.View.Main;
import cpp.VNCreator.View.NewProject;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;
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
	
	final String bckFodler = File.separator + "Background";
	final String actFolder = File.separator + "Actors";
	
	FileChooser imagechooser;
	Controller controller;
	FileChooser fileChooser;
	File file;
	Stage primaryStage;
	Gson gson;
	
	public ProjectManager(Stage primaryStage, Controller controller) {
		this.controller = controller;
		this.primaryStage = primaryStage;
		fileChooser = new FileChooser();
		setFileType();
		fileChooser.setInitialDirectory(new File(System.
				getProperty("user.home")));
		
		imagechooser = new FileChooser();
		imagechooser.setInitialDirectory(new File(System.
				getProperty("user.home")));
		imagechooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
            );
		
		//List<SaveNode> list = new ArrayList<SaveNode>();
		
		gson = new GsonBuilder()
	             .disableHtmlEscaping()
	             .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
	             .setPrettyPrinting()
	             .serializeNulls()
	             //.registerTypeHierarchyAdapter(list.getClass(), new CustomParser())
	             .registerTypeAdapter(Color.class, new ColorParser())
	             .create();
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
		
		try(Writer writer = new FileWriter(file + ".json")){
			gson.toJson(new SaveProject(controller.getStartID(),
					controller.getTree(), controller.getBookmark(), 
					controller.getTreePoint()), writer);
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
		SaveProject save = null;
		try(JsonReader reader = new JsonReader( new FileReader(
				new File(file.toString() + File.separator + file.getName() + ".json")))){
			save = gson.fromJson(reader, SaveProject.class);
		} catch (Exception e) {e.printStackTrace();}
		
		
		Hashtable<Integer, Node> tree =	createStory(save.getTree());
		Story story = new Story(tree.get(save.start), tree);
		controller.loadProject(story, createBookmark(save,story), createCanvas(save,story));
		
	}
	
	private Hashtable<Integer, Node> createStory(ArrayList<SaveNode> save){
		Hashtable<Integer, Node> tree = new Hashtable<Integer, Node>();
		for(SaveNode node : save){
			if(node.type == nodeType.Option){
				ArrayList<OptionText> oNode = new ArrayList<OptionText>();
				for(SaveOptionText oText : ((SaveOption)node).children){
					oNode.add(new OptionText(oText.title, oText.title));
				}
				tree.put(node.id, new Option(node.id, node.title, node.text, oNode));
			}else{
				tree.put(node.id, new Text(node.id, node.title, node.text));
			}
		}
		
		for(SaveNode node : save){
			Node tmpNode = tree.get(node.id);
			for(int id : node.parent){
				tmpNode.addParent(tree.get(id));
			}
			if(node.type == nodeType.Option){
				for(SaveOptionText sText : ((SaveOption)node).children){
					if(sText.id != -1){
						for(OptionText text : ((Option)tmpNode).getChildren()){
							if(text.getTitle().equals(sText.title)) 
								text.setNode(tree.get(sText.id));
						}
					}					
				}
			}else{
				((Text)tmpNode).setChild(tree.get(((SaveText)node).child));
			}
		}
		return tree;
	}
	
	private ArrayList<Node> createBookmark(SaveProject save, Story story){
		ArrayList<Node> bookmark = new ArrayList<Node>();
		for(int i : save.bookmark){
			bookmark.add(story.getNode(i));
		}
		return bookmark;
	}
	
	private Hashtable<Integer, TreePoint> createCanvas(SaveProject save, Story story){
		Hashtable<Integer,TreePoint> lookup = new Hashtable<Integer,TreePoint>();
		for(SaveTreePoint node : save.canvas){
			lookup.put(node.node, new TreePoint(node.x, node.y, node.height,
					node.width, node.color, story.getNode(node.node)));
		}
		return lookup;
	}

	public void setDirectory(File file) {
		if(file.mkdir()){
			File tmp = (new File(file + bckFodler));
			tmp.mkdir();
			tmp = (new File(file + actFolder));
			tmp.mkdir();
			this.file = new File(file + File.separator + file.getName());
			controller.verfiedDir();
		}
	}

	public ArrayList<File> importBackground() {
		return loadImage(bckFodler );
	}

	public ArrayList<File> importActor() {
		return loadImage(actFolder);
	}
	
	public ArrayList<File> loadImage(String folder){
		List<File> images = imagechooser.showOpenMultipleDialog(primaryStage);
		ArrayList<File> loaded = new ArrayList<File>();
		for(File file : images){
			File tmp = new File(this.file + folder + File.separator + file.getName());
			if(tmp.exists()){
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Copy");
				alert.setHeaderText("File already exist");
				alert.setContentText("Replace old image with new image?");
				alert.getButtonTypes().setAll( ButtonType.YES, ButtonType.NO);
				
				Optional<ButtonType> results = alert.showAndWait();
				if(results.get() == ButtonType.NO) continue;
			}
			try {
				Files.copy(file.toPath(), tmp.toPath(), StandardCopyOption.REPLACE_EXISTING);
				loaded.add(tmp);
			} catch (IOException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Importer error!");
				alert.setHeaderText("File could not copy file.");
				alert.setContentText(file + "\n" + tmp);
			}
		}
		return loaded;
	}
}
