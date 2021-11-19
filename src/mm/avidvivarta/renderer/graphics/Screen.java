package mm.avidvivarta.renderer.graphics;

import java.util.Random;

public class Screen {

	private int width, height;

	private final int MAP_SIZE = 64;
	private final int MAP_SIZE_MASK = MAP_SIZE - 1;

	private int[] pixels;
	private int[] tiles = new int[MAP_SIZE * MAP_SIZE];
	private static int tileIndexForAttractor = 0;
	private static long initialTime = System.currentTimeMillis();

	private Random random = new Random();

	public Screen(int width, int height) {

		this.pixels = new int[width * height];
		this.width = width;
		this.height = height;

		for (int i = 0; i < MAP_SIZE * MAP_SIZE; i++) {
			this.tiles[i] = random.nextInt(0xffffff);
		}

	}

	public int[] render(int xUpdate, int yUpdate) {

		for (int y = 0; y < this.height; y++) {
			int yy = y + yUpdate;
			for (int x = 0; x < this.width; x++) {
				int xx = x + xUpdate;
				int tileIndex = ((xx >> 4) & this.MAP_SIZE_MASK) + ((yy >> 4) & this.MAP_SIZE_MASK) * this.MAP_SIZE;
				pixels[x + y * this.width] = this.tiles[tileIndex];
			}
		}
		return pixels;

	}

	public int[] renderLorenzPoints(int xUpdate, int yUpdate) {

		long timeNow = System.currentTimeMillis();
		if (timeNow - initialTime >= 1000) {
			initialTime = timeNow;
			tileIndexForAttractor = ++tileIndexForAttractor % this.tiles.length;
		}

		for (int y = 0; y < this.height; y++) {
			if (y < 0 || y >= this.height) break;
			for (int x = 0; x < this.width; x++) {
				if (x < 0 || y >= this.width) break;
				if (x == xUpdate && y == yUpdate) {
					pixels[x + y * this.width] = this.tiles[tileIndexForAttractor];
				}
			}
		}
		return pixels;

	}

	public void clear() {

		for (int i : pixels) {
			i = 0;
		}

	}

}
