/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2025 All Rights Reserved.
 */
package pers.zkx.algo.leetcode.week2.w171;

/**
 *
 * @author kui yuan
 * @version $Id: CompletePrime, v 0.1 2025-12-06 22:31 your_name Exp $
 */
public class CompletePrime {
    public boolean completePrime(int num) {
        if(num<=1){
            return false;
        }
        // 计算每个前缀
        int temp=num;
        while(temp>0){
            System.out.println(temp);
            if(!checkPrime(temp)){
                return false;
            }
            temp/=10;
        }
        // 计算每个后缀
        temp=num;
        int base=10;
        while(temp>0) {
            System.out.println(num % base);
            if (!checkPrime(num % base)) {
                return false;
            }
            base *= 10;
            temp /= 10;
        }
        return true;
    }

    private boolean checkPrime(int num){
        if (num<=1){
            return false;
        }
        if(num==2){
            return true;
        }
        for(int i=2;i<=Math.sqrt(num);i++){
            if(num%i==0){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        CompletePrime completePrime=new CompletePrime();
        System.out.println(completePrime.completePrime(23));
    }
}
