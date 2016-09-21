package cpp.TextAdvEditor.View;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * Creates a window with a combo box to allow user to select
 * a node from the list of titles.
 * 
 * @author Stephen Jackson
 *
 */
public class SearchNodes {
	
	DisplaySearch displaySearch;
	
	@FXML
	private TextField input;
	
	@FXML
	private ComboBox<String> selected;

	private ObservableList<String> list;

	@FXML
	private void cancel(){
		displaySearch.close();
	}
	
	/**
	 *  Sets the value that the combo box is set to
	 */
	@FXML
	private void setNode(){
		displaySearch.set(selected.getValue());
	}
	
	/**
	 * Allows the list to be edited by changing a search parameter in
	 * the text field input.
	 * 
	 * @param list of Node titles
	 * @param displaySearch
	 */
	public void setSearch(ObservableList<String>  list, 
			DisplaySearch displaySearch){
		this.displaySearch = displaySearch;
		this.list = list;
		input.textProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0,
					String arg1, String arg2) {
				updateList();
			}
			
		});
		updateList();
	}
	
	/**
	 * updates the combo box based on the input. When the input is
	 * empty the list will turn to the default list. Show the list if
	 * the input is not empty else hide the combo box.
	 */
	private void updateList(){
		
		ObservableList<String> buffer = FXCollections.observableArrayList();
		selected.getItems().clear();
		boolean hide = false;
		
		if(input.getText().equals("")){
			buffer = list;
			hide = true;
		}else{
			for(String item : list){
			
				if(item.toLowerCase().contains(input.getText()
						.toLowerCase())){
					buffer.add(item);
				}			
			}
		}
		selected.getItems().addAll(buffer);
		
		if(hide){
			selected.hide();
		}else{
			selected.show();
		}
	}
}
