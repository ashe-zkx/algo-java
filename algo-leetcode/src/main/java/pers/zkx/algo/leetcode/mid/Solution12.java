package pers.zkx.algo.leetcode.mid;

/**
 * Solution12
 *
 * @author zhangkuixing
 * @since 2026-01-14
 */
public class Solution12 {

    public String intToRoman(int num) {
        StringBuilder sb = new StringBuilder();
        while (num>0){
            if (num>=1000){
                sb.append(numToStr(num/1000*1000));
                num = num%1000;
            }else if (num>=100){
                sb.append(numToStr(num/100*100));
                num = num%100;
            }else if (num>=10){
                sb.append(numToStr(num/10*10));
                num = num%10;
            }else {
                sb.append(numToStr(num));
                num = 0;
            }
        }
        return sb.toString();
    }

    private String numToStr(int num){
        if (num<=9){
            return numToStr(num,"I","V","X");
        }else if(num<=90){
            return numToStr(num/10,"X","L","C");
        }else if(num<=900){
            return numToStr(num/100,"C","D","M");
        }else if (num<=9000){
            return "M".repeat(num/1000);
        }
        return "";
    }

    private String numToStr(int num, String one, String five, String ten){
        if (num<=9){
            if (num<=3){
                return one.repeat(num);
            }else if (num==4){
                return one + five;
            }else if (num==5){
                return five;
            }else  if (num==9){
                return one + ten;
            }else {
                return five + one.repeat(num-5);
            }
        }
        if (num==10){
            return ten;
        }
        return "";
    }

    public static void main(String[] args) {
        Solution12 solution12 = new Solution12();
        System.out.println(solution12.intToRoman(3749));
    }

}
