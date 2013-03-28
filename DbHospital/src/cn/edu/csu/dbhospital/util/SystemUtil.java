package cn.edu.csu.dbhospital.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import cn.edu.csu.dbhospital.ApplicationActionBarAdvisor;
import cn.edu.csu.dbhospital.db.TDirectoryManager;
import cn.edu.csu.dbhospital.pojo.TDirectory;
import cn.edu.csu.dbhospital.pojo.TWorker;

public class SystemUtil {

	public static TWorker LOGIN_WORKER;
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat simpleDateFormatWithWeek = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat simpleDateFormatWithoutYear = new SimpleDateFormat("M��dd��");

	// null value
	public static final int NULL_VALUE = -1;

	// Ȩ����Ϊ1��û��Ϊ0
	public static final int RIGHT_YES = 1;
	public static final int RIGHT_NO = 0;

	// ���ڶԻ���ķ���ֵ��
	public static final int RESULT_FAIL = 0;

	// �����κ�������
	public static final int ARRANGEMENT_ONE = 0;// ��һ��
	public static final int ARRANGEMENT_TWO = 1;// �ڶ���

	// �����Ա�
	public static final int GANSER_MALE_VALUE = 1;
	public static final int GANSER_FEMALE_VALUE = 0;
	public static final String GANSER_FEMALE = "Ů";
	public static final String GANSER_MALE = "��";

	// �����ֵ��е�����
	public static final String COUNT1 = "COUNT1";
	public static final String COUNT2 = "COUNT2";
	public static final String COUNT3 = "COUNT3";
	public static int COUNT1_VALUE = 1;
	public static int COUNT2_VALUE = 2;
	public static int COUNT3_VALUE = 3;

	// Ĭ�ϵ�ֵ
	public static final float EXPENSE_DEFAULT = 200f;
	public static final int LIMIT_DEFAULT = 2;

	public static final int YES_VALUE = 1;
	public static final int NO_VALUE = 0;
	public static final String YES = "��";
	public static final String NO = "��";

	// ��ʽ������
	public static String formatDate(Date date) {
		return simpleDateFormat.format(date);
	}

	// ��ʽ������
	public static Date parseDate(String date) {
		try {
			return simpleDateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Date();
	}

	// ��ʽ�����ڣ�����а�������
	public static String formatDateWithWeek(Date date) {
		return simpleDateFormatWithWeek.format(date);
	}

	// ��ʽ�����ڣ�����в�������
	public static String formatDateWithoutYear(Date date) {
		return simpleDateFormatWithoutYear.format(date);
	}

	// ������� ���
	public static String buildCardNumber() {
		String cardid = UUID.randomUUID().toString().substring(0, 7);
		return "ZL" + cardid;
	}

	// ������� ���
	public static String buildRegisterNumber() {
		String cardid = UUID.randomUUID().toString().substring(0, 7);
		return "HD" + cardid;
	}
	
	// ��ʾ��Ϣ
	public static void showSystemMessage(String message) {
		ApplicationActionBarAdvisor.instance.showMessage(message);
	}

	// ϵͳ����ʱ��ʼ������
	public static void initData() {
		TDirectoryManager directoryManager = new TDirectoryManager();
		ArrayList<TDirectory> list;
		try {
			list = directoryManager.searchByName(COUNT1);
			if (list.size() > 0) {
				COUNT1_VALUE = Integer.valueOf(list.get(0).getValue()).intValue();
			} else {
				directoryManager.add(new TDirectory(COUNT1, COUNT1_VALUE + ""));
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
		try {
			list = directoryManager.searchByName(COUNT2);
			if (list.size() > 0) {
				COUNT2_VALUE = Integer.valueOf(list.get(0).getValue()).intValue();
			} else {
				directoryManager.add(new TDirectory(COUNT2, COUNT2_VALUE + ""));
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
		try {
			list = directoryManager.searchByName(COUNT3);
			if (list.size() > 0) {
				COUNT3_VALUE = Integer.valueOf(list.get(0).getValue()).intValue();
			} else {
				directoryManager.add(new TDirectory(COUNT3, COUNT3_VALUE + ""));
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

}
