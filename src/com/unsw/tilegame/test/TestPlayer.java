package com.unsw.tilegame.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.unsw.tilegame.Game;
import com.unsw.tilegame.Handler;
import com.unsw.tilegame.entities.creatures.Player;
import com.unsw.tilegame.worlds.World;

class TestPlayer {
	/**
	 * for most of the methods of players has been tested in other test files, so there are only three simple tests
	 */
	@Test
	void testInstantiatePlayer() {
		Player player = new Player(null,100,200);
		Assertions.assertEquals(true,player.isActive());
		Assertions.assertEquals(player.getX(), 100);
		Assertions.assertEquals(player.getY(), 200);
	}
	
	@Test
	void testPlayerMove() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world2.txt",1);
		world.getEntityManager().getEntities().clear();
		Player p = new Player(handler, 150, 200);
		world.getEntityManager().getEntities().add(p);
		world.getEntityManager().setPlayer(p);
		handler.setWorld(world);
		p.setxMove(4);
		p.setyMove(-2);
		p.moveX();
		p.moveY();
		Assertions.assertEquals(p.getX(),154);
		Assertions.assertEquals(p.getY(), 198);
	}
	
	@Test
	void testPlayerCollideWithWall() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world2.txt",1);
		world.getEntityManager().getEntities().clear();
		Player p = new Player(handler, 0, 10);
		world.getEntityManager().getEntities().add(p);
		handler.setWorld(world);
		Assertions.assertEquals(p.collisionWithTile((int)p.getX(),(int)p.getY()),true);
	}
}
