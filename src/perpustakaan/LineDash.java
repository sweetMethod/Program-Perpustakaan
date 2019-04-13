package perpustakaan;

import com.itextpdf.text.pdf.PdfContentByte;

/*
* Sumber https://itextpdf.com/en/resources/faq/technical-support/itext-5/how-define-different-border-types-single-cell
*/

public interface LineDash {
   public void applyLineDash(PdfContentByte canvas);
}
