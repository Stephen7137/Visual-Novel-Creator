package cpp.VNCreator.Controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Optional;

import cpp.VNCreator.Model.SearchNode;
import cpp.VNCreator.Model.Story;
import cpp.VNCreator.Model.TreePoint;
import cpp.VNCreator.Node.Node;
import cpp.VNCreator.View.MainScene;
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
	private ProjectManager save;
	private SimConsole console;
	private SceneEditor sEditor;
	private TextManager textManager;
	private ImageLoader imgLoader;
	private MainScene mainScene;
	Story story;
	
	private Point2D mouse;
	private Point2D oldPos;
	private boolean onSelected;
	private boolean onConnect;
	
	public void startUp(Canvas canvas, Stage primaryStage, TextManager textManager, SceneEditor sEditor, 
				MainScene mainScene, SimConsole console) {
		this.console = console;
		this.mainScene = mainScene;
		this.sEditor = sEditor;
		this.textManager = textManager;
		
		cnvsManager = new CanvasManager(canvas);
		imgLoader = new ImageLoader();
		story = new Story();
		chEditor = new ChapterEditor(story.getTree());
		save = new ProjectManager(primaryStage,this);
		
		sEditor.setLoader(imgLoader);
	}
	
	public void next(int n){
		if(chEditor.next(n)){
			update();
		}		
	}
	
	public void nextSelect(int n){
		chEditor.nextSelect(n);
		updateSel();
	}
	
	public void backSelect(){
		chEditor.backSelect();
		updateSel();
	}
	
	public void forwardSelect(){
		chEditor.forwardSelect();
		updateSel();
	}
	
	public void updateSel(){
		cnvsManager.setSelected(chEditor.getSelectedID());
		if(chEditor.getSelected() != null){
			textManager.update(chEditor.getSelected());
			sEditor.setNode(chEditor.getSelected());
		}else{
			textManager.clear();
		}
	}
	
	public void updateScene(){
		cnvsManager.update();
		//sEditor.updateText();
	}
	
	public void update(){
		if(chEditor.getCurrentNode() != null){
			console.update(chEditor.getCurrentNode());
			//sEditor.update(chEditor.getCurrentNode());
		}else{
			console.clear();
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
		story.setStart(chEditor.getStart());
	}

	public void mouseRelease(MouseEvent e) {
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
			
			int id = cnvsManager.onNode(e.getX(),e.getY());
			if(e.getClickCount() >= 2){
				if(cnvsManager.onConnect(e.getX(),e.getY()) && cnvsManager.isConnected()){
					System.out.println("delete");
					chEditor.disconnect(cnvsManager.getSelected(), cnvsManager.getConnected());
					cnvsManager.resetOnConnect();
					updateSel();
				}
			}
			if(!chEditor.isSelect(id)){
				chEditor.setSelected(id);
				cnvsManager.setSelected(id);
				updateSel();
			}
		}
	}

	public void onDrag(MouseEvent e) {

		if( !onConnect && !onSelected && cnvsManager.onSelected(e.getX(),e.getY())) onSelected = true;
		if( !onSelected && !onConnect && cnvsManager.onConnect(e.getX(),e.getY())
				&& !cnvsManager.isConnected()){
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

	public void reDraw() {
		cnvsManager.update();
	}

	public void addBookmark() {
		chEditor.addBookmark();
	}

	public void removeBookmark() {
		chEditor.removeBookmark();		
	}

	public void save() {
		save.save();		
	}

	public void newProject() {
		save.newProject();
	}

	public void load() {
		save.load();
	}

	public void export() {
		save.export();
	}

	public boolean selIsNull() {
		return chEditor.getSelected() == null;
	}

	public boolean inBookmark() {
		return chEditor.currentBookmark();
	}

	public void loadBackground() {
		imgLoader.loadBackground(save.importBackground());
		sEditor.loadBackIcon(imgLoader.getBackground());
	}
	
	public void loadSprite(){
		imgLoader.loadActor(save.importActor());
		sEditor.loadActorIcon(imgLoader.getSprite());
	}
	
	public void loadTextBackground() {
		imgLoader.loadTextBack(save.importTextBack());
		sEditor.loadTextBackIcon(imgLoader.getTextBack());		
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

	public void verfiedDir() {
		mainScene.enable();
	}

	public int getStartID() {
		return chEditor.getStart() != null ? chEditor.getStart().getID() : -1;
	}

	public ArrayList<Node> getTree() {
		return new ArrayList<Node>(story.getTree().values());
	}

	public ArrayList<Node> getBookmark() {
		return chEditor.getBookMark();
	}

	public ArrayList<TreePoint> getTreePoint() {
		return cnvsManager.getTree();
	}

	public void loadProject(Story story, ArrayList<Node> bookmark, 
			Hashtable<Integer, TreePoint> lookup, ArrayList<File> background,
			ArrayList<File> actors, ArrayList<File> textBack) {
		this.story = story;
		chEditor.load(story.getStart(), story.getTree(), bookmark);
		cnvsManager.load(lookup);
		mainScene.enable();

		imgLoader.loadBackground(background);
		imgLoader.loadActor(actors);
		imgLoader.loadTextBack(textBack);
		sEditor.loadBackIcon(imgLoader.getBackground());
		sEditor.loadActorIcon(imgLoader.getSprite());
		sEditor.loadTextBackIcon(imgLoader.getTextBack());
	}

	public void closeSave() {
		// TODO Auto-generated method stub
		
	}
}
