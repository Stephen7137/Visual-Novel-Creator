package cpp.VNCreator.View;

import cpp.VNCreator.Controller.ImageLoader;
import cpp.VNCreator.Controller.ImageLoader.ImageStorage;
import cpp.VNCreator.Model.Sprite;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map.Entry;
import cpp.VNCreator.Controller.Controller;
import cpp.VNCreator.Node.Node;
import cpp.VNCreator.Node.Scene;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class SceneEditor {

	Controller cotroller;
	ImageLoader loader;
	Image bckgrndImage;
	GraphicsContext gc;
	GraphicsContext bgc;
	ImageLoader imgLoader;
	Timeline timeline;
	ImageView actor;
	boolean reset;
	
	private final float fixedIterval = 0.02f;
	private ArrayList<Sprite> actorList;
	private ArrayList<Sprite> activeActor;
	AnimationTimer animTimer;
	int fps = 0;

	long sceneTimer = 0;
	
	@FXML
	private ImageView imagebck;
	
	@FXML
	private Pane stageSet;
	
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
	private void play(){
		animTimer.start();
		if(reset){
			timeline.playFromStart();
			reset = false;
		}else{
			timeline.play();
		}		
	}
	
	@FXML
	private void pause(){
		animTimer.stop();
		timeline.pause();
	}
	
	@FXML
	private void stop(){
		timeline.pause();
		reset = true;
		pause();
		resetSprite();
		update();
	}
	
	public void update(Node node) {
		loadScene(node.getScene());
	}
	
	public void loadScene(Scene scene){
		for(Sprite sprite : scene.getSprites() ){
			activeActor.add(sprite);
			sprite.setImage(loader.getSprite(sprite.getFileName()));
		}
	}
	
	public void update(){
		bgc.setFill(Color.BLACK);
		bgc.fillRect(0, 0, backdrop.getWidth(), backdrop.getHeight());
		
//		gc.setFill(Color.WHITE);
//		gc.fillRect(0, 0, 200, 200);
//		if(bckgrndImage != null){
//			gc.drawImage(bckgrndImage, 0, 0);
//		}		
//		for(Sprite sprite : activeActor){
//			gc.drawImage(sprite.getImage(), sprite.getCurX(), sprite.getCurY());
//		}
//		gc.setFill(Color.AQUA);
//		gc.fillOval( 0, 0, 10, 10);
//		gc.fillOval(300, 300, 10, 10);
	}
	
	private void fixedUpdate(){
		fps++;
		for(Sprite sprite : actorList ){
			if( sceneTimer > sprite.getStart()){
				activeActor.add(sprite);
				sprite.setStartPos();
			}
		}
		for(Sprite sprite : activeActor){
			sprite.move();
		}
	}

	public void clear() {
		//TODO
	}

	
	//int x = 0;
	//int y = 0;
	public void controller(Controller cotroller) {
		//gc = canvas.getGraphicsContext2D();
		bgc = backdrop.getGraphicsContext2D();
		this.cotroller = cotroller;
		canvasPane.heightProperty().addListener( observable -> updateCanvas());
		canvasPane.widthProperty().addListener( observable -> updateCanvas());
		
//		imagebck.scaleXProperty().bind(canvasPane.heightProperty());
		
		imagebck.fitWidthProperty().bind(canvasPane.widthProperty());
		imagebck.fitHeightProperty().bind(canvasPane.heightProperty());
		
		timeline = new Timeline();
		actorList = new ArrayList<Sprite>();
		activeActor = new ArrayList<Sprite>();
		
		animTimer = new AnimationTimer(){
			//TODO
			//int fps = 0;
			long lastTime = 0;
			long interval = 0;
			
			@Override
			public void handle(long now) {
				
				if(lastTime > 0){
					interval += (now - lastTime);
					sceneTimer += (now - lastTime);
					if( 1000000000 * fixedIterval <= interval){
						fixedUpdate();
						interval = 0;
					}
					update();
				}
				if(sceneTimer >= 1000000000){
					fps = 0;
					sceneTimer = 0;
				}
				lastTime = now;
			}
		};
		pause();
		updateCanvas();
	}

	double ar = 1.77777777778;
	private void updateCanvas(){
		
		backdrop.heightProperty().set(canvasPane.getHeight());
		backdrop.widthProperty().set(canvasPane.getWidth());
		System.out.println(imagebck.getScaleX());
		//imagebck.setFitWidth(backdrop.getWidth());
//		if(imagebck.getFitHeight() > backdrop.getHeight())
//			imagebck.set
//		if( backdrop.getWidth() > backdrop.getHeight() * ar){
//			canvas.widthProperty().set(backdrop.getHeight() * ar);
//			canvas.heightProperty().set(backdrop.getHeight());
//		}else{
//			canvas.widthProperty().set(backdrop.getWidth());
//			canvas.heightProperty().set(backdrop.getWidth() * (1/ar));
//		}
		
		
//		if(imgLoader.getRatio() != 0){
//			backdrop.getWidth() * 
//				imgLoader.getRatio());
//		}
		
		
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
			//TODO
			Sprite sprite= new Sprite("", new Point2D(0, 0), new Point2D(300, 300), 0, 20);
			sprite.setImage(entry.getValue().getImage());
			sprite.setStartPos();
			activeActor.add(sprite);			
		}
	}
	
	private void setActor(String name) {
		
		timeline = new Timeline();
		actor = new ImageView(imgLoader.getSprite(name));
		actor.setLayoutX(700);
		actor.setLayoutY(0);
		actor.scaleXProperty().bind(imagebck.scaleXProperty());
		KeyValue key = new KeyValue(actor.layoutXProperty(),300);
		KeyFrame frame = new KeyFrame(Duration.millis(5000), key);
		ImageView actor1 = new ImageView(imgLoader.getSprite(name));
		System.out.println(actor.getScaleX());
		System.out.println(imagebck.getScaleX());
		actor1.setLayoutX(0);
		actor1.setLayoutY(0);
		actor1.scaleXProperty().bind(imagebck.scaleXProperty());
		stageSet.getChildren().addAll(actor1, actor);
		KeyValue key1 = new KeyValue(actor1.layoutXProperty(),400);
		KeyFrame frame1 = new KeyFrame(Duration.millis(5000), key1);
		timeline.getKeyFrames().addAll(frame1, frame );
		timeline.setDelay(new Duration(500));
	}

	public void setBackground(String name){
		imagebck.setImage(imgLoader.getBackground(name));
		imagebck.setSmooth(true);
		imagebck.setPreserveRatio(true);
		//imagebck.setFitHeight(300);
		System.out.println("resize: " + imagebck.isResizable());
		System.out.println("resize: " + imagebck.preserveRatioProperty().getValue());
		updateCanvas();
		
//		if(bckgrndImage == null) updateCanvas();
//		bckgrndImage = imgLoader.getBackground(name);
	}
	
	private void resetSprite(){
		//TODO
		for(Sprite sprite : activeActor){
			sprite.setStartPos();
		}
	}

	public void setLoader(ImageLoader imgLoader) {
		this.imgLoader = imgLoader;
	}
}
