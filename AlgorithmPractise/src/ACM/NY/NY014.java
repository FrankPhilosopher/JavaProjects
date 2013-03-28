/**
* @Author��������  
* @CreateTime��2011-5-4 ����09:38:27
* @Description��
*/

package ACM.NY;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;

public class NY014 {
	
	public static void main(String arg[]){
		Scanner sin = new Scanner(System.in);
		int t=sin.nextInt();
		NY014 main= new NY014();
		Vector<Item> items=new Vector<Item>();
		while(t-->0){
			int l=sin.nextInt();
			int b,e;
			while(l-->0){
				items.clear();  //�ǵ�ÿ�β��Զ�Ҫ�Ƚ�ԭ�����Ǹ����
				b=sin.nextInt();
				e=sin.nextInt();
				Item item=main.new Item(b,e);   //�ⲿ���еľ�̬���������ڲ���
				items.add(item);
			}
			Collections.sort(items, main.new ItemComparator());  //�������򷽷�
			int count=0;
			Iterator it=items.iterator(); //���һ��������
			Item first;
			int end=-1;
			while(it.hasNext()){
				first=(Item)it.next();
				if(first.a>end){  //����û�Ŀ�ʼʱ���������Ǹ�����ʱ��֮��Ϳ��԰��Ż
					count++;
					end=first.b;
				}
			}
			System.out.println(count);
		}		
	}
	
	class Item{   //�������ʾһ�����a��b�ֱ��ʾ��ʼʱ��ͽ���ʱ��
		
		int a,b;
		public Item(int b,int e){
			this.a=b;
			this.b=e;
		}
	}
	
	class ItemComparator  implements Comparator{   //����ķ�����������ʱ�����������
	
		@Override
		public int compare(Object o1, Object o2) {
			Item i1=(Item)o1;  //һ��Ҫ��ת��
			Item i2=(Item)o2;
			if(i1.b-i2.b<0){
				return -1;
			}else if(i1.b-i2.b>0){
				return 1;
			}else {
				return 0;
			}
		}
	}
}
