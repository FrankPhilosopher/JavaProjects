package mydip.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Panel;
import java.text.DecimalFormat;

import mydip.tools.Histogram;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;

public class HistogramDialog extends Dialog {
	private JFreeChart chart;
	private Histogram histogram;
	private Shell shell;

	public HistogramDialog(Shell parent, Histogram histogram) {
		super(parent);
		this.histogram = histogram;
		this.chart = createChart();
	}

	public void open() {
		Shell parent = getParent();
		shell = new Shell(parent, SWT.SHELL_TRIM);
		shell.setText("Histogram");
		shell.setSize(820, 650);

		createMenuBar(shell);
		createContents(shell);

		shell.setLayout(new FillLayout());// 记住，一定要有layout！
		shell.open();
		Display display = parent.getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	// 创建chart
	private JFreeChart createChart() {
		JFreeChart chart = null;
		if (histogram.isV() == true) {
			chart = ChartFactory.createBarChart(histogram.getTitle(), histogram.getXTitle(), histogram.getYTitle(), histogram.getDataset(),
					PlotOrientation.VERTICAL, false, false, false);
		} else {
			chart = ChartFactory.createBarChart(histogram.getTitle(), histogram.getXTitle(), histogram.getYTitle(), histogram.getDataset(),
					PlotOrientation.HORIZONTAL, false, false, false);
		}
		CategoryPlot categoryplot = chart.getCategoryPlot();
		categoryplot.setRangeGridlinesVisible(true);// 设置横虚线可见
		categoryplot.setRangeGridlinePaint(Color.YELLOW);
		// 设置X轴
		CategoryAxis categoryaxis = categoryplot.getDomainAxis();
		categoryaxis.setCategoryMargin(histogram.getMargin());// 设置边距
		chart.setBackgroundPaint(histogram.getBgcolor()); // 设置背景颜色
		categoryaxis.setTickLabelFont(new Font("black", Font.BOLD, histogram.getXsz()));
		// categoryaxis.setLowerMargin(10);//不要设置这个，会报错，字符串越界！
		// categoryaxis.setUpperMargin(10);
		// 设置Y轴
		NumberAxis numAxis = (NumberAxis) categoryplot.getRangeAxis();
		DecimalFormat decimalFormat = new DecimalFormat("0.00%");
		numAxis.setNumberFormatOverride(decimalFormat);// 设置数据精度
		numAxis.setTickLabelFont(new Font("black", Font.BOLD, histogram.getYsz()));
		// 设置bar的属性
		BarRenderer renderer = new BarRenderer();
		renderer.setItemMargin(0.2);
		categoryplot.setRenderer(renderer);
		return chart;
	}

	// 创建菜单栏
	private void createMenuBar(Shell s) {
		Menu m = new Menu(s, SWT.BAR);
		MenuItem file = new MenuItem(m, SWT.CASCADE);
		file.setText("File");
		Menu filemenu = new Menu(s, SWT.DROP_DOWN);
		file.setMenu(filemenu);
		MenuItem saveItem = new MenuItem(filemenu, SWT.PUSH);
		saveItem.setText("Save");
		saveItem.addSelectionListener(new SaveHistogramListener());
		MenuItem exitItem = new MenuItem(filemenu, SWT.PUSH);
		exitItem.setText("Exit");
		exitItem.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();//关闭shell
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		
		s.setMenuBar(m);
	}

	// 创建主窗体
	//awt jfreechart panel
	private void createContents(Shell shell) {
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayout(new FillLayout());
		Composite composite_1 = new Composite(composite, SWT.EMBEDDED);
		Frame frame = SWT_AWT.new_Frame(composite_1);
		Panel panel = new Panel();
		frame.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		ChartPanel chartPanel = new ChartPanel(chart);
		panel.add(chartPanel);
	}

	//保存直方图
	public class SaveHistogramListener implements SelectionListener {
		@Override
		public void widgetSelected(SelectionEvent e) {
			FileDialog fileDialog = new FileDialog(Display.getDefault().getShells()[0], SWT.SAVE);
			fileDialog.setFilterExtensions(new String[] { ".jpg", ".gif", ".png", ".tif" });// 试验
			String filepath = fileDialog.open();
			if (filepath != null) {
				histogram.saveHistogram(filepath, chart);
//				shell.redraw();//这里会出现保存了之后chart就没了！原因是saveHistogram方法中把数据clear掉了！
			}
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
		}
	}
}
