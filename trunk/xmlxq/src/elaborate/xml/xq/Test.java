/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.xml.xq;

import java.io.File;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

/**
 *
 * @author lendle
 */
public class Test {
    public static void main(String [] args) throws Exception{
        final DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        final DocumentBuilder db=dbf.newDocumentBuilder();
        final Document doc=db.parse(new File("Group.xsd"));

        final XQ xNode=new XQ(doc.getDocumentElement());
        System.out.println(xNode.getFirstChildElement().getQName());
        System.out.println(xNode.getFirstChildElement().getNextNode().getQName());
        System.out.println(xNode.getFirstChildElement().getNextElementNode().getQName());
        System.out.println(xNode.getFirstChildElement().getAttributeNode("namespace").getQName());

        System.out.println(xNode.getFirstChildElement().getAttributeNode("namespace").getValue());
        System.out.println(xNode.getFirstChildElement().getNextElementNode().getValue());
        System.out.println(xNode);
        System.out.println(xNode.getFirstChildElement().getNextElementNode());

        //List<XQ> allElementNodes=xNode.evaluateAsNodeList("//*[local-name()='element']");
        /*List<XQ> allAttrNodes=xNode.evaluateAsNodeList("//@*");
        for(XQ attr : allAttrNodes){
            System.out.println(attr.getQName()+"="+attr.getValue());
        }
        System.out.println("========================================================");
        XQ groupTypeNode=xNode.findElementsByAttributeValue("name", "Group").get(0);
        System.out.println(groupTypeNode.findElementsByAttributeValue("name", "Group"));
        System.out.println("========================================================");*/
        System.out.println(xNode.findElementsByTextValue("456").get(0));
        System.out.println(xNode.findElementsByAttributeValue("minOccurs", "2"));

        System.out.println(xNode.findElementsByAttributeValue("name", "Group").get(0).findElementsByTextValue("456").get(0));

        final XQ xq2=new XQ("<xml><a><a1>a1</a1></a><b><b1>b1</b1></b></xml>");
        System.out.println(xq2);
        System.out.println(xq2.findElementsByNodeName("a1").get(0).getValue());

        System.out.println(xNode.findElementsByAttributeValue("name", "Group").get(0).findElementsByAttributeValue("name", "union"));
    }
}
