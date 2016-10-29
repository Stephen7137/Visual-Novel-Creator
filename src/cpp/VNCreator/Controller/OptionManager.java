package cpp.VNCreator.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import cpp.VNCreator.Node.OptionText;
import cpp.VNCreator.View.Main;
import cpp.VNCreator.View.OptionBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 * Controls the Option text from each node and keeps track of
 * what node the text is pointing to. Clears panel when there is
 * no options.
 * 
 * @author Stephen Jackson
 *
 */
public class OptionManager {

	TabPane option;
	Controller controller;
	ArrayList<OptionText> children;
	ArrayList<OptionBox> optionBox;
	private int curTab;
	
	public OptionManager(TabPane option, Controller controller){
		this.controller = controller;
		this.option = option;
		
		option.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
		    @Override
		    public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
		    	if( newValue.intValue() == children.size()){
		    		addOption();
		    	}else if(newValue.intValue() > 0){		    		
		    		curTab = newValue.intValue();
		    	}
		    }
		}); 
		//TODO temp replace ussing CSS style file;
		option.setStyle("-fx-open-tab-animation: NONE; -fx-close-tab-animation: NONE;");
	}
	
	/**
	 * Creates the options panel and adds a activation listener
	 * to tell what box is active. Sets the text to oText of each
	 * node and sets the id to be returned latter.
	 * @param text all options
	 * @throws IOException 
	 */
	public void setOption(ArrayList<OptionText> arrayList){
		this.children = arrayList;
		curTab = 0;
		buildOption();
	}
	
	private void buildOption(){
		reset();
		optionBox = new ArrayList<OptionBox>();
		ObservableList<Tab> collection = FXCollections.observableArrayList();
		for(int i = 0; i < children.size(); i++){
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("OptionBox.fxml"));
			Tab tab = new Tab();
			tab.setText(Integer.toString(i+1));
			try {
				tab.setContent(loader.load());
			} catch (IOException e) {
				e.printStackTrace();
			}
			OptionBox console = loader.getController();
			optionBox.add(console);
			console.controller(this, children.get(i).getTitle(), children.get(i).getText(), i);
			if(i == 0) console.dissableUp();
			if(i == children.size()-1) console.dissableDown();			
			collection.add(tab);
			//option.getTabs().add(tab);			
		}
		Tab tab = new Tab();
		tab.setText("+");
		collection.add(tab);
		option.getTabs().addAll(collection);
		option.getSelectionModel().select(curTab);
	}

	private void addOption() {
		save();
		children.add(new OptionText());
		buildOption();
		controller.updateSel();
	}

	public void reset() {
		option.getTabs().clear();
	}

	public ArrayList<OptionText> save() {
		for(OptionBox box : optionBox){
			children.get(box.getID()).setText(box.getTitle(), box.getText());
		}
		return children;
	}
	
	public void shiftUp(int id) {
		curTab = id - 1;
		swap(id, id-1);
		buildOption();
	}

	public void shiftDown(int id) {
		curTab = id + 1;
		swap(id, id+1);
		buildOption();
	}
	
	private void swap(int i, int j){
		Collections.swap(children, i, j);
	}

	public void delete(int id) {		
		children.remove(children.get(id));
		curTab = 0;
		buildOption();
		controller.updateSel();
	}
}
