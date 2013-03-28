/**
* @Author：胡家威  
* @CreateTime：2011-5-12 下午11:24:39
* @Description：
*/

package ACM.NY;

import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Vector;

/**
 * 
 */
public class NY012 {
	
	public static void main(String[] args) {
		Scanner sin=new Scanner(System.in);
		int t=sin.nextInt();
		Vector<Item> items=new Vector<Item>();
		int l,w,h,x,r,count;
		double a,b;
		double right;
		NY012 main=new NY012();
		while(t-->0){
			items.clear();
			right=0;
			count=0;
			l=sin.nextInt();
			w=sin.nextInt();
			h=sin.nextInt();
			while(l-->0){
				x=sin.nextInt();
				r=sin.nextInt();
				if(r>h/2){   
					a=x-Math.sqrt(r*r-1/4*h*h);
					if(a<0)	a=0;
					b=x+Math.sqrt(r*r-1/4*h*h);
					if(b>w)	b=w;
					Item item=main.new Item(a,b);
					items.add(item);
				}				
			}
			Collections.sort(items, main.new ItemComparator());
			boolean flg=false;  //标识是否可以了，默认是没有
			int i;
			while(!items.isEmpty()){
				for(i=0;i<items.size();i++){
					Item cur=items.get(i);
					if(cur.a<=right){
						count++;
						right=cur.b; 
						items.remove(i);
						if(right==w){   //在改变了right值之后就进行判断，如果满足就得到了结果，要跳出for和while循环
							flg=true;
						}
						break;  //如果找到了那个满足条件的区间就要跳出for循环，重新回到while找下一个
					}
				}
				if(flg){   //找到了那个区间并且满足right=w，那么这时要跳出while循环
					break;
				}
			}
			if(right==w){
				System.out.println(count);
			}else{
				System.out.println("0");
			}
		}		
	}
	
	class Item{
		double a;
		double b;
		
		public Item(double a,double b){
			this.a=a;
			this.b=b;
		}
	}
	
	class ItemComparator implements Comparator{

		@Override
		public int compare(Object o1, Object o2) {
			Item i1=(Item)o1;
			Item i2=(Item)o2;
			if(i1.b-i2.b<0){        //注意，这次不一样了，这次是降序排列
				return 1;
			}else if(i1.b-i2.b>0){
				return -1;
			}else {
				return 0;
			}
		}
	}
}
