package arcircle.Slick2D.Shooting.Scene;
import org.newdawn.slick.Input;

/**
*キーボードの入力を管理するクラス<p>
*スペースキーで撃つ<p>
*カーソルキーで移動　と定義されている
*/
public class KeyInput
{
	//キーボード入力の状態を保持するフィールド
	boolean keyup;
	boolean keydown;
	boolean keyleft;
	boolean keyright;
	
	/**
	 * 押された瞬間を判別するため、0-2の値をとる
	 * 0:押されていない 1:押されている 2:ついさっき押されたばかり
	 */
	int keyshot;

	public KeyInput() {
		keyup = false;
		keydown = false;
		keyleft = false;
		keyright = false;
		keyshot = 0;
	}

	/**
	 * キーが押されたときに呼ばれる処理。
	 * 変数にキー状態を保存する。
	 */
	public void keyPressed(int key, char c) {
		if (key == Input.KEY_LEFT)
		{
			keyleft = true;
		}
		if (key == Input.KEY_RIGHT)
		{
			keyright = true;
		}
		if (key == Input.KEY_UP)
		{
			keyup = true;
		}
		if (key == Input.KEY_DOWN)
		{
			keydown = true;
		}
		if (key == Input.KEY_SPACE)
		{
			System.out.print("p");
			//初めて押された
			if (keyshot == 0)
			{
				//押された瞬間を表すフラグ
				keyshot = 2;
			}
			else
			{
				//押されている状態
				keyshot = 1;
			}
		}

		if (key == Input.KEY_ESCAPE)
		{
			System.exit(0);
		}
	}
	
	/**
	 * 押されていたキーを放したときに呼ばれる処理
	 */
	public void keyReleased(int key, char c) {
		if (key == Input.KEY_LEFT)
		{
			keyleft = false;
		}
		if (key == Input.KEY_RIGHT)
		{
			keyright = false;
		}
		if (key == Input.KEY_UP)
		{
			keyup = false;
		}
		if (key == Input.KEY_DOWN)
		{
			keydown = false;
		}
		if (key == Input.KEY_SPACE)
		{
			keyshot = 0;
			System.out.print("r");
		}
	}
	
	public void spaceKeyUpdate() {
		if(keyshot == 2) {
			keyshot = 1;
		}
	}
	
	/**
	 * x軸の入力を取得
	 * @return -1:右　0:なし　1:左
	 */
	public int getXDirection()
	{
		int ret = 0;	//静止状態
		if (keyright)
		{
			ret = 1;
		}
		if (keyleft)
		{
			ret = -1;
		}
		return ret;
	}
	
	/**
	 * y軸の入力を取得
	 * @return -1:上　0:なし　1:下
	 */
	public int getYDirection()
	{
		int ret = 0;	//静止状態
		if (keydown)
		{
			ret = 1;
		}
		if (keyup)
		{
			ret = -1;
		}
		return ret;
	}
	
	/**
	 * ショットボタン（＝スペースキー）の状態を取得する
	 * @return 0:押されていない 1:押されている 2:ついさっき押されたばかり
	 */
	public int checkShotKey()
	{
		return keyshot;
	}


}
