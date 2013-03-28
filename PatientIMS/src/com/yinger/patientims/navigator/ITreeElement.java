package com.yinger.patientims.navigator;

import java.util.List;

/**
 * 定义导航视图内部的节点的接口
 * 
 * 我觉得不好的地方就是没在这里是用泛型机制！
 */
public interface ITreeElement {

	public void setName(String name);

	public String getName();

	public void setChildren(List children);

	public List getChildren();

	public boolean hasChildren();

	public void addChildren(ITreeElement iElement);

}
