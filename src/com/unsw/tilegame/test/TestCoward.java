package com.unsw.tilegame.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.unsw.tilegame.Game;
import com.unsw.tilegame.Handler;
import com.unsw.tilegame.entities.creatures.Player;
import com.unsw.tilegame.entities.effect.ArrowEffect;
import com.unsw.tilegame.entities.effect.BombEffect;
import com.unsw.tilegame.entities.enemy.Coward;
import com.unsw.tilegame.entities.equipment.Arrow;
import com.unsw.tilegame.entities.equipment.Bomb;
import com.unsw.tilegame.entities.equipment.Sword;
import com.unsw.tilegame.worlds.World;

import javafx.scene.shape.Rectangle;

class TestCoward {

	@Test
	void testInstantiateCoward() {
		Coward coward = new Coward(null,100,200);
		Assertions.assertEquals(true,coward.isActive());
		Assertions.assertEquals(coward.getX(), 100);
		Assertions.assertEquals(coward.getY(), 200);
	}
	@Test
	void testHunterMove() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world2.txt",1);
		world.getEntityManager().getEntities().clear();
		Coward p = new Coward(handler, 150, 200);
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
	void testCowardCollideWithWall() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world2.txt",1);
		world.getEntityManager().getEntities().clear();
		Coward p = new Coward(handler, 0, 10);
		world.getEntityManager().getEntities().add(p);
		handler.setWorld(world);
		Assertions.assertEquals(p.collisionWithTile((int)p.getX(),(int)p.getY()),true);
	}
	
	@Test
	void testAttackPlayer() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world2.txt",1);
		world.getEntityManager().getEntities().clear();
		Coward p = new Coward(handler, 150, 200);
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
	void testPlayerAttackCowardBySword() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world2.txt",1);
		world.getEntityManager().getEntities().clear();
		world.getEquipmentManager().getEquipments().clear();
		Coward p = new Coward(handler, 150, 200);
		Player player = new Player(handler,130,200);
		Sword sword = new Sword(handler,135,135);
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
	void testPlayerAttackCowardByArrow() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world2.txt",1);
		world.getEntityManager().getEntities().clear();
		world.getEquipmentManager().getEquipments().clear();
		Coward p = new Coward(handler, 300, 200);
		Player player = new Player(handler,120,200);
		Arrow a = new Arrow(handler,100,100);
		world.getEquipmentManager().addEquipment(a);
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
		if (p.getCollisionBounds(0, 0).getBoundsInParent().intersects(shot.getCollisionBounds(shot.getSpeed(), 0).getBoundsInParent())){
			p.hurt(100);
		}
		//Assertions.assertEquals(0, p.health);
		Assertions.assertEquals(p.isActive(),false);
	}
	@Test
	void testPlayerAttackCowardByBomb() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world2.txt",1);
		world.getEntityManager().getEntities().clear();
		world.getEquipmentManager().getEquipments().clear();
		Coward p = new Coward(handler, 300, 200);
		Player player = new Player(handler,120,200);
		Bomb b = new Bomb(handler,100,100);
		world.getEquipmentManager().addEquipment(b);
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
		if (p.getCollisionBounds(0, 0).getBoundsInParent().intersects(shot.getCollisionBounds(shot.getSpeed(), 0).getBoundsInParent())){
			p.hurt(100);
		}
		Assertions.assertEquals(p.isActive(),false);
	}
	@Test
	void testCowardSpecialMoveRIGHTNearPlayer() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world2.txt",1);
		world.getEntityManager().getEntities().clear();
		world.getEquipmentManager().getEquipments().clear();
		Coward p = new Coward(handler, 300, 200);
		Player player = new Player(handler,80,200);
		world.getEntityManager().getEntities().add(player);
		world.getEntityManager().getEntities().add(p);
		world.getEntityManager().setPlayer(player);
		handler.setWorld(world);
		p.setxMove(4);
		p.moveX();
		Assertions.assertEquals(4, p.getSpeed());
		Assertions.assertEquals(304, p.getX());
		Assertions.assertEquals(200, p.getY());
	}
	@Test
	void testCowardSpecialMoveUPNearPlayer() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world2.txt",1);
		world.getEntityManager().getEntities().clear();
		world.getEquipmentManager().getEquipments().clear();
		Coward p = new Coward(handler,700, 320);
		Player player = new Player(handler,700,200);
		world.getEntityManager().getEntities().add(player);
		world.getEntityManager().getEntities().add(p);
		world.getEntityManager().setPlayer(player);
		handler.setWorld(world);
		p.setyMove(4);
		p.moveY();
		Assertions.assertEquals(4, p.getSpeed());
		Assertions.assertEquals(700, p.getX());
		Assertions.assertEquals(324, p.getY());
	}
	@Test
	void testCowardSpecialMoveLEFTFarFromPlayer() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world2.txt",1);
		world.getEntityManager().getEntities().clear();
		world.getEquipmentManager().getEquipments().clear();
		Coward p = new Coward(handler, 400, 200);
		Player player = new Player(handler,80,200);
		world.getEntityManager().getEntities().add(player);
		world.getEntityManager().getEntities().add(p);
		world.getEntityManager().setPlayer(player);
		handler.setWorld(world);
		p.setxMove(-4);
		p.moveX();
		Assertions.assertEquals(4, p.getSpeed());
		Assertions.assertEquals(396, p.getX());
		Assertions.assertEquals(200, p.getY());
	}
	@Test
	void testCowardSpecialMoveDOWNFarAwayPlayer() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world2.txt",1);
		world.getEntityManager().getEntities().clear();
		world.getEquipmentManager().getEquipments().clear();
		Coward p = new Coward(handler, 200, 820);
		Player player = new Player(handler,200,200);
		world.getEntityManager().getEntities().add(player);
		world.getEntityManager().getEntities().add(p);
		world.getEntityManager().setPlayer(player);
		handler.setWorld(world);
		p.setyMove(-4);
		p.moveY();
		Assertions.assertEquals(4, p.getSpeed());
		Assertions.assertEquals(200, p.getX());
		Assertions.assertEquals(816, p.getY());
	}
}
