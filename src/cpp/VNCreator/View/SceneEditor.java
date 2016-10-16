package cpp.VNCreator.View;

import cpp.VNCreator.Controller.ImageLoader;
import cpp.VNCreator.Controller.ImageLoader.ImageStorage;
import cpp.VNCreator.Model.Sprite;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;

import cpp.VNCreator.Controller.Controller;
import cpp.VNCreator.Node.Node;
import cpp.VNCreator.Node.Scene;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class SceneEditor {

	Controller cotroller;
	ImageLoader loader;
	Image bckgrndImage;
	GraphicsContext gc;
	Hashtable<String, ImageStorage> bckgrndTable;
	Hashtable<String, ImageStorage> spriteTable;
	private final float fixedIterval = 0.02f;
	private ArrayList<Sprite> actorList;
	private ArrayList<Sprite> activeActor;
	AnimationTimer animTimer;
	int fps = 0;

	long sceneTimer = 0;
	
	@FXML
	private Canvas canvas;
	
	@FXML
	private VBox background;
	
	@FXML
	private VBox cast;
	
	@FXML
	private AnchorPane canvasPane;
	
	@FXML
	private void play(){
		animTimer.start();
	}
	
	@FXML
	private void pause(){
		animTimer.stop();
	}
	
	@FXML
	private void stop(){
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
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		if(bckgrndImage != null){
			gc.drawImage(bckgrndImage, 0, 0);
		}		
		for(Sprite sprite : activeActor){
			gc.drawImage(sprite.getImage(), sprite.getCurX(), sprite.getCurY());
		}
		gc.setFill(Color.AQUA);
		gc.fillOval( 0, 0, 10, 10);
		gc.fillOval(300, 300, 10, 10);
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

	
	int x = 0;
	int y = 0;
	public void controller(Controller cotroller) {
		gc = canvas.getGraphicsContext2D();
		this.cotroller = cotroller;
		canvasPane.heightProperty().addListener( observable -> updateCanvas());
		canvasPane.widthProperty().addListener( observable -> updateCanvas());
		
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
					System.out.println(fps);
					fps = 0;
					sceneTimer = 0;
				}
				lastTime = now;
			}
		};
		
		pause();
	}

	private void updateCanvas(){
		canvas.heightProperty().set(canvasPane.getHeight());
		canvas.widthProperty().set(canvasPane.getWidth());
		update();
	}

	public void loadBackIcon(Hashtable<String, ImageStorage> bckgrndTable) {
		this.bckgrndTable = bckgrndTable;
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
	
	public void loadSpriteIcon(Hashtable<String, ImageStorage> spriteTable) {
		this.spriteTable = spriteTable;
		for(Entry<String, ImageStorage> entry : spriteTable.entrySet() ){
			ImageView view = new ImageView(entry.getValue().getImage());
			view.setFitHeight(100);
			view.setPreserveRatio(true);
			view.setOnMousePressed(event -> {
				setBackground(entry.getValue().getName());
			});
			cast.getChildren().add(view);
			//TODO
			Sprite sprite= new Sprite("", new Point2D(0, 0), new Point2D(300, 300), 0, 20);
			sprite.setImage(entry.getValue().getImage());
			sprite.setStartPos();
			activeActor.add(sprite);			
		}
	}
	
	public void setBackground(String name){
		bckgrndImage = bckgrndTable.get(name).getImage();
	}
	
	private void resetSprite(){
		//TODO
		for(Sprite sprite : activeActor){
			sprite.setStartPos();
		}
	}
}
