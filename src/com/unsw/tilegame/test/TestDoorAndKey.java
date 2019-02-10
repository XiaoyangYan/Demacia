package com.unsw.tilegame.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.unsw.tilegame.Game;
import com.unsw.tilegame.Handler;
import com.unsw.tilegame.entities.creatures.Player;
import com.unsw.tilegame.entities.equipment.Key;
import com.unsw.tilegame.entities.statics.Door;
import com.unsw.tilegame.worlds.World;

class TestDoorAndKey {

	@Test
	void testPickUpAKey() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world1.txt",1);
		world.getEntityManager().getEntities().clear();
		Player p = new Player(handler, 60, 60);
		Key a = new Key(handler,50,50);
		world.getEntityManager().getEntities().add(p);
		world.getEquipmentManager().addEquipment(a);
		world.getEntityManager().setPlayer(p);
		handler.setWorld(world);
		a.pickUP();
		a.die();
		assertTrue(a.isActive()== false);
	}
	
	@Test
	void testKeyInInventory() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world1.txt",1);
		world.getEntityManager().getEntities().clear();
		Player p = new Player(handler, 60, 60);
		world.getEquipmentManager().getEquipments().clear();
		Key a = new Key(handler,100,100);
		world.getEntityManager().getEntities().add(p);
		world.getEquipmentManager().getEquipments().add(a);
		world.getEntityManager().setPlayer(p);
		handler.setWorld(world);
		handler.getWorld().getEntityManager().getPlayer().getInventory().addEquipment(a);		
		assertTrue(p.getInventory().getInventoryItems().get(0).getName()=="Key");
	}
	
	@Test
	void testInstantiateADoor() {
		Door door = new Door(null,300,300);
		Assertions.assertEquals(true,door.isActive());
		Assertions.assertEquals(door.getX(), 300);
		Assertions.assertEquals(door.getY(), 300);
	}
	
	@Test
	void testEnterDoor() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world1.txt",1);
		world.getEntityManager().getEntities().clear();
		Player p = new Player(handler, 400, 400);
		Door d = new Door(handler,430,400);
		world.getEquipmentManager().getEquipments().clear();
		Key a = new Key(handler,100,100);
		world.getEntityManager().getEntities().add(p);
		world.getEquipmentManager().getEquipments().add(a);
		world.getEntityManager().setPlayer(p);
		handler.setWorld(world);
		handler.getWorld().getEntityManager().getPlayer().getInventory().addEquipment(a);
		Assertions.assertEquals(d.open, false);
		Player.setCurrentWeapon(a);
		if (p.getCollisionBounds(0, 0).getBoundsInParent().intersects(d.getCollisionBounds(0, 0).getBoundsInParent())){
			if (Player.getCurrentWeapon().getId() == 6) {
				d.move();
			}
		}
		Assertions.assertEquals(Player.currentWeapon,null);
		Assertions.assertEquals(d.open, true);
	}

}
