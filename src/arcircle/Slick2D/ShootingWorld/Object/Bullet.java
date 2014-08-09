package arcircle.Slick2D.ShootingWorld.Object;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import arcircle.Slick2D.ShootingWorld.ObjectPool;

/**
* 敵の弾クラス<p>
* 移動処理、描画処理など
*/
public class Bullet extends GameObject
{
	double direction;
	double speed;
	double speedX;
	double speedY;

	/**
	 * コンストラクタ
	 */
	public Bullet()
	{
		//初期化時はactiveでない
		active = false;
	}

	//ObjectPool の doGameObject で移動する際に使われている drawと同タイミング
	/**
	 * 動作を規定する。メインループ１周につき一回呼ばれる
	 */
	public void move()
	{
		//スピード分動いていく
		x += speedX;
		y += speedY;

		//画面外に行ったらインスタンスを無効化する
		if ( (x < 0)||(500 < x)||(y < 0)||(500 < y) )
		{
			active = false;
		}
	}

	//ObjectPool の doGameObject で描画する際に使われている moveと同タイミング
	/**
	 * 描画処理。
	 * １ループで一回呼ばれる。
	 * @param g 描画先グラフィックハンドル
	 */
	public void draw(Graphics g)
	{
		g.setColor(Color.blue);
		g.drawRect((int)(x-3), (int)(y-3), (int)6, (int)6);
	}

	//インスタンスを作成したとき、インスタンスは有効化されていない
	//これは、new○○メソッド(例：Bullet)で使われていないオブジェクトを探して、インスタンスを有効にする。
	/**
	 * インスタンスを有効にする。インスタンスの使い回しをしているので、
	 * 初期化処理もここで行う。
	 * @param ix 生成する位置(X座標)
	 * @param iy 生成する位置(Y座標)
	 * @param idirection 向き(単位は度　0-360)
	 * @param ispeed 速度(単位はピクセル)
	 */
	public void activate(double ix, double iy, double idirection, double ispeed)
	{
		x = ix;
		y = iy;
		direction = idirection;
		speed = ispeed;
		//弾のインスタンスを有効にする
		active = true;

		//高速化のため、極座標からXY速度に変換しておく。
		double radian;
		radian = Math.toRadians(direction);	//度をラジアンに変換
		speedX = speed * Math.cos(radian);
		speedY = speed * Math.sin(radian);
	}


	//staticメソッド：staticメソッドはクラス単位のメソッドである
	//用いる際は、Bullet.FireRound(x,y)のように用いる
	//このメソッドをEnemyで用いることで敵の弾を表現している

	/**
	 * 全方位に弾を撃つ
	 */
	public static void FireRound(double x, double y)
	{
		for (int i = 0; i < 360; i += 60 )
		{
			ObjectPool.newBullet(x, y, i, 3);
		}
	}

	//プレイヤーの位置に向けて3way弾を撃つ
	public static void FireAim(double x, double y, Player player)
	{
		double degree = Math.toDegrees(Math.atan2(player.y - y, player.x - x));
		ObjectPool.newBullet(x, y, degree, 4);
		ObjectPool.newBullet(x, y, degree+20, 4);
		ObjectPool.newBullet(x, y, degree-20, 4);
	}
}

