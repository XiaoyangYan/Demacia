package com.unsw.tilegame.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.unsw.tilegame.Game;
import com.unsw.tilegame.Handler;
import com.unsw.tilegame.entities.creatures.Player;
import com.unsw.tilegame.tiles.Tile;
import com.unsw.tilegame.worlds.World;

class TestPit {
	/**
	 * test whether the player is on the pit tile
	 */
	@Test
	void testPit() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world2.txt",1);
		world.getEntityManager().getEntities().clear();
		
		Player player = new Player(handler,1088,320);
		world.getEntityManager().getEntities().add(player);
		world.getEntityManager().setPlayer(player);
		handler.setWorld(world);
		int tx = (int)(player.getX())/Tile.TILEWIDTH;
		int ty = (int)((player.getY())/Tile.TILEHEIGHT);
		System.out.println(tx);
		System.out.println(ty);
		boolean pitted = false;
		if (player.collisionWithPitTile(tx,ty)) {
			pitted = true;
			player.setActive(false);
		}
		Assertions.assertEquals(pitted, true);
		Assertions.assertEquals(player.isActive(), false);
	}

}
