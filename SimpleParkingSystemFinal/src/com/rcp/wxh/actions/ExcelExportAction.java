package com.rcp.wxh.actions;

import java.io.File;
import java.util.List;

import jxl.Workbook;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.rcp.wxh.composite.PageComposite;
import com.rcp.wxh.utils.MessageDialogUtil;

/**
 * excel 数据导出
 * 
 * @author wuxuehong 2011-12-5
 * 
 * TODO:yinger    没有分成两种，可以统一使用第二种方法，只是在第一个构造函数中加上最后一句注释
 * 
 */
public class ExcelExportAction {

	private List<Object> objs;
	private String title;
	private TableViewer tv;
	private Shell shell;
	private String filename;
	private PageComposite page;

	public ExcelExportAction(Shell shell, String title, String filename, PageComposite page) {
		this.title = title;
		this.shell = shell;
		this.filename = filename;
		this.page = page; //tv = page.getTableView(); // 从分页模板中获取表格视图
	}

	public ExcelExportAction(Shell shell, String title, String filename, TableViewer tv) {
		this.title = title;
		this.shell = shell;
		this.filename = filename;
		this.tv = tv;
	}

	/**
	 * 将数据写入excel文件
	 * 如果使用第一个构造函数 则使用该方法
	 * object : pageComposote
	 * 
	 * @throws Exception
	 */
	public void run() throws Exception {
		tv = page.getTableView(); // 从分页模板中获取表格视图
		objs = page.getObjs(); // 从分页模块中获取当前所有的数据集合
		if (tv == null || objs == null) {
			MessageDialogUtil.showWarningMessage(shell, "暂无可导出数据!");
			return;
		}
		// 打开文件选择对话框
		org.eclipse.swt.widgets.FileDialog fd = new org.eclipse.swt.widgets.FileDialog(shell, SWT.SAVE);
		fd.setFileName(filename);
		fd.setFilterExtensions(new String[] { "*.xls" });
		filename = fd.open();
		if (filename == null || "".equals(filename))
			return; // 如果取消文件对话框则返回

		WritableWorkbook book = Workbook.createWorkbook(new File(filename));
		WritableSheet sheet = book.createSheet(title, 0);
		WritableFont font = new WritableFont(WritableFont.createFont("楷体_GB2312"), 17, WritableFont.BOLD);
		WritableCellFormat format = new WritableCellFormat(font);
		// 表头
		int count = tv.getTable().getColumnCount(); // 列数
		TableColumn[] tc = tv.getTable().getColumns();
		for (int i = 0; i < count; i++) {
			jxl.write.Label label = new jxl.write.Label(i, 0, tc[i].getText());
			label.setCellFormat(format);
			sheet.addCell(label);
		}

		// 获取表格中的所有数据
		int size = objs.size();
		ITableLabelProvider lp = (ITableLabelProvider) tv.getLabelProvider(); // 表格标签提供其
		for (int i = 0; i < size; i++) { // 行数
			for (int j = 0; j < count; j++) { // 列数
				sheet.addCell(new jxl.write.Label(j, i + 1, lp.getColumnText(objs.get(i), j)));
			}
		}
		book.write();
		book.close();
		MessageDialogUtil.showInfoMessage(shell, filename + "导出成功!");
	}

	/**
	 * object: tableViewer
	 * 如果使用第二个构造函数 则使用该方法导出
	 */
	public void run2() throws Exception {
		if (tv == null || tv.getTable().getItems().length == 0) {
			MessageDialogUtil.showWarningMessage(shell, "暂无可导出数据!");
			return;
		}

		// 打开文件选择对话框
		org.eclipse.swt.widgets.FileDialog fd = new org.eclipse.swt.widgets.FileDialog(shell, SWT.SAVE);
		fd.setFileName(filename);
		fd.setFilterExtensions(new String[] { "*.xls" });
		filename = fd.open();
		if (filename == null || "".equals(filename))
			return; // 如果取消文件对话框则返回

		WritableWorkbook book = Workbook.createWorkbook(new File(filename));
		WritableSheet sheet = book.createSheet(title, 0);
		WritableFont font = new WritableFont(WritableFont.createFont("楷体_GB2312"), 17, WritableFont.BOLD);
		WritableCellFormat format = new WritableCellFormat(font);
		// 表头
		int count = tv.getTable().getColumnCount(); // 列数
		TableColumn[] tc = tv.getTable().getColumns();
		for (int i = 0; i < count; i++) {
			jxl.write.Label label = new jxl.write.Label(i, 0, tc[i].getText());
			label.setCellFormat(format);
			sheet.addCell(label);
		}

		TableItem[] ti = tv.getTable().getItems();
		TableItem tii = null;
		// 获取表格中的所有数据
		for (int i = 0; i < ti.length; i++) { // 行数
			tii = ti[i];
			for (int j = 0; j < count; j++) { // 列数
				sheet.addCell(new jxl.write.Label(j, i + 1, tii.getText(j)));
			}
		}
		book.write();
		book.close();
		MessageDialogUtil.showInfoMessage(shell, filename + "导出成功!");
	}

}
