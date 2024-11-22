package org.example;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Parser implements Runnable {

    private final File bz2;

    public Parser(File bz2) {
        this.bz2 = bz2;
    }

    public void run() {
        try (BZip2CompressorInputStream in = new BZip2CompressorInputStream(new FileInputStream(bz2))) {
            var factory = SAXParserFactory.newInstance();
            var parser = factory.newSAXParser();
            parser.parse(in, new WikiHandler());
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
