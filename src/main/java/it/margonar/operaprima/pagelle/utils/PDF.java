package it.margonar.operaprima.pagelle.utils;

import it.margonar.operaprima.pagelle.model.Docente;
import it.margonar.operaprima.pagelle.model.Esame;
import it.margonar.operaprima.pagelle.model.PagellaAltro;
import it.margonar.operaprima.pagelle.model.PagellaCoro;
import it.margonar.operaprima.pagelle.model.PagellaFormazione;
import it.margonar.operaprima.pagelle.model.PagellaLaboratorio;
import it.margonar.operaprima.pagelle.model.PagellaOrchestra;
import it.margonar.operaprima.pagelle.model.PagellaStrumento;
import it.margonar.operaprima.pagelle.model.Studente;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Component
public class PDF {
    private BaseColor grigio = BaseColor.LIGHT_GRAY;
    private Font font = new Font(FontFamily.HELVETICA, 10);
    private Font font1 = new Font(FontFamily.HELVETICA, 10, Font.BOLD);
    private Font font2 = new Font(FontFamily.HELVETICA, 10, Font.NORMAL);
    private Font grassetto = new Font(FontFamily.HELVETICA, 12, Font.BOLD);
    private Font small = new Font(FontFamily.HELVETICA, 8);

    private Summary s;
    private Studente stu;
    private Document document;

    private List<String> competenzeStrumento;
    private List<String> competenzeFormazione;

    private int count = 1;
    private String logoPath;
    private String pagellePath;

    public void setS(Summary s) {
        this.s = s;
    }

    private void newPage() throws MalformedURLException, IOException, DocumentException {
        document.newPage();
        logo();
    }

    public String createPDF(Summary summary) throws DocumentException, SQLException,
            MalformedURLException, IOException {

        this.s = summary;
        stu = s.getStudente();

        document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, new FileOutputStream(pagellePath + "/pagella_"
                + s.getStudente().getCognome() + "_" + s.getStudente().getNome() + "_"
                + s.getStudente().getId() + ".pdf"));
        document.open();

        logo();
        tabellaAnagrafica();
        tabellaCorsi();
        tabellaStrumento();

        newPage();

        tabellaFormazione();
        document.add(new Phrase("\n", font));
        tabellaLaboratorio();

        newPage();
        tabellaCoro();
        document.add(new Phrase("\n", font));
        tabellaOrchestra();
        document.add(new Phrase("\n", font));
        tabellaAltro();

        signature();

        if (s.getEsame() != null) {
            newPage();
            tabellaEsame();
        } else {
            newPage();
        }


        document.close();
        return null;

    }

    private void signature() throws DocumentException, com.itextpdf.text.DocumentException {
        PdfPTable tab = new PdfPTable(2);
        PdfPCell cell = new PdfPCell(new Phrase("Ala, giugno 2014"));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorder(0);
        tab.addCell(cell);

        cell = new PdfPCell(new Phrase("IL DIRETTORE"));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        cell.setBorder(0);

        tab.addCell(cell);
        document.add(tab);

    }

    private void tabellaAnagrafica() throws DocumentException {

        // 1 tabella
        float[] colsWidth = { 1f, 2f }; // Code 1
        PdfPTable t1 = new PdfPTable(colsWidth);

        PdfPCell sx = new PdfPCell(new Phrase(40, "Cognome e Nome", grassetto));
        setPropertiesSX(sx);
        t1.addCell(sx);

        PdfPCell dx = new PdfPCell(new Phrase(stu.getCognome() + " " + stu.getNome(), font));
        setPropertiesDX(dx);
        t1.addCell(dx);

        sx.setPhrase(new Phrase(40, "Data di Nascita", grassetto));
        sx.setBorderWidthTop(0);
        t1.addCell(sx);

        Date data = stu.getDataNascita();
        dx.setPhrase(new Phrase(data != null ? formatDate(data) : "", font));
        dx.setBorderWidthTop(0);
        t1.addCell(dx);

        sx.setPhrase(new Phrase(40, "Iscritto dal", grassetto));
        t1.addCell(sx);

        data = stu.getDataIscrizione();
        dx.setPhrase(new Phrase(data != null ? formatDate(data) : "", font));
        t1.addCell(dx);

        document.add(t1);

        document.add(new Phrase(40, "\n", small));
    }

    private void tabellaCorsi() throws DocumentException, SQLException {
        float[] colsWidth = { 1f, 1f }; // Code 1
        PdfPTable t1 = new PdfPTable(colsWidth);
        Phrase fr = new Phrase();

        PdfPCell sx = new PdfPCell(new Phrase(40, "A - CORSO 1: Strumento", grassetto));
        setPropertiesSX(sx);
        t1.addCell(sx);

        if (s.getPs() != null) {
            fr = new Phrase("X", font);
        }
        PdfPCell dx = new PdfPCell(fr);
        setPropertiesDX(dx);
        t1.addCell(dx);

        sx = new PdfPCell(new Phrase(40, "B - CORSO 2: Formazione Musicale", grassetto));
        setPropertiesSX(sx);
        t1.addCell(sx);

        if (s.getPf() != null) {
            fr = new Phrase("X", font);
        } else {
            fr = null;
        }
        dx = new PdfPCell(fr);
        setPropertiesDX(dx);
        t1.addCell(dx);

        sx = new PdfPCell(new Phrase(40, "C - CORSO 3: Laboratorio Musicale", grassetto));
        setPropertiesSX(sx);
        t1.addCell(sx);

        if (s.getPl() != null) {
            fr = new Phrase("X", font);
        } else {
            fr = null;
        }
        dx = new PdfPCell(fr);
        setPropertiesDX(dx);
        t1.addCell(dx);

        sx = new PdfPCell(new Phrase(40, "D - CORSO 4: Educazione Corali", grassetto));
        setPropertiesSX(sx);
        t1.addCell(sx);

        if (s.getPc() != null) {
            fr = new Phrase("X", font);
        } else {
            fr = null;
        }
        dx = new PdfPCell(fr);
        setPropertiesDX(dx);
        t1.addCell(dx);

        sx = new PdfPCell(new Phrase(40, "E - CORSO 5: Laboratorio Orchestrale", grassetto));
        setPropertiesSX(sx);
        t1.addCell(sx);

        if (s.getPo() != null) {
            fr = new Phrase("X", font);
        } else {
            fr = null;
        }
        dx = new PdfPCell(fr);
        setPropertiesDX(dx);
        t1.addCell(dx);

        sx = new PdfPCell(new Phrase(40, "F - CORSO 6: Cultura Musicale / Altro", grassetto));
        setPropertiesSX(sx);
        t1.addCell(sx);

        if (s.getPa() != null) {
            fr = new Phrase("X", font);
        } else {
            fr = null;
        }
        dx = new PdfPCell(fr);
        setPropertiesDX(dx);
        t1.addCell(dx);

        document.add(t1);
        document.add(new Phrase("\n", small));
    }

    private void tabellaStrumento() throws DocumentException, SQLException {
        // 2 tabella: STRUMENTO
        // intestazione
        PdfPCell titolo = new PdfPCell(new Phrase(40, "Cognome e Nome", grassetto));

        PagellaStrumento ps = s.getPs();
        boolean empty = ps == null;

        Docente doc = empty ? null : ps.getDocente();

        setPropertiesSX(titolo);
        titolo.setBorder(0);

        PdfPTable ts = new PdfPTable(1);

        titolo.setPhrase(new Phrase(40, "A - CORSO 1: Strumento "
                + (empty ? "" : ps.getStrumento().getNome()), grassetto));
        ts.addCell(titolo);

        titolo.setPhrase(new Phrase(40, "DOCENTE del Corso: "
                + (!empty ? doc.getCognome() + " " + doc.getNome() : ""), grassetto));
        ts.addCell(titolo);
        document.add(ts);

        ts = new PdfPTable(2);
        titolo.setPhrase(new Phrase(40, "Sede del Corso " + (empty ? "" : ps.getSede()), grassetto));
        ts.addCell(titolo);
        titolo.setPhrase(new Phrase(40, "Anno di studi " + (empty ? "" : ps.getAnno()), grassetto));
        ts.addCell(titolo);
        document.add(ts);

        // testi-repertorio
        float[] colsWidth2 = { 1f, 4f };
        ts = new PdfPTable(colsWidth2);

        PdfPCell name = new PdfPCell(new Phrase(40, "TESTI STUDI e TECNICA", font2));
        setPropertiesName(name);
        ts.addCell(name);

        PdfPCell value = new PdfPCell(new Phrase(empty ? "" : ps.getTesti(), font));
        setPropertiesValue(value);
        ts.addCell(value);

        name.setPhrase(new Phrase(40, "\nREPERTORIO\n\n", font2));
        ts.addCell(name);

        value.setPhrase(new Phrase(empty ? "" : ps.getRepertorio(), font));
        ts.addCell(value);

        document.add(ts);

        // competenze
        float[] colsWidth3 = { 2f, 1f };
        PdfPTable tc = new PdfPTable(colsWidth3);

        float[] colsWidth4 = { 5f, 1f };
        PdfPTable tcomp = new PdfPTable(colsWidth4);
        value.setBorderWidthRight(0f);
        for (int i = 0; i < competenzeStrumento.size(); i++) {
            name.setPhrase(new Phrase(competenzeStrumento.get(i) + " (1-5)", font2));
            tcomp.addCell(name);
            int comp = empty ? 0 : ps.getCompetenza(i);
            value.setPhrase(new Phrase(empty ? "" : comp > 0 ? Integer.toString(comp) : "", font2));
            tcomp.addCell(value);
        }
        PdfPCell inserisci = new PdfPCell();
        inserisci.setPadding(0);
        inserisci.setBorder(0);
        tcomp.setWidthPercentage(100);
        inserisci.addElement(tcomp);
        tc.addCell(inserisci);

        tcomp = new PdfPTable(1);
        value.setSpaceCharRatio(10);
        value.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

        Phrase phrase = new Phrase(40);
        Chunk chunk1 = new Chunk(
                "\n1 - Non raggiunti\n 2 - Parzialmente raggiunti\n 3 - Complessivamente raggiunti\n 4 - Pienamente raggiunti\n 5 - Prefissati superati",
                font1);
        Chunk chunk3 = new Chunk("VALUTAZIONE\nOBIETTIVI\n", font1);
        Chunk chunk2 = new Chunk(
                "\n"
                        + "La valutazione di alcune voci si riferisce anche agli elementi del percorso ABRSM\n",
                small);
        phrase.add(chunk3);
        phrase.add(chunk2);
        phrase.add(chunk1);
        value.setPhrase(phrase);

        value.setBorder(0);
        tcomp.addCell(value);
        tcomp.setWidthPercentage(100);

        inserisci = new PdfPCell();
        inserisci.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        inserisci.setPadding(0);
        inserisci.addElement(tcomp);
        inserisci.setBorderColor(grigio);
        inserisci.setBorderWidth(0.5f);
        inserisci.setBorderWidthRight(1);
        inserisci.setBorderWidthTop(0);

        tc.addCell(inserisci);

        document.add(tc);

        PdfPTable note = new PdfPTable(1);
        value.setPhrase(new Phrase(40, "Complessivo: " + (empty ? "" : ps.getNote()), font2));
        value.setBorderWidthBottom(1f);
        value.setBorderWidthRight(1f);
        value.setBorderWidthLeft(1f);
        note.addCell(value);
        document.add(note);
        document.add(new Phrase("\n\n"));
    }

    private void logo() throws MalformedURLException, IOException, DocumentException {
        Image image = Image.getInstance(logoPath);
        image.scalePercent(20);

        float[] colswidth = { 3f, 1f };
        PdfPTable logo = new PdfPTable(colswidth);
        PdfPCell cella = new PdfPCell(image);
        cella.setBorder(0);
        logo.addCell(cella);

        PdfPCell cella1 = new PdfPCell(new PdfPCell(new Phrase(
                "PORTFOLIO\nALLIEVO\nAnno Scolastico 2013/2014", grassetto)));
        cella1.setBorder(0);
        logo.addCell(cella1);

        document.add(logo);
        document.add(new Phrase("\n"));

    }

    private void tabellaFormazione() throws DocumentException, SQLException {
        // 2 tabella: Formazione
        // intestazione
        PagellaFormazione pf = s.getPf();
        boolean empty = pf == null;
        Docente doc = empty ? null : pf.getDocente();

        PdfPCell titolo = new PdfPCell(new Phrase(40, "Cognome e Nome", grassetto));
        setPropertiesSX(titolo);
        titolo.setBorder(0);

        PdfPTable ts = new PdfPTable(1);
        titolo.setPhrase(new Phrase(40, "B - CORSO 2: Formazione Musicale", grassetto));
        ts.addCell(titolo);

        titolo.setPhrase(new Phrase(40, "DOCENTE del Corso: "
                + (empty ? "" : doc.getCognome() + " " + doc.getNome()), grassetto));
        ts.addCell(titolo);
        document.add(ts);

        ts = new PdfPTable(2);
        titolo.setPhrase(new Phrase(40, "Sede del Corso " + (empty ? "" : pf.getSede()), grassetto));
        ts.addCell(titolo);

        titolo.setPhrase(new Phrase(40, "Corso B1", grassetto));
        ts.addCell(titolo);
        document.add(ts);

        // testi-repertorio
        float[] colsWidth2 = { 1f, 4f };
        ts = new PdfPTable(colsWidth2);

        PdfPCell name = new PdfPCell(new Phrase(40, "TESTI PROGRAMMA SVOLTO", font2));
        setPropertiesName(name);
        ts.addCell(name);

        PdfPCell value = new PdfPCell(new Phrase(empty ? "" : pf.getTesti(), font));
        setPropertiesValue(value);
        ts.addCell(value);

        document.add(ts);

        // competenze
        float[] colsWidth3 = { 2f, 1f };
        PdfPTable tc = new PdfPTable(colsWidth3);

        float[] colsWidth4 = { 5f, 1f };
        PdfPTable tcomp = new PdfPTable(colsWidth4);
        value.setBorderWidthRight(0f);
        for (int i = 0; i < competenzeFormazione.size(); i++) {
            name.setPhrase(new Phrase(competenzeFormazione.get(i) + "(1-5)", font2));
            tcomp.addCell(name);
            int comp = empty ? 0 : pf.getCompetenza(i);
            value.setPhrase(new Phrase(empty ? "" : comp > 0 ? Integer.toString(comp) : "", font2));
            tcomp.addCell(value);
        }
        PdfPCell inserisci = new PdfPCell();
        inserisci.setPadding(0);
        inserisci.setBorder(0);
        tcomp.setWidthPercentage(100);
        inserisci.addElement(tcomp);
        tc.addCell(inserisci);

        tcomp = new PdfPTable(1);
        value.setSpaceCharRatio(10);
        value.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

        Phrase phrase = new Phrase(40);
        Chunk chunk1 = new Chunk(
                "\n1 - Non raggiunti\n 2 - Parzialmente raggiunti\n 3 - Complessivamente raggiunti\n 4 - Pienamente raggiunti\n 5 - Prefissati superati",
                font1);
        Chunk chunk3 = new Chunk("VALUTAZIONE\nOBIETTIVI\n", font1);
        Chunk chunk2 = new Chunk(
                "\n"
                        + "La valutazione di alcune voci si riferisce anche agli elementi del percorso ABRSM\n",
                small);
        phrase.add(chunk3);
        phrase.add(chunk2);
        phrase.add(chunk1);
        value.setPhrase(phrase);

        value.setBorder(0);
        tcomp.addCell(value);
        tcomp.setWidthPercentage(100);

        inserisci = new PdfPCell();
        inserisci.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        inserisci.setPadding(0);
        inserisci.addElement(tcomp);
        inserisci.setBorderColor(grigio);
        inserisci.setBorderWidth(0.5f);
        inserisci.setBorderWidthRight(1);
        inserisci.setBorderWidthTop(0);

        tc.addCell(inserisci);

        document.add(tc);

        PdfPTable note = new PdfPTable(1);
        value.setPhrase(new Phrase(40, "Complessivo: " + (empty ? "" : pf.getNote()), font2));
        value.setBorderWidthBottom(1f);
        value.setBorderWidthRight(1f);
        value.setBorderWidthLeft(1f);
        note.addCell(value);
        document.add(note);
        document.add(new Phrase("\n\n"));
    }

    private void tabellaLaboratorio() throws DocumentException, SQLException {
        // 2 tabella: STRUMENTO
        // intestazione
        PagellaLaboratorio pl = s.getPl();
        boolean empty = pl == null;
        Docente doc = empty ? null : pl.getDocente();

        PdfPCell titolo = new PdfPCell(new Phrase(40, "Cognome e Nome", grassetto));
        setPropertiesSX(titolo);
        titolo.setBorder(0);

        PdfPTable ts = new PdfPTable(1);
        titolo.setPhrase(new Phrase(40, "C - CORSO 3: Laboratorio Musicale"
                + (empty ? "" : "( " + pl.getLaboratorio().getNome() + " )"), grassetto));
        ts.addCell(titolo);

        titolo.setPhrase(new Phrase(40, "DOCENTE del Corso: "
                + (empty ? "" : doc.getCognome() + " " + doc.getNome()), grassetto));
        ts.addCell(titolo);

        document.add(ts);

        ts = new PdfPTable(2);
        titolo.setPhrase(new Phrase(40, "Sede del Corso " + (empty ? "" : pl.getSede()), grassetto));
        ts.addCell(titolo);

        titolo.setPhrase(new Phrase(40, "Anno di Laboratorio " + (empty ? "" : pl.getAnno()),
                grassetto));
        ts.addCell(titolo);
        document.add(ts);

        // testi-repertorio
        float[] colsWidth2 = { 1f, 4f };
        ts = new PdfPTable(colsWidth2);

        PdfPCell name = new PdfPCell(new Phrase(40, "REPERTORIO e PERCORSO MUSICALE", font2));
        setPropertiesName(name);
        ts.addCell(name);

        PdfPCell value = new PdfPCell(new Phrase(empty ? "" : pl.getRepertorio(), font));
        setPropertiesValue(value);
        ts.addCell(value);

        document.add(ts);

        PdfPTable note = new PdfPTable(1);
        value.setPhrase(new Phrase(40, "Complessivo: " + (empty ? "" : pl.getNote()), font2));
        value.setBorderWidthBottom(1f);
        value.setBorderWidthRight(1f);
        value.setBorderWidthLeft(1f);
        note.addCell(value);
        document.add(note);
        document.add(new Phrase("\n\n"));
    }

    private void tabellaOrchestra() throws DocumentException, SQLException {
        // 2 tabella: STRUMENTO
        // intestazione
        PagellaOrchestra po = s.getPo();
        boolean empty = po == null;
        Docente doc = empty ? null : po.getDocente();

        PdfPCell titolo = new PdfPCell(new Phrase(40, "Cognome e Nome", grassetto));
        setPropertiesSX(titolo);
        titolo.setBorder(0);

        PdfPTable ts = new PdfPTable(1);
        titolo.setPhrase(new Phrase(40, "E - CORSO 5: Laboratorio Orchestrale "
                + (empty ? "" : po.getOrchestra().getNome()), grassetto));
        ts.addCell(titolo);

        titolo.setPhrase(new Phrase(40, "DOCENTE del Corso: "
                + (empty ? "" : doc.getCognome() + " " + doc.getNome()), grassetto));
        ts.addCell(titolo);
        document.add(ts);

        ts = new PdfPTable(2);
        titolo.setPhrase(new Phrase(40, "Sede del corso " + (empty ? "" : po.getSede()), grassetto));
        ts.addCell(titolo);
        titolo.setPhrase(new Phrase(40, "Anno di corso " + (empty ? "" : po.getAnno()), grassetto));
        ts.addCell(titolo);
        document.add(ts);

        // testi-repertorio
        float[] colsWidth2 = { 1f, 4f };
        ts = new PdfPTable(colsWidth2);

        PdfPCell name = new PdfPCell(new Phrase(40, "REPERTORIO PERCORSO MUSICALE", font2));
        setPropertiesName(name);
        ts.addCell(name);

        PdfPCell value = new PdfPCell(new Phrase(empty ? "" : po.getRepertorio(), font));
        setPropertiesValue(value);
        ts.addCell(value);

        document.add(ts);

        PdfPTable note = new PdfPTable(1);
        value.setPhrase(new Phrase(40, "Complessivo: " + (empty ? "" : po.getNote()), font2));
        value.setBorderWidthBottom(1f);
        value.setBorderWidthRight(1f);
        value.setBorderWidthLeft(1f);
        note.addCell(value);
        document.add(note);
        document.add(new Phrase("\n\n"));
    }

    private void tabellaCoro() throws DocumentException, SQLException {
        // 2 tabella: STRUMENTO
        // intestazione
        PagellaCoro pc = s.getPc();
        boolean empty = pc == null;
        Docente doc = empty ? null : pc.getDocente();

        PdfPCell titolo = new PdfPCell(new Phrase(40, "Cognome e Nome", grassetto));
        setPropertiesSX(titolo);
        titolo.setBorder(0);

        PdfPTable ts = new PdfPTable(1);
        titolo.setPhrase(new Phrase(40, "D - CORSO 4: Esercitazioni Corali ", grassetto));
        ts.addCell(titolo);

        titolo.setPhrase(new Phrase(40, "DOCENTE del Corso: "
                + (empty ? "" : doc.getCognome() + " " + doc.getNome()), grassetto));
        ts.addCell(titolo);
        document.add(ts);

        ts = new PdfPTable(2);
        titolo.setPhrase(new Phrase(40, "Sede del corso " + (empty ? "" : pc.getSede()), grassetto));
        ts.addCell(titolo);
        titolo.setPhrase(new Phrase(40, "Anno di corso " + (empty ? "" : pc.getAnno()), grassetto));
        ts.addCell(titolo);
        document.add(ts);

        // testi-repertorio
        float[] colsWidth2 = { 1f, 4f };
        ts = new PdfPTable(colsWidth2);

        PdfPCell name = new PdfPCell(new Phrase(40, "REPERTORIO PERCORSO MUSICALE", font2));
        setPropertiesName(name);
        ts.addCell(name);

        PdfPCell value = new PdfPCell(new Phrase(empty ? "" : pc.getRepertorio(), font));
        setPropertiesValue(value);
        ts.addCell(value);
        document.add(ts);

        PdfPTable note = new PdfPTable(1);
        value.setPhrase(new Phrase(40, "Complessivo" + (empty ? "" : pc.getNote()), font2));
        value.setBorderWidthBottom(1f);
        value.setBorderWidthRight(1f);
        value.setBorderWidthLeft(1f);
        note.addCell(value);
        document.add(note);
        document.add(new Phrase("\n\n"));
    }

    private void tabellaAltro() throws DocumentException, SQLException {
        // 2 tabella: STRUMENTO
        // intestazione
        PagellaAltro pa = s.getPa();
        boolean empty = pa == null;
        Docente doc = empty ? null : pa.getDocente();

        PdfPCell titolo = new PdfPCell(new Phrase(40, "Cognome e Nome", grassetto));
        setPropertiesSX(titolo);
        titolo.setBorder(0);

        PdfPTable ts = new PdfPTable(1);
        titolo.setPhrase(new Phrase(40,
                "F - CORSO 6: Cultura Musicale o altra Esperienza Formativa", grassetto));
        ts.addCell(titolo);

        titolo.setPhrase(new Phrase(40, "DOCENTE del Corso: "
                + (empty ? "" : doc.getCognome() + " " + doc.getNome()), grassetto));
        ts.addCell(titolo);
        document.add(ts);

        ts = new PdfPTable(2);
        titolo.setPhrase(new Phrase(40, "Sede del corso " + (empty ? "" : pa.getSede()), grassetto));
        ts.addCell(titolo);
        titolo.setPhrase(new Phrase(40, "Anno di corso " + (empty ? "" : pa.getAnno()), grassetto));
        ts.addCell(titolo);
        document.add(ts);

        // testi-repertorio
        float[] colsWidth2 = { 1f, 4f };
        ts = new PdfPTable(colsWidth2);

        PdfPCell name = new PdfPCell(new Phrase(40, "PERCORSO MUSICALE", font2));
        setPropertiesName(name);
        ts.addCell(name);

        PdfPCell value = new PdfPCell(new Phrase(empty ? "" : pa.getRepertorio(), font));
        setPropertiesValue(value);
        ts.addCell(value);
        document.add(ts);

        PdfPTable note = new PdfPTable(1);
        value.setPhrase(new Phrase(40, "Complessivo" + (empty ? "" : pa.getNote()), font2));
        value.setBorderWidthBottom(1f);
        value.setBorderWidthRight(1f);
        value.setBorderWidthLeft(1f);
        note.addCell(value);
        document.add(note);
        document.add(new Phrase("\n\n"));
    }

    private void tabellaEsame() throws DocumentException, SQLException {
        // 2 tabella: STRUMENTO
        // intestazione
        Esame e = s.getEsame();

        PdfPTable ts = new PdfPTable(1);

        PdfPCell titolo = new PdfPCell(new Phrase(40, "ESAME", grassetto));
        setPropertiesSX(titolo);
        titolo.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        titolo.setBorder(0);
        ts.addCell(titolo);

        titolo = new PdfPCell(new Phrase(40, "Data " + formatDate(e.getData()), grassetto));
        setPropertiesSX(titolo);
        titolo.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        titolo.setBorder(0);
        ts.addCell(titolo);
        document.add(ts);

        ts = new PdfPTable(2);

        PdfPCell name = new PdfPCell(new Phrase(40, "Membri della Commissione", font2));
        setPropertiesName(name);
        ts.addCell(name);

        name = new PdfPCell(new Phrase(40, "Firme", font2));
        setPropertiesName(name);
        name.setBorderWidthRight(1f);
        ts.addCell(name);
        document.add(ts);

        ts = new PdfPTable(2);
        for (int i = 0; i < 4; i++) {
            name = new PdfPCell(new Phrase(40, "", font2));
            setPropertiesName(name);
            name.setBorderWidthRight(1f);
            ts.addCell(name);
            ts.addCell(name);
        }
        document.add(ts);
        document.add(new Phrase("\n\n"));
    }

    private void setPropertiesSX(PdfPCell sx) {
        sx.setBackgroundColor(grigio);
        sx.setBorder(0);
        sx.setMinimumHeight(20f);
        sx.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
    }

    private void setPropertiesDX(PdfPCell dx) {
        dx.setBorder(0);
        dx.setBorderColor(grigio);
        dx.setBorderWidthRight(1f);
        dx.setBorderWidthBottom(0.5f);
        dx.setBorderWidthTop(0.5f);
        dx.setMinimumHeight(20f);
        dx.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        dx.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
    }

    private void setPropertiesName(PdfPCell name) {
        name.setBackgroundColor(BaseColor.WHITE);
        name.setBorderColor(grigio);
        name.setBorder(0);
        name.setBorderWidthLeft(1f);
        name.setBorderWidthBottom(0.5f);
        name.setMinimumHeight(20f);
        name.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
    }

    private void setPropertiesValue(PdfPCell value) {
        value.setBorderColor(grigio);
        value.setBorder(0);
        value.setBorderWidthRight(1f);
        value.setBorderWidthBottom(0.5f);
        value.setMinimumHeight(20f);
        value.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
    }

    private String formatDate(Date d) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(d);
    }

    @Value("#{\"${strumento.c}\".split(',')}")
    public void setCompetenzeStrumento(List<String> competenze) {
        this.competenzeStrumento = competenze;
    }

    @Value("#{\"${formazione.c}\".split(',')}")
    public void setCompetenzeFormazione(List<String> competenze) {
        this.competenzeFormazione = competenze;
    }

    @Value("${logo}")
    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    @Value("${pagelle.dir}")
    public void setPagellePath(String pagellePath) {
        this.pagellePath = pagellePath;
    }

}
