package cn.itcast.tetris.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import cn.itcast.tetris.listener.GroundListener;
import cn.itcast.tetris.util.Global;


/**
 * 可以叫做地形, 或地面<BR>
 * 维护障碍物的信息<BR>
 * 可以使用提供的 addObstacle(int, int) 和 addStubbornObstacle(int ,int) 方法添加障碍物。<BR>
 * <BR>
 * 可以通过setObstacleColor(), setStubbornObstacleColor() 或
 * setGriddingColor()方法更改障碍物或网格的颜色<BR>
 * 通过setDrawGridding() 方法设置是否画空白(网格)<BR>
 * 用 setColorfulSupport() 方法设置是否支持彩色显示<BR>
 * <BR>
 * 覆盖 drawObstacle(Graphics, int, int, int, int) 方法可以改变障碍物的显示方式<BR>
 * 覆盖 drawStubbornObstacle(Graphics, int, int, int, int) 方法可以改变不可消除的障碍物的显示方式<BR>
 * <BR>
 * 
 * @version 1.0, 01/01/08
 * 
 * @author 汤阳光
 * 
 */
public class Ground {

	/**
	 * 监听器
	 */
	protected Set<GroundListener> listeners = new HashSet<GroundListener>();

	/**
	 * 容器
	 */
	protected UnitType[][] obstacles = new UnitType[Global.WIDTH][Global.HEIGHT];

	/**
	 * 不可消除的障碍物的颜色
	 */
	protected Color stubbornObstacleColor = UnitType.STUBBORN_OBSTACLE
			.getColor();

	/**
	 * 默认的网格颜色
	 */
	public static final Color DEFAULT_GRIDDING_COLOR = Color.LIGHT_GRAY;

	/**
	 * 网格的颜色
	 */
	protected Color griddingColor = DEFAULT_GRIDDING_COLOR;

	public static final Color DEFAULT_OBSTACLE_COLOR = UnitType.OBSTACLE
			.getColor();
	/**
	 * 障碍物的颜色
	 */
	protected Color obstacleColor = DEFAULT_OBSTACLE_COLOR;

	public static final Color DEFAULT_FULL_LINE_COLOR = Color.DARK_GRAY;
	/**
	 * 满行的颜色
	 */
	protected Color fullLineColor = DEFAULT_FULL_LINE_COLOR;

	/**
	 * 是否画网格 的开关
	 */
	protected boolean drawGridding;

	/**
	 * 是否支持彩色石头
	 */
	protected boolean colorfulSupport;

	/**
	 * 是否还能接受石头
	 */
	protected boolean full;

	protected Random random = new Random();

	public Ground() {
		init();
	}

	/**
	 * 初始化，将会调用clear() 方法<BR>
	 * 
	 */
	public void init() {
		clear();
		full = false;
	}

	/**
	 * 清空容器
	 */
	public void clear() {
		/**
		 * 初始化数组
		 */
		for (int x = 0; x < Global.WIDTH; x++)
			for (int y = 0; y < Global.HEIGHT; y++)
				obstacles[x][y] = UnitType.BLANK.clone();
	}

	/**
	 * 随机生成一个不可消除的障碍物， 这个随机的坐标的y 坐标不小于5
	 */
	public void genernateAStubbornStochasticObstacle() {
		Random random = new Random();
		if (Global.HEIGHT < 5)
			return;
		int y = random.nextInt(5) + Global.HEIGHT - 5;
		int x = random.nextInt(Global.WIDTH);
		addStubbornObstacle(x, y);
	}

	/**
	 * 在指定的范围内随机生成一些障碍物<BR>
	 * 产生的区域行是1 - lineNum
	 * 
	 * @param amount
	 *            要生成的数量
	 * @param lineNum
	 *            行号， 从1开始
	 */
	public void generateSomeStochasticObstacle(int amount, int lineNum) {
		if (lineNum < 1)
			return;
		if (lineNum > Global.HEIGHT)
			lineNum = Global.HEIGHT;
		for (int i = 0; i < amount; i++) {
			int x = random.nextInt(Global.WIDTH);
			int y = random.nextInt(lineNum) + Global.HEIGHT - lineNum;
			obstacles[x][y] = UnitType.OBSTACLE.clone();
			obstacles[x][y].setColor(Global.getRandomColor());
		}
	}

	/**
	 * 把指定的图形变成石头<BR>
	 * 然后将会调用 deleteFullLine() 方法扫描并删除满行
	 * 
	 * @param shape
	 */
	public void accept(Shape shape) {

		/**
		 * 把图形对应的坐标变成石头
		 */
		int left = shape.getLeft();
		int top = shape.getTop();

		for (int x = 0; x < 4; x++)
			for (int y = 0; y < 4; y++)
				if (left + x < Global.WIDTH && top + y < Global.HEIGHT) {
					if (shape.isMember(x, y, false))
						/**
						 * 如果超出上边界了, 就是放满了
						 */
						if (top + y < 0) {
							full = true;
							for (GroundListener l : listeners)
								l.groundIsFull(this);
						} else {
							/**
							 * 先变成障碍物
							 */
							obstacles[left + x][top + y]
									.cloneProperties(UnitType.OBSTACLE);
							obstacles[left + x][top + y]
									.setColor(colorfulSupport ? shape
											.getColor() : obstacleColor);
						}
				}
		/**
		 * 扫描并删除满行
		 */
		deleteFullLine();
	}

	/**
	 * 扫描并删除满行<BR>
	 * 将调用 deleteLine(int) 方法删除满行
	 */
	public void deleteFullLine() {
		/**
		 * 这次一共消了几行
		 */
		int deletedLineCount = 0;
		/**
		 * 从最后一行开始, 一直到第一行
		 */
		for (int y = Global.HEIGHT - 1; y >= 0; y--) {
			boolean isFull = true;
			for (int x = 0; x < Global.WIDTH; x++) {
				if (obstacles[x][y].equals(UnitType.BLANK))
					isFull = false;
			}
			/**
			 * 如果当前行满了
			 */
			if (isFull) {
				/**
				 * 删除满(当前)行并且当前扫描行号加 1
				 */
				deleteLine(y++);
				deletedLineCount++;
			}
		}

		/**
		 * 如果消行了， 则触发消行事件
		 */
		if (deletedLineCount > 0)
			for (GroundListener l : listeners)
				l.fullLineDeleted(this, deletedLineCount);
	}

	/**
	 * 删除指定的行(这一行上面所有的石头整体下移一行)
	 * 
	 * @param lineNum
	 */
	public void deleteLine(int lineNum) {

		/**
		 * 触发将要消行事件
		 */
		for (GroundListener l : listeners)
			l.beforeDeleteFullLine(this, lineNum);

		for (int y = lineNum; y > 0; y--)
			for (int x = 0; x < Global.WIDTH; x++)
				if (!obstacles[x][y].equals(UnitType.STUBBORN_OBSTACLE))
					if (obstacles[x][y - 1].equals(UnitType.STUBBORN_OBSTACLE)) {
						obstacles[x][y].cloneProperties(UnitType.BLANK);
						obstacles[x][y].setColor(this.griddingColor);
					} else
						obstacles[x][y].cloneProperties(obstacles[x][y - 1]);
		/**
		 * 第一行变成空白
		 */
		for (int x = 0; x < Global.WIDTH; x++)
			if (!obstacles[x][0].equals(UnitType.STUBBORN_OBSTACLE))
				obstacles[x][0] = UnitType.BLANK.clone();
	}

	/**
	 * 容器是否放满了(是否还能接受图形)<BR>
	 * 
	 * @return
	 */
	public boolean isFull() {
		return full;
	}

	/**
	 * 根据图形的动作，判断是否会碰到障碍物或不可消除的障碍物, 或是否会超出边界,
	 * 
	 * @param shape
	 * @param action
	 *            图形将要做的动作
	 * @return 图形是否可以做这个动作
	 */
	public synchronized boolean isMoveable(Shape shape, int action) {
		int left = shape.getLeft();
		int top = shape.getTop();
		/**
		 * 根据动作，得到最新信息
		 */
		switch (action) {

		case Shape.UP:
			top--;
			break;
		case Shape.DOWN:
			top++;
			break;
		case Shape.LEFT:
			left--;
			break;
		case Shape.RIGHT:
			left++;
			break;
		}

		if (top < 0 - shape.getHeight()) {
			return false;
		}
		for (int x = 0; x < 4; x++)
			for (int y = 0; y < 4; y++)

				/**
				 * 如果这个位置超出边界又是图形的一部分
				 */
				if ((left + x < 0 || left + x >= Global.WIDTH || top + y >= Global.HEIGHT)
						&& shape.isMember(x, y, action == Shape.ROTATE))
					return false;
				else if (top + y < 0)
					continue;
				else {
					/**
					 * 或者位置不是空白（是障碍物或不可消除的障碍物）又是图形的一部分
					 */
					if (shape.isMember(x, y, action == Shape.ROTATE))
						if (!obstacles[left + x][top + y]
								.equals(UnitType.BLANK))
							return false;
				}
		return true;
	}

	/**
	 * 改变指定行的颜色,(可以做为消行前的效果)
	 * 
	 * @param lineNum
	 */
	public void changeFullLineColor(int lineNum) {

		for (int x = 0; x < Global.WIDTH; x++)
			obstacles[x][lineNum].setColor(fullLineColor);
	}

	/**
	 * 在指定的位置添加一个障碍物
	 * 
	 * @param x
	 *            x 格子坐标
	 * @param y
	 *            y 格子坐标
	 */
	public void addObstacle(int x, int y) {
		if (x < 0 || x >= Global.WIDTH || y < 0 || y >= Global.HEIGHT)
			throw new RuntimeException("这个位置超出了显示区域 (x:" + x + "  y:" + y + ")");
		obstacles[x][y].cloneProperties(UnitType.OBSTACLE);
	}

	/**
	 * 在指定的位置添加一块不可消除的障碍物
	 * 
	 * @param x
	 *            x 格子坐标
	 * @param y
	 *            y 格子坐标
	 */
	public void addStubbornObstacle(int x, int y) {
		if (x < 0 || x >= Global.WIDTH || y < 0 || y >= Global.HEIGHT)
			throw new RuntimeException("这个位置超出了显示区域 (x:" + x + "  y:" + y + ")");
		obstacles[x][y].cloneProperties(UnitType.STUBBORN_OBSTACLE);
	}

	/**
	 * 显示, 将调用 drawGridding(Graphics, int, int, int, int),
	 * drawObstacle(Graphics, int, int, int, int)和
	 * drawStubbornObstacle(Graphics, int, int, int, int) 方法
	 * 
	 * @param g
	 */
	public void drawMe(Graphics g) {
		for (int x = 0; x < Global.WIDTH; x++)
			for (int y = 0; y < Global.HEIGHT; y++) {
				/**
				 * 画空白/网格(如果允许)
				 */
				if (drawGridding && obstacles[x][y].equals(UnitType.BLANK)) {
					g.setColor(griddingColor);
					drawGridding(g, x * Global.CELL_WIDTH, y
							* Global.CELL_HEIGHT, Global.CELL_WIDTH,
							Global.CELL_HEIGHT);
				}
				/**
				 * 画不可消除的障碍物
				 */
				else if (obstacles[x][y].equals(UnitType.STUBBORN_OBSTACLE)) {
					g.setColor(stubbornObstacleColor);
					drawStubbornObstacle(g, x * Global.CELL_WIDTH, y
							* Global.CELL_HEIGHT, Global.CELL_WIDTH,
							Global.CELL_HEIGHT);
				}
				/**
				 * 画障碍物
				 */
				else if (obstacles[x][y].equals(UnitType.OBSTACLE)) {
					g.setColor(obstacles[x][y].getColor());
					drawObstacle(g, x * Global.CELL_WIDTH, y
							* Global.CELL_HEIGHT, Global.CELL_WIDTH,
							Global.CELL_HEIGHT);
				}
			}
	}

	/**
	 * 
	 * 画一个空白的方法(网格), 可以覆盖这个方法改变空白(网格)的显示
	 * 
	 * @param g
	 * @param x
	 *            像素坐标 x
	 * @param y
	 *            像素坐标 y
	 * @param width
	 *            宽度(单位:像素)
	 * @param height
	 *            高度(单位:像素)
	 */
	public void drawGridding(Graphics g, int x, int y, int width, int height) {
		g.drawRect(x, y, width, height);
	}

	/**
	 * 
	 * 画一个不可消除的障碍物的方法, 可以覆盖这个方法改变不可消除的障碍物的显示
	 * 
	 * @param g
	 * @param x
	 *            像素坐标 x
	 * @param y
	 *            像素坐标 y
	 * @param width
	 *            宽度(单位:像素)
	 * @param height
	 *            高度(单位:像素)
	 */
	public void drawStubbornObstacle(Graphics g, int x, int y, int width,
			int height) {
		g.fill3DRect(x, y, width, height, true);
	}

	/**
	 * 
	 * 画一个障碍物的方法, 可以覆盖这个方法改变障碍物的显示
	 * 
	 * @param g
	 * @param x
	 *            像素坐标 x
	 * @param y
	 *            像素坐标 y
	 * @param width
	 *            宽度(单位:像素)
	 * @param height
	 *            高度(单位:像素)
	 */
	public void drawObstacle(Graphics g, int x, int y, int width, int height) {
		g.fill3DRect(x, y, width, height, true);
	}

	/**
	 * 得到不可消除的障碍物的颜色
	 * 
	 * @return
	 */
	public Color getStubbornObstacleColor() {
		return stubbornObstacleColor;
	}

	/**
	 * 设置不可消除的障碍物的颜色
	 * 
	 * @param stubbornObstacleColor
	 */
	public void setStubbornObstacleColor(Color stubbornObstacleColor) {
		this.stubbornObstacleColor = stubbornObstacleColor;
	}

	/**
	 * 得到网格的颜色
	 * 
	 * @return
	 */
	public Color getGriddingColor() {
		return griddingColor;
	}

	/**
	 * 设置网格的颜色
	 * 
	 * @param griddingColor
	 */
	public void setGriddingColor(Color griddingColor) {
		this.griddingColor = griddingColor;
	}

	/**
	 * 得到障碍物的颜色
	 * 
	 * @return
	 */
	public Color getObstacleColor() {
		return obstacleColor;
	}

	/**
	 * 设置障碍物的颜色
	 * 
	 * @param obstacleColor
	 */
	public void setObstacleColor(Color obstacleColor) {
		this.obstacleColor = obstacleColor;
	}

	/**
	 * 得到满行的颜色
	 * 
	 * @return
	 */
	public Color getFullLineColor() {
		return fullLineColor;
	}

	/**
	 * 设置满行的颜色
	 * 
	 * @param fullLineColor
	 */
	public void setFullLineColor(Color fullLineColor) {
		this.fullLineColor = fullLineColor;
	}

	/**
	 * 是否画网格
	 * 
	 * @return
	 */

	public boolean isDrawGridding() {
		return drawGridding;
	}

	/**
	 * 设置是否画网格
	 * 
	 * @param drawGridding
	 */
	public void setDrawGridding(boolean drawGridding) {
		this.drawGridding = drawGridding;
	}

	/**
	 * 是否支持彩色显示
	 * 
	 * @return
	 */
	public boolean isColorfulSupport() {
		return colorfulSupport;
	}

	/**
	 * 设置是否支持彩色显示
	 * 
	 * @param colorfulSupport
	 */
	public void setColorfulSupport(boolean colorfulSupport) {
		this.colorfulSupport = colorfulSupport;
	}

	/**
	 * 添加监听器, 可添加多个
	 * 
	 * @param l
	 */
	public void addGroundListener(GroundListener l) {
		if (l != null)
			this.listeners.add(l);
	}

	/**
	 * 移除监听器
	 * 
	 * @param l
	 */
	public void removeGroundListener(GroundListener l) {
		if (l != null)
			this.listeners.remove(l);
	}

	/**
	 * 指定位置是否是不可除的障碍物
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isStubbornObstacle(int x, int y) {
		if (x >= 0 && x < Global.WIDTH && y >= 0 && y < Global.HEIGHT)
			return obstacles[x][y].equals(UnitType.STUBBORN_OBSTACLE);
		else
			throw new RuntimeException("这个坐标超出了显示区域: (x:" + x + " y:" + y + ")");
	}

	/**
	 * 指定位置是否是障碍物
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isObstacle(int x, int y) {
		if (x >= 0 && x < Global.WIDTH && y >= 0 && y < Global.HEIGHT)
			return obstacles[x][y].equals(UnitType.OBSTACLE);
		else
			throw new RuntimeException("这个坐标超出了显示区域: (x:" + x + " y:" + y + ")");
	}

	/**
	 * 指定位置是否是空白
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isBlank(int x, int y) {
		if (x >= 0 && x < Global.WIDTH && y >= 0 && y < Global.HEIGHT)
			return obstacles[x][y].equals(UnitType.BLANK);
		else
			throw new RuntimeException("这个坐标超出了显示区域: (x:" + x + " y:" + y + ")");
	}
}
