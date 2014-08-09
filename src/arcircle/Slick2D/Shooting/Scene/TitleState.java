package arcircle.Slick2D.Shooting.Scene;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.SelectTransition;

import arcircle.Slick2D.ShootingMain.ShootingGame;

public class TitleState extends BasicGameState {

	private int state;
	private UnicodeFont font;
	private int count = 0;

	StateBasedGame stateGame;

	public TitleState(int state) {
		this.state = state;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		stateGame = sbg;
		font = ShootingGame.font;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		g.setColor(Color.white);
		g.fillRect(0, 0, ShootingGame.WIDTH, ShootingGame.HEIGHT);

		g.setColor(Color.black);
		g.setFont(font);
		int titleWidth = font.getWidth("S h o o t i n g");
		g.drawString("S h o o t i n g", (ShootingGame.WIDTH - titleWidth) / 2,
				150);

		// 点滅させる
		if (count % 10 >= 1) {
			g.setFont(font);
			int hitWidth = font.getWidth("hit SPACE key");
			g.drawString("hit SPACE key", (ShootingGame.WIDTH - hitWidth) / 2,
					350);
		}

		ShootingGame.score.drawScore(g);
		ShootingGame.score.drawHiScore(g);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		if (gc.getInput().isKeyDown(Input.KEY_SPACE)) {
			sbg.enterState(State.BATTLE, new EmptyTransition(),
					new SelectTransition());
		}
		count++;
	}

	@Override
	public int getID() {
		return this.state;
	}
}
