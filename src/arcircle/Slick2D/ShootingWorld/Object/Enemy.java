package arcircle.Slick2D.ShootingWorld.Object;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import arcircle.Slick2D.ShootingMain.Score;
import arcircle.Slick2D.ShootingWorld.Level;
import arcircle.Slick2D.ShootingWorld.ObjectPool;

/**
*敵クラス<p>
*移動処理、描画処理など
*/
public class Enemy extends GameObject
{
	//生存時間（弾を撃つタイミングに使用）
	int counter = 0;
	//体力
	int hitPoint;
	//敵の種類
	int type;
	//あたり判定フラグ
	boolean isHit = false;
	//プレイヤーの位置を知っておくため、Playerインスタンスへの参照をを保持
	Player player;
	//打ち始めフラグ
	boolean startShoot;
	//撃つ量
	int shootNum;

	/**
	 * コンストラクタ
	 * @param プレイヤークラスのインスタンス（プレイヤーの位置を把握するため）
	 */
	public Enemy(Player iplayer)
	{
		//プレイヤーの位置を把握
		player = iplayer;
		//初期化時はactiveでない
		active = false;
	}

	/**
	 * ObjectPoolクラスのNewEnemyメソッドで呼び出されている。
	 * NewEnemyメソッドはMyCanvasクラスのGameMainメソッドで呼び出されている。
	 * (100 + random.nextInt(300), 0)という値を渡しているため、
	 * 敵は画面の一番上で、x軸は画面内をランダムに出現。
	 */
	/**
	 * インスタンスを有効にする。インスタンスの使い回しをしているので、
	 * 初期化処理もここで行う。
	 * @param ix 生成する位置(X座標)
	 * @param iy 生成する位置(Y座標)
	 */
	public void activate(double ix, double iy)
	{
		x = ix;
		y = iy;
		//ランダムに出現する敵を決める
		type = (int)(2*Math.random()+0.5);
		//弾のインスタンスを有効にする
		active = true;
		//体力
		hitPoint = 3;
		//生存時間を0に
		counter = 0;
		//弾には当たってない
		isHit = false;
		//レベルに応じて出す弾を買える
		shootNum = Level.getLevel();

		startShoot = false;
	}

	/**
	 * プレイヤーの弾と衝突したときの振る舞い
	 */
	public void hit()
	{
		//体力減らす
		hitPoint--;
		isHit = true;
		if (hitPoint < 0)
		{
			//得られる得点は敵の種類で変わる
			switch(type)
			{
				case 0:
					//スコアに加算
					Score.addScore(10);
					break;
				case 1:
					//スコアに加算
					Score.addScore(20);
					break;
				default:
			}

			//爆発の生成
			ObjectPool.newParticle(x, y, 45, 2);
			ObjectPool.newParticle(x, y, 135, 2);
			ObjectPool.newParticle(x, y, 225, 2);
			ObjectPool.newParticle(x, y, 315, 2);
			active = false;
		}
	}

	/**
	 * 動作を規定する。メインループ１周につき一回呼ばれる
	 */
	public void move()
	{
		//種類で分岐
		switch(type)
		{
			case 0:
				move_enemy0();
				break;
			case 1:
				move_enemy1();
				break;
			case 2:
				move_enemy2();
				break;
			default:
		}
	}

	/**
	 * 敵その１の動作
	 */
	void move_enemy0()
	{
		counter++;
		y++;
		//ゆらゆら
		x += Math.sin(y / 20);

		//画面外に出たら消去
		if ( (500 < y) )
		{
			active = false;
		}

		//一定間隔で弾を撃つ
		if ((counter%80)==0)
		{
			Bullet.FireRound(x, y);
		}
	}

	/**
	 * 敵その２の動作
	 */
	void move_enemy1()
	{
		counter++;
		double p = 60;	//静止までの時間
		double q = 200;	//画面のどこで静止するか
		//二次関数で動きを表現
		y = (-q / Math.pow(p,2) * Math.pow((counter - p), 2) + q);

		//画面外に出たら消去
		if ( (-30 > y) )
		{
			active = false;
		}

		//一定間隔で弾を撃つ
		if ((counter%60)==0)
		{
			//撃ち始め
			startShoot = true;
		}

		//撃ち始めフラグが立っていたら、レベルと等しい回数、弾を撃つ
		if (startShoot)
		{
			if (((counter%5)==0)&&(shootNum>0))
			{
				Bullet.FireAim(x, y, player);
				shootNum--;
			}
		}
	}
	
	void move_enemy2()
	{
		counter++;
		y++;
		//ゆらゆら
		x += Math.sin(y / 40);

		//画面外に出たら消去
		if ( (500 < y) )
		{
			active = false;
		}

		//一定間隔で弾を撃つ
		if ((counter%20)==0)
		{
			Bullet.FireMissile(x, y);
		}
	}


	/**
	 * 描画処理。
	 * １ループで一回呼ばれる。
	 * @param g 描画先グラフィックハンドル
	 */
	public void draw(Graphics g)
	{
		//弾が当たっていたら色をオレンジに
		if (isHit)
		{
			g.setColor(Color.orange);
		}
		else
		{
			switch(type)
			{
				case 0:
					g.setColor(Color.black);
					break;
				case 1:
					g.setColor(Color.blue);
					break;
				default:
			}
		}
		isHit = false;
		g.drawRect((int)x-16, (int)y-16, (int)32, (int)32);
	}
}


/*
 * 敵のグラフィックを描画してる関数
 *
 drawRect
public void drawRect(int x,
                     int y,
                     int width,
                     int height)
                     指定された矩形の輪郭を描きます。矩形は、左端と右端がそれぞれ x と x + width、
                     上端と下端がそれぞれ y と y + height で指定されます。
                     矩形は、グラフィックスコンテキストの現在の色を使って描画されます。
パラメータ:
x - 描画される矩形の x 座標
y - 描画される矩形の y 座標
width - 描画される矩形の幅
height - 描画される矩形の高さ
*/
