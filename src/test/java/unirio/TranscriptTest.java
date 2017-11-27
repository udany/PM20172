package unirio;

import org.junit.Assert;
import org.junit.Test;

public class TranscriptTest {

    @Test
    public void enrollmentYear() throws Exception {
        Transcript t = Transcript.builder().studentCode("20171210001").build();

        Assert.assertEquals("Enrollment year is 2017", t.getEnrollmentYear(), 2017);
    }

    @Test
    public void integralizationPlan() throws Exception {
        Transcript t;

        t = Transcript.builder()
                .studentCode("20171210001")
                .semester(5)
                .build();

        Assert.assertEquals("Must not present (>=2014 && <7)", false, t.mustPresentIntegralizationPlan());

        t = Transcript.builder()
                .studentCode("20141210001")
                .semester(7)
                .build();

        Assert.assertEquals("Must  present (>=2014 && >=7)", true, t.mustPresentIntegralizationPlan());

        t = Transcript.builder()
                .studentCode("20131210001")
                .semester(7)
                .build();

        Assert.assertEquals("Must not present (<2014 && <11)", false, t.mustPresentIntegralizationPlan());

        t = Transcript.builder()
                .studentCode("20131210001")
                .semester(11)
                .build();

        Assert.assertEquals("Must  present (<2014 && >=11)", true, t.mustPresentIntegralizationPlan());
    }
}
