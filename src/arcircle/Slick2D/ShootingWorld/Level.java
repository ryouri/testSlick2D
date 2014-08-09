package arcircle.Slick2D.ShootingWorld;
/**
*	レベル管理<p>
*	レベルは500フレーム毎に１増加し、最大で８まで増加することとした。<br>
*	レベルは敵の生成間隔、敵の弾の生成数に影響する。
*/
public class Level
{
	static int level;
	
	/**
	 * 現在のレベルを返す
	 * @return レベル(0-8)
	 */
	public static int getLevel()
	{
		return level;
	}
	
	/**
	 * レベルを１増やす
	 */
	public static void addLevel()
	{
		//最大レベルは8
		if (level < 8)
		{
			level++;
		}
		System.out.println("level:"+level);//DEBUG
	}

	/**
	 * レベルを０に戻す
	 */
	public static void initLevel()
	{
		level = 0;
	}
}

