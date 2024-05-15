package entities;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.Playing;
import levels.Level;
import utilz.LoadSave;
import static utilz.Constants.EnemyConstants.*;

public class EnemyManager {

	private Playing playing;
	private BufferedImage[][] piggyArr;
	private ArrayList<Piggy> piggies = new ArrayList<>();

	public EnemyManager(Playing playing) {
		this.playing = playing;
		loadEnemyImgs();
	}

	public void loadEnemies(Level level) {
		piggies = level.getPigs();
	}

	public void update(int[][] lvlData, Player player) {
		boolean isAnyActive = false;
		for (Piggy c : piggies)
			if (c.isActive()) {
				c.update(lvlData, player);
				isAnyActive = true;
			}
		if (!isAnyActive)
			playing.setLevelCompleted(true);
	}

	public void draw(Graphics g, int xLvlOffset) {
		drawPigs(g, xLvlOffset);
	}

	private void drawPigs(Graphics g, int xLvlOffset) {
		for (Piggy c : piggies)
			if (c.isActive()) {

				g.drawImage(piggyArr[c.getState()][c.getAniIndex()], (int) c.getHitbox().x - xLvlOffset - PIGGY_DRAWOFFSET_X + c.flipX(), (int) c.getHitbox().y - PIGGY_DRAWOFFSET_Y,
						PIGGY_WIDTH * c.flipW(), PIGGY_HEIGHT, null);

//				c.drawHitbox(g, xLvlOffset);
//				c.drawAttackBox(g, xLvlOffset);
			}

	}

	public void checkEnemyHit(Rectangle2D.Float attackBox) {
		for (Piggy c : piggies)
			if (c.isActive())
				if (attackBox.intersects(c.getHitbox())) {
					c.hurt(10);
					return;
				}
	}

	private void loadEnemyImgs() {
		piggyArr = new BufferedImage[5][11];
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.PIGGY_SPRITE);
		for (int j = 0; j < piggyArr.length; j++)
			for (int i = 0; i < piggyArr[j].length; i++)
				piggyArr[j][i] = temp.getSubimage(i * PIGGY_WIDTH_DEFAULT, j * PIGGY_HEIGHT_DEFAULT, PIGGY_WIDTH_DEFAULT, PIGGY_HEIGHT_DEFAULT);
	}

	public void resetAllEnemies() {
		for (Piggy c : piggies)
			c.resetEnemy();
	}

}
