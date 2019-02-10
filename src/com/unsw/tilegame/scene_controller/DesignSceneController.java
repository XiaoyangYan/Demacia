package com.unsw.tilegame.scene_controller;

import com.unsw.tilegame.Main;

import javafx.stage.Stage;

public class DesignSceneController {
	private Stage stage;

	/**
	 * @param stage
	 */
	public DesignSceneController(Stage stage) {
		if (stage == null) {
			throw new IllegalArgumentException("Stage cannot be null");
		}
		this.stage = stage;
	}


	/**
	 * pressing for one scene
	 */
	public void handleMousePress() {
		stage.setScene(Main.getScenes().get(SceneName.GAMESCENE));
	}
}
