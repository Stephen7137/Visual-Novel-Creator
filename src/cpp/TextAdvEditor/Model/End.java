package cpp.TextAdvEditor.Model;

/**
 * Contains the next Chapter when the node
 * reaches the end of the tree.
 * 
 * @author Stephen Jackson
 *
 */
public class End extends Text {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7022159750827172354L;

	public End(int key) {
		super(key);
	}

	private String chName;
	
	public void setNextCh(String chName){
		this.chName = chName;
	}
	
	public String getNextFile(){
		return chName;
	}
}
