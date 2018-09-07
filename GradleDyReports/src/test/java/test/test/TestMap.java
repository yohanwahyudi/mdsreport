package test.test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TestMap {
	
	public static void main(String args[]) {
		Map<String, Integer> map1 = new HashMap<String, Integer>();
		
		map1.put("a", 1);
		map1.put("b", 2);
		map1.put("c", 5);
		
		System.out.println(map1.get("a"));
		System.out.println(map1.get("d"));
		
		Iterator<Map.Entry<String, Integer>> iterator = map1.entrySet().iterator();
		while(iterator.hasNext()) {
			Map.Entry<String, Integer> entry = iterator.next();
			
			System.out.println(entry.getKey()+":"+entry.getValue());
		}
	}

}
