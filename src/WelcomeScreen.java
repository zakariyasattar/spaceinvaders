import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class WelcomeScreen extends Screen
{
	//image to draw on the welcome screen
	private static Image img = ImageLoader.loadCompatibleImage("sprites/space-invaders.jpg");

	//the position to draw a message
	private int x, y;
	private int counter = 0;

	public WelcomeScreen(GameState s, int w, int h) {
		super(s, w, h);
	}

	//draw the welcome screen
	public void render(Graphics2D g) {

		//draw our image in the middle of the screen
		g.drawImage(img, width/2 - img.getWidth(null)/2, height/2 - img.getHeight(null)/2, null);

		//set the drawing font and color
		g.setFont(new Font("Circular", Font.BOLD, 42));
		g.setColor(Color.orange);

		//draw a message
		if(counter < 40)
			g.drawString("Press Enter to See Instructions", 85, 80);
		counter++;
		if(counter > 80)
			counter = 0;
	}

	//updates the welcome screen
	public void update() {

		//move the message 1 pixel down and to the right every update
		x++;
		y++;

		//bring the message back on to the screen if it leaves
		if(x > width)
			x = 0;
		if(y > height)
			y = 0;
	}

	//called when a key is pressed
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if(code == KeyEvent.VK_ENTER) {
			state.switchToInstructionScreen();

		}

	}

	public void keyReleased(KeyEvent e) {
	}

	public void mousePressed(Point2D p) {
	}

	public void mouseReleased(Point2D p) {
	}

	public void mouseMoved(Point2D p) {
	}

	public void mouseDragged(Point2D p) {
	}
}
