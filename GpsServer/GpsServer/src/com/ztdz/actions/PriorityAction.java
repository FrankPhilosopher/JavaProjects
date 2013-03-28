package com.ztdz.actions;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.ztdz.pojo.TLog;
import com.ztdz.pojo.TRequest;
import com.ztdz.pojo.TResponse;
import com.ztdz.pojo.TTerminal;
import com.ztdz.pojo.TUser;
import com.ztdz.service.impl.LogServiceImpl;
import com.ztdz.service.impl.RequestServiceImpl;
import com.ztdz.service.impl.ResponseServiceImpl;
import com.ztdz.service.impl.TerminalServiceImpl;
import com.ztdz.tools.ProtocolUtil;

/**
 * ��Ȩ������Ӧ
 * ������Ȩ����
 * ���ñ�������
 * ���ö�λʱ����
 * ���Ͷϵ�
 * @author wuxuehong
 *
 * 2012-6-9
 */
public class PriorityAction extends ActionSupport implements SessionAware{
	
	private Map<String, Object> session;
	
	private String sim;   //�ն˺���
	private String p1;     //��Ȩ����1
	private String p2;    //��Ȩ����2
	private String p3;    //��Ȩ����3
	private String phone;   //��������
	private Integer period; //��λʱ����
	private Integer level;  //��������
	private boolean result; //�������
	private boolean canstop; //֪ͨǰ̨ɨ�����
	
	private RequestServiceImpl requestService;
	private ResponseServiceImpl responseService;
	private TerminalServiceImpl terminalService;
	private LogServiceImpl logService;
	
	/**
	 * ������������
	 */
	public void clearRequest(){
		TRequest req = requestService.findById(sim);
		if(req != null)
			requestService.deleteRequest(req);
		TResponse res = responseService.findById(sim);
		if(res != null)
			responseService.deleteResponse(res);
	}
	/**
	 * ������Ȩ����
	 * @return
	 */
	public String setPrivilege(){
		System.out.println(sim+"\t"+p1+"\t"+p2+"\t"+p3);
		clearRequest();
		TRequest request = new TRequest();
		request.setCommand(ProtocolUtil.getSetPrivilegeCom(sim, p1, p2, p3));
//System.out.println(request.getCommand()+"*******************************************here !~");
		request.setSim(sim);
		request.setTime(new Date());
		requestService.addRequest(request);
		System.out.println(request.getCommand()+"&&&&&&&&&&&&&&&&&&&&&&7");
		return SUCCESS;
	}
	
	/**
	 * ���ñ�������
	 * @return
	 */
	public String setWarnPhone(){
		System.out.println(sim+"\t"+phone);
		clearRequest();
		TRequest request = new TRequest();
		request.setCommand(ProtocolUtil.getSetWarnPhoneCom(sim, phone));
		request.setSim(sim);
		request.setTime(new Date());
		requestService.addRequest(request);
		return SUCCESS;
	}
	
	/**
	 * ���ö�λʱ����
	 * @return
	 */
	public String setPeriod(){
		System.out.println(sim+"\t"+period);
		clearRequest();
		TRequest request = new TRequest();
		request.setCommand(ProtocolUtil.getSetSeconCom(sim, period));
		request.setSim(sim);
		request.setTime(new Date());
		requestService.addRequest(request);
		return SUCCESS;
	}

	/**
	 * ���Ͷϵ�
	 * @return
	 */
	public String setStopOil(){
		System.out.println(sim+"\t\t"+level);
		clearRequest();
		TRequest request = new TRequest();
		request.setCommand(ProtocolUtil.getStopOilEleCom(sim, level));
		request.setSim(sim);
		request.setTime(new Date());
		requestService.addRequest(request);
		return SUCCESS;
	}
	
	/**
	 * �ָ��͵�
	 * @return
	 */
	public String setRecoverOil(){
		System.out.println(sim);
		clearRequest();
		TRequest request = new TRequest();
		request.setCommand(ProtocolUtil.getRecOilEleCom(sim));
		request.setSim(sim);
		request.setTime(new Date());
		requestService.addRequest(request);
		return SUCCESS;
	}
	/**
	 * ���ͨ�Ž��
	 * @return
	 */
	public String checkResult(){
		TResponse response = responseService.findById(sim);
		System.out.println("��ͨ���ü��******************************"+sim+"\t\t"+response);
		if(response == null){
			result = false;
			canstop = false;
		}else{
			if(ProtocolUtil.getCommResult(response.getResponse())){
					result = true;
			}else{
					result = false;
			}
			canstop = true;
		}
		return SUCCESS;
	}
	
	/**
	 * �������ͨ�Ž��
	 * @return
	 */
	public String lockResult(){
		TResponse response = responseService.findById(sim);
		System.out.println("����������******************************"+sim+"\t\t"+response+"\t\tlevel"+level);
		if(response == null){
			result = false;
			canstop = false;
		}else{
			if(ProtocolUtil.getCommResult(response.getResponse())){   //�������ɹ�
					result = true;
				    TTerminal t = terminalService.findBySim(sim);
				    if(t != null){
//				    	 t.setNetstatus(level); //1����״̬
				    	 t.setLock(level);
				    	 terminalService.updateTerminal(t);
				    	 TUser user = (TUser) session.get("user");
				    	 logService.addLog(new TLog(user, "��" + user.getName() + "��" + "���նˡ�" + sim + "������������", new Date()));
				    }
			}else{
					result = false;
			}
			canstop = true;
		}
		return SUCCESS;
	}
	
	/**
	 * ������ͨ�Ž��
	 * @return
	 */
	public String unlockResult(){
		TResponse response = responseService.findById(sim);
		System.out.println("����������******************************"+sim+"\t\t"+response);
		if(response == null){
			result = false;
			canstop = false;
		}else{
			if(ProtocolUtil.getCommResult(response.getResponse())){
				result = true;
				 TTerminal t = terminalService.findBySim(sim);
			     if(t != null){
//			    	 t.setNetstatus(0); //1����״̬
			    	 t.setLock(0);
			    	 terminalService.updateTerminal(t);
			    	 TUser user = (TUser) session.get("user");
			    	 logService.addLog(new TLog(user, "��" + user.getName() + "��" + "���նˡ�" + sim + "�������˽���", new Date()));
			    }
			}else{
					result = false;
			}
			canstop = true;
		}
		return SUCCESS;
	}
	
	
	public String getSim() {
		return sim;
	}

	public void setSim(String sim) {
		this.sim = sim;
	}

	public String getP1() {
		return p1;
	}

	public void setP1(String p1) {
		this.p1 = p1;
	}

	public String getP2() {
		return p2;
	}

	public void setP2(String p2) {
		this.p2 = p2;
	}

	public String getP3() {
		return p3;
	}

	public void setP3(String p3) {
		this.p3 = p3;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public void setRequestService(RequestServiceImpl requestService) {
		this.requestService = requestService;
	}

	public void setResponseService(ResponseServiceImpl responseService) {
		this.responseService = responseService;
	}

	public boolean isCanstop() {
		return canstop;
	}

	public void setCanstop(boolean canstop) {
		this.canstop = canstop;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	public void setTerminalService(TerminalServiceImpl terminalService) {
		this.terminalService = terminalService;
	}
	public void setLogService(LogServiceImpl logService) {
		this.logService = logService;
	}
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}
