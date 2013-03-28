package ACM.CSU;

/**
 * ���·���㷨
 * ���ݱ�̸�д����Ҫ�������������Թ�ʱ�ķ�����
 * �����������飬���ǽ�����������Թ������ĸ������λ��
 * AC
 */
import java.util.Scanner;

public class CSU1080_3 {

	public static int[][] maze;
	public static int[][] dis;
	public static int[][] mark;//�Ƿ���Է��ʣ�1��ʾ����
	public static int[] nx = { 1, 0, -1, 0 };// ���ø�����С��int[4]����ʾ����
	public static int[] ny = { 0, 1, 0, -1 };//�����Ҷ�Ӧ�ķֱ������£��ң��ϣ���
	public static int m, n, sr, sc, er, ec;

	public static void main(String[] args) {
		Scanner sin = new Scanner(System.in);
		int t = sin.nextInt();
		for (int k = 1; k <= t; k++) {
			m = sin.nextInt();
			n = sin.nextInt();
			sr = sin.nextInt();
			sc = sin.nextInt();
			er = sin.nextInt();
			ec = sin.nextInt();
			maze = new int[m + 2][n + 2];// �ͱ�̲�ͬ���ǣ��ҵ�����ÿ�β��Զ���������
			dis = new int[m + 2][n + 2];
			mark = new int[m + 2][n + 2];
			for (int i = 0; i <= m + 1; i++) {// �޸�ԭʼ�ĸ�ֵ��ʽ������ѭ�����������Ȱ����ܵĵ㶼ʹ�����ǲ��÷���
				mark[i][0] = 1;
				mark[i][n + 1] = 1;
				maze[i][0] = 1;//ע��Ҫ��maze�������һ���ֵĵ㸳ֵΪ1����Ȼ������������Խ������⣬��ʵûԽ�絫�������ʼʱû��ֵ���޷��жϣ�
				maze[i][n + 1] = 1;
			}
			for (int j = 0; j <= n + 1; j++) {
				mark[0][j] = 1;
				mark[m + 1][j] = 1;
				maze[0][j] = 1;
				maze[m + 1][j] = 1;
			}
			for (int i = 1; i <= m; i++) {
				String s = sin.next();
				for (int j = 1; j <= n; j++) {
					maze[i][j] = Integer.parseInt(s.substring(j - 1, j));
				}
			}
			if (maze[sr][sc] == 1 || maze[er][ec] == 1) {// �������ж��׺�β�Ƿ���ͨ·�����ǿ϶�û��·��
				System.out.println("No Path.");
			} else {
				for (int i = 0; i <= m + 1; i++) {
					for (int j = 0; j <= n + 1; j++) {
						dis[i][j] = 0;//��ʼ��ʱ���е㵽���ľ��붼��0
						mark[i][j]=0;
					}
				}
				dis[sr][sc] = 0;
				mark[sr][sc] = 1;
				setDistance(sr,sc,0);//����㿪ʼ����setDistance����
				if (dis[er][ec] != 0 ) {
					System.out.println(dis[er][ec]);
				} else {
					System.out.println("No Path.");
				}
			}
		}
	}

	//���õ㵽���ľ���
	public static void setDistance(int r,int c,int dist) {
		dis[r][c]=dist;
		for(int i=0;i<4;i++){//�ĸ�����Ҫ����distance
			//ע����������ǰ�����һ����û�з��ʹ�����������ͨ·���������ľ��뻹��0�����Ǳȵ�ǰ��ľ����1(����֮ǰ������ֵ)
			//��Ҫע�������жϵ�˳��mark��maze֮ǰ����Ȼ���������Խ��
			if(mark[r+nx[i]][c+ny[i]]==0 && maze[r+nx[i]][c+ny[i]]==0 &&(dis[r+nx[i]][c+ny[i]]==0 || dis[r+nx[i]][c+ny[i]]>dist+1) ){
				setDistance(r+nx[i], c+ny[i], dist+1);
			}
		}
	}
}

