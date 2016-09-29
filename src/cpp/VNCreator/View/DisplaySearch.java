package cpp.VNCreator.View;

import java.io.IOException;
import java.util.ArrayList;

import cpp.VNCreator.Node.Node;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Creates the window for searching through a list of nodes
 * 
 * @author Stephen Jackson
 *
 */
public class DisplaySearch {

	String input;
	Stage stage;
	
	/**
	 * Creates a window and sets the default list of NodeText.
	 * @param list
	 * @param owner
	 * @param currentID
	 * @return id of the selected node.
	 */
	public int getSearch(ArrayList<Node> list, Stage owner, int currentID){
		
		stage = new Stage();
		input = null;
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("SearchNodes.fxml"));
		
		Scene scene = null;
		try {
			scene = new Scene(loader.load());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		SearchNodes search = loader.getController();
		search.setSearch(createList(list), this);
		
		stage.setScene(scene);
		stage.setTitle("TA Editor");
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(owner);
		stage.showAndWait();
		
		return getID(list, currentID);
	}
	
	/**
	 * Get the current id from the selected title.
	 * @param list
	 * @param currentID
	 * @return id of title
	 */
	private int getID(ArrayList<Node> list, int currentID){
		if(input==null){
			return currentID;
		}else{
			for(Node text : list){
				if(text.getText().equals(input)) return text.getID();
			}
		}
		
		return -1;
	}
	
	/**
	 * converts the list of NodeText into a observableArrayList.
	 * @param list
	 * @return
	 */
	private ObservableList<String> createList(ArrayList<Node> list){
		
		ObservableList<String> buffer =	FXCollections.observableArrayList();
		
		for(Node text : list){
			buffer.add(text.getText());
		}
		
		return buffer;
	}

	public void set(String value) {
		this.input = value;
		stage.close();
	}

	public void close() {
		input = null;
		stage.close();
	}
}
