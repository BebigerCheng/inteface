package learn;

import learn.mapmax.MapMax;
import org.apache.commons.collections4.map.CaseInsensitiveMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *Created by chengliang on 2018/7/20.
 */
public class SomeTest {
    
    /*public static void main(String[] args){
        Map<String, String> map = new HashMap<>();
        map.put("uSerNaMe","chengliang");
        Map<String, String> result = new CaseInsensitiveMap<>(map);
        System.out.println(result.get("username"));
        
        
        String s = "equalsIgnoreCase";
        String s2 = "equalsignorecase";
        
        System.out.println(s.equalsIgnoreCase(s2));
    }*/
    
    public static void main(String[] args){
        MapMax mapMax = new MapMax();
        mapMax.put("1", "value1");
        mapMax.put("2", "value2");
        mapMax.put("3", "value3");

        mapMax.put("1", "value4");
        Object s = mapMax.get("1");
        System.out.println(s);
        
        /*//第一种：普遍使用，二次取值  
        System.out.println("通过Map.keySet遍历key和value：");
        for (String key : map.keySet()) {
            System.out.println("key= "+ key + " and value= " + map.get(key));
        }

        //第二种  
        System.out.println("通过Map.entrySet使用iterator遍历key和value：");
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
        }

        //第三种：推荐，尤其是容量大时  
        System.out.println("通过Map.entrySet遍历key和value");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
        }

        //第四种  
        System.out.println("通过Map.values()遍历所有的value，但不能遍历key");
        for (String v : map.values()) {
            System.out.println("value= " + v);
        }*/
    }
}

