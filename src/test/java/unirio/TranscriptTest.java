package unirio;

import org.junit.Assert;
import org.junit.Test;

public class TranscriptTest {

    @Test
    public void enrollmentYear() throws Exception {
        Transcript t = Transcript.builder().studentCode("20171210038").build();

        Assert.assertEquals("Enrollment year is 2017", t.getEnrollmentYear(), 2017);
    }
}
