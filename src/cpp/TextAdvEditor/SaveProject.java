package cpp.TextAdvEditor;

import java.io.Serializable;
import java.util.ArrayList;

import cpp.TextAdvEditor.Model.Chapter;
import cpp.TextAdvEditor.Model.Story;
import cpp.TextAdvEditor.Model.Text;
import cpp.TextAdvEditor.Model.TreePoint;

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
	
	private ArrayList<SaveCanvas> canvas;
	private ArrayList<SaveEditor> editor;
	private int currentChapterID;
	private Story story;
	
	public SaveProject(Story story){
		canvas = new ArrayList<SaveCanvas>();
		editor = new  ArrayList<SaveEditor> ();
		this.story = story;
	}
	
	public void addManagers(CanvasManager cnvsManager, ChapterEditor chEditor){
		currentChapterID = chEditor.getCHID();
		canvas.add(new SaveCanvas(cnvsManager));
		editor.add(new SaveEditor(chEditor));
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
		private ArrayList<TreePoint> canvas;
		private int chapterID;
		
		public SaveCanvas(CanvasManager cnvsManager){
			update(cnvsManager);
		}
		
		public void loadCanvas(CanvasManager cnvsManager){
			cnvsManager.load(chapterID, canvas);
		}
		
		public void update(CanvasManager cnvsManager){
			chapterID = cnvsManager.getID();
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
		private ArrayList<Text> bookmark;
		private ArrayList<Text> noChild;
		private ArrayList<Text> noParent;
		private Text currentNode;
		private int chapterID;
		
		public SaveEditor(ChapterEditor chEditor){
			update(chEditor);
		}
		
		public void loadEditor(ChapterEditor chEditor){
			chEditor.load(chapterID, currentNode, noParent, noChild, bookmark);
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
			chapterID = chEditor.getCHID();
		}
	}

	/**
	 * update refreshes the SaveEditor and SaveCanvas contents so that the
	 * newest information is saved to file.
	 * @param cnvsManager is the canvas editor.
	 * @param chEditor is the node editor.
	 */
	public void update(CanvasManager cnvsManager, ChapterEditor chEditor) {
		currentChapterID = chEditor.getCHID();
		story.getCH(currentChapterID).setStart(chEditor.getStart());
		searchCanvas(cnvsManager.getChapterID()).update(cnvsManager);
		searchEditor(chEditor.getCHID()).update(chEditor);	
	}
	
	/**
	 * searches array of SaveEditors with chapter id ID.
	 * @param ID chapterID
	 * @return SaveEditor with ChapterID ID.
	 */
	public SaveEditor searchEditor(int ID){
		for(int i = 0; i < editor.size();i++){
			if(editor.get(i).chapterID == ID) return editor.get(i);
		}
		return null;
	}
	
	/**
	 * searches array of SaveCanvas with chapter id ID.
	 * @param ID chapterID
	 * @return SaveCanvas with ChapterID ID.
	 */
	public SaveCanvas searchCanvas(int ID){
		for(int i = 0; i < canvas.size();i++){
			if(canvas.get(i).chapterID == ID) return canvas.get(i);
		}
		return null;
	}

	/**
	 * loadProject restores the CanvasManager and ChapterEditor from
	 * a previous save.
	 * @param cnvsManager
	 * @param chEditor
	 */
	public void loadProject(CanvasManager cnvsManager, 
			ChapterEditor chEditor) {
		Chapter chapter = story.getCH(currentChapterID);
		chEditor.loadTree(chapter.getTree(), chapter.getStart());
		searchEditor(currentChapterID).loadEditor(chEditor);
		searchCanvas(currentChapterID).loadCanvas(cnvsManager);
		cnvsManager.setStart(chEditor.getStart().getKey());
	}
	
	public Story getStory(){
		return story;
	}
}
