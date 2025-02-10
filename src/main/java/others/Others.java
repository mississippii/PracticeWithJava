package others;

import java.io.IOException;
import java.nio.file.Path;

public class Others {
    public static void main(String[] args) throws IOException {
        Path path = Path.of("/home/veer/Documents/Java/PracticeWithJava/src/main/resources/hello.txt");
        System.out.println(path);
    }
}
