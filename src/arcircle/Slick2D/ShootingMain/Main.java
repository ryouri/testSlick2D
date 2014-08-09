package arcircle.Slick2D.ShootingMain;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;


public class Main {

	public static void main(String[] args) {

        System.out.println("Hello_My_World");

        //エントリーポイント
        AppGameContainer app;
        try{
            app = new AppGameContainer(new ShootingGame(ShootingGame.GAMENAME));
            app.setDisplayMode(ShootingGame.WIDTH, ShootingGame.HEIGHT, false);
            app.setTargetFrameRate(ShootingGame.FPS);
            app.setShowFPS(false);
            app.start();
        }catch(SlickException e){
            e.printStackTrace();
        }
	}
}
