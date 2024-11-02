package Others.Collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorTest {
    @Test
    public void firstTest() {
        Calculator calc = new Calculator();
        int result = calc.add(5, 3);
        Assertions.assertEquals(8,result);
        System.out.println("First test passed!");
    }
}
