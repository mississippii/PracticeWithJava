package Threading;

import java.math.BigDecimal;

public class FibonacciNuber {
    public int n;
    public String id;
    private long memo[] = new long[60];
    public FibonacciNuber(int n, String id) {
        this.n = n;
        this.id = id;
        for(int i=0;i<60;i++){
            memo[i]=-1;
        }
    }
    public long fib(int n){
        if(n<=1)return memo[n]=n ;
        if(memo[n]!=-1) return memo[n];
        return memo[n]= fib(n-1)+(fib(n-2));
    }
}
