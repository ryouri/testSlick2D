package arcircle.Slick2D.ShootingMain;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.UnicodeFont;

/**
*スコアクラス<p>
*得点の制御、表示
*/
public class Score
{
	static int myscore;
	static int hiscore;
	//フォントクラス　あまり気にしない
	UnicodeFont scoreFont;

	/**
	 * コンストラクタ
	 */
	Score()
	{
		scoreFont = ShootingGame.font;
		myscore = 0;
	}

	/**
	 * スコアの描画
	 * @param g 描画先グラフィックハンドル
	 */
	public void drawScore(Graphics g)
	{
		g.setColor(Color.black);
		g.setFont(scoreFont);
		g.drawString("score:"+myscore, 5, 0);
	}

	/**
	 * ハイスコアの描画
	 * @param g 描画先グラフィックハンドル
	 */
	public void drawHiScore(Graphics g)
	{
		g.setColor(Color.black);
		g.setFont(scoreFont);
		g.drawString("hiscore:"+hiscore, 402, 0);
	}

	/**
	 * スコアに追加
	 * @param gain 追加する得点
	 */
	public static void addScore(int gain)
	{
		myscore += gain;
	}

	/**
	 * ハイスコア更新処理<p>
	 * ハイスコアを越えていたら、スコアを外部ファイルに保存する。
	 */
	public static void compareScore()
	{
		//ハイスコアを更新
		if (myscore > hiscore)
		{
			hiscore = myscore;
		}
		myscore = 0;
	}
}

