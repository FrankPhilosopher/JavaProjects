package ACM.CSU;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class CSU1082 {

	public static YearCows yearOne=new YearCows
	(1,BigInteger.ONE,BigInteger.ZERO,BigInteger.ONE);
	public static YearCows yearTwo=new YearCows
	(2,BigInteger.ZERO,BigInteger.ONE,BigInteger.ONE);
	public static ArrayList<YearCows> years=new ArrayList<YearCows>();
	
	public static void main(String[] args) {
		Scanner sin = new Scanner(System.in);
		years.add(yearOne);
		years.add(yearTwo);
		for(int i=3;i<66;i++){
			setCow(i);
		}
		while(sin.hasNext()){
			int n=sin.nextInt();
			System.out.println(years.get(n-1).sum);
		}
	}
	
	public static void setCow(int n) {
		YearCows lastyear=years.get(n-2);
		YearCows newYear=new YearCows();
		newYear.small=lastyear.big.multiply(new BigInteger("2"));
		newYear.big=lastyear.small.add(lastyear.big);
		newYear.sum=newYear.small.add(newYear.big);
		years.add(newYear);
	}

}
class YearCows{
	int year;
	BigInteger small;
	BigInteger big;
	BigInteger sum;
	
	public YearCows(){
		
	}
	
	public YearCows(int year,BigInteger small,BigInteger big,BigInteger sum){
		this.year=year;
		this.small=small;
		this.big=big;
		this.sum=sum;
	}
}
