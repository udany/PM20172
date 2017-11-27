package unirio;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class TranscriptReaderTest {

    @Test
    public void throwsExceptionOnFileNotFound() throws Exception {
        try {
            TranscriptReader reader = new TranscriptReader("nonfile");

            reader.close();
        }catch (FileNotFoundException e){
            return;
        }

        Assert.fail("Didn't throw exception");
    }

    @Test
    public void opensFileSuccessfully() throws Exception {
        try {
            TranscriptReader reader = new TranscriptReader("./data/daniel.pdf");

            reader.close();
        }catch (FileNotFoundException e){
            Assert.fail("Threw exception, shouldn't have: "+e.getMessage());
        }
    }

    @Test
    public void outputsText() throws Exception {
        try {
            TranscriptReader reader = new TranscriptReader("./data/daniel.pdf");

            String r = reader.read(true);

            Assert.assertTrue("Outputs a string longer than 0", r.length() > 0);

            reader.close();
        }catch (FileNotFoundException e){
            Assert.fail("Threw exception, shouldn't have: "+e.getMessage());
        }
    }
}
