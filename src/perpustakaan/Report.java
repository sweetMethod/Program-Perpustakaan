package perpustakaan;

import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.util.Date;
import java.util.List;
import perpustakaan.models.Anggota;
import perpustakaan.models.BayarDenda;
import perpustakaan.models.Buku;
import perpustakaan.models.Denda;
import perpustakaan.models.Kunjungan;
import perpustakaan.models.Peminjaman;
import perpustakaan.models.Pengembalian;

public class Report {

    private final PdfPTable layout;
    private PdfPCell cell;
    
    public Report() {
        //Buat dan atur layout halaman
        layout = new PdfPTable(1);
        
        //Set width layout
        layout.setWidthPercentage(100);
        
        // Header
        cell = new PdfPCell(new Phrase("SEKOLAH DASAR ISLAM TERPADU\nAL - HAMIDIYYAH",new Font(Font.FontFamily.UNDEFINED, 14, Font.BOLD)));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        layout.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Perum. Bambu Kuning Blok F7 Rt. 15/13 Bojonggede Timur kec. Bojonggede",new Font(Font.FontFamily.UNDEFINED, 8, Font.NORMAL)));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        layout.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Telp. 0877 7088 679",new Font(Font.FontFamily.UNDEFINED, 8, Font.NORMAL)));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setCellEvent(new CustomBorder(null, null, null, new SolidLine()));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(3f);
        layout.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Perihal   : Laporan Perpustakaan",new Font(Font.FontFamily.UNDEFINED, 10, Font.NORMAL)));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setPaddingTop(10f);
        layout.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Tanggal : "+Format.DateFormat(new Date()),new Font(Font.FontFamily.UNDEFINED, 10, Font.NORMAL)));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setPaddingTop(3f);
        layout.addCell(cell);
    }
    
    public void addAnggota(List<Anggota> anggotaList) {
        cell = new PdfPCell(new Phrase("Daftar Anggota",new Font(Font.FontFamily.UNDEFINED, 12, Font.BOLD)));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setPaddingTop(20f);
        cell.setPaddingBottom(10f);
        layout.addCell(cell);
        
        PdfPTable table = new PdfPTable(2);
        
        cell = new PdfPCell(new Phrase("Nama Anggota"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Kelas Anggota"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        for (Anggota anggota : anggotaList) {
            cell = new PdfPCell(new Phrase(anggota.getNama_anggota()));
            cell.setPaddingLeft(10f);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(anggota.getKelas()));
            cell.setPaddingLeft(10f);
            table.addCell(cell);
        }
        
        cell = new PdfPCell(table);
        layout.addCell(cell);
    }
    
    public void addBuku(List<Buku> bukuList) {
        cell = new PdfPCell(new Phrase("Daftar Buku",new Font(Font.FontFamily.UNDEFINED, 12, Font.BOLD)));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setPaddingTop(20f);
        cell.setPaddingBottom(10f);
        layout.addCell(cell);
        
        PdfPTable table = new PdfPTable(5);
        
        cell = new PdfPCell(new Phrase("Nomor Buku"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Judul Buku"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Penerbit"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Pengarang"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Jumlah Buku"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        for (Buku buku : bukuList) {
            cell = new PdfPCell(new Phrase(buku.getNomor_buku()));
            cell.setPaddingLeft(10f);
            table.addCell(cell);
            
            cell = new PdfPCell(new Phrase(buku.getJudul_buku()));
            cell.setPaddingLeft(10f);
            table.addCell(cell);
            
            cell = new PdfPCell(new Phrase(buku.getPenerbit()));
            cell.setPaddingLeft(10f);
            table.addCell(cell);
            
            cell = new PdfPCell(new Phrase(buku.getPengarang()));
            cell.setPaddingLeft(10f);
            table.addCell(cell);
            
            cell = new PdfPCell(new Phrase(buku.getJml_buku()+""));
            cell.setPaddingLeft(10f);
            table.addCell(cell);
        }
        
        cell = new PdfPCell(table);
        layout.addCell(cell);
    }

    public void addPeminjaman(List<Peminjaman> peminjamanList) {
        cell = new PdfPCell(new Phrase("Peminjaman",new Font(Font.FontFamily.UNDEFINED, 12, Font.BOLD)));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setPaddingTop(20f);
        cell.setPaddingBottom(10f);
        layout.addCell(cell);
        
        PdfPTable table = new PdfPTable(7);
        
        cell = new PdfPCell(new Phrase("Nama",new Font(Font.FontFamily.UNDEFINED, 10, Font.NORMAL)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Kelas",new Font(Font.FontFamily.UNDEFINED, 10, Font.NORMAL)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Judul Buku",new Font(Font.FontFamily.UNDEFINED, 10, Font.NORMAL)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Tanggal Peminjaman",new Font(Font.FontFamily.UNDEFINED, 10, Font.NORMAL)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Terakhir Pengembalian",new Font(Font.FontFamily.UNDEFINED, 10, Font.NORMAL)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Tanggal Pengembalian",new Font(Font.FontFamily.UNDEFINED, 10, Font.NORMAL)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Keterlambatan",new Font(Font.FontFamily.UNDEFINED, 10, Font.NORMAL)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        for (Peminjaman peminjaman : peminjamanList) {
            Anggota anggota = Anggota.anggota(peminjaman);
            Buku buku = Buku.buku(peminjaman);
            Pengembalian pengembalian = Pengembalian.pengembalian(peminjaman);
            
            cell = new PdfPCell(new Phrase(anggota.getNama_anggota()));
            cell.setPaddingLeft(10f);
            table.addCell(cell);
            
            cell = new PdfPCell(new Phrase(anggota.getKelas()));
            cell.setPaddingLeft(10f);
            table.addCell(cell);
            
            cell = new PdfPCell(new Phrase(buku.getJudul_buku()));
            cell.setPaddingLeft(10f);
            table.addCell(cell);
            
            cell = new PdfPCell(new Phrase(Format.DateFormat(peminjaman.getTgl_peminjaman())));
            cell.setPaddingLeft(10f);
            table.addCell(cell);
            
            cell = new PdfPCell(new Phrase(Format.DateFormat(peminjaman.getExpired())));
            cell.setPaddingLeft(10f);
            table.addCell(cell);
            
            if (pengembalian != null) {
                cell = new PdfPCell(new Phrase(Format.DateFormat(pengembalian.getTgl_pengembalian())));
                cell.setPaddingLeft(10f);
                table.addCell(cell);
                
                int keterlambatan = peminjaman.keterlambatan(pengembalian);
                
                if (keterlambatan > 0) {
                    cell = new PdfPCell(new Phrase(keterlambatan +" Hari"));
                    cell.setPaddingLeft(10f);
                    table.addCell(cell);
                } else {
                    cell = new PdfPCell(new Phrase("-"));
                    cell.setPaddingLeft(10f);
                    table.addCell(cell);
                }
            } else {;
                cell = new PdfPCell(new Phrase("Belum dikembalikan"));
                cell.setPaddingLeft(10f);
                table.addCell(cell);
                
                cell = new PdfPCell(new Phrase("-"));
                cell.setPaddingLeft(10f);
                table.addCell(cell);
            }
        }
        
        cell = new PdfPCell(table);
        layout.addCell(cell);
    }
    
    public void addKunjungan(List<Kunjungan> kunjunganList) {
        cell = new PdfPCell(new Phrase("Kunjungan",new Font(Font.FontFamily.UNDEFINED, 12, Font.BOLD)));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setPaddingTop(20f);
        cell.setPaddingBottom(10f);
        layout.addCell(cell);
        
        PdfPTable table = new PdfPTable(3);
        
        cell = new PdfPCell(new Phrase("Nama",new Font(Font.FontFamily.UNDEFINED, 10, Font.NORMAL)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Kelas",new Font(Font.FontFamily.UNDEFINED, 10, Font.NORMAL)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
          
        cell = new PdfPCell(new Phrase("Tanggal Kunjungan",new Font(Font.FontFamily.UNDEFINED, 10, Font.NORMAL)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
       
        for (Kunjungan kunjungan : kunjunganList) {
            Anggota anggota = Anggota.anggota(kunjungan);
            
            cell = new PdfPCell(new Phrase(anggota.getNama_anggota()));
            cell.setPaddingLeft(10f);
            table.addCell(cell);
            
            cell = new PdfPCell(new Phrase(anggota.getKelas()));
            cell.setPaddingLeft(10f);
            table.addCell(cell);
            
            cell = new PdfPCell(new Phrase(Format.DateFormat(kunjungan.getTgl_kunjungan())));
            cell.setPaddingLeft(10f);
            table.addCell(cell);
            
        }
        
        cell = new PdfPCell(table);
        layout.addCell(cell);
        
    }
    
    public void addBayarDenda(List<Peminjaman> peminjamanList) {
        cell = new PdfPCell(new Phrase("Bayar Denda",new Font(Font.FontFamily.UNDEFINED, 12, Font.BOLD)));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setPaddingTop(20f);
        cell.setPaddingBottom(10f);
        layout.addCell(cell);
        
        PdfPTable table = new PdfPTable(3);
        
        cell = new PdfPCell(new Phrase("Nama Anggota",new Font(Font.FontFamily.UNDEFINED, 10, Font.NORMAL)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Tipe Denda",new Font(Font.FontFamily.UNDEFINED, 10, Font.NORMAL)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
          
        cell = new PdfPCell(new Phrase("Tarif",new Font(Font.FontFamily.UNDEFINED, 10, Font.NORMAL)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
       
        for (Peminjaman peminjaman : peminjamanList) {
            Anggota anggota = Anggota.anggota(peminjaman);
            BayarDenda bayarDenda = BayarDenda.bayarDenda(peminjaman);
            Denda denda = Denda.denda(bayarDenda.getId_denda());
            
            cell = new PdfPCell(new Phrase(anggota.getNama_anggota()));
            cell.setPaddingLeft(10f);
            table.addCell(cell);
            
            cell = new PdfPCell(new Phrase(denda.getTipe_denda()));
            cell.setPaddingLeft(10f);
            table.addCell(cell);
            
            cell = new PdfPCell(new Phrase(Rupiah.format(bayarDenda.getTotal_bayar())));
            cell.setPaddingLeft(10f);
            table.addCell(cell);
        }
        
        cell = new PdfPCell(table);
        layout.addCell(cell);
        
    }
    
    public PdfPTable getLayout() {
        return layout;
    }
}
