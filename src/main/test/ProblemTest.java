import controller.Problem;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class ProblemTest {

    @Test
    public void getPunctaj() {
        Problem pr = new Problem();
        assertEquals(3, pr.getUserProblemScore(1, 5)); //trebuie adaugate punctaje in tabela pentru a putea testa
    }

    @Test
    public void getPunctajNull() {
        Problem pr = new Problem();
        assertEquals(-1, pr.getUserProblemScore(2, 3));
    }
}