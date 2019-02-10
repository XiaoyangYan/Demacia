package com.unsw.tilegame.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.unsw.tilegame.Game;
import com.unsw.tilegame.Handler;
import com.unsw.tilegame.entities.creatures.Player;
import com.unsw.tilegame.entities.enemy.Hunter;
import com.unsw.tilegame.entities.equipment.Sword;
import com.unsw.tilegame.worlds.World;

class TestSword {

	@Test
	void testPickSword() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world2.txt",1);
		world.getEntityManager().getEntities().clear();
		Player p = new Player(handler, 60, 60);
		Sword  a = new Sword(handler,50,50);
		world.getEntityManager().getEntities().add(p);
		world.getEquipmentManager().addEquipment(a);
		world.getEntityManager().setPlayer(p);
		handler.setWorld(world);
		a.pickUP();
		a.die();
		assertTrue(a.isActive()== false);
	}
	
	@Test
	void equipSword() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world2.txt",1);
		world.getEntityManager().getEntities().clear();
		Player p = new Player(handler, 60, 60);
		Sword a = new Sword(handler,50,50);
		world.getEntityManager().getEntities().add(p);
		world.getEquipmentManager().addEquipment(a);
		world.getEntityManager().setPlayer(p);
		handler.setWorld(world);
		a.pickUP();
		Player.setCurrentWeapon(a);
		assertTrue(p.hasWeapon() == true);
	}
	//For I have tested the attack method in other files related with test
	//the behaviour of enemy, so I will not show in this file.
	
	@Test
	void testHitFive() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world2.txt",1);
		world.getEntityManager().getEntities().clear();
		Player p = new Player(handler, 100, 130);
		Sword a = new Sword(handler,120,120);
		Player.setCurrentWeapon(a);
		Hunter hunter = new Hunter(handler,120,120);
		world.getEntityManager().getEntities().add(p);
		world.getEquipmentManager().addEquipment(a);
		world.getEntityManager().setPlayer(p);
		handler.setWorld(world);
		int hit5 = 0;
		Assertions.assertEquals(hunter.getHealth(),20);
		for(int t = 0; t < 5; t++) {
			if (p.getCollisionBounds(0, 0).getBoundsInParent().intersects(hunter.getCollisionBounds(0, 0).getBoundsInParent())){
				hunter.hurt(4);
				hit5++;
			}
		}
		p.getInventory().removeEquipment(Player.currentWeapon);
		Player.currentWeapon = null;
		Assertions.assertEquals(hunter.getHealth(),0);
		Assertions.assertEquals(Player.currentWeapon, null);
		Assertions.assertEquals(hit5,5);
	}
}
