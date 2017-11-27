package unirio;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
public class TranscriptItem {
    @Getter private String code;
    @Getter private String name;
    @Getter private String cr;
    @Getter private String ch;
    @Getter private String grade;
    @Getter private String attendance;
    @Getter private String statusCode;
}