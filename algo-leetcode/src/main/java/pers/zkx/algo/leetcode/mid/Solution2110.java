package pers.zkx.algo.leetcode.mid;

/**
 * @author: zhangkuixing
 * @date: 2025/12/15 23:43
 */
public class Solution2110 {
    public long getDescentPeriods(int[] prices) {
        if(prices== null || prices.length==0){
            return 0L;
        }
        long ans = prices.length;
        long curSeqLen = 1L;
        for(int i=1;i<prices.length;i++){
            if(prices[i-1]-prices[i]==1){
                curSeqLen++;
            }else{
                ans += (curSeqLen-1)*curSeqLen/2;
                curSeqLen = 1L;
            }
        }
        ans += (curSeqLen-1)*curSeqLen/2;
        return ans;
    }
}
