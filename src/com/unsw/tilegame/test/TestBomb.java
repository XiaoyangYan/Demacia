package com.unsw.tilegame.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.unsw.tilegame.Game;
import com.unsw.tilegame.Handler;
import com.unsw.tilegame.entities.creatures.Player;
import com.unsw.tilegame.entities.effect.BombEffect;
import com.unsw.tilegame.entities.equipment.Bomb;
import com.unsw.tilegame.tiles.Tile;
import com.unsw.tilegame.worlds.World;

class TestBomb {

	@Test
	void testAddBomb() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world2.txt",1);
		world.getEntityManager().getEntities().clear();
		Player p = new Player(handler, 60, 60);
		world.getEquipmentManager().getEquipments().clear();
		Bomb a = new Bomb(handler,80,80);
		world.getEntityManager().getEntities().add(p);
		world.getEquipmentManager().getEquipments().add(a);
		world.getEntityManager().setPlayer(p);
		handler.setWorld(world);
		handler.getWorld().getEntityManager().getPlayer().getInventory().addEquipment(a);
		assertTrue(p.getInventory().getInventoryItems().get(0).getName()=="Bomb");
		
	}
	@Test
	void testBombInInventory() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world2.txt",1);
		world.getEntityManager().getEntities().clear();
		Player p = new Player(handler, 60, 60);
		world.getEquipmentManager().getEquipments().clear();
		Bomb a = new Bomb(handler,80,80);
		world.getEntityManager().getEntities().add(p);
		world.getEntityManager().setPlayer(p);
		world.getEquipmentManager().addEquipment(a);
		handler.setWorld(world);
		handler.getWorld().getEntityManager().getPlayer().getInventory().addEquipment(a);		
		assertTrue(p.getInventory().getInventoryItems().get(0).getName()=="Bomb");
	}
	@Test
	void testBombisActive() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world2.txt",1);
		world.getEntityManager().getEntities().clear();
		Player p = new Player(handler, 60, 60);
		Bomb  a = new Bomb(handler,50,50);
		world.getEntityManager().getEntities().add(p);
		world.getEquipmentManager().addEquipment(a);
		world.getEntityManager().setPlayer(p);
		handler.setWorld(world);
		a.pickUP();
		a.die();
		assertTrue(a.isActive()== false);
	}
	
	@Test
	void testBombEject() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world2.txt",1);
		world.getEntityManager().getEntities().clear();
		Player p = new Player(handler, 160, 160);
		Bomb a = new Bomb(handler,150,150);
		world.getEntityManager().getEntities().add(p);
		world.getEquipmentManager().addEquipment(a);
		world.getEntityManager().setPlayer(p);
		handler.setWorld(world);
		Player.setCurrentWeapon(a);
		BombEffect shot = null;
		if (Player.getCurrentWeapon().getId() == 7) {
			shot = new BombEffect(handler,p.getX(),p.getY(),BombEffect.RIGHT);
		}
		assertTrue(shot.isActive());
	}
	
	@Test
	void testBombBoom() {
		Game game = new Game();
		Handler handler = new Handler(game);
		World world = new World(handler, "res/worlds/world2.txt",1);
		world.getEntityManager().getEntities().clear();
		Player p = new Player(handler, 160, 160);
		Bomb a = new Bomb(handler,150,150);
		world.getEntityManager().getEntities().add(p);
		world.getEquipmentManager().addEquipment(a);
		world.getEntityManager().setPlayer(p);
		handler.setWorld(world);
		Player.setCurrentWeapon(a);
		BombEffect shot = null;
		if (Player.getCurrentWeapon().getId() == 7) {
			shot = new BombEffect(handler,p.getX(),p.getY(),BombEffect.LEFT);
		}
		int i = 12;
		for(int t = 0; t < i; t++) {
			shot.setX(shot.getX()-shot.getSpeed());
		}
		int tx = (int)(shot.getX()-shot.getSpeed()+shot.getBounds().getX())/Tile.TILEWIDTH;
		int ty = (int)(shot.getY()+shot.getBounds().getY())/Tile.TILEHEIGHT;
		if (shot.collisionWithTile(tx,ty)) {
			shot.setActive(false);
		}
		Assertions.assertEquals(shot.isActive(),false);
	}

}
