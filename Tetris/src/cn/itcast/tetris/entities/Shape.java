package cn.itcast.tetris.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.util.TooManyListenersException;

import cn.itcast.tetris.listener.ShapeListener;
import cn.itcast.tetris.util.Global;


/**
 * 
 * 维护一个图形, 一个图形可以有一种或多种状态<BR>
 * <BR>
 * 可以通过 setColor() 方法改变图形的颜色<BR>
 * <BR>
 * 可以通过覆盖 drawUnit(Graphics, int, int, int, int) 方法改变图形的显示<BR>
 * <BR>
 * 用内部类 ShapeDriver 驱动图形定时向下移动<BR>
 * <BR>
 * 使用时一定要给实例注册监听器, 否则不能正常运行 <BR>
 * 
 * @version 1.0, 01/01/08
 * 
 * @author 汤阳光
 * 
 */
public class Shape {

	/**
	 * 变形(旋转)
	 */
	public static final int ROTATE = 5;
	/**
	 * 上移
	 */
	public static final int UP = 1;
	/**
	 * 下落
	 */
	public static final int DOWN = 2;
	/**
	 * 左移
	 */
	public static final int LEFT = 3;
	/**
	 * 右移
	 */
	public static final int RIGHT = 4;

	/**
	 * 监听器组
	 */
	protected ShapeListener listener;

	/**
	 * 
	 */
	protected int[][] body;

	/**
	 * 当前显示的状态
	 */
	protected int status;

	/**
	 * 图形的真实高度
	 */
	protected int height;

	/**
	 * 左上角的位置
	 */
	protected int left;

	/**
	 * 左上角的位置
	 */
	protected int top;

	/**
	 * 下落的速度
	 */
	protected int speed;

	/**
	 * 生命
	 */
	protected boolean life;

	/**
	 * 暂停状态
	 */
	protected boolean pause;

	protected boolean swift;

	protected int swiftSpeed = Global.SWIFT_SPEED;

	protected Thread shapeThread, swiftThread;

	/**
	 * 颜色
	 */
	protected Color color = Color.BLUE;

	/**
	 * 指定类型, 指定状态的构造方法<BR>
	 * 将会调用 init() 方法
	 * 
	 * @param body
	 * @param status
	 */
	public Shape(int[][] body, int status) {
		super();
		this.body = body;
		this.status = status;
		for (int y = 0; y < 4; y++)
			for (int x = 0; x < 4; x++)
				if (isMember(x, y, false))
					height = y + 1;
		init();
	}

	/**
	 * 初始化位置，速度等
	 */
	public void init() {
		life = true;
		pause = false;
		swift = false;
		left = Global.WIDTH / 2 - 2;
		top = 0 - height;
		speed = Global.CURRENT_SPEED;
	}

	/**
	 * 旋转(或叫做变形, 显示下一个状态)
	 */
	public void rotate() {
		status = (status + 1) % body.length;
	}

	public void moveUp() {
		top--;
	}

	/**
	 * 向下移动
	 */
	public void moveDown() {
		top++;
	}

	/**
	 * 向左移动
	 */
	public void moveLeft() {
		left--;
	}

	/**
	 * 向右移动
	 */
	public void moveRight() {
		left++;
	}

	/**
	 * 驱动图形定时下落的内部类
	 * 
	 * @version 1.0, 01/01/08
	 * 
	 * @author 汤阳光
	 */
	protected class ShapeDriver implements Runnable {

		/**
		 * 驱动图形定时下落
		 */
		public void run() {
			if (listener == null)
				throw new RuntimeException("请先注册 ShapeListener");

			while (life && listener.isShapeMoveDownable(Shape.this)) {
				if (!swift) {
					if (!pause) {
						moveDown();
						/**
						 * 触发下落事件
						 */
						listener.shapeMovedDown(Shape.this);
					}
				}
				try {
					Thread.sleep(speed);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			life = false;
		}

	}

	/**
	 * 显示, 将调用drawUnit(Graphics, int, int, int, int)方法
	 * 
	 * @param g
	 */
	public void drawMe(Graphics g) {
		/**
		 * 死了就不画了，针对游戏结束时的最后一个图形
		 */
		if (!life)
			return;
		g.setColor(color);
		for (int x = 0; x < 4; x++)
			for (int y = 0; y < 4; y++)
				if (getFlagByPoint(status, x, y))
					drawUnit(g, (left + x) * Global.CELL_WIDTH, (top + y)
							* Global.CELL_HEIGHT, Global.CELL_WIDTH,
							Global.CELL_HEIGHT);
	}

	/**
	 * 
	 * 画具体的每一个方块的方法, 可以覆盖这个方法改变图形的显示
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
	public void drawUnit(Graphics g, int x, int y, int width, int height) {
		g.fill3DRect(x, y, width, height, true);
	}

	/**
	 * 相对坐标（x，y）是否是图形中的点
	 * 
	 * @param x
	 *            相对坐标x
	 * @param y
	 *            相对坐标y
	 * @return
	 */
	protected boolean getFlagByPoint(int status, int x, int y) {
		return body[status][y * 4 + x] == 1;
	}

	/**
	 * 
	 * 指定的位置是否是图形的一部分
	 * 
	 * @param x
	 *            x（格子）（相对）坐标
	 * @param y
	 *            y（格子）（相对）坐标
	 * @param isRotate
	 *            是否旋转了
	 * @return
	 */
	public boolean isMember(int x, int y, boolean isRotate) {
		return getFlagByPoint(isRotate ? (status + 1) % body.length : status,
				x, y);
	}

	/**
	 * 加速
	 */
	public void speedUp() {
		if (speed > Global.SPEED_STEP)
			speed -= Global.SPEED_STEP;
		Global.CURRENT_SPEED = speed;
	}

	/**
	 * 减速
	 */
	public void speedDown() {
		speed += Global.SPEED_STEP;
		Global.CURRENT_SPEED = speed;
	}

	/**
	 * 得到图形的下落速度
	 * 
	 * @return
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * 设置图形的下落速度
	 * 
	 * @param speed
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * 得到图形的暂停状态
	 * 
	 * @return
	 */
	public boolean isPause() {
		return pause;
	}

	/**
	 * 设置图形的暂停状态
	 * 
	 * @param pause
	 */
	public void setPause(boolean pause) {
		this.pause = pause;
	}

	/**
	 * 更改暂停状态<BR>
	 * 若是暂停状态, 则继续下落<BR>
	 * 若正在下落, 则暂停
	 */
	public void changePause() {
		this.pause = !this.pause;
	}

	/**
	 * 得到图形的当前是第几种状态
	 * 
	 * @return
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * 设置图形的当前是第几种状态
	 * 
	 * @param status
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * 得到图形的位置
	 * 
	 * @return
	 */
	public int getLeft() {
		return left;
	}

	/**
	 * 设置图形的位置
	 * 
	 * @param left
	 */
	public void setLeft(int left) {
		this.left = left;
	}

	/**
	 * 得到图形的位置
	 * 
	 * @return
	 */
	public int getTop() {
		return top;
	}

	/**
	 * 设置图形的位置
	 * 
	 * @param top
	 */
	public void setTop(int top) {
		this.top = top;
	}

	/**
	 * 得到图形的颜色
	 * 
	 * @return
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * 设置图形的颜色
	 * 
	 * @param color
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * 设置图形的类型（多种状态）
	 * 
	 * @param body
	 */
	public void setBody(int[][] body) {
		this.body = body;
	}

	/**
	 * 添加监听器<BR>
	 * 将会启动驱动图形下落的线程
	 * 
	 * @param l
	 */
	public void addShapeListener(ShapeListener l) {
		if (l == null || this.listener == l)
			return;
		if (this.listener != null)
			throw new RuntimeException(new TooManyListenersException());
		this.listener = l;

		start();
	}

	protected void start() {
		shapeThread = new Thread(new ShapeDriver());
		shapeThread.start();
	}

	public boolean isLife() {
		return life;
	}

	/**
	 * 结束图形定时下落的线程
	 */
	public synchronized void die() {
		this.life = false;
	}

	public boolean isSwift() {
		return swift;
	}

	/**
	 * 一落到底
	 * 
	 * @param swift
	 */
	public void setSwift(boolean swift) {

		if (this.swift == swift)
			return;

		this.swift = swift;
		if (this.swift) {
			swiftThread = new Thread(new ShapeSwiftDriver());
			swiftThread.start();
		}
	}

	protected class ShapeSwiftDriver implements Runnable {

		public void run() {
			// TODO Auto-generated method stub
			while (swift && life) {
				if (listener == null)
					throw new RuntimeException("请先注册 ShapeListener");
				if (listener.isShapeMoveDownable(Shape.this)) {
					if (!pause) {
						moveDown();
						/**
						 * 触发下落事件
						 */
						listener.shapeMovedDown(Shape.this);
					}
					try {
						Thread.sleep(swiftSpeed);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					life = false;
				}
			}
		}

	}

	public int getSwiftSpeed() {
		return swiftSpeed;
	}

	public void setSwiftSpeed(int swiftSpeed) {
		this.swiftSpeed = swiftSpeed;
	}

	public int getHeight() {
		return height;
	}

}
