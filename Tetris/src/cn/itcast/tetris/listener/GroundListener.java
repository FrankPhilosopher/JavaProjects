package cn.itcast.tetris.listener;

import cn.itcast.tetris.entities.Ground;

/**
 * 地面监听器
 * 
 * @version 1.0, 01/01/08
 * 
 * @author 汤阳光
 * 
 */

public interface GroundListener {
	/**
	 * 将要消行事件
	 * 
	 * 
	 * @param ground
	 * 
	 * @param lineNum
	 *            将要消除的行的行号
	 * @param lineNum
	 */
	void beforeDeleteFullLine(Ground ground, int lineNum);

	/**
	 * 消除满行事件
	 * 
	 * @param ground
	 * @param deletedLineCount
	 *            本次消除的行数
	 */
	void fullLineDeleted(Ground ground, int deletedLineCount);

	void groundIsFull(Ground ground);
}
