package com.projectclasses;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.fit.pdfdom.PDFDomTree;

import java.io.*;
import java.net.URL;

public class PDFToHTMLConverter {
    public static void main(String[] args) throws IOException {
        URL resource = PsychometricReader.class.getClassLoader().getResource("tests/psychometric_feb_2014_acc.pdf");
        String outputFilePath = "C:\\Users\\USER\\Documents\\GitHub\\Psychomeclick\\Project\\JPsychometric\\src\\main\\resources\\tests\\file.html";

        File f = new File(resource.getFile());

        PDFTextStripper textStripper = new PDFTextStripper();

        try {
            PDDocument document = PDDocument.load(f);
            PDFDomTree parser = new PDFDomTree(); ////LF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
            //  SLF4J: Defaulting to no-operation (NOP) logger implementation
            PrintWriter writer = new PrintWriter(new FileWriter(outputFilePath));
            parser.writeText(document, writer);
            writer.close();
            document.close();
            System.out.println("PDF converted to HTML successfully.");
        } catch (IOException e) {
            System.out.println("PDF WAS NOT converted to HTML successfully.");

            e.printStackTrace();
        }
    }
}
