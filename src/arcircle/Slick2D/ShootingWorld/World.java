package arcircle.Slick2D.ShootingWorld;

import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import arcircle.Slick2D.Shooting.Scene.Battle;
import arcircle.Slick2D.Shooting.Scene.KeyInput;
import arcircle.Slick2D.ShootingMain.Score;
import arcircle.Slick2D.ShootingMain.ShootingGame;

public class World {

	ObjectPool objectPool;
	Level level;
	Random random;
	private int count = 0;
	
	//押されている
	static final int SHOT_PRESSED = 1;
	//今押されたばかり
	static final int SHOT_DOWN = 2;
	static final int PLAYER_SHOT_INTERVAL = 6;
	
	static final int LEVEL_UP_FRAME = 500;
	
	Battle battle;
	
	public World(Battle battle) {
		this.battle = battle;
	}

	public void init() {
		objectPool = new ObjectPool();
		level = new Level();
		random = new Random();
	}
	
	public void render(Graphics g) { 
    	g.setColor(Color.white);
    	g.fillRect(0, 0, ShootingGame.WIDTH, ShootingGame.HEIGHT);
		
		//ゲームオブジェクトの一括描画処理
		objectPool.drawAll(g);
		//スコア描画
		ShootingGame.score.drawScore(g);
		ShootingGame.score.drawHiScore(g);
	}
	
	public boolean update(KeyInput keyInput) {
		//衝突の判定
		objectPool.getColision();
		
		//プレイヤーの動作
		objectPool.movePlayer(keyInput);

		//敵出現間隔：レベルに応じて短くなる
		if (count % (100 - Level.getLevel() * 10) == 0)
		{
			ObjectPool.newEnemy(100 + random.nextInt(300), 0);
		}

		//500フレーム経過するとレベルが上昇
		if ((count % LEVEL_UP_FRAME) == 0)
		{
			Level.addLevel();
		}

		//スペースキーが押されており、かつカウンタが３の倍数なら弾を撃つ（等間隔に弾を撃てる）
		if ((keyInput.checkShotKey() == SHOT_PRESSED) && (count % PLAYER_SHOT_INTERVAL == 0))
		{
			objectPool.shotPlayer();
		}
		
		objectPool.moveAll();
		
		if (objectPool.isGameover())
		{
			if (keyInput.checkShotKey() == SHOT_DOWN) {
				battle.changeStateTitle();
				Score.compareScore();
			}
		}
		
		count++;
		
		return true;
	}
	
	public boolean isGameOver() {
		return objectPool.isGameover();
	}
}


