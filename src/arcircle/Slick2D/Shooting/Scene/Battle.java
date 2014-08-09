package arcircle.Slick2D.Shooting.Scene;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import arcircle.Slick2D.ShootingMain.ShootingGame;
import arcircle.Slick2D.ShootingWorld.World;

public class Battle extends BasicGameState {

	private int state;
	UnicodeFont font;

	World world;
	KeyInput keyInput;
	
	StateBasedGame stateGame;
	
	//TODO: Musicクラスの利用
	Music music;
	
	public Battle(int state) {
		this.state = state;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		font = ShootingGame.font;
		stateGame = sbg;

		//TODO: Musicクラスの利用
		try {
			music = new Music("music/kirby.ogg");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		world.render(g);
		if(world.isGameOver()) {
	    	g.setColor(Color.black);
	    	g.setFont(font);
	    	int gameOverWidth = font.getWidth("G A M E  O V E R");
	    	g.drawString("G A M E  O V E R", (ShootingGame.WIDTH - gameOverWidth) / 2, 150);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		world.update(keyInput);
		keyInput.spaceKeyUpdate();
	}
	
	public void changeStateTitle() {
		stateGame.enterState(State.TITLE, new FadeOutTransition(Color.black,
				500), new FadeInTransition(Color.black, 500));
	}
	
	@Override
	public void keyPressed(int key, char c) {
		super.keyPressed(key, c);
		keyInput.keyPressed(key, c);
	}
	
	
	
	@Override
	public void keyReleased(int key, char c) {
		super.keyReleased(key, c);
		keyInput.keyReleased(key, c);
	}

	@Override
	public int getID() {
		return this.state;
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.enter(container, game);
		keyInput = new KeyInput();
		world = new World(this);
		world.init();

		//TODO: Musicクラスの利用
		music.setPosition(0);
		music.play();
		music.loop();

		//ゲーム中に動的に曲を変えたい場合
		//このBasicGameStateクラスが持つクラス
		//今回で言えば，Battleクラスが持つWorldクラスに，
		//Battleクラスの参照を渡しておき，
		//MusicChangeメソッドなどを作成し，呼び出させるという方法がある．
		
		//他にも，ShootingGameクラスなどのStateBasedGameクラスに音楽管理クラスを
		//持たせ，そこで管理するという方法もある．
	}

	@Override
	public void leave(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.leave(container, game);
		world = null;

		//TODO: Musicクラスの利用
		music.stop();
	}
}
