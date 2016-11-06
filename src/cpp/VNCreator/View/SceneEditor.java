package cpp.VNCreator.View;

import cpp.VNCreator.Controller.ImageLoader;
import cpp.VNCreator.Controller.ImageLoader.ImageStorage;
import cpp.VNCreator.Model.Sprite;
import cpp.VNCreator.Node.Actor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Random;

import cpp.VNCreator.Controller.Controller;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
	
	private boolean reset;
	private double ar = 1.77777777778;
	private double height = 0;
	private double width = 0;
	private ArrayList<Sprite> actorList;
	private ArrayList<Actor> layers;
	AnimationTimer animTimer;
	int fps = 0;

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
	private ComboBox<ComboObj> layerSel;
	
	@FXML
	private ComboBox<ComboImg> imageSel;
	
	@FXML
	private void play(){
		animTimer.start();
		if(reset){
			parTrans.playFromStart();
			reset = false;
		}else{
			parTrans.play();
		}		
	}
	
	@FXML
	private void pause(){
		animTimer.stop();
		parTrans.pause();
	}
	
	@FXML
	private void stop(){
		parTrans.pause();
		reset = true;
		pause();
		resetSprite();
		update();
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
		actorPos.setActor(comboObj.getActor());
	}
	
	@FXML
	private void setActor(){
		actorPos.setImage(imageSel.getValue().toString(), 
				imageSel.getValue().getActor());
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
	
	private class ComboImg{
		
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
				if(actor.isFlipped()){
					gc.drawImage(image, actor.getEndX() + image.getWidth(), actor.getEndY(), -image.getWidth(), image.getHeight());
				}else{
					gc.drawImage(image, actor.getEndX(), actor.getEndY());
				}			
			}
		}		
	}
	
	private void update(){
		bgc.setFill(Color.BLACK);
		bgc.fillRect(0, 0, backdrop.getWidth(), backdrop.getHeight());
		
		if(bckgrndImage != null){
			gc.drawImage(bckgrndImage, 0, 0);
			
		}		
		for(Sprite sprite : actorList){
			if(sprite.isFlipped()){
				gc.drawImage(sprite.getImage(), sprite.getCurX() + sprite.getImage().getWidth(),  sprite.getCurY(), - sprite.getImage().getWidth(), sprite.getImage().getHeight());
			}else{
				gc.drawImage(sprite.getImage(), sprite.getCurX(), sprite.getCurY());
			}
		}
	}
	
	public void clear() {
		//TODO
	}

	public void controller(Controller cotroller) {
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
                            setGraphic(image.getActor());//image.getActor());
                        }
                   }
              };
          }
       });
		
		gc = canvas.getGraphicsContext2D();
		bgc = backdrop.getGraphicsContext2D();
		this.cotroller = cotroller;
		canvasPane.heightProperty().addListener( observable -> updateCanvas());
		canvasPane.widthProperty().addListener( observable -> updateCanvas());
		
		parTrans = new ParallelTransition();
		actorList = new ArrayList<Sprite>();
		layers = new ArrayList<Actor>();
		
		animTimer = new AnimationTimer(){
			
			@Override
			public void handle(long now) {
			}
		};
		pause();
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
			view.setOnMousePressed(event -> {
				setActor(entry.getValue().getName());
			});
			cast.getChildren().add(view);
			imageSel.getItems().add(new ComboImg( entry.getValue().getName(), view));
		}
	}
	
	private void setActor(String name) {
		Random ran = new Random();
		Timeline timeline = new Timeline();
		Sprite sprite = new Sprite(imgLoader.getSprite(name) , new Point2D(0,0), new Point2D(300,300), ran.nextInt(2) == 0);
		actorList.add(sprite);
		KeyValue key = new KeyValue(sprite.curXProperty(), ran.nextDouble()*1000);
		KeyFrame frame = new KeyFrame(Duration.millis(ran.nextInt(10000)), key);
		timeline.getKeyFrames().add(frame);
		timeline.setDelay(new Duration(500));
		parTrans.getChildren().add(timeline);
	}

	public void setBackground(String name){
		bckgrndImage = imgLoader.getBackground(name);
		height = bckgrndImage.getHeight();
		width = bckgrndImage.getWidth();
		ar = width / height;
		canvas.setHeight(height);
		canvas.setWidth(width);
		updateCanvas();
	}
	
	private void resetSprite(){
		//TODO
		for(Sprite sprite : actorList){
			sprite.setStartPos();
		}
	}

	public void setLoader(ImageLoader imgLoader) {
		this.imgLoader = imgLoader;
	}
}
