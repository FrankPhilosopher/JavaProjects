/**
* @Author£ººú¼ÒÍþ  
* @CreateTime£º2011-5-8 ÏÂÎç12:53:04
* @Description£ºAC
*/

package ACM.CSU;
import java.util.Scanner;
public class CSU1049 {
    public static void main(String[] args) {
        double total = 0;
        Scanner sc = new Scanner(System.in);
        int len,index,i,res,n;
        while (sc.hasNext()) {
            total = 0;
            n=0;
            len= sc.nextInt();
            index = sc.nextInt();
            String s = sc.next();
            String a[] = s.split("~");
            for (i = 0; i < len; i++) {
                total += Double.parseDouble(a[i]);
            }
            res = (int) Math.floor(total);
            while(res!=0)
            {
                res/=10;
                n++;
            }
            for (i = 0; i < index - n - 4; i++) {
                System.out.print(" ");
            }
            System.out.printf("%.3f\n", total);
        }
    }
}
