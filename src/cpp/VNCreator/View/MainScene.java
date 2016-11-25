package cpp.VNCreator.View;

import cpp.VNCreator.Controller.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

/**
 * Controls the main window TAedior will be working out of
 * including menu bar and tab window.
 * 
 * @author Stephen Jackson
 *
 */
public class MainScene {

	private Controller controller;
	
	@FXML
	private TabPane tabPane;
	
	@FXML
	private Menu sprite;
	
	@FXML
	private Menu marker;
	
	@FXML
	private MenuItem save;
	
	@FXML
	private MenuItem export;
	
	@FXML
	private MenuItem addBook;
	
	@FXML
	private MenuItem removeBook;
	
	@FXML
	private MenuBar bar;
		
	@FXML
	private void addBookmark(){
		controller.addBookmark();
	}
	
	@FXML
	private void removeBookmark(){
		controller.removeBookmark();
	}
	
	
	@FXML
	private void setCurrent(){
		controller.setCurrent();
	}
	
	@FXML
	private void setStart(){
		controller.setStart();
	}
	
	/**
	 * Creates a window for the user to search through a 
	 * array of NodeText contains all bookmark.
	 */
	@FXML
	private void bookmark(){
		controller.searchBookmarks();
	}
	
	/**
	 * Creates a window for the user to search through a 
	 * array of NodeText contains all nodes with no child.
	 */
	@FXML
	private void noChild(){
		controller.searchNoChildren();
	}
	
	/**
	 * Creates a window for the user to search through a 
	 * array of NodeText contains all nodes with no Parent.
	 */
	@FXML
	private void noParent(){
		controller.searchNoParent();
	}
	
	/**
	 * Creates a window for the user to search through a 
	 * array of NodeText contains all nodes.
	 */
	@FXML
	private void tree(){
		controller.searchTree();
	}
	
	@FXML
	private void save(){
		controller.save();
	}
	
	@FXML
	private void newProject(){
		controller.newProject();
	}
	
	@FXML
	private void load(){
		controller.load();
	}
	
	/**
	 * Checks to make sure Story has a valid starting node
	 * and then exports Story else gives a warning. User can
	 * choose to continue exporting with errors.
	 */
	@FXML
	private void export(){
		if(!controller.vallidate()) noParents();
		controller.export();
	}
	
	@FXML
	private void importBackground(){
		controller.loadBackground();
	}
	
	@FXML
	private void importActor(){
		controller.loadSprite();
	}
	
	@FXML
	private void importTextBack(){
		controller.loadTextBackground();
	}
	
	/**
	 * Creates a Alert box and gets the user input of the user.
	 * notifies if a Node has no parents.
	 * @return the users input.
	 */
	private boolean noParents(){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Node with no Parent");
		alert.setHeaderText(null);
		alert.setContentText("Node contains no Parent!\n"
				+ "Node will be inaccessible in the Text Adventure.\n"
				+ "Are you sure you want to continue?");
		
		alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
		
		if(alert.showAndWait().get() == ButtonType.YES) return true;
		return false;
	}
	
	/**
	 * Adds Tab to the stage for multiple to interact with the story
	 * @param string title of the tab
	 * @param load pane to be add to the tab
	 */
	public void addTab(String string, AnchorPane load) {
		Tab tab = new Tab(string);
		tab.setContent(load);
		tabPane.getTabs().add(tab);
	}

	/**
	 * Gets the controller of the tabs and allows for interaction
	 * of the Panels.
	 * @param editor
	 * @param cHeditor
	 */
	public void setController(Controller controller) {
		this.controller = controller;
	}

	public void start() {
		//TODO
		//tabPane.disableProperty().set(true);
		//ObservableList<Menu> list = bar.getMenus();
		//mark.disableProperty().set(true);
		//image.disableProperty().set(true);
	}	
	
	/**
	 * Validates add bookmark or removes bookmark menu item
	 * to allow book mark work if the selected node is not 
	 * null and toggles if it is in the bookmark list or not.
	 */
	public void update(){
		if(controller.selIsNull()){
			addBook.setDisable(true);
			removeBook.setDisable(true);
		}else{
			if(controller.inBookmark()){
				addBook.setDisable(true);
				removeBook.setDisable(false);
			}else{
				addBook.setDisable(false);
				removeBook.setDisable(true);
			}
		}
	}
	
	public void disable(){
		tabPane.setDisable(true);
		sprite.setDisable(true);
		marker.setDisable(true);
		save.setDisable(true);
		export.setDisable(true);
	}
	
	public void enable(){
		tabPane.setDisable(false);
		sprite.setDisable(false);
		marker.setDisable(false);
		save.setDisable(false);
		export.setDisable(false);
	}
}
