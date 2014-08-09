package arcircle.Slick2D.ShootingMain;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.state.StateBasedGame;

import arcircle.Slick2D.JapaneseFont.JapaneseFontGenerater;
import arcircle.Slick2D.Shooting.Scene.Battle;
import arcircle.Slick2D.Shooting.Scene.State;
import arcircle.Slick2D.Shooting.Scene.TitleState;

public class ShootingGame extends StateBasedGame {

	public static final int WIDTH  = 500;
    public static final int HEIGHT = 500;
    public static final int FPS = 60;
    public static final String GAMENAME = "Test Shooting";
    
    public static UnicodeFont font;

	public static Score score;

	public ShootingGame(String name) {
        super(GAMENAME);
        this.addState(new TitleState(State.TITLE));
        this.addState(new Battle(State.BATTLE));
        
	}
    
	@Override
	public void initStatesList(GameContainer container) throws SlickException {
        this.getState(State.TITLE).init(container, this);
        this.getState(State.BATTLE).init(container, this);
        this.enterState(State.TITLE);
        
        //コンストラクタで実行するとエラー発生するよー
        font = JapaneseFontGenerater.generateFont(20, false, false, null);
        //フォント生成後に！
        score = new Score();
	}
}
