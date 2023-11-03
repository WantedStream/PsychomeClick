package com.projectclasses;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;
import org.fit.pdfdom.PDFDomTree;
import org.fit.pdfdom.PDFToHTML;
import org.w3c.dom.Document;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class PsychometricReader {


    public static void main(String[] args) throws IOException {
        URL resource = PsychometricReader.class.getClassLoader().getResource("tests/psychometric_feb_2014_acc.pdf");
        String outputFilePath = "tests/file.html";

        File f = new File(resource.getFile());
        PDDocument dc = PDDocument.load(f);

        PDFTextStripper textStripper = new PDFTextStripper();

        try {
            PDDocument document = PDDocument.load(f);
            PDFDomTree parser = new PDFDomTree();
            PrintWriter writer = new PrintWriter(new FileWriter(outputFilePath));
            parser.writeText(document, writer);
            writer.close();
            document.close();
            System.out.println("PDF converted to HTML successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
