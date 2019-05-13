package controller;

import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class ProblemTest {

    @Test
    public void getEnuntProblema() {
        Problem pr = new Problem();
        assertEquals("Fie x un numar natural de trei cifre. Scrieti un program care sa elimine una dintre cifrele numarului astfel incat numarul de doua cifre ramas sa fie maxim.", pr.getEnuntProblema(2));
    }

    @Test
    public void getEnuntProblemaNull() {
        Problem pr = new Problem();
        assertNull(pr.getEnuntProblema(223));
    }

    @Test
    public void getPunctaj() {
        Problem pr = new Problem();
        assertEquals(3, pr.getPunctaj(1,5)); //trebuie adaugate punctaje in tabela pentru a putea testa
    }

    @Test
    public void getPunctajNull() {
        Problem pr = new Problem();
        assertEquals(-1,pr.getPunctaj(2,3));
    }

    @Test
    public void getLastProblemIdTest() {
        Problem pr = new Problem();
        try {
            assertNotNull(pr.getLastProblemId());
        } catch (SQLException e) {
            assertNull(e);
        }
    }
}