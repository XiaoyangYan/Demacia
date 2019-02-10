package com.unsw.tilegame.input;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * managing key input
 * @see java.util.EventListener
 * @author xiaoyang
 * @version 1.2
 * @since 1.2
 */
public class MouseManagerJavaFx implements EventHandler<MouseEvent> {
	private boolean leftPressed, rightPressed;
	private int mouseX, mouseY;


	/**
	 * @param event
	 */
	@Override
	public void handle(MouseEvent event) {
		if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
			mousePressed(event);
		} else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
			mouseReleased(event);
		} else if (event.getEventType() == MouseEvent.MOUSE_MOVED) {
			mouseMoved(event);
		}
		
	}

	/**
	 * @param e
	 */
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseButton.PRIMARY)
			leftPressed = true;
		else if(e.getButton() == MouseButton.SECONDARY)
			rightPressed = true;
	}

	/**
	 * @param e
	 */
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseButton.PRIMARY)
			leftPressed = false;
		else if(e.getButton() == MouseButton.SECONDARY)
			rightPressed = false;
	}

	/**
	 * @param e
	 */
	public void mouseMoved(MouseEvent e) {
		mouseX = (int) e.getX();
		mouseY = (int) e.getY();
	}
	
	public boolean isLeftPressed() {
		return leftPressed;
	}

	public void setLeftPressed(boolean leftPressed) {
		this.leftPressed = leftPressed;
	}

	public boolean isRightPressed() {
		return rightPressed;
	}

	public void setRightPressed(boolean rightPressed) {
		this.rightPressed = rightPressed;
	}

	public int getMouseX() {
		return mouseX;
	}

	public void setMouseX(int mouseX) {
		this.mouseX = mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

	public void setMouseY(int mouseY) {
		this.mouseY = mouseY;
	}

}
