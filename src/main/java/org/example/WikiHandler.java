package org.example;

import org.apache.commons.lang3.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.nio.charset.StandardCharsets;

public class WikiHandler extends DefaultHandler {

    private String path = "";

    private StringBuilder buffer;

    private String title;

    private int id;

    private String text;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        path += ("/" + qName);
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        if (buffer == null) {
            buffer = new StringBuilder();
        }
        buffer.append(new String(ch, start, length));
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        var value = StringUtils.trimToNull(buffer.toString());

        if ("/mediawiki/page/id".equals(path)) {
            id = Integer.parseInt(value);
        } else if ("/mediawiki/page/title".equals(path)) {
            title = value;
        } else if ("/mediawiki/page/revision/text".equals(path)) {
            text = value;
        } else if ("/mediawiki/page".equals(path)) {
            System.out.println("INSERT INTO article(id,title,text) VALUES(" + id + ",X'" + hex(title) + "',X'" + hex(text) + "');");
        }

        int pos = path.lastIndexOf("/");
        path = path.substring(0, pos);

        buffer = null;
    }

    private String hex(String text) {
        var bytes = text.getBytes(StandardCharsets.UTF_8);
        StringBuilder hex = new StringBuilder();
        for (byte b : bytes) {
            hex.append(String.format("%02x", b));
        }
        return hex.toString();
    }
}
