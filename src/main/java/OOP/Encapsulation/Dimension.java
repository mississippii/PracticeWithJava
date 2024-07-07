package OOP.Encapsulation;

public class Dimension {
    private final int height;
    private final int width;
    private final int length;

    public Dimension(int height, int width, int length) {
        this.height = height;
        this.width = width;
        this.length = length;
    }
    public int getVolume(){
        return height * width * length;
    }
}
