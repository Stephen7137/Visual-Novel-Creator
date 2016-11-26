package cpp.VNCreator.View;

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
	
	public void setController(TextSceneBack scene){
		DoubleProperty txtX = new SimpleDoubleProperty();
		txtX.addListener(observalble -> scene.setSceneX(txtX.get()));
		textX.textProperty().addListener(numListener(textX, txtX));
		
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
