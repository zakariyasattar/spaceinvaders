/*
    This class represents a basic alien in the game
*/

import java.awt.*;
import java.awt.geom.*;

public class Alien extends GameObject {

    private Image image = ImageLoader.loadCompatibleImage("sprites/alienA1.png");
    private int updateCounter = 0;

	public Alien(int x, int y, int w, int h) {
        super(x, y, w, h);
	}

	public void update() {
    
        //keep track of how many times this Alien has been updated
        updateCounter++;
        
        //every 120th update, move the bounds of the alien half it's width to the right
        if(updateCounter % 120 == 0)
            getBounds().x += getBounds().width/2;
    
    }
    
    //draw the image represented by the alien
	public void render(Graphics2D g) {

		g.drawImage(image,
                    (int)getBounds().x,
                    (int)getBounds().y,
                    (int)getBounds().width,
                    (int)getBounds().height,
                    null);

	}

}