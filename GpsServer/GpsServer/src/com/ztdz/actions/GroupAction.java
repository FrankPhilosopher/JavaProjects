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
 * ��action���������û������������Ӧ
 * 
 * @@author wuxuehong
 * 
 *          2012-5-24
 */
public class GroupAction extends ActionSupport implements SessionAware, ServletRequestAware {
	private int selectAreaId;
    private List<TArea> areas;
	private static final int PAGESIZE = 10;
	private Map<String, Object> session; // session �Ự
	private HttpServletRequest request;// request

	private TUser user;
	private TOrgainzation org;

	private Integer tid; // �ն�����
	private String sim; // �ն˱��
	private String option; // ��������
	private String searchValue; // ����ֵ

	private List<TTerminal> terminals;
	private List<TCarInfo> cars; // ������Ϣ

	// ҳ����ת����
	private String page;
	private String TARGET = "target";
	private String result;

	// ��ҳ�йص�����
	private int total;// ����
	private int pageIndex;// ��ǰҳ
	private int pages;// ҳ������

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

	// ������ʱ���йص����ԣ�������������������
	private Date searchStartDate;
	private Date searchEndDate;

	// �������йص����� --- �����action�ڲ������ԣ�����get��set
	private boolean isSearching = false;// ������Ա�ʾ�Ƿ�ǰ���б��Ǵ�����������У���Ϊ��������õĻ���ô���������ҳ��Ч
	private String searchString;// Ҫ������"����"

	/**
	 * ��spring ��ɷ���ע��
	 */
	private UserServiceImpl userService;
	private LogServiceImpl logService;
	private OrganizationServiceImpl organizationService;
	private TerminalServiceImpl terminalService;
	private CarInfoServiceImpl carInfoService;
	private AccountServiceImpl accountService;
	private TempPositionServiceImpl tempPositionService;
	private AreaServiceImpl areaService;

	// ���ݱ������
	private int searchWeiyi;// Ψһ����������
	private String searchTerminalNum;// �ն˱�� ���� �ֻ���
	private Date searchFromDate;// ��ʼʱ��
	private Date searchToDate;// ����ʱ��
	private int onoff;// ����״̬
	private int carType;// Ŀ������
	// private int searchGroupId;// �����������ķ������
	private List<TTerminal> terminalExcelList;// ����б�
	private List<TCarInfo> carInfoList;// �������б�
	private String cols;// ѡ�е�Ҫ�������У������ֺͶ������
	private InputStream excelStream; // excel������

	// private static final int PAGESIZE = 10;
	// private TTerminal terminal;// ���б༭���ն�
	// private boolean isSearching = false;// ������Ա�ʾ�Ƿ�ǰ���б��Ǵ�����������У���Ϊ��������õĻ���ô���������ҳ��Ч
	// private String searchString;// Ҫ������"����"

	/**
	 * ��������
	 * 
	 * @@return
	 */
	public String basicInfo() {
		user = (TUser) session.get("user");// ֱ�ӴӻỰ�л�ȡ
		return SUCCESS;
	}

	/**
	 * �û�����
	 * 
	 * @@return
	 */
	public String userManager() {
		return terminalManager();
	}

	/**
	 * ��ѯ�ն��û� �˻�����
	 * 
	 * @return
	 */
	// <<<<<<< GroupAction.java
	// public String userSearch() {
	// user = (TUser) session.get("user");// ֱ�ӴӻỰ�л�ȡ
	// System.out.println(sim + "\t" + user.getTOrgainzation().getOrgId());
	// if (sim != null) {
	// int orgid = user.getTOrgainzation().getOrgId();
	// total = terminalService.getCountMohuByOrgAndSim(sim, orgid);
	// // �õ���ҳ��
	// if (total % Page.DEFAULT_PAGE_SIZE == 0) {
	// pages = total / Page.DEFAULT_PAGE_SIZE;
	// } else {
	// pages = total / Page.DEFAULT_PAGE_SIZE + 1;
	// }
	// // �õ���ǰ��ҳ����
	// if (pageIndex < 1)
	// pageIndex = 1;
	// if (pageIndex > pages)// ��ʱ���ж�
	// pageIndex = pages;
	// terminals = terminalService.findByOrg(orgid, (pageIndex - 1) * Page.DEFAULT_PAGE_SIZE, Page.DEFAULT_PAGE_SIZE);
	//
	// terminals = terminalService.findMohuByOrgAndSim(sim, orgid);
	// } else {
	// userManager();
	// }
	// =======
	public String userSearch() {
		// user = (TUser) session.get("user");// ֱ�ӴӻỰ�л�ȡ
		// System.out.println(sim+"\t"+user.getTOrgainzation().getOrgId());
		// if(sim != null){
		// int orgid = user.getTOrgainzation().getOrgId();
		// total = terminalService.getCountMohuByOrgAndSim(sim, orgid);
		// // �õ���ҳ��
		// if (total % Page.DEFAULT_PAGE_SIZE == 0) {
		// pages = total / Page.DEFAULT_PAGE_SIZE;
		// } else {
		// pages = total / Page.DEFAULT_PAGE_SIZE + 1;
		// }
		// // �õ���ǰ��ҳ����
		// if (pageIndex < 1)
		// pageIndex = 1;
		// if (pageIndex > pages)// ��ʱ���ж�
		// pageIndex = pages;
		// terminals = terminalService.findByOrg(orgid,(pageIndex - 1) * Page.DEFAULT_PAGE_SIZE, Page.DEFAULT_PAGE_SIZE);
		//
		// terminals = terminalService.findMohuByOrgAndSim(sim, orgid);
		// }else{
		// userManager();
		// }
		terminalSearch();
		// >>>>>>> 1.11
		page = "Group_userManager";
		return TARGET;
	}

	/**
	 * ��ѯ�ն����� �ն˹���
	 * 
	 * @return
	 */
	public String terminalSearch() {

		user = (TUser) session.get("user");// ֱ�ӴӻỰ�л�ȡ
		if (option != null) {
			// >>>>>>> 1.11
			int orgid = user.getTOrgainzation().getOrgId();
			if ("1".equals(option)) { // �������
				total = terminalService.getCountMohuByOrgAndCarNumber(searchValue, orgid);
			} else if ("2".equals(option)) {// ��ϵ��
				total = terminalService.getCountMohuByOrgAndUserName(searchValue, orgid);
			} else if ("3".equals(option)) { // �ն˺���
				total = terminalService.getCountMohuByOrgAndSim(searchValue, orgid);
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
			// <<<<<<< GroupAction.java
			// terminals = terminalService.findByOrg(orgid, (pageIndex - 1) * Page.DEFAULT_PAGE_SIZE, Page.DEFAULT_PAGE_SIZE);
			//
			// terminals = terminalService.findMohuByOrgAndSim(sim, orgid);
			// } else {
			// =======
			// terminals = terminalService.findByOrg(orgid,(pageIndex - 1) * Page.DEFAULT_PAGE_SIZE, Page.DEFAULT_PAGE_SIZE);
			if ("1".equals(option)) { // �������
				terminals = terminalService.findMohuByOrgAndCarNumber(searchValue, orgid, (pageIndex - 1) * Page.DEFAULT_PAGE_SIZE, Page.DEFAULT_PAGE_SIZE);
			} else if ("2".equals(option)) {// ��ϵ��
				terminals = terminalService.findMohuByOrgAndUserName(searchValue, orgid, (pageIndex - 1) * Page.DEFAULT_PAGE_SIZE, Page.DEFAULT_PAGE_SIZE);
			} else if ("3".equals(option)) { // �ն˺���
				terminals = terminalService.findMohuByOrgAndSim(searchValue, orgid, (pageIndex - 1) * Page.DEFAULT_PAGE_SIZE, Page.DEFAULT_PAGE_SIZE);
			}
		} else {
			userManager();
		}
		page = "Group_terminalManager";
		return TARGET;
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
		cars = carInfoService.findAll();
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
		page = "Group_editTerminal";
		user = (TUser) session.get("user");
		logService.addLog(new TLog(user, "��" + user.getName() + "��" + "�޸����նˡ�" + terminal.getSim() + "��", new Date()));
		request.setAttribute("result", "�޸ĳɹ���");
		return TARGET;
	}

	/**
	 * ɾ���ն��û���Ϣ
	 * 
	 * @return
	 */
	public String deleteTerminal() {
		// terminalService.delTerminalById(tid);
		terminalService.delTerminalBySim(sim);
		page = "Group_userManager";
		user = (TUser) session.get("user");
		logService.addLog(new TLog(user, "��" + user.getName() + "��" + "ɾ�����նˡ�" + sim + "��", new Date()));
		userManager();
		return TARGET;
	}

	/**
	 * ��������ն��û�����
	 * 
	 * @@return
	 */
	public String addTerminal() {
		areas = areaService.findAll();// �õ������б�
		cars = carInfoService.findAll();
		terminal = new TTerminal();
		return SUCCESS;
	}

	/**
	 * �����ն���Ϣ
	 * 
	 * @@return
	 */
	public String saveTerminal() {
		page = "Group_addTerminal";
		TTerminal temp = terminalService.findBySim(terminal.getSim());
		if (temp != null) {
			request.setAttribute("result", "���ն˱���Ѿ�����!");
			return TARGET;
		}
		temp = terminalService.findByPhone(terminal.getPhone());
		if (temp != null) {
			request.setAttribute("result", "��SIM�����Ѿ�����!");
			return TARGET;
		}
		TArea t1= areaService.findById(selectAreaId);
		terminal.setTArea(t1);
		terminal.setRegistertime(new Date());
		TUser user = (TUser) session.get("user");
		terminal.setTOrgainzation(user.getTOrgainzation());
		terminal.setSignal(0);
		terminal.setElepress("0");
		terminalService.addTerminal(terminal);

		// terminal = terminalService.findBySim(terminal.getSim());
		TTempPosition position = new TTempPosition();
System.out.println("************************"+terminal.getSim());
		position.setSim(terminal.getSim());
		position.setTTerminal(terminal);
		position.setLatitude(-1);
		position.setLongitude(-1);
		position.setLatoffset(0);
		position.setLngoffset(0);
		position.setPTime(new Date());
		tempPositionService.addTempPosition(position);
		request.setAttribute("result", "�����ն��û��ɹ�!");
		user = (TUser) session.get("user");
		logService.addLog(new TLog(user, "��" + user.getName() + "��" + "�������նˡ�" + terminal.getSim() + "��", new Date()));
		return TARGET;
	}

	/**
	 * �ն˹���
	 * 
	 * @@return
	 */
	public String terminalManager() {
		sim = null;
		option = null;
		searchValue = null;
		TUser user = (TUser) session.get("user");
		int orgid = user.getTOrgainzation().getOrgId();
		total = terminalService.getCountByOrg(orgid);
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
		terminals = terminalService.findByOrg(orgid, (pageIndex - 1) * Page.DEFAULT_PAGE_SIZE, Page.DEFAULT_PAGE_SIZE);
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
		calendar.add(Calendar.DATE, 1);// ����һ����賿
		searchEndDate = calendar.getTime();
		calendar.add(Calendar.DATE, -3);// ǰ������賿
		searchStartDate = calendar.getTime();// �趨��ʼ�ͽ���ʱ��
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
			page = "Group_logManager";
			return TARGET;
		}
		findLogList();
		return TARGET;
	}

	/**
	 * �õ�ָ��ʱ��֮�ڵ���־�б����ڷ�ҳ
	 * 
	 * @return
	 */
	public String findLogList() {
		System.out.println(searchStartDate);
		System.out.println(searchEndDate);
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
		page = "Group_logManager";
		return TARGET;
	}

	// ///////////////////////////// �ն��û��ĳ�ֵ /////////////////////////////////
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
	 * ���ù����е��ն��û���ѯ
	 * 
	 * @return
	 */
	public String terminalExpenseSearch() {
		isSearching = true;
		pageIndex = 0;
		findTerminalExpenseList();
		return TARGET;
	}

	/**
	 * ���ڷ�ҳ�е��ն��û����ù��� �ն˵�ģ��������û�еײ�֧�֣��ն�Ӧ���Ǹ���sim��������
	 * 
	 * @return
	 */
	public String findTerminalExpenseList() {
		user = (TUser) session.get("user");
		int orgId = user.getTOrgainzation().getOrgId();
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
		System.out.println("pageIndex=" + pageIndex);
		// �õ���ҳ��ʾ�ļ�¼
		if (isSearching) {
			terminalList = terminalService.findMohuByOrgAndSim(searchString, orgId, (pageIndex - 1) * PAGESIZE, PAGESIZE);
		} else {
			terminalList = terminalService.findByOrg(orgId, (pageIndex - 1) * PAGESIZE, PAGESIZE);
		}
		page = "Group_terminalExpenseManager";
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
		System.out.println(serviceStartDate.toLocaleString());
		System.out.println(serviceEndDate.toLocaleString());
		// �ж�����ʱ�����Ƿ���һ���꣡
		if ((serviceEndDate.getYear() - serviceStartDate.getYear()) != 1) {
			request.setAttribute("result", "ʱ����������һ���꣡");
			page = "Group_terminalExpenseAdd";
			return TARGET;// ��������ҳ�棬��������ݾͻ�û�ˣ�---Ӧ�ò���û����
		}
		user = (TUser) session.get("user");
		TOrgainzation pOrgainzation = user.getTOrgainzation();// pOrgainzation��Ϊnull����������Ϊ��
		if (pOrgainzation.getBalance() < pOrgainzation.getFeestandard()) {// ������������ͷ�����ѱȽϣ��������
			request.setAttribute("result", "������������㣡");
			page = "Group_terminalExpenseAdd";
			return TARGET;
		}
		// ���������Ҫ����update
		pOrgainzation.setBalance(pOrgainzation.getBalance() - pOrgainzation.getFeestandard());
		// ��ֵ�ɹ�������terminal
		terminal.setStartTime(serviceStartDate);// ���÷���ʱ��
		terminal.setEndTime(serviceEndDate);
		terminalService.updateTerminal(terminal);
		// ����һ���˵�
		// account.setExpense(pOrgainzation.getFeestandard());// �����˵��ķ����Ǳ�׼���
		// account��null������������ڽ���֮ǰnew���Ǵ�ģ���Ϊû��id
		// account��null��ԭ����ǰ̨Ҫ�ύ�ı��в�û��account�е����ݣ��Ҿ����ǣ������Ǵ��ˣ�
		// ���һ��������<input name="account.Expense" type="hidden" value="terminal.TOrgainzation.feestandard" />
		account.setTOrgainzation(pOrgainzation);// ���ն˳�ֵʱ�˵�����ʾ�Ļ��Ƿ����û�
		account.setPaiddate(new Date());
		account.setPaider(pOrgainzation.getName());
		String remark = user.getName() + "Ϊ�ն�" + terminal.getSim() + "��ֵ�ˣ�����ʱ����" + terminal.getStartTime().toLocaleString() + "-"
				+ terminal.getEndTime().toLocaleString();
		account.setRemark(remark);// ��ӱ�ע
		organizationService.updateOrganization(pOrgainzation);// �ύ���
		accountService.addAccount(account);// TODO�����ﱨ��
		request.setAttribute("result", "��ֵ�ɹ���");
		page = "Group_terminalExpenseAdd";
		return TARGET;
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////

	// ------------------------------ ���ݱ������ ---------------------------------------//

	/**
	 * ���ݱ�������б�
	 */
	public String excelManager() {
		isSearching = false;
		searchString = "";
		cols = "";
		searchFromDate = null;
		searchEndDate = null;
		carType = 0;
		searchWeiyi = -1;// �������Ҫ�������ʱ��������
		onoff = 2;
		pageIndex = 0;// ע�����︳ֵ�仯��Ϊ�˱���ǰ����б�ͺ�����б�����ͻ
		carInfoList = carInfoService.findAll();// �õ����еĳ�����
		findTerminalExcelList();
		return SUCCESS;
	}

	/**
	 * ����terminal
	 */
	public String terminalExcelSearch() {
		isSearching = true;
		findTerminalExcelList();
		page = "Group_excelManager";
		return TARGET;
	}

	/**
	 * �õ�����������ն��б�
	 * 
	 * @return
	 */
	public String findTerminalExcelList() {
		user = (TUser) session.get("user");
		terminalExcelList = new ArrayList<TTerminal>();
		if (!isSearching) {
			total = 0;// ע�����︳ֵ�仯��Ϊ�˱���ǰ����б�ͺ�����б�����ͻ
			pages = 0;
			pageIndex = 0;
			page = "Group_excelManager";
			return TARGET;
		}
		if (searchEndDate == null && searchFromDate == null && onoff == 2 && carType == 0) {
			total = 0;// ע�����︳ֵ�仯��Ϊ�˱���ǰ����б�ͺ�����б�����ͻ
			pages = 0;
			pageIndex = 0;
			page = "Group_excelManager";
			return TARGET;
		}
		DetachedCriteria detachedCriteria = getCurrentCriteria();
		DetachedCriteria detachedCriteria2 = getCurrentCriteria();
		// �õ��ܼ�¼��
		total = terminalService.getCountByCriteria(detachedCriteria);
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
		terminalExcelList = terminalService.findAllByCriteria(detachedCriteria2, PAGESIZE * (pageIndex - 1), PAGESIZE);
		page = "Group_excelManager";
		return TARGET;
	}

	// �õ���ǰ������
	private DetachedCriteria getCurrentCriteria() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(TTerminal.class);
		user = (TUser) session.get("user");
		// ����������
		detachedCriteria.createAlias("TOrgainzation", "to").add(Restrictions.eq("to.orgId", user.getTOrgainzation().getOrgId()));
		if (searchFromDate != null) {
			detachedCriteria.add(Expression.ge("registertime", searchFromDate));
		}
		Date dd;
		if (searchEndDate != null) {
			dd = searchEndDate;
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dd);
			calendar.add(Calendar.DATE, 1);// ��һ����賿
			dd = calendar.getTime();
			detachedCriteria.add(Expression.le("registertime", dd));
		}
		if (onoff != 2) {
			detachedCriteria.add(Restrictions.eq("netstatus", onoff));
		}
		if (carType != 0) {
			// ������ķ�����
			detachedCriteria.createAlias("TCarInfo", "carInfo").add(Restrictions.eq("carInfo.carTypeId", carType));
		}
		return detachedCriteria;
	}

	// ����ѡ�е��е�������
	public String excelExport() {
		String[] colValues = cols.split(",");
		if (colValues.length == 0) {
			page = "Group_excelManager";
			return TARGET;
		}
		// /////////////////////////////// ����Excel��ʼ /////////////////////////////////////////////
		jxl.write.Label label;// label
		WritableWorkbook workbook;// ������
		ByteArrayOutputStream out = null;// �ֽ������
		try {
			out = new ByteArrayOutputStream();
			// ������д��� Excel������
			workbook = Workbook.createWorkbook(out);
			// ����Excel������
			WritableSheet sheet = workbook.createSheet("�ն���Ϣͳ��", 0);
			// ����

			String[] title = { "�ն˱��", "�ն�SIM����", "Ŀ�����к�", "Ŀ������", "Ŀ���ͺ�", "����ʱ��", "����ʼʱ��", "�������ʱ��", "����״̬", "�û���", "��ϵ�绰", "ծȯ������", "��������", "��������", "����״̬",
					"����ʱ�����(����)", "��ƿ��ѹ", "�ź�ǿ��", "����״̬", "����ʱ��", "��λģʽ", "��վ���", "С����Ԫ��", "γ�ȷ���", "γ��", "���ȷ���", "����" };
			boolean[] t = new boolean[title.length];
			int i = 0;
			// ���Ƿ񵼳�
			for (; i < colValues.length - 1; i++) {
				if (Integer.parseInt(colValues[i]) == 1) {
					t[i] = true;
				} else {
					t[i] = false;
				}
			}
			// ��λ��Ϣ�Ƿ񵼳�
			if (Integer.parseInt(colValues[colValues.length - 1]) == 1) {
				for (; i < t.length; i++) {
					t[i] = true;
				}
			} else {
				for (; i < t.length; i++) {
					t[i] = false;
				}
			}

			int index = 0;
			// ����label�����б�����⣬����label��ӵ�sheet��
			for (int j = 0; j < title.length; j++) {
				if (t[j]) {
					label = new jxl.write.Label(index, 0, title[j]);// ��һ�У�index��
					sheet.addCell(label);
					sheet.setColumnView(index, 25);// ����֪�� index�еĿ�ȣ�
					index++;
				}
			}
			DetachedCriteria detachedCriteria = getCurrentCriteria();

			terminalExcelList = terminalService.findAllByCriteria(detachedCriteria);
			for (int j = 0; j < terminalExcelList.size(); j++) {
				index = 0;
				terminal = terminalExcelList.get(j);

				if (t[0])
					sheet.addCell(new jxl.write.Label(index++, j + 1, terminal.getSim()));
				if (t[1])
					sheet.addCell(new jxl.write.Label(index++, j + 1, terminal.getPhone()));
				if (t[2])
					sheet.addCell(new jxl.write.Label(index++, j + 1, terminal.getCarnumber()));
				if (t[3])
					sheet.addCell(new jxl.write.Label(index++, j + 1, terminal.getTCarInfo().getTypeName()));

				if (t[4])
					sheet.addCell(new jxl.write.Label(index++, j + 1, terminal.getModel()));
				if (t[5]) {
					if (terminal.getRegistertime() != null && dateFormat.format(terminal.getRegistertime()) != null) {
						sheet.addCell(new jxl.write.Label(index++, j + 1, dateFormat.format(terminal.getRegistertime())));
					} else {
						sheet.addCell(new jxl.write.Label(index++, j + 1, ""));
					}
				}
				if (t[6]) {
					if (terminal.getStartTime() != null && dateFormat.format(terminal.getStartTime()) != null) {
						sheet.addCell(new jxl.write.Label(index++, j + 1, dateFormat.format(terminal.getStartTime())));
					} else {
						sheet.addCell(new jxl.write.Label(index++, j + 1, ""));
					}
				}
				if (t[7]) {
					if (terminal.getEndTime() != null && dateFormat.format(terminal.getEndTime()) != null) {
						sheet.addCell(new jxl.write.Label(index++, j + 1, dateFormat.format(terminal.getEndTime())));
					} else {
						sheet.addCell(new jxl.write.Label(index++, j + 1, ""));
					}
				}

				if (t[8]) {
					String lockString = "";
					if (terminal.getLock() == 0) {
						lockString = "����";
					} else if (terminal.getLock() == 1) {
						lockString = "�ͼ�����";
					} else if (terminal.getLock() == 2) {
						lockString = "�߼�����";
					}
					sheet.addCell(new jxl.write.Label(index++, j + 1, lockString));
				}
				if (t[9])
					sheet.addCell(new jxl.write.Label(index++, j + 1, terminal.getUsername()));
				if (t[10])
					sheet.addCell(new jxl.write.Label(index++, j + 1, terminal.getCellphone()));
				if (t[11])
					sheet.addCell(new jxl.write.Label(index++, j + 1, terminal.getPrincipal()));
				if (t[12])
					sheet.addCell(new jxl.write.Label(index++, j + 1, terminal.getTOrgainzation().getTOrgainzation().getName()));
				if (t[13])
					sheet.addCell(new jxl.write.Label(index++, j + 1, terminal.getTOrgainzation().getName()));

				if (t[14]) {
					String workString = "";
					if (terminal.getLock() == 0) {
						workString = "����";
					} else if (terminal.getLock() == 1) {
						workString = "����";
					}
					sheet.addCell(new jxl.write.Label(index++, j + 1, workString));
				}
				if (t[15])
					sheet.addCell(new jxl.write.Label(index++, j + 1, String.valueOf(terminal.getWorktime())));
				if (t[16])
					sheet.addCell(new jxl.write.Label(index++, j + 1, terminal.getElepress() == null ? "" : terminal.getElepress()));
				if (t[17])
					sheet.addCell(new jxl.write.Label(index++, j + 1, String.valueOf(terminal.getSignal()).equalsIgnoreCase("null") ? "" : String
							.valueOf(terminal.getSignal())));

				if (t[18]) {
					String netString = "";
					if (terminal.getNetstatus() == 1) {
						netString = "����";
					} else if (terminal.getNetstatus() == 0) {
						netString = "����";
					} else {
						netString = "δ֪";
					}
					sheet.addCell(new jxl.write.Label(index++, j + 1, netString));
				}
				if (t[19])
					sheet.addCell(new jxl.write.Label(index++, j + 1, terminal.getRemark() == null ? "" : terminal.getRemark()));

				if (t[20])
					if (terminal.getTTempPositions().get(0) != null) {
						sheet.addCell(new jxl.write.Label(index++, j + 1, (terminal.getTTempPositions().get(0)).getLocationModel()));
					} else {
						sheet.addCell(new jxl.write.Label(index++, j + 1, ""));
					}
				if (t[21]) {
					if (terminal.getTTempPositions().get(0) != null) {
						sheet.addCell(new jxl.write.Label(index++, j + 1, (terminal.getTTempPositions().get(0)).getStationId()));
					} else {
						sheet.addCell(new jxl.write.Label(index++, j + 1, ""));
					}
				}
				if (t[22])
					if (terminal.getTTempPositions().get(0) != null) {
						sheet.addCell(new jxl.write.Label(index++, j + 1, (terminal.getTTempPositions().get(0)).getPlotId()));
					} else {
						sheet.addCell(new jxl.write.Label(index++, j + 1, ""));
					}
				if (t[23])
					if (terminal.getTTempPositions().get(0) != null) {
						sheet.addCell(new jxl.write.Label(index++, j + 1, (terminal.getTTempPositions().get(0)).getLatiDirection()));
					} else {
						sheet.addCell(new jxl.write.Label(index++, j + 1, ""));
					}
				if (t[24])
					if (terminal.getTTempPositions().get(0) != null) {
						sheet.addCell(new jxl.write.Label(index++, j + 1, String.valueOf((terminal.getTTempPositions().get(0)).getLatitude())));
					} else {
						sheet.addCell(new jxl.write.Label(index++, j + 1, ""));
					}

				if (t[25])
					if (terminal.getTTempPositions().get(0) != null) {
						sheet.addCell(new jxl.write.Label(index++, j + 1, (terminal.getTTempPositions().get(0)).getLongDirection()));
					} else {
						sheet.addCell(new jxl.write.Label(index++, j + 1, ""));
					}
				if (t[26])
					if (terminal.getTTempPositions().get(0) != null) {
						sheet.addCell(new jxl.write.Label(index++, j + 1, String.valueOf((terminal.getTTempPositions().get(0)).getLongitude())));
					} else {
						sheet.addCell(new jxl.write.Label(index++, j + 1, ""));
					}

			}
			workbook.write();
			workbook.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		excelStream = new ByteArrayInputStream(out.toByteArray());
		return "excel";
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////

	// ///////////////////////////////////////set and getters.///////////////////////////////////////////////
	@Override
	public void setSession(Map<String, Object> session) {
		// TODO Auto-generated method stub
		this.session = session;
	}

	public Map<String, Object> getSession() {
		return session;
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

	public OrganizationServiceImpl getOrganizationService() {
		return organizationService;
	}

	public void setOrganizationService(OrganizationServiceImpl organizationService) {
		this.organizationService = organizationService;
	}

	public TUser getUser() {
		return user;
	}

	public void setUser(TUser user) {
		this.user = user;
	}

	public TOrgainzation getOrg() {
		return org;
	}

	public void setOrg(TOrgainzation org) {
		this.org = org;
	}

	public List<TTerminal> getTerminals() {
		return terminals;
	}

	public void setTerminals(List<TTerminal> terminals) {
		this.terminals = terminals;
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

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public TerminalServiceImpl getTerminalService() {
		return terminalService;
	}

	public void setTerminalService(TerminalServiceImpl terminalService) {
		this.terminalService = terminalService;
	}

	public List<TCarInfo> getCars() {
		return cars;
	}

	public void setCars(List<TCarInfo> cars) {
		this.cars = cars;
	}

	public CarInfoServiceImpl getCarInfoService() {
		return carInfoService;
	}

	public void setCarInfoService(CarInfoServiceImpl carInfoService) {
		this.carInfoService = carInfoService;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public TTerminal getTerminal() {
		return terminal;
	}

	public void setTerminal(TTerminal terminal) {
		this.terminal = terminal;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
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

	public List<TLog> getLogList() {
		return logList;
	}

	public void setLogList(List<TLog> logList) {
		this.logList = logList;
	}

	public boolean isSearching() {
		return isSearching;
	}

	public void setSearching(boolean isSearching) {
		this.isSearching = isSearching;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
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

	public int getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(int terminalId) {
		this.terminalId = terminalId;
	}

	public List<TTerminal> getTerminalList() {
		return terminalList;
	}

	public void setTerminalList(List<TTerminal> terminalList) {
		this.terminalList = terminalList;
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

	public AccountServiceImpl getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountServiceImpl accountService) {
		this.accountService = accountService;
	}

	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public TempPositionServiceImpl getTempPositionService() {
		return tempPositionService;
	}

	public void setTempPositionService(TempPositionServiceImpl tempPositionService) {
		this.tempPositionService = tempPositionService;
	}

	public String getSim() {
		return sim;
	}

	public int getSearchWeiyi() {
		return searchWeiyi;
	}

	public void setSearchWeiyi(int searchWeiyi) {
		this.searchWeiyi = searchWeiyi;
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

	public List<TTerminal> getTerminalExcelList() {
		return terminalExcelList;
	}

	public void setTerminalExcelList(List<TTerminal> terminalExcelList) {
		this.terminalExcelList = terminalExcelList;
	}

	public List<TCarInfo> getCarInfoList() {
		return carInfoList;
	}

	public void setCarInfoList(List<TCarInfo> carInfoList) {
		this.carInfoList = carInfoList;
	}

	public String getCols() {
		return cols;
	}

	public void setCols(String cols) {
		this.cols = cols;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public void setSim(String sim) {
		this.sim = sim;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public List<TArea> getAreas() {
		return areas;
	}

	public void setAreas(List<TArea> areas) {
		this.areas = areas;
	}

	public AreaServiceImpl getAreaService() {
		return areaService;
	}

	public void setAreaService(AreaServiceImpl areaService) {
		this.areaService = areaService;
	}

	public int getSelectAreaId() {
		return selectAreaId;
	}

	public void setSelectAreaId(int selectAreaId) {
		this.selectAreaId = selectAreaId;
	}

}
