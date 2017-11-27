package unirio;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class TranscriptItemTest {

    @Test
    public void belongsToBsi() throws Exception {
        TranscriptItem item;

        item = TranscriptItem.builder().code("TIN0000").build();
        Assert.assertEquals("Starts with TIN so yes", true, item.belongsToBSI());

        item = TranscriptItem.builder().code("ABC0000").build();
        Assert.assertEquals("Doesn't start with TIN so no", false, item.belongsToBSI());
    }

    @Test
    public void isMandatory() throws Exception {
        TranscriptItem item;

        item = TranscriptItem.builder().code("TIN0107").build();
        Assert.assertEquals("TIN0107 is in the Mandatory array, so yes", true, item.isMandatory());

        item = TranscriptItem.builder().code("TIN0000").build();
        Assert.assertEquals("TIN0000 isn't in the Mandatory array, so no", false, item.isMandatory());
    }
}
