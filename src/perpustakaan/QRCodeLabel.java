package perpustakaan;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import perpustakaan.models.Buku;


public class QRCodeLabel {

    private PdfPTable layout;

    public QRCodeLabel(List<Buku> bukuList) {
        //Buat dan atur layout halaman
        layout = new PdfPTable(5);

        layout.setWidthPercentage(100);

        //Input data label ke layout halaman
        bukuList.stream().forEach((buku) -> {
            AtomicInteger jml = new AtomicInteger(buku.getJml_buku());
            while (jml.get() > 0) {
                try {
                    insert(layout, buku);
                } catch (IOException | WriterException | BadElementException e) {
                }
                jml.getAndDecrement();
            }
        });

        //Jika jumlah cell pada baris terakhir kurang dari 5 column
        int totalItem = bukuList.stream().mapToInt(Buku::getJml_buku).sum();
        if (totalItem % 5 > 0) {
            AtomicInteger sisa = new AtomicInteger(5 - (totalItem % 5));
            while (sisa.get() > 0) {
                PdfPCell emptyCell = new PdfPCell();
                emptyCell.setBorder(Rectangle.NO_BORDER);
                layout.addCell(emptyCell);
                sisa.getAndDecrement();
            }
        }
    }

    private void insert(PdfPTable layout, Buku buku) throws IOException, WriterException, BadElementException {
        //QR Code
        Image image = Image.getInstance(createQRCode(buku.getNomor_buku()));
        PdfPCell imageCell = new PdfPCell(image, true);
        imageCell.setBorder(Rectangle.NO_BORDER);

        //Judul
        AtomicReference<String> jdl_buku = new AtomicReference<>(buku.getJudul_buku());

        if (jdl_buku.get().length() > 70) jdl_buku.set(jdl_buku.get().substring(0, 70) + "...");

        PdfPCell textCell = new PdfPCell(new Phrase(jdl_buku.get(), new Font(Font.FontFamily.TIMES_ROMAN, 6)));
        textCell.setBorder(Rectangle.NO_BORDER);
        textCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

        //Label
        PdfPTable label = new PdfPTable(2);
        label.setWidthPercentage(100);
        label.addCell(imageCell);
        label.addCell(textCell);

        //Cell Label
        PdfPCell cellLabel = new PdfPCell(label);
        cellLabel.setBorder(Rectangle.NO_BORDER);
        cellLabel.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellLabel.setPadding(16);

        //Masukkan label ke dalam layout halaman
        layout.addCell(cellLabel);
    }

    private byte[] createQRCode(String text) throws WriterException, IOException {

        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 0);

        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 350, 350, hints);

        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", arrayOutputStream);
        return arrayOutputStream.toByteArray();
    }

    public PdfPTable getLayout() {
        return layout;
    }
}
