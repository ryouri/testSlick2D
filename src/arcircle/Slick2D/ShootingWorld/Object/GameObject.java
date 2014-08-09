package arcircle.Slick2D.ShootingWorld.Object;
import org.newdawn.slick.Graphics;

/**
*ゲームオブジェクト抽象クラス<p>
*プレイヤー、弾、敵などのスーパークラス
*Player,Enemy,Bullet,MyBullet,Particleで継承されている
*/
public abstract class GameObject
{
	/**
	 * インスタンス有効フラグ（falseならインスタンスは処理されない）
	*/
    public boolean active;
	/**
	 * 座標のx成分
	*/
	public double x;
	/**
	 * 座標のy成分
	*/
	public double y;

	/**
	 * ステップ毎に実行されるメソッド
	 */
    public abstract void move();

	/**
	 * 描画
	 */
    public abstract void draw(Graphics g);
}

