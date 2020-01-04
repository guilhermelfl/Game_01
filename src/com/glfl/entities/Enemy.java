package com.glfl.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.glfl.main.Game;
import com.glfl.world.Camera;
import com.glfl.world.World;

public class Enemy extends Entity {

	public enum Direction {
		RIGHT, LEFT;
	}

	private double speed = 0.4;
	private int enemyAtack = 1;
	private Direction direction = Direction.RIGHT;

	private int frames = 0, maxFrames = 10, index = 0, maxIndex = 4;

	private List<BufferedImage> sprites;

	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, null);
		// TODO Auto-generated constructor stub
		sprites = new ArrayList<BufferedImage>();

		sprites.add(Game.spritesheet.getSprite(112, 16, 16, 16));
		sprites.add(Game.spritesheet.getSprite(112 + (1 * 16), 16, 16, 16));
		sprites.add(Game.spritesheet.getSprite(112 + (2 * 16), 16, 16, 16));
		sprites.add(Game.spritesheet.getSprite(112 + (2 * 16), 0, 16, 16));
		
		setMask(3, 6, 10, 10);
	}

	public void tick() {
		if (Game.rand.nextBoolean()) {
			if (!this.isCollidingWithPlayer()) {
				if (Game.player.getX() < this.getX() && World.isFree((this.x - speed), this.getY())
						&& !isColliding((this.x - speed), this.getY())) {
					this.x -= speed;
					direction = Direction.LEFT;
				} else if (Game.player.getX() > this.getX() && World.isFree((this.x + speed), this.getY())
						&& !isColliding((this.x + speed), this.getY())) {
					this.x += speed;
					direction = Direction.RIGHT;
				}

				if (Game.player.getY() < this.getY() && World.isFree(this.getX(), (this.y - speed))
						&& !isColliding(this.getX(), (this.y - speed))) {
					this.y -= speed;
				} else if (Game.player.getY() > this.getY() && World.isFree(this.getX(), (this.y + speed))
						&& !isColliding(this.getX(), (this.y + speed))) {
					this.y += speed;
				}
			} else {
				// encostou no player
				Game.player.playerTakesDamage(this.enemyAtack);
			}
		}

		frames++;
		if (frames == maxFrames) {
			frames = 0;
			if (direction == Direction.RIGHT) {
				index++;
				if (!(index < maxIndex)) {
					index = 0;
				}
			}
			if (direction == Direction.LEFT) {
				index--;
				if ((index < 0)) {
					index = maxIndex-1;
				}
			}
		}
	}

	public void render(Graphics g) {
		g.drawImage(sprites.get(index), this.getX() - Camera.x, this.getY() - Camera.y, null);
	}

	public boolean isCollidingWithPlayer() {

		Rectangle currentEnemy = new Rectangle(this.getX() + maskx, this.getY() + masky, maskw, maskh);
		Rectangle player = new Rectangle(Game.player.getX() + Game.player.maskx, Game.player.getY() + Game.player.masky,
				Game.player.maskw, Game.player.maskh);

		return currentEnemy.intersects(player);
	}

	public boolean isColliding(double xnextDouble, double ynextDouble) {
		int xnext = (int) xnextDouble;
		int ynext = (int) ynextDouble;
		Rectangle currentEnemy = new Rectangle(xnext + maskx, ynext + masky, maskw, maskh);

		for (int i = 0; i < Game.enemies.size(); i++) {
			Enemy e = Game.enemies.get(i);
			if (e == this) {
				continue;
			} else {
				Rectangle targetEnemy = new Rectangle(Game.enemies.get(i).getX() + maskx,
						Game.enemies.get(i).getY() + masky, maskw, maskh);
				if (currentEnemy.intersects(targetEnemy)) {
					return true;
				}
			}
		}

		return false;
	}

}
