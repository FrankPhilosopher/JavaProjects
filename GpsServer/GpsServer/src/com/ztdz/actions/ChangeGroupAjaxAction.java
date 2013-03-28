package com.ztdz.actions;

import java.util.List;

import com.opensymphony.xwork2.ActionSupport;
import com.ztdz.pojo.TOrgainzation;
import com.ztdz.service.impl.OrganizationServiceImpl;

public class ChangeGroupAjaxAction extends ActionSupport {
	private OrganizationServiceImpl organizationService;
	private List<TOrgainzation> groups;
	private int jituanId;

	public String getGroupsByJituanId() {
		groups = organizationService.findAllGroupsByBloc(jituanId);
		return SUCCESS;
	}

	public OrganizationServiceImpl getOrganizationService() {
		return organizationService;
	}

	public void setOrganizationService(
			OrganizationServiceImpl organizationService) {
		this.organizationService = organizationService;
	}

	public List<TOrgainzation> getGroups() {
		return groups;
	}

	public void setGroups(List<TOrgainzation> groups) {
		this.groups = groups;
	}

	public int getJituanId() {
		return jituanId;
	}

	public void setJituanId(int jituanId) {
		this.jituanId = jituanId;
	}
}
