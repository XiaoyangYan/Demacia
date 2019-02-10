package com.unsw.tilegame.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.unsw.tilegame.Game;
import com.unsw.tilegame.Handler;
import com.unsw.tilegame.entities.creatures.Player;
import com.unsw.tilegame.entities.statics.Exit;
import com.unsw.tilegame.worlds.World;

class TestExit {

	@Test
	void testInstantiateExit() {
		Exit exit = new Exit(null,300,300);
		Assertions.assertEquals(true,exit.isActive());
		Assertions.assertEquals(exit.getX(), 300);
		Assertions.assertEquals(exit.getY(), 300);
	}
	
	@Test
	void testGameWinEnterExit() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world2.txt",1);
		world.getEntityManager().getEntities().clear();
		Player p = new Player(handler, 1140, 1140);
		Exit d = new Exit(handler,1152,1152);
		world.getEquipmentManager().getEquipments().clear();
		world.getEntityManager().getEntities().add(p);
		world.getEntityManager().getEntities().add(d);
		world.getEntityManager().setPlayer(p);
		handler.setWorld(world);
		boolean isWin = false;
		if (p.getCollisionBounds(0, 0).getBoundsInLocal().intersects(d.getCollisionBounds(0, 0).getBoundsInLocal())){
			isWin = true;
		}
		Assertions.assertEquals(isWin, true);
	}

}
