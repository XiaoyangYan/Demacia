package com.unsw.tilegame.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.unsw.tilegame.Game;
import com.unsw.tilegame.Handler;
import com.unsw.tilegame.entities.creatures.Player;
import com.unsw.tilegame.entities.statics.Rock;
import com.unsw.tilegame.tiles.Tile;
import com.unsw.tilegame.worlds.World;

class TestBoulderandExit {

	@Test
	void testInstantiateBoulder() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world1.txt",1);
		world.getEntityManager().getEntities().clear();
		Rock p = new Rock(handler, 100, 300);
		world.getEntityManager().getEntities().add(p);
		handler.setWorld(world);
		Assertions.assertEquals(p.isActive(), true);
	}
	/**
	 * when the player is collided with a boulder, he can move the boulder, the following method test the collision
	 * of the player and rock, shows the effect the rock will move together with the rock
	 */
	@Test
	void pushRock() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world2.txt",1);
		world.getEntityManager().getEntities().clear();
		Rock p = new Rock(handler, 480, 200);
		Player player = new Player(handler,230,200);
		world.getEntityManager().getEntities().add(p);
		world.getEntityManager().getEntities().add(player);
		world.getEntityManager().setPlayer(player);
		handler.setWorld(world);
		player.setxMove(10);
		int i = (int)((p.getX()-player.getX())/player.getxMove());
		for(int t = 0; t < i; t++) {
			player.setX(player.getX()+player.getxMove());
		}
		boolean collided = false;
		if (p.getCollisionBounds(0, 0).getBoundsInParent().intersects(player.getCollisionBounds(0, 0).getBoundsInParent())){
			collided = true;
		}
		Assertions.assertEquals(collided, true);
		player.setxMove(-20);
		player.moveX();
		p.setXMove((float)player.getxMove());
		p.moveX();
		Assertions.assertEquals(player.getX(), 460);
		Assertions.assertEquals(p.getX(), 460);
	}
	/**
	 * the following method tests when a rock is on a switch tile, it can not moved anymore.
	 */
	@Test
	void RockOnSwitchTile() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world2.txt",1);
		world.getEntityManager().getEntities().clear();
		Rock p = new Rock(handler,832, 576);
		Player player = new Player(handler,230,200);
		world.getEntityManager().getEntities().add(p);
		world.getEntityManager().getEntities().add(player);
		world.getEntityManager().setPlayer(player);
		handler.setWorld(world);
		int tx = (int)(p.getX())/Tile.TILEWIDTH;
		int ty = (int)((p.getY())/Tile.TILEHEIGHT);
		System.out.println(tx);
		System.out.println(ty);
		boolean switched = false;
		if (p.collisionWithSwitchTile(tx,ty)) {
			switched = true;
		}
		Assertions.assertEquals(switched, true);
	}
}
