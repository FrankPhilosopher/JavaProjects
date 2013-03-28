package cn.itcast.tetris.entities;

import java.awt.Color;
import java.util.Random;

import cn.itcast.tetris.listener.ShapeListener;
import cn.itcast.tetris.util.Global;


/**
 * 图形工厂<BR>
 * 可以产生多种形状, 多种颜色的图形<BR>
 * <BR>
 * setDefaultShapeColor() 方法可以设置默认产生的图形的颜色<BR>
 * 也可以通过 setColorfulShape() 方法设置是否产生彩色图形<BR>
 * <BR>
 * 
 * @version 1.0, 01/01/08
 * 
 * @author 汤阳光
 * 
 */
public class ShapeFactory {
	/**
	 * 可以生产出这些形状的图形
	 */
	protected static int shapes[][][] = new int[][][] {
	/* 第一种 */{ /** ***** */
	{ 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } },

	/* 第二种 */
	{ /** ********* */
	{ 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },

	{ 0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0 },

	{ 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },

	{ 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 } },
	/* 第三种 */
	{ /** ******* */
	{ 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },

	{ 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },

	{ 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },

	{ 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0 } },
	/* 第四种 */
	{ /** ******** */
	{ 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },

	{ 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, } },
	/* 第五种 */
	{ /** ******** */
	{ 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },

	{ 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 } },
	/* 第六种 */
	{ /** *********** */
	{ 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },

	{ 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },

	{ 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },

	{ 0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 } },

	/* 第七种 */
	{ /** ********** */
	{ 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },

	{ 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0 } },

	};

	protected Random random = new Random();

	/**
	 * 图形的默认颜色
	 */
	public static final Color DEFAULT_SHAPE_COLOR = new Color(0x990066);
	/**
	 * 生产的图形的颜色
	 */
	protected Color defaultShapeColor = DEFAULT_SHAPE_COLOR;

	/**
	 * 是否产生彩色图形
	 */
	protected boolean colorfulShape;

	/**
	 * 生产出随机类型的图形，并且把传过来的监听器注册给它
	 * 
	 * @param shpaeListener
	 * @return
	 */
	public Shape getShape(ShapeListener shapeListener) {
		int type = random.nextInt(shapes.length);
		Shape shape = new Shape(shapes[type], random
				.nextInt(shapes[type].length));
		shape.setColor(colorfulShape ? Global.getRandomColor()
				: defaultShapeColor);
		shape.addShapeListener(shapeListener);
		return shape;
	}

	/**
	 * 得到默认产生的图形的颜色
	 * 
	 * @return
	 */
	public Color getDefaultShapeColor() {
		return defaultShapeColor;
	}

	/**
	 * 设置默认产生的图形的颜色
	 * 
	 * @param defaultShapeColor
	 */
	public void setDefaultShapeColor(Color defaultShapeColor) {
		this.defaultShapeColor = defaultShapeColor;
	}

	/**
	 * 是否产生彩色图形
	 * 
	 * @return 是否产生彩色图形
	 */
	public boolean isColorfulShape() {
		return colorfulShape;
	}

	/**
	 * 设置是否产生彩色图形
	 * 
	 * @param colorfulShape
	 */
	public void setColorfulShape(boolean colorfulShape) {
		this.colorfulShape = colorfulShape;
	}

}
