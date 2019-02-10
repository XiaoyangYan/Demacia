package com.unsw.tilegame.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.unsw.tilegame.Game;
import com.unsw.tilegame.Handler;
import com.unsw.tilegame.entities.creatures.Player;
import com.unsw.tilegame.entities.potion.Hover;
import com.unsw.tilegame.tiles.Tile;
import com.unsw.tilegame.worlds.World;

class TestHover {

	@Test
	void testHoverPickUp() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world1.txt",1);
		world.getEntityManager().getEntities().clear();
		Player p = new Player(handler, 60, 60);
		Hover  a = new Hover(handler,50,50);
		world.getEntityManager().getEntities().add(p);
		world.getEquipmentManager().addEquipment(a);
		world.getEntityManager().setPlayer(p);
		handler.setWorld(world);
		a.pickUP();
		a.die();
		assertTrue(a.isActive()== false);
	}
	@Test
	void testPlayerHovering() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world2.txt",1);
		world.getEntityManager().getEntities().clear();
		Player p = new Player(handler, 60, 60);
		Hover  a = new Hover(handler,50,50);
		world.getEntityManager().getEntities().add(p);
		world.getEquipmentManager().addEquipment(a);
		world.getEntityManager().setPlayer(p);
		handler.setWorld(world);
		boolean collided = false;
		if (a.getCollisionBounds(0, 0).getBoundsInParent().intersects(p.getCollisionBounds(0, 0).getBoundsInParent())) {
			collided = true;
		}
		assertTrue(collided == true);
		a.pickUP();
		Player.setOnHover(true);
		assertTrue(p.isHover()== true);
	}
	
	@Test
	void testPlayerCollisionAfterHovering() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world3.txt",1);
		world.getEntityManager().getEntities().clear();
		Player p = new Player(handler, 120, 120);
		Hover  a = new Hover(handler,120,120);
		world.getEntityManager().getEntities().add(p);
		world.getEquipmentManager().addEquipment(a);
		world.getEntityManager().setPlayer(p);
		handler.setWorld(world);
		a.pickUP();
		Player.setOnHover(true);
		p.setxMove(-60);
		p.setyMove(-60);
		p.moveX();
		p.moveY();
		int ty = (int)(p.getY()+p.getBounds().getY())/Tile.TILEHEIGHT;
		int tx = (int)(p.getX()+p.getBounds().getX())/Tile.TILEWIDTH;
		assertTrue(p.collisionWithTile(tx,ty) == false);
		Assertions.assertEquals(p.getX(), 60);
		Assertions.assertEquals(p.getY(), 60);
	}
	
	@Test
	void testPlayerCollisionWithPitAfterHovering() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world2.txt",1);
		world.getEntityManager().getEntities().clear();
		Player p = new Player(handler, 100, 100);
		Hover  a = new Hover(handler,120,120);
		world.getEntityManager().getEntities().add(p);
		world.getEquipmentManager().addEquipment(a);
		world.getEntityManager().setPlayer(p);
		handler.setWorld(world);
		a.pickUP();
		Player.setOnHover(true);
		p.setX(192);
		p.setY(1024);
		int ty = (int)(p.getY()+p.getBounds().getY())/Tile.TILEHEIGHT;
		int tx = (int)(p.getX()+p.getBounds().getX())/Tile.TILEWIDTH;
		Assertions.assertEquals(p.collisionWithPitTile(tx,ty),true);
		Assertions.assertEquals(p.isActive(), true);
	}
	
}
