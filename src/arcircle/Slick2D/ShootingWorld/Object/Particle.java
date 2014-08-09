package arcircle.Slick2D.ShootingWorld.Object;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
/**
*パーティクルクラス<p>
*（爆発や、敵に弾が当たったときの効果）<p>
*移動処理、描画処理など
*/
public class Particle extends GameObject
{
	//弾の進行方向の角度（極座標）
	double direction;
	//弾のdirection方向への進む速さ(極座標)
	double speed;
	//弾のx方向への進む速度
	double speedX;
	//弾のy方向への進む速度
	double speedY;
	//爆発の大きさ
	int size;

	public Particle()
	{
		active = false;
	}

	/**
	 * 動作を規定する。メインループ１周につき一回呼ばれる
	 */
	public void move()
	{
		x += speedX;
		y += speedY;
		size--;

		if ( (x < 0)||(500 < x)||(y < 0)||(500 < y) )
		{
			active = false;
		}
		if (size < 0)
		{
			active = false;
		}
	}

	/**
	 * 描画処理。
	 * １ループで一回呼ばれる。
	 * @param g 描画先グラフィックハンドル
	 */
	public void draw(Graphics g)
	{
		g.setColor(Color.gray);
		g.drawOval((int)(x-size/2), (int)(y-size/2), size, size);
	}

	//インスタンスを作成したときは有効ではない
	//そのとき出したい場所を指定して値を渡して、代入する
	//初期化処理もここで行う（オブジェクトを使い回しているので）
	public void activate(double ix, double iy, double idirection, double ispeed)
	{
		x = ix;
		y = iy;
		direction = idirection;
		speed = ispeed;
		active = true;			//弾のインスタンスを有効にする
		size = 30;

		//高速化のため、極座標からXY速度に変換しておく。
		double radian;
		radian = Math.toRadians(direction);	//度をラジアンに変換
		speedX = speed * Math.cos(radian);
		speedY = speed * Math.sin(radian);
	}
}

