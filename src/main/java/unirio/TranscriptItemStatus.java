package unirio;

public enum TranscriptItemStatus {
    Approved    ("APV"),
    Dismissed   ("DIS"),
    Enrolled    ("ASC"),
    Failed      ("REP"),
    FailedToAtt ("REF");

    public final String code;
    TranscriptItemStatus(String code) {
        this.code = code;
    }
}
