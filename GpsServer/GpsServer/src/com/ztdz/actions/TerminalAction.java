package com.ztdz.actions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.ztdz.pojo.TPosition;
import com.ztdz.pojo.TTempPosition;
import com.ztdz.pojo.TTerminal;
import com.ztdz.pojo.TUser;
import com.ztdz.service.impl.PositionServiceImpl;
import com.ztdz.service.impl.TempPositionServiceImpl;
import com.ztdz.service.impl.TerminalServiceImpl;
import com.ztdz.tools.PositionConvertUtil;

/**
 * ��Action��Ҫ������� �ն� ��λ��ѯ ���������٣��켣��ѯ������դ����
 * ����Ȩ����
 * �󶨱��������
 * @author wuxuehong
 *
 * 2012-6-7
 */
public class TerminalAction  extends ActionSupport implements SessionAware{

	private Map<String, Object> session; //session �Ự
	
	private String sim;//�ն˺�  ���ڲ�ѯ�ն��豸�����Ϣ
	private Date timeStart;
	private Date timeEnd; 
	private TTempPosition position; //��ǰ��λ��Ϣ
	private List<TPosition> positions; //��ʷ��λ��Ϣ
	private List<TTempPosition> tpositions; //���пͻ���λ��Ϣ
	private TTerminal terminal; //�ն���Ϣ
	
	private TempPositionServiceImpl tempPositionService;
	private PositionServiceImpl positionService;
	private TerminalServiceImpl terminalService;
	
	private int total;  //���ն���
	private int ptotal; //����λ��Ϣ�ն���Ŀ
	private int outline; //�����ն���
	private int online;  //�����ն���
	
	private Date searchStartDate;
	private String startTime;
	private Date searchEndDate;
	private String endTime;
	
	private String page;
	private String TARGET = "target";
	private String result;
	
	private int flag = 0; //Ĭ���ǹ켣�ط�   1��ʾͣ����¼��
	/**
	 * ��λ��ѯ
	 * @return
	 */
	public String locationCheck(){
		position = tempPositionService.findById(sim);
//		if(position != null){
//			TTerminal terminal = terminalService.findBySim(position.getSim());
//			position.setTTerminal(terminal);
//		}
//		System.out.println("**************"+position.getTTerminal().getNetstatus());
		PositionConvertUtil.convert(position);
		return SUCCESS;
	}
	
	/**
	 * ��������
	 * @return
	 */
	public String moveSecond(){
		position = tempPositionService.findById(sim);
//		if(position != null){
//			TTerminal terminal = terminalService.findBySim(position.getSim());
//			position.setTTerminal(terminal);
//		}
		PositionConvertUtil.convert(position);
		return SUCCESS;
	}
	
	/**
	 * �켣��ѯ
	 * @return
	 */
	public String orbitCheck(){
		position = tempPositionService.findById(sim);
		PositionConvertUtil.convert(position);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		searchEndDate = calendar.getTime();
		calendar.add(Calendar.DATE, -1);// ǰ1����賿
		searchStartDate = calendar.getTime();// �趨��ʼ�ͽ���ʱ��
		return SUCCESS;
//		positions = positionService.findBySimBetween(sim, timeStart, timeEnd);
	}
	
	/**
	 * ��ѯͣ����
	 * @return
	 */
	public String stopPositionSearch(){
		try{
			flag = 1;
			page = "Terminal_orbitCheck";
			if("".equals(searchStartDate) || searchStartDate == null || "".equals(searchEndDate) || searchEndDate == null){
				result = "�������ѯʱ��!";
				return TARGET;
			}
			SimpleDateFormat sd =  new SimpleDateFormat("yyyy-MM-dd");
			String sstartTime = sd.format(searchStartDate)+" "+startTime;
			String sendTime = sd.format(searchEndDate)+" "+endTime;
			SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(!startTime.trim().matches("[012]\\d:[012345]\\d:[012345]\\d")||!endTime.matches("[012]\\d:[012345]\\d:[012345]\\d")){
				result = "ʱ���������:yyyy-MM-dd HH:mm:ss";
				return TARGET;
			}
			java.util.Date date = sdf.parse(sstartTime);
			java.util.Date date2 = sdf.parse(sendTime);
			long dtime = date2.getTime()-date.getTime();
			long max = 3*24*3600*1000;
			if(dtime>max){
				result = "�뽫ʱ�������3����!";
				return TARGET;
			}
			List<TPosition> temppositions = positionService.findStopBySimBetween(sim, date, date2);
			positions = new ArrayList<TPosition>();
			TPosition temp = null;
			if(temppositions.size()>0){
				temp = temppositions.get(0);
				positions.add(temppositions.get(0));
			}
			for(int i=0;i<temppositions.size();i++){
				TPosition cur = temppositions.get(i);
				if(cur.getPTime().getTime()-temp.getPTime().getTime() > 600000){
					positions.add(cur);
				}
				temp = cur;
			}
			PositionConvertUtil.convert(positions);
			position = null;
		}catch (Exception e) {
			e.printStackTrace();
			result = "���ڸ�ʽ:yyyy-MM-dd HH:mm:ss";
			return TARGET;
		}
		return TARGET;
	}
	
	/**
	 * �켣��ѯ
	 * @return
	 */
	public String orbitCheckSearch(){
		try {
			flag = 0;
			page = "Terminal_orbitCheck";
			if("".equals(searchStartDate) || searchStartDate == null || "".equals(searchEndDate) || searchEndDate == null){
				result = "�������ѯʱ��!";
				return TARGET;
			}
			SimpleDateFormat sd =  new SimpleDateFormat("yyyy-MM-dd");
			String sstartTime = sd.format(searchStartDate)+" "+startTime;
			String sendTime = sd.format(searchEndDate)+" "+endTime;
			SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(!startTime.trim().matches("[012]\\d:[012345]\\d:[012345]\\d")||!endTime.matches("[012]\\d:[012345]\\d:[012345]\\d")){
				result = "ʱ���������:yyyy-MM-dd HH:mm:ss";
				return TARGET;
			}
System.err.println("sim:"+sim);
System.err.println("startTime:"+sstartTime);
System.err.println("endTime:"+sendTime);
			java.util.Date date = sdf.parse(sstartTime);
			java.util.Date date2 = sdf.parse(sendTime);
			long dtime = date2.getTime()-date.getTime();
			long max = 3*24*3600*1000;
			if(dtime>max){
				result = "�뽫ʱ�������3����!";
				return TARGET;
			}
			System.err.println("start:"+date);
			System.err.println("end:"+date2);
			positions = positionService.findBySimBetween(sim, date, date2);
			PositionConvertUtil.convert(positions);
			position = null;
	System.out.println(positions.size()+"***********************************************************************");
		} catch (Exception e) {
			e.printStackTrace();
			result = "���ڸ�ʽ:yyyy-MM-dd HH:mm:ss";
			return TARGET;
		}
		return TARGET;
	}
	
	/**
	 * ����դ��
	 * @return
	 */
	public String fence(){
		position = tempPositionService.findById(sim);
		PositionConvertUtil.convert(position);
		return SUCCESS;
	}
	
	/**
	 * ��ȡ�����ն˶�λ
	 * @return
	 */
	public String allLocations(){
		TUser user = (TUser) session.get("user");
		List<TTerminal> terminals = terminalService.findByOrg(user.getTOrgainzation().getOrgId());
		Iterator<TTerminal> it = terminals.iterator();
		tpositions  = new ArrayList<TTempPosition>();
		total = terminals.size(); //�ն�����
		ptotal = 0;
		online = 0;
		outline = 0;
		while(it.hasNext()){
			TTerminal p = it.next();
			System.out.println(p.getTTempPositions()+"\t\t\t");
			if(p.getTTempPositions() != null && p.getTTempPositions().size() == 1){
				TTempPosition ttp = p.getTTempPositions().get(0);
		System.out.println("***********"+ttp.getLocationModel());
				if(ttp.getLatitude() != -1 || "1".equals(ttp.getLocationModel())){        //�����ǰ�ն˺��ж�λ��Ϣ  �����ǻ�վ��λi��
					tpositions.add(PositionConvertUtil.convert(ttp));
					ptotal++;
					if(p.getNetstatus() == 1) online++;
					else outline++;
				}
			}
		}
		return SUCCESS;
	}
	/**
	 * ��Ȩ����
	 * @return
	 */
	public String priority(){
		terminal = terminalService.findBySim(sim);
		System.out.println(sim+"\t\t"+terminal.getTOrgainzation().getWarnphone());
		return SUCCESS;
	}
	@Override
	public void setSession(Map<String, Object> session) {
		// TODO Auto-generated method stub
		this.session = session;
	}

	public String getSim() {
		return sim;
	}

	public void setSim(String sim) {
		this.sim = sim;
	}

	public TempPositionServiceImpl getTempPositionService() {
		return tempPositionService;
	}

	public void setTempPositionService(TempPositionServiceImpl tempPositionService) {
		this.tempPositionService = tempPositionService;
	}

	public PositionServiceImpl getPositionService() {
		return positionService;
	}

	public void setPositionService(PositionServiceImpl positionService) {
		this.positionService = positionService;
	}

	public TTempPosition getPosition() {
		return position;
	}

	public void setPosition(TTempPosition position) {
		this.position = position;
	}

	public Date getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(Date timeStart) {
		this.timeStart = timeStart;
	}

	public Date getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(Date timeEnd) {
		this.timeEnd = timeEnd;
	}

	public List<TPosition> getPositions() {
		return positions;
	}

	public void setPositions(List<TPosition> positions) {
		this.positions = positions;
	}

	public TTerminal getTerminal() {
		return terminal;
	}

	public void setTerminal(TTerminal terminal) {
		this.terminal = terminal;
	}

	public TerminalServiceImpl getTerminalService() {
		return terminalService;
	}

	public void setTerminalService(TerminalServiceImpl terminalService) {
		this.terminalService = terminalService;
	}

	public List<TTempPosition> getTpositions() {
		return tpositions;
	}

	public void setTpositions(List<TTempPosition> tpositions) {
		this.tpositions = tpositions;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
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

	public int getPtotal() {
		return ptotal;
	}

	public void setPtotal(int ptotal) {
		this.ptotal = ptotal;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public Map<String, Object> getSession() {
		return session;
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

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

}
