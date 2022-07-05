import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StepTest {

    @Test
    void getNumber() {
        Step step = new Step("1 2 3");
        assertEquals(1,step.getNumber());
    }

    @Test
    void getRow() {
        Step step = new Step("1 2 3");
        assertEquals(2,step.getCoordinate().getRow());
    }

    @Test
    void getColumn() {
        Step step = new Step("1 2 3");
        assertEquals(3,step.getCoordinate().getColumn());
    }
}