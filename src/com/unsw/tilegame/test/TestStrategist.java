package com.unsw.tilegame.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.unsw.tilegame.Game;
import com.unsw.tilegame.Handler;
import com.unsw.tilegame.entities.creatures.Player;
import com.unsw.tilegame.entities.effect.ArrowEffect;
import com.unsw.tilegame.entities.effect.BombEffect;
import com.unsw.tilegame.entities.enemy.Strategist;
import com.unsw.tilegame.entities.equipment.Arrow;
import com.unsw.tilegame.entities.equipment.Bomb;
import com.unsw.tilegame.entities.equipment.Sword;
import com.unsw.tilegame.worlds.World;

import javafx.scene.shape.Rectangle;

class TestStrategist {

	@Test
	void testInstantiateStrategist() {
		Strategist strategist = new Strategist(null,100,200);
		Assertions.assertEquals(true,strategist.isActive());
		Assertions.assertEquals(strategist.getX(), 100);
		Assertions.assertEquals(strategist.getY(), 200);
	}

	@Test
	void testStrategistMove() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world2.txt",1);
		world.getEntityManager().getEntities().clear();
		Strategist p = new Strategist(handler, 150, 200);
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
	void testStrategistCollideWithWall() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world2.txt",1);
		world.getEntityManager().getEntities().clear();
		Strategist p = new Strategist(handler, 0, 10);
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
		Strategist p = new Strategist(handler, 150, 200);
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
	void testPlayerAttackStrategistBySword() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world2.txt",1);
		world.getEntityManager().getEntities().clear();
		world.getEquipmentManager().getEquipments().clear();
		Strategist p = new Strategist(handler, 150, 200);
		Player player = new Player(handler,130,200);
		Sword sword = new Sword(handler,100,100);
		player.getInventory().addEquipment(sword);
		Player.setCurrentWeapon(sword);
		world.getEntityManager().getEntities().add(player);
		world.getEntityManager().setPlayer(player);
		handler.setWorld(world);
		Rectangle cb = player.getCollisionBounds(0, 0);
		if (p.getCollisionBounds(0, 0).getBoundsInParent().intersects(cb.getBoundsInParent())){
			p.hurt(100);
		}
		Assertions.assertEquals(0, p.getHealth());
		Assertions.assertEquals(p.isActive(), false);
	}
	@Test
	void testPlayerAttackStrategistByArrow() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world2.txt",1);
		world.getEntityManager().getEntities().clear();
		world.getEquipmentManager().getEquipments().clear();
		Strategist p = new Strategist(handler, 150, 200);
		Player player = new Player(handler,80,280);
		Arrow a = new Arrow(handler,100,100);
		player.getInventory().addEquipment(a);
		Player.setCurrentWeapon(a);
		world.getEntityManager().getEntities().add(player);
		world.getEntityManager().getEntities().add(p);
		world.getEntityManager().setPlayer(player);
		handler.setWorld(world);
		ArrowEffect shot = new ArrowEffect(handler,player.getX(),player.getY(),54,8,ArrowEffect.RIGHT); 
		int i = (int)((p.getX()-player.getX())/shot.getSpeed());
		System.out.println(i);
		for(int t = 0; t < i; t++) {
			shot.setX(shot.getX()+shot.getSpeed());
		}
		if (p.getCollisionBounds(0, 0).getBoundsInParent().intersects(shot.getCollisionBounds(shot.getSpeed(), 0).getBoundsInParent())){
			p.hurt(100);
		}
		Assertions.assertEquals(p.isActive(),false);
	}
	
	@Test
	void testPlayerAttackStrategistByBomb() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world2.txt",1);
		world.getEntityManager().getEntities().clear();
		world.getEquipmentManager().getEquipments().clear();
		Strategist p = new Strategist(handler, 300, 200);
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
		for(int a = 0; a < i; a++) {
			shot.setX(shot.getX()+shot.getSpeed());
		}
		if (p.getCollisionBounds(0, 0).getBoundsInParent().intersects(shot.getCollisionBounds(shot.getSpeed(), 0).getBoundsInParent())){
			p.hurt(100);
		}
		Assertions.assertEquals(0, p.getHealth());
		Assertions.assertEquals(p.isActive(),false);
	}
}
