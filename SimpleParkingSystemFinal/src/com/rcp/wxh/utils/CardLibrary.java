package com.rcp.wxh.utils;

import com.sun.jna.Library;

/**
 * ¶Á¿¨Æ÷ ¿¨Æ¬²Ù×÷ º¯Êý¿â
 * 
 * @author wuxuehong 2011-11-10
 * 
 */
public interface CardLibrary extends Library {

	/**
	 * ¶ÁÈ¡¿¨Æ¬
	 * 
	 * @param mode
	 * @param blk_add
	 * @param num_blk
	 * @param snr
	 * @param buffer
	 * @return
	 */
	public int MF_Read(byte mode, byte blk_add, byte num_blk, byte[] snr, byte[] buffer);
}
