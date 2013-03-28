package cn.itcast.tetris.listener;

import cn.itcast.tetris.entities.Shape;

/**
 * 图形监听器
 * 
 * @version 1.0, 01/01/08
 * 
 * @author 汤阳光
 * 
 */

public interface ShapeListener {
	/**
	 * 图形询问是否可以下落
	 * 
	 * 
	 * @param shape
	 * @return
	 */
	boolean isShapeMoveDownable(Shape shape);

	/**
	 * 图形下落事件
	 * 
	 * @param shape
	 */
	void shapeMovedDown(Shape shape);
}
