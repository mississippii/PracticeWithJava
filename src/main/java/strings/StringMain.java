package strings;

public class StringMain {
    private static int countChar(int start, int end , String s, char c){
        char[] ara = s.toCharArray();
        int count = 0;
        for(int i=start;i<=end;i++){
            if(ara[i]==c) count++;
        }
        return count;
    }
    public static int minimumDeletions(String s) {
        int countB = 0, deletions = 0;
        for (char c : s.toCharArray()) {
            if (c == 'b') {
                countB++;
            } else {
                deletions = Math.min(deletions + 1, countB);
            }
        }
        return deletions;
    }
    public static void main(String[] args) {
        /**
        Stored in the string pool. String pool is a special memory area in the heap
         */
        String str1 = "aababbab";
        int ans = minimumDeletions(str1);
        /**
         Create a new object in the heap outside the string pool
        */
        String str3 = new String("Hello java");
        String str4 = new String("Hello java");

        char[] ch = {'H','e','l','l','o'};
        String str5 = new String(ch);
        /**
         when you need to modify a string frequently, use StringBuilder
         */
        StringBuilder sb = new StringBuilder("Hello java");
        sb.append(" world");
        String str6 = sb.toString();
        System.out.println(str6);
        /**
         similar to StringBuilder but thread safe. slightly slower than StringBuilder
         */
        StringBuffer sb2 = new StringBuffer("Hello java");
        sb2.append(" world");
        String str7 = sb2.toString();
        System.out.println(str7);

        String s1 = "Tanvir";
        System.out.println(s1.hashCode());
        s1 = "Tanvir hasan";
        System.out.println(s1.hashCode());

    }
}
