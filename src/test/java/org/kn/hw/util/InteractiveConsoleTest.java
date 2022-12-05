package org.kn.hw.util;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class InteractiveConsoleTest {

    private static final ByteArrayOutputStream oStream = new ByteArrayOutputStream();
    private static final PrintStream stdout = System.out;


    @BeforeAll
    public static void setOutStreams() {
        //temporal redirection of stdout for a clearer screen during tests.
        System.setOut(new PrintStream((oStream)));
    }

    @Test
    public void currencyMustHaveThreeLetters() {
        String input = "abc";

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        assertEquals(InteractiveConsole.acceptCurrency().length(), 3);
    }

    @Test
    public void currencyMustHaveOnlyAlphabetLetters() {
        String input = "ab3";

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        assertThrows(NoSuchElementException.class, InteractiveConsole::acceptCurrency);
    }

    @Test
    public void currencyWithMoreThanThreeLettersThrowsException() {
        String input = "abcd";

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        assertThrows(NoSuchElementException.class, InteractiveConsole::acceptCurrency);
    }

    @Test
    public void currencyWithLessThanThreeLettersThrowsException() {
        String input = "ab";

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        assertThrows(NoSuchElementException.class, InteractiveConsole::acceptCurrency);
    }

    @Test
    public void keepOnThrowsExceptionWhenWrongDigitEntered() {
        String input = "2";

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        assertThrows(NoSuchElementException.class, InteractiveConsole::keepOn);
    }

    @Test
    public void keepOnThrowsExceptionWhenNonDigitCharsEntered() {
        String input = "a2";

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        assertThrows(NoSuchElementException.class, InteractiveConsole::keepOn);
    }

    @Test
    public void keepOnReturnsTrueWhen1() {
        String input = "1";

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        assertTrue(InteractiveConsole.keepOn());
    }

    @Test
    public void keepOnReturnsFalseWhen0() {
        String input = "0";

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        assertFalse(InteractiveConsole.keepOn());
    }


    @AfterAll
    public static void resetInputStream() throws Exception {
        oStream.flush();
        oStream.close();
        System.setOut(stdout);
    }
}