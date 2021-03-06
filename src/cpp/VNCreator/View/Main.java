package cpp.VNCreator.View;
	
import java.io.IOException;
import cpp.VNCreator.Controller.Controller;
import cpp.VNCreator.Controller.TextManager;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
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
	
	/**
	 * Sets up the default window to be shown
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
						
			Scene scene = createScene(primaryStage);
			primaryStage.setScene(scene);
			primaryStage.setTitle("VN Editor");
			primaryStage.setMaximized(true);
			primaryStage.setMinHeight(700);
			primaryStage.setMinWidth(1000);
			primaryStage.setResizable(true);
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
	private Scene createScene(Stage primaryStage) throws IOException{
				
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("MainScene.fxml"));
		Scene scene = new Scene(loader.load());
		MainScene mainController = loader.getController();
		mainController.disable();
		
		Controller controller = new Controller();
		loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("SimConsole.fxml"));
		mainController.addTab("Console", loader.load());
		SimConsole console = loader.getController();
		console.controller(controller);
		
		loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("StoryEditor.fxml"));
		mainController.addTab("Story Editor", loader.load());
		StoryEditor storyEditor = loader.getController();
		
		loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("Editor.fxml"));
		storyEditor.loadOverview(loader.load());
		Editor editor = loader.getController();
		editor.setCanvasManger(controller);
		
		loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("SceneEditor.fxml"));
		storyEditor.loadScene(loader.load());
		SceneEditor sEditor = loader.getController();
		sEditor.controller(controller);
				
		mainController.setController(controller);
		controller.startUp(editor.getCanvas(), primaryStage, 
				new TextManager(storyEditor.getTextArea(), controller), sEditor,
				mainController, console);
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>(){

			@Override
			public void handle(WindowEvent arg0) {
				controller.closeSave();
			}
			
		});
		
		return scene;
	}
			
	public static void main(String[] args) {
		launch(args);
	}
}
