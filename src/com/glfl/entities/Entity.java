package com.glfl.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.glfl.main.Game;
import com.glfl.world.Camera;

public class Entity {

	public static BufferedImage LIFEPACK_EN = Game.spritesheet.getSprite(6 * 16, 0, 16, 16);
	public static BufferedImage WEAPONRIGHT_EN = Game.spritesheet.getSprite(7 * 16, 0, 16, 16);
	public static BufferedImage WEAPONLEFT_EN = Game.spritesheet.getSprite(8 * 16, 0, 16, 16);
	public static BufferedImage BULLET_EN = Game.spritesheet.getSprite(6 * 16, 16, 16, 16);
	public static BufferedImage ENEMY_EN_1 = Game.spritesheet.getSprite(7 * 16, 16, 16, 16);
	public static BufferedImage ENEMY_EN_2 = Game.spritesheet.getSprite(8 * 16, 16, 16, 16);

	protected double x;
	protected double y;
	protected int width;
	protected int height;

	protected int maskx;
	protected int masky;
	protected int maskw;
	protected int maskh;

	protected BufferedImage sprite;

	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;

		this.maskx = 0;
		this.masky = 0;
		this.maskw = width;
		this.maskh = height;
	}

	public void setMask(int maskx, int masky, int maskwidth, int maskheight) {
		this.maskx = maskx;
		this.masky = masky;
		this.maskw = maskwidth;
		this.maskh = maskheight;
	}

	public int getX() {
		return (int) x;
	}

	public int getY() {
		return (int) y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void render(Graphics g) {
		g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);

//		setMask(3, 6, 10, 10);
//		g.setColor(Color.blue);
//		g.fillRect(this.getX() - Camera.x +maskx, this.getY() - Camera.y+masky,
//					maskw, maskh);
	}

	public void tick() {

	}

	public static boolean isColidding(Entity e1, Entity e2) {
		Rectangle e1mask = new Rectangle(e1.getX() + e1.maskx, e1.getY() + e1.masky, e1.maskw, e1.maskh);
		Rectangle e2mask = new Rectangle(e2.getX() + e2.maskx, e2.getY() + e2.masky, e2.maskw, e2.maskh);

		return e1mask.intersects(e2mask);
	}

}
