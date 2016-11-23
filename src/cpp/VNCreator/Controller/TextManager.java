package cpp.VNCreator.Controller;

import java.io.IOException;
import java.util.ArrayList;
import cpp.VNCreator.Model.NodeType.nodeType;
import cpp.VNCreator.Node.Node;
import cpp.VNCreator.Node.Option;
import cpp.VNCreator.Node.OptionText;
import cpp.VNCreator.View.Main;
import cpp.VNCreator.View.OptionBox;
import cpp.VNCreator.View.TextBox;
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
public class TextManager {

	TabPane mainTab;
	Controller controller;
	
	Node node;
	private int curTab;
	private int tabNum;
	boolean update;
	
	public TextManager(TabPane mainTab, Controller controller){
		this.controller = controller;
		this.mainTab = mainTab;
		
		mainTab.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
		    @Override
		    public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
		    	if(update){
			    	if( node.getType() == nodeType.Option && newValue.intValue() == tabNum){
			    		addOption();
			    	}else if(newValue.intValue() >= 0){		    		
			    		curTab = newValue.intValue();
			    	}
		    	}
		    }
		}); 
		mainTab.setStyle("-fx-open-tab-animation: NONE; -fx-close-tab-animation: NONE;");
	}
	
	/**
	 *TODO update
	 * Creates the options panel and adds a activation listener
	 * to tell what box is active. Sets the text to oText of each
	 * node and sets the id to be returned latter.
	 * @param text all options
	 * @throws IOException 
	 */
	public void update(Node node){
		update = false;
		this.node = node;
		curTab = 0;
		buildOption();
		update = true;
	}
	
	private void buildOption(){
		clear();
		FXMLLoader loader = new FXMLLoader();
		ObservableList<Tab> collection = FXCollections.observableArrayList();
		Tab tab = new Tab("Text");
		loader.setLocation(Main.class.getResource("TextBox.fxml"));
		try {
			tab.setContent(loader.load());
			((TextBox)loader.getController()).controller(this, node.getTitle(), node.getText(), -1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		collection.add(tab);
		
		if(node.getType() == nodeType.Option){
			ArrayList<OptionText> optionNode = ((Option)node).getChildren();
			for(int i = 0; i < optionNode.size(); i++){
				loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("OptionBox.fxml"));
				tab = new Tab();
				tab.setText(Integer.toString(i+1));
				try {
					tab.setContent(loader.load());
				} catch (IOException e) {
					e.printStackTrace();
				}
				OptionBox console = loader.getController();
				console.controller(this, optionNode.get(i).getTitle(), 
						optionNode.get(i).getText(), i);
				if(i == 0) console.dissableUp();
				if(i == optionNode.size()-1) console.dissableDown();			
				collection.add(tab);			
			}
			tab = new Tab();
			tab.setText("+");
			collection.add(tab);			
		}
		
		mainTab.getTabs().addAll(collection);
		tabNum = collection.size() - 1;
		mainTab.getSelectionModel().select(curTab);
	}

	private void addOption() {
		((Option)node).addChild();
		buildOption();
		controller.updateSel();
	}
	
	public void shiftUp(int id) {
		shift(id, id - 1);
	}

	public void shiftDown(int id) {
		shift(id, id + 1);
	}
	
	public void shift(int i, int j){
		update = false;
		swap(i, j);
		curTab = j + 1;
		buildOption();
		controller.updateScene();
		update = true;
	}
	
	private void swap(int i, int j){
		((Option)node).swapChild(i, j);
	}

	public void delete(int id) {		
		((Option)node).removeChild(((Option)node).getChildren().get(id));
		curTab = 0;
		buildOption();
		controller.updateSel();
	}

	public void updateText(int id, String title, String text) {
		if(id == -1){
			node.setText(title, text);
		}else{
			OptionText optText = ((Option)node).getChildren().get(id);
			optText.setText(title, text);
		}
		controller.updateScene();
	}

	public void clear() {
		mainTab.getTabs().clear();
	}
}
