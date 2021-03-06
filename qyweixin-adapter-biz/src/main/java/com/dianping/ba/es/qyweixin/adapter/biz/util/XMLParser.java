package com.dianping.ba.es.qyweixin.adapter.biz.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * Created by justin on 14-7-24.
 */
public class XMLParser {
    private static String MESSAGE_TYPE = "MsgType";
    private static String EVENT = "Event";
    private static String ENCRYPT_TYPE = "Encrypt";

    public static Document parseDocument(InputStream is) {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(is);
            return doc;
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return null;
    }

    public static String getMsgType(Document document) {
        if (document == null) {
            return null;
        }
        Element element = document.getDocumentElement();
        NodeList list = element.getElementsByTagName(MESSAGE_TYPE);
        String msgType = list.item(0).getTextContent();
        return msgType;
    }

    public static String getEvent(Document document) {
        if (document == null) {
            return null;
        }
        Element element = document.getDocumentElement();
        NodeList list = element.getElementsByTagName(EVENT);
        String event = list.item(0).getTextContent();
        return event;
    }

    public static String getEncryptedMsg(Document document) {
        if (document == null) {
            return null;
        }
        Element element = document.getDocumentElement();
        NodeList list = element.getElementsByTagName(ENCRYPT_TYPE);
        String msgType = list.item(0).getTextContent();
        return msgType;
    }

    public static Document convertStringToDocument(String xmlStr) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xmlStr)));
            return doc;
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return null;
    }

    public static String convertDocumentToString(Document doc) {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = tf.newTransformer();
            // below code to remove XML declaration
            // transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            String output = writer.getBuffer().toString();
            return output;
        } catch (TransformerException e) {
//            e.printStackTrace();
        }

        return null;
    }


}
