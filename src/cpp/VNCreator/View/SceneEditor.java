package cpp.VNCreator.View;

import cpp.VNCreator.Controller.ImageLoader;
import cpp.VNCreator.Controller.ImageLoader.ImageStorage;
import cpp.VNCreator.Model.Sprite;
import cpp.VNCreator.Model.NodeType.nodeType;
import cpp.VNCreator.Node.Actor;
import cpp.VNCreator.Node.Node;
import cpp.VNCreator.Node.Option;
import cpp.VNCreator.Node.OptionText;
import cpp.VNCreator.Node.OptionText.OptionScene;
import cpp.VNCreator.Node.Scene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map.Entry;

import cpp.VNCreator.Controller.Controller;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Callback;
import javafx.util.Duration;

public class SceneEditor {

	Controller cotroller;
	ImageLoader loader;
	
	Image bckgrndImage;
	GraphicsContext gc;
	GraphicsContext bgc;
	ImageLoader imgLoader;
	ParallelTransition parTrans;
	ActorPosition actorPos;
	Node node;
	
	private boolean reset;
	private double ar = 1.77777777778;
	private double height = 0;
	private double width = 0;
	private ArrayList<Sprite> actorList;
	private ArrayList<Actor> layers;
	private HashMap<String, ImageView> icons;
	AnimationTimer animTimer;
	AnimationTimer previewTimer;
	int fps = 0;
	
	DoubleProperty textX;
	DoubleProperty textY;
	BooleanProperty mask;

	long sceneTimer = 0;
	
	@FXML
	private AnchorPane pane;
	
	@FXML
	private Canvas backdrop;
	
	@FXML
	private Canvas canvas;
	
	@FXML
	private VBox background;
	
	@FXML
	private VBox cast;
	
	@FXML
	private StackPane canvasPane;
	
	@FXML
	private TabPane textScene;
		
	@FXML
	private ComboBox<ComboObj> layerSel;
	
	@FXML
	private ComboBox<ComboImg> imageSel;
	
	@FXML
	private ComboBox<String> font;
	
	@FXML
	private ComboBox<Integer> size;
	
	@FXML
	private ColorPicker maskColor;
	
	@FXML
	private ColorPicker textColor;
	
	@FXML
	private void setMaskColor(){
		node.getScene().setMaskColor(maskColor.getValue());
	}
	
	@FXML
	private void setTextColor(){
		node.getScene().setTextColor(textColor.getValue());
	}
	
	@FXML
	private void play(){
		if(reset){
			buildScene();
			parTrans.playFromStart();
			reset = false;
		}else{
			parTrans.play();
		}	
		
		previewTimer.stop();
		animTimer.start();
	}
	
	@FXML
	private void pause(){
		animTimer.stop();
		parTrans.pause();
	}
	
	@FXML
	private void stop(){
		reset = true;
		pause();
		previewTimer.start();
	}
	
	@FXML
	private void addLayer(){
		Actor actor = new Actor();
		layers.add(actor);
		layerSel.getItems().add(new ComboObj("Layer " + layers.size(), actor));
	}
	
	@FXML
	private void setLayer(){
		ComboObj comboObj = layerSel.getValue();
		if(pane.isDisabled()) pane.setDisable(false);
		actorPos.setActor(icons.get(comboObj.getActor().getName()) ,comboObj.getActor());
		imageSel.setValue(null);
	}
	
	@FXML
	private void setActor(){
		actorPos.setImage(imageSel.getValue().toString(), 
				imageSel.getValue().getActor());
	}
	
	@FXML
	private void setFont(){
		node.getScene().setFont(font.getValue());
		node.getScene().setFontSize(size.getValue());
	}
	
	private class ComboObj{
		
		private String name;
		private Actor actor;
		
		public ComboObj( String name, Actor actor){
			this.name = name;
			this.actor = actor;
		}
		
		public String toString(){
			return name;
		}
		
		public Actor getActor(){
			return actor;
		}
	}
	
	public class ComboImg{
		
		private String name;
		private ImageView image;
		
		public ComboImg( String name, ImageView image){
			this.name = name;
			this.image = image;
		}
		
		public String toString(){
			return name;
		}
		
		public ImageView getActor(){
			return image;
		}
	}
	
	public void drawPreview(){
		stop();
		bgc.setFill(Color.BLACK);
		bgc.fillRect(0, 0, backdrop.getWidth(), backdrop.getHeight());
		if(bckgrndImage != null){
			gc.drawImage(bckgrndImage, 0, 0);
			for(Actor actor : layers){
				Image image = imgLoader.getSprite(actor.getName());
				if(image != null){
					if(actor.isFlipped()){
						gc.drawImage(image, actor.getEndX() + image.getWidth(), actor.getEndY(), -image.getWidth(), image.getHeight());
					}else{
						gc.drawImage(image, actor.getEndX(), actor.getEndY());
					}
				}							
			}
			gc.fillText(node.getText(), textX.get(), textY.get());
		}
	}
	
	private void buildScene(){
		actorList = new ArrayList<Sprite>();
		parTrans.getChildren().clear();
		long timerDuration = 0;
		for(Actor actor: layers){
			if( timerDuration < actor.getDuration() + actor.getDelay())
				timerDuration = actor.getDuration() + actor.getDelay();
			Timeline timeline = new Timeline();
			Sprite sprite = new Sprite(imgLoader.getSprite(actor.getName()) , actor.getStartX(), 
					actor.getStartY(), actor.isFlipped());
			actorList.add(sprite);
			KeyValue keyX = new KeyValue(sprite.curXProperty(), actor.getEndX());
			KeyValue keyY = new KeyValue(sprite.curYProperty(), actor.getEndY());
			KeyFrame frameX = new KeyFrame(Duration.millis(actor.getDuration()), keyX);
			KeyFrame frameY = new KeyFrame(Duration.millis(actor.getDuration()), keyY);
			timeline.getKeyFrames().addAll(frameX, frameY);
			timeline.setDelay(Duration.millis(actor.getDelay()));
			parTrans.getChildren().add(timeline);
		}
		mask.set(false);
		Timeline timeline = new Timeline();
		KeyValue keyb = new KeyValue(mask, true);
		KeyFrame frameb = new KeyFrame(Duration.millis(1), keyb);
		timeline.getKeyFrames().add(frameb);
		timeline.setDelay(Duration.millis(timerDuration));
		parTrans.getChildren().add(timeline);
	}
	
	private void update(){
		Scene scene = node != null ? node.getScene() : null;
		bgc.setFill(Color.BLACK);
		bgc.fillRect(0, 0, backdrop.getWidth(), backdrop.getHeight());
		
		if(bckgrndImage != null){
			gc.drawImage(bckgrndImage, 0, 0);
		}	

		for(Sprite sprite : actorList){
			if(sprite.isFlipped()){
				gc.drawImage(sprite.getImage(), sprite.getCurX() + sprite.getImage().getWidth(),  sprite.getCurY(),
						- sprite.getImage().getWidth(), sprite.getImage().getHeight());
			}else{
				gc.drawImage(sprite.getImage(), sprite.getCurX(), sprite.getCurY());
			}
		}
		
		if(scene != null){
			
			if(mask.getValue()){
				gc.setFill(scene.getMaskColor());
				gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
			}
			
			gc.setFont(new Font(scene.getFont(), scene.getFontSize()));
			gc.setFill(scene.getTextColor());
			
			if(scene.getBackground().length() > 0)gc.drawImage(imgLoader.getTextBack(scene.getTextBackground()),
					scene.getSceneX(), scene.getSceneY());
			gc.fillText(node.getText(), scene.getTextX(),scene.getTextY());
			
			if(node.getType() == nodeType.Option){
				for(OptionText oText : ((Option)node).getChildren()){
					OptionScene oScene = oText.getOptionScene();
					if(oScene.getTextBackground().length() > 0)gc.drawImage(imgLoader.getTextBack(oScene.getTextBackground()),
							oScene.getSceneX(), oScene.getSceneY());
					gc.fillText(oText.getText(), oScene.getTextX(), oScene.getSceneY());
				}
			}
			
		}		
	}
	
	private void drawBacground(Color color){
		gc.setFill(color);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}

	public void controller(Controller cotroller) {
		
		gc = canvas.getGraphicsContext2D();
		bgc = backdrop.getGraphicsContext2D();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("ActorPosition.fxml"));
		try {
			pane.getChildren().add(loader.load());
			actorPos = loader.getController();
			actorPos.setListeners(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		pane.setDisable(true);
		imageSel.setCellFactory(new Callback<ListView<ComboImg>, ListCell<ComboImg>>() {
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
				
		for(int i = 1; i < 101; i++){
			size.getItems().add(i);
		}
		
		textX = new SimpleDoubleProperty();
		textY = new SimpleDoubleProperty();
		mask = new SimpleBooleanProperty();
		
		
		textX.addListener(observalble -> node.getScene().setTextX(textX.get()));
		textY.addListener(observalble -> node.getScene().setTextY(textY.get()));
		
		size.getSelectionModel().select((int) gc.getFont().getSize());
		font.getItems().addAll(Font.getFamilies());
		font.getSelectionModel().select(gc.getFont().getFamily());
		
		icons = new HashMap<String, ImageView>();
		
		this.cotroller = cotroller;
		canvasPane.heightProperty().addListener( observable -> updateCanvas());
		canvasPane.widthProperty().addListener( observable -> updateCanvas());
		
		parTrans = new ParallelTransition();
		actorList = new ArrayList<Sprite>();
		layers = new ArrayList<Actor>();
		
		animTimer = new AnimationTimer(){
			
			@Override
			public void handle(long now) {
				update();
			}
		};
		animTimer.stop();
		previewTimer = new AnimationTimer(){
			
			@Override
			public void handle(long now) {
				drawPreview();
			}
		};
		previewTimer.start();
		reset = true;
	}

	private void updateCanvas(){
		
		backdrop.heightProperty().set(canvasPane.getHeight());
		backdrop.widthProperty().set(canvasPane.getWidth());
		
		if(height != 0 && width != 0){
			if( backdrop.getWidth() > backdrop.getHeight() * ar){
				canvas.setScaleX(backdrop.getHeight() * ar / width);
				canvas.setScaleY(backdrop.getHeight() / height);
			}else{
				canvas.setScaleX(backdrop.getWidth()/  width);
				canvas.setScaleY(backdrop.getWidth() * (1/ar) / height);
			}
		}
		update();
	}

	public void loadBackIcon(Hashtable<String, ImageStorage> bckgrndTable) {
		background.getChildren().clear();
		for(Entry<String, ImageStorage> entry : bckgrndTable.entrySet() ){
			ImageView view = new ImageView(entry.getValue().getImage());
			view.setFitHeight(100);
			view.setPreserveRatio(true);
			view.setOnMousePressed(event -> {
				setBackground(entry.getValue().getName());
			});
			background.getChildren().add(view);
		}
	}
	
	public void loadActorIcon(Hashtable<String, ImageStorage> spriteTable) {
		for(Entry<String, ImageStorage> entry : spriteTable.entrySet() ){
			ImageView view = new ImageView(entry.getValue().getImage());
			view.setFitHeight(100);
			view.setPreserveRatio(true);
			cast.getChildren().add(view);
			imageSel.getItems().add(new ComboImg( entry.getValue().getName(), view));
			icons.put(entry.getValue().getName(), view);
		}
	}
	

	public void loadTextBackIcon(Hashtable<String, ImageStorage> text) {
		for(Entry<String, ImageStorage> entry : text.entrySet() ){
			ImageView view = new ImageView(entry.getValue().getImage());
			view.setFitHeight(100);
			view.setPreserveRatio(true);
//			cast.getChildren().add(view);
//			imageSel.getItems().add(new ComboImg( entry.getValue().getName(), view));
//			icons.put(entry.getValue().getName(), view);
		}
	}

	public void setBackground(String name){
		bckgrndImage = imgLoader.getBackground(name);
		node.getScene().setBackground(name);
		if(bckgrndImage != null){
			height = bckgrndImage.getHeight();
			width = bckgrndImage.getWidth();
			ar = width / height;
			canvas.setHeight(height);
			canvas.setWidth(width);
		}else{
			drawBacground(Color.WHITE);
		}
		updateCanvas();
	}
	
	public void setActors(ArrayList<Actor> actors){
		reset = true;
		layers = actors;
		layerSel.getItems().clear();
		for(int i = 0; i < layers.size(); i++){
			layerSel.getItems().add(new ComboObj("Layer " + (i + 1), layers.get(i)));
		}	
	}

	public void setLoader(ImageLoader imgLoader) {
		this.imgLoader = imgLoader;
	}

	public void setNode(Node selected) {
		node = selected;
		Scene scene = node.getScene();
//		if(scene.getTextX() != 0 && scene.getTextY() != 0){TODO
//			textFieldX.setText(String.valueOf(scene.getTextX()));
//			textFieldX.setText(String.valueOf(scene.getTextX()));
//		}		
		setBackground(scene.getBackground());
		setActors(scene.getLayers());
		stop();
	}
		
	
}
