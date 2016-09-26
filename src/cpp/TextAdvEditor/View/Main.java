package cpp.TextAdvEditor.View;
	
import java.io.IOException;
import java.util.Random;

import cpp.TextAdvEditor.CanvasManager;
import cpp.TextAdvEditor.ChapterEditor;
import cpp.TextAdvEditor.ImageLoader;
import cpp.TextAdvEditor.ProjectManager;
import cpp.TextAdvEditor.Model.Story;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;

/**
 * TAeditor is a editor for making text adventures with
 * branching story lines. The story is able to be edited
 * using a integrative canvas giving a visual representation
 * of the Story tree and a text editor to change the text
 * of each node. There is a console simulation to see how
 * the text will look ran through a console like program.
 * The Story is able to be saved and loaded to keep the data
 * that was add or changed. The export provides a json to be
 * used with outside program.
 * 
 * @author Stephen Jackson
 *
 */
public class Main extends Application {
	
	int height = 600;
	int width = 800;
	TextAdvEditorControler mainController;
	
	/**
	 * Sets up the default window to be shown
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			
			
			Scene scene = createScene(new ProjectManager(primaryStage),
					primaryStage);
			primaryStage.setScene(scene);
			primaryStage.setTitle("TA Editor");
			primaryStage.setMaximized(true);
			primaryStage.setMinHeight(primaryStage.getHeight());
			primaryStage.setMinWidth(primaryStage.getWidth());
			primaryStage.setResizable(true);
			mainController.start();
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets up widow and connects the controllers together.
	 * Adds tabs to the main window and allows the controllers to have
	 * access to the story editor.
	 * @param projectManager
	 * @param primaryStage
	 * @return the main scene to be show to the user.
	 * @throws IOException if .fxml files cannot be found or open.
	 */
	private Scene createScene(ProjectManager projectManager, 
			Stage primaryStage) throws IOException{
		
		Random ran = new Random();
		Story story = new Story(ran.nextInt(10000));
		ChapterEditor cHeditor = new ChapterEditor(story.getSrtCh().getID(),
				story.getSrtCh().getTree());
		CanvasManager cnvsManager = new CanvasManager(
				story.getSrtCh().getID());
		projectManager.setStory(story, cnvsManager, cHeditor);
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("Text Adv Editor GUI.fxml"));
		Scene scene = new Scene(loader.load());
		mainController = loader.getController();
		mainController.setWriteChapter(projectManager, primaryStage);
		
		loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("SimConsole.fxml"));
		mainController.addTab("Console", loader.load());
		SimConsole console = loader.getController();
		console.cHeditor(cHeditor);
		
		loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("SceneEditor.fxml"));
		mainController.addTab("Scene Editor", loader.load());
		//SimConsole console = loader.getController();
		//console.cHeditor(cHeditor);
		
		loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("Editor.fxml"));
		mainController.addTab("Overview", loader.load());
		Editor editor = loader.getController();
		editor.setCanvasManger(cnvsManager, cHeditor);
		mainController.setData(editor, cHeditor, new ImageLoader(primaryStage));
		
		return scene;
	}
			
	public static void main(String[] args) {
		launch(args);
	}
}
