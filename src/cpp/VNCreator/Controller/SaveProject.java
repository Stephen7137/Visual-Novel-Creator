package cpp.VNCreator.Controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

import cpp.VNCreator.Model.Story;
import cpp.VNCreator.Model.TreePoint;
import cpp.VNCreator.Node.Node;

/**
 * SaveProject is used for saving the current
 * state that the canvas, story tree, and editor.
 * Saves info to a file .project and selected 
 * location.
 * @author Stephen Jackson
 *
 */
public class SaveProject implements Serializable{
	
	protected static final long serialVersionUID = 232323;
	
	private SaveCanvas canvas;
	private SaveEditor editor;
	private Story story;
	
	public SaveProject(Story story){
		this.story = story;
	}
	
	public void addManagers(CanvasManager cnvsManager, ChapterEditor chEditor){
		canvas = new SaveCanvas(cnvsManager);
		editor = new SaveEditor(chEditor);
	}
	
	/**
	 * SaveCanvas holds the canvas points and chapter Id to
	 * be saved to a file with out any non important data.
	 * 
	 * @author Stephen Jackson
	 *
	 */
	class SaveCanvas implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 65393544174854803L;
		private Hashtable<Integer, TreePoint> canvas;
		
		public SaveCanvas(CanvasManager cnvsManager){
			update(cnvsManager);
		}
		
		public void loadCanvas(CanvasManager cnvsManager){
			cnvsManager.load(canvas);
		}
		
		public void update(CanvasManager cnvsManager){
			canvas = cnvsManager.saveCanvas();
		}
	}
	
	/**
	 * SaveEditor collects all the important information
	 * from editor to be saved to file.
	 * 
	 * @author Stephen Jackson
	 *
	 */
	class SaveEditor implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1344309469972722400L;
		private ArrayList<Node> bookmark;
		private ArrayList<Node> noChild;
		private ArrayList<Node> noParent;
		private Node currentNode;
		
		public SaveEditor(ChapterEditor chEditor){
			update(chEditor);
		}
		
		public void loadEditor(ChapterEditor chEditor){
			chEditor.load(currentNode, noParent, noChild, bookmark);
		}

		/**
		 * update refreshes the variables so they are up to date
		 * when saving to a file.
		 * @param chEditor the node editor.
		 */
		public void update(ChapterEditor chEditor) {
			bookmark = chEditor.saveBookmark();
			noChild = chEditor.saveNoChild();
			noParent = chEditor.saveNoParent();
			currentNode = chEditor.saveCurrent();
		}
	}

	/**
	 * update refreshes the SaveEditor and SaveCanvas contents so that the
	 * newest information is saved to file.
	 * @param cnvsManager is the canvas editor.
	 * @param chEditor is the node editor.
	 */
	public void update(CanvasManager cnvsManager, ChapterEditor chEditor) {
		story.setStart(chEditor.getStart());
		editor.update(chEditor);
		canvas.update(cnvsManager);	
	}

	/**
	 * loadProject restores the CanvasManager and ChapterEditor from
	 * a previous save.
	 * @param cnvsManager
	 * @param chEditor
	 */
	public void loadProject(CanvasManager cnvsManager, 
			ChapterEditor chEditor) {
		chEditor.loadTree(story.getTree(), story.getStart());
		editor.loadEditor(chEditor);
		canvas.loadCanvas(cnvsManager);
		cnvsManager.setStart(chEditor.getStart().getID());
	}
	
	public Story getStory(){
		return story;
	}
}
