package arcircle.Slick2D.ShootingWorld;
import org.newdawn.slick.Graphics;

import arcircle.Slick2D.Shooting.Scene.KeyInput;
import arcircle.Slick2D.ShootingWorld.Object.Bullet;
import arcircle.Slick2D.ShootingWorld.Object.Enemy;
import arcircle.Slick2D.ShootingWorld.Object.GameObject;
import arcircle.Slick2D.ShootingWorld.Object.MyBullet;
import arcircle.Slick2D.ShootingWorld.Object.Particle;
import arcircle.Slick2D.ShootingWorld.Object.Player;

/**
*ゲームオブジェクトの管理クラス<p>
*プレイヤーや弾、敵などのインスタンスを持ち<p>
*オブジェクト同士の相互作用（衝突処理など）を一括管理する。
*/
public class ObjectPool
{

	/**
	 * 敵インスタンスをあらかじめ生成し、ためておくための配列。
	 */
	static Bullet[] bullet;
	/**
	 * 敵弾インスタンスをあらかじめ生成し、ためておくための配列。
	 */
	static Enemy[] enemy;
	/**
	 * プレイヤーの弾インスタンスをあらかじめ生成し、ためておくための配列。
	 */
	static MyBullet[] mybullet;
	/**
	 * 爆発インスタンスをあらかじめ生成し、ためておくための配列。
	 */
	static Particle[] particle;

	/**
	 * プレイヤーのインスタンス。
	 */
	Player player;

	//定数
	static final int DIST_PLAYER_TO_BULLET = 8;
	static final int DIST_PLAYER_TO_ENEMY = 16;
	static final int DIST_ENEMY_TO_MYBULLET = 16;

	//最大数の設定
	static final int BULLET_MAX = 100;
	static final int ENEMY_MAX = 100;
	static final int PARTICLE_MAX = 100;
	static final int MYBULLET_MAX = 10;

	//プレイヤー関係の定数
	static final int PLAYER_FIRST_X = 250;
	static final int PLAYER_FIRST_Y = 400;
	static final int PLAYER_SPEED = 4;



	/**
	 * コンストラクタ
	 */
	public ObjectPool()
	{
		//プレイヤーを作る
		//Playerというクラスのインスタンスを生成（引数指定）してクラス型変数のplayerに代入
		player = new Player(PLAYER_FIRST_X, PLAYER_FIRST_Y, PLAYER_SPEED);
		player.active = true;

		//弾の配列を確保し、配列の要素分インスタンスを作る
		bullet = new Bullet[BULLET_MAX];
		for(int i = 0; i < bullet.length; i++)
		{
				bullet[i] = new Bullet();
		}

		//敵の配列を確保し、配列の要素分インスタンスを作る
		//Enemyクラス型、で要素数"ENEMY_MAX"の配列を生成。
		enemy = new Enemy[ENEMY_MAX];
		for(int i = 0; i < enemy.length; i++)
		{
			//インデックスiの配列enemyの要素(とあるEnemyというクラス型の変数)に
			//playerというクラス型の変数を指定して生成、代入
			enemy[i] = new Enemy(player);
		}

		//プレイヤーの弾の配列を確保し、配列の要素分インスタンスを作る
		mybullet = new MyBullet[MYBULLET_MAX];
		for(int i = 0; i < mybullet.length; i++)
		{
				mybullet[i] = new MyBullet();
		}

		//爆発の配列を確保し、配列の要素分インスタンスを作る
		particle = new Particle[PARTICLE_MAX];
		for(int i = 0; i < particle.length; i++)
		{
				particle[i] = new Particle();
		}
	}


	//MyCanbasで呼び出されている。
	//ほぼ全てのオブジェクトを描画している。
	//doGameObjectsに渡している。playerは特殊なため例外。
	/**
	 * 描画処理。すべてのゲームオブジェクトを描画する。
	 * MyCanvasクラスのGameMainメソッドで呼び出される。
	 * またdoGameObjectsから、各オブジェクトのmoveとdrawを呼び出して、描画と移動を行う。
	 */
	public void moveAll()
	{
		moveObjects(bullet);
		moveObjects(enemy);
        moveObjects(mybullet);
        moveObjects(particle);
	}

	/**
	 * ゲームオブジェクトの配列を参照し、
	 * activeになっているインスタンスを移動・描画する
	 * 各インスタンスのmoveとdrawを呼び出している。
	 * moveとdrawは各クラスで実装されている
	 */
    public void moveObjects(GameObject[] objary)
    {
        for (int i = 0; i < objary.length; i++) {
            if (objary[i].active == true)
			{
                objary[i].move();
            }
        }
    }
    
	public void drawAll(Graphics g)
	{
		drawObjects(g, bullet);
		drawObjects(g, enemy);
		drawObjects(g, mybullet);
        drawObjects(g, particle);
		player.draw(g);
	}

	/**
	 * ゲームオブジェクトの配列を参照し、
	 * activeになっているインスタンスを移動・描画する
	 * 各インスタンスのmoveとdrawを呼び出している。
	 * moveとdrawは各クラスで実装されている
	 */
    public void drawObjects(org.newdawn.slick.Graphics g, GameObject[] objary)
    {
        for (int i = 0; i < objary.length; i++) {

            if (objary[i].active == true)
			{
                objary[i].draw(g);
            }
        }
    }
    

	//newBullet()はBulletクラスのFireRound(),FireAim()で呼び出されている。
	//このFireRound(),FireAim()はmove_enemy()で呼び出されている。
    /**
     * 弾の生成・初期化（実際は配列のインスタンスを使い回す）
     * @param ix 生成先x座標
     * @param iy 生成先y座標
     * @param idirection 動かす方向
     * @param ispeed 動かす速度
     * @return 弾のID（空きが無ければ-1）
     */
	public static int newBullet(double ix, double iy, double idirection, double ispeed)
	{
		for (int i = 0; i < BULLET_MAX; i++)
		{
			if ((bullet[i].active) == false)
			{
				bullet[i].activate(ix, iy, idirection, ispeed);
				return i;
			}
		}
		return -1;		//見つからなかった
	}

    /**
     * 敵の生成・初期化（実際は配列のインスタンスを使い回す）
     * MyCanvasのGameMainメソッドで呼び出されている
     * @param ix 生成先x座標
     * @param iy 生成先y座標
     * @return 敵のID（空きが無ければ-1）
     */
	public static int newEnemy(double ix, double iy)
	{
		for (int i = 0; i < ENEMY_MAX; i++)
		{
			if ((enemy[i].active) == false)
			{
				enemy[i].activate(ix, iy);
				return i;
			}
		}
		return -1;		//見つからなかった
	}

	//ObjectPoolのshotPlayerで使われている。
	//さらにMyCanbasでshotPlayerが呼び出されている。
    /**
     * プレイヤー弾の生成・初期化（実際は配列のインスタンスを使い回す）
     * @param ix 生成先x座標
     * @param iy 生成先y座標
	 * @return プレイヤー弾のID（空きが無ければ-1）
     */
	public static int newMyBullets(double ix, double iy)
	{
		for (int i = 0; i < MYBULLET_MAX; i++)
		{
			if ((mybullet[i].active) == false)
			{
				mybullet[i].activate(ix, iy);
				return i;
			}
		}
		return -1;		//見つからなかった
	}

	//各オブジェクトの衝突の際に呼び出されている。
    /**
     * 爆発の生成・初期化（実際は配列のインスタンスを使い回す）
     * @param ix 生成先x座標
     * @param iy 生成先y座標
     * @param idirection 動かす方向
     * @param ispeed 動かす速度
	 * @return 爆発のID（空きが無ければ-1）
     */
	public static int newParticle(double ix, double iy, double idirection, double ispeed)
	{
		for (int i = 0; i < PARTICLE_MAX; i++)
		{
			if ((particle[i].active) == false)
			{
				particle[i].activate(ix, iy, idirection, ispeed);
				return i;
			}
		}
		return -1;		//見つからなかった
	}

	/**
	 * プレイヤーが弾を撃つ
	 * SPACEキーが押されるとMyCanvasクラスから呼び出される
	 */
	public void shotPlayer()
	{
		//プレーヤーが可視の時だけ弾が打てる
		if (player.active)
		{
			player.shot();
			newMyBullets(player.x, player.y);
			newMyBullets(player.x+10, player.y);
			newMyBullets(player.x-10, player.y);
		}
	}

	/**
	 * プレイヤーが移動する処理
	 * MyCamvasのsceneGameメソッドで毎回呼ばれる。
	 * @param keyinput キー入力クラスのインスタンス。
	 */
	public void movePlayer(KeyInput keyinput)
	{
		player.move(keyinput.getXDirection(), keyinput.getYDirection());
	}

	/**
	 * ２点間の距離を返す
	 * @param ga ゲームオブジェクト
	 * @param gb 比較先ゲームオブジェクト
	 * @return 距離
	 */
	public double getDistance(GameObject ga, GameObject gb)
	{
		//三平方の定理
		double Xdiff = Math.abs(ga.x - gb.x);
		double Ydiff = Math.abs(ga.y - gb.y);
		return Math.sqrt(Math.pow(Xdiff,2) + Math.pow(Ydiff,2));
	}

	/**
	 * 衝突判定
	 */
	public void getColision()
	{
		//弾vsプレイヤーの衝突
        for (int i = 0; i < bullet.length; i++) {
			if ((bullet[i].active)&&(player.active))
			{
				//あたり判定
				if (getDistance(player, bullet[i]) < DIST_PLAYER_TO_BULLET)
				{
					//プレイヤー消滅（見えなくするだけ）
					player.deactive();

					//爆発を生成
					for (int j = 0; j < 360; j += 20)
					{
						newParticle(player.x, player.y, j, 2);
					}

					//弾消滅
					bullet[i].active = false;
				}
			}
        }

		//プレイヤーの弾vs敵の衝突
        for (int i = 0; i < enemy.length; i++) {
			if (enemy[i].active == true)
			{
				for (int j = 0; j < mybullet.length; j++)
				{
					if (mybullet[j].active == true)
					{
						//あたり判定
						if (getDistance(enemy[i], mybullet[j]) < DIST_ENEMY_TO_MYBULLET)
						{
							newParticle(mybullet[j].x, mybullet[j].y, 270, 2);
							//敵の体力を減らす
							enemy[i].hit();
							//弾消滅
							mybullet[j].active = false;
						}
					}
				}
			}
        }

		//敵vsプレイヤーの衝突
        for (int i = 0; i < enemy.length; i++) {
			if ((enemy[i].active)&&(player.active))
			{
				//あたり判定
				if (getDistance(player, enemy[i]) < DIST_PLAYER_TO_ENEMY)
				{
					//プレイヤー消滅（見えなくするだけ）
					player.deactive();

					//爆発を生成
					for (int j = 0; j < 360; j += 20)
					{
						newParticle(player.x, player.y, j, 2);
					}
					//敵は消滅しない
				}
			}
        }

	}

	/**
	 * ゲームオーバーかどうかを返す
	 * @return ゲームオーバーならtrue
	 */
	public boolean isGameover()
	{
		return !player.active;
	}



}
