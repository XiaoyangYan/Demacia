package com.unsw.tilegame.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.unsw.tilegame.Game;
import com.unsw.tilegame.Handler;
import com.unsw.tilegame.entities.creatures.Player;
import com.unsw.tilegame.entities.effect.ArrowEffect;
import com.unsw.tilegame.entities.equipment.Arrow;
import com.unsw.tilegame.tiles.Tile;
import com.unsw.tilegame.worlds.World;

class TestArrow {
	/**
	 * the test aims to test the player pick up the arrow, use the pickUp() methods in the Arrow class
	 * and the moment we call the method, the arrow disappears.
	 */
	@Test
	void testPickArrow() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world1.txt",1);
		world.getEntityManager().getEntities().clear();
		Player p = new Player(handler, 60, 60);
		Arrow  a = new Arrow(handler,50,50);
		world.getEntityManager().getEntities().add(p);
		world.getEntityManager().setPlayer(p);
		world.getEquipmentManager().addEquipment(a);
		world.getEntityManager().setPlayer(p);
		handler.setWorld(world);
		a.pickUP();
		a.die();
		assertTrue(a.isActive()== false);
		assertTrue(handler.getPlayer().getInventory().getInventoryItems().size()==1);
	}
	/**
	 * the test aims to test the player equip the arrow, the moment the player pick up the arrow, the 
	 * arrow are changed to be an item and should be added to the inventory list, also should be set
	 *  as a currentWeapon.
	 */
	@Test
	void equipArrow() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world1.txt",1);
		world.getEntityManager().getEntities().clear();
		Player p = new Player(handler, 60, 60);
		Arrow  a = new Arrow(handler,50,50);
		world.getEntityManager().getEntities().add(p);
		world.getEntityManager().setPlayer(p);
		world.getEquipmentManager().addEquipment(a);
		world.getEntityManager().setPlayer(p);
		handler.setWorld(world);
		a.pickUP();
		handler.getPlayer().getInventory().addEquipment(a);
		Player.setCurrentWeapon(a);
		assertTrue(p.hasWeapon() == true);
	}
	/**
	 * The method is used to test the functionality of an arrow when it is shot out, the count should less
	 * one in the inventory list, and we proved that the arrow is alive when we shot this out
	 */
	@Test
	void arrowEffect() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world1.txt",1);
		world.getEntityManager().getEntities().clear();
		Player p = new Player(handler, 60, 60);
		Arrow  a = new Arrow(handler,50,50);
		world.getEntityManager().getEntities().add(p);
		world.getEntityManager().setPlayer(p);
		world.getEquipmentManager().addEquipment(a);
		world.getEntityManager().setPlayer(p);
		handler.setWorld(world);
		a.pickUP();
		Assertions.assertEquals(a.getCount(),10);
		Player.setCurrentWeapon(a);
		ArrowEffect shot = null;
		if (Player.getCurrentWeapon().getId() == 3) {
			shot = new ArrowEffect(handler,p.getX(),p.getY(),54,8,ArrowEffect.RIGHT);
		}
		Assertions.assertEquals(a.getCount(), 9);
		assertTrue(shot.isActive());
	}
	/**
	 * Arrow will be destroyed when they met the wall or other entities, so we just proved the arrow is
	 * not alive when it is destroyed.
	 */
	@Test
	void arrowEffectEnd() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world1.txt",1);
		world.getEntityManager().getEntities().clear();
		Player player = new Player(handler,200,280);
		Arrow  a = new Arrow(handler,50,50);
		world.getEquipmentManager().addEquipment(a);
		player.getInventory().addEquipment(a);
		Player.setCurrentWeapon(a);
		world.getEntityManager().getEntities().add(player);
		world.getEntityManager().setPlayer(player);
		handler.setWorld(world);
		ArrowEffect shot = new ArrowEffect(handler,player.getX(),player.getY(),54,8,ArrowEffect.LEFT); 
		int i = 8;
		for(int t = 0; t < i; t++) {
			shot.setX(shot.getX()-shot.getSpeed());
		}
		int tx = (int)(shot.getX()-shot.getSpeed()+shot.getBounds().getX())/Tile.TILEWIDTH;
		int ty = (int)(shot.getY()+shot.getBounds().getY())/Tile.TILEHEIGHT;
		if (shot.collisionWithTile(tx,ty)) {
			shot.setActive(false);
		}
		Assertions.assertEquals(shot.isActive(), false);
	}
}
