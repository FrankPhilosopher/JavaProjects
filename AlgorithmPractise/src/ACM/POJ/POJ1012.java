/**
 * @Author��������  
 * @CreateTime��2011-4-21 ����08:54:04
 * @Description�� �ڶ��棬�ƺ��д�
 */
package ACM.POJ;

import java.util.Scanner;

public class POJ1012 {

	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		while (scn.hasNext()) {
			int k = scn.nextInt();
			if (k == 0) {
				return;
			} else {
				int m; // Ҫ���m
				int t = 0;
				for (t = 0;;t++) {
					boolean gett = false;
//					for (m = 2;; m++) {
					for (m=(2*t+1)*k;m<(2*t+2)*k; m++) {  // ��̽����m
						if(m == (2*t+2)*k ){
							break;
						}
						int p[] = new int[2 * k]; // ���е���
						for (int i = 0; i < k; i++) {
							p[i] = 1; // 1��ʾ����
							p[k + i] = -1; // -1��ʾ���� 0��ʾ����
						}
						int count = 0; // ���ڼ����ı���
						boolean getm = false; // �õ���m��ֵ
						for (int j = 0;; j++) {
							if (j == 2 * k)		//ע�������������j����2*k
								j = 0; // ʹ��ѭ��
							if (p[j] == 0)
								continue; // ��������
							else {
								count++; //����count��1����Ϊ����˿϶�Ҫ����ȥ��Ȼ�����ж�-- ���û������m����ô����jѭ��
								if (count == m) { // ���������m����
									if (p[j] == 1) { // ����Ǻ��ˣ����жϻ����Ƿ�ȫ����,���û��ȫ������ô���m����
										boolean alldie = true; // Ĭ�ϻ���ȫ����

										for (int n = k; n < 2 * k; n++) { // k �� 2*k-1���ǻ���
											if (p[n] != 0) { // ��һ��û��������flagΪfalse�����˳�forѭ��
												alldie = false;
												break;
											}
										}

										if (alldie) { // �������ȫ�������m��������j��ѭ��
											getm = true;
										}
										break;
									} else { // �����m�����ǻ���
										p[j] = 0;
										count = 0; // count����Ϊ0��������m��
										continue; // ����Ҫ�ص� j��forѭ����ȥ
									}
								}
							}
						}// j

						if (getm) {
							gett = true;
							System.out.println(m);
//							break; // �˳� m��ѭ��������while�Ĵ�ѭ��
							break; // �˳� m��ѭ��������t��ѭ��
						} 
					}// m
					if (gett) {
						break; // �˳� t��ѭ��������while��ѭ��
					} 
				}//t
			}// else
		}// while
	}
}
