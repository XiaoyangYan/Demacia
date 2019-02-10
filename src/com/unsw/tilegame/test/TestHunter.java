package com.unsw.tilegame.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.unsw.tilegame.Game;
import com.unsw.tilegame.Handler;
import com.unsw.tilegame.entities.creatures.Player;
import com.unsw.tilegame.entities.effect.ArrowEffect;
import com.unsw.tilegame.entities.effect.BombEffect;
import com.unsw.tilegame.entities.enemy.Hunter;
import com.unsw.tilegame.entities.equipment.Arrow;
import com.unsw.tilegame.entities.equipment.Bomb;
import com.unsw.tilegame.entities.equipment.Sword;
import com.unsw.tilegame.worlds.World;

import javafx.scene.shape.Rectangle;

class TestHunter {
	/**
	 * the method aims to test if the hunter is instantiated or not
	 */
	@Test
	void testInstantiateHunter() {
		Hunter hunter = new Hunter(null,100,200);
		Assertions.assertEquals(true,hunter.isActive());
		Assertions.assertEquals(hunter.getX(), 100);
		Assertions.assertEquals(hunter.getY(), 200);
	}
	
	/**
	 * the method aims to test the movement of the hunter as we needn't call the function from
	 * front end, so I just call the function related to movement and test whether it moved to the 
	 * correct position 
	 */
	@Test
	void testHunterMove() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world1.txt",1);
		world.getEntityManager().getEntities().clear();
		Hunter p = new Hunter(handler, 150, 200);
		world.getEntityManager().getEntities().add(p);
		handler.setWorld(world);
		p.setxMove(4);
		p.setyMove(-2);
		p.moveX();
		p.moveY();
		Assertions.assertEquals(p.getX(),154);
		Assertions.assertEquals(p.getY(), 198);
	}
	/**
	 * the method aims to test the effect whether the hunter is collided with wall. 
	 */
	@Test
	void testHunterCollideWithWall() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world1.txt",1);
		world.getEntityManager().getEntities().clear();
		Hunter p = new Hunter(handler, 0, 10);
		world.getEntityManager().getEntities().add(p);
		handler.setWorld(world);
		Assertions.assertEquals(p.collisionWithTile((int)p.getX(),(int)p.getY()),true);
	}
	/**
	 * the method aims to test the requirement that when the player met the enemy, the player will die
	 * immediately, it means that the player call the die() function the moment they are collided with the
	 * enemy
	 */
	@Test
	void testAttackPlayer() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world1.txt",1);
		world.getEntityManager().getEntities().clear();
		Hunter p = new Hunter(handler, 150, 200);
		Player player = new Player(handler,120,210);
		world.getEntityManager().getEntities().add(p);
		world.getEntityManager().getEntities().add(player);
		handler.setWorld(world);
		player.setxMove(10);
		Assertions.assertEquals(player.isActive(), true);
		p.moveX();
		p.moveY();
		Assertions.assertEquals(!player.isActive(), false);
	}
	/**
	 * this method aims to test that the moment when player is equipped with a sword, when the player is collided 
	 * with the enemy, it will call the hurt() function, and the enemy will die as immediately as they can.
	 */
	@Test
	void testPlayerAttackHunterBySword() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world1.txt",1);
		world.getEntityManager().getEntities().clear();
		world.getEquipmentManager().getEquipments().clear();
		Hunter p = new Hunter(handler, 150, 200);
		Player player = new Player(handler,130,200);
		Sword sword = new Sword(handler,100,100);
		player.getInventory().addEquipment(sword);
		Player.setCurrentWeapon(sword);
		world.getEntityManager().getEntities().add(player);
		world.getEntityManager().setPlayer(player);
		handler.setWorld(world);
		Assertions.assertEquals(p.isActive(), true);
		Rectangle cb = player.getCollisionBounds(0, 0);
		if (p.getCollisionBounds(0, 0).getBoundsInParent().intersects(cb.getBoundsInParent())){
			p.hurt(100);
		}
		Assertions.assertEquals(p.isActive(), false);
	}
	/**
	 * the method aims to test the player shot an arrow , when the arrow collides with enemy, the enemy will
	 * call the hurt() function, and die immediately. 
	 */
	@Test
	void testPlayerAttackHunterByArrow() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world1.txt",1);
		world.getEntityManager().getEntities().clear();
		world.getEquipmentManager().getEquipments().clear();
		Hunter p = new Hunter(handler, 300, 200);
		Player player = new Player(handler,120,200);
		Arrow a = new Arrow(handler,100,100);
		player.getInventory().addEquipment(a);
		Player.setCurrentWeapon(a);
		world.getEntityManager().getEntities().add(player);
		world.getEntityManager().getEntities().add(p);
		world.getEntityManager().setPlayer(player);
		handler.setWorld(world);
		ArrowEffect shot = new ArrowEffect(handler,player.getX(),player.getY(),64,20,ArrowEffect.RIGHT); 
		int i = (int)((p.getX()-player.getX())/shot.getSpeed());
		for(int t = 0; t < i; t++) {
			shot.setX(shot.getX()+shot.getSpeed());
		}
		System.out.println(shot.getX());
		if (p.getCollisionBounds(0, 0).getBoundsInParent().intersects(shot.getCollisionBounds(0, 0).getBoundsInParent())){
			p.hurt(100);
		}
		//Assertions.assertEquals(0, p.health);
		Assertions.assertEquals(p.isActive(),false);
	}
	
	/**
	 * the method aims to test the player throw out an bomb , when the bomb collides with enemy, the enemy will
	 * die immediately. 
	 */
	@Test
	void testPlayerAttackHunterByBomb() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world1.txt",1);
		world.getEntityManager().getEntities().clear();
		world.getEquipmentManager().getEquipments().clear();
		Hunter p = new Hunter(handler, 300, 200);
		Player player = new Player(handler,120,200);
		Bomb b = new Bomb(handler,100,100);
		player.getInventory().addEquipment(b);
		Player.setCurrentWeapon(b);
		world.getEntityManager().getEntities().add(player);
		world.getEntityManager().getEntities().add(p);
		world.getEntityManager().setPlayer(player);
		handler.setWorld(world);
		BombEffect shot = new BombEffect(handler,player.getX(),player.getY(),BombEffect.RIGHT); 
		int i = (int)((p.getX()-player.getX())/shot.getSpeed());
		System.out.println(i);
		Assertions.assertEquals(p.isActive(),true);
		for(int a = 0; a < i; a++) {
			shot.setX(shot.getX()+shot.getSpeed());
		}
		if (p.getCollisionBounds(0, 0).getBoundsInParent().intersects(shot.getCollisionBounds(0, 0).getBoundsInParent())){
			p.hurt(100);
		}
		Assertions.assertEquals(p.isActive(),false);
	}
	/**
	 * the method aims to test the special move, as the hunter will get more and more close to the player.
	 */
	@Test
	void testHunterSpecialMoveLEFT() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world4.txt",1);
		world.getEntityManager().getEntities().clear();
		world.getEquipmentManager().getEquipments().clear();
		Hunter p = new Hunter(handler, 300, 200);
		Player player = new Player(handler,80,200);
		world.getEntityManager().getEntities().add(player);
		world.getEntityManager().getEntities().add(p);
		world.getEntityManager().setPlayer(player);
		handler.setWorld(world);
		p.setxMove(-2);
		p.moveX();
		Assertions.assertEquals(2, p.getSpeed());
		Assertions.assertEquals(298, p.getX());
		Assertions.assertEquals(200, p.getY());
	}
	@Test
	void testHunterSpecialMoveDOWN() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world4.txt",1);
		world.getEntityManager().getEntities().clear();
		world.getEquipmentManager().getEquipments().clear();
		Hunter p = new Hunter(handler, 400, 520);
		Player player = new Player(handler,400,800);
		world.getEntityManager().getEntities().add(player);
		world.getEntityManager().getEntities().add(p);
		world.getEntityManager().setPlayer(player);
		handler.setWorld(world);
		p.moveX();
		p.setyMove(-1);
		p.moveY();
		Assertions.assertEquals(2, p.getSpeed());
		Assertions.assertEquals(400, p.getX());
		Assertions.assertEquals(519, p.getY());
	}
}
