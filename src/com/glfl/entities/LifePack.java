package com.glfl.entities;

import java.awt.image.BufferedImage;

public class LifePack extends Entity {
	
	private int lifeValue = 10;

	public LifePack(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		// TODO Auto-generated constructor stub
	}
	
	public void setLifeValue(int life) {
		this.lifeValue = life;
	}
	
	public int getLifeValue() {
		return lifeValue;
	}

}
