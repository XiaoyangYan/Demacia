package com.unsw.tilegame.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.unsw.tilegame.Game;
import com.unsw.tilegame.entities.EntityManager;
import com.unsw.tilegame.entities.creatures.Player;
import com.unsw.tilegame.entities.enemy.Coward;
import com.unsw.tilegame.entities.enemy.HoundDog;
import com.unsw.tilegame.entities.enemy.Hunter;
import com.unsw.tilegame.entities.enemy.Strategist;
import com.unsw.tilegame.entities.equipment.Arrow;
import com.unsw.tilegame.entities.equipment.Bomb;
import com.unsw.tilegame.entities.equipment.EquipmentManager;
import com.unsw.tilegame.entities.equipment.Gold;
import com.unsw.tilegame.entities.equipment.Key;
import com.unsw.tilegame.entities.equipment.Sword;
import com.unsw.tilegame.entities.potion.Hover;
import com.unsw.tilegame.entities.potion.Invincible;
import com.unsw.tilegame.entities.statics.Door;
import com.unsw.tilegame.entities.statics.Exit;
import com.unsw.tilegame.entities.statics.Rock;
import com.unsw.tilegame.scene_controller.DesignSceneController;
import com.unsw.tilegame.tool.Assets;
import com.unsw.tilegame.worlds.World;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
/**
 * the class aims to return a screen for design mode
 * @author YAN XIAOYANG
 *
 */
public class DesignModeScreen implements SceneMaker {
	private Game game;
	private float selectedItemX = 0f, selectedItemY = 0f;
	private Canvas canvas;
	private Stage stage;
	private World world;
	private Pane pane;
	private Map<Point, ImageView> drawList;
	private EquipmentManager equipmentManager;
	private EntityManager entityManager;
	/**
	 * design mode for the game
	 * @param game
	 * @param stage
	 */
	public DesignModeScreen(Game game, Stage stage) {
		this.pane = new Pane();
		pane.setPrefSize(1280, 800);
		this.game = game;
		this.stage = stage;
		this.drawList = new HashMap<Point, ImageView>();
		canvas = new Canvas();
		world = new World(game.getHandler(),"res/worlds/world6.txt",7);
		Player player = new Player(world.getHandler(),100,100);
		entityManager = new EntityManager(world.getHandler(),player);
		equipmentManager = new EquipmentManager(world.getHandler());
		entityManager.addEntity(player);
		world.buildWorld(entityManager);
		world.getEntityManager().addEntity(player);
	}
	/**
	 * create a loop for designing game
	 * @return loop
	 */
	private Timeline createLoop() {
	    final Duration d = Duration.millis(1000/60);
	    final KeyFrame oneFrame = new KeyFrame(d, this::run);
	    Timeline t = new Timeline(60, oneFrame);
	    t.setCycleCount(Timeline.INDEFINITE);
	    return t;
	}
	/**
	 * run a canvas
	 * @param e
	 */
	public void run(Event e) {
		GraphicsContext g= canvas.getGraphicsContext2D();
		g.clearRect(0, 0, 800,800);
		g.setStroke(Color.BLUE);
		ImageView dirt = new ImageView(Assets.dirt);
		dirt.setFitHeight(40);
		dirt.setFitWidth(40);
		for(int y =0;y < 20;y++){
			for(int x = 0;x < 20;x++){
				g.drawImage(dirt.getImage(), x*40, y*40);
			}
		}
		ImageView imageChoose = new ImageView(this.getClass().getResource("/texture/choose.png").toExternalForm());
		imageChoose.setFitHeight(40);
		imageChoose.setFitWidth(40);
		canvas.requestFocus();
		pane.setOnKeyPressed(event->{
			if (event.getCode().equals(KeyCode.W)) {
				g.drawImage(imageChoose.getImage(), selectedItemX, selectedItemY-40);
				selectedItemY-=40;
			} else if (event.getCode().equals(KeyCode.S)) {
				g.drawImage(imageChoose.getImage(), selectedItemX, selectedItemY+40);
				selectedItemY+=40;
			} else if (event.getCode().equals(KeyCode.A)) {
				g.drawImage(imageChoose.getImage(), selectedItemX-40, selectedItemY);
				selectedItemX-=40;
			} else if (event.getCode().equals(KeyCode.D)) {
				g.drawImage(imageChoose.getImage(), selectedItemX+40, selectedItemY);
				selectedItemX+=40;
			}
		});
		for(Map.Entry<Point, ImageView> entry: drawList.entrySet()) {
			g.drawImage(entry.getValue().getImage(), entry.getKey().getX(), entry.getKey().getY());
		}
		g.drawImage(imageChoose.getImage(), selectedItemX, selectedItemY);
	}
	/**
	 * return a scene
	 */
	@Override
	public Scene getScene() {
		canvas.setWidth(800);
		canvas.setHeight(800);
		canvas.setLayoutX(0);
		canvas.setLayoutY(0);
		canvas.setFocusTraversable(true);
		pane.getChildren().add(canvas);
		pane.setFocusTraversable(true);
		ArrayList<ImageView> assetList = new ArrayList<ImageView>();
		final ImageView arrow =  new ImageView(Assets.arrow);
		final ImageView bomb = new ImageView(Assets.bomb);
		final ImageView exit = new ImageView(Assets.exit);
		final ImageView hover = new ImageView(Assets.hover);
		final ImageView invincible = new ImageView(Assets.invincible);
		assetList.add(arrow);
		assetList.add(exit);
		assetList.add(bomb);
		assetList.add(hover);
		assetList.add(invincible);
		assetList.add(new ImageView(Assets.key));
		assetList.add(new ImageView(Assets.gold));
		assetList.add(new ImageView(Assets.stone));
		assetList.add(new ImageView(Assets.sword));
		assetList.add(new ImageView(Assets.coward_down[0]));
		assetList.add(new ImageView(Assets.door[0]));
		assetList.add(new ImageView(Assets.hunter_down[0]));
		assetList.add(new ImageView(Assets.stratigist_down[0]));
		assetList.add(new ImageView(Assets.hound_left[0]));
		assetList.add(new ImageView(Assets.rock));
		assetList.add(new ImageView(Assets.switchPlace));
		assetList.add(new ImageView(Assets.pit));
		for (int i = 0; i < 17; i++) {
			Button itemB = new Button();
			itemB.setPrefSize(70, 70);
			itemB.setLayoutX(900+70*(i%4));
			itemB.setLayoutY(200+70*(i/4));
			assetList.get(i).setFitHeight(50);
			assetList.get(i).setFitWidth(50);
			itemB.setGraphic(assetList.get(i));
			int remember = i;
			itemB.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					createItem(remember);
					Point p = new Point((int)selectedItemX, (int)selectedItemY);
					ImageView iv = assetList.get(remember);
					iv.setFitHeight(40);
					iv.setFitWidth(40);
					drawList.put(p,iv);
				}
			});
			pane.getChildren().add(itemB);
		}
		Timeline time = createLoop();
		time.play();
		Button buttonGame = new Button("GAME");
		buttonGame.setPrefSize(80, 80);
		buttonGame.setLayoutX(920);
		buttonGame.setLayoutY(550);
		
		buttonGame.setOnAction(event->{
			Game.worldId = 7;
			canvas.getGraphicsContext2D().clearRect(0, 0, 800, 800);
			drawList.clear();
			ImageView dirt = new ImageView(Assets.dirt);
			dirt.setFitHeight(40);
			dirt.setFitWidth(40);
			for(int y =0;y < 20;y++){
				for(int x = 0;x < 20;x++){
					canvas.getGraphicsContext2D().drawImage(dirt.getImage(), x*40, y*40);
				}
			}
			time.pause();
			World inputWorld = world;
			inputWorld.setEntityManager(entityManager);
			inputWorld.setEquipmentManager(equipmentManager);
			game.getHandler().setWorld(inputWorld);
			game.getEngine().gameLoop.play();
			new DesignSceneController(stage).handleMousePress();
		});
		pane.getChildren().addAll(buttonGame);
		Scene scene = new Scene(pane,1280, 800);
		
		return scene;
	}
	/**
	 * create an equipment or entity
	 * @param i
	 */
	public void createItem(int i) {
		switch(i) {
		case 0:
			Arrow arrow = new Arrow(game.getHandler(),selectedItemX*1.5f,selectedItemY*1.5f);
			equipmentManager.addEquipment(arrow);
			break;
		case 1:
			Exit exit = new Exit(game.getHandler(),selectedItemX*1.5f,selectedItemY*1.5f);
			entityManager.getEntities().add(exit);
			break;
		case 2:
			Bomb bomb = new Bomb(game.getHandler(),selectedItemX*1.5f, selectedItemY*1.5f);
			equipmentManager.addEquipment(bomb);
			break;
		case 3:
			Hover hover = new Hover(game.getHandler(),selectedItemX*1.5f,selectedItemY*1.5f);
			equipmentManager.addEquipment(hover);
			break;
		case 4 :
			Invincible invincible = new Invincible(game.getHandler(),selectedItemX*1.5f, selectedItemY*1.5f);
			equipmentManager.addEquipment(invincible);
			break;
		case 5:
			Key key = new Key(game.getHandler(),selectedItemX*1.5f, selectedItemY*1.5f);
			equipmentManager.getEquipments().add(key);
			break;
		case 6:
			Gold gold = new Gold(game.getHandler(),selectedItemX*1.5f, selectedItemY*1.5f);
			equipmentManager.getEquipments().add(gold);
			break;
		case 7:
			world.setTiles((int)(selectedItemX*1.5f/64), (int)(selectedItemX*1.5f/64), 2);
			break;
		case 8:
			Sword sword = new Sword(game.getHandler(),selectedItemX*1.5f, selectedItemY*1.5f);
			equipmentManager.addEquipment(sword);
			break;
		case 9:
			Coward coward = new Coward(game.getHandler(),selectedItemX*1.5f, selectedItemY*1.5f);
			entityManager.getEntities().add(coward);
			break;
		case 10:
			Door door = new Door(game.getHandler(),selectedItemX*1.5f, selectedItemY*1.5f);
		    entityManager.getEntities().add(door);
			break;
		case 11:
			Hunter hunter = new Hunter(game.getHandler(),selectedItemX*1.5f,selectedItemY*1.5f);
			entityManager.getEntities().add(hunter);
			break;
		case 12:
			Strategist strategist = new Strategist(game.getHandler(),selectedItemX*1.5f, selectedItemY*1.5f);
			entityManager.getEntities().add(strategist);
			break;
		case 13:
			hunter = new Hunter(game.getHandler(),selectedItemX*1.5f, selectedItemY*1.5f);
			HoundDog dog = new HoundDog(game.getHandler(),selectedItemX*1.5f, selectedItemY*1.5f,hunter);
			entityManager.getEntities().add(dog);
			break;
		case 14:
			Rock rock = new Rock(game.getHandler(),selectedItemX*1.5f, selectedItemY*1.5f);
			entityManager.addEntity(rock);
			break;
		case 15:
			world.setTiles((int)(selectedItemX*1.5f/64), (int)(selectedItemX*1.5f/64), 3);
			break;
		case 16:
			world.setTiles((int)(selectedItemX*1.5f/64), (int)(selectedItemX*1.5f/64), 4);
			break;
		}
	}
	/**
	 * a point to record the location of each item
	 * @author YAN XIAOYANG
	 *
	 */
	public class Point{
		private int x;
		private int y;
		public Point(int x, int y) {
			this.setX(x);
			this.setY(y);
		}
		public int getX() {
			return x;
		}
		public void setX(int x) {
			this.x = x;
		}
		public int getY() {
			return y;
		}
		public void setY(int y) {
			this.y = y;
		}
	}
}
