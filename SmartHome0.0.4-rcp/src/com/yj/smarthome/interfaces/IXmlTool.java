package com.yj.smarthome.interfaces;

import java.io.File;
import java.util.Map;

import org.dom4j.Document;

/**
 * ����XML�ļ��Ľӿ�
 * 
 * @author yinger
 * 
 */
public interface IXmlTool {

	//���ط������˵��豸��document��xml�ļ���map�Ƿ��ؽ��
	public void loadServerDevices(Document document, Map<Integer, Object> map);

	//���ؿͻ��˵ĵƿ��豸
	public void loadClinetDevices(Document document, Map<Integer, Object> map);

	//�õ��豸���ͱ��
	public int getDeviceTypeId();

	//����һ���µ�xml�ļ������ұ���һ������ʵ������Ϣ
	public void createClientControlXml(File file, IRenameable renameObject) throws Exception;

	//����µ��豸�����ص��豸�����ļ���
	public void writeNewDevice(Document document, IRenameable renameObject);

	//�޸�ָ���豸������
	public void changeName(Document document, IRenameable renameObject);

	//�޸�ָ���豸��״̬
	public void changeState(Document document, IStateable stateObject);

}
