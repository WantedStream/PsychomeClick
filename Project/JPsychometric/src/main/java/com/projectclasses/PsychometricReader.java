package com.projectclasses;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Scanner;

public class PsychometricReader {


    public static void main(String[] args) throws IOException {
         URL resource = PsychometricReader.class.getClassLoader().getResource("tests/psychometric_feb_2014_acc.pdf");
        File f = new File(resource.getFile());
       PDDocument dc= PDDocument.load(f);

       int noOfPages= dc.getNumberOfPages();
        PDFTextStripper textStripper = new PDFTextStripper();

        String pdfText = textStripper.getText(dc);
        System.out.println(pdfText);
        PDPage doc = dc.getPage(0);

        int index = pdfText.indexOf("אנלוגיות");
        if (index != -1) {
            System.out.println("Word found at index: " + index);
        } else {
            System.out.println("Word not found in the PDF.");
        }
        dc.close();

        System.out.println(index+"-<");
    }
    public static String convertToUTF8(String s) {
        String out = null;
        try {
            out = new String(s.getBytes("UTF-8"), "ISO-8859-1");
        } catch (java.io.UnsupportedEncodingException e) {
            return null;
        }
        return out;
    }
}
