package cpp.VNCreator.Controller;

import java.util.ArrayList;
import cpp.VNCreator.Model.SearchNode;
import cpp.VNCreator.Model.Story;
import cpp.VNCreator.Node.Node;
import cpp.VNCreator.View.DisplaySearch;
import cpp.VNCreator.View.Editor;
import cpp.VNCreator.View.SceneEditor;
import cpp.VNCreator.View.SimConsole;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
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
	private Point2D mouse;
	private Point2D oldPos;
	private boolean onSelected;
	
	public void startUp(Canvas canvas, Stage primaryStage, Editor editor) {
		this.editor = editor;
		manager = new ProjectManager(primaryStage);
		cnvsManager = new CanvasManager(canvas);

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
		if(chEditor.getSelected() != null){
			editor.update(chEditor.getSelected());
			cnvsManager.setSelected(chEditor.getSelectedID());
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

	public void delete(Node selected) {
		//TODO
		chEditor.delete();
		cnvsManager.delete();

	}
	
	
	public void SetSelected(int ID){
		chEditor.setSelected(ID);
		cnvsManager.setSelected(ID);
		update();
	}
	
	
	public void setCurrent(){
		chEditor.setCurrent(cnvsManager.getSelected());
	}
	
	public void setStart(){
		cnvsManager.setStart();
		chEditor.setStart();
	}

	public void moseRelease(MouseEvent e) {
		//TODO
		onSelected = false;
		oldPos = null;
//		if(onConnect){
//			if(cHeditor.isChild(cnvsManager.onNode(e.getX(),e.getY(),false))){
//				cnvsManager.disconnect(cHeditor.disconnect(
//						cnvsManager.onNode(e.getX(),e.getY(),false)));
//			}else{
//				cnvsManager.connect(cHeditor.connect(
//					cnvsManager.onNode(e.getX(),e.getY(),false)));
//			}
//			updateEditor();
//			onConnect = false;	
//		}
	}

	public void mousePress(MouseEvent e) {
		if(e.getButton() == MouseButton.PRIMARY){
			int press = e.getClickCount();
			
//			switch(cnvsManager.tools(e.getX(),e.getY())){
//			case 0:
//				cnvsManager.createNode(e.getX(),e.getY(), 
//						chEditor.getSelected());
//				break;
//			}
			if(cnvsManager.onSelected(e.getX(),e.getY())){
				onSelected = true;
			}
			if(cnvsManager.onConnect(e.getX(),e.getY())){
				//onConnect = true;
			}
			
			if(cnvsManager.onNode(e.getX(),e.getY(),true) >= 0){
				chEditor.setSelected(cnvsManager.getSelected());
				//TODO
				//updateEditor();
			}else if( press > 1){
				//disable();
				chEditor.setSelected(-1);
				//cnvsManager.resetSelected();
			}
		}
	}

	public void follow(MouseEvent e) {
		//TODO
		if(oldPos != null){
			if(onSelected){
				cnvsManager.moveNode(oldPos.getX() - e.getScreenX(),
						oldPos.getY() - e.getScreenY());
			}else{
				cnvsManager.moveScreen(oldPos.getX() - e.getScreenX(),
						oldPos.getY() - e.getScreenY());
			}			
		}
		oldPos = new Point2D(e.getScreenX(), e.getScreenY());
		
//		if(onConnect){
//			cnvsManager.drawConnect(e.getX(),e.getY(), cHeditor.isChild(
//					cnvsManager.onNode(e.getX(),e.getY(),false)));
//		}
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
		
	}

	public void loadActor() {
		// TODO Auto-generated method stub
		
	}

	public ArrayList<SearchNode> getAllNode() {
		// TODO Auto-generated method stub
		return null;
	}

	public void searchTree() {

		DisplaySearch search = new DisplaySearch();
		//setSelected(search.getSearch(controller.getAllNode(),primaryStage));
		// TODO Auto-generated method stub
		
	}

	public void searchNoParent() {

		DisplaySearch search = new DisplaySearch();
		//setSelected(search.getSearch(cHeditor.getNoParentNode(),primaryStage,
		//		cHeditor.getSelectedKey()));
		// TODO Auto-generated method stub
		
	}

	public void searchNoChildren() {

		DisplaySearch search = new DisplaySearch();
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

	public void setLocation(double x, double y) {
		mouse = new Point2D(x,y);
	}
}
