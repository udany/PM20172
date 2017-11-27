package unirio;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class HelloWorldTest {

    @Test
    public void name() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        App.print(new PrintStream(out));
        String s = out.toString();
        Assert.assertEquals("Hello", s);
    }
}
