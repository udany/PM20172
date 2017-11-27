package unirio;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

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

    @Test
    public void itemCounts() throws Exception {
        Transcript t = Transcript.builder()
                .studentCode("20171210001")
                .items(Arrays.asList(
                        TranscriptItem.builder().code("TIN0107").statusCode(TranscriptItemStatus.Failed.code).build(),//TP1
                        TranscriptItem.builder().code("TIN0107").statusCode(TranscriptItemStatus.Approved.code).build(),
                        TranscriptItem.builder().code("TIN0112").statusCode(TranscriptItemStatus.Approved.code).build(),//FSI
                        TranscriptItem.builder().code("TIN0108").statusCode(TranscriptItemStatus.Enrolled.code).build(),//OC
                        TranscriptItem.builder().code("TIN0000").statusCode(TranscriptItemStatus.Approved.code).build(),//Optativa
                        TranscriptItem.builder().code("ABC0000").statusCode(TranscriptItemStatus.Approved.code).build()//Eletiva
                ))
                .build();

        Assert.assertEquals("2 Mandatory", 2, t.mandatoryClassesTaken());
        Assert.assertEquals("1 Enrolled", 1, t.currentlyEnrolledClasses());
        Assert.assertEquals("1 Optional", 1, t.optionalClassesTaken());
        Assert.assertEquals("1 Elective", 1, t.electiveClassesTaken());
    }

    @Test
    public void canFinishInTime() throws Exception {
        Transcript t;


        t = Transcript.builder()
                .studentCode("20171210001")
                .semester(1)
                .items(new ArrayList<>())
                .build();
        Assert.assertEquals("2017.1 can finish with no subjects taken", true, t.canFinishInTime());


        t = Transcript.builder()
                .studentCode("20171210001")
                .semester(6)
                .items(new ArrayList<>())
                .build();
        Assert.assertEquals("2017.1 can't finish with no subjects taken at 6th semester", false, t.canFinishInTime());


        t = Transcript.builder()
                .studentCode("20131210001")
                .semester(6)
                .items(new ArrayList<>())
                .build();
        Assert.assertEquals("2013.1 can finish with no subjects taken at 6th semester", true, t.canFinishInTime());


        t = Transcript.builder()
                .studentCode("20131210001")
                .semester(8)
                .items(new ArrayList<>())
                .build();
        Assert.assertEquals("2013.1 can't finish with no subjects taken at 8th semester", false, t.canFinishInTime());

    }
}
