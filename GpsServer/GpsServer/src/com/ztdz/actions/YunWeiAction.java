package com.ztdz.actions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTransactionManager;

import com.opensymphony.xwork2.ActionSupport;
import com.ztdz.pojo.TAccount;
import com.ztdz.pojo.TArea;
import com.ztdz.pojo.TCarInfo;
import com.ztdz.pojo.TLog;
import com.ztdz.pojo.TOrgainzation;
import com.ztdz.pojo.TTempPosition;
import com.ztdz.pojo.TTerminal;
import com.ztdz.pojo.TUser;
import com.ztdz.service.impl.AccountServiceImpl;
import com.ztdz.service.impl.AreaServiceImpl;
import com.ztdz.service.impl.CarInfoServiceImpl;
import com.ztdz.service.impl.LogServiceImpl;
import com.ztdz.service.impl.OrganizationServiceImpl;
import com.ztdz.service.impl.TempPositionServiceImpl;
import com.ztdz.service.impl.TerminalServiceImpl;
import com.ztdz.service.impl.UserServiceImpl;
import com.ztdz.tools.Page;

/**
 * 该action服务于 运维用户请求的特殊响应
 * 
 * @author hujiawei
 * 
 *         2012-5-24
 */
public class YunWeiAction extends ActionSupport implements SessionAware, ServletRequestAware {
	private String value; // 搜索值
	private String option; // 搜索条件
	private int selectAreaId;
    private List<TArea> areas;
	private static final int PAGESIZE = 10;
	private Map<String, Object> session; // session 会话
	private HttpServletRequest request;// request
	private final String TARGET = "target"; // 当返回的页面是用户自定义页面的时候
											// 不返回SUCCESS,而是返回TARGET
	private String page; // 用户自定义返回页面
	private final String REDIRECT = "redirect"; // 当返回的页面是用户自定义页面的时候
												// 不返回SUCCESS,而是返回TARGET
	private String redActionName;// 重定向到的action的名字

	private TUser user; // 用户信息

	/* 与前台有关的属性字段 */
	// 基本资料
	private String oldPwd, newPwd; // 用户基本信息 密码修改

	// 分组用户管理界面
	private List<TOrgainzation> groupList;// 前台要显示的分组机构列表
	private List<TOrgainzation> jituanList;// 前台要显示的该运维所管省份的集团机构列表
	private int orgId;// 分组用户的机构id，这个是用于编辑，删除
	private int selectOrgId;// 上面分组机构的集团机构id，这个是用于编辑时选择上一级集团机构
	private TOrgainzation orgainzation;// 进行编辑的分组机构
	private TOrgainzation jituan;// 上面分组机构的集团机构

	// 分组用户的账号管理界面
	private Set<TUser> groupUserList;// 前台要显示的分组机构中的账户列表
	private int groupUserId;// 分组用户的账户id，这个是用于编辑，删除
	private TUser groupUser;// 分组用户的账户，把它提出来不用user是怕两者搞混了

	// 日志管理界面
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");// 日期格式实例
	private List<TLog> logList;// 前台要显示的日志列表

	// 服务费管理界面
	private TAccount account;// 前台编辑的账单
	private List<TAccount> accountList;// 账单列表

	private int terminalId;// 进行处理的终端编号
	private TTerminal terminal;// 进行编辑的终端
	private List<TTerminal> terminalList;// 终端列表

	private Date serviceStartDate;// 终端的服务起始时间
	private Date serviceEndDate;// 终端的服务结束时间

	private int serviceYear;// 充值年数

	// 数据报表管理
	private int searchWeiyi;// 唯一搜索的条件
	private String searchTerminalNum;// 终端编号 或者 手机号
	private Date searchFromDate;// 开始时间
	private Date searchToDate;// 结束时间
	private int onoff;// 在线状态
	private int carType;// 目标类型
	private int searchGroupId;// 搜索的所属的分组机构
	private List<TTerminal> terminalExcelList;// 结果列表
	private List<TCarInfo> carInfoList;// 车类型列表
	private String cols;// 选中的要导出的列，用数字和逗号组成
	private InputStream excelStream; // excel输入流

	// 两个与时间有关的属性，这两个常用于搜索中
	private Date searchStartDate;
	private Date searchEndDate;

	// 与搜索有关的属性 --- 这个是action内部的属性，不用get和set
	private boolean isSearching = false;// 这个属性表示是否当前的列表是处于搜索结果中，因为如果不设置的话那么搜索结果翻页无效
	private String searchString;// 要搜索的"名称"

	// 分页有关的数据 ---- TODO：这几个数据之间还存在着一些干扰！
	private int total;// 总数
	private int pageIndex;// 当前页
	private int pages;// 页面总数

	/**
	 * 由spring 完成服务注入
	 */
	private AreaServiceImpl areaService;
	private OrganizationServiceImpl organizationService;
	private UserServiceImpl userService;
	private LogServiceImpl logService;
	private AccountServiceImpl accountService;
	private TerminalServiceImpl terminalService;
	private CarInfoServiceImpl carInfoService;
	private TempPositionServiceImpl tempPositionService;

	private HibernateTransactionManager txManager;// 事务管理
	private String sim;// 终端删除

	// ------------------------------------基本资料----------------------------------------//

	/**
	 * 查询运维用户基本资料
	 * 
	 * @return
	 */
	public String basicInfo() {
		user = (TUser) session.get("user");// 直接从会话中获取
		return SUCCESS;
	}

	/**
	 * 保存运维用户基本资料
	 * 
	 * @return
	 */
	public String saveBasicInfo() {
		user = (TUser) session.get("user");
		if (oldPwd == null || "".equals(oldPwd) || !user.getPwd().equals(oldPwd)) {
			request.setAttribute("result", "旧密码输入错误！");
			page = "YunWei_basicInfo";
			return TARGET;
		}
		// TODO:如果错误，跳到错误页面
		if (newPwd == null || "".equals(newPwd)) {
			request.setAttribute("result", "新密码不能为空！");
			page = "YunWei_basicInfo";
			return TARGET;
		}
		user.setPwd(newPwd);
		userService.updateUser(user);
		request.setAttribute("result", "修改成功！");
		logService.addLog(new TLog(user, "【" + user.getName() + "】" + "修改了基本资料", new Date()));
		page = "YunWei_basicInfo";
		return TARGET;
	}

	// ------------------------------------分组用户管理----------------------------------------//

	/**
	 * 进入分组用户管理界面
	 * 
	 * @return
	 */
	public String groupManager() {
		isSearching = false;// 每次遇到xxxManager方法都要将isSearching置为false，然后将搜索的字符串置为空
		searchString = "";
		pageIndex = 0;
		findGroupList();
		return SUCCESS;
	}

	/**
	 * 根据输入的名称搜索分组用户
	 * 
	 * @return
	 */
	public String groupSearch() {
		// 查数据库然后调用findGroupList方法得到列表
		isSearching = true;// 每次遇到xxxSearch方法都要将isSearching置为true
		pageIndex = 0;
		findGroupList();
		return TARGET;
	}

	/**
	 * 查找当前运维用户管理下的分组机构（分页）
	 * 
	 * @return
	 */
	public String findGroupList() {
		user = (TUser) session.get("user");
		// 得到总记录数
		if (isSearching) {
			if (searchString.equalsIgnoreCase("")) {
				total = organizationService.getCountAllGroupsByOM(user.getTOrgainzation().getTArea().getAreaId());
			} else {
				total = organizationService.getCountMohuByYunwei(searchString, user.getTOrgainzation().getTArea().getAreaId());
			}
		} else {
			total = organizationService.getCountAllGroupsByOM(user.getTOrgainzation().getTArea().getAreaId());
		}
		// 得到总页数
		if (total % PAGESIZE == 0) {
			pages = total / PAGESIZE;
		} else {
			pages = total / PAGESIZE + 1;
		}
		// 得到当前的页索引
		if (pageIndex < 1)
			pageIndex = 1;
		if (pageIndex > pages)
			pageIndex = pages;
		// 得到该页显示的记录
		if (isSearching) {
			if (searchString.equalsIgnoreCase("")) {
				groupList = organizationService.findAllGroupsByBloc(user.getTOrgainzation().getTArea().getAreaId(), (pageIndex - 1) * PAGESIZE, PAGESIZE);
			} else {
				groupList = organizationService.findMohuByYunwei(searchString, user.getTOrgainzation().getTArea().getAreaId(), (pageIndex - 1) * PAGESIZE,
						PAGESIZE);
			}
		} else {
			groupList = organizationService.findAllGroupsByOM(user.getTOrgainzation().getTArea().getAreaId(), (pageIndex - 1) * PAGESIZE, PAGESIZE);
		}
		page = "YunWei_groupManager";
		return TARGET;
	}

	/**
	 * 编辑一个分组机构--实际只是查看分组机构
	 * 
	 * @return
	 */
	public String editGroupInfo() {
		areas = areaService.findAll();// 得到地区列表
		user = (TUser) session.get("user");
		System.out.println(orgId);
		orgainzation = organizationService.findById(orgId);
		jituan = orgainzation.getTOrgainzation();// 得到分组的上一级集团
		// if (jituanList == null) {// TODO:如果为空才去数据库中加载集团机构列表
		jituanList = organizationService.findAllBlocByAreaId(user.getTOrgainzation().getTArea().getAreaId());// 得到集团列表
		// }
		return SUCCESS;
	}

	// -----------------------------------日志管理----------------------------------------//

	/**
	 * 日志管理 日志管理不遵循前面的isSearching规律，它不处理isSearching
	 * 
	 * @return
	 */
	public String logManager() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		// calendar.add(Calendar.DATE, 1);// 今天的凌晨
		searchEndDate = calendar.getTime();
		calendar.add(Calendar.DATE, -2);// 两天前的凌晨
		searchStartDate = calendar.getTime();// 设定开始和结束时间
		pageIndex = 0;
		findLogList();
		return SUCCESS;
	}

	/**
	 * 日志搜索
	 * 
	 * @return
	 */
	public String logSearch() {
		// 通过前台来设置开始和结束时间，但是要做一些判断
		if (searchEndDate.before(searchStartDate)) {
			request.setAttribute("result", "时间设置有误！请保证结束时间在开始时间之后！");
			page = "YunWei_logManager";
			return TARGET;
		}
		pageIndex = 0;
		findLogList();
		return TARGET;
	}

	/**
	 * 得到指定时间之内的日志列表，用于分页
	 * 
	 * @return
	 */
	public String findLogList() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(searchEndDate);
		calendar.add(Calendar.DATE, 1);// 后一天的凌晨
		searchEndDate = calendar.getTime();

		user = (TUser) session.get("user");
		// 得到总记录数
		total = logService.getCountByUserIdByTimeBetween(user.getUserid(), searchStartDate, searchEndDate);
		// 得到总页数
		if (total % PAGESIZE == 0) {
			pages = total / PAGESIZE;
		} else {
			pages = total / PAGESIZE + 1;
		}
		// 得到当前的页索引
		if (pageIndex < 1)
			pageIndex = 1;
		if (pageIndex > pages)
			pageIndex = pages;
		logList = logService.findByUserIdByTimeBetween(user.getUserid(), searchStartDate, searchEndDate, (pageIndex - 1) * PAGESIZE, PAGESIZE);

		calendar.add(Calendar.DATE, -1);// 使页面显示符合人们习惯
		searchEndDate = calendar.getTime();

		page = "YunWei_logManager";
		return TARGET;
	}

	// ------------------------------ 数据报表管理 ---------------------------------------//

	// /**
	// * 数据报表管理列表
	// */
	// public String excelManager() {
	// isSearching = false;
	// searchString = "";
	// searchWeiyi = -1;// 这个很重要，进入的时候不搜索！
	// onoff = 2;
	// pageIndex = 0;// 注意这里赋值变化！为了避免前面的列表和后面的列表发生冲突
	// // 得到运维下所有的分组
	// groupList = organizationService.findAllGroupsByOM(user.getTOrgainzation().getTArea().getAreaId());
	// // 得到所有的车类型
	// carInfoList = carInfoService.findAll();
	// searchTerminalExcelList();
	// return SUCCESS;
	// }
	//
	// /**
	// * 得到搜索结果的终端列表
	// *
	// * @return
	// */
	// public String searchTerminalExcelList() {
	// terminalExcelList = new ArrayList<TTerminal>();
	// // System.out.println("searchWeiyi:" + searchWeiyi);
	// DetachedCriteria detachedCriteria = null;
	// DetachedCriteria detachedCriteria2 = null;
	// if (searchWeiyi == -1) {// 这个表示刚刚进入这个页面，还没有搜索！
	// total = 0;// 注意这里赋值变化！为了避免前面的列表和后面的列表发生冲突
	// pages = 0;
	// pageIndex = 0;// 注意，这里只是清空分页的部分，其他的部分例如搜索条件还是保留！所以，第二次以上进入这个页面时卡伊看到上次搜索的条件
	// } else {
	// // 模糊搜索
	// if (searchWeiyi == 1 || searchWeiyi == 2 || searchWeiyi == 3 || searchWeiyi == 4 || searchWeiyi == 5) {// 唯一性搜索
	//
	// detachedCriteria = getCurrentMohuCriteria();
	// detachedCriteria2 = getCurrentMohuCriteria();
	//
	// // if (searchWeiyi == 1) {// 按照 终端编号 搜索
	// // terminal = terminalService.findBySim(searchString);
	// // } else if (searchWeiyi == 2) {// 按照终端手机号搜索
	// // terminal = terminalService.findByPhone(searchString);
	// // } else if (searchWeiyi == 3) {// 车号码 -- 目标序列号
	// // terminal = terminalService.findByCarnumber(searchString);
	// // } else if (searchWeiyi == 4) {// 用户名称
	// // terminal = terminalService.findByUsername(searchString);
	// // } else if (searchWeiyi == 5) {//
	// // terminal = terminalService.findByPrincipal(searchString);
	// // }
	// // // terminal一定要是这个运维的地区中的
	// // user = (TUser) session.get("user");
	// // int areaid = user.getTOrgainzation().getTArea().getAreaId();
	// // List<TOrgainzation> groups = organizationService.findAllGroupsByOM(areaid);
	// // List<TTerminal> tempTerminals = new ArrayList<TTerminal>();
	// // for (int i = 0; i < groups.size(); i++) {
	// // tempTerminals.addAll(terminalService.findByOrg(groups.get(i).getOrgId()));
	// // }
	// // if (terminal != null && tempTerminals.contains(terminal)) {// 搜索结果如果不为null那么就添加到list中
	// // terminalExcelList.add(terminal);
	// // total = 1;
	// // pages = 1;
	// // pageIndex = 1;
	// // } else {
	// // total = 0;
	// // pages = 0;
	// // pageIndex = 0;
	// // }
	// } else {// 组合条件搜索
	// // 条件之间会相互的干扰！为了避免数据库操作的干扰！
	// // 虽然是组合条件查询，但是可能并没有给出条件值！此时就立即返回！
	// if (searchEndDate == null && searchFromDate == null && onoff == 2 && searchGroupId == 0 && carType == 0) {
	// page = "YunWei_excelManager";
	// return TARGET;
	// }
	// detachedCriteria = getCurrentCriteria();
	// detachedCriteria2 = getCurrentCriteria();
	// }
	// // 得到总记录数
	// total = terminalService.getCountByCriteria(detachedCriteria);
	// System.out.println(total);
	// // 得到总页数
	// if (total % PAGESIZE == 0) {
	// pages = total / PAGESIZE;
	// } else {
	// pages = total / PAGESIZE + 1;
	// }
	// // 得到当前的页索引
	// if (pageIndex < 1)
	// pageIndex = 1;
	// if (pageIndex > pages)
	// pageIndex = pages;
	// terminalExcelList = terminalService.findAllByCriteria(detachedCriteria2);
	// }
	// // System.out.println("list size:" + terminalExcelList.size());
	// page = "YunWei_excelManager";
	// return TARGET;
	// }
	//
	// // 得到当前的模糊查询条件
	// private DetachedCriteria getCurrentMohuCriteria() {
	// user = (TUser) session.get("user");
	// DetachedCriteria criteria = DetachedCriteria.forClass(TTerminal.class);
	// // 隐含的条件应该没有问题了
	// criteria.createAlias("TOrgainzation", "t").createAlias("t.TArea", "tt")
	// .add(Restrictions.eq("tt.areaId", user.getTOrgainzation().getTArea().getAreaId()));
	// if (searchWeiyi == 1) {// 按照 终端编号 搜索
	// // terminal = terminalService.findBySim(searchString);
	// criteria.add(Restrictions.like("sim", searchString, MatchMode.ANYWHERE));
	// } else if (searchWeiyi == 2) {// 按照终端手机号搜索
	// // terminal = terminalService.findByPhone(searchString);
	// criteria.add(Restrictions.like("phone", searchString, MatchMode.ANYWHERE));
	// } else if (searchWeiyi == 3) {// 车号码 -- 目标序列号
	// // terminal = terminalService.findByCarnumber(searchString);
	// criteria.add(Restrictions.like("carnumber", searchString, MatchMode.ANYWHERE));
	// } else if (searchWeiyi == 4) {// 用户名称
	// // terminal = terminalService.findByUsername(searchString);
	// criteria.add(Restrictions.like("username", searchString, MatchMode.ANYWHERE));
	// } else if (searchWeiyi == 5) {//
	// // terminal = terminalService.findByPrincipal(searchString);
	// criteria.add(Restrictions.like("principal", searchString, MatchMode.ANYWHERE));
	// }
	// return criteria;
	// }
	//
	// // 得到当前的条件
	// private DetachedCriteria getCurrentCriteria() {
	// DetachedCriteria detachedCriteria = DetachedCriteria.forClass(TTerminal.class);
	// user = (TUser) session.get("user");
	// // //隐含的条件
	// // detachedCriteria.createAlias("TOrgainzation.TArea", "tt").add(Restrictions.eq("tt.areaId",
	// // user.getTOrgainzation().getTArea().getAreaId()));
	// // detachedCriteria.add(Restrictions.eq("TOrgainzation.TArea.areaId", user.getTOrgainzation().getTArea().getAreaId()));
	// // detachedCriteria.createAlias("TOrgainzation", "to").createAlias("TArea", "ta").add(Restrictions.eq("to.ta.areaId",
	// // user.getTOrgainzation().getTArea().getAreaId()));
	// // TODO:隐含的条件可能还有问题
	// detachedCriteria.createAlias("TOrgainzation", "t").createAlias("t.TArea", "tt")
	// .add(Restrictions.eq("tt.areaId", user.getTOrgainzation().getTArea().getAreaId()));
	// if (searchFromDate != null) {
	// detachedCriteria.add(Expression.ge("registertime", searchFromDate));
	// }
	// if (searchEndDate != null) {
	// detachedCriteria.add(Expression.le("registertime", searchEndDate));
	// }
	// if (onoff != 2) {
	// detachedCriteria.add(Restrictions.eq("netstatus", onoff));
	// }
	// if (searchGroupId != 0) {
	// // 其他表的主键
	// detachedCriteria.add(Restrictions.eq("TOrgainzation.orgId", searchGroupId));
	// }
	// if (carType != 0) {
	// // 其他表的非主键
	// detachedCriteria.createAlias("TCarInfo", "carInfo").add(Restrictions.eq("carInfo.carTypeId", carType));
	// }
	// return detachedCriteria;
	// }
	//
	// // 根据选中的列导出数据
	// public String excelExport() {
	// String[] colValues = cols.split(",");
	// if (colValues.length == 0) {
	// page = "YunWei_excelManager";
	// return TARGET;
	// }
	// // /////////////////////////////// 创建Excel表开始 /////////////////////////////////////////////
	// jxl.write.Label label;// label
	// WritableWorkbook workbook;// 工作簿
	// ByteArrayOutputStream out = null;// 字节输出流
	// try {
	// out = new ByteArrayOutputStream();
	// // 创建可写入的 Excel工作薄
	// workbook = Workbook.createWorkbook(out);
	// // 创建Excel工作表
	// WritableSheet sheet = workbook.createSheet("终端信息统计", 0);
	// // 标题
	// String[] title = { "ID", "终端编号", "目标序列号", "目标类型", "入网时间", "服务结束时间", "债券责任人", "所属集团", "所属分组", "定位信息", "工作状态", "电瓶电压", "信号强度", "离线时间"
	// };
	// boolean[] t = new boolean[title.length];
	// for (int i = 0; i < title.length; i++) {
	// if (Integer.parseInt(colValues[i]) == 1) {
	// t[i] = true;
	// } else {
	// t[i] = false;
	// }
	// }
	// int index = 0;
	// // 创建label，其中保存标题，并将label添加到sheet中
	// for (int i = 0; i < title.length; i++) {
	// if (t[i]) {
	// label = new jxl.write.Label(index, 0, title[i]);// 第一行，index列
	// sheet.addCell(label);
	// sheet.setColumnView(index, 25);// 不知道 index列的宽度
	// index++;
	// }
	// }
	// // 写入数据！
	// // DetachedCriteria detachedCriteria = getCurrentCriteria();
	// DetachedCriteria detachedCriteria = null;
	//
	// if (searchWeiyi == 1 || searchWeiyi == 2 || searchWeiyi == 3 || searchWeiyi == 4 || searchWeiyi == 5) {
	// detachedCriteria = getCurrentMohuCriteria();
	// } else {
	// detachedCriteria = getCurrentCriteria();
	// }
	//
	// terminalExcelList = terminalService.findAllByCriteria(detachedCriteria);
	// for (int i = 0; i < terminalExcelList.size(); i++) {
	// index = 0;
	// terminal = terminalExcelList.get(i);
	// if (t[0])
	// sheet.addCell(new jxl.write.Label(index++, i + 1, String.valueOf(terminal.getId())));
	// if (t[1])
	// sheet.addCell(new jxl.write.Label(index++, i + 1, terminal.getSim()));
	// if (t[2])
	// sheet.addCell(new jxl.write.Label(index++, i + 1, terminal.getCarnumber()));
	// if (t[3])
	// sheet.addCell(new jxl.write.Label(index++, i + 1, terminal.getTCarInfo().getTypeName()));
	// if (t[4])
	// if (terminal.getRegistertime() != null) {
	// sheet.addCell(new jxl.write.Label(index++, i + 1, dateFormat.format(terminal.getRegistertime())));
	// } else {
	// sheet.addCell(new jxl.write.Label(index++, i + 1, ""));
	// }
	// if (t[5])
	// if (terminal.getRegistertime() != null) {
	// sheet.addCell(new jxl.write.Label(index++, i + 1, dateFormat.format(terminal.getEndTime())));
	// } else {
	// sheet.addCell(new jxl.write.Label(index++, i + 1, ""));
	// }
	// if (t[6])
	// sheet.addCell(new jxl.write.Label(index++, i + 1, terminal.getPrincipal()));
	// if (t[7])
	// sheet.addCell(new jxl.write.Label(index++, i + 1, terminal.getTOrgainzation().getTOrgainzation().getName()));
	// if (t[8])
	// sheet.addCell(new jxl.write.Label(index++, i + 1, terminal.getTOrgainzation().getName()));
	// if (t[9])
	// if (terminal.getTTempPositions().get(0).getLocationModel() != null) {
	// sheet.addCell(new jxl.write.Label(index++, i + 1, (terminal.getTTempPositions().get(0)).getLocationModel()));
	// } else {
	// sheet.addCell(new jxl.write.Label(index++, i + 1, ""));
	// }
	// if (t[10]) {
	// sheet.addCell(new jxl.write.Label(index++, i + 1, terminal.getStatus()));
	// }
	// if (t[11])
	// sheet.addCell(new jxl.write.Label(index++, i + 1, terminal.getElepress() == null ? "" : terminal.getElepress()));
	// if (t[12])
	// sheet.addCell(new jxl.write.Label(index++, i + 1, String.valueOf(terminal.getSignal()).equalsIgnoreCase("null") ? "" : String
	// .valueOf(terminal.getSignal())));
	// if (t[13])
	// sheet.addCell(new jxl.write.Label(index++, i + 1, terminal.getRemark() == null ? "" : terminal.getRemark()));
	// }
	// workbook.write();
	// workbook.close();
	// out.close();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// excelStream = new ByteArrayInputStream(out.toByteArray());
	// return "excel";
	// }

	// ------------------------------- 费用充值 --------------------------------------------//
	/**
	 * 分组用户费用管理
	 */
	public String groupExpenseManager() {
		user = (TUser) session.get("user");
		isSearching = false;
		searchString = "";
		pageIndex = 0;
		findGroupExpenseList();
		return SUCCESS;
	}

	/**
	 * 费用管理中的分组用户查询
	 * 
	 * @return
	 */
	public String groupExpenseSearch() {
		isSearching = true;
		pageIndex = 0;
		findGroupExpenseList();
		return TARGET;
	}

	/**
	 * 用于分页中的分组用户费用管理
	 * 
	 * @return
	 */
	public String findGroupExpenseList() {
		// 得到总记录数
		if (isSearching) {
			if (searchString.equalsIgnoreCase("")) {
				total = organizationService.getCountAllGroupsByOM(user.getTOrgainzation().getTArea().getAreaId());
			} else {
				total = organizationService.getCountMohuByYunwei(searchString, user.getTOrgainzation().getTArea().getAreaId());
			}
			// total = organizationService.getCountMohuByYunwei(searchString, user.getTOrgainzation().getTArea().getAreaId());
		} else {
			total = organizationService.getCountAllGroupsByOM(user.getTOrgainzation().getTArea().getAreaId());
		}
		// 得到总页数
		if (total % PAGESIZE == 0) {
			pages = total / PAGESIZE;
		} else {
			pages = total / PAGESIZE + 1;
		}
		// 得到当前的页索引
		if (pageIndex < 1)
			pageIndex = 1;
		if (pageIndex > pages)
			pageIndex = pages;
		System.out.println("pageIndex=" + pageIndex);
		// 得到该页显示的记录
		if (isSearching) {
			// groupList = organizationService
			// .findMohuByYunwei(searchString, user.getTOrgainzation().getTArea().getAreaId(), (pageIndex - 1) * PAGESIZE, PAGESIZE);
			if (searchString.equalsIgnoreCase("")) {
				groupList = organizationService.findAllGroupsByBloc(user.getTOrgainzation().getTArea().getAreaId(), (pageIndex - 1) * PAGESIZE, PAGESIZE);
			} else {
				groupList = organizationService.findMohuByYunwei(searchString, user.getTOrgainzation().getTArea().getAreaId(), (pageIndex - 1) * PAGESIZE,
						PAGESIZE);
			}
		} else {
			groupList = organizationService.findAllGroupsByOM(user.getTOrgainzation().getTArea().getAreaId(), (pageIndex - 1) * PAGESIZE, PAGESIZE);
		}
		page = "YunWei_groupExpenseManager";
		return TARGET;
	}

	// ///////////////////////////// 分组用户的充值 /////////////////////////////////
	/**
	 * 进入组用户充值界面，这里并没有充值
	 * 
	 * @return
	 */
	public String groupExpenseAdd() {
		orgainzation = organizationService.findById(orgId);
		account = null;// 应该不需要new，如果不充值的话就产生了多余的对象
		return SUCCESS;
	}

	/**
	 * 给组用户充值
	 * 
	 * @return
	 */
	public String addGroupExpense() {
		TOrgainzation pOrgainzation = orgainzation.getTOrgainzation();
		if (pOrgainzation.getBalance() < account.getExpense()) {// 集团机构的余额和充值金额比较，如果不足
			request.setAttribute("result", "集团机构的余额不足！");
			page = "YunWei_groupExpenseAdd";// 重新输入，所以是重定向
			return TARGET;
		}
		if (orgainzation.getBalance() + account.getExpense() > 100000) {// 集团机构的余额和充值金额比较，如果不足
			request.setAttribute("result", "账户余额足够，不需要充值！");
			page = "JiTuan_groupExpenseAdd";// 重新输入，所以是重定向
			return TARGET;
		}
		// 集团减，分组加，不要忘记update
		pOrgainzation.setBalance(pOrgainzation.getBalance() - account.getExpense());
		orgainzation.setBalance(orgainzation.getBalance() + account.getExpense());
		account.setTOrgainzation(orgainzation);
		account.setPaider(pOrgainzation.getName());
		account.setPaiddate(new Date());
		String remark = "分组机构【" + orgainzation.getName() + "】充值了" + account.getExpense() + "元";
		account.setRemark(remark);// 添加备注
		organizationService.updateOrganization(pOrgainzation);// 把提交语句都放在后面
		organizationService.updateOrganization(orgainzation);
		accountService.addAccount(account);
		// 添加日志
		logService.addLog(new TLog(user, remark, new Date()));
		request.setAttribute("result", "充值成功！");
		page = "YunWei_groupExpenseAdd";
		return TARGET;
	}

	// ///////////////////////////// 分组用户的充值明细管理 /////////////////////////////////
	/**
	 * 分组用户的充值明细，可以查询
	 * 
	 * @return
	 */
	public String groupExpenseDetail() {
		// 设置起始时间和结束时间
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		// calendar.add(Calendar.DATE, 1);// 这种方式可以，得到的是下一天
		searchEndDate = calendar.getTime();
		calendar.add(Calendar.DATE, -2);// 前三天的凌晨
		searchStartDate = calendar.getTime();
		pageIndex = 0;
		findGroupExpenseDetailList();
		return SUCCESS;
	}

	/**
	 * 分组用户的充值查询
	 * 
	 * @return
	 */
	public String groupExpenseDetailSearch() {
		// 这里得到了搜索的起始时间和结束时间
		pageIndex = 0;
		findGroupExpenseDetailList();
		return TARGET;
	}

	/**
	 * 得到账单列表<br/>
	 * 单独将这个方法列出来是为了分页的需要，并且这个方法可以重用
	 * 
	 * @return
	 */
	public String findGroupExpenseDetailList() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(searchEndDate);
		calendar.add(Calendar.DATE, 1);// 后一天的凌晨
		searchEndDate = calendar.getTime();
		// 得到总记录数
		total = accountService.getCountByOrgIdByTime(orgId, searchStartDate, searchEndDate);
		// 得到总页数
		if (total % PAGESIZE == 0) {
			pages = total / PAGESIZE;
		} else {
			pages = total / PAGESIZE + 1;
		}
		// 得到当前的页索引
		if (pageIndex < 1)
			pageIndex = 1;
		if (pageIndex > pages)
			pageIndex = pages;
		// 得到该页显示的记录
		accountList = accountService.findByOrgIdByTime(orgId, searchStartDate, searchEndDate, (pageIndex - 1) * PAGESIZE, PAGESIZE);
		calendar.add(Calendar.DATE, -1);// 使页面显示符合人们习惯
		searchEndDate = calendar.getTime();

		page = "YunWei_groupExpenseDetail";
		return TARGET;
	}

	// /////////////////------ 分组机构的终端管理------////////////////////////////
	/**
	 * 终端列表管理
	 */
	public String terminalManager() {
		orgainzation = organizationService.findById(orgId);
		isSearching = false;
		searchString = "";
		pageIndex = 0;
		findTerminalList();
		return TARGET;
	}

	/**
	 * 终端查询
	 * 
	 * @return
	 */
	public String terminalSearch() {
		if ("1".equals(option)) { // 机器编号
			total = terminalService.getCountMohuByOrgAndCarNumber(value,
					orgId);
		} else if ("2".equals(option)) {// 联系人
			total = terminalService
					.getCountMohuByOrgAndUserName(value, orgId);
		} else if ("3".equals(option)) { // 终端号码
			total = terminalService.getCountMohuByOrgAndSim(value, orgId);
		}
		// 得到总页数
		if (total % Page.DEFAULT_PAGE_SIZE == 0) {
			pages = total / Page.DEFAULT_PAGE_SIZE;
		} else {
			pages = total / Page.DEFAULT_PAGE_SIZE + 1;
		}
		// 得到当前的页索引
		if (pageIndex < 1)
			pageIndex = 1;
		if (pageIndex > pages)// 暂时不判断
			pageIndex = pages;

		if ("1".equals(option)) { // 机器编号
			terminalList = terminalService.findMohuByOrgAndCarNumber(value,
					orgId, (pageIndex - 1) * Page.DEFAULT_PAGE_SIZE,
					Page.DEFAULT_PAGE_SIZE);
		} else if ("2".equals(option)) {// 联系人
			terminalList = terminalService.findMohuByOrgAndUserName(value,
					orgId, (pageIndex - 1) * Page.DEFAULT_PAGE_SIZE,
					Page.DEFAULT_PAGE_SIZE);
		} else if ("3".equals(option)) { // 终端号码
			terminalList = terminalService.findMohuByOrgAndSim(value, orgId,
					(pageIndex - 1) * Page.DEFAULT_PAGE_SIZE,
					Page.DEFAULT_PAGE_SIZE);
		}
		page = "YunWei_terminalSearchResult";
		return TARGET;
	}

	/**
	 * 跳往添加终端用户界面
	 * 
	 * @@return
	 */
	public String addTerminal() {
		areas = areaService.findAll();// 得到地区列表
		carInfoList = carInfoService.findAll();
		terminal = new TTerminal();
		return SUCCESS;
	}

	/**
	 * 保存终端用户信息
	 * 
	 * @@return
	 */
	public String saveTerminal() {
		TTerminal temp = terminalService.findBySim(terminal.getSim());
		if (temp != null) {
			request.setAttribute("result", "终端编号已存在!");
			page = "YunWei_addTerminal";
			return TARGET;
		}
		temp = terminalService.findByPhone(terminal.getPhone());
		if (temp != null) {
			request.setAttribute("result", "该SIM卡号已经存在!");
			page = "YunWei_addTerminal";
			return TARGET;
		}
		TArea t1= areaService.findById(selectAreaId);
		terminal.setTArea(t1);
		terminal.setRegistertime(new Date());
		TUser user = (TUser) session.get("user");
		terminal.setTOrgainzation(orgainzation);
		terminalService.addTerminal(terminal);

		// terminal = terminalService.findBySim(terminal.getSim());
		TTempPosition position = new TTempPosition();
		position.setSim(terminal.getSim());
		position.setTTerminal(terminal);
		position.setLatitude(-1);
		position.setLongitude(-1);
		position.setLocationModel("");
		position.setPTime(new Date());
		tempPositionService.addTempPosition(position);
		page = "YunWei_addTerminal";
		request.setAttribute("result", "新增终端用户成功!");
		user = (TUser) session.get("user");
		logService.addLog(new TLog(user, "【" + user.getName() + "】" + "新增了终端【" + terminal.getSim() + "】", new Date()));
		return TARGET;
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * 用于分页中的终端用户费用管理 终端的模糊搜索还没有底层支持，终端应该是根据sim卡号搜索
	 * 
	 * @return
	 */
	public String findTerminalList() {
		// 得到总记录数
		if (isSearching) {
			if (searchString.equalsIgnoreCase("")) {
				total = terminalService.getCountByOrg(orgId);
			} else {
				total = terminalService.getCountMohuByOrgAndSim(searchString, orgId);
			}
		} else {
			total = terminalService.getCountByOrg(orgId);
		}
		// 得到总页数
		if (total % PAGESIZE == 0) {
			pages = total / PAGESIZE;
		} else {
			pages = total / PAGESIZE + 1;
		}
		// 得到当前的页索引
		if (pageIndex < 1)
			pageIndex = 1;
		if (pageIndex > pages)
			pageIndex = pages;
		// 得到该页显示的记录
		if (isSearching) {
			if (searchString.equalsIgnoreCase("")) {
				terminalList = terminalService.findByOrg(orgId, (pageIndex - 1) * PAGESIZE, PAGESIZE);
			} else {
				terminalList = terminalService.findMohuByOrgAndSim(searchString, orgId, (pageIndex - 1) * PAGESIZE, PAGESIZE);
			}
		} else {
			terminalList = terminalService.findByOrg(orgId, (pageIndex - 1) * PAGESIZE, PAGESIZE);
		}
		page = "YunWei_terminalManager";
		return TARGET;
	}

	/**
	 * 删除终端用户信息
	 * 
	 * @return
	 */
	public String deleteTerminal() {
		terminalService.delTerminalBySim(sim);
		user = (TUser) session.get("user");
		logService.addLog(new TLog(user, "【" + user.getName() + "】" + "删除了终端【" + sim + "】", new Date()));
		return terminalManager();
	}

	// //////////////////////////////// 分组用户下的终端用户的费用管理 ///////////////////////////////
	/**
	 * 分组用户下的终端用户的费用管理
	 */
	public String terminalExpenseManager() {
		isSearching = false;
		searchString = "";
		pageIndex = 0;
		findTerminalExpenseList();
		return SUCCESS;
	}

	/**
	 * 编辑终端用户信息
	 * 
	 * @return
	 */
	public String editTerminal() {
		// 获取终端信息
		areas = areaService.findAll();// 得到地区列表
		terminal = terminalService.findBySim(sim);
		carInfoList = carInfoService.findAll();
		return SUCCESS;
	}

	/**
	 * 更新终端用户信息
	 * 
	 * @return
	 */
	public String updateTerminal() {
		TArea t1= areaService.findById(selectAreaId);
		terminal.setTArea(t1);
		terminalService.updateTerminal(terminal);
		page = "YunWei_editTerminal";
		user = (TUser) session.get("user");
		logService.addLog(new TLog(user, "【" + user.getName() + "】" + "修改了终端【" + terminal.getSim() + "】", new Date()));
		request.setAttribute("result", "修改成功！");
		return TARGET;
	}

	/**
	 * 费用管理中的终端用户查询
	 * 
	 * @return
	 */
	public String terminalExpenseSearch() {
		isSearching = true;
		if ("1".equals(option)) { // 机器编号
			total = terminalService.getCountMohuByOrgAndCarNumber(value,
					orgId);
		} else if ("2".equals(option)) {// 联系人
			total = terminalService
					.getCountMohuByOrgAndUserName(value, orgId);
		} else if ("3".equals(option)) { // 终端号码
			total = terminalService.getCountMohuByOrgAndSim(value, orgId);
		}
		// 得到总页数
		if (total % Page.DEFAULT_PAGE_SIZE == 0) {
			pages = total / Page.DEFAULT_PAGE_SIZE;
		} else {
			pages = total / Page.DEFAULT_PAGE_SIZE + 1;
		}
		// 得到当前的页索引
		if (pageIndex < 1)
			pageIndex = 1;
		if (pageIndex > pages)// 暂时不判断
			pageIndex = pages;

		if ("1".equals(option)) { // 机器编号
			terminalList = terminalService.findMohuByOrgAndCarNumber(value,
					orgId, (pageIndex - 1) * Page.DEFAULT_PAGE_SIZE,
					Page.DEFAULT_PAGE_SIZE);
		} else if ("2".equals(option)) {// 联系人
			terminalList = terminalService.findMohuByOrgAndUserName(value,
					orgId, (pageIndex - 1) * Page.DEFAULT_PAGE_SIZE,
					Page.DEFAULT_PAGE_SIZE);
		} else if ("3".equals(option)) { // 终端号码
			terminalList = terminalService.findMohuByOrgAndSim(value, orgId,
					(pageIndex - 1) * Page.DEFAULT_PAGE_SIZE,
					Page.DEFAULT_PAGE_SIZE);
		}
		page = "YunWei_terminalExpenseSearchResult";
		return TARGET;
	}

	/**
	 * 用于分页中的终端用户费用管理 终端的模糊搜索还没有底层支持，终端应该是根据sim卡号搜索
	 * 
	 * @return
	 */
	public String findTerminalExpenseList() {
		// 得到总记录数
		if (isSearching) {
			total = terminalService.getCountMohuByOrgAndSim(searchString, orgId);
		} else {
			total = terminalService.getCountByOrg(orgId);
		}
		// 得到总页数
		if (total % PAGESIZE == 0) {
			pages = total / PAGESIZE;
		} else {
			pages = total / PAGESIZE + 1;
		}
		// 得到当前的页索引
		if (pageIndex < 1)
			pageIndex = 1;
		if (pageIndex > pages)
			pageIndex = pages;
		// 得到该页显示的记录
		if (isSearching) {
			terminalList = terminalService.findMohuByOrgAndSim(searchString, orgId, (pageIndex - 1) * PAGESIZE, PAGESIZE);
		} else {
			terminalList = terminalService.findByOrg(orgId, (pageIndex - 1) * PAGESIZE, PAGESIZE);
		}
		page = "YunWei_terminalExpenseManager";
		return TARGET;
	}

	// ///////////////////////////// 终端用户的充值 /////////////////////////////////
	/**
	 * 进入终端用户充值界面，这里并没有充值
	 * 
	 * @return
	 */
	public String terminalExpenseAdd() {
		System.out.println("terminal Id = " + terminalId);
		terminal = terminalService.findById(terminalId);
		account = null;// 应该不需要new，如果不充值的话就产生了多余的对象
		return SUCCESS;
	}

	/**
	 * 给终端用户充值
	 * 
	 * @return
	 */
	public String addTerminalExpense() {
		if (serviceYear == 0) {
			request.setAttribute("result", "请选择充值年数！");
			page = "YunWei_terminalExpenseAdd";
			return TARGET;
		}
		TOrgainzation pOrgainzation = organizationService.findById(orgId);
		if (pOrgainzation.getBalance() < pOrgainzation.getFeestandard() * serviceYear) {// 分组机构的余额和服务年费比较，如果不足
			request.setAttribute("result", "分组机构的余额不足！");
			page = "YunWei_terminalExpenseAdd";
			return TARGET;
		}
		// 分组减，不要忘记update
		pOrgainzation.setBalance(pOrgainzation.getBalance() - pOrgainzation.getFeestandard() * serviceYear);
		organizationService.updateOrganization(pOrgainzation);// 提交语句
		// 充值成功，更新terminal
		Date today = new Date();
		if (terminal.getEndTime().before(today)) {// 服务已经结束了
			terminal.setStartTime(today);
		} /*
		 * else { terminal.setStartTime(terminal.getEndTime()); }
		 */
		// 根据输入年份得到结束时间
		Date toDate = terminal.getStartTime();
		Date newDate = new Date((toDate.getYear() + serviceYear), toDate.getMonth(), toDate.getDate());
		terminal.setEndTime(newDate);
		terminalService.updateTerminal(terminal);

		// 插入一条账单
		account.setTOrgainzation(pOrgainzation);// 给终端充值时账单中显示的还是分组用户
		account.setPaiddate(today);
		account.setPaider(pOrgainzation.getName());
		account.setExpense(pOrgainzation.getFeestandard() * serviceYear);
		account.setRemark("终端【" + terminal.getSim() + "】充值了" + serviceYear + "年");// 添加备注
		accountService.addAccount(account);// TODO：这里报错！

		// 插入一条日志
		String remark = "【" + user.getName() + "】为终端【" + terminal.getSim() + "】充值了，服务时间是【" + terminal.getStartTime().toLocaleString() + "】-【"
				+ terminal.getEndTime().toLocaleString() + "】";
		logService.addLog(new TLog(user, remark, today));

		request.setAttribute("result", "充值成功！");
		page = "YunWei_terminalExpenseAdd";
		return TARGET;
	}

	// 终端用户的充值查询和充值明细没有！

	// //////////////set and getters./////////////////
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public int getSelectOrgId() {
		return selectOrgId;
	}

	public void setSelectOrgId(int selectOrgId) {
		this.selectOrgId = selectOrgId;
	}

	public TUser getGroupUser() {
		return groupUser;
	}

	public void setGroupUser(TUser groupUser) {
		this.groupUser = groupUser;
	}

	public int getGroupUserId() {
		return groupUserId;
	}

	public List<TLog> getLogList() {
		return logList;
	}

	public void setLogList(List<TLog> logList) {
		this.logList = logList;
	}

	public void setGroupUserId(int groupUserId) {
		this.groupUserId = groupUserId;
	}

	public String getRedActionName() {
		return redActionName;
	}

	public void setRedActionName(String redActionName) {
		this.redActionName = redActionName;
	}

	public UserServiceImpl getUserService() {
		return userService;
	}

	public void setUserService(UserServiceImpl userService) {
		this.userService = userService;
	}

	public LogServiceImpl getLogService() {
		return logService;
	}

	public void setLogService(LogServiceImpl logService) {
		this.logService = logService;
	}

	public TUser getUser() {
		return user;
	}

	public void setUser(TUser user) {
		this.user = user;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public List<TOrgainzation> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<TOrgainzation> groupList) {
		this.groupList = groupList;
	}

	public Set<TUser> getGroupUserList() {
		return groupUserList;
	}

	public int getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(int terminalId) {
		this.terminalId = terminalId;
	}

	public void setGroupUserList(Set<TUser> groupUserList) {
		this.groupUserList = groupUserList;
	}

	public OrganizationServiceImpl getOrganizationService() {
		return organizationService;
	}

	public void setOrganizationService(OrganizationServiceImpl organizationService) {
		this.organizationService = organizationService;
	}

	public String getOldPwd() {
		return oldPwd;
	}

	public Date getSearchStartDate() {
		return searchStartDate;
	}

	public void setSearchStartDate(Date searchStartDate) {
		this.searchStartDate = searchStartDate;
	}

	public Date getSearchEndDate() {
		return searchEndDate;
	}

	public void setSearchEndDate(Date searchEndDate) {
		this.searchEndDate = searchEndDate;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public TOrgainzation getJituan() {
		return jituan;
	}

	public void setJituan(TOrgainzation jituan) {
		this.jituan = jituan;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public AreaServiceImpl getAreaService() {
		return areaService;
	}

	public void setAreaService(AreaServiceImpl areaService) {
		this.areaService = areaService;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public Date getServiceStartDate() {
		return serviceStartDate;
	}

	public void setServiceStartDate(Date serviceStartDate) {
		this.serviceStartDate = serviceStartDate;
	}

	public Date getServiceEndDate() {
		return serviceEndDate;
	}

	public void setServiceEndDate(Date serviceEndDate) {
		this.serviceEndDate = serviceEndDate;
	}

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public TOrgainzation getOrgainzation() {
		return orgainzation;
	}

	public void setOrgainzation(TOrgainzation orgainzation) {
		this.orgainzation = orgainzation;
	}

	public List<TOrgainzation> getJituanList() {
		return jituanList;
	}

	public void setJituanList(List<TOrgainzation> jituanList) {
		this.jituanList = jituanList;
	}

	public TAccount getAccount() {
		return account;
	}

	public void setAccount(TAccount account) {
		this.account = account;
	}

	public List<TAccount> getAccountList() {
		return accountList;
	}

	public void setAccountList(List<TAccount> accountList) {
		this.accountList = accountList;
	}

	public TTerminal getTerminal() {
		return terminal;
	}

	public void setTerminal(TTerminal terminal) {
		this.terminal = terminal;
	}

	public List<TTerminal> getTerminalList() {
		return terminalList;
	}

	public void setTerminalList(List<TTerminal> terminalList) {
		this.terminalList = terminalList;
	}

	public AccountServiceImpl getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountServiceImpl accountService) {
		this.accountService = accountService;
	}

	public TerminalServiceImpl getTerminalService() {
		return terminalService;
	}

	public void setTerminalService(TerminalServiceImpl terminalService) {
		this.terminalService = terminalService;
	}

	public HibernateTransactionManager getTxManager() {
		return txManager;
	}

	public void setTxManager(HibernateTransactionManager txManager) {
		this.txManager = txManager;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public String getSearchTerminalNum() {
		return searchTerminalNum;
	}

	public void setSearchTerminalNum(String searchTerminalNum) {
		this.searchTerminalNum = searchTerminalNum;
	}

	public Date getSearchFromDate() {
		return searchFromDate;
	}

	public void setSearchFromDate(Date searchFromDate) {
		this.searchFromDate = searchFromDate;
	}

	public Date getSearchToDate() {
		return searchToDate;
	}

	public void setSearchToDate(Date searchToDate) {
		this.searchToDate = searchToDate;
	}

	public List<TTerminal> getTerminalExcelList() {
		return terminalExcelList;
	}

	public void setTerminalExcelList(List<TTerminal> terminalExcelList) {
		this.terminalExcelList = terminalExcelList;
	}

	public CarInfoServiceImpl getCarInfoService() {
		return carInfoService;
	}

	public void setCarInfoService(CarInfoServiceImpl carInfoService) {
		this.carInfoService = carInfoService;
	}

	public List<TCarInfo> getCarInfoList() {
		return carInfoList;
	}

	public void setCarInfoList(List<TCarInfo> carInfoList) {
		this.carInfoList = carInfoList;
	}

	public int getOnoff() {
		return onoff;
	}

	public void setOnoff(int onoff) {
		this.onoff = onoff;
	}

	public int getCarType() {
		return carType;
	}

	public void setCarType(int carType) {
		this.carType = carType;
	}

	public int getSearchWeiyi() {
		return searchWeiyi;
	}

	public void setSearchWeiyi(int searchWeiyi) {
		this.searchWeiyi = searchWeiyi;
	}

	public int getSearchGroupId() {
		return searchGroupId;
	}

	public void setSearchGroupId(int searchGroupId) {
		this.searchGroupId = searchGroupId;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public String getCols() {
		return cols;
	}

	public void setCols(String cols) {
		this.cols = cols;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public TempPositionServiceImpl getTempPositionService() {
		return tempPositionService;
	}

	public void setTempPositionService(TempPositionServiceImpl tempPositionService) {
		this.tempPositionService = tempPositionService;
	}

	public int getServiceYear() {
		return serviceYear;
	}

	public void setServiceYear(int serviceYear) {
		this.serviceYear = serviceYear;
	}

	public String getSim() {
		return sim;
	}

	public void setSim(String sim) {
		this.sim = sim;
	}

	public List<TArea> getAreas() {
		return areas;
	}

	public void setAreas(List<TArea> areas) {
		this.areas = areas;
	}

	public int getSelectAreaId() {
		return selectAreaId;
	}

	public void setSelectAreaId(int selectAreaId) {
		this.selectAreaId = selectAreaId;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
