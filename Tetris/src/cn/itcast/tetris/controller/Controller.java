package cn.itcast.tetris.controller;

import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JLabel;

import cn.itcast.tetris.entities.Ground;
import cn.itcast.tetris.entities.Shape;
import cn.itcast.tetris.entities.ShapeFactory;
import cn.itcast.tetris.listener.GameListener;
import cn.itcast.tetris.listener.GroundListener;
import cn.itcast.tetris.listener.ShapeListener;
import cn.itcast.tetris.util.Global;
import cn.itcast.tetris.view.GamePanel;


/**
 * 控制器<BR>
 * 控制Ground, Snake, Food<BR>
 * 负责游戏的逻辑<BR>
 * 处理按键事件<BR>
 * <BR>
 * 
 * @version 1.0, 01/01/08
 * 
 * @author 汤阳光
 * 
 */
public class Controller extends KeyAdapter implements ShapeListener,
		GroundListener {

	protected Set<GameListener> listeners = new HashSet<GameListener>();
	
	/**
	 * 图形工厂
	 */
	protected ShapeFactory shapeFactory;

	protected Shape shape;

	protected Ground ground;

	protected GamePanel gamePanel;

	protected JLabel gameInfoLabel;

	/**
	 * 当前的游戏状态
	 */
	protected boolean playing;

	/**
	 * 
	 * @param shapeFactory
	 * @param ground
	 * @param gamePanel
	 */
	public Controller(ShapeFactory shapeFactory, Ground ground,
			GamePanel gamePanel) {
		super();
		this.shapeFactory = shapeFactory;
		this.ground = ground;
		this.gamePanel = gamePanel;
	}

	/**
	 * 多接受一个 JTextComponent, 可以给在这个组件上显示提示信息
	 * 
	 * @param shapeFactory
	 * @param ground
	 * @param gamePanel
	 * @param gameInfoLabel
	 */
	public Controller(ShapeFactory shapeFactory, Ground ground,
			GamePanel gamePanel, JLabel gameInfoLabel) {

		this(shapeFactory, ground, gamePanel);
		this.setGameInfoLabel(gameInfoLabel);
	}

	/**
	 * 处理键盘按键 <BR>
	 * LEFT: 向左移动<BR>
	 * RIGHT：向右移动<BR>
	 * DOWN: 向下移动<BR>
	 * UP: 变形<BR>
	 * PAGE UP: 加快速度<BR>
	 * PAGE DOWN: 减慢速度<BR>
	 * Y: 重新开始游戏<BR>
	 * ENTER: 暂停/继续
	 */
	@Override
	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() != KeyEvent.VK_Y && !playing)
			return;

		switch (e.getKeyCode()) {
		/**
		 * 方向左
		 */
		case KeyEvent.VK_LEFT:
			if (isPausingGame()) {
				this.continueGame();
			}
			shape.setSwift(false);

			if (isPlaying() && ground.isMoveable(shape, Shape.LEFT))
				shape.moveLeft();
			break;
		/**
		 * 方向右
		 */
		case KeyEvent.VK_RIGHT:
			if (isPausingGame()) {
				this.continueGame();
			}
			shape.setSwift(false);

			if (isPlaying() && ground.isMoveable(shape, Shape.RIGHT))
				shape.moveRight();
			break;
		/**
		 * 方向上
		 */
		case KeyEvent.VK_UP:

			if (isPlaying()) {
				if (!shape.isPause()) {
					if (ground.isMoveable(shape, Shape.ROTATE)) {
						shape.setSwift(false);
						shape.rotate();
					}
				} else {
					if (ground.isMoveable(shape, Shape.UP))
						shape.moveUp();
					else {
						shape.die();
						shape = shapeFactory.getShape(this);
					}
				}
			}

			break;
		/**
		 * 方向下
		 */
		case KeyEvent.VK_DOWN:
			if (isPausingGame()) {
				this.continueGame();
			}
			if (isPlaying() && isShapeMoveDownable(shape))
				shape.moveDown();
			break;
		/**
		 * PAGE UP
		 */
		case KeyEvent.VK_PAGE_UP:
			shape.speedUp();
			break;
		/**
		 * PAGE DOWN
		 */
		case KeyEvent.VK_PAGE_DOWN:
			shape.speedDown();
			break;
		/**
		 * 反引号,换一个图形
		 */
		case KeyEvent.VK_BACK_QUOTE:
			if (isPlaying()) {
				shape.die();
				shape = shapeFactory.getShape(this);
			}
			break;
		case KeyEvent.VK_ENTER:
			if (isPausingGame())
				this.continueGame();
			else
				this.pauseGame();
			break;
		case KeyEvent.VK_Y:
			if (!isPlaying())
				newGame();
			break;
		case KeyEvent.VK_SPACE:

			if (isPlaying() && !isPausingGame())
				shape.setSwift(true);
			break;
		}
		/**
		 * 重新显示
		 */
		gamePanel.redisplay(ground, shape);
		if (gameInfoLabel != null)
			gameInfoLabel.setText(this.getNewInfo());
	}

	/**
	 * 询问一下图形是否可以下落，如果不能下落了，就会让图形变成障碍物<BR>
	 * 这个方法是同步的
	 */
	public synchronized boolean isShapeMoveDownable(Shape s) {

		if (shape == null)
			return true;
		if (!playing || shape != s)
			return false;

		if (ground.isMoveable(shape, Shape.DOWN))
			return true;

		shape.die();
		ground.accept(shape);
		if (playing && !ground.isFull()) {
			shape = shapeFactory.getShape(this);
		}
		gamePanel.redisplay(ground, shape);
		if (gameInfoLabel != null)
			gameInfoLabel.setText(this.getNewInfo());

		return false;
	}

	/**
	 * 处理图形触发的 shapeMovedDown (图形下落) 事件<BR>
	 * 将会重新显示
	 */
	public void shapeMovedDown(Shape s) {
		if (playing && ground != null && shape != null)
			gamePanel.redisplay(ground, shape);
	}

	/**
	 * 开始一个新游戏
	 */
	public void newGame() {
		playing = true;
		ground.init();
		ground.addGroundListener(this);

		Global.CURRENT_SPEED = Global.DEFAULT_SPEED;
		shape = shapeFactory.getShape(this);

		if (playing)
			gamePanel.redisplay(ground, shape);

		if (gameInfoLabel != null)
			gameInfoLabel.setText(this.getNewInfo());

		for (GameListener l : listeners)
			l.gameStart();
	}

	/**
	 * 停止当前游戏
	 */
	public void stopGame() {
		if (shape == null)
			return;
		playing = false;
		for (GameListener l : listeners)
			l.gameOver();
	}

	/**
	 * 暂停游戏
	 */
	public void pauseGame() {
		if (shape == null)
			return;
		shape.setPause(true);
		for (GameListener l : listeners)
			l.gamePause();
	}

	/**
	 * 继续游戏
	 */
	public void continueGame() {
		shape.setPause(false);
		for (GameListener l : listeners)
			l.gameContinue();
	}

	/**
	 * 游戏是否是在暂停状态
	 */
	public boolean isPausingGame() {
		return shape.isPause();
	}

	/**
	 * 获得游戏的最新提示信息
	 * 
	 * @return
	 */
	public String getNewInfo() {
		if (!playing || ground.isFull())
			return " ";// "提示: 按 Y 开始新游戏";
		else
			return new StringBuffer().append("提示: ").append(" 速度 ").append(
					shape.getSpeed()).append("毫秒/格").toString();
	}

	public ShapeFactory getShapeFactory() {
		return shapeFactory;
	}

	public void setShapeFactory(ShapeFactory shapeFactory) {
		this.shapeFactory = shapeFactory;
	}

	public Ground getGround() {
		return ground;
	}

	public void setGround(Ground ground) {
		this.ground = ground;
	}

	public GamePanel getGamePanel() {
		return gamePanel;
	}

	public void setGamePanel(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	/**
	 * 处理Ground 触发的 beforeDeleteFullLine 事件将会改变满行的颜色并暂停一段时间
	 */
	public void beforeDeleteFullLine(Ground ground, int lineNum) {
		ground.changeFullLineColor(lineNum);
		gamePanel.redisplay(ground, shape);
		try {
			Thread.sleep(Global.STAY_TIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 处理Ground 触发的 fullLineDeleted 事件, 这个方法什么也没做, 只是打印了一句话
	 */
	public void fullLineDeleted(Ground ground, int deletedLineCount) {
		System.out.println("消了 " + deletedLineCount + " 行");
	}

	/**
	 * 是否正在游戏中
	 * 
	 * @return
	 */
	public boolean isPlaying() {
		if (playing && !ground.isFull())
			return true;
		return false;
	}

	/**
	 * 得到显示提示信息的组件
	 * 
	 * @return
	 */
	public JLabel getGameInfoLabel() {
		return gameInfoLabel;
	}

	/**
	 * 设置
	 * 
	 * @param gameInfoLabel
	 */
	public void setGameInfoLabel(JLabel gameInfoLabel) {
		this.gameInfoLabel = gameInfoLabel;
		this.gameInfoLabel.setSize(Global.WIDTH * Global.CELL_WIDTH, 20);
		this.gameInfoLabel.setFont(new Font("宋体", Font.PLAIN, 12));
		gameInfoLabel.setText(this.getNewInfo());
	}

	/**
	 * 处理Ground 的 groundIsFull() 事件, 将触发游戏结束事件
	 */
	public void groundIsFull(Ground ground) {
		if (playing) {
			playing = false;
			for (GameListener l : listeners)
				l.gameOver();
		}
	}

	/**
	 * 添加监听器, 可添加多个
	 * 
	 * @param l
	 */
	public void addGameListener(GameListener l) {
		if (l != null)
			this.listeners.add(l);
	}

	/**
	 * 移除监听器
	 * 
	 * @param l
	 */
	public void removeGameListener(GameListener l) {
		if (l != null)
			this.listeners.remove(l);
	}

}
