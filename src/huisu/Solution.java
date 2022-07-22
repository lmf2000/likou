package huisu;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;


public class Solution {

    @Test
    public void test() {
        System.out.println(reconstructQueue(new int[][]{{7, 0}, {4, 4}, {7, 1}, {5, 0}, {6, 1}, {5, 2}}));
        System.out.println("hot-fix edit");
    }

    public int[][] reconstructQueue(int[][] people) {
        Arrays.sort(people, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if( o1[0] != o2[0]) {  //身高不相等, 降序排序
                    return o2[0] - o1[0];
                } else {       //身高相同  按排名升序
                    return o2[1] - o1[1];
                }
            }
        });
        //此时升高是降序 根据排名逐个插入
        ArrayList<Object> ans = new ArrayList<>();
        for (int[] person : people) {
            ans.add(person[1], person);  //根据排名插入
        }
        return ans.toArray(new int[ans.size()][]);
    }

}

