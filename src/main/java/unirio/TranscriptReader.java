package unirio;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.EventType;
import com.itextpdf.kernel.pdf.canvas.parser.PdfCanvasProcessor;
import com.itextpdf.kernel.pdf.canvas.parser.data.IEventData;
import com.itextpdf.kernel.pdf.canvas.parser.data.TextRenderInfo;
import com.itextpdf.kernel.pdf.canvas.parser.filter.TextRegionEventFilter;
import com.itextpdf.kernel.pdf.canvas.parser.listener.FilteredEventListener;
import com.itextpdf.kernel.pdf.canvas.parser.listener.LocationTextExtractionStrategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TranscriptReader {
    private String src;
    private PdfDocument doc;
    private int pageCount;

    public TranscriptReader(String src) throws IOException{
        this.src = src;

        File f = new File(src);
        if (!f.exists()){
            throw new FileNotFoundException("Could find PDF: "+src);
        }

        doc = new PdfDocument(new PdfReader(src));
        pageCount = doc.getNumberOfPages();
    }

    private String readPage(int index) throws Exception{
        if (index > pageCount) {
            throw new Exception("Page is above the page count");
        }

        PdfPage page = doc.getPage(index);

        Rectangle rect = page.getPageSize().clone();

        FontFilter fontFilter = new FontFilter(rect);
        FilteredEventListener listener = new FilteredEventListener();
        LocationTextExtractionStrategy extractionStrategy = listener.attachEventListener(new LocationTextExtractionStrategy(), fontFilter);
        new PdfCanvasProcessor(listener).processPageContent(page);

        return  extractionStrategy.getResultantText();
    }

    public String read() throws Exception{
        StringBuilder b = new StringBuilder();
        for (int i = 1; i <= pageCount; i++){
            b.append(readPage(i));
            b.append("\n");
        }

        return b.toString();
    }

    public void close(){
        doc.close();
    }

    private class FontFilter extends TextRegionEventFilter {
        public FontFilter(Rectangle filterRect) {
            super(filterRect);
        }

        @Override
        public boolean accept(IEventData data, EventType type) {
            if (type.equals(EventType.RENDER_TEXT)) {
                TextRenderInfo renderInfo = (TextRenderInfo) data;

                PdfFont font = renderInfo.getFont();
                if (null != font) {
                    String fontName = font.getFontProgram().getFontNames().getFontName();
                    return !fontName.endsWith("Bold") && !fontName.endsWith("Oblique");
                }
            }
            return false;
        }
    }
}