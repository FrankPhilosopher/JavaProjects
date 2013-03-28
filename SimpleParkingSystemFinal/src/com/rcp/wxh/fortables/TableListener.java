package com.rcp.wxh.fortables;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;

import com.swtdesigner.SWTResourceManager;

public class TableListener {// 这个是用来统一表的格式用的

	public static void tableCellHeight(final Table table) {// 表行的高度，所有表可统一设定
		table.addListener(SWT.MeasureItem, new Listener() {
			public void handleEvent(Event event) {
				event.height = 20;
			}
		});

	}

	public static void tableColor(final Table table) { // 统一表格的颜色
		table.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
	}

}
