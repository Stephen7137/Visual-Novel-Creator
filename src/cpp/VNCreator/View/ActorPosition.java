package cpp.VNCreator.View;

import cpp.VNCreator.Node.Actor;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class ActorPosition {
	
	Actor actor;
	String redDouble = "[-]?[0-9]+[.]?[0-9]*";
	String redLong = "[0-9]+";
	
	@FXML
	private ImageView image;
	
	@FXML
	private TextField startX;
	
	@FXML
	private TextField startY;
	
	@FXML
	private TextField endX;
	
	@FXML
	private TextField endY;
	
	@FXML
	private TextField delay;
	
	@FXML
	private TextField duration;
	
	@FXML
	private CheckBox flip;
	
	public void setActor(ImageView imageView, Actor actor){
		if(imageView != null) 
			image.setImage(imageView.getImage());
		else
			image.setImage(null);
		this.actor = actor;
		startX.setText(String.valueOf(actor.getStartX()));
		startY.setText(String.valueOf(actor.getStartY()));
		endX.setText(String.valueOf(actor.getEndX()));
		endY.setText(String.valueOf(actor.getEndY()));
		delay.setText(String.valueOf(actor.getDelay()));
		duration.setText(String.valueOf(actor.getDuration()));
		flip.setSelected(actor.isFlipped());
	}
	//TODO clean up numListener(startX, actor.));
	public void setListeners(SceneEditor sEditor){
		startX.textProperty().addListener( new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0,
					String oldValue, String newValue) {
				Double value = validateDouble(newValue);
				if( value != null){
					actor.setStartX(value);
					sEditor.drawPreview();
				}else{
					if(newValue.length() == 0)
						startX.setText(newValue);
					else
						startX.setText(oldValue);
				}
			}
		});
		
		startY.textProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0,
					String oldValue, String newValue) {
				Double value = validateDouble(newValue);
				if( value != null){
					actor.setStartY(value);
					sEditor.drawPreview();
				}else{
					if(newValue.length() == 0)
						startY.setText(newValue);
					else
						startY.setText(oldValue);
				}
			}
		});
		
		endX.textProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0,
					String oldValue, String newValue) {
				Double value = validateDouble(newValue);
				if( value != null){
					actor.setEndX(value);
					sEditor.drawPreview();
				}else{
					if(newValue.length() == 0)
						endX.setText(newValue);
					else
						endX.setText(oldValue);
				}
			}
		});
		
		endY.textProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0,
					String oldValue, String newValue) {
				Double value = validateDouble(newValue);
				if( value != null){
					actor.setEndY(value);
					sEditor.drawPreview();
				}else{
					if(newValue.length() == 0)
						endY.setText(newValue);
					else
						endY.setText(oldValue);
				}
			}
		});
		
		delay.textProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0,
					String oldValue, String newValue) {
				Long value = validateLong(newValue);
				if( value != null){
					actor.setDelay(value);
					sEditor.drawPreview();
				}else{
					if(newValue.length() == 0)
						delay.setText(newValue);
					else
						delay.setText(oldValue);
				}
			}
		});
		
		duration.textProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0,
					String oldValue, String newValue) {
				Long value = validateLong(newValue);
				if( value != null){
					actor.setDuration(value);
					sEditor.drawPreview();
				}else{
					if(newValue.length() == 0)
						duration.setText(newValue);
					else
						duration.setText(oldValue);
				}
			}
		});
		
		flip.selectedProperty().addListener(new ChangeListener<Boolean>(){

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, 
					Boolean oldValue, Boolean newValue) {
				actor.setFlip(newValue);
				sEditor.drawPreview();
			}
		});
	}
	
	private Double validateDouble(String text){
		if(!text.matches("[-]?[0-9]+[.]?[0-9]*")) return null;
		try{
			return Double.valueOf(text);
		} catch(NumberFormatException nfe) {}
		return null;
	}
	
	private Long validateLong(String text){
		if(!text.matches("[0-9]+")) return null;
		try{
			return Long.valueOf(text);
		} catch(NumberFormatException nfe) {}
		return null;
	}
	
//	private ChangeListener<String> numListener(TextField field, DoubleProperty numProp, 
//			SceneEditor sEditor, String red){
//		
//		return new ChangeListener<String>(){
//
//			@Override
//			public void changed(ObservableValue<? extends String> arg0,
//					String oldValue, String newValue) {
//				Double value = null;
//				if(newValue.matches(red)){
//					try{
//					value = Double.valueOf(newValue);
//					} catch(NumberFormatException nfe) {}
//				}
//				
//				if( value != null){
//					numProp.set(value);
//					sEditor.drawPreview();
//				}else{
//					if(newValue.length() == 0)
//						field.setText(newValue);
//					else
//						field.setText(oldValue);
//				}
//			}
//		};
//	}

	public void setImage(String string, ImageView image) {
		this.image.setImage(image.getImage());
		actor.setName(string);
	}
}
