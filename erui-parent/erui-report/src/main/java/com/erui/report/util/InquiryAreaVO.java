package com.erui.report.util;

import java.util.HashSet;
import java.util.Set;

/**
 * 询单中大区的键值对象
 * 
 * @author wangxiaodan
 *
 */
public class InquiryAreaVO {
	// 大区名称
	private String areaName;
	// 国家列表
	private Set<String> countries;
	// 事业部列表
	private Set<String> orgs;

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Set<String> getCountries() {
		return countries;
	}

	public void pushCountry(String country) {
		if (countries == null) {
			countries = new HashSet<>();
		}
		countries.add(country);
	}

	public Set<String> getOrgs() {
		return orgs;
	}
	public void pushOrg(String org) {
		if (orgs == null) {
			orgs = new HashSet<>();
		}
		orgs.add(org);
	}
}
