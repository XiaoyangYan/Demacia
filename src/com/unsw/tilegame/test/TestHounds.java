package com.unsw.tilegame.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.unsw.tilegame.Game;
import com.unsw.tilegame.Handler;
import com.unsw.tilegame.entities.creatures.Player;
import com.unsw.tilegame.entities.effect.ArrowEffect;
import com.unsw.tilegame.entities.effect.BombEffect;
import com.unsw.tilegame.entities.enemy.HoundDog;
import com.unsw.tilegame.entities.enemy.Hunter;
import com.unsw.tilegame.entities.equipment.Arrow;
import com.unsw.tilegame.entities.equipment.Bomb;
import com.unsw.tilegame.entities.equipment.Sword;
import com.unsw.tilegame.worlds.World;

import javafx.scene.shape.Rectangle;

class TestHounds {

	@Test
	void testInstantiateHounds() {
		Hunter hunter = new Hunter(null,100,200);
		HoundDog hound = new HoundDog(null,200,300,hunter);
		Assertions.assertEquals(true,hound.isActive());
		Assertions.assertEquals(hound.getX(), 200);
		Assertions.assertEquals(hound.getY(), 300);
	}
	
	@Test
	void testHoundDogMove() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world1.txt",1);
		world.getEntityManager().getEntities().clear();
		Hunter hunter = new Hunter(handler, 350, 200);
		HoundDog p = new HoundDog(handler,150, 200,hunter);
		world.getEntityManager().getEntities().add(p);
		handler.setWorld(world);
		p.setxMove(4);
		p.setyMove(-2);
		p.moveX();
		p.moveY();
		Assertions.assertEquals(p.getX(),154);
		Assertions.assertEquals(p.getY(), 198);
	}
	
	@Test
	void testHoundDogCollideWithWall() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world1.txt",1);
		world.getEntityManager().getEntities().clear();
		Hunter hunter = new Hunter(handler, 200, 200);
		HoundDog p = new HoundDog(handler,0, 10,hunter);
		world.getEntityManager().getEntities().add(p);
		handler.setWorld(world);
		Assertions.assertEquals(p.collisionWithTile((int)p.getX(),(int)p.getY()),true);
	}
	
	@Test
	void testAttackPlayer() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world1.txt",1);
		world.getEntityManager().getEntities().clear();
		Hunter hunter = new Hunter(handler, 150, 200);
		HoundDog p = new HoundDog(handler,200,300,hunter);
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
	
	@Test
	void testPlayerAttackHoundBySword() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world1.txt",1);
		world.getEntityManager().getEntities().clear();
		world.getEquipmentManager().getEquipments().clear();
		Hunter hunter = new Hunter(handler, 150, 200);
		HoundDog p = new HoundDog(handler,120,180,hunter);
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
	@Test
	void testPlayerAttackHoundByArrow() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world1.txt",1);
		world.getEntityManager().getEntities().clear();
		world.getEquipmentManager().getEquipments().clear();
		Hunter hunter = new Hunter(handler, 350, 200);
		HoundDog p = new HoundDog(handler,300, 200,hunter);
		Player player = new Player(handler,120,200);
		Arrow a = new Arrow(handler,100,100);
		player.getInventory().addEquipment(a);
		Player.setCurrentWeapon(a);
		world.getEntityManager().getEntities().add(player);
		world.getEntityManager().getEntities().add(p);
		world.getEntityManager().setPlayer(player);
		handler.setWorld(world);
		ArrowEffect shot = new ArrowEffect(handler,player.getX(),player.getY(),54,20,ArrowEffect.RIGHT); 
		int i = (int)((p.getX()-player.getX())/shot.getSpeed());
		System.out.println(i);
		for(int t = 0; t < i; t++) {
			shot.setX(shot.getX()+shot.getSpeed());
		}
		if (p.getCollisionBounds(0, 0).getBoundsInParent().intersects(shot.getCollisionBounds(0, 0).getBoundsInParent())){
			p.hurt(100);
		}
		//Assertions.assertEquals(0, p.health);
		Assertions.assertEquals(p.isActive(),false);
	}
	@Test
	void testPlayerAttackHoundByBomb() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world1.txt",1);
		world.getEntityManager().getEntities().clear();
		world.getEquipmentManager().getEquipments().clear();
		Hunter hunter = new Hunter(handler, 300, 400);
		HoundDog p = new HoundDog(handler,300, 200,hunter);
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
		Assertions.assertEquals(p.isActive(),true);
		for(int a = 0; a < i; a++) {
			shot.setX(shot.getX()+shot.getSpeed());
		}
		if (p.getCollisionBounds(0, 0).getBoundsInParent().intersects(shot.getCollisionBounds(0, 0).getBoundsInParent())){
			p.hurt(100);
		}
		Assertions.assertEquals(p.isActive(),false);
	}
	@Test
	void testHoundSpecialMoveLEFT() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world1.txt",1);
		world.getEntityManager().getEntities().clear();
		world.getEquipmentManager().getEquipments().clear();
		Hunter hunter = new Hunter(handler, 300, 400);
		HoundDog p = new HoundDog(handler,300, 200,hunter);
		Player player = new Player(handler,80,200);
		world.getEntityManager().getEntities().add(player);
		world.getEntityManager().getEntities().add(p);
		world.getEntityManager().setPlayer(player);
		handler.setWorld(world);
		p.setxMove(-3);
		p.moveX();
		p.setyMove(3);
		p.moveY();
		Assertions.assertEquals(3, p.getSpeed());
		Assertions.assertEquals(297, p.getX());
		Assertions.assertEquals(203, p.getY());
	}
	/**
	 * the test aims to test the dogs move and the case is that
	 * ********************************hunter******
	 * *********************player*****************
	 * ************hound***************************
	 * the hounds speed is 3, according to the rule of dog's move i have written in the function move() in HoundDog classes
	 * the dog will move right in speed 3 to get closer to the player and move upwards in speed 3.
	 */
	@Test
	void testHoundSpecialMoveRIGHTUP() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world3.txt",1);
		world.getEntityManager().getEntities().clear();
		world.getEquipmentManager().getEquipments().clear();
		Hunter hunter = new Hunter(handler, 500, 120);
		HoundDog p = new HoundDog(handler,195,300,hunter);
		Player player = new Player(handler,400,200);
		world.getEntityManager().getEntities().add(player);
		world.getEntityManager().getEntities().add(p);
		world.getEntityManager().setPlayer(player);
		handler.setWorld(world);
		p.setxMove(3);
		p.moveX();
		p.setyMove(-3);
		p.moveY();
		Assertions.assertEquals(3, p.getSpeed());
		Assertions.assertEquals(198, p.getX());
		Assertions.assertEquals(297, p.getY());
	}
	/**
	 * the test aims to test the dogs move and the case is that
	 * ****************hound***********************
	 * ********************hunter******************
	 * **************************player************
	 * the hounds speed is 3, according to the rule of dog's move i have written in the function move() in HoundDog classes
	 * the dog will move right to get closer to the player by a speed of 2.2*3 and move upwards by a speed of 2.2*3.
	 */

	@Test
	void testHoundSpecialMoveRIGHTDOWN() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world1.txt",1);
		world.getEntityManager().getEntities().clear();
		world.getEquipmentManager().getEquipments().clear();
		Hunter hunter = new Hunter(handler, 400, 300);
		HoundDog p = new HoundDog(handler,195,200,hunter);
		Player player = new Player(handler,800,400);
		world.getEntityManager().getEntities().add(player);
		world.getEntityManager().getEntities().add(p);
		world.getEntityManager().setPlayer(player);
		handler.setWorld(world);
		p.setxMove(6);
		p.moveX();
		p.setyMove(6);
		p.moveY();
		Assertions.assertEquals(3, p.getSpeed());
		Assertions.assertEquals(201, (int)p.getX());
		Assertions.assertEquals(206, (int)p.getY());
	}
}
