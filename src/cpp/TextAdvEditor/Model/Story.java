package cpp.TextAdvEditor.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Holds all chapters and the 
 * title of the story as well as
 * point to the starting chapter;
 * 
 * @author Stephen Jackson
 *
 */
public class Story implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1561701032116116746L;
	private String strName;
	private Chapter start;
	private ArrayList<Chapter> chapter;
	
	public Story(int ID){
		chapter = new ArrayList<Chapter>();
		start = new Chapter(ID);
		this.chapter.add(start);
	}
	
	public Chapter getSrtCh(){
		return chapter.get(0);
	}
	
	public void setCHName(String chName){
		this.strName = chName;
	}
	
	public String getCHName(){
		return strName;
	}
	
	public int numOfNode(){
		return chapter.size();
	}

	public Chapter getCH(int chapterID) {
		return search(chapterID);
		
	}
	
	/**
	 * Searches through chapter array in search
	 * for chapter with id.
	 * @param chapterID
	 * @return chapter with id chapterID.
	 */
	private Chapter search(int chapterID){
		for(int i = 0; i < chapter.size(); i++){
			if(chapter.get(i).getID() == chapterID) return chapter.get(i);
		}
		return null;
	}
}
