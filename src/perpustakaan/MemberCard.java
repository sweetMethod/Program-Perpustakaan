package perpustakaan;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import perpustakaan.models.Anggota;

public class MemberCard {
    private PdfPTable layout;

    public MemberCard(List<Anggota> anggotaList) {
        //Buat dan atur layout halaman
        layout = new PdfPTable(2);

        layout.setWidthPercentage(100);

        //Input data label ke layout halaman
        anggotaList.stream().forEach((anggota) -> {
            try {
                insert(layout, anggota);
            } catch (IOException | WriterException | BadElementException ex) {
                Logger.getLogger(MemberCard.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        //Jika jumlah cell pada baris terakhir kurang dari 2 column
        int totalItem = anggotaList.size();
        if (totalItem % 2 > 0) {
            AtomicInteger sisa = new AtomicInteger(2 - (totalItem % 2));
            while (sisa.get() > 0) {
                PdfPCell emptyCell = new PdfPCell();
                emptyCell.setBorder(Rectangle.NO_BORDER);
                layout.addCell(emptyCell);
                sisa.getAndDecrement();
            }
        }
    }

    private void insert(PdfPTable layout, Anggota anggota) throws IOException, WriterException, BadElementException {
        PdfPTable card = new PdfPTable(1);
        PdfPTable biodata = new PdfPTable(2);
        PdfPTable body = new PdfPTable(2);
        
        try {
            biodata.setWidths(new float[]{1,3});
        } catch (DocumentException ex) {
            Logger.getLogger(MemberCard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            body.setWidths(new float[]{4,1});
        } catch (DocumentException ex) {
            Logger.getLogger(MemberCard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Header
        PdfPCell hCell1 = new PdfPCell(new Phrase("SEKOLAH DASAR ISLAM TERPADU\nAL - HAMIDIYYAH",new Font(FontFamily.UNDEFINED, 14, Font.BOLD)));
        hCell1.setBorder(Rectangle.NO_BORDER);
        hCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        
        PdfPCell hCell2 = new PdfPCell(new Phrase("Perum. Bambu Kuning Blok F7 Rt. 15/13 Bojonggede Timur kec. Bojonggede",new Font(FontFamily.UNDEFINED, 8, Font.NORMAL)));
        hCell2.setBorder(Rectangle.NO_BORDER);
        hCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        
        PdfPCell hCell3 = new PdfPCell(new Phrase("Telp. 0877 7088 679",new Font(FontFamily.UNDEFINED, 8, Font.NORMAL)));
        hCell3.setBorder(Rectangle.NO_BORDER);
        hCell3.setCellEvent(new CustomBorder(null, null, null, new SolidLine()));
        hCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        hCell3.setPadding(3f);
        
        PdfPCell judulCell = new PdfPCell(new Phrase("KARTU PERPUSTAKAAN",new Font(FontFamily.UNDEFINED, 10, Font.BOLD)));
        judulCell.setBorder(Rectangle.NO_BORDER);
        judulCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        judulCell.setPadding(10f);
        
        //Biodata
        PdfPCell namaLabel = new PdfPCell(new Phrase("Nama", new Font(FontFamily.UNDEFINED, 12, Font.NORMAL)));
        PdfPCell nama = new PdfPCell(new Phrase(anggota.getNama_anggota(), new Font(FontFamily.UNDEFINED, 12, Font.NORMAL)));
        PdfPCell kelasLabel = new PdfPCell(new Phrase("Kelas", new Font(FontFamily.UNDEFINED, 12, Font.NORMAL)));
        PdfPCell kelas = new PdfPCell(new Phrase(anggota.getKelas(), new Font(FontFamily.UNDEFINED, 12, Font.NORMAL)));
        
        namaLabel.setBorder(Rectangle.NO_BORDER);
        nama.setBorder(Rectangle.NO_BORDER);
        kelasLabel.setBorder(Rectangle.NO_BORDER);
        kelas.setBorder(Rectangle.NO_BORDER);
        
        biodata.addCell(namaLabel);
        biodata.addCell(nama);
        biodata.addCell(kelasLabel);
        biodata.addCell(kelas);
        
        PdfPCell biodataCell = new PdfPCell(biodata);
        biodataCell.setBorder(Rectangle.NO_BORDER);
        
        //QR Code
        Image image = Image.getInstance(createQRCode(String.valueOf(anggota.getId_anggota())));
        image.scaleToFit(60, 60);
        PdfPCell imageCell = new PdfPCell(image);
        imageCell.setBorder(Rectangle.NO_BORDER);
        imageCell.setHorizontalAlignment(Element.ALIGN_RIGHT);

        body.addCell(biodataCell);
        body.addCell(imageCell);
        
        PdfPCell bodyCell = new PdfPCell(body);
        bodyCell.setBorder(Rectangle.NO_BORDER);
        
        //Label
        card.addCell(hCell1);
        card.addCell(hCell2);
        card.addCell(hCell3);
        card.addCell(judulCell);
        card.addCell(bodyCell);
        
        //Cell Card
        PdfPCell cardCell = new PdfPCell(card);
        cardCell.setPadding(20);

        //Masukkan label ke dalam layout halaman
        layout.addCell(cardCell);
    }

    private byte[] createQRCode(String text) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 0);

        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 1, 1, hints);

        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", arrayOutputStream);
        return arrayOutputStream.toByteArray();
    }

    public PdfPTable getLayout() {
        return layout;
    }
}
