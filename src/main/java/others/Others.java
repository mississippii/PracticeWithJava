package others;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Others {

    private static int factorial(int x){
        if(x==1 || x==0)return 1;
        return x*factorial(--x);
    }

    private static void swap(int a,int b){
        a = a+b-(b=a);
        System.out.println(a+" "+b);
        return;
    }

    private static int singleNumber(int[] nums) {
        int ans = 0;
        for (int i = 0; i < 32; i++) {
            int cnt = 0;
            for (int j : nums) {
                cnt += (j >> i) & 1;
            }
            ans = ans | (cnt % 3) << i;
        }
        return ans;
    }
    public static void main(String[] args) throws IOException {
        swap(5,10);
    }
}
