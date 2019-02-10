package com.unsw.tilegame.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.unsw.tilegame.Game;
import com.unsw.tilegame.Handler;
import com.unsw.tilegame.entities.creatures.Player;
import com.unsw.tilegame.entities.enemy.Hunter;
import com.unsw.tilegame.entities.potion.Invincible;
import com.unsw.tilegame.worlds.World;

class TestInvincible {

	@Test
	void testPickUpInvincible() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world1.txt",1);
		world.getEntityManager().getEntities().clear();
		Player p = new Player(handler, 60, 60);
		Invincible  a = new Invincible(handler,50,50);
		world.getEntityManager().getEntities().add(p);
		world.getEquipmentManager().getEquipments().add(a);
		world.getEntityManager().setPlayer(p);
		handler.setWorld(world);
		a.pickUP();
		a.die();
		assertTrue(a.isActive()== false);
	}
	
	@Test
	void testInvincibleStatus() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world1.txt",1);
		world.getEntityManager().getEntities().clear();
		Player p = new Player(handler, 100, 200);
		Invincible  a = new Invincible(handler,120,190);
		world.getEntityManager().getEntities().add(p);
		world.getEquipmentManager().addEquipment(a);
		world.getEntityManager().setPlayer(p);
		handler.setWorld(world);
		a.pickUP();
		boolean collided = false;
		if (a.getCollisionBounds(0, 0).getBoundsInParent().intersects(p.getCollisionBounds(0, 0).getBoundsInParent())) {
			collided = true;
		}
		assertTrue(collided == true);
		a.pickUP();
		Player.setOnInvincible(true);
		assertTrue(Player.getInvincible()== true);
	}
	@Test
	void testBecomeInvincible() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world1.txt",1);
		world.getEntityManager().getEntities().clear();
		Player p = new Player(handler, 800, 300);
		Hunter h = new Hunter(handler,900,300);
		Invincible  a = new Invincible(handler,120,120);
		world.getEntityManager().getEntities().add(p);
		world.getEquipmentManager().addEquipment(a);
		world.getEntityManager().getEntities().add(h);
		world.getEntityManager().setPlayer(p);
		handler.setWorld(world);
		a.pickUP();
		Player.setOnInvincible(true);
		p.setxMove(10);
		int i = (int)((h.getX()-p.getX())/p.getxMove());
		for(int t = 0; t < i; t++) {
			p.moveX();
		}
		if (p.getCollisionBounds(0, 0).getBoundsInParent().intersects(h.getCollisionBounds(0, 0).getBoundsInParent())){
			h.hurt(100);
		}
		Assertions.assertEquals(p.isActive(), true);
		Assertions.assertEquals(h.isActive(), false);
		
	}
}
