package learn.suanfa;

import java.util.ArrayList;
import java.util.List;

/**
 *Created by chengliang on 2018/7/23.
 */
public class PaiXu {
    
    public static void main(String[] args){
        ArrayList<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(3);
        list.add(5);
        list.add(7);
        list.add(6);
        list.add(10);
        list.add(1);
        list.add(8);
        list.add(9);
        list.add(2);
        list.add(4);
       // List<Integer> integers = maoPao(list);
        List<Integer> integers = xuanZhe(list);
        for (int i = 0; i <integers.size() ; i++) {
            System.out.println(integers.get(i));
        }
    }
    
    /**
     * 冒泡排序
     *@Author:chengliang
     *@date 2018/7/23_15:12
     *@param 
     *@return 
     */
    public static List<Integer> maoPao(List<Integer> list){
        for (int i = 0; i < list.size(); i++) {
            for (int j = i+1; j < list.size(); j++) {
                if(list.get(i) > list.get(j)){
                    int change = list.get(i);
                    list.set(i,list.get(j));
                    list.set(j,change);
                }
            }
        }
        return list;
    }
    
    /**
     * 选择排序
     *@Author:chengliang
     *@date 2018/7/23_15:12
     *@param 
     *@return 
     */
    public static List<Integer> xuanZhe(List<Integer> list){
        int nextMinIndex = 0;
        int minIndex = -1;
        
        for (int i = 0; i < list.size(); i++) {
            //获取第一个最小的数
            minIndex = nextMinIndex;
            for (int j = nextMinIndex+1; j < list.size() ; j++) {
                if(list.get(minIndex) > list.get(j)){
                    minIndex = j;
                }
            }
            if(minIndex == nextMinIndex){
                nextMinIndex++;
                continue;
            }
            int change = list.get(minIndex);
            list.set(minIndex,list.get(nextMinIndex));
            list.set(nextMinIndex,change);
            nextMinIndex++;
        }
        
        return list;
    }
    
    
}
