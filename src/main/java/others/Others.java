package others;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;

public class Others {
    public static void main(String[] args) throws IOException {
        Integer[] ara =new Integer[] {34, 4, 12, 70, 21, 65};
        int x = Arrays.binarySearch(ara, 12);
        System.out.println(x);

    }
}
