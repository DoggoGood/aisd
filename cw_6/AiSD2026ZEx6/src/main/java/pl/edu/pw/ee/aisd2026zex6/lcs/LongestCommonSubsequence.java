package pl.edu.pw.ee.aisd2026zex6.lcs;

public class LongestCommonSubsequence {

    
    public String findLcs(String left, String top){
        int[][] dp = new int[left.length() + 1][top.length() + 1];
        for (int i = 1; i < left.length(); i++) {
            for (int j = 1; j < top.length(); j++) {
                if (left.[i] == top[j]) {
                    dp[i-1][j-1]++;
                }
            }
        }
        return null;
    }
    
}
