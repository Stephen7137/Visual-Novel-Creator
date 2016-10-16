package cpp.VNCreator.Controller;

import java.util.ArrayList;
import java.util.Optional;

import cpp.VNCreator.Model.SearchNode;
import cpp.VNCreator.Model.Story;
import cpp.VNCreator.View.Editor;
import cpp.VNCreator.View.SceneEditor;
import cpp.VNCreator.View.SimConsole;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Controller {
	
	private ChapterEditor chEditor;
	private CanvasManager cnvsManager;
	private ProjectManager manager;
	private SimConsole console;
	private SceneEditor sEditor;
	private Editor editor;
	private ImageLoader imgLoader;
	
	private Point2D mouse;
	private Point2D oldPos;
	private boolean onSelected;
	private boolean onConnect;
	
	public void startUp(Canvas canvas, Stage primaryStage, Editor editor, SceneEditor sEditor) {
		this.sEditor = sEditor;
		this.editor = editor;
		manager = new ProjectManager(primaryStage);
		cnvsManager = new CanvasManager(canvas);

		imgLoader = new ImageLoader(primaryStage);
		Story story = new Story();
		chEditor = new ChapterEditor(story.getTree());
		manager.setStory(story, cnvsManager, chEditor);
	}
	
	public void next(int n){
		if(chEditor.next(n)){
			update();
		}		
	}
	
	public void updateSel(){
		cnvsManager.setSelected(chEditor.getSelectedID());
		if(chEditor.getSelected() != null){
			editor.update(chEditor.getSelected());
		}else{
			editor.clear();
		}
	}
	
	public void update(){
		if(chEditor.getCurrentNode() != null){
			console.update(chEditor.getCurrentNode());
			sEditor.update(chEditor.getCurrentNode());
		}else{
			console.clear();
			sEditor.clear();
		}
	}

	public void back() {
		chEditor.back();
		update();		
	}
	
	public void delete(){
		delete(cnvsManager.onNode(mouse.getX(), mouse.getY()));		
	}
	
	public void delete(int id){
		if(id != -1){
			if(!chEditor.isEmpty(id)){
				if(!deleteError()) return;
			}
			chEditor.delete(id);
			cnvsManager.delete(id);
			updateSel();
		}		
	}
	
	/**
	 * Create a alert box if user is trying to delete a node that has
	 * text in it.
	 * @return user input.
	 */
	private boolean deleteError(){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete Node");
		alert.setHeaderText(null);
		alert.setContentText("Node contains Text!\nAre you sure "
				+ "you want to delte node?");
		
		ButtonType buttonYes = new ButtonType("Yes");
		ButtonType buttonNo = new ButtonType("No");
		
		alert.getButtonTypes().setAll(buttonYes, buttonNo);
		
		Optional<ButtonType> results = alert.showAndWait();
		if(results.get() == buttonYes) return true;
		return false;
	}
	
	public void SetSelected(int ID){
		chEditor.setSelected(ID);
		cnvsManager.setSelected(ID);
		update();
	}
	
	
	public void setCurrent(){
		chEditor.setCurrent();
		updateSel();
	}
	
	public void setStart(){
		cnvsManager.setStart();
		chEditor.setStart();
	}

	public void moseRelease(MouseEvent e) {
		onSelected = false;
		if(onConnect){
			chEditor.connect(cnvsManager.onNode(e.getX(),e.getY()), cnvsManager.getConnected());
			cnvsManager.resetOnConnect();
			updateSel();
			onConnect = false;
		}
		oldPos = null;
	}

	public void mousePress(MouseEvent e) {
		if(e.getButton() == MouseButton.PRIMARY){
			
			if( e.getClickCount() == 2){
				if(cnvsManager.onSelected(e.getX(),e.getY())){
					onConnect = true;
				}else{
					onConnect = false;
				}
			}
			//TODO
			int id = cnvsManager.onNode(e.getX(),e.getY());
			if(!chEditor.isSelect(id)){
				chEditor.setSelected(id);
				cnvsManager.setSelected(id);
				updateSel();
			}
		}
	}

	public void onDrag(MouseEvent e) {
		//TODO
		if( !onConnect && !onSelected && cnvsManager.onSelected(e.getX(),e.getY())) onSelected = true;
		if( !onSelected && !onConnect && cnvsManager.onConnect(e.getX(),e.getY())){
			chEditor.setSelected(cnvsManager.getSelected());
			updateSel();
			onConnect = true;
		}
		if(oldPos != null && e.getButton() == MouseButton.PRIMARY){
			if(onSelected){
				cnvsManager.moveNode(oldPos.getX() - e.getScreenX(),
						oldPos.getY() - e.getScreenY());
			}else if(onConnect){
				cnvsManager.drawConnect(e.getX(),e.getY());
			}else{
				cnvsManager.moveScreen(oldPos.getX() - e.getScreenX(),
						oldPos.getY() - e.getScreenY());
			}			
		}
		oldPos = new Point2D(e.getScreenX(), e.getScreenY());
	}
	
	public void highlight(int id) {
		// TODO Auto-generated method stub
		
	}

	public Object reDraw() {
		cnvsManager.update();// TODO Auto-generated method stub
		return null;
	}

	public void addBookmark() {
		// TODO Auto-generated method stub
		
	}

	public void removeBookmark() {
		// TODO Auto-generated method stub
		
	}

	public void save() {
		// TODO Auto-generated method stub
		
	}

	public void saveAs() {
		// TODO Auto-generated method stub
		
	}

	public void load() {
		// TODO Auto-generated method stub
		
	}

	public void export() {
		manager.export();
	}

	public boolean selIsNull() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean inBookmark() {
		// TODO Auto-generated method stub
		return false;
	}

	public void loadBackground() {
		// TODO Auto-generated method stub
		imgLoader.loadBackground();
		sEditor.loadBackIcon(imgLoader.getBackground());
	}
	
	public void loadSprite(){
		imgLoader.loadActor();
		sEditor.loadSpriteIcon(imgLoader.getSprite());
	}

	public ArrayList<SearchNode> getAllNode() {
		// TODO Auto-generated method stub
		return null;
	}

	public void searchTree() {

		//DisplaySearch search = new DisplaySearch();
		//setSelected(search.getSearch(controller.getAllNode(),primaryStage));
		// TODO Auto-generated method stub
		
	}

	public void searchNoParent() {

		//DisplaySearch search = new DisplaySearch();
		//setSelected(search.getSearch(cHeditor.getNoParentNode(),primaryStage,
		//		cHeditor.getSelectedKey()));
		// TODO Auto-generated method stub
		
	}

	public void searchNoChildren() {

		//DisplaySearch search = new DisplaySearch();
		//setSelected(search.getSearch(cHeditor.getNoChildNode(),primaryStage,
		//		cHeditor.getSelectedKey()));
		// TODO Auto-generated method stub
		
	}

	public void searchBookmarks() {
		// TODO Auto-generated method stub

		//DisplaySearch search = new DisplaySearch();
		//setSelected(search.getSearch(cHeditor.getBookMark(),owner,
		//		cHeditor.getSelectedKey()));
	}

	public boolean vallidate() {
		return chEditor.validate();
	}

	public void createText() {
		cnvsManager.createNode(mouse.getX(), mouse.getY(), chEditor.createText());
		updateSel();
	}
	
	public void createOption(){
		cnvsManager.createNode(mouse.getX(), mouse.getY(), chEditor.createOption());
		updateSel();
	}

	public void setLocation(double x, double y) {
		mouse = new Point2D(x,y);
	}
}
