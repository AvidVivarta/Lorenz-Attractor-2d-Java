package mm.avidvivarta.renderer;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import mm.avidvivarta.Lorenz.LorenzAttractor;
import mm.avidvivarta.renderer.graphics.Screen;
import mm.avidvivarta.renderer.point.Point3D;
import mm.avidvivarta.renderer.point.PointConvertor;

public class Display extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	public static int width = 300;
	public static int height = width / 5 * 4;
	public static int scale = 3;
	public static final String TITLE = "Lorenz Attractor";
	private static boolean running = false;

	private Thread thread;
	private JFrame frame;
	private Screen screen;
	private LorenzAttractor la;

	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	private int xUpdateValue = 0, yUpdateValue = 0;

	public Display() {

//		System.out.println(ANSICodes.ANSI_BLACK_ON_WHITE + "Created Display object" + ANSICodes.ANSI_RESET);
		Dimension size = new Dimension(this.width * this.scale, this.height * this.scale);
		this.setPreferredSize(size);
		this.init();

	}

	private void init() {

		this.frame = new JFrame();
		this.screen = new Screen(this.width, this.height);
		this.la = new LorenzAttractor(10, 40, 0.05, 0.01);
	}

	public synchronized void start() {

		this.thread = new Thread(this, "Display");
		Display.running = true;
		this.thread.start();

	}

	public synchronized void stop() {

		Display.running = false;
		try {
			this.thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void run() {

		int updatesPerSecondsNeeded = 60;
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		double ns = 1000_000_000.0 / updatesPerSecondsNeeded;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		while (Display.running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				this.update();
				updates++;
				delta--;
			}
			this.render();
			frames++;
			if (System.currentTimeMillis() - timer >= 1000) {
				timer += 1000;
				this.frame.setTitle(TITLE + " | " + frames + " fps | " + updates + " ups");
				updates = 0;
				frames = 0;
			}
		}
		this.stop();

	}

	private void render() {

		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) { this.createBufferStrategy(3); return; }

		int[] values = this.screen.renderLorenzPoints(this.xUpdateValue, this.yUpdateValue);
		for (int i = 0; i < this.pixels.length; i++) {
			this.pixels[i] = values[i];
		}

		Graphics g = bs.getDrawGraphics();

		// Draws a black background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		// Draws the updated points of the attractor
		g.drawImage(this.image, 0, 0, this.getWidth(), this.getHeight(), null);

		g.dispose();
		bs.show();

	}

	private void update() {

		// Here points of lorenz attractor are updated
		Point3D point3d = this.la.iterate();
		Point point = PointConvertor.convertPoint(point3d);
		this.xUpdateValue = (int) point.getX();
		this.yUpdateValue = (int) point.getY();
		System.out.println("x update: " + this.xUpdateValue + ", y update: " + this.yUpdateValue + ", time: "
				+ this.la.getCurrentTime() + ", point3d: " + this.la.getCurrentLocation().toString());

	}

	public JFrame getFrame() {

		return frame;

	}

	public static void main(String[] args) {

		Display display = new Display();
		display.getFrame().setTitle(Display.TITLE);
		display.getFrame().add(display);
		display.getFrame().setResizable(false);
		display.getFrame().setVisible(true);
		display.getFrame().pack();
		display.getFrame().setLocationRelativeTo(null);
		display.getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		display.start();

	}
}
