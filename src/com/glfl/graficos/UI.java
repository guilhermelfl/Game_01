package com.glfl.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.glfl.main.Game;

public class UI {
	int x = 4;
	int y = 4;
	
	int xBorder = 1;
	int yBorder = 1;
	
	int uiHeight = 8;
	int uiWidth = 50;
	
	int fontSize = 8;
	int fontYBorder = 1;
	
	

	public void render(Graphics g) {
		int maxLifeBarWidth = 50;
		int lifeBarWidth = (int)((Game.player.life/Game.player.maxLife) * maxLifeBarWidth);
		g.setColor(Color.black);
		g.fillRect(x-xBorder, y-yBorder, maxLifeBarWidth+ xBorder*2, uiHeight + yBorder*2);
		g.setColor(Color.red);
		g.fillRect(x, y, maxLifeBarWidth, uiHeight);
		g.setColor(Color.green);
		g.fillRect(x, y, lifeBarWidth, uiHeight);
		g.setColor(Color.gray);
		g.setFont(new Font("arial",Font.BOLD,fontSize));
		g.drawString((int)Game.player.life+" / "+(int)Game.player.maxLife,
				(int)(x+fontYBorder), y+uiHeight-fontYBorder);
	}

}
