package cpp.VNCreator.View;

import java.util.ArrayList;

import cpp.VNCreator.Node.OptionText.OptionScene;
import cpp.VNCreator.Node.TextSceneBack;
import cpp.VNCreator.View.SceneEditor.ComboImg;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

public class TextScene {
	@FXML
	private ComboBox<ComboImg> text;
	
	@FXML
	private ComboBox<ComboImg> active;
	
	@FXML
	private TextField textX;
	
	@FXML
	private TextField textY;
	
	@FXML
	private TextField offsetX;
	
	@FXML
	private TextField offsetY;
	
	public void setTextController(TextSceneBack scene, ArrayList<ComboImg> textBack){
		active.setVisible(false);
		setController(scene, textBack);
	}
	
	public void setOptionController(OptionScene scene, ArrayList<ComboImg> textBack){
		setController(scene, textBack);
		
		active.getItems().addAll(textBack);
		active.onActionProperty().addListener(observalble -> 
			scene.setActiveTextBack(active.getValue().toString()));
		active.setCellFactory(new Callback<ListView<ComboImg>, ListCell<ComboImg>>() {
            @Override public ListCell<ComboImg> call(ListView<ComboImg> p) {
                return new ListCell<ComboImg>() {
                    
                    @Override protected void updateItem(ComboImg image, boolean empty) {
                        super.updateItem(image, empty);
                        
                        if (image == null || empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(image.getActor());
                        }
                   }
              };
          }
       });
	}
	
	private void setController(TextSceneBack scene, ArrayList<ComboImg> textBack){
		DoubleProperty txX = new SimpleDoubleProperty();
		txX.addListener(observalble -> scene.setSceneX(txX.get()));
		textX.textProperty().set(String.valueOf(scene.getSceneX()));
		textX.textProperty().addListener(numListener(textX, txX));
				
		DoubleProperty txY = new SimpleDoubleProperty();
		txY.addListener(observalble -> scene.setSceneY(txY.get()));
		textY.textProperty().set(String.valueOf(scene.getSceneY()));
		textY.textProperty().addListener(numListener(textY, txY));		
		
		DoubleProperty offSetX = new SimpleDoubleProperty();
		offSetX.addListener(observalble -> scene.setTextX(offSetX.get()));
		offsetX.textProperty().set(String.valueOf(scene.getSceneX()));
		offsetX.textProperty().addListener(numListener(offsetX, txY));		
		
		DoubleProperty offSetY = new SimpleDoubleProperty();
		offSetY.addListener(observalble -> scene.setTextY(offSetY.get()));
		offsetY.textProperty().set(String.valueOf(scene.getTextY()));
		offsetY.textProperty().addListener(numListener(offsetY, txY));
		
		text.getItems().addAll(textBack);
		text.onActionProperty().addListener(observalble -> 
			scene.setTextBackground(text.getValue().toString()));
		text.setCellFactory(new Callback<ListView<ComboImg>, ListCell<ComboImg>>() {
            @Override public ListCell<ComboImg> call(ListView<ComboImg> p) {
                return new ListCell<ComboImg>() {
                    
                    @Override protected void updateItem(ComboImg image, boolean empty) {
                        super.updateItem(image, empty);
                        
                        if (image == null || empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(image.getActor());
                        }
                   }
              };
          }
       });
	}
	
	private ChangeListener<String> numListener(TextField field, DoubleProperty numProp){
		
		return new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0,
					String oldValue, String newValue) {
				Double value = null;
				if(newValue.matches("[-]?[0-9]+[.]?[0-9]*")){
					try{
					value = Double.valueOf(newValue);
					} catch(NumberFormatException nfe) {}
				}
				
				if( value != null){
					numProp.set(value);
				}else{
					if(newValue.length() == 0)
						field.setText(newValue);
					else
						field.setText(oldValue);
				}
			}
		};
	}
}