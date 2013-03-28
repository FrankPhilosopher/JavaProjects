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
import com.opensymphony.xwork2.Result;
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
import com.ztdz.tools.PositionConvertUtil;

/**
 * ��action������ �����û������������Ӧ
 * 
 * @author hujiawei
 * 
 *         2012-5-24
 */
public class JiTuanAction extends ActionSupport implements SessionAware, ServletRequestAware {
	private int selectAreaId;
    private List<TArea> areas;
	private static final int PAGESIZE = 10;
	private Map<String, Object> session; // session �Ự
	private HttpServletRequest request;// request
	private final String TARGET = "target"; // �����ص�ҳ�����û��Զ���ҳ���ʱ��,������SUCCESS,���Ƿ���TARGET
	private String page; // �û��Զ��巵��ҳ��
	private final String REDIRECT = "redirect";
	private String redActionName;// �ض��򵽵�action������

	private TUser user; // �û���Ϣ

	/* ��ǰ̨�йص������ֶ� */
	// ��������
	private String oldPwd, newPwd; // �û�������Ϣ �����޸�

	// �����û��������
	private List<TOrgainzation> groupList;// ǰ̨Ҫ��ʾ�ķ�������б�
	private int orgId;// �����û��Ļ���id����������ڱ༭��ɾ��
	private int selectOrgId;// �����������ļ��Ż���id����������ڱ༭ʱѡ����һ�����Ż���
	private TOrgainzation orgainzation;// ���б༭�ķ������
	private TOrgainzation jituan;// �����������ļ��Ż���
	private List<TArea> areaList;// �����б�
	private int areaId;// �������
	private TArea area;// ѡ�еĵ���

	// �����û����˺Ź������
	private List<TUser> groupUserList;// ǰ̨Ҫ��ʾ�ķ�������е��˻��б�
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

	// ���ն������й�
	private int searchType;
	private String sim;// ����simģ����ѯ�ն�

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

	
	//�ն˶�λ����
	private List<TTempPosition> tpositions; //���пͻ���λ��Ϣ
	private int ptotal; //����λ��Ϣ�ն���Ŀ
	private int outline; //�����ն���
	private int online;  //�����ն���
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
	
	
	//---�����ն˶�λ
	public String allLocations(){
		List<TTerminal> terminals = terminalService.findByOrg(orgId);
		Iterator<TTerminal> it = terminals.iterator();
		tpositions  = new ArrayList<TTempPosition>();
		total = terminals.size(); //�ն�����
		while(it.hasNext()){
			TTerminal p = it.next();
			if(p.getTTempPositions() != null && p.getTTempPositions().size() == 1){
				TTempPosition ttp = p.getTTempPositions().get(0);
				if(ttp.getLatitude() != -1){        //�����ǰ�ն˺��ж�λ��Ϣ
					tpositions.add(PositionConvertUtil.convert(ttp));
					ptotal++;
					if(p.getNetstatus() == 1) online++;
					else outline++;
				}
			}
		}
		page = "Terminal_allLocations";
		return TARGET;
	}

	// ------------------------------------��������----------------------------------------//

	/**
	 * ��ѯ�����û���������
	 * 
	 * @return
	 */
	public String basicInfo() {
		user = (TUser) session.get("user");// ֱ�ӴӻỰ�л�ȡ
		return SUCCESS;
	}

	/**
	 * ���漯���û���������
	 * 
	 * @return
	 */
	public String saveBasicInfo() {
		user = (TUser) session.get("user");
		if (oldPwd == null || "".equals(oldPwd) || !user.getPwd().equals(oldPwd)) {
			request.setAttribute("result", "�������������");
			page = "JiTuan_basicInfo";
			return TARGET;
		}
		// TODO:���������������ҳ��
		if (newPwd == null || "".equals(newPwd)) {
			request.setAttribute("result", "�����벻��Ϊ�գ�");
			page = "JiTuan_basicInfo";
			return TARGET;
		}
		user.setPwd(newPwd);
		userService.updateUser(user);
		request.setAttribute("result", "�޸ĳɹ���");
		logService.addLog(new TLog(user, "��" + user.getName() + "��" + "�޸��˻�������", new Date()));
		page = "JiTuan_basicInfo";
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
	 * TODO:����������������������û�
	 * 
	 * @return
	 */
	public String groupSearch() {
		System.out.println("search group name: " + searchString);
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
				total = organizationService.getCountAllGroupsByBloc(user.getTOrgainzation().getOrgId());
			} else {
				total = organizationService.getCountMohuByJituan(searchString, user.getTOrgainzation().getOrgId());
			}
		} else {
			total = organizationService.getCountAllGroupsByBloc(user.getTOrgainzation().getOrgId());
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
			if (searchString.equalsIgnoreCase("")) {
				groupList = organizationService.findAllGroupsByBloc(user.getTOrgainzation().getOrgId());
			} else {
				groupList = organizationService.findMohuByJituan(searchString, user.getTOrgainzation().getOrgId(), (pageIndex - 1) * PAGESIZE, PAGESIZE);
			}
		} else {
			groupList = organizationService.findAllGroupsByBloc(user.getTOrgainzation().getOrgId(), (pageIndex - 1) * PAGESIZE, PAGESIZE);
		}
		page = "JiTuan_groupManager";
		return TARGET;
	}

	/**
	 * ����µķ������֮ǰ�Ĳ���
	 * 
	 * @return
	 */
	public String beforeAddGroup() {
		user = (TUser) session.get("user");
		orgainzation = null;// new��Ϊ�˷�ֹ֮ǰ������
		jituan = null;// ��ͬ����ά֮��
		// ��ͬ����ά֮��������û�м���list��������Ҫ�е����б�
		areaList = areaService.findAll();
		page = "JiTuan_addGroup";
		return TARGET;
	}

	/**
	 * ����µķ������
	 * 
	 * @return
	 */
	public String addGroup() {
		user = (TUser) session.get("user");
		jituan = user.getTOrgainzation();// ��ͬ����ά֮��
		area = areaService.findById(areaId);// ��ȡѡ�еĵ���
		orgainzation.setTArea(area);
		orgainzation.setTOrgainzation(jituan);
		orgainzation.setFeestandard(jituan.getFeestandard());
		orgainzation.setBalance(0);// �������Ϊ0
		orgainzation.setOrgLevel(3);// �������
		// orgainzation.setTArea(user.getTOrgainzation().getTArea());// ��ͬ����ά֮������������Ϊ��ǰ�ĵ�¼��ά�Ĺ�������
		orgainzation.setRegistertime(new Date());// ע��ʱ��Ϊ��ǰʱ��
		organizationService.addOrganization(orgainzation);
		request.setAttribute("result", "��ӳɹ���");
		logService.addLog(new TLog(user, "��" + user.getName() + "��" + "������µķ��������" + orgainzation.getName() + "��", new Date()));
		// redActionName = "JiTuan_groupManager";// ���ﻹ���ض����б��У�һ�㲻������ӣ�
		// return REDIRECT;
		// redActionName = "JiTuan_addGroup";//
		// return TARGET;
		return SUCCESS;
	}

	/**
	 * ɾ��һ���������
	 * 
	 * @return
	 */
	public String deleteGroup() {
		System.out.println(orgId);
		user = (TUser) session.get("user");
		organizationService.delOrganization(orgId);
		logService.addLog(new TLog(user, "��" + user.getName() + "��" + "ɾ���˷��������" + orgainzation.getName() + "��", new Date()));
		redActionName = "JiTuan_groupManager";// �ض����б���
		return REDIRECT;
	}

	/**
	 * �༭һ���������
	 * 
	 * @return
	 */
	public String editGroupInfo() {
		user = (TUser) session.get("user");
		areaList = areaService.findAll();
		System.out.println(orgId);
		orgainzation = organizationService.findById(orgId);
		jituan = orgainzation.getTOrgainzation();// �õ��������һ������
		return SUCCESS;
	}

	/**
	 * ����һ����������Ļ�����Ϣ
	 * 
	 * @return
	 */
	public String saveGroupInfo() {
		System.out.println(selectOrgId);
		user = (TUser) session.get("user");
		area = areaService.findById(areaId);// ��ȡѡ�еĵ���
		orgainzation.setTArea(area);
		organizationService.updateOrganization(orgainzation);
		logService.addLog(new TLog(user, "��" + user.getName() + "��" + "�޸��˷��������" + orgainzation.getName() + "��", new Date()));
		request.setAttribute("result", "�޸ĳɹ���");
		page = "JiTuan_editGroupInfo";//
		return TARGET;// ���ﲻҪ�ض��򣬻��Ǳ༭ҳ�棬��ʾ�ɹ�
	}

	// ------------------------------------�����û��˻�����----------------------------------//

	/**
	 * �õ���������е��˻��б�
	 * 
	 * @return
	 */
	public String groupUserManager() {
		pageIndex = 0;
		findGroupUserList();
		return SUCCESS;
	}

	/**
	 * ������������˻�
	 * 
	 * @return
	 */
	public String groupUserSearch() {
		findGroupUserList();
		page = "JiTuan_groupUserManager";
		return TARGET;
	}

	/**
	 * �õ���������е��˻��б� �����Ǽٵģ��˻�����ķ�ҳ��ģ���������Ǽٵģ�ÿ�εõ��Ķ���ȫ������
	 * 
	 * @return
	 */
	public String findGroupUserList() {
		orgainzation = organizationService.findById(orgId);
		// �õ��ܼ�¼��
		total = orgainzation.getTUsers().size();
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
		// groupUserList = orgainzation.getTUsers();//set--->˳�����
		groupUserList = userService.findByOrgId(orgId);
		page = "JiTuan_groupUserManager";
		return TARGET;
	}

	/**
	 * ���һ���˻�֮ǰ�Ĳ���
	 * 
	 * @return
	 */
	public String beforeAddGroupUser() {
		groupUser = new TUser();
		page = "JiTuan_addGroupUser";
		return TARGET;
	}

	/**
	 * ���һ���˻�
	 * 
	 * @return
	 */
	public String addGroupUser() {
		user = (TUser) session.get("user");
		TUser tempTUser = userService.findByUserId(groupUser.getUserid());
		if (tempTUser != null) {
			request.setAttribute("result", "�˻����Ѿ������ˣ�");
			// page = "JiTuan_addGroupUser";
			// return TARGET;
			return SUCCESS;
		}
		groupUser.setTOrgainzation(orgainzation);// orgainzation������ֵ�ģ�����Σ�
		groupUser.setRegistertime(new Date());
		userService.addUser(groupUser);
		logService.addLog(new TLog(user, "��" + user.getName() + "��" + "Ϊ���������" + orgainzation.getName() + "��������˻���" + groupUser.getName() + "��", new Date()));
		request.setAttribute("result", "��ӳɹ���");
		// page = "JiTuan_addGroupUser";
		// return TARGET;
		return SUCCESS;
	}

	/**
	 * ɾ��һ����������µ��˻�
	 * 
	 * @return
	 */
	public String deleteGroupUser() {
		user = (TUser) session.get("user");
		String remark = "��" + user.getName() + "��" + "ɾ���˷��������" + orgainzation.getName() + "���µ��˻���" + userService.findById(groupUserId).getName() + "��";
		userService.delUserById(groupUserId);
		logService.addLog(new TLog(user, remark, new Date()));
		redActionName = "JiTuan_groupUserManager";// ���Թ��ˣ����Խ���action���ض���������֤�˺���ʱ�������б���Ϣ�����µ�
		return REDIRECT;
		// page = "JiTuan_addGroupUser";
		// return TARGET;
	}

	/**
	 * �༭һ����������µ��˻�
	 * 
	 * @return
	 */
	public String editGroupUserInfo() {
		groupUser = userService.findById(groupUserId);
		return SUCCESS;
	}

	/**
	 * ����һ������������˻�������Ϣ
	 * 
	 * @return
	 */
	public String saveGroupUserInfo() {
		user = (TUser) session.get("user");
		try {
			userService.updateUser(groupUser);
			request.setAttribute("result", "�޸ĳɹ���");
			logService.addLog(new TLog(user, "��" + user.getName() + "��" + "�޸��˷��������" + orgainzation.getName() + "���µ��˻���" + groupUser.getName() + "������Ϣ",
					new Date()));
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result", "�޸�ʧ�ܣ�");// ò��Ҫ�ж�userid�Ƿ���Ч
		}
		// groupUser = userService.findById(groupUserId);// ����ִ���˺ܶ���sql��䣡user->org->users!
		// redActionName = "JiTuan_groupUserManager";// ���Թ��ˣ����Խ���action���ض���������֤�˺���ʱ�������б���Ϣ�����µ�
		// return REDIRECT;
		page = "JiTuan_editGroupUserInfo";
		return TARGET;
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
//		calendar.add(Calendar.DATE, 1);// ����һ����賿
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
			page = "JiTuan_logManager";
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
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(searchEndDate);
		calendar.add(Calendar.DATE, 1);// ��һ����賿
		searchEndDate=calendar.getTime();
		
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
		
		calendar.add(Calendar.DATE, -1);//ʹҳ����ʾ��������ϰ��
		searchEndDate=calendar.getTime();
		
		page = "JiTuan_logManager";
		return TARGET;
	}

	// ------------------------------ ���ݱ������ ---------------------------------------//

	/**
	 * ���ݱ�������б�
	 */
	public String excelManager() {
		isSearching = false;
		searchString = "";
		cols = "";
		searchWeiyi = -1;// �������Ҫ�������ʱ��������
		pageIndex = 0;// ע�����︳ֵ�仯��Ϊ�˱���ǰ����б�ͺ�����б�����ͻ
		onoff = 2;
		searchFromDate = null;
		searchEndDate = null;
		searchGroupId = 0;
		carType = 0;
		// �õ���ά�����еķ���
		groupList = organizationService.findAllGroupsByBloc(user.getTOrgainzation().getOrgId());
		// �õ����еĳ�����
		carInfoList = carInfoService.findAll();
		findTerminalExcelList();
		return SUCCESS;
	}

	/**
	 * ����terminal
	 */
	public String terminalExcelSearch() {
		isSearching = true;
		findTerminalExcelList();
		page = "JiTuan_excelManager";
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
			page = "JiTuan_excelManager";
			return TARGET;
		}
		if (searchEndDate == null && searchFromDate == null && onoff == 2 && searchGroupId == 0 && carType == 0) {
			total = 0;// ע�����︳ֵ�仯��Ϊ�˱���ǰ����б�ͺ�����б�����ͻ
			pages = 0;
			pageIndex = 0;
			page = "JiTuan_excelManager";
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
		page = "JiTuan_excelManager";
		return TARGET;
	}

	// �õ���ǰ������
	private DetachedCriteria getCurrentCriteria() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(TTerminal.class);
		user = (TUser) session.get("user");
		// ����������Ӧ��û��������
		detachedCriteria.createAlias("TOrgainzation", "t").createAlias("t.TOrgainzation", "tt")
				.add(Restrictions.eq("tt.orgId", user.getTOrgainzation().getOrgId()));
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
		if (searchGroupId != 0) {// �����������
			detachedCriteria.add(Restrictions.eq("TOrgainzation.orgId", searchGroupId));
		}
		if (carType != 0) {// ������ķ�����
			detachedCriteria.createAlias("TCarInfo", "carInfo").add(Restrictions.eq("carInfo.carTypeId", carType));
		}
		return detachedCriteria;
	}

	// ����ѡ�е��е�������
	public String excelExport() {
		String[] colValues = cols.split(",");
		if (colValues.length == 0) {
			page = "JiTuan_excelManager";
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
					sheet.setColumnView(index, 25);
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
					}  else if (terminal.getNetstatus() == 0){
						netString = "����";
					} else{
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

	// ------------------------------- ���ó�ֵ --------------------------------------------//
	/**
	 * �����û����ù���
	 */
	public String groupExpenseManager() {
		user = (TUser) session.get("user");
		isSearching = false;
		pageIndex = 0;
		searchString = "";
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
		page = "JiTuan_groupExpenseManager";
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
				total = organizationService.getCountAllGroupsByBloc(user.getTOrgainzation().getOrgId());
			} else {
				total = organizationService.getCountMohuByJituan(searchString, user.getTOrgainzation().getOrgId());
			}
		} else {
			total = organizationService.getCountAllGroupsByBloc(user.getTOrgainzation().getOrgId());
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
			if (searchString.equalsIgnoreCase("")) {
				groupList = organizationService.findAllGroupsByBloc(user.getTOrgainzation().getOrgId());
			} else {
				groupList = organizationService.findMohuByJituan(searchString, user.getTOrgainzation().getOrgId(), (pageIndex - 1) * PAGESIZE, PAGESIZE);
			}
		} else {
			groupList = organizationService.findAllGroupsByBloc(user.getTOrgainzation().getOrgId(), (pageIndex - 1) * PAGESIZE, PAGESIZE);
		}
		page = "JiTuan_groupExpenseManager";
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
		user = (TUser) session.get("user");
		TOrgainzation pOrgainzation = user.getTOrgainzation();// ��ͬ����ά,����õ�����
		if (pOrgainzation.getBalance() < account.getExpense()) {// ���Ż��������ͳ�ֵ���Ƚϣ��������
			request.setAttribute("result", "���Ż��������㣡");
			page = "JiTuan_groupExpenseAdd";// �������룬�������ض���
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
		organizationService.updateOrganization(pOrgainzation);// ���ύ��䶼���ں���
		organizationService.updateOrganization(orgainzation);

		account.setTOrgainzation(orgainzation);
		account.setPaider(pOrgainzation.getName());
		account.setPaiddate(new Date());
		String remark = "���������" + orgainzation.getName() + "����ֵ��" + account.getExpense() + "Ԫ";
		account.setRemark(remark);// ��ӱ�ע
		accountService.addAccount(account);

		logService.addLog(new TLog(user, remark, new Date()));

		request.setAttribute("result", "��ֵ�ɹ���");
		page = "JiTuan_groupExpenseAdd";
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
//		calendar.add(Calendar.DATE, 1);// ���ַ�ʽ���ԣ��õ�������һ��
		searchEndDate = calendar.getTime();
		calendar.add(Calendar.DATE, -2);// ǰ������賿
		searchStartDate = calendar.getTime();
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
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(searchEndDate);
		calendar.add(Calendar.DATE, 1);// ��һ����賿
		searchEndDate=calendar.getTime();
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
		calendar.add(Calendar.DATE, -1);//ʹҳ����ʾ��������ϰ��
		searchEndDate=calendar.getTime();
		
		page = "JiTuan_groupExpenseDetail";
		return TARGET;
	}

	// /////////////////------ ����������ն˹���------////////////////////////////
	/**
	 * �ն��б����
	 */
	public String terminalManager() {
		orgainzation = organizationService.findById(orgId);// �ҵ�����
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
		isSearching = true;
		pageIndex = 0;
		findTerminalList();
		return TARGET;
	}

	/**
	 * ����ն�֮ǰ�Ĳ���
	 * 
	 * @return
	 */
	public String beforeAddTerminal() {
		areas = areaService.findAll();// �õ������б�
		terminal = new TTerminal();
		carInfoList = carInfoService.findAll();
		user = (TUser) session.get("user");
		// ������ά�ĵ����õ������б�
		groupList = organizationService.findAllGroupsByOM(user.getTOrgainzation().getTArea().getAreaId());
		page = "JiTuan_addTerminal";
		return TARGET;
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
			page = "JiTuan_addTerminal";
			return TARGET;
		}
		temp = terminalService.findByPhone(terminal.getPhone());
		if (temp != null) {
			request.setAttribute("result", "��SIM�����Ѿ�����!");
			page = "JiTuan_addTerminal";
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
		page = "JiTuan_addTerminal";
		request.setAttribute("result", "�����ն��û��ɹ�!");
		user = (TUser) session.get("user");
		logService.addLog(new TLog(user, "��" + user.getName() + "��" + "�������նˡ�" + terminal.getSim() + "��", new Date()));
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
		logService.addLog(new TLog(user, "��" + user.getName() + "��" + "ɾ�����նˡ�" + sim + "��", new Date()));
		return terminalManager();
	}

	/**
	 * 
	 * 
	 * @return
	 */
	public String findTerminalList() {
		System.out.println(orgId);// ѡ�еķ���id
		// �õ��ܼ�¼��
		if (isSearching) {
			// total = terminalService.getCountMohuByOrgAndSim(searchString, orgId);
			if (searchString.equalsIgnoreCase("")) {// Ϊ��������ȫ��
				total = terminalService.getCountByOrg(user.getTOrgainzation().getOrgId());
			} else {
				if (searchType == 1) {// �ն˱�Ų�ѯ
					total = terminalService.getCountMohuByOrgAndSim(searchString, orgId);
				} else if (searchType == 2) {// Ŀ�����к�
					total = terminalService.getCountMohuByOrgAndCarNumber(searchString, orgId);
				}
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
		// System.out.println("pageIndex=" + pageIndex);
		// �õ���ҳ��ʾ�ļ�¼
		if (isSearching) {
			// terminalList = terminalService.findMohuByOrgAndSim(searchString, orgId, (pageIndex - 1) * PAGESIZE, PAGESIZE);
			if (searchString.equalsIgnoreCase("")) {
				terminalList = terminalService.findByOrg(user.getTOrgainzation().getOrgId());
			} else {
				if (searchType == 1) {// �ն˱�Ų�ѯ
					terminalList = terminalService.findMohuByOrgAndSim(searchString, orgId, (pageIndex - 1) * PAGESIZE, PAGESIZE);
				} else if (searchType == 2) {// Ŀ�����к�
					terminalList = terminalService.findMohuByOrgAndCarNumber(searchString, orgId, (pageIndex - 1) * PAGESIZE, PAGESIZE);
				}
			}
		} else {
			terminalList = terminalService.findByOrg(orgId, (pageIndex - 1) * PAGESIZE, PAGESIZE);
		}
		page = "JiTuan_terminalManager";
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
		page = "JiTuan_editTerminal";
		user = (TUser) session.get("user");
		logService.addLog(new TLog(user, "��" + user.getName() + "��" + "�޸����նˡ�" + terminal.getSim() + "��", new Date()));
		request.setAttribute("result", "�޸ĳɹ���");
		return TARGET;
	}

	// //////////////////////////////// �����û��µ��ն��û��ķ��ù��� ///////////////////////////////
	/**
	 * �����û��µ��ն��û��ķ��ù���
	 */
	public String terminalExpenseManager() {
		isSearching = false;
		searchString = "";
		pageIndex = 0;
		searchType = 0;
		findTerminalExpenseList();
		return SUCCESS;
	}

	/**
	 * ���ù����е��ն��û���ѯ
	 * 
	 * @return
	 */
	public String terminalExpenseSearch() {
		if (searchType == 0) {// ��Ȼ���������������û��ѡ����������
			page = "JiTuan_terminalExpenseManager";
			return TARGET;
		}
		isSearching = true;
		pageIndex = 0;
		findTerminalExpenseList();
		page = "JiTuan_terminalExpenseManager";
		return TARGET;
	}

	/**
	 * ���ڷ�ҳ�е��ն��û����ù��� �ն˵�ģ��������û�еײ�֧��
	 * 
	 * @return
	 */
	public String findTerminalExpenseList() {
		// System.out.println(orgId);// ѡ�еķ���id
		// �õ��ܼ�¼��
		user = (TUser) session.get("user");
		if (isSearching) {
			if (searchString.equalsIgnoreCase("")) {// Ϊ��������ȫ��
				total = terminalService.getCountByOrg(user.getTOrgainzation().getOrgId());
			} else {
				if (searchType == 1) {// �ն˱�Ų�ѯ
					total = terminalService.getCountMohuByOrgAndSim(searchString, orgId);
				} else if (searchType == 2) {// Ŀ�����к�
					total = terminalService.getCountMohuByOrgAndCarNumber(searchString, orgId);
				}
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
		// System.out.println("pageIndex=" + pageIndex);
		// �õ���ҳ��ʾ�ļ�¼
		if (isSearching) {
			if (searchString.equalsIgnoreCase("")) {
				terminalList = terminalService.findByOrg(user.getTOrgainzation().getOrgId());
			} else {
				if (searchType == 1) {// �ն˱�Ų�ѯ
					terminalList = terminalService.findMohuByOrgAndSim(searchString, orgId, (pageIndex - 1) * PAGESIZE, PAGESIZE);
				} else if (searchType == 2) {// Ŀ�����к�
					terminalList = terminalService.findMohuByOrgAndCarNumber(searchString, orgId, (pageIndex - 1) * PAGESIZE, PAGESIZE);
				}
			}
		} else {
			terminalList = terminalService.findByOrg(orgId, (pageIndex - 1) * PAGESIZE, PAGESIZE);
		}
		page = "JiTuan_terminalExpenseManager";
		return TARGET;
	}

	// ///////////////////////////// �ն��û��ĳ�ֵ /////////////////////////////////
	/**
	 * �����ն��û���ֵ���棬���ﲢû�г�ֵ
	 * 
	 * @return
	 */
	public String terminalExpenseAdd() {
		// System.out.println("terminal Id = " + terminalId);
		terminal = terminalService.findById(terminalId);
		account = null;// Ӧ�ò���Ҫnew���������ֵ�Ļ��Ͳ����˶���Ķ���
		// account = new TAccount();
		return SUCCESS;
	}

	/**
	 * ���ն��û���ֵ
	 * 
	 */
	public String addTerminalExpense() {
		if (serviceYear == 0) {
			request.setAttribute("result", "��ѡ���ֵ������");
			page = "JiTuan_terminalExpenseAdd";
			return TARGET;
		}
		terminal = terminalService.findById(terminalId);
		// terminal.getTOrgainzation(),organizationService.findById(orgId)�õ���org��null
		TOrgainzation pOrgainzation = organizationService.findById(orgId);// pOrgainzation��Ϊnull����������Ϊ��
		if (pOrgainzation.getBalance() < pOrgainzation.getFeestandard() * serviceYear) {// ������������ͷ�����ѱȽϣ��������
			request.setAttribute("result", "������������㣡");
			page = "JiTuan_terminalExpenseAdd";
			return TARGET;
		}
		// ���������Ҫ����update
		pOrgainzation.setBalance(pOrgainzation.getBalance() - pOrgainzation.getFeestandard() * serviceYear);
		organizationService.updateOrganization(pOrgainzation);// �ύ���

		// ��ֵ�ɹ�������terminal
		Date today = new Date();
		if (terminal.getEndTime().before(today)) {// �����Ѿ�������
			terminal.setStartTime(today);
		} /*else {
			terminal.setStartTime(terminal.getEndTime());
		}*/
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

		// ����һ���˵�����־��Ϣ
		String remark = "��" + user.getName() + "��Ϊ�նˡ�" + terminal.getSim() + "����ֵ�ˣ�����ʱ���ǡ�" + dateFormat.format(terminal.getStartTime()) + "��-��"
				+ dateFormat.format(terminal.getEndTime()) + "��";
		logService.addLog(new TLog(user, remark, today));

		request.setAttribute("result", "��ֵ�ɹ���");
		page = "JiTuan_terminalExpenseAdd";
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

	public List<TUser> getGroupUserList() {
		return groupUserList;
	}

	public int getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(int terminalId) {
		this.terminalId = terminalId;
	}

	public OrganizationServiceImpl getOrganizationService() {
		return organizationService;
	}

	public void setGroupUserList(List<TUser> groupUserList) {
		this.groupUserList = groupUserList;
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

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
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

	public CarInfoServiceImpl getCarInfoService() {
		return carInfoService;
	}

	public List<TArea> getAreaList() {
		return areaList;
	}

	public void setAreaList(List<TArea> areaList) {
		this.areaList = areaList;
	}

	public void setCarInfoService(CarInfoServiceImpl carInfoService) {
		this.carInfoService = carInfoService;
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

	public int getSearchType() {
		return searchType;
	}

	public void setSearchType(int searchType) {
		this.searchType = searchType;
	}

	public int getServiceYear() {
		return serviceYear;
	}

	public void setServiceYear(int serviceYear) {
		this.serviceYear = serviceYear;
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

	public void setSim(String sim) {
		this.sim = sim;
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

}
