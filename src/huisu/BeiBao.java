package huisu;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class BeiBao {


    @Test
    public void test() {
        System.out.println();
    }


    Integer[][] memo;  // memo[i][j]: [0,i]物品填充容量为j获得最大价值

    private int beibao1(List<Integer> w, List<Integer> v, int c) {
        int n = w.size();   // n个物品
        memo = new Integer[n][c + 1];
        return bestValue(w, v, n - 1, c);
    }

    /**
     * @param w     重量
     * @param v     价值
     * @param index
     * @param c     容量
     * @return [0, index]物品 填充容量为c获得最大价值
     */
    private int bestValue(List<Integer> w, List<Integer> v, int index, int c) {
        if (index < 0 || c <= 0)
            return 0;

        if (memo[index][c] != null)
            return memo[index][c];

        //不拿index物品
        int res = bestValue(w, v, index - 1, c);
        //拿index物品
        if (c >= w.get(index)) {
            res = Math.max(v.get(index) + bestValue(w, v, index - 1, c - w.get(index)), res);
        }
        memo[index][c] = res;
        return res;
    }

    //动态规划
    public int beibao2(List<Integer> w, List<Integer> v, int c) {
        int n = w.size();
        //dp[i][j]: [0,i]物品填充容量j获得最大价值
        //dp[i][j] = max( dp[i-1][j], dp[i-1][j-w[i]] + v[i] )
        int[][] dp = new int[n][c + 1];
        for (int j = 0; j <= c; j++) {
            //先解决 0 物品在所有容量的情况, 因为在当前行, 总会用到上一行
            dp[0][j] = (j >= w.get(0) ? v.get(0) : 0);
        }
        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= c; j++) {
                //不拿 i 物品
                dp[i][j] = dp[i - 1][j];
                if (j >= w.get(i)) {
                    //拿 i 物品
                    dp[i][j] = Math.max(dp[i - 1][j - w.get(i)] + v.get(i), dp[i][j]);
                }
            }
        }
        return dp[n - 1][c];
    }

    //空间优化
    public int beibao3(List<Integer> w, List<Integer> v, int c) {
        int n = w.size();
        //dp[i][j]: [0,i]物品填充容量j获得最大价值
        //当前行只会用到上一行, 所以只用两行
        int[][] dp = new int[2][c + 1];
        for (int j = 0; j <= c; j++) {
            //先解决 0 物品在所有容量的情况, 因为在当前行, 总会用到上一行
            dp[0][j] = j >= w.get(0) ? v.get(0) : 0;
        }
        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= c; j++) {
                //不拿 i 物品
                dp[i % 2][j] = dp[(i - 1) % 2][j];
                if (j >= w.get(i)) {
                    //拿 i 物品
                    dp[i % 2][j] = Math.max(dp[(i - 1) % 2][j - w.get(i)] + v.get(i), dp[i % 2][j]);
                }
            }
        }
        return dp[(n - 1) % 2][c];
    }

    //空间再优化
    public int beibao4(List<Integer> w, List<Integer> v, int c) {
        int n = w.size();
        //dp[i]: 填充容量j获得最大价值
        int[] dp = new int[c + 1];
        for (int j = 0; j <= c; j++) {
            //先解决 0 物品在所有容量的情况, 因为在当前行, 总会用到上一行
            dp[j] = j >= w.get(0) ? v.get(0) : 0;
        }
        for (int i = 1; i < n; i++) {
            //i : 考虑到第i个物品
            for (int j = c; j >= w.get(i); j--) {
                dp[j] = Math.max(dp[j], v.get(i) + dp[j - w.get(i)]);
            }
        }
        return dp[c];
    }

    private int wanquanBeibao(List<Integer> w, List<Integer> v, int c) {
        int n = w.size();
        //dp[i][j]: [0,i]物品填充容量j获得最大价值
        //dp[i][j] = max( dp[i-1][j], dp[i][j-w[i]] + v[i] )
        int[][] dp = new int[n][c + 1];
        for (int j = 0; j <= c; j++) {
            dp[0][j] = (j >= w.get(0) ? dp[0][j - w.get(0)] + v.get(0) : 0);
        }
        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= c; j++) {
                dp[i][j] = dp[i - 1][j];  //不拿i物品
                if (j >= w.get(i)) {
                    //拿i物品,  dp[i][j-w.get(i)]可能也拿了若干个i物品
                    dp[i][j] = Math.max(dp[i][j], dp[i][j - w.get(i)] + v.get(i));
                }
            }
        }
        return dp[n - 1][c];
    }

    //空间优化
    private static int wanquanBeibao1(List<Integer> w, List<Integer> v, int c) {
        int n = w.size();
        int[] dp = new int[c + 1];
        for (int j = 0; j <= c; j++) {
            dp[j] = (j >= w.get(0) ? dp[j - w.get(0)] + v.get(0) : 0);
        }
        for (int i = 1; i < n; i++) {
            for (int j = w.get(i); j <= c; j++) {
                // 参照上一个格子和同一行左边的格子
                dp[j] = Math.max(dp[j], dp[j - w.get(i)] + v.get(i));
            }
        }
        return dp[c];
    }


    private int wanquanBeibao2(List<Integer> w, List<Integer> v, int c) {
        int n = w.size();
        memo = new Integer[n][c + 1];
        return bestValue1(w, v, n - 1, c);
    }

    /**
     * @return [0, index]物品 填充容量为c获得最大价值
     */
    private int bestValue1(List<Integer> w, List<Integer> v, int index, int c) {
        if (index < 0 || c == 0) return 0;

        if (memo[index][c] != null) return memo[index][c];

        // 不拿index物品
        int res = bestValue1(w, v, index - 1, c);
        if( c >= w.get(index) ) {
            // 拿index物品   bestValue1(w, v, index, c - w.get(index))  可能还会拿若干个index物品
            res = Math.max(res, bestValue1(w, v, index, c - w.get(index) ) + v.get(index));
        }
        memo[index][c] = res;
        return res;
    }

    public void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt(); // 物品种数
        int c = scanner.nextInt(); // 背包容积
        List<Integer> w = new ArrayList<>();
        List<Integer> v = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            w.add(scanner.nextInt());
            v.add(scanner.nextInt());
        }
        int max = wanquanBeibao1(w, v, c);
        System.out.println(max);
    }


}