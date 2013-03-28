package com.ztdz.actions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
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
import com.ztdz.tools.OrgLevel;
import com.ztdz.tools.Page;
import com.ztdz.tools.PositionConvertUtil;

/**
 * ��action ���ڷ���ϵͳ����Ա������������Ӧ
 * 
 * @author weijiyuan
 * 
 *         2012-5-24
 */
public class AdminAction extends ActionSupport implements SessionAware,
		ServletRequestAware {
	/**
	 * ����ģ�鹫������
	 */
	private HttpServletRequest request;// request
	private Map<String, Object> session; // session �Ự
	private TUser user; // �û���Ϣ
	private final String REDIRECT = "redirect";
	private String redActionName;// �ض��򵽵�action������

	private String page; // �û��Զ��巵��ҳ��
	private final String TARGET = "target";
	private final String SUCCESS = "success";

	// ��ҳ����
	private int total;// ����
	private int pageIndex;// ��ǰҳ
	private int pages;// ҳ������

	// ��spring ��ɷ���ע��
	private UserServiceImpl userService;
	private LogServiceImpl logService;
	private OrganizationServiceImpl organizationService;
	private AreaServiceImpl areaService;
	private CarInfoServiceImpl carInfoService;
	private AccountServiceImpl accountService;
	private TerminalServiceImpl terminalService;
	private TempPositionServiceImpl tempPositionService;

	// �������Ͻ���
	private String oldPwd, newPwd; // �û�������Ϣ �����޸�

	// ��ά�뼯���û����õ�����
	private List<TOrgainzation> orgs; // ������֯���� ����/��ά/��
	private List<TArea> areas; // ������Ϣ
	private int orgId;// ��ά�û��Ļ���id����������ڱ༭��ɾ��
	private int selectAreaId;
	private String orgSearchName;// ģ����ѯ
	private int provinceId;

	// ��ά�û��������
	private TOrgainzation yunwei; // ��ά������Ϣ
	private List<TUser> yunweiUserList;// ǰ̨Ҫ��ʾ����ά�����е��˻��б�
	private int yunweiUserId;// ��ά�û����˻�id����������ڱ༭��ɾ��
	private TUser yunweiUser;// ��ά�û����˻����������������user�������߸����

	// �����û��������
	private TOrgainzation jituan;// ���Ż�����Ϣ
	private List<TUser> jituanUserList;// ǰ̨Ҫ��ʾ����ά�����е��˻��б�
	private int jituanUserId;// �����û����˻�id����������ڱ༭��ɾ��
	private TUser jituanUser;// �����û����˻����������������user�������߸����
	// �����µķ�����ն�
	private List<TUser> groupList;// ĳ�����Ż����µ����з���
	private List<TTerminal> terminals;// ĳ�����µ������նˣ�
	private String sim;// ����simģ����ѯ�ն�
	private TOrgainzation orgainzation;
	private int groupId;
	private List<TUser> groupUserList;
	private TUser groupUser;
	private int groupUserId;
	private String option; // ��������
	private String value; // ����ֵ
	private int groupID;// ����ת���ն�

	// ��־�������
	private List<TLog> logList;// ǰ̨Ҫ��ʾ����־�б�
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");// ���ڸ�ʽʵ��
	private Date searchStartDate;
	private Date searchEndDate;

	// �����������
	private int areaId;
	private TArea area;

	// ������Ϣ����
	private TCarInfo carInfo;
	private int carTypeId;
	private List<TCarInfo> cars; // ������Ϣ

	// ����ѹ������
	private List<TAccount> accounts; // �˵���Ϣ
	private String orgName;// ����name
	private TAccount account;
	private int expense;// ��ֵ���

	// ���ݱ������
	private static final int PAGESIZE = 10;
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
	private int jituanId;// �������ŵ�id
	private List<TOrgainzation> jituanGroupList;// �����µ����з�������б�
	private List<TOrgainzation> jituanList;// �����б�
	private TTerminal terminal;// ���б༭���ն�
	private boolean isSearching = false;// ������Ա�ʾ�Ƿ�ǰ���б��Ǵ�����������У���Ϊ��������õĻ���ô���������ҳ��Ч
	private String searchString;// Ҫ������"����"

	// �ն˶�λ����
	private List<TTempPosition> tpositions; // ���пͻ���λ��Ϣ
	private int ptotal; // ����λ��Ϣ�ն���Ŀ
	private int outline; // �����ն���
	private int online; // �����ն���

	// ------------------------------------��������----------------------------------------//
	/**
	 * ����ϵͳ�û���������
	 * 
	 * @return
	 */
	public String saveBasicInfo() {
		if (oldPwd == null || "".equals(oldPwd)
				|| !user.getPwd().equals(oldPwd)) {
			request.setAttribute("result", "�������������");
		} else if (newPwd == null || "".equals(newPwd)) {
			request.setAttribute("result", "�����벻��Ϊ�գ�");
		} else {
			user.setPwd(newPwd);
			userService.updateUser(user);
			request.setAttribute("result", "�޸ĳɹ���");
			logService.addLog(new TLog(user, "��" + user.getName() + "��"
					+ "�޸��˻�������", new Date()));
		}
		page = "Admin_basicInfo";
		return TARGET;
	}

	// ------------------------------------��ά�û�����----------------------------------------//
	/**
	 * ��ת��������ά�����Ľ���
	 * 
	 * @return
	 */
	public String forwardAddYunwei() {
		yunwei = new TOrgainzation();// ��ֹ����ҳ��
		areas = areaService.findAll();// �õ������б�
		page = "Admin_addYunwei";
		return TARGET;
	}

	/**
	 * ����µ���ά����
	 * 
	 * @return
	 */
	public String addYunwei() {
		user = (TUser) session.get("user");
		TArea tArea = areaService.findById(selectAreaId);
		yunwei.setTArea(tArea);
		TOrgainzation rootOrg = organizationService.findRootOrg();// �ҵ�root����
		yunwei.setTOrgainzation(rootOrg);
		yunwei.setOrgLevel(OrgLevel.YUNWEI_LEVLE);// ��ά����
		yunwei.setRegistertime(new Date());
		organizationService.addOrganization(yunwei);
		request.setAttribute("result", "������ά�����ɹ���");
		logService.addLog(new TLog(user, "��" + user.getName() + "��"
				+ "�����һ����ά����������Ϊ��  " + yunwei.getName() + "�� idΪ "
				+ yunwei.getOrgId(), new Date()));
		page = "Admin_addYunwei";
		return TARGET;
	}

	/**
	 * ɾ��һ����ά����
	 * 
	 * @return
	 */
	public String deleteYunwei() {
		user = (TUser) session.get("user");
		yunwei = organizationService.findById(orgId);
		organizationService.delOrganization(orgId);
		logService.addLog(new TLog(user, "��" + user.getName() + "��"
				+ "ɾ����һ����ά����������Ϊ �� " + yunwei.getName() + "�� idΪ "
				+ yunwei.getOrgId(), new Date()));
		redActionName = "Admin_yunweiManager";
		return REDIRECT;
	}

	/**
	 * �༭һ����ά����
	 * 
	 * @return
	 */
	public String editYunweiInfo() {
		yunwei = organizationService.findById(orgId);
		areas = areaService.findAll();// �õ������б�
		System.err.println("aaaaaaaaaaaaaaaaaaaaaaaaaaa" + areas.size());
		page = "Admin_editYunwei";
		return TARGET;
	}

	/**
	 * ����һ����ά�����Ļ�����Ϣ
	 * 
	 * @return
	 */
	public String saveYunweiInfo() {

		user = (TUser) session.get("user");
		TArea tArea = areaService.findById(selectAreaId);
		yunwei.setTArea(tArea);
		organizationService.updateOrganization(yunwei);
		request.setAttribute("result", "�޸ĳɹ�");
		logService.addLog(new TLog(user, "��" + user.getName() + "��" + "�޸��ˡ�  "
				+ yunwei.getName() + "������Ϣ", new Date()));
		return editYunweiInfo();
	}

	/**
	 * �õ���ά�����е��˻��б�
	 * 
	 * @return
	 */
	public String yunweiUserManager() {
		findYunweiUserList();
		page = "Admin_yunweiUserManager";
		return TARGET;
	}

	/**
	 * �õ���ά�����е��˻��б�
	 * 
	 * @return
	 */
	public String findYunweiUserList() {
		yunwei = organizationService.findById(orgId);
		List<TUser> list = userService.findByOrgId(orgId);
		// �õ��ܼ�¼��
		total = list.size();
		// �õ���ҳ��
		if (total % Page.DEFAULT_PAGE_SIZE == 0) {
			pages = total / Page.DEFAULT_PAGE_SIZE;
		} else {
			pages = total / Page.DEFAULT_PAGE_SIZE + 1;
		}
		// �õ���ǰ��ҳ����
		if (pageIndex < 1)
			pageIndex = 1;
		if (pageIndex > pages)
			pageIndex = pages;
		yunweiUserList = userService.findByOrgId(orgId);
		page = "Admin_yunweiUserManager";
		return TARGET;
	}

	/**
	 * ���һ����ά�˻�֮ǰ�Ĳ���
	 * 
	 * @return
	 */
	public String beforeAddYunweiUser() {
		yunweiUser = new TUser();
		page = "Admin_addYunweiUser";
		return TARGET;
	}

	/**
	 * ���һ����ά�˻�
	 * 
	 * @return
	 */
	public String addYunweiUser() {
		if (userService.findByUserId(yunweiUser.getUserid()) != null) {
			request.setAttribute("result", "�û����Ѵ���");
			return SUCCESS;
		}
		user = (TUser) session.get("user");
		yunweiUser.setTOrgainzation(yunwei);
		yunweiUser.setRegistertime(new Date());
		userService.addUser(yunweiUser);
		request.setAttribute("result", "��ӳɹ�");
		logService.addLog(new TLog(user, "��" + user.getName() + "��" + "Ϊ"
				+ yunwei.getName() + "�����һ����Ϊ��  " + yunweiUser.getName()
				+ "�����˻�", new Date()));
		page = "Admin_addYunweiUser";
		return TARGET;
	}

	/**
	 * ɾ��һ����ά�����µ��˻�
	 * 
	 * @return
	 */
	public String deleteYunweiUser() {
		user = (TUser) session.get("user");
		yunwei = organizationService.findById(orgId);
		yunweiUser = userService.findById(yunweiUserId);
		userService.delUserById(yunweiUserId);
		logService.addLog(new TLog(user, "��" + user.getName() + "��" + "ɾ���ˡ�"
				+ yunwei.getName() + "����һ����ά�û�������Ϊ �� " + yunweiUser.getName()
				+ "��idΪ " + yunweiUser.getId(), new Date()));
		redActionName="Admin_yunweiUserManager";
		return REDIRECT;
	}

	/**
	 * �༭һ����ά�����µ��˻�
	 * 
	 * @return
	 */
	public String editYunweiUserInfo() {
		yunweiUser = userService.findById(yunweiUserId);
		page = "Admin_editYunweiUserInfo";
		return TARGET;
	}

	/**
	 * ����һ����ά�������˻�������Ϣ
	 * 
	 * @return
	 */
	public String saveYunweiUserInfo() {
		user = (TUser) session.get("user");
		yunwei = organizationService.findById(orgId);
		userService.updateUser(yunweiUser);
		logService.addLog(new TLog(user, "��" + user.getName() + "��" + "�޸��ˡ�"
				+ yunwei.getName() + "����һ����ά�û�  " + yunweiUser.getName()
				+ " ����Ϣ", new Date()));
		request.setAttribute("result", "�޸ĳɹ�");
		yunweiUser = userService.findById(yunweiUserId);// ����ִ���˺ܶ���sql��䣡user->org->users!
		page = "Admin_editYunweiUserInfo";
		return TARGET;
	}

	/**
	 * ���û��������ά��������ģ����ѯ
	 */
	public String yunweiSearch() {
		total = organizationService.getCountYunweiMohu(orgSearchName); // ��ȡ��ά��������
		// �õ���ҳ��
		if (total % Page.DEFAULT_PAGE_SIZE == 0) {
			pages = total / Page.DEFAULT_PAGE_SIZE;
		} else {
			pages = total / Page.DEFAULT_PAGE_SIZE + 1;
		}
		if (pageIndex < 1)
			pageIndex = 1;
		if (pageIndex > pages)// ��ʱ���ж�
			pageIndex = pages;
		orgs = organizationService.findYunweiMohu(orgSearchName,
				(pageIndex - 1) * Page.DEFAULT_PAGE_SIZE,
				Page.DEFAULT_PAGE_SIZE);
		page = "Admin_yunweiSearchResult";
		return TARGET;
	}

	// -----------------------------------�����û�����----------------------------------------//

	/**
	 * ��ת�����Ӽ��Ż����Ľ���
	 * 
	 * @return
	 */
	public String forwardAddJituan() {
		jituan = new TOrgainzation();// ��ֹ����ҳ��
		areas = areaService.findAll();// �õ������б�
		page = "Admin_addJituan";
		return TARGET;
	}

	/**
	 * ����µļ��Ż���
	 * 
	 * @return
	 */
	public String addJituan() {
		user = (TUser) session.get("user");
		TArea tArea = areaService.findById(selectAreaId);
		jituan.setTArea(tArea);
		TOrgainzation rootOrg = organizationService.findRootOrg();// �ҵ�root����
		jituan.setTOrgainzation(rootOrg);
		jituan.setBalance(0);
		jituan.setOrgLevel(OrgLevel.JITUAN_LEVEL);// ���Ż���
		jituan.setRegistertime(new Date());
		organizationService.addOrganization(jituan);
		request.setAttribute("result", "���Ӽ��Ż����ɹ�");
		logService.addLog(new TLog(user, "��" + user.getName() + "��"
				+ "�����һ�����Ż���������Ϊ��  " + jituan.getName() + "��idΪ "
				+ jituan.getOrgId(), new Date()));
		request.setAttribute("result", "���Ӽ��Ż����ɹ���");
		page = "Admin_addJituan";
		return TARGET;
	}

	/**
	 * ɾ��һ�����Ż���
	 * 
	 * @return
	 */
	public String deleteJituan() {
		user = (TUser) session.get("user");
		jituan = organizationService.findById(orgId);
		organizationService.delOrganization(orgId);
		logService.addLog(new TLog(user, "��" + user.getName() + "��"
				+ "ɾ����һ�����Ż���������Ϊ  ��" + jituan.getName() + "��idΪ "
				+ jituan.getOrgId(), new Date()));
		redActionName="Admin_jituanManager";
		return REDIRECT;
	}

	/**
	 * �༭һ�����Ż���
	 * 
	 * @return
	 */
	public String editJituanInfo() {
		jituan = organizationService.findById(orgId);
		areas = areaService.findAll();// �õ������б�
		page = "Admin_editJituan";
		return TARGET;
	}

	/**
	 * ����һ�����Ż����Ļ�����Ϣ
	 * 
	 * @return
	 */
	public String saveJituanInfo() {
		user = (TUser) session.get("user");
		TArea tArea = areaService.findById(selectAreaId);
		jituan.setTArea(tArea);
		organizationService.updateOrganization(jituan);
		request.setAttribute("result", "�޸ĳɹ�");
		logService.addLog(new TLog(user, "��" + user.getName() + "��" + "�޸��� �� "
				+ jituan.getName() + "������Ϣ", new Date()));
		page = "Admin_editJituan";
		return TARGET;
	}

	/**
	 * �õ����Ż����е��˻��б�
	 * 
	 * @return
	 */
	public String jituanUserManager() {
		findJituanUserList();
		page = "Admin_jituanUserManager";
		return TARGET;
	}

	/**
	 * �õ����Ż����е��˻��б�
	 * 
	 * @return
	 */
	public String findJituanUserList() {
		jituan = organizationService.findById(orgId);
		List<TUser> list = userService.findByOrgId(orgId);
		// �õ��ܼ�¼��
		total = list.size();
		// �õ���ҳ��
		if (total % Page.DEFAULT_PAGE_SIZE == 0) {
			pages = total / Page.DEFAULT_PAGE_SIZE;
		} else {
			pages = total / Page.DEFAULT_PAGE_SIZE + 1;
		}
		// �õ���ǰ��ҳ����
		if (pageIndex < 1)
			pageIndex = 1;
		if (pageIndex > pages)
			pageIndex = pages;
		jituanUserList = userService.findByOrgId(orgId);
		page = "Admin_jituanUserManager";
		return TARGET;
	}

	/**
	 * ���һ�������˻�֮ǰ�Ĳ���
	 * 
	 * @return
	 */
	public String beforeAddJituanUser() {
		jituanUser = new TUser();
		page = "Admin_addJituanUser";
		return TARGET;
	}

	/**
	 * ���һ�������˻�
	 * 
	 * @return
	 */
	public String addJituanUser() {
		if (userService.findByUserId(jituanUser.getUserid()) != null) {
			request.setAttribute("result", "�û����Ѵ���");
			return SUCCESS;
		}
		user = (TUser) session.get("user");
		jituanUser.setTOrgainzation(jituan);//
		jituanUser.setRegistertime(new Date());
		userService.addUser(jituanUser);
		request.setAttribute("result", "��ӳɹ�");
		logService.addLog(new TLog(user, "��" + user.getName() + "��" + "Ϊ��"
				+ jituan.getName() + "�������һ����Ϊ �� " + jituanUser.getName()
				+ "�����˻�", new Date()));
		return SUCCESS;
	}

	/**
	 * ɾ��һ�����Ż����µ��˻�
	 * 
	 * @return
	 */
	public String deleteJituanUser() {
		user = (TUser) session.get("user");
		jituanUser = userService.findById(jituanUserId);
		jituan = organizationService.findById(orgId);
		userService.delUserById(jituanUserId);
		logService.addLog(new TLog(user, "��" + user.getName() + "��" + "ɾ���ˡ�"
				+ jituan.getName() + "����һ�������û�������Ϊ  ��" + jituanUser.getName()
				+ "��idΪ " + jituanUser.getId(), new Date()));
		redActionName="Admin_jituanUserManager";
		return REDIRECT;
	}

	/**
	 * �༭һ�����Ż����µ��˻�
	 * 
	 * @return
	 */
	public String editJituanUserInfo() {
		jituanUser = userService.findById(jituanUserId);
		page = "Admin_editJituanUserInfo";
		return TARGET;
	}

	/**
	 * ����һ�����Ż������˻�������Ϣ
	 * 
	 * @return
	 */
	public String saveJituanUserInfo() {
		user = (TUser) session.get("user");
		jituan = organizationService.findById(orgId);
		userService.updateUser(jituanUser);
		logService.addLog(new TLog(user, "��" + user.getName() + "��" + "�޸��ˡ�"
				+ jituan.getName() + "����һ�������û� �� " + jituanUser.getName()
				+ "�� ����Ϣ", new Date()));
		request.setAttribute("result", "�޸ĳɹ�");
		page = "Admin_editJituanUserInfo";
		return TARGET;
	}

	/**
	 * ���û�����ļ��Ż�������ģ����ѯ
	 */
	public String jituanSearch() {
		total = organizationService.getCountJituanMohu(orgSearchName); // ��ȡ��ά��������
		// �õ���ҳ��
		if (total % Page.DEFAULT_PAGE_SIZE == 0) {
			pages = total / Page.DEFAULT_PAGE_SIZE;
		} else {
			pages = total / Page.DEFAULT_PAGE_SIZE + 1;
		}
		if (pageIndex < 1)
			pageIndex = 1;
		if (pageIndex > pages)
			pageIndex = pages;
		orgs = organizationService.findJituanMohu(orgSearchName,
				(pageIndex - 1) * Page.DEFAULT_PAGE_SIZE,
				Page.DEFAULT_PAGE_SIZE);
		page = "Admin_jituanSearchResult";
		return TARGET;
	}

	// --------------------------------�����µķ���----------------------------------------//
	/**
	 * ��ʾ�����µ����з������
	 * 
	 * @return
	 */
	public String groupManager() {
		findGroupList();
		page = "Admin_groupManager";
		return TARGET;
	}

	/**
	 * ���ҵ�ǰ�����û������µķ����������ҳ��
	 * 
	 * @return
	 */
	public void findGroupList() {
		jituan = organizationService.findById(orgId);
		total = organizationService.getCountAllGroupsByBloc(orgId);
		// �õ���ҳ��
		if (total % Page.DEFAULT_PAGE_SIZE == 0) {
			pages = total / Page.DEFAULT_PAGE_SIZE;
		} else {
			pages = total / Page.DEFAULT_PAGE_SIZE + 1;
		}
		// �õ���ǰ��ҳ����
		if (pageIndex < 1)
			pageIndex = 1;
		if (pageIndex > pages)
			pageIndex = pages;
		groupList = organizationService.findAllGroupsByBloc(orgId,
				(pageIndex - 1) * Page.DEFAULT_PAGE_SIZE,
				Page.DEFAULT_PAGE_SIZE);
	}

	/**
	 * ����µķ������֮ǰ�Ĳ���
	 * 
	 * @return
	 */
	public String beforeAddGroup() {
		orgainzation = new TOrgainzation();// ��ֹ����ҳ��
		areas = areaService.findAll();// �õ������б�
		page = "Admin_addGroup";
		return TARGET;
	}

	/**
	 * ����µķ������
	 * 
	 * @return
	 */
	public String addGroup() {
		orgainzation.setTOrgainzation(jituan);
		orgainzation.setFeestandard(jituan.getFeestandard());
		orgainzation.setOrgLevel(3);// �������
		TArea area = areaService.findById(selectAreaId);
		orgainzation.setTArea(area);
		orgainzation.setBalance(0);
		orgainzation.setFeestandard(jituan.getFeestandard());
		orgainzation.setRegistertime(new Date());// ע��ʱ��Ϊ��ǰʱ��
		organizationService.addOrganization(orgainzation);
		request.setAttribute("result", "���ӷ�������ɹ�");

		user = (TUser) session.get("user");
		logService.addLog(new TLog(user, "��" + user.getName() + "��" + "Ϊ��"
				+ jituan.getName() + "��������µķ��������" + orgainzation.getName()
				+ "��", new Date()));
		page = "Admin_addGroup";
		return TARGET;
	}

	/**
	 * ���û�����ķ����������ģ����ѯ
	 */
	public String groupSearch() {
		total = organizationService.getCountMohuByJituan(orgSearchName, orgId);
		// �õ���ҳ��
		if (total % Page.DEFAULT_PAGE_SIZE == 0) {
			pages = total / Page.DEFAULT_PAGE_SIZE;
		} else {
			pages = total / Page.DEFAULT_PAGE_SIZE + 1;
		}
		// �õ���ǰ��ҳ����
		if (pageIndex < 1)
			pageIndex = 1;
		if (pageIndex > pages)
			pageIndex = pages;
		groupList = organizationService.findMohuByJituan(orgSearchName, orgId,
				(pageIndex - 1) * Page.DEFAULT_PAGE_SIZE,
				Page.DEFAULT_PAGE_SIZE);
		page = "Admin_groupSearchResult";
		return TARGET;
	}

	/**
	 * ɾ��һ���������
	 * 
	 * @return
	 */
	public String deleteGroup() {
		user = (TUser) session.get("user");
		orgainzation = organizationService.findById(groupId);
		organizationService.delOrganization(groupId);
		logService.addLog(new TLog(user, "��" + user.getName() + "��" + "ɾ���ˡ�"
				+ jituan.getName() + "���µķ��������" + orgainzation.getName() + "��",
				new Date()));
		redActionName="Admin_groupManager";
		return REDIRECT;
	}

	/**
	 * �༭һ���������
	 * 
	 * @return
	 */
	public String editGroupInfo() {
		orgainzation = organizationService.findById(groupId);
		areas = areaService.findAll();// �õ������б�
		jituan = orgainzation.getTOrgainzation();// �õ��������һ������
		page = "Admin_editGroupInfo";
		return TARGET;
	}

	/**
	 * ����һ����������Ļ�����Ϣ
	 * 
	 * @return
	 */
	public String saveGroupInfo() {
		user = (TUser) session.get("user");
		TArea tArea = areaService.findById(selectAreaId);
		orgainzation.setTArea(tArea);
		organizationService.updateOrganization(orgainzation);
		logService.addLog(new TLog(user, "��" + user.getName() + "��" + "�޸���"
				+ jituan.getName() + "�µķ��������" + orgainzation.getName() + "��",
				new Date()));
		request.setAttribute("result", "�޸ĳɹ���");
		page = "Admin_editGroupInfo";
		return TARGET;
	}

	/**
	 * �õ���������е��˻��б�
	 * 
	 * @return
	 */
	public String groupUserManager() {
		findGroupUserList();
		page = "Admin_groupUserManager";
		return TARGET;
	}

	/**
	 * �����ն˶�λ
	 * 
	 * @return
	 */
	public String allLocations() {
		List<TTerminal> terminals = terminalService.findByOrg(groupId);
		Iterator<TTerminal> it = terminals.iterator();
		tpositions = new ArrayList<TTempPosition>();
		total = terminals.size(); // �ն�����
		ptotal = 0;
		online = 0;
		outline = 0;
		while (it.hasNext()) {
			TTerminal p = it.next();
			if (p.getTTempPositions() != null
					&& p.getTTempPositions().size() == 1) {
				TTempPosition ttp = p.getTTempPositions().get(0);
				if (ttp.getLatitude() != -1) { // �����ǰ�ն˺��ж�λ��Ϣ
					tpositions.add(PositionConvertUtil.convert(ttp));
					ptotal++;
					if (p.getNetstatus() == 1)
						online++;
					else
						outline++;
				}
			}
		}
		System.err.println(tpositions.size() + "^^^^^^^^^^^^^^^^^6");
		page = "Terminal_allLocations";
		return TARGET;
	}

	/**
	 * 
	 * @return
	 */
	public String findGroupUserList() {
		orgainzation = organizationService.findById(groupId);
		// �õ��ܼ�¼��
		total = userService.findByOrgId(groupId).size();
		// �õ���ҳ��
		if (total % Page.DEFAULT_PAGE_SIZE == 0) {
			pages = total / Page.DEFAULT_PAGE_SIZE;
		} else {
			pages = total / Page.DEFAULT_PAGE_SIZE + 1;
		}
		// �õ���ǰ��ҳ����
		if (pageIndex < 1)
			pageIndex = 1;
		if (pageIndex > pages)
			pageIndex = pages;
		groupUserList = userService.findByOrgId(groupId);
		page = "Admin_groupUserManager";
		return TARGET;
	}

	/**
	 * ���һ���˻�֮ǰ�Ĳ���
	 * 
	 * @return
	 */
	public String beforeAddGroupUser() {
		groupUser = new TUser();
		page = "Admin_addGroupUser";
		return TARGET;
	}

	/**
	 * ���һ���˻�
	 * 
	 * @return
	 */
	public String addGroupUser() {
		if (userService.findByUserId(groupUser.getUserid()) != null) {
			request.setAttribute("result", "�û����Ѵ���");
			return SUCCESS;
		}
		user = (TUser) session.get("user");
		groupUser.setTOrgainzation(orgainzation);// orgainzation������ֵ�ģ�����Σ�
		groupUser.setRegistertime(new Date());
		userService.addUser(groupUser);
		logService.addLog(new TLog(user, "��" + user.getName() + "��" + "Ϊ��"
				+ jituan.getName() + "���µķ��������" + orgainzation.getName()
				+ "��������˻���" + groupUser.getName() + "��", new Date()));
		request.setAttribute("result", "��ӳɹ�");
		return SUCCESS;
	}

	/**
	 * ɾ��һ����������µ��˻�
	 * 
	 * @return
	 */
	public String deleteGroupUser() {
		user = (TUser) session.get("user");
		TUser tUser = userService.findById(groupUserId);
		userService.delUserById(groupUserId);
		logService.addLog(new TLog(user, "��" + user.getName() + "��Ϊ��"
				+ jituan.getName() + "��ɾ���˷��������" + orgainzation.getName()
				+ "���µ��˻���" + tUser.getName() + "��", new Date()));
		redActionName="Admin_groupUserManager";
		return REDIRECT;
	}

	/**
	 * �༭һ����������µ��˻�
	 * 
	 * @return
	 */
	public String editGroupUserInfo() {
		groupUser = userService.findById(groupUserId);
		page = "Admin_editGroupUserInfo";
		return TARGET;
	}

	/**
	 * ����һ������������˻�������Ϣ
	 * 
	 * @return
	 */
	public String saveGroupUserInfo() {
		user = (TUser) session.get("user");
		userService.updateUser(groupUser);
		request.setAttribute("result", "�޸ĳɹ���");
		logService.addLog(new TLog(user, "��" + user.getName() + "��Ϊ"
				+ jituan.getName() + "���޸��˷��������" + orgainzation.getName()
				+ "���µ��˻���" + groupUser.getName() + "������Ϣ", new Date()));
		page = "Admin_editGroupUserInfo";
		return TARGET;
	}

	// ---------------------�����µķ�������µ��ն�------------------------------//
	/**
	 * �鿴ĳ�������µ��ն�
	 * 
	 * @@return
	 */
	public String terminalManager() {
		total = terminalService.getCountByOrg(groupId);
		orgainzation = organizationService.findById(groupId);
		// �õ���ҳ��
		if (total % Page.DEFAULT_PAGE_SIZE == 0) {
			pages = total / Page.DEFAULT_PAGE_SIZE;
		} else {
			pages = total / Page.DEFAULT_PAGE_SIZE + 1;
		}
		// �õ���ǰ��ҳ����
		if (pageIndex < 1)
			pageIndex = 1;
		if (pageIndex > pages)
			pageIndex = pages;
		terminals = terminalService.findByOrg(groupId, (pageIndex - 1)
				* Page.DEFAULT_PAGE_SIZE, Page.DEFAULT_PAGE_SIZE);
		page = "Admin_terminalManager";
		return TARGET;
	}

	/**
	 * ��������������ѯ�ն�
	 */
	public String terminalSearch() {
		if ("1".equals(option)) { // �������
			total = terminalService.getCountMohuByOrgAndCarNumber(value,
					groupId);
		} else if ("2".equals(option)) {// ��ϵ��
			total = terminalService
					.getCountMohuByOrgAndUserName(value, groupId);
		} else if ("3".equals(option)) { // �ն˺���
			total = terminalService.getCountMohuByOrgAndSim(value, groupId);
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
			terminals = terminalService.findMohuByOrgAndCarNumber(value,
					groupId, (pageIndex - 1) * Page.DEFAULT_PAGE_SIZE,
					Page.DEFAULT_PAGE_SIZE);
		} else if ("2".equals(option)) {// ��ϵ��
			terminals = terminalService.findMohuByOrgAndUserName(value,
					groupId, (pageIndex - 1) * Page.DEFAULT_PAGE_SIZE,
					Page.DEFAULT_PAGE_SIZE);
		} else if ("3".equals(option)) { // �ն˺���
			terminals = terminalService.findMohuByOrgAndSim(value, groupId,
					(pageIndex - 1) * Page.DEFAULT_PAGE_SIZE,
					Page.DEFAULT_PAGE_SIZE);
		}
		page = "Admin_terminalSearchResult";
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
	 * �����ն��û���Ϣ
	 * 
	 * @@return
	 */
	public String saveTerminal() {
		TTerminal temp = terminalService.findBySim(terminal.getSim());
		if (temp != null) {
			request.setAttribute("result", "�ն˱���Ѵ���!");
			page = "Admin_addTerminal";
			return TARGET;
		}
		temp = terminalService.findByPhone(terminal.getPhone());
		if (temp != null) {
			request.setAttribute("result", "��SIM�����Ѿ�����!");
			page = "Admin_addTerminal";
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
		page = "Admin_addTerminal";
		request.setAttribute("result", "�����ն��û��ɹ�!");
		user = (TUser) session.get("user");
		logService.addLog(new TLog(user, "��" + user.getName() + "��" + "�������նˡ�"
				+ terminal.getSim() + "��", new Date()));
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
		user = (TUser) session.get("user");
		logService.addLog(new TLog(user, "��" + user.getName() + "��" + "ɾ�����նˡ�"
				+ sim + "��", new Date()));
		redActionName="Admin_terminalManager";
		return REDIRECT;
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
		page = "Admin_editTerminal";
		user = (TUser) session.get("user");
		logService.addLog(new TLog(user, "��" + user.getName() + "��" + "�޸����նˡ�"
				+ terminal.getSim() + "��", new Date()));
		request.setAttribute("result", "�޸ĳɹ���");
		return TARGET;
	}

	// ��ת��ת���ն�ҳ��
	public String changeTerminal() {

		terminal = terminalService.findBySim(sim);
		orgs = organizationService.findAllBloc();
		return SUCCESS;
	}

	// ת���ն�
	public String transferTerminal() {
		TOrgainzation t1 = organizationService.findById(groupID);
		terminal.setTOrgainzation(t1);
		terminalService.updateTerminal(terminal);
		request.setAttribute("result", "ת���ն˳ɹ���");

		user = (TUser) session.get("user");
		logService.addLog(new TLog(user, "��" + user.getName() + "��" + "�޸����նˡ�"
				+ terminal.getSim() + "��", new Date()));

		page = "Admin_changeTerminal";
		return TARGET;
	}

	// -----------------------------------��־����----------------------------------------//
	/**
	 * ��־����
	 * 
	 * @return
	 */
	public String logManager() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		searchEndDate = calendar.getTime();
		calendar.add(Calendar.DATE, -2);// ǰ������賿
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
			page = "Admin_logManager";
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
		// �õ��ܼ�¼��
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(searchEndDate);
		calendar.add(Calendar.DATE, 1);// ��һ����賿
		searchEndDate = calendar.getTime();
		total = logService
				.getCountByTimeBetween(searchStartDate, searchEndDate);
		// �õ���ҳ��
		if (total % Page.DEFAULT_PAGE_SIZE == 0) {
			pages = total / Page.DEFAULT_PAGE_SIZE;
		} else {
			pages = total / Page.DEFAULT_PAGE_SIZE + 1;
		}
		// �õ���ǰ��ҳ����
		if (pageIndex < 1)
			pageIndex = 1;
		if (pageIndex > pages)
			pageIndex = pages;
		logList = logService.findByTimeBetween(searchStartDate, searchEndDate,
				(pageIndex - 1) * Page.DEFAULT_PAGE_SIZE,
				Page.DEFAULT_PAGE_SIZE);
		calendar.add(Calendar.DATE, -1);// ʹҳ����ʾ��������ϰ��
		searchEndDate = calendar.getTime();
		page = "Admin_logManager";
		return TARGET;
	}

	// -----------------------------------���ݱ������---------------------------------------//
	// -----------------------------------������Ϣ����---------------------------------------//
	/**
	 * ɾ��һ��������Ϣ
	 * 
	 * @return
	 */
	public String deleteCarInfo() {
		user = (TUser) session.get("user");
		TCarInfo carInfo = carInfoService.findById(carTypeId);
		carInfoService.deleteCarInfo(carTypeId);
		logService.addLog(new TLog(user, "��" + user.getName() + "��" + "ɾ����"
				+ "��Ϊ�� " + carInfo.getTypeName() + "�� �ĳ���", new Date()));
		return carManager();
	}

	/**
	 * ���һ��������Ϣ֮ǰ�Ĳ���
	 * 
	 * @return
	 */
	public String forwardAddCarInfo() {
		carInfo = new TCarInfo();
		page = "Admin_addCarInfo";
		return TARGET;
	}

	/**
	 * ���һ��������Ϣ
	 * 
	 * @return
	 */
	public String addCarInfo() {
		user = (TUser) session.get("user");
		carInfoService.addCarInfo(carInfo);
		logService.addLog(new TLog(user, "��" + user.getName() + "��" + "������"
				+ "��Ϊ�� " + carInfo.getTypeName() + "�� �ĳ���", new Date()));
		return carManager();
	}

	/**
	 * �༭һ������
	 * 
	 * @return
	 */
	public String editCarInfo() {
		carInfo = carInfoService.findById(carTypeId);
		page = "Admin_editCarInfo";
		return TARGET;
	}

	/**
	 * ����һ��������Ϣ
	 * 
	 * @return
	 */
	public String saveCarInfo() {
		user = (TUser) session.get("user");
		carInfoService.updateCarInfo(carInfo);
		logService.addLog(new TLog(user, "��" + user.getName() + "��" + "�޸���"
				+ "��Ϊ ��" + carInfo.getTypeName() + " �ĳ�����Ϣ", new Date()));
		return carManager();
	}

	// -----------------------------------������Ϣ����---------------------------------------//
	/**
	 * ɾ��һ��������Ϣ
	 * 
	 * @return
	 */
	public String deleteArea() {
		user = (TUser) session.get("user");
		TArea area = areaService.findById(areaId);
		areaService.deleleArea(areaId);
		logService.addLog(new TLog(user, "��" + user.getName() + "��" + "ɾ����"
				+ "��Ϊ�� " + area.getName() + "���ĵ�����Ϣ", new Date()));
		return areaManager();
	}

	/**
	 * ���һ��������Ϣ֮ǰ�Ĳ���
	 * 
	 * @return
	 */
	public String forwardAddArea() {
		area = new TArea();
		page = "Admin_addArea";
		return TARGET;
	}

	/**
	 * ���һ��������Ϣ
	 * 
	 * @return
	 */
	public String addArea() {
		user = (TUser) session.get("user");
		areaService.addArea(area);
		logService.addLog(new TLog(user, "��" + user.getName() + "��" + "������"
				+ "��Ϊ ��" + area.getName() + "���ĵ�����Ϣ", new Date()));
		return areaManager();
	}

	/**
	 * �༭һ������
	 * 
	 * @return
	 */
	public String editArea() {
		area = areaService.findById(areaId);
		page = "Admin_editArea";
		return TARGET;
	}

	/**
	 * ����һ������
	 * 
	 * @return
	 */
	public String saveArea() {
		user = (TUser) session.get("user");
		areaService.updateArea(area);
		logService.addLog(new TLog(user, "��" + user.getName() + "��" + "�޸���"
				+ "��Ϊ�� " + area.getName() + "���ĵ�����Ϣ", new Date()));
		return areaManager();
	}

	// ------------------------------------����ѹ���---------------------------------------//

	/**
	 * ������ֵ����
	 */
	public String beforeRecharge() {
		jituan = organizationService.findById(orgId);
		page = "Admin_beforeRecharge";
		return TARGET;
	}

	/**
	 * �����ų�ֵ
	 * 
	 * @return
	 */
	public String recharge() {
		user = (TUser) session.get("user");
		// �������Ҫ ����ֹ���ס������ݿ�������ȡjituan����Ϣ�������ÿͻ��˷��ص���Ϣ
		jituan = organizationService.findById(jituan.getOrgId());
		jituan.setBalance(jituan.getBalance() + expense);
		organizationService.updateOrganization(jituan);
		request.setAttribute("result2", "��ֵ�ɹ�");

		logService.addLog(new TLog(user, "��" + user.getName() + "��" + "Ϊ��"
				+ jituan.getName() + "����ֵ" + expense + "Ԫ", new Date()));

		account = new TAccount();
		account.setExpense(expense);
		account.setPaiddate(new Date());

		TUser user2 = (TUser) session.get("user");
		account.setPaider(user2.getName());
		account.setTOrgainzation(jituan);
		account.setRemark(user2.getName() + "����" + jituan.getName() + "����ֵ"
				+ expense + "Ԫ");

		accountService.addAccount(account);
		page = "Admin_beforeRecharge";
		return TARGET;
	}

	/**
	 * ��ֵ�˵�����
	 * 
	 * @return
	 */
	public String accountManager() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		searchEndDate = calendar.getTime();
		calendar.add(Calendar.DATE, -2);// ǰ������賿
		searchStartDate = calendar.getTime();// �趨��ʼ�ͽ���ʱ��
		findAccounts();
		return SUCCESS;
	}

	/**
	 * ��ֵ�˵�����
	 * 
	 * @return
	 */
	public String accountSearch() {
		// ͨ��ǰ̨�����ÿ�ʼ�ͽ���ʱ�䣬����Ҫ��һЩ�ж�
		if (searchEndDate.before(searchStartDate)) {
			request.setAttribute("result", "ʱ�����������뱣֤����ʱ���ڿ�ʼʱ��֮��");
			page = "Admin_accountManager";
			return TARGET;
		}
		findAccounts();
		return TARGET;
	}

	/**
	 * �õ�ָ��ʱ��֮�ڵĳ�ֵ�˵��б����ڷ�ҳ
	 * 
	 * @return
	 */
	public String findAccounts() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(searchEndDate);
		calendar.add(Calendar.DATE, 1);// ��һ����賿
		searchEndDate = calendar.getTime();
		// �õ��ܼ�¼��
		total = accountService.getCountByOrgLevel(OrgLevel.JITUAN_LEVEL,
				searchStartDate, searchEndDate);
		// �õ���ҳ��
		if (total % Page.DEFAULT_PAGE_SIZE == 0) {
			pages = total / Page.DEFAULT_PAGE_SIZE;
		} else {
			pages = total / Page.DEFAULT_PAGE_SIZE + 1;
		}
		// �õ���ǰ��ҳ����
		if (pageIndex < 1)
			pageIndex = 1;
		if (pageIndex > pages)
			pageIndex = pages;
		accounts = accountService.findByOrgLevel(OrgLevel.JITUAN_LEVEL,
				searchStartDate, searchEndDate, (pageIndex - 1)
						* Page.DEFAULT_PAGE_SIZE, Page.DEFAULT_PAGE_SIZE);
		calendar.add(Calendar.DATE, -1);// ʹҳ����ʾ��������ϰ��
		searchEndDate = calendar.getTime();
		page = "Admin_accountManager";
		return TARGET;
	}

	// -------------------------------����ϵͳ���Ĳ˵�����¼�-----------------------------------//
	// -------------------------------����ϵͳ���Ĳ˵�����¼�-----------------------------------//
	// -------------------------------����ϵͳ���Ĳ˵�����¼�-----------------------------------//
	/**
	 * ��ѯϵͳ����Ա��������
	 * 
	 * @return
	 */
	public String basicInfo() {
		// ֱ�ӴӻỰ�л�ȡ
		user = (TUser) session.get("user");
		return SUCCESS;
	}

	/**
	 * ��ά����
	 * 
	 * @return
	 */
	public String yunweiManager() {
		total = organizationService.getCountAllOM(); // ��ȡ��ά��������
		// �õ���ҳ��
		if (total % Page.DEFAULT_PAGE_SIZE == 0) {
			pages = total / Page.DEFAULT_PAGE_SIZE;
		} else {
			pages = total / Page.DEFAULT_PAGE_SIZE + 1;
		}
		if (pageIndex < 1)
			pageIndex = 1;
		if (pageIndex > pages)// ��ʱ���ж�
			pageIndex = pages;
		orgs = organizationService.findAllOM((pageIndex - 1)
				* Page.DEFAULT_PAGE_SIZE, Page.DEFAULT_PAGE_SIZE);
		page = "Admin_yunweiManager";
		return TARGET;
	}

	/**
	 * ���Ż�������
	 * 
	 * @return
	 */
	public String jituanManager() {
		total = organizationService.getCountAllBloc(); // ��ȡ���Ż�������
		// �õ���ҳ��
		if (total % Page.DEFAULT_PAGE_SIZE == 0) {
			pages = total / Page.DEFAULT_PAGE_SIZE;
		} else {
			pages = total / Page.DEFAULT_PAGE_SIZE + 1;
		}
		if (pageIndex < 1)
			pageIndex = 1;
		if (pageIndex > pages)// ��ʱ���ж�
			pageIndex = pages;
		orgs = organizationService.findAllBloc((pageIndex - 1)
				* Page.DEFAULT_PAGE_SIZE, Page.DEFAULT_PAGE_SIZE);
		page = "Admin_jituanManager";
		return TARGET;
	}

	/**
	 * Ŀ������ ������Ϣ����
	 * 
	 * @return
	 */
	public String carManager() {
		total = carInfoService.getCountAll();
		// �õ���ҳ��
		if (total % Page.DEFAULT_PAGE_SIZE == 0) {
			pages = total / Page.DEFAULT_PAGE_SIZE;
		} else {
			pages = total / Page.DEFAULT_PAGE_SIZE + 1;
		}
		if (pageIndex < 1)
			pageIndex = 1;
		if (pageIndex > pages)// ��ʱ���ж�
			pageIndex = pages;
		cars = carInfoService.findAll((pageIndex - 1) * Page.DEFAULT_PAGE_SIZE,
				Page.DEFAULT_PAGE_SIZE);
		page = "Admin_carManager";
		return TARGET;
	}

	/**
	 * ������Ϣ����
	 * 
	 * @return
	 */
	public String areaManager() {
		total = areaService.getCountAll();
		// �õ���ҳ��
		if (total % Page.DEFAULT_PAGE_SIZE == 0) {
			pages = total / Page.DEFAULT_PAGE_SIZE;
		} else {
			pages = total / Page.DEFAULT_PAGE_SIZE + 1;
		}
		if (pageIndex < 1)
			pageIndex = 1;
		if (pageIndex > pages)
			pageIndex = pages;
		areas = areaService.findAll((pageIndex - 1) * Page.DEFAULT_PAGE_SIZE,
				Page.DEFAULT_PAGE_SIZE);
		page = "Admin_areaManager";
		return TARGET;
	}

	/**
	 * ���ó�ֵ
	 * 
	 * @return
	 */
	public String expenseManager() {
		total = organizationService.getCountAllBloc(); // ��ȡ���Ż�������
		// �õ���ҳ��
		if (total % Page.DEFAULT_PAGE_SIZE == 0) {
			pages = total / Page.DEFAULT_PAGE_SIZE;
		} else {
			pages = total / Page.DEFAULT_PAGE_SIZE + 1;
		}
		if (pageIndex < 1)
			pageIndex = 1;
		if (pageIndex > pages)
			pageIndex = pages;
		orgs = organizationService.findAllBloc((pageIndex - 1)
				* Page.DEFAULT_PAGE_SIZE, Page.DEFAULT_PAGE_SIZE);
		page = "Admin_expenseManager";
		return TARGET;
	}

	/**
	 * ���ó�ֵ�µ���������
	 */
	public String expenseJituanSearch() {
		total = organizationService.getCountJituanMohu(orgSearchName); // ��ȡ��ά��������
		// �õ���ҳ��
		if (total % Page.DEFAULT_PAGE_SIZE == 0) {
			pages = total / Page.DEFAULT_PAGE_SIZE;
		} else {
			pages = total / Page.DEFAULT_PAGE_SIZE + 1;
		}
		if (pageIndex < 1)
			pageIndex = 1;
		if (pageIndex > pages)
			pageIndex = pages;
		orgs = organizationService.findJituanMohu(orgSearchName,
				(pageIndex - 1) * Page.DEFAULT_PAGE_SIZE,
				Page.DEFAULT_PAGE_SIZE);
		page = "Admin_expenseManagerSearchResult";
		return TARGET;
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////

	// ------------------------------ ���ݱ������
	// --------------------------------------//

	/**
	 * ���ݱ�������б�
	 */
	public String excelManager() {

		user = (TUser) session.get("user");
		isSearching = false;
		searchString = "";
		cols = "";
		searchWeiyi = -1;// �������Ҫ�������ʱ��������
		onoff = 2;
		pageIndex = 0;// ע�����︳ֵ�仯��Ϊ�˱���ǰ����б�ͺ�����б�����ͻ
		// �õ����еļ���
		jituanList = organizationService.findAllBloc();
		// �õ���ά�����еķ���
		groupList = organizationService.findAllGroupsByBloc(user
				.getTOrgainzation().getOrgId());
		// �õ����еĳ�����
		carInfoList = carInfoService.findAll();
		findTerminalExcelList();
		return SUCCESS;
	}

	/**
	 * �õ������µķ����б�
	 */
	public String findJiTuanGroupList() {
		jituanGroupList = organizationService.findAllGroupsByBloc(jituanId);
		page = "Admin_excelManager";
		return TARGET;
	}

	public String terminalExcelSearch() {
		isSearching = true;
		findTerminalExcelList();
		page = "Admin_excelManager";
		return TARGET;
	}

	/**
	 * �õ�����������ն��б�
	 * 
	 * @return
	 */
	public String findTerminalExcelList() {
		terminalExcelList = new ArrayList<TTerminal>();
		if (!isSearching) {
			total = 0;// ע�����︳ֵ�仯��Ϊ�˱���ǰ����б�ͺ�����б�����ͻ
			pages = 0;
			pageIndex = 0;
			page = "Admin_excelManager";
			return TARGET;
		}

		if (searchEndDate == null && searchFromDate == null && onoff == 2
				&& jituanId == 0 && searchGroupId == 0 && carType == 0) {
			total = 0;// ע�����︳ֵ�仯��Ϊ�˱���ǰ����б�ͺ�����б�����ͻ
			pages = 0;
			pageIndex = 0;
			page = "Admin_excelManager";
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
		terminalExcelList = terminalService.findAllByCriteria(
				detachedCriteria2, PAGESIZE * (pageIndex - 1), PAGESIZE);
//	    Calendar calendar = Calendar.getInstance();
//		calendar.setTime(searchEndDate);
//		calendar.add(Calendar.DATE, -1);// ��������ϰ��
//		searchEndDate = calendar.getTime();

		page = "Admin_excelManager";
		return TARGET;
	}

	// // �õ���ǰ��ģ����ѯ����
	// private DetachedCriteria getCurrentMohuCriteria() {
	// DetachedCriteria criteria = DetachedCriteria.forClass(TTerminal.class);
	// if (searchWeiyi == 1) {// ���� �ն˱�� ����
	// // terminal = terminalService.findBySim(searchString);
	// criteria.add(Restrictions.like("sim", searchString, MatchMode.ANYWHERE));
	// } else if (searchWeiyi == 2) {// �����ն��ֻ�������
	// // terminal = terminalService.findByPhone(searchString);
	// criteria.add(Restrictions.like("phone", searchString,
	// MatchMode.ANYWHERE));
	// } else if (searchWeiyi == 3) {// ������ -- Ŀ�����к�
	// // terminal = terminalService.findByCarnumber(searchString);
	// criteria.add(Restrictions.like("carnumber", searchString,
	// MatchMode.ANYWHERE));
	// } else if (searchWeiyi == 4) {// �û�����
	// // terminal = terminalService.findByUsername(searchString);
	// criteria.add(Restrictions.like("username", searchString,
	// MatchMode.ANYWHERE));
	// } else if (searchWeiyi == 5) {//
	// // terminal = terminalService.findByPrincipal(searchString);
	// criteria.add(Restrictions.like("principal", searchString,
	// MatchMode.ANYWHERE));
	// }
	// return criteria;
	// }

	// �õ���ǰ����ϲ�ѯ����
	private DetachedCriteria getCurrentCriteria() {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(TTerminal.class);
		if (searchFromDate != null) {
			detachedCriteria.add(Restrictions.ge("registertime", searchFromDate));
		}
		Date dd;
		if (searchEndDate != null) {
			dd = searchEndDate;
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dd);
			calendar.add(Calendar.DATE, 1);// ��һ����賿
			dd = calendar.getTime();
			detachedCriteria.add(Restrictions.le("registertime", dd));
		}

		if (onoff != 2) {
			detachedCriteria.add(Restrictions.eq("netstatus", onoff));
		}
		if (carType != 0) {
			detachedCriteria.createAlias("TCarInfo", "carInfo").add(
					Restrictions.eq("carInfo.carTypeId", carType));
		}
		// ������Ҫע�⣬���ѡ���˼��Ŷ�û��ѡ�з���Ļ��������������µ����е��ն�
		if (searchGroupId != 0) {
			detachedCriteria.add(Restrictions.eq("TOrgainzation.orgId",
					searchGroupId));
		} else {
			if (jituanId != 0) {
				detachedCriteria.createAlias("TOrgainzation", "t")
						.createAlias("t.TOrgainzation", "tt")
						.add(Restrictions.eq("tt.orgId", jituanId));
			}
		}
		return detachedCriteria;
	}

	// ����ѡ�е��е�������
	public String excelExport() {
		String[] colValues = cols.split(",");
		if (colValues.length == 0) {
			page = "Admin_excelManager";
			return TARGET;
		}
		// /////////////////////////////// ����Excel��ʼ
		// /////////////////////////////////////////////
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
			String[] title = { "�ն˱��", "�ն�SIM����", "Ŀ�����к�", "Ŀ������", "Ŀ���ͺ�",
					"����ʱ��", "����ʼʱ��", "�������ʱ��", "����״̬", "�û���", "��ϵ�绰", "ծȯ������",
					"��������", "��������", "����״̬", "����ʱ�����(����)", "��ƿ��ѹ", "�ź�ǿ��",
					"����״̬", "����ʱ��", "��λģʽ", "��վ���", "С����Ԫ��", "γ�ȷ���", "γ��",
					"���ȷ���", "����" };

			boolean[] t = new boolean[title.length];

			System.out.println(colValues.length);// 21
			System.out.println(title.length);// 27
			int i = 0;
			// ���Ƿ񵼳�
			for (; i < colValues.length - 1; i++) {
				if (Integer.parseInt(colValues[i]) == 1) {
					t[i] = true;
				} else {
					t[i] = false;
				}
			}

			System.out.println(i);

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
			for (int k = 0; k < title.length; k++) {
				if (t[k]) {
					label = new jxl.write.Label(index, 0, title[k]);// ��һ�У�index��
					sheet.addCell(label);
					sheet.setColumnView(index, 25);//
					index++;
				}
			}

			DetachedCriteria detachedCriteria = getCurrentCriteria();

			terminalExcelList = terminalService
					.findAllByCriteria(detachedCriteria);
			for (int j = 0; j < terminalExcelList.size(); j++) {
				index = 0;
				terminal = terminalExcelList.get(j);
				if (t[0])
					sheet.addCell(new jxl.write.Label(index++, j + 1, terminal
							.getSim()));
				if (t[1])
					sheet.addCell(new jxl.write.Label(index++, j + 1, terminal
							.getPhone()));
				if (t[2])
					sheet.addCell(new jxl.write.Label(index++, j + 1, terminal
							.getCarnumber()));
				if (t[3])
					sheet.addCell(new jxl.write.Label(index++, j + 1, terminal
							.getTCarInfo().getTypeName()));

				if (t[4])
					sheet.addCell(new jxl.write.Label(index++, j + 1, terminal
							.getModel()));
				if (t[5]) {
					if (terminal.getRegistertime() != null
							&& dateFormat.format(terminal.getRegistertime()) != null) {
						sheet.addCell(new jxl.write.Label(index++, j + 1,
								dateFormat.format(terminal.getRegistertime())));
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
					sheet.addCell(new jxl.write.Label(index++, j + 1,
							lockString));
				}
				if (t[9])
					sheet.addCell(new jxl.write.Label(index++, j + 1, terminal
							.getUsername()));
				if (t[10])
					sheet.addCell(new jxl.write.Label(index++, j + 1, terminal
							.getCellphone()));
				if (t[11])
					sheet.addCell(new jxl.write.Label(index++, j + 1, terminal
							.getPrincipal()));
				if (t[12])
					sheet.addCell(new jxl.write.Label(index++, j + 1, terminal
							.getTOrgainzation().getTOrgainzation().getName()));
				if (t[13])
					sheet.addCell(new jxl.write.Label(index++, j + 1, terminal
							.getTOrgainzation().getName()));

				if (t[14]) {
					String workString = "";
					if (terminal.getLock() == 0) {
						workString = "����";
					} else if (terminal.getLock() == 1) {
						workString = "����";
					}
					sheet.addCell(new jxl.write.Label(index++, j + 1,
							workString));
				}
				if (t[15])
					sheet.addCell(new jxl.write.Label(index++, j + 1, String
							.valueOf(terminal.getWorktime())));
				if (t[16])
					sheet.addCell(new jxl.write.Label(index++, j + 1, terminal
							.getElepress() == null ? "" : terminal
							.getElepress()));
				if (t[17])
					sheet.addCell(new jxl.write.Label(index++, j + 1, String
							.valueOf(terminal.getSignal()).equalsIgnoreCase(
									"null") ? "" : String.valueOf(terminal
							.getSignal())));

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
					sheet.addCell(new jxl.write.Label(index++, j + 1, terminal
							.getRemark() == null ? "" : terminal.getRemark()));

				if (t[20])
					if (terminal.getTTempPositions().get(0) != null) {
						sheet.addCell(new jxl.write.Label(index++, j + 1,
								(terminal.getTTempPositions().get(0))
										.getLocationModel()));
					} else {
						sheet.addCell(new jxl.write.Label(index++, j + 1, ""));
					}
				if (t[21]) {
					if (terminal.getTTempPositions().get(0) != null) {
						sheet.addCell(new jxl.write.Label(index++, j + 1,
								(terminal.getTTempPositions().get(0))
										.getStationId()));
					} else {
						sheet.addCell(new jxl.write.Label(index++, j + 1, ""));
					}
				}
				if (t[22])
					if (terminal.getTTempPositions().get(0) != null) {
						sheet.addCell(new jxl.write.Label(index++, j + 1,
								(terminal.getTTempPositions().get(0))
										.getPlotId()));
					} else {
						sheet.addCell(new jxl.write.Label(index++, j + 1, ""));
					}
				if (t[23])
					if (terminal.getTTempPositions().get(0) != null) {
						sheet.addCell(new jxl.write.Label(index++, j + 1,
								(terminal.getTTempPositions().get(0))
										.getLatiDirection()));
					} else {
						sheet.addCell(new jxl.write.Label(index++, j + 1, ""));
					}
				if (t[24])
					if (terminal.getTTempPositions().get(0) != null) {
						sheet.addCell(new jxl.write.Label(index++, j + 1,
								String.valueOf((terminal.getTTempPositions()
										.get(0)).getLatitude())));
					} else {
						sheet.addCell(new jxl.write.Label(index++, j + 1, ""));
					}

				if (t[25])
					if (terminal.getTTempPositions().get(0) != null) {
						sheet.addCell(new jxl.write.Label(index++, j + 1,
								(terminal.getTTempPositions().get(0))
										.getLongDirection()));
					} else {
						sheet.addCell(new jxl.write.Label(index++, j + 1, ""));
					}
				if (t[26])
					if (terminal.getTTempPositions().get(0) != null) {
						sheet.addCell(new jxl.write.Label(index++, j + 1,
								String.valueOf((terminal.getTTempPositions()
										.get(0)).getLongitude())));
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

	// //////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * �ն˲�ѯ����ϸ��Ϣҳ
	 */
	public String terminalDetail() {
		searchWeiyi = 0;
		searchString = "";
		terminal = null;
		return SUCCESS;
	}

	/**
	 * ��ѯ�ն�����
	 */
	public String terminalDetailSearch() {
		// if (searchWeiyi == 1 || searchWeiyi == 2 || searchWeiyi == 3 ||
		// searchWeiyi == 4 || searchWeiyi == 5) {// Ψһ������
		if (searchWeiyi == 1) {// ���� �ն˱�� ����
			terminal = terminalService.findBySim(searchString);
		} else if (searchWeiyi == 2) {// �����ն��ֻ�������
			terminal = terminalService.findByPhone(searchString);
		} else if (searchWeiyi == 3) {// ������ -- Ŀ�����к�
			terminal = terminalService.findByCarnumber(searchString);
		} else if (searchWeiyi == 4) {// �û�����
			terminal = terminalService.findByUsername(searchString);
		} else if (searchWeiyi == 5) {// ծȯ������
			terminal = terminalService.findByPrincipal(searchString);
		}
		// }
		page = "Admin_terminalDetail";
		return TARGET;
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////

	// ---------------------------getters and setters-----------------//
	// ---------------------------getters and setters-----------------//
	// ---------------------------getters and setters-----------------//
	@Override
	public void setSession(Map<String, Object> session) {
		// TODO Auto-generated method stub
		this.session = session;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public TUser getUser() {
		return user;
	}

	public void setUser(TUser user) {
		this.user = user;
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

	public void setOrganizationService(
			OrganizationServiceImpl organizationService) {
		this.organizationService = organizationService;
	}

	public List<TOrgainzation> getOrgs() {
		return orgs;
	}

	public void setOrgs(List<TOrgainzation> orgs) {
		this.orgs = orgs;
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

	public AreaServiceImpl getAreaService() {
		return areaService;
	}

	public void setAreaService(AreaServiceImpl areaService) {
		this.areaService = areaService;
	}

	public List<TArea> getAreas() {
		return areas;
	}

	public void setAreas(List<TArea> areas) {
		this.areas = areas;
	}

	public TOrgainzation getYunwei() {
		return yunwei;
	}

	public void setYunwei(TOrgainzation yunwei) {
		this.yunwei = yunwei;
	}

	public List<TLog> getLogList() {
		return logList;
	}

	public void setLogList(List<TLog> logList) {
		this.logList = logList;
	}

	public DateFormat getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	public void setJituanUser(TUser jituanUser) {
		this.jituanUser = jituanUser;
	}

	public CarInfoServiceImpl getCarInfoService() {
		return carInfoService;
	}

	public void setCarInfoService(CarInfoServiceImpl carInfoService) {
		this.carInfoService = carInfoService;
	}

	public List<TCarInfo> getCars() {
		return cars;
	}

	public void setCars(List<TCarInfo> cars) {
		this.cars = cars;
	}

	public AccountServiceImpl getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountServiceImpl accountService) {
		this.accountService = accountService;
	}

	public List<TAccount> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<TAccount> accounts) {
		this.accounts = accounts;
	}

	public int getSelectAreaId() {
		return selectAreaId;
	}

	public void setSelectAreaId(int selectAreaId) {
		this.selectAreaId = selectAreaId;
	}

	public List<TUser> getYunweiUserList() {
		return yunweiUserList;
	}

	public void setYunweiUserList(List<TUser> yunweiUserList) {
		this.yunweiUserList = yunweiUserList;
	}

	public int getYunweiUserId() {
		return yunweiUserId;
	}

	public void setYunweiUserId(int yunweiUserId) {
		this.yunweiUserId = yunweiUserId;
	}

	public TUser getYunweiUser() {
		return yunweiUser;
	}

	public void setYunweiUser(TUser yunweiUser) {
		this.yunweiUser = yunweiUser;
	}

	public String getTARGET() {
		return TARGET;
	}

	public String getPage() {
		return page;
	}

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public String getSUCCESS() {
		return SUCCESS;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getOldPwd() {
		return oldPwd;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	public TOrgainzation getJituan() {
		return jituan;
	}

	public void setJituan(TOrgainzation jituan) {
		this.jituan = jituan;
	}

	public List<TUser> getJituanUserList() {
		return jituanUserList;
	}

	public void setJituanUserList(List<TUser> jituanUserList) {
		this.jituanUserList = jituanUserList;
	}

	public int getJituanUserId() {
		return jituanUserId;
	}

	public void setJituanUserId(int jituanUserId) {
		this.jituanUserId = jituanUserId;
	}

	public TUser getJituanUser() {
		return jituanUser;
	}

	public void setJitaunUser(TUser jitaunUser) {
		this.jituanUser = jitaunUser;
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

	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	public TArea getArea() {
		return area;
	}

	public void setArea(TArea area) {
		this.area = area;
	}

	public TCarInfo getCarInfo() {
		return carInfo;
	}

	public void setCarInfo(TCarInfo carInfo) {
		this.carInfo = carInfo;
	}

	public int getCarTypeId() {
		return carTypeId;
	}

	public void setCarTypeId(int carTypeId) {
		this.carTypeId = carTypeId;
	}

	public TAccount getAccount() {
		return account;
	}

	public void setAccount(TAccount account) {
		this.account = account;
	}

	public int getExpense() {
		return expense;
	}

	public void setExpense(int expense) {
		this.expense = expense;
	}

	public String getOrgSearchName() {
		return orgSearchName;
	}

	public void setOrgSearchName(String orgSearchName) {
		this.orgSearchName = orgSearchName;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public List<TUser> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<TUser> groupList) {
		this.groupList = groupList;
	}

	public TerminalServiceImpl getTerminalService() {
		return terminalService;
	}

	public void setTerminalService(TerminalServiceImpl terminalService) {
		this.terminalService = terminalService;
	}

	public List<TTerminal> getTerminals() {
		return terminals;
	}

	public void setTerminals(List<TTerminal> terminals) {
		this.terminals = terminals;
	}

	public String getSim() {
		return sim;
	}

	public void setSim(String sim) {
		this.sim = sim;
	}

	public TOrgainzation getOrgainzation() {
		return orgainzation;
	}

	public void setOrgainzation(TOrgainzation orgainzation) {
		this.orgainzation = orgainzation;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public List<TUser> getGroupUserList() {
		return groupUserList;
	}

	public void setGroupUserList(List<TUser> groupUserList) {
		this.groupUserList = groupUserList;
	}

	public TUser getGroupUser() {
		return groupUser;
	}

	public void setGroupUser(TUser groupUser) {
		this.groupUser = groupUser;
	}

	public TempPositionServiceImpl getTempPositionService() {
		return tempPositionService;
	}

	public void setTempPositionService(
			TempPositionServiceImpl tempPositionService) {
		this.tempPositionService = tempPositionService;
	}

	public int getGroupUserId() {
		return groupUserId;
	}

	public void setGroupUserId(int groupUserId) {
		this.groupUserId = groupUserId;
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

	public int getSearchGroupId() {
		return searchGroupId;
	}

	public void setSearchGroupId(int searchGroupId) {
		this.searchGroupId = searchGroupId;
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

	public TTerminal getTerminal() {
		return terminal;
	}

	public void setTerminal(TTerminal terminal) {
		this.terminal = terminal;
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

	public int getJituanId() {
		return jituanId;
	}

	public void setJituanId(int jituanId) {
		this.jituanId = jituanId;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public List<TOrgainzation> getJituanGroupList() {
		return jituanGroupList;
	}

	public void setJituanGroupList(List<TOrgainzation> jituanGroupList) {
		this.jituanGroupList = jituanGroupList;
	}

	public List<TOrgainzation> getJituanList() {
		return jituanList;
	}

	public void setJituanList(List<TOrgainzation> jituanList) {
		this.jituanList = jituanList;
	}

	public int getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}

	public static int getPagesize() {
		return PAGESIZE;
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

	public List<TTempPosition> getTpositions() {
		return tpositions;
	}

	public void setTpositions(List<TTempPosition> tpositions) {
		this.tpositions = tpositions;
	}

	public int getPtotal() {
		return ptotal;
	}

	public void setPtotal(int ptotal) {
		this.ptotal = ptotal;
	}

	public int getOutline() {
		return outline;
	}

	public void setOutline(int outline) {
		this.outline = outline;
	}

	public int getOnline() {
		return online;
	}

	public void setOnline(int online) {
		this.online = online;
	}

	public int getGroupID() {
		return groupID;
	}

	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}

	public String getRedActionName() {
		return redActionName;
	}

	public void setRedActionName(String redActionName) {
		this.redActionName = redActionName;
	}

	public String getREDIRECT() {
		return REDIRECT;
	}

}
