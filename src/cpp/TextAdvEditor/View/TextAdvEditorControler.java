package cpp.TextAdvEditor.View;

import java.util.Optional;

import cpp.TextAdvEditor.ChapterEditor;
import cpp.TextAdvEditor.ProjectManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Controls the main window TAedior will be working out of
 * including menu bar and tab window.
 * 
 * @author Stephen Jackson
 *
 */
public class TextAdvEditorControler {
		
	private ProjectManager manager;
	private ChapterEditor cHeditor;
	Editor editor;
	
	private Stage owner;	
	
	@FXML
	private TabPane tabPane;
	
	@FXML
	private MenuItem addBook;
	
	@FXML
	private MenuItem removeBook;
	
	@FXML
	private void addBookmark(){
		cHeditor.addBookmark();
	}
	
	@FXML
	private void removeBookmark(){
		cHeditor.removeBookmark();
	}
	
	
	@FXML
	private void setCurrent(){
		editor.setCurrent();
	}
	
	@FXML
	private void setStart(){
		editor.setStart();
	}
	
	/**
	 * Creates a window for the user to search through a 
	 * array of NodeText contains all bookmark.
	 */
	@FXML
	private void bookmark(){
		DisplaySearch search = new DisplaySearch();
		setSelected(search.getSearch(cHeditor.getBookMark(),owner,
				cHeditor.getSelectedKey()));
	}
	
	/**
	 * Creates a window for the user to search through a 
	 * array of NodeText contains all nodes with no child.
	 */
	@FXML
	private void noChild(){
		DisplaySearch search = new DisplaySearch();
		setSelected(search.getSearch(cHeditor.getNoChildNode(),owner,
				cHeditor.getSelectedKey()));
	}
	
	/**
	 * Creates a window for the user to search through a 
	 * array of NodeText contains all nodes with no Parent.
	 */
	@FXML
	private void noParent(){
		DisplaySearch search = new DisplaySearch();
		setSelected(search.getSearch(cHeditor.getNoParentNode(),owner,
				cHeditor.getSelectedKey()));
	}
	
	/**
	 * Creates a window for the user to search through a 
	 * array of NodeText contains all nodes.
	 */
	@FXML
	private void tree(){
		DisplaySearch search = new DisplaySearch();
		setSelected(search.getSearch(cHeditor.getAllNode(),owner,
				cHeditor.getSelectedKey()));
	}
	
	@FXML
	private void save(){
		manager.save();
	}
	
	@FXML
	private void saveAs(){
		manager.saveAs();
	}
	
	@FXML
	private void load(){
		manager.load();
	}
	
	/**
	 * Checks to make sure Story has a valid starting node
	 * and then exports Story else gives a warning. User can
	 * choose to continue exporting with errors.
	 */
	@FXML
	private void export(){
		if(cHeditor.validate()){
			manager.export();
		}else{
			if(noParents()){
				manager.export();
			}
		}
	}
	
	/**
	 * Validates add bookmark or removes bookmark menu item
	 * to allow book mark work if the selected node is not 
	 * null and toggles if it is in the bookmark list or not.
	 */
	@FXML
	private void update(){
		if(cHeditor.isNull()){
			addBook.setDisable(true);
			removeBook.setDisable(true);
		}else{
			if(cHeditor.currentBookmark()){
				addBook.setDisable(true);
				removeBook.setDisable(false);
			}else{
				addBook.setDisable(false);
				removeBook.setDisable(true);
			}
		}
		
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
		
		ButtonType buttonYes = new ButtonType("Yes");
		ButtonType buttonNo = new ButtonType("No");
		
		alert.getButtonTypes().setAll(buttonYes, buttonNo);
		
		Optional<ButtonType> results = alert.showAndWait();
		if(results.get() == buttonYes) return true;
		return false;
	}
	
	/**
	 * Sets the ProjectManager and current Stage to allow menu to
	 * save the Story.
	 * @param manager
	 * @param owner
	 */
	public void setWriteChapter(ProjectManager manager, Stage owner){
		this.owner = owner;
		this.manager = manager;	
	}
	
	/**
	 * Adds Tab to the stage for multiple to interact with the story
	 * @param string title of the tab
	 * @param load pane to be add to the tab
	 */
	public void addTab(String string, AnchorPane load) {
		Tab tab = new Tab();
		tab.setText(string);
		tab.setContent(load);
		tabPane.getTabs().add(tab);
	}
	
	private void setSelected(int key){
		editor.SetSelected(key);
	}

	/**
	 * Gets the controller of the tabs and allows for interaction
	 * of the Panels.
	 * @param editor
	 * @param cHeditor
	 */
	public void setData(Editor editor, ChapterEditor cHeditor) {
		this.editor = editor;
		this.cHeditor = cHeditor;
		update();
	}
}
