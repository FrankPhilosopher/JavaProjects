package com.rcp.wbw.demo;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RefineryUtilities;

import com.rcp.wxh.dao.impl.TCarRecordDao;

public class CopyOfParkingSystemChartZhu_test extends ApplicationFrame {
	private static String timefrom;
	private static String timeto;
	private static int type;
	private static java.sql.Date datefrom;
	private static java.sql.Date dateto;
	private static int highValue_Y;
	private static int minValue_Y;
	private static double highValue_X;
	private static double minValue_X;
	private static double daycount;
	private static Date min, max;

	// private static double daycount_c;
	// 因为这是出入场图，所以传入的type没有用到,所以这里的type的意思是按日统计（0）还是按月统计（1）
	public CopyOfParkingSystemChartZhu_test(String title, String timefrom, String timeto, int type) {
		super(title);
		this.timefrom = timefrom;
		this.timeto = timeto;
		this.type = type;
		datefrom = java.sql.Date.valueOf(timefrom);
		dateto = java.sql.Date.valueOf(timeto);
		ChartPanel chartPanel = (ChartPanel) createDemoPanel();
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		setContentPane(chartPanel);
	}

	public static JFreeChart createChartZhu(IntervalXYDataset dataset) {

		JFreeChart chart = ChartFactory.createXYBarChart("时间段内车辆出入统计柱形图", // title
				"数据日期从" + min + "到" + max, true,// x-axis label
				"车辆出入数量", // y-axis label
				dataset, PlotOrientation.VERTICAL, // data
				true, // create legend?
				true, // generate tooltips?
				false // generate URLs?
				);
		chart.setBackgroundPaint(Color.white);
		chart.getTitle().setFont(new Font("宋体", Font.BOLD, 15));
		// chart.getCategoryPlot().getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_45);

		XYPlot plot2 = (XYPlot) chart.getPlot();
		plot2.setBackgroundPaint(Color.lightGray);
		plot2.setDomainGridlinePaint(Color.white);
		plot2.setRangeGridlinePaint(Color.white);
		plot2.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));// 设定坐标轴与图表数据显示部分距离
		plot2.setDomainCrosshairVisible(true);
		plot2.setRangeCrosshairVisible(true);
		plot2.getRangeAxis().setLabelFont(new Font("宋体", Font.BOLD, 15));
		// 横轴框里的标题字体
		chart.getLegend().setItemFont(new Font("宋体", Font.ITALIC, 10));
		// 横轴列表字体
		plot2.getDomainAxis().setTickLabelFont(new Font("新宋体", 1, 10));
		// 横轴小标题字体
		plot2.getDomainAxis().setLabelFont(new Font("新宋体", 1, 10));
		plot2.setNoDataMessage("没有数据");
		plot2.setBackgroundAlpha(0.5f);

		XYBarRenderer xyBarRender = new XYBarRenderer();
		xyBarRender.setMargin(0.5);// 设置柱形图之间的间隔

		GradientPaint gradientpaint1 = new GradientPaint(0.0F, 0.0F, Color.black, 0.0F, 0.0F, new Color(0, 0, 64));
		GradientPaint gradientpaint3 = new GradientPaint(0.0F, 0.0F, Color.red, 0.0F, 0.0F, new Color(64, 0, 0));

		xyBarRender.setSeriesPaint(0, gradientpaint1);
		xyBarRender.setSeriesPaint(1, gradientpaint3);
		xyBarRender.setSeriesVisibleInLegend(1, true, true);
		xyBarRender.setBarAlignmentFactor(0.5);
		xyBarRender.setDrawBarOutline(false);

		xyBarRender.setSeriesStroke(0, new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f,
				new float[] { 10.0f, 6.0f }, 0.0f));
		xyBarRender.setSeriesStroke(1, new BasicStroke(5.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND, 5.0f,
				new float[] { 15.0f, 10.0f }, 10.0f));

		plot2.setRenderer(xyBarRender);
		ClusteredXYBarRenderer clusteredxybarrenderer = new ClusteredXYBarRenderer(0.001D, false);
		plot2.setRenderer(clusteredxybarrenderer);

		// CategoryAxis横抽，通过getDomainAxis取得，NumberAxis纵轴，通过getRangeAxis取得。
		DateAxis axis = (DateAxis) plot2.getDomainAxis();
		// axis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd"));
		if (type == 0) // type = 0则以1天为横轴最小坐标，为1则以1月为横轴最小坐标
		{
			axis.setDateFormatOverride(new SimpleDateFormat("dd"));// 设置时间格式
			axis.setTickUnit(new DateTickUnit(DateTickUnit.DAY, 1));
		} else {
			axis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM"));
			axis.setTickUnit(new DateTickUnit(DateTickUnit.MONTH, 1));
		}
		axis.setLabelFont(new Font("黑体", Font.TRUETYPE_FONT, 12));
		// axis.setLabelAngle(0.85);
		axis.setAutoTickUnitSelection(false);// 时间轴的数据标签是否自动确
		// ItemLabelPosition itemlabelposition = new
		// ItemLabelPosition(ItemLabelAnchor.INSIDE12,TextAnchor.CENTER_RIGHT,TextAnchor.CENTER_RIGHT, -1.57D);

		// 下面这一块是用来设置轴的时间间隔的，可能会出现，出错时可以简单设处日期就行了
		// 设置X轴间隔的另一种方法更好不出错
		int a = 1;
		a = (int) (daycount / 10) + 1;
		// axis.setTickUnit(new DateTickUnit(DateTickUnit.DAY,a));
		// axis.setLabelAngle(0.45);
		// axis.setLabelInsets(, true);

		NumberAxis numberaxis = (NumberAxis) plot2.getRangeAxis();
		numberaxis.setAxisLineVisible(false);// 是否显示纵坐标
		numberaxis.setTickMarksVisible(false);// 是否显示坐标标尺
		numberaxis.setAutoRangeIncludesZero(false);// 是否自动包含0起点? 默认为true
		numberaxis.setAutoTickUnitSelection(false);// 数据轴的数据标签是否自动确定
		int space_y = (int) ((highValue_Y * 1.1 - minValue_Y * 0.9) / 10);
		if (space_y == 0)
			space_y = 1;
		System.out.println("spacespace sapce space" + space_y);
		numberaxis.setTickUnit(new NumberTickUnit(space_y));// 设置刻度显示的密度
		numberaxis.setAutoRangeMinimumSize(2D);
		return chart;

	}

	public static IntervalXYDataset createDatasetBar() throws Exception {
		IntervalXYDataset dataset1 = null;
		Object[][] chart = null;
		Object[][] chart_qu = null;
		TCarRecordDao tdao = new TCarRecordDao();
		TimeSeries s1, s2;
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		try {
			if (type == 0) {
				// 从DAO层获得数据，以天为统计单位，数据线以天为单位
				chart = tdao.getCarRecodebyTimearea_day(timefrom, timeto, 1);
				chart_qu = tdao.getCarRecodebyTimearea_day(timefrom, timeto, 0);
				s1 = new TimeSeries("入场柱形", Day.class);
				s2 = new TimeSeries("出场柱形", Day.class);
			} else {

				// 传进DAO层的数据是年月，数据线以月的单位
				Date date = java.sql.Date.valueOf(timefrom);
				Date date_t = java.sql.Date.valueOf(timeto);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String newdate_from = sdf.format(date);
				String timefrom_temp = newdate_from.substring(0, 7);
				String newdate_to = sdf.format(date_t);
				String timeto_temp = newdate_to.substring(0, 7);
				chart = tdao.getCarRecodebyTimearea_month(timefrom_temp, timeto_temp, 1);
				chart_qu = tdao.getCarRecodebyTimearea_month(timefrom_temp, timeto_temp, 0);
				s1 = new TimeSeries("入场柱形", Month.class);
				s2 = new TimeSeries("出场柱形", Month.class);

			}

			s1.clear();
			Date d, min_r = null, min_c = null, max_r = null, max_c = null;
			double h;
			if (chart != null && chart.length > 0) { // 数据为空的处理
				System.out.println("ff");
				min_r = (Date) chart[0][1];
				max_r = (Date) chart[0][1];
				for (int i = 0; i < chart.length; i++) {
					d = (Date) chart[i][1];
					if (d.before(min_r))
						min_r = d;
					if (d.after(max_r))
						max_r = d;
					h = new Double(chart[i][0].toString());
					// 添加数据，0是以天为单位，1是以月为单位
					if (type == 0)
						s1.add(new Day(d.getDate(), (d.getMonth() + 1), (d.getYear() + 1900)), h);
					else
						s1.add(new Month((d.getMonth() + 1), (d.getYear() + 1900)), h);
				}
				dataset.addSeries(s1);
			} else
				s1 = null;

			s2.clear();

			if (chart_qu != null && chart_qu.length > 0) {
				min_c = (Date) chart_qu[0][1];
				max_c = (Date) chart_qu[0][1];
				for (int i = 0; i < chart_qu.length; i++) {
					d = (Date) chart_qu[i][1];
					if (d.before(min_c))
						min_c = d;
					if (d.after(max_c))
						max_c = d;
					h = new Double(chart_qu[i][0].toString());
					if (type == 0)
						s2.add(new Day(d.getDate(), (d.getMonth() + 1), (d.getYear() + 1900)), h);
					else
						s2.add(new Month((d.getMonth() + 1), (d.getYear() + 1900)), h);
				}
				dataset.addSeries(s2);
			} else
				s2 = null;

			// 取得有数据的时间段的开始和结束
			if (min_c != null && min_r != null)
				min = min_c.before(min_r) ? min_c : min_r;
			else if (min_c != null)
				min = min_c;
			else if (min_r != null)
				min = min_r;
			else
				min = null;

			if (max_c != null && max_r != null)
				max = max_c.before(max_r) ? max_r : max_c;
			else if (max_c != null)
				max = max_c;
			else if (max_r != null)
				max = max_r;
			else
				max = null;

			int seriesCount = dataset.getSeriesCount();// 一共有多少个序列，目前为一个
			for (int i = 0; i < seriesCount; i++) {
				int itemCount = dataset.getItemCount(i);// 每一个序列有多少个数据项
				for (int j = 0; j < itemCount; j++) {
					if (highValue_Y < dataset.getYValue(i, j)) {// 取第i个序列中的第j个数据项的最大值
						highValue_Y = (int) dataset.getYValue(i, j);
					}
					if (minValue_Y > dataset.getYValue(i, j)) {// 取第i个序列中的第j个数据项的最小值
						minValue_Y = (int) dataset.getYValue(i, j);
					}
				}
			}

			dataset1 = dataset;
			return dataset1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return dataset1;
		}

	}

	public static JPanel createDemoPanel() {
		JFreeChart chart;
		ChartPanel panel = null;
		try {
			chart = createChartZhu(createDatasetBar());

			panel = new ChartPanel(chart);
			panel.setFillZoomRectangle(true);
			panel.setMouseWheelEnabled(true);
			return panel;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return panel;
		}

	}

	/**
	 * Starting point for the demonstration application.
	 * 
	 * @param args
	 *            ignored.
	 */
	public static void main(String[] args) {

		CopyOfParkingSystemChartZhu_test demo = new CopyOfParkingSystemChartZhu_test("Time Series Chart Demo 1",
				"2011-11-1", "2011-12-25", 0);
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);

	}

}
