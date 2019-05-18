import Model.dao.Problem;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static Model.dao.Test.*;
import static Model.dao.User.*;
public class ProblemTest {

    @Test
    public void getPunctaj() {
        assertEquals(3, getUserProblemScore(1, 5)); //trebuie adaugate punctaje in tabela pentru a putea testa
    }

    @Test
    public void getPunctajNull() {
        Problem pr = new Problem();
        assertEquals(-1, getUserProblemScore(2, 3));
    }
}