package test;

import java.util.HashMap;

import manager.CarManager;

public class Test1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CarManager cManager=new CarManager();
		HashMap<String,String> hmHashMap=new HashMap<String, String>();
		HashMap<String,String> hmHashMap2=new HashMap<String, String>();
		hmHashMap.put("status","2");
		hmHashMap2.put("number","湘A336");
		int resutlt=cManager.update(hmHashMap,hmHashMap2);
		if(resutlt==0){
			System.out.println("更改失败");
		}else{			
			System.out.println("更改成功");
		}
	}

}
