package com.glfl.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.glfl.entities.BulletPack;
import com.glfl.entities.Enemy;
import com.glfl.entities.Entity;
import com.glfl.entities.LifePack;
import com.glfl.entities.Weapon;
import com.glfl.main.Game;

public class World {

	private static Tile[] tiles;
	public static int WIDTH, HEIGHT;
	public static final int TILE_SIZE = 16;

	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			tiles = new Tile[map.getWidth() * map.getHeight()];
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
			for (int xx = 0; xx < map.getWidth(); xx++) {
				for (int yy = 0; yy < map.getHeight(); yy++) {
					int pixelAtual = pixels[xx + (yy * map.getWidth())];
					if (pixelAtual == 0xFFFFFFFF) {
						// System.out.println("Branco/parede");
						tiles[xx + (yy * WIDTH)] = new WallTile(xx * 16, yy * 16, Tile.TILE_WALL);
					} else if (pixelAtual == 0xFF000000) {
						// System.out.println("Preto/chao");
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR);
					} else {
						// Preto/Chão
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR);
						if (pixelAtual == 0xFF0026FF) {
//							System.out.println("azul/jogador");
							Game.player.setX(xx * 16);
							Game.player.setY(yy * 16);
						} else if (pixelAtual == 0xFFFF0000) {
//							 System.out.println("vermelho/inimigo");
							Enemy en = new Enemy(xx * 16, yy * 16, 16, 16, null);
							Game.entities.add(en);
							Game.enemies.add(en);
						} else if (pixelAtual == 0xFFFFFF02) {
							System.out.println("amarelo/municao");
							BulletPack bp = new BulletPack(xx * 16, yy * 16, 16, 16, Entity.BULLET_EN);
//							bp.setMask(3, 6, 10, 10);
							Game.entities.add(bp);
						} else if (pixelAtual == 0xFFFF00DC) {
//							 System.out.println("rosa/vida");
							LifePack lp = new LifePack(xx * 16, yy * 16, 16, 16, Entity.LIFEPACK_EN);
							lp.setMask(3, 6, 10, 10);
							Game.entities.add(lp);
						} else if (pixelAtual == 0xFFFFB200) {
//							 System.out.println("laranja/arma");
							Weapon weapon = new Weapon(xx * 16, yy * 16, 16, 16, Entity.WEAPONRIGHT_EN);
							weapon.setMask(5, 8, 11, 5);
							Game.entities.add(weapon);
						}
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static boolean isFree(double xnextDouble, double ynextDouble) {
		int xnext = (int) xnextDouble;
		int ynext = (int) ynextDouble;
		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;

		int x2 = (xnext + TILE_SIZE - 1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;

		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext + TILE_SIZE - 1) / TILE_SIZE;

		int x4 = (xnext + TILE_SIZE - 1) / TILE_SIZE;
		int y4 = (ynext + TILE_SIZE - 1) / TILE_SIZE;

		return !(tiles[x1 + y1 * World.WIDTH] instanceof WallTile || tiles[x2 + y2 * World.WIDTH] instanceof WallTile
				|| tiles[x3 + y3 * World.WIDTH] instanceof WallTile
				|| tiles[x4 + y4 * World.WIDTH] instanceof WallTile);
	}

	public void render(Graphics g) {
		int xStart = Camera.x >> 4;
		int yStart = Camera.y >> 4;

		int xFinal = xStart + (Game.WIDTH >> 4);
		int yFinal = yStart + (Game.HEIGHT >> 4);

		for (int xx = xStart; xx <= xFinal; xx++) {
			for (int yy = yStart; yy <= yFinal; yy++) {
				if (xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT)
					continue;
				Tile tile = tiles[xx + (yy * WIDTH)];
				tile.render(g);
			}
		}
	}

}
