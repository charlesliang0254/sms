package cn.edu.chd.sms.util;

import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class XMLParser {
    public static void generateXMLFile(String filename,Map<String,Object> map) throws IOException {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("analysis");
        createNode(root,map);
        try(FileWriter writer = new FileWriter(filename)){
            XMLWriter xmlWriter = new XMLWriter(writer);
            xmlWriter.write(document);
            xmlWriter.close();
        }
    }

    public static Map<String,Object> parseXMLFile(String filename) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(filename);
        Map<String,Object> map = getMapFromElement(document.getRootElement());
        return map;
    }
    private static void createNode(Element parent,Map<String,Object> map){
        for(Map.Entry<String,Object> entry:map.entrySet()){
            Element elem = parent.addElement(entry.getKey());
            if(entry.getValue() instanceof Map){
                createNode(elem, (Map<String, Object>) entry.getValue());
            }
            else{
                elem.addText(entry.getValue().toString());
            }
        }
    }

    private static Map<String,Object> getMapFromElement(Element parent){
        Map<String,Object> map=new HashMap<>();
        for(int i=0,size=parent.nodeCount();i<size;i++){
            Node node = parent.node(i);
            if(node instanceof Element){
                Map<String, Object> child = getMapFromElement((Element) node);
                if(child==null||child.isEmpty()){
                    map.put(node.getName(), node.getText());
                }
                else{
                    map.put(node.getName(), child);
                }
            }
        }
        return map;
    }
}
