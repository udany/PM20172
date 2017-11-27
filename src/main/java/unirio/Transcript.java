package unirio;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transcript {
    @Getter private String studentName;
    @Getter private String studentCode;
    @Getter private List<TranscriptItem> items;

    public List<TranscriptItem> getItems(){
        return Collections.unmodifiableList(items);
    }

    public int getEnrollmentYear(){
        return studentCode.equals("") ? 0 : Integer.parseInt(studentCode.substring(0,4));
    }
}