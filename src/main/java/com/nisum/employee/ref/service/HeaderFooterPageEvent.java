package com.nisum.employee.ref.service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class HeaderFooterPageEvent extends PdfPageEventHelper {

    public void onStartPage(PdfWriter writer, Document document) {
       // ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("Top Left"), 30, 800, 0);
       // ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("Top Right"), 550, 800, 0);
    }

    public void onEndPage(PdfWriter writer, Document document) {
    	Font footerFont = new Font(Font.FontFamily.TIMES_ROMAN, 8,Font.ITALIC | Font.UNDERLINE, BaseColor.GRAY);
        footerFont.setStyle(Font.ITALIC);
    	ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("______________________________________________________________________________________________________________________________",footerFont), ((document.left() + document.right())/2)+1f ,40, 0);
    	//ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, new Phrase(String.format("Page %d", writer.getPageNumber()),footerFont), (document.left() + document.right())/2 , document.bottom()-20, 0);
    	
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("NISUM CONSULTING PRIVATE LIMITED",footerFont), 270, 30, 0);
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("PLOT NO: 107,LUMBINI ENCLAVE,EURO SCHOOL ROAD,GACHIBOWLI,HYDERABAD - 500032.",footerFont), 270, 20, 0);
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("CIN: U74900TG2016FTC103157, CELL : +91 9989036666 INFO@NISUM.COM, WWW.NISUM.COM",footerFont), 270, 10, 0);
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("" + document.getPageNumber(),footerFont), 550, 30, 0);
    }

}