package com.unsw.tilegame.entities.equipment;

import com.unsw.tilegame.Handler;
import com.unsw.tilegame.tiles.Tile;
import com.unsw.tilegame.tool.Assets;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


/**
 * arrow class
 * @author xiaoyang
 * @version 1.5
 * @since 1.5
 */
public class Arrow extends Equipment{

	/**
	 * @param handler
	 * @param x
	 * @param y
	 */
	public Arrow(Handler handler, float x, float y) {
		super(handler, x, y, Tile.TILEWIDTH, Tile.TILEHEIGHT);
		id = 3;
		count = 10;
		name = "Arrow";
	}

	/**
	 * @param g
	 */
	@Override
	public void render(GraphicsContext g) {
		g.drawImage(Assets.arrow, (int) (x - handler.getGameCamera().getxOffset()), 
				(int) (y - handler.getGameCamera().getyOffset()), width, height);
	}


	@Override
	public void die() {
		this.active = false;
	}

	@Override
	public void pickUP() {
		handler.getPlayer().getInventory().addEquipment(this);
	}



	@Override
	public void tick() {
		if (handler.getPlayer().getCollisionBounds(0f, 0f).getBoundsInLocal().intersects(
				getCollisionBounds(0f,0f).getBoundsInLocal())){
			pickUP();
			die();
		}
	}

	/**
	 * @return image of arrow
	 */
	public Image getTexture() {
		return Assets.arrow;
	}
}
