package com.rcp.wbw.demo;

import java.awt.Color;
import java.awt.Font;
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
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RefineryUtilities;

import com.rcp.wxh.dao.impl.TCarRecordDao;

public class ParkingSystemChartQu extends ApplicationFrame {
	private static String timefrom;
	private static String timeto;
	private static int type;
	private static int highValue_Y;
	private static int minValue_Y;
	private static Date min, max;
	private static double daycount;

	// 因为这是出入场折线图，所以传入的type没有用到,所以这里的type的意思是按日统计（0）还是按月统计（1）
	public ParkingSystemChartQu(String title, String timefrom, String timeto, int type) {
		super(title);
		this.timefrom = timefrom;
		this.timeto = timeto;
		this.type = type;
		ChartPanel chartPanel = (ChartPanel) createDemoPanel();
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));//大小写死了！
		setContentPane(chartPanel);
	}

	/**
	 * Creates a chart.
	 * 
	 * @param dataset
	 *            a dataset.
	 * 
	 * @return A chart.
	 */
	@SuppressWarnings("deprecation")
	public static JFreeChart createChartQu(XYDataset dataset) {
		// 初始时间段写出来，和传进来的开始结束时间不一样，是有数据的开始结束时间
		// String f = (min.getYear()+1900)+"年"+(min.getMonth()+1)+"月"+min.getDay()+"日";
		// String t = (max.getYear()+1900)+"年"+(max.getMonth()+1)+"月"+max.getDay()+"日";
		JFreeChart chart = ChartFactory.createTimeSeriesChart("时间段内车辆出入统计图", // title
				"数据日期从" + min + "到" + max, // x-axis label
				"车辆出入数量", // y-axis label
				dataset, // data
				true, // create legend?
				true, // generate tooltips?
				false // generate URLs?
				);
		chart.setBackgroundPaint(Color.white);
		chart.getTitle().setFont(new Font("宋体", Font.BOLD, 15));
		XYPlot plot = (XYPlot) chart.getPlot();

		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);
		plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));// 设定坐标轴与图表数据显示部分距离
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);
		XYLineAndShapeRenderer xylinerenderer = (XYLineAndShapeRenderer) plot.getRenderer();
		// 以下分别给同一个曲线图中3条曲线设置颜色,0为第一条1为第二条,......
		xylinerenderer.setSeriesPaint(0, Color.RED);
		xylinerenderer.setSeriesPaint(1, Color.black);
		plot.getRangeAxis().setLabelFont(new Font("宋体", Font.BOLD, 15));
		// 横轴框里的标题字体
		// plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT, true);
		chart.getLegend().setItemFont(new Font("宋体", Font.ITALIC, 10));
		// 横轴列表字体
		plot.getDomainAxis().setTickLabelFont(new Font("新宋体", 1, 10));
		// 横轴小标题字体
		plot.getDomainAxis().setLabelFont(new Font("新宋体", 1, 10));
		plot.setNoDataMessage("没有数据");

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true); // 数据点可见
			renderer.setBaseShapesFilled(true);// 数据点是实心点
			renderer.setDrawSeriesLineAsPath(true);
			renderer.setSeriesShape(0, new java.awt.geom.Ellipse2D.Double(-5D, -5D, 10D, 10D));// 数据点是圆点
		}

		DateAxis axis = (DateAxis) plot.getDomainAxis();
		// domainAxis.setUpperMargin(0.05); // 设置距离图片左端距离
		// domainAxis.setLowerMargin(0.05); // 设置距离图片右端距离
		if (type == 0) // type = 0则以1天为横轴最小坐标，为1则以1月为横轴最小坐标
		{
			axis.setDateFormatOverride(new SimpleDateFormat("dd"));// 设置时间格式
			axis.setTickUnit(new DateTickUnit(DateTickUnit.DAY, 1));
		} else {
			axis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM"));
			axis.setTickUnit(new DateTickUnit(DateTickUnit.MONTH, 1));
		}
		axis.setLabelFont(new Font("黑体", Font.TRUETYPE_FONT, 12));
		axis.setAutoTickUnitSelection(false);// 时间轴的数据标签是否自动确
		// 下面这一块是用来设置轴的时间间隔的，可能会出现，出错时可以简单设处日期就行了
		int a = 1;
		a = (int) (daycount / 10) + 1;
		if (a == 0)
			a = 1;
		// axis.setVerticalTickLabels(true);
		NumberAxis numberaxis = (NumberAxis) plot.getRangeAxis();
		numberaxis.setAxisLineVisible(false);// 是否显示纵坐标
		numberaxis.setTickMarksVisible(false);// 是否显示坐标标尺
		numberaxis.setAutoRangeIncludesZero(false);// 是否自动包含0起点? 默认为true
		numberaxis.setAutoTickUnitSelection(false);// 数据轴的数据标签是否自动确定
		// numberaxis.setRange(2.000D,4.400D);
		numberaxis.setTickUnit(new NumberTickUnit(1D));
		int space_y = (int) ((highValue_Y * 1.1 - minValue_Y * 0.9) / 10);
		if (space_y == 0)
			space_y = 1;
		System.out.println("spacespace sapce space" + space_y);
		numberaxis.setTickUnit(new NumberTickUnit(space_y));// 设置刻度显示的密度
		numberaxis.setAutoRangeMinimumSize(2D);
		return chart;

	}

	/**
	 * Creates a dataset, consisting of two series of monthly data.
	 * 
	 * @return The dataset.
	 */
	public static XYDataset createDataset() throws Exception {

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
				s1 = new TimeSeries("入场折线", Day.class);
				s2 = new TimeSeries("出场折线", Day.class);
			} else {

				// 传进DAO层的数据是年月，数据线以月的单位
				java.sql.Date date = java.sql.Date.valueOf(timefrom);
				java.sql.Date date_t = java.sql.Date.valueOf(timeto);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String newdate_from = sdf.format(date);
				String timefrom_temp = newdate_from.substring(0, 7);
				// System.out.println("jie取后的时间："+timefrom);
				String newdate_to = sdf.format(date_t);
				String timeto_temp = newdate_to.substring(0, 7);// 截取后的时间格式为“2010-05”“2010-11”
				// System.out.println(timefrom+"改后" +""+timeto);
				chart = tdao.getCarRecodebyTimearea_month(timefrom_temp, timeto_temp, 1);
				chart_qu = tdao.getCarRecodebyTimearea_month(timefrom_temp, timeto_temp, 0);
				s1 = new TimeSeries("入场折线", Month.class);
				s2 = new TimeSeries("出场折线", Month.class);
			}

			s1.clear();
			Date d, min_r = null, min_c = null, max_r = null, max_c = null;
			double h;
			if (chart != null && chart.length > 0) { // 数据不为空的处理
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
			return dataset;

		} catch (Exception e) {
			e.printStackTrace();
			return dataset;
		}
	}

	public static JPanel createDemoPanel() {
		JFreeChart chart;
		ChartPanel panel = null;
		try {
			chart = createChartQu(createDataset());
			panel = new ChartPanel(chart);
			panel.setFillZoomRectangle(true);
			panel.setMouseWheelEnabled(true);
			return panel;
		} catch (Exception e) {
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
		ParkingSystemChartQu demo = new ParkingSystemChartQu("Time Series Chart Demo 1", "2011-10-25", "2011-12-25", 0);
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);
	}
}
