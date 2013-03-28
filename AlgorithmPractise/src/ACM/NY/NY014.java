/**
* @Author：胡家威  
* @CreateTime：2011-5-4 上午09:38:27
* @Description：
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
				items.clear();  //记得每次测试都要先将原来的那个清空
				b=sin.nextInt();
				e=sin.nextInt();
				Item item=main.new Item(b,e);   //外部类中的静态方法访问内部类
				items.add(item);
			}
			Collections.sort(items, main.new ItemComparator());  //调用排序方法
			int count=0;
			Iterator it=items.iterator(); //获得一个迭代器
			Item first;
			int end=-1;
			while(it.hasNext()){
				first=(Item)it.next();
				if(first.a>end){  //如果该活动的开始时间在最后的那个结束时间之后就可以安排活动
					count++;
					end=first.b;
				}
			}
			System.out.println(count);
		}		
	}
	
	class Item{   //用这个表示一个活动，a，b分别表示开始时间和结束时间
		
		int a,b;
		public Item(int b,int e){
			this.a=b;
			this.b=e;
		}
	}
	
	class ItemComparator  implements Comparator{   //排序的方案，按结束时间从升序排列
	
		@Override
		public int compare(Object o1, Object o2) {
			Item i1=(Item)o1;  //一定要先转型
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
