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
 * ��action������ ��ά�û������������Ӧ
 * 
 * @author hujiawei
 * 
 *         2012-5-24
 */
public class YunWeiAction extends ActionSupport implements SessionAware, ServletRequestAware {
	private String value; // ����ֵ
	private String option; // ��������
	private int selectAreaId;
    private List<TArea> areas;
	private static final int PAGESIZE = 10;
	private Map<String, Object> session; // session �Ự
	private HttpServletRequest request;// request
	private final String TARGET = "target"; // �����ص�ҳ�����û��Զ���ҳ���ʱ��
											// ������SUCCESS,���Ƿ���TARGET
	private String page; // �û��Զ��巵��ҳ��
	private final String REDIRECT = "redirect"; // �����ص�ҳ�����û��Զ���ҳ���ʱ��
												// ������SUCCESS,���Ƿ���TARGET
	private String redActionName;// �ض��򵽵�action������

	private TUser user; // �û���Ϣ

	/* ��ǰ̨�йص������ֶ� */
	// ��������
	private String oldPwd, newPwd; // �û�������Ϣ �����޸�

	// �����û��������
	private List<TOrgainzation> groupList;// ǰ̨Ҫ��ʾ�ķ�������б�
	private List<TOrgainzation> jituanList;// ǰ̨Ҫ��ʾ�ĸ���ά����ʡ�ݵļ��Ż����б�
	private int orgId;// �����û��Ļ���id����������ڱ༭��ɾ��
	private int selectOrgId;// �����������ļ��Ż���id����������ڱ༭ʱѡ����һ�����Ż���
	private TOrgainzation orgainzation;// ���б༭�ķ������
	private TOrgainzation jituan;// �����������ļ��Ż���

	// �����û����˺Ź������
	private Set<TUser> groupUserList;// ǰ̨Ҫ��ʾ�ķ�������е��˻��б�
	private int groupUserId;// �����û����˻�id����������ڱ༭��ɾ��
	private TUser groupUser;// �����û����˻����������������user�������߸����

	// ��־�������
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");// ���ڸ�ʽʵ��
	private List<TLog> logList;// ǰ̨Ҫ��ʾ����־�б�

	// ����ѹ������
	private TAccount account;// ǰ̨�༭���˵�
	private List<TAccount> accountList;// �˵��б�

	private int terminalId;// ���д�����ն˱��
	private TTerminal terminal;// ���б༭���ն�
	private List<TTerminal> terminalList;// �ն��б�

	private Date serviceStartDate;// �ն˵ķ�����ʼʱ��
	private Date serviceEndDate;// �ն˵ķ������ʱ��

	private int serviceYear;// ��ֵ����

	// ���ݱ������
	private int searchWeiyi;// Ψһ����������
	private String searchTerminalNum;// �ն˱�� ���� �ֻ���
	private Date searchFromDate;// ��ʼʱ��
	private Date searchToDate;// ����ʱ��
	private int onoff;// ����״̬
	private int carType;// Ŀ������
	private int searchGroupId;// �����������ķ������
	private List<TTerminal> terminalExcelList;// ����б�
	private List<TCarInfo> carInfoList;// �������б�
	private String cols;// ѡ�е�Ҫ�������У������ֺͶ������
	private InputStream excelStream; // excel������

	// ������ʱ���йص����ԣ�������������������
	private Date searchStartDate;
	private Date searchEndDate;

	// �������йص����� --- �����action�ڲ������ԣ�����get��set
	private boolean isSearching = false;// ������Ա�ʾ�Ƿ�ǰ���б��Ǵ�����������У���Ϊ��������õĻ���ô���������ҳ��Ч
	private String searchString;// Ҫ������"����"

	// ��ҳ�йص����� ---- TODO���⼸������֮�仹������һЩ���ţ�
	private int total;// ����
	private int pageIndex;// ��ǰҳ
	private int pages;// ҳ������

	/**
	 * ��spring ��ɷ���ע��
	 */
	private AreaServiceImpl areaService;
	private OrganizationServiceImpl organizationService;
	private UserServiceImpl userService;
	private LogServiceImpl logService;
	private AccountServiceImpl accountService;
	private TerminalServiceImpl terminalService;
	private CarInfoServiceImpl carInfoService;
	private TempPositionServiceImpl tempPositionService;

	private HibernateTransactionManager txManager;// �������
	private String sim;// �ն�ɾ��

	// ------------------------------------��������----------------------------------------//

	/**
	 * ��ѯ��ά�û���������
	 * 
	 * @return
	 */
	public String basicInfo() {
		user = (TUser) session.get("user");// ֱ�ӴӻỰ�л�ȡ
		return SUCCESS;
	}

	/**
	 * ������ά�û���������
	 * 
	 * @return
	 */
	public String saveBasicInfo() {
		user = (TUser) session.get("user");
		if (oldPwd == null || "".equals(oldPwd) || !user.getPwd().equals(oldPwd)) {
			request.setAttribute("result", "�������������");
			page = "YunWei_basicInfo";
			return TARGET;
		}
		// TODO:���������������ҳ��
		if (newPwd == null || "".equals(newPwd)) {
			request.setAttribute("result", "�����벻��Ϊ�գ�");
			page = "YunWei_basicInfo";
			return TARGET;
		}
		user.setPwd(newPwd);
		userService.updateUser(user);
		request.setAttribute("result", "�޸ĳɹ���");
		logService.addLog(new TLog(user, "��" + user.getName() + "��" + "�޸��˻�������", new Date()));
		page = "YunWei_basicInfo";
		return TARGET;
	}

	// ------------------------------------�����û�����----------------------------------------//

	/**
	 * ��������û��������
	 * 
	 * @return
	 */
	public String groupManager() {
		isSearching = false;// ÿ������xxxManager������Ҫ��isSearching��Ϊfalse��Ȼ���������ַ�����Ϊ��
		searchString = "";
		pageIndex = 0;
		findGroupList();
		return SUCCESS;
	}

	/**
	 * ����������������������û�
	 * 
	 * @return
	 */
	public String groupSearch() {
		// �����ݿ�Ȼ�����findGroupList�����õ��б�
		isSearching = true;// ÿ������xxxSearch������Ҫ��isSearching��Ϊtrue
		pageIndex = 0;
		findGroupList();
		return TARGET;
	}

	/**
	 * ���ҵ�ǰ��ά�û������µķ����������ҳ��
	 * 
	 * @return
	 */
	public String findGroupList() {
		user = (TUser) session.get("user");
		// �õ��ܼ�¼��
		if (isSearching) {
			if (searchString.equalsIgnoreCase("")) {
				total = organizationService.getCountAllGroupsByOM(user.getTOrgainzation().getTArea().getAreaId());
			} else {
				total = organizationService.getCountMohuByYunwei(searchString, user.getTOrgainzation().getTArea().getAreaId());
			}
		} else {
			total = organizationService.getCountAllGroupsByOM(user.getTOrgainzation().getTArea().getAreaId());
		}
		// �õ���ҳ��
		if (total % PAGESIZE == 0) {
			pages = total / PAGESIZE;
		} else {
			pages = total / PAGESIZE + 1;
		}
		// �õ���ǰ��ҳ����
		if (pageIndex < 1)
			pageIndex = 1;
		if (pageIndex > pages)
			pageIndex = pages;
		// �õ���ҳ��ʾ�ļ�¼
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
	 * �༭һ���������--ʵ��ֻ�ǲ鿴�������
	 * 
	 * @return
	 */
	public String editGroupInfo() {
		areas = areaService.findAll();// �õ������б�
		user = (TUser) session.get("user");
		System.out.println(orgId);
		orgainzation = organizationService.findById(orgId);
		jituan = orgainzation.getTOrgainzation();// �õ��������һ������
		// if (jituanList == null) {// TODO:���Ϊ�ղ�ȥ���ݿ��м��ؼ��Ż����б�
		jituanList = organizationService.findAllBlocByAreaId(user.getTOrgainzation().getTArea().getAreaId());// �õ������б�
		// }
		return SUCCESS;
	}

	// -----------------------------------��־����----------------------------------------//

	/**
	 * ��־���� ��־������ѭǰ���isSearching���ɣ���������isSearching
	 * 
	 * @return
	 */
	public String logManager() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		// calendar.add(Calendar.DATE, 1);// ������賿
		searchEndDate = calendar.getTime();
		calendar.add(Calendar.DATE, -2);// ����ǰ���賿
		searchStartDate = calendar.getTime();// �趨��ʼ�ͽ���ʱ��
		pageIndex = 0;
		findLogList();
		return SUCCESS;
	}

	/**
	 * ��־����
	 * 
	 * @return
	 */
	public String logSearch() {
		// ͨ��ǰ̨�����ÿ�ʼ�ͽ���ʱ�䣬����Ҫ��һЩ�ж�
		if (searchEndDate.before(searchStartDate)) {
			request.setAttribute("result", "ʱ�����������뱣֤����ʱ���ڿ�ʼʱ��֮��");
			page = "YunWei_logManager";
			return TARGET;
		}
		pageIndex = 0;
		findLogList();
		return TARGET;
	}

	/**
	 * �õ�ָ��ʱ��֮�ڵ���־�б����ڷ�ҳ
	 * 
	 * @return
	 */
	public String findLogList() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(searchEndDate);
		calendar.add(Calendar.DATE, 1);// ��һ����賿
		searchEndDate = calendar.getTime();

		user = (TUser) session.get("user");
		// �õ��ܼ�¼��
		total = logService.getCountByUserIdByTimeBetween(user.getUserid(), searchStartDate, searchEndDate);
		// �õ���ҳ��
		if (total % PAGESIZE == 0) {
			pages = total / PAGESIZE;
		} else {
			pages = total / PAGESIZE + 1;
		}
		// �õ���ǰ��ҳ����
		if (pageIndex < 1)
			pageIndex = 1;
		if (pageIndex > pages)
			pageIndex = pages;
		logList = logService.findByUserIdByTimeBetween(user.getUserid(), searchStartDate, searchEndDate, (pageIndex - 1) * PAGESIZE, PAGESIZE);

		calendar.add(Calendar.DATE, -1);// ʹҳ����ʾ��������ϰ��
		searchEndDate = calendar.getTime();

		page = "YunWei_logManager";
		return TARGET;
	}

	// ------------------------------ ���ݱ������ ---------------------------------------//

	// /**
	// * ���ݱ�������б�
	// */
	// public String excelManager() {
	// isSearching = false;
	// searchString = "";
	// searchWeiyi = -1;// �������Ҫ�������ʱ��������
	// onoff = 2;
	// pageIndex = 0;// ע�����︳ֵ�仯��Ϊ�˱���ǰ����б�ͺ�����б�����ͻ
	// // �õ���ά�����еķ���
	// groupList = organizationService.findAllGroupsByOM(user.getTOrgainzation().getTArea().getAreaId());
	// // �õ����еĳ�����
	// carInfoList = carInfoService.findAll();
	// searchTerminalExcelList();
	// return SUCCESS;
	// }
	//
	// /**
	// * �õ�����������ն��б�
	// *
	// * @return
	// */
	// public String searchTerminalExcelList() {
	// terminalExcelList = new ArrayList<TTerminal>();
	// // System.out.println("searchWeiyi:" + searchWeiyi);
	// DetachedCriteria detachedCriteria = null;
	// DetachedCriteria detachedCriteria2 = null;
	// if (searchWeiyi == -1) {// �����ʾ�ոս������ҳ�棬��û��������
	// total = 0;// ע�����︳ֵ�仯��Ϊ�˱���ǰ����б�ͺ�����б�����ͻ
	// pages = 0;
	// pageIndex = 0;// ע�⣬����ֻ����շ�ҳ�Ĳ��֣������Ĳ������������������Ǳ��������ԣ��ڶ������Ͻ������ҳ��ʱ���������ϴ�����������
	// } else {
	// // ģ������
	// if (searchWeiyi == 1 || searchWeiyi == 2 || searchWeiyi == 3 || searchWeiyi == 4 || searchWeiyi == 5) {// Ψһ������
	//
	// detachedCriteria = getCurrentMohuCriteria();
	// detachedCriteria2 = getCurrentMohuCriteria();
	//
	// // if (searchWeiyi == 1) {// ���� �ն˱�� ����
	// // terminal = terminalService.findBySim(searchString);
	// // } else if (searchWeiyi == 2) {// �����ն��ֻ�������
	// // terminal = terminalService.findByPhone(searchString);
	// // } else if (searchWeiyi == 3) {// ������ -- Ŀ�����к�
	// // terminal = terminalService.findByCarnumber(searchString);
	// // } else if (searchWeiyi == 4) {// �û�����
	// // terminal = terminalService.findByUsername(searchString);
	// // } else if (searchWeiyi == 5) {//
	// // terminal = terminalService.findByPrincipal(searchString);
	// // }
	// // // terminalһ��Ҫ�������ά�ĵ����е�
	// // user = (TUser) session.get("user");
	// // int areaid = user.getTOrgainzation().getTArea().getAreaId();
	// // List<TOrgainzation> groups = organizationService.findAllGroupsByOM(areaid);
	// // List<TTerminal> tempTerminals = new ArrayList<TTerminal>();
	// // for (int i = 0; i < groups.size(); i++) {
	// // tempTerminals.addAll(terminalService.findByOrg(groups.get(i).getOrgId()));
	// // }
	// // if (terminal != null && tempTerminals.contains(terminal)) {// ������������Ϊnull��ô����ӵ�list��
	// // terminalExcelList.add(terminal);
	// // total = 1;
	// // pages = 1;
	// // pageIndex = 1;
	// // } else {
	// // total = 0;
	// // pages = 0;
	// // pageIndex = 0;
	// // }
	// } else {// �����������
	// // ����֮����໥�ĸ��ţ�Ϊ�˱������ݿ�����ĸ��ţ�
	// // ��Ȼ�����������ѯ�����ǿ��ܲ�û�и�������ֵ����ʱ���������أ�
	// if (searchEndDate == null && searchFromDate == null && onoff == 2 && searchGroupId == 0 && carType == 0) {
	// page = "YunWei_excelManager";
	// return TARGET;
	// }
	// detachedCriteria = getCurrentCriteria();
	// detachedCriteria2 = getCurrentCriteria();
	// }
	// // �õ��ܼ�¼��
	// total = terminalService.getCountByCriteria(detachedCriteria);
	// System.out.println(total);
	// // �õ���ҳ��
	// if (total % PAGESIZE == 0) {
	// pages = total / PAGESIZE;
	// } else {
	// pages = total / PAGESIZE + 1;
	// }
	// // �õ���ǰ��ҳ����
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
	// // �õ���ǰ��ģ����ѯ����
	// private DetachedCriteria getCurrentMohuCriteria() {
	// user = (TUser) session.get("user");
	// DetachedCriteria criteria = DetachedCriteria.forClass(TTerminal.class);
	// // ����������Ӧ��û��������
	// criteria.createAlias("TOrgainzation", "t").createAlias("t.TArea", "tt")
	// .add(Restrictions.eq("tt.areaId", user.getTOrgainzation().getTArea().getAreaId()));
	// if (searchWeiyi == 1) {// ���� �ն˱�� ����
	// // terminal = terminalService.findBySim(searchString);
	// criteria.add(Restrictions.like("sim", searchString, MatchMode.ANYWHERE));
	// } else if (searchWeiyi == 2) {// �����ն��ֻ�������
	// // terminal = terminalService.findByPhone(searchString);
	// criteria.add(Restrictions.like("phone", searchString, MatchMode.ANYWHERE));
	// } else if (searchWeiyi == 3) {// ������ -- Ŀ�����к�
	// // terminal = terminalService.findByCarnumber(searchString);
	// criteria.add(Restrictions.like("carnumber", searchString, MatchMode.ANYWHERE));
	// } else if (searchWeiyi == 4) {// �û�����
	// // terminal = terminalService.findByUsername(searchString);
	// criteria.add(Restrictions.like("username", searchString, MatchMode.ANYWHERE));
	// } else if (searchWeiyi == 5) {//
	// // terminal = terminalService.findByPrincipal(searchString);
	// criteria.add(Restrictions.like("principal", searchString, MatchMode.ANYWHERE));
	// }
	// return criteria;
	// }
	//
	// // �õ���ǰ������
	// private DetachedCriteria getCurrentCriteria() {
	// DetachedCriteria detachedCriteria = DetachedCriteria.forClass(TTerminal.class);
	// user = (TUser) session.get("user");
	// // //����������
	// // detachedCriteria.createAlias("TOrgainzation.TArea", "tt").add(Restrictions.eq("tt.areaId",
	// // user.getTOrgainzation().getTArea().getAreaId()));
	// // detachedCriteria.add(Restrictions.eq("TOrgainzation.TArea.areaId", user.getTOrgainzation().getTArea().getAreaId()));
	// // detachedCriteria.createAlias("TOrgainzation", "to").createAlias("TArea", "ta").add(Restrictions.eq("to.ta.areaId",
	// // user.getTOrgainzation().getTArea().getAreaId()));
	// // TODO:�������������ܻ�������
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
	// // �����������
	// detachedCriteria.add(Restrictions.eq("TOrgainzation.orgId", searchGroupId));
	// }
	// if (carType != 0) {
	// // ������ķ�����
	// detachedCriteria.createAlias("TCarInfo", "carInfo").add(Restrictions.eq("carInfo.carTypeId", carType));
	// }
	// return detachedCriteria;
	// }
	//
	// // ����ѡ�е��е�������
	// public String excelExport() {
	// String[] colValues = cols.split(",");
	// if (colValues.length == 0) {
	// page = "YunWei_excelManager";
	// return TARGET;
	// }
	// // /////////////////////////////// ����Excel��ʼ /////////////////////////////////////////////
	// jxl.write.Label label;// label
	// WritableWorkbook workbook;// ������
	// ByteArrayOutputStream out = null;// �ֽ������
	// try {
	// out = new ByteArrayOutputStream();
	// // ������д��� Excel������
	// workbook = Workbook.createWorkbook(out);
	// // ����Excel������
	// WritableSheet sheet = workbook.createSheet("�ն���Ϣͳ��", 0);
	// // ����
	// String[] title = { "ID", "�ն˱��", "Ŀ�����к�", "Ŀ������", "����ʱ��", "�������ʱ��", "ծȯ������", "��������", "��������", "��λ��Ϣ", "����״̬", "��ƿ��ѹ", "�ź�ǿ��", "����ʱ��"
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
	// // ����label�����б�����⣬����label��ӵ�sheet��
	// for (int i = 0; i < title.length; i++) {
	// if (t[i]) {
	// label = new jxl.write.Label(index, 0, title[i]);// ��һ�У�index��
	// sheet.addCell(label);
	// sheet.setColumnView(index, 25);// ��֪�� index�еĿ��
	// index++;
	// }
	// }
	// // д�����ݣ�
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

	// ------------------------------- ���ó�ֵ --------------------------------------------//
	/**
	 * �����û����ù���
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
	 * ���ù����еķ����û���ѯ
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
	 * ���ڷ�ҳ�еķ����û����ù���
	 * 
	 * @return
	 */
	public String findGroupExpenseList() {
		// �õ��ܼ�¼��
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
		// �õ���ҳ��
		if (total % PAGESIZE == 0) {
			pages = total / PAGESIZE;
		} else {
			pages = total / PAGESIZE + 1;
		}
		// �õ���ǰ��ҳ����
		if (pageIndex < 1)
			pageIndex = 1;
		if (pageIndex > pages)
			pageIndex = pages;
		System.out.println("pageIndex=" + pageIndex);
		// �õ���ҳ��ʾ�ļ�¼
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

	// ///////////////////////////// �����û��ĳ�ֵ /////////////////////////////////
	/**
	 * �������û���ֵ���棬���ﲢû�г�ֵ
	 * 
	 * @return
	 */
	public String groupExpenseAdd() {
		orgainzation = organizationService.findById(orgId);
		account = null;// Ӧ�ò���Ҫnew���������ֵ�Ļ��Ͳ����˶���Ķ���
		return SUCCESS;
	}

	/**
	 * �����û���ֵ
	 * 
	 * @return
	 */
	public String addGroupExpense() {
		TOrgainzation pOrgainzation = orgainzation.getTOrgainzation();
		if (pOrgainzation.getBalance() < account.getExpense()) {// ���Ż��������ͳ�ֵ���Ƚϣ��������
			request.setAttribute("result", "���Ż��������㣡");
			page = "YunWei_groupExpenseAdd";// �������룬�������ض���
			return TARGET;
		}
		if (orgainzation.getBalance() + account.getExpense() > 100000) {// ���Ż��������ͳ�ֵ���Ƚϣ��������
			request.setAttribute("result", "�˻�����㹻������Ҫ��ֵ��");
			page = "JiTuan_groupExpenseAdd";// �������룬�������ض���
			return TARGET;
		}
		// ���ż�������ӣ���Ҫ����update
		pOrgainzation.setBalance(pOrgainzation.getBalance() - account.getExpense());
		orgainzation.setBalance(orgainzation.getBalance() + account.getExpense());
		account.setTOrgainzation(orgainzation);
		account.setPaider(pOrgainzation.getName());
		account.setPaiddate(new Date());
		String remark = "���������" + orgainzation.getName() + "����ֵ��" + account.getExpense() + "Ԫ";
		account.setRemark(remark);// ��ӱ�ע
		organizationService.updateOrganization(pOrgainzation);// ���ύ��䶼���ں���
		organizationService.updateOrganization(orgainzation);
		accountService.addAccount(account);
		// �����־
		logService.addLog(new TLog(user, remark, new Date()));
		request.setAttribute("result", "��ֵ�ɹ���");
		page = "YunWei_groupExpenseAdd";
		return TARGET;
	}

	// ///////////////////////////// �����û��ĳ�ֵ��ϸ���� /////////////////////////////////
	/**
	 * �����û��ĳ�ֵ��ϸ�����Բ�ѯ
	 * 
	 * @return
	 */
	public String groupExpenseDetail() {
		// ������ʼʱ��ͽ���ʱ��
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		// calendar.add(Calendar.DATE, 1);// ���ַ�ʽ���ԣ��õ�������һ��
		searchEndDate = calendar.getTime();
		calendar.add(Calendar.DATE, -2);// ǰ������賿
		searchStartDate = calendar.getTime();
		pageIndex = 0;
		findGroupExpenseDetailList();
		return SUCCESS;
	}

	/**
	 * �����û��ĳ�ֵ��ѯ
	 * 
	 * @return
	 */
	public String groupExpenseDetailSearch() {
		// ����õ�����������ʼʱ��ͽ���ʱ��
		pageIndex = 0;
		findGroupExpenseDetailList();
		return TARGET;
	}

	/**
	 * �õ��˵��б�<br/>
	 * ��������������г�����Ϊ�˷�ҳ����Ҫ���������������������
	 * 
	 * @return
	 */
	public String findGroupExpenseDetailList() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(searchEndDate);
		calendar.add(Calendar.DATE, 1);// ��һ����賿
		searchEndDate = calendar.getTime();
		// �õ��ܼ�¼��
		total = accountService.getCountByOrgIdByTime(orgId, searchStartDate, searchEndDate);
		// �õ���ҳ��
		if (total % PAGESIZE == 0) {
			pages = total / PAGESIZE;
		} else {
			pages = total / PAGESIZE + 1;
		}
		// �õ���ǰ��ҳ����
		if (pageIndex < 1)
			pageIndex = 1;
		if (pageIndex > pages)
			pageIndex = pages;
		// �õ���ҳ��ʾ�ļ�¼
		accountList = accountService.findByOrgIdByTime(orgId, searchStartDate, searchEndDate, (pageIndex - 1) * PAGESIZE, PAGESIZE);
		calendar.add(Calendar.DATE, -1);// ʹҳ����ʾ��������ϰ��
		searchEndDate = calendar.getTime();

		page = "YunWei_groupExpenseDetail";
		return TARGET;
	}

	// /////////////////------ ����������ն˹���------////////////////////////////
	/**
	 * �ն��б����
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
	 * �ն˲�ѯ
	 * 
	 * @return
	 */
	public String terminalSearch() {
		if ("1".equals(option)) { // �������
			total = terminalService.getCountMohuByOrgAndCarNumber(value,
					orgId);
		} else if ("2".equals(option)) {// ��ϵ��
			total = terminalService
					.getCountMohuByOrgAndUserName(value, orgId);
		} else if ("3".equals(option)) { // �ն˺���
			total = terminalService.getCountMohuByOrgAndSim(value, orgId);
		}
		// �õ���ҳ��
		if (total % Page.DEFAULT_PAGE_SIZE == 0) {
			pages = total / Page.DEFAULT_PAGE_SIZE;
		} else {
			pages = total / Page.DEFAULT_PAGE_SIZE + 1;
		}
		// �õ���ǰ��ҳ����
		if (pageIndex < 1)
			pageIndex = 1;
		if (pageIndex > pages)// ��ʱ���ж�
			pageIndex = pages;

		if ("1".equals(option)) { // �������
			terminalList = terminalService.findMohuByOrgAndCarNumber(value,
					orgId, (pageIndex - 1) * Page.DEFAULT_PAGE_SIZE,
					Page.DEFAULT_PAGE_SIZE);
		} else if ("2".equals(option)) {// ��ϵ��
			terminalList = terminalService.findMohuByOrgAndUserName(value,
					orgId, (pageIndex - 1) * Page.DEFAULT_PAGE_SIZE,
					Page.DEFAULT_PAGE_SIZE);
		} else if ("3".equals(option)) { // �ն˺���
			terminalList = terminalService.findMohuByOrgAndSim(value, orgId,
					(pageIndex - 1) * Page.DEFAULT_PAGE_SIZE,
					Page.DEFAULT_PAGE_SIZE);
		}
		page = "YunWei_terminalSearchResult";
		return TARGET;
	}

	/**
	 * ��������ն��û�����
	 * 
	 * @@return
	 */
	public String addTerminal() {
		areas = areaService.findAll();// �õ������б�
		carInfoList = carInfoService.findAll();
		terminal = new TTerminal();
		return SUCCESS;
	}

	/**
	 * �����ն��û���Ϣ
	 * 
	 * @@return
	 */
	public String saveTerminal() {
		TTerminal temp = terminalService.findBySim(terminal.getSim());
		if (temp != null) {
			request.setAttribute("result", "�ն˱���Ѵ���!");
			page = "YunWei_addTerminal";
			return TARGET;
		}
		temp = terminalService.findByPhone(terminal.getPhone());
		if (temp != null) {
			request.setAttribute("result", "��SIM�����Ѿ�����!");
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
		request.setAttribute("result", "�����ն��û��ɹ�!");
		user = (TUser) session.get("user");
		logService.addLog(new TLog(user, "��" + user.getName() + "��" + "�������նˡ�" + terminal.getSim() + "��", new Date()));
		return TARGET;
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * ���ڷ�ҳ�е��ն��û����ù��� �ն˵�ģ��������û�еײ�֧�֣��ն�Ӧ���Ǹ���sim��������
	 * 
	 * @return
	 */
	public String findTerminalList() {
		// �õ��ܼ�¼��
		if (isSearching) {
			if (searchString.equalsIgnoreCase("")) {
				total = terminalService.getCountByOrg(orgId);
			} else {
				total = terminalService.getCountMohuByOrgAndSim(searchString, orgId);
			}
		} else {
			total = terminalService.getCountByOrg(orgId);
		}
		// �õ���ҳ��
		if (total % PAGESIZE == 0) {
			pages = total / PAGESIZE;
		} else {
			pages = total / PAGESIZE + 1;
		}
		// �õ���ǰ��ҳ����
		if (pageIndex < 1)
			pageIndex = 1;
		if (pageIndex > pages)
			pageIndex = pages;
		// �õ���ҳ��ʾ�ļ�¼
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
	 * ɾ���ն��û���Ϣ
	 * 
	 * @return
	 */
	public String deleteTerminal() {
		terminalService.delTerminalBySim(sim);
		user = (TUser) session.get("user");
		logService.addLog(new TLog(user, "��" + user.getName() + "��" + "ɾ�����նˡ�" + sim + "��", new Date()));
		return terminalManager();
	}

	// //////////////////////////////// �����û��µ��ն��û��ķ��ù��� ///////////////////////////////
	/**
	 * �����û��µ��ն��û��ķ��ù���
	 */
	public String terminalExpenseManager() {
		isSearching = false;
		searchString = "";
		pageIndex = 0;
		findTerminalExpenseList();
		return SUCCESS;
	}

	/**
	 * �༭�ն��û���Ϣ
	 * 
	 * @return
	 */
	public String editTerminal() {
		// ��ȡ�ն���Ϣ
		areas = areaService.findAll();// �õ������б�
		terminal = terminalService.findBySim(sim);
		carInfoList = carInfoService.findAll();
		return SUCCESS;
	}

	/**
	 * �����ն��û���Ϣ
	 * 
	 * @return
	 */
	public String updateTerminal() {
		TArea t1= areaService.findById(selectAreaId);
		terminal.setTArea(t1);
		terminalService.updateTerminal(terminal);
		page = "YunWei_editTerminal";
		user = (TUser) session.get("user");
		logService.addLog(new TLog(user, "��" + user.getName() + "��" + "�޸����նˡ�" + terminal.getSim() + "��", new Date()));
		request.setAttribute("result", "�޸ĳɹ���");
		return TARGET;
	}

	/**
	 * ���ù����е��ն��û���ѯ
	 * 
	 * @return
	 */
	public String terminalExpenseSearch() {
		isSearching = true;
		if ("1".equals(option)) { // �������
			total = terminalService.getCountMohuByOrgAndCarNumber(value,
					orgId);
		} else if ("2".equals(option)) {// ��ϵ��
			total = terminalService
					.getCountMohuByOrgAndUserName(value, orgId);
		} else if ("3".equals(option)) { // �ն˺���
			total = terminalService.getCountMohuByOrgAndSim(value, orgId);
		}
		// �õ���ҳ��
		if (total % Page.DEFAULT_PAGE_SIZE == 0) {
			pages = total / Page.DEFAULT_PAGE_SIZE;
		} else {
			pages = total / Page.DEFAULT_PAGE_SIZE + 1;
		}
		// �õ���ǰ��ҳ����
		if (pageIndex < 1)
			pageIndex = 1;
		if (pageIndex > pages)// ��ʱ���ж�
			pageIndex = pages;

		if ("1".equals(option)) { // �������
			terminalList = terminalService.findMohuByOrgAndCarNumber(value,
					orgId, (pageIndex - 1) * Page.DEFAULT_PAGE_SIZE,
					Page.DEFAULT_PAGE_SIZE);
		} else if ("2".equals(option)) {// ��ϵ��
			terminalList = terminalService.findMohuByOrgAndUserName(value,
					orgId, (pageIndex - 1) * Page.DEFAULT_PAGE_SIZE,
					Page.DEFAULT_PAGE_SIZE);
		} else if ("3".equals(option)) { // �ն˺���
			terminalList = terminalService.findMohuByOrgAndSim(value, orgId,
					(pageIndex - 1) * Page.DEFAULT_PAGE_SIZE,
					Page.DEFAULT_PAGE_SIZE);
		}
		page = "YunWei_terminalExpenseSearchResult";
		return TARGET;
	}

	/**
	 * ���ڷ�ҳ�е��ն��û����ù��� �ն˵�ģ��������û�еײ�֧�֣��ն�Ӧ���Ǹ���sim��������
	 * 
	 * @return
	 */
	public String findTerminalExpenseList() {
		// �õ��ܼ�¼��
		if (isSearching) {
			total = terminalService.getCountMohuByOrgAndSim(searchString, orgId);
		} else {
			total = terminalService.getCountByOrg(orgId);
		}
		// �õ���ҳ��
		if (total % PAGESIZE == 0) {
			pages = total / PAGESIZE;
		} else {
			pages = total / PAGESIZE + 1;
		}
		// �õ���ǰ��ҳ����
		if (pageIndex < 1)
			pageIndex = 1;
		if (pageIndex > pages)
			pageIndex = pages;
		// �õ���ҳ��ʾ�ļ�¼
		if (isSearching) {
			terminalList = terminalService.findMohuByOrgAndSim(searchString, orgId, (pageIndex - 1) * PAGESIZE, PAGESIZE);
		} else {
			terminalList = terminalService.findByOrg(orgId, (pageIndex - 1) * PAGESIZE, PAGESIZE);
		}
		page = "YunWei_terminalExpenseManager";
		return TARGET;
	}

	// ///////////////////////////// �ն��û��ĳ�ֵ /////////////////////////////////
	/**
	 * �����ն��û���ֵ���棬���ﲢû�г�ֵ
	 * 
	 * @return
	 */
	public String terminalExpenseAdd() {
		System.out.println("terminal Id = " + terminalId);
		terminal = terminalService.findById(terminalId);
		account = null;// Ӧ�ò���Ҫnew���������ֵ�Ļ��Ͳ����˶���Ķ���
		return SUCCESS;
	}

	/**
	 * ���ն��û���ֵ
	 * 
	 * @return
	 */
	public String addTerminalExpense() {
		if (serviceYear == 0) {
			request.setAttribute("result", "��ѡ���ֵ������");
			page = "YunWei_terminalExpenseAdd";
			return TARGET;
		}
		TOrgainzation pOrgainzation = organizationService.findById(orgId);
		if (pOrgainzation.getBalance() < pOrgainzation.getFeestandard() * serviceYear) {// ������������ͷ�����ѱȽϣ��������
			request.setAttribute("result", "������������㣡");
			page = "YunWei_terminalExpenseAdd";
			return TARGET;
		}
		// ���������Ҫ����update
		pOrgainzation.setBalance(pOrgainzation.getBalance() - pOrgainzation.getFeestandard() * serviceYear);
		organizationService.updateOrganization(pOrgainzation);// �ύ���
		// ��ֵ�ɹ�������terminal
		Date today = new Date();
		if (terminal.getEndTime().before(today)) {// �����Ѿ�������
			terminal.setStartTime(today);
		} /*
		 * else { terminal.setStartTime(terminal.getEndTime()); }
		 */
		// ����������ݵõ�����ʱ��
		Date toDate = terminal.getStartTime();
		Date newDate = new Date((toDate.getYear() + serviceYear), toDate.getMonth(), toDate.getDate());
		terminal.setEndTime(newDate);
		terminalService.updateTerminal(terminal);

		// ����һ���˵�
		account.setTOrgainzation(pOrgainzation);// ���ն˳�ֵʱ�˵�����ʾ�Ļ��Ƿ����û�
		account.setPaiddate(today);
		account.setPaider(pOrgainzation.getName());
		account.setExpense(pOrgainzation.getFeestandard() * serviceYear);
		account.setRemark("�նˡ�" + terminal.getSim() + "����ֵ��" + serviceYear + "��");// ��ӱ�ע
		accountService.addAccount(account);// TODO�����ﱨ��

		// ����һ����־
		String remark = "��" + user.getName() + "��Ϊ�նˡ�" + terminal.getSim() + "����ֵ�ˣ�����ʱ���ǡ�" + terminal.getStartTime().toLocaleString() + "��-��"
				+ terminal.getEndTime().toLocaleString() + "��";
		logService.addLog(new TLog(user, remark, today));

		request.setAttribute("result", "��ֵ�ɹ���");
		page = "YunWei_terminalExpenseAdd";
		return TARGET;
	}

	// �ն��û��ĳ�ֵ��ѯ�ͳ�ֵ��ϸû�У�

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
