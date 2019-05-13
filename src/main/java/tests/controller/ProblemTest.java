package controller;

import org.junit.Test;

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
        assertEquals(null ,pr.getEnuntProblema(223));
    }

    @Test
    public void getPunctaj() {
        Problem pr = new Problem();
        assertEquals(3, pr.getPunctaj(1,5));
    }

    @Test
    public void getPunctajNull() {
        Problem pr = new Problem();
        assertEquals(-1,pr.getPunctaj(2,3));
    }
}