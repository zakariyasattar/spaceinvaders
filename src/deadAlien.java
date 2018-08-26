///*
//    This class represents a basic alien in the game
//*/
//
//import java.awt.*;
//import java.awt.geom.*;
//
//public class deadAlien extends Alien{
//
//    private Image image = ImageLoader.loadCompatibleImage("sprites/explosion.png");
//    private int updateCounter = 0;
//    private int shootCountDown = (int) ((Math.random() + 0) * 240);
//    boolean edge = false;
//    private boolean master = false;
//   
//	public deadAlien(int x, int y, int w, int h) {
//		super(x, y, w, h, false);
//	}
//	public boolean isMaster() {
//		return master;
//	}
//        
//	public Laser shoot() {
//		if(shootCountDown <= 0) {
//			shootCountDown = (int) ((Math.random() + 0) * 240);
//			return new Laser((int)getBounds().x, (int)(getBounds().height+getBounds().y), 4, 12, -1);
//		}
//		else
//			return null;
//	}
//	
//    
//    //draw the image represented by the alien
//	public void render(Graphics2D g) {
//
//		g.drawImage(image,
//                    (int) getBounds().x,
//                    (int) getBounds().y,
//                    (int) getBounds().width,
//                    (int) getBounds().height
//                    ,null);
//
//	}
//
//}