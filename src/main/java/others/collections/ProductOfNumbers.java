package others.collections;

public class ProductOfNumbers {
    private int[] ara;
    int id =0;

    public ProductOfNumbers() {
        this.ara = new int[10001];
    }
    public void add(int num) {
        ara[id++] = num;
    }
    public int getProduct(int k) {
        int index = id -1;
        int ans=1;
        while (k>0){
         ans*=ara[index--];
         k--;
        }
        return ans;
    }
}
