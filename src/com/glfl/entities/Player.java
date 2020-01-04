package com.glfl.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.glfl.main.Game;
import com.glfl.world.Camera;
import com.glfl.world.World;

public class Player extends Entity {

	public enum Direction {
		RIGHT, LEFT;
	}

	public boolean right, left, up, down;
	public double speed = 1;
	public Direction direction = Direction.RIGHT;
	private boolean moved = false;

	private int frames = 0, maxFrames = 5, index = 0, maxIndex = 4;
	private List<BufferedImage> rightPlayer;
	private List<BufferedImage> leftPlayer;

	public double maxLife = 100;
	public double life;

	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		// TODO Auto-generated constructor stub

		rightPlayer = new ArrayList<BufferedImage>();
		leftPlayer = new ArrayList<BufferedImage>();

		life = maxLife;

		for (int i = 0; i < maxIndex; i++) {
			rightPlayer.add(Game.spritesheet.getSprite(32 + (i * 16), 0, 16, 16));
		}
		for (int i = 0; i < maxIndex; i++) {
			leftPlayer.add(Game.spritesheet.getSprite(32 + (((maxIndex - 1) - i) * 16), 16, 16, 16));
		}

		setMask(3, 0, 10, 16);
	}

	public void tick() {
		moved = false;
		if (right && World.isFree((x + speed), this.getY())) {
			x += speed;
			moved = true;
		} else if (left && World.isFree((x - speed), this.getY())) {
			x -= speed;
			moved = true;
		}

		if (up && World.isFree(this.getX(), (y - speed))) {
			y -= speed;
			moved = true;
		} else if (down && World.isFree(this.getX(), (y + speed))) {
			y += speed;
			moved = true;
		}

		if (moved) {
			frames++;
			if (frames == maxFrames) {
				frames = 0;
				index++;
				if (!(index < maxIndex)) {
					index = 0;
				}
			}

		}
		
		checkItems();

		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH / 2), 0, (World.WIDTH * 16) - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT / 2), 0, (World.HEIGHT * 16) - Game.HEIGHT);

	}

	public void render(Graphics g) {

		if (!moved) {
			if (direction == Direction.RIGHT) {
				g.drawImage(rightPlayer.get(0), this.getX() - Camera.x, this.getY() - Camera.y, null);
			} else if (direction == Direction.LEFT) {
				g.drawImage(leftPlayer.get(0), this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
		} else {
			if (direction == Direction.RIGHT) {
				g.drawImage(rightPlayer.get(index), this.getX() - Camera.x, this.getY() - Camera.y, null);
			} else if (direction == Direction.LEFT) {
				g.drawImage(leftPlayer.get(index), this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
		}

//		setMask(3, 0, 10, 16);
//		g.setColor(Color.blue);
//		g.fillRect(this.getX() - Camera.x +maskx, this.getY() - Camera.y+masky,
//					maskw, maskh);
	}

	public void playerTakesDamage(int damage) {
		life = life - Game.rand.nextInt(damage + 1);
		System.out.println("life: " + life);
		if (life <= 0) {
			// YOU LOSE!!
		}
	}
	
	public void checkItems() {
		for(Entity e : Game.entities) {
			if(e instanceof LifePack) {
				if(lifePackColidding(e)) {
					return;
				}
			}
		}
	}
	
	private boolean lifePackColidding(Entity e) {
		if(Entity.isColidding(this, e)) {
			LifePack lp = (LifePack) e;
			life += lp.getLifeValue();
			if(life >= maxLife) {
				life = maxLife;
			}
			Game.entitiesToRemove.add(e);
			return true;
		}
		return false;
	}

}
