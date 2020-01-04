package com.glfl.world;

public class Camera {

	public static int x = 0;
	public static int y = 0;

	public static int clamp(int atual, int min, int max) {
		if (atual < min) {
			return min;
		}else if (atual > max) {
			return max;
		}
		return atual;
	}

}
