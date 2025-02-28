package others.collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorTest {
    @Test
    public void firstTest() {
        Calculator calc = new Calculator();
        Assertions.assertEquals(8,7);
        System.out.println("First test passed!");
    }
}
