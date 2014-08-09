package arcircle.Slick2D.ShootingWorld.Object;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
*プレイヤーの弾クラス<p>
*移動処理、描画処理など
*/
public class MyBullet extends GameObject
{
	/**
	 * コンストラクタ
	 */
	public MyBullet()
	{
		active = false;
	}
	
	/**
	 * ステップ毎処理
	 */
	public void move()
	{
        y -= 15;
		//画面の外に出たら消去
		if ( (y < 0) )
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
		g.drawRect((int)x-3, (int)y-10, (int)6, (int)20);
	}
	
	//初期化処理もここで行う（オブジェクトを使い回しているので）
	public void activate(double ix, double iy)
	{
		x = ix;
		y = iy;
		active = true;			//弾のインスタンスを有効にする
	}
}

