/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.xml.xq;

import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

/**
 *
 * @author lendle
 */
public class DefaultManipulatorImpl implements Manipulator{

    public String getValue(Node contextNode) {
        if(contextNode==null){
            return null;
        }
        if(contextNode.getNodeType()==Node.ELEMENT_NODE){
            return ((Element)contextNode).getTextContent();
        }
        else if(contextNode.getNodeType()==Node.ATTRIBUTE_NODE){
            return ((Attr)contextNode).getNodeValue();
        }
        else if(contextNode.getNodeType()==Node.ELEMENT_NODE){
            return ((Text)contextNode).getNodeValue();
        }
        else return null;
    }

    public void setValue(Node contextNode, String value) {
        if(contextNode==null){
            return;
        }
        if(contextNode.getNodeType()==Node.ELEMENT_NODE){
            ((Element)contextNode).setTextContent(value);
        }
        else if(contextNode.getNodeType()==Node.ATTRIBUTE_NODE){
            ((Attr)contextNode).setNodeValue(value);
        }
        else if(contextNode.getNodeType()==Node.ELEMENT_NODE){
            ((Text)contextNode).setNodeValue(value);
        }
        else return;
    }

    public String getAttributeValue(Element element, String attribute) {
        return element.getAttribute(attribute);
    }

    public void setAttributeValue(Element element, String attribute, String value) {
        element.setAttribute(attribute, value);
    }

    public String getAttributeValue(Element element, QName attrQName) {
        return element.getAttributeNS(attrQName.getNamespaceURI(), attrQName.getLocalPart());
    }

    public void setAttributeValue(Element element, QName attrQName, String value) {
        element.setAttributeNS(attrQName.getNamespaceURI(), attrQName.getLocalPart(), value);
    }

    public String toString(Node contextNode) {
        if(contextNode==null){
            return null;
        }
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();
            t.setOutputProperty("omit-xml-declaration", "yes");
            StringWriter output = new StringWriter();
            t.transform(new DOMSource(contextNode), new StreamResult(output));
            return output.toString();
        } catch (TransformerException ex) {
            Logger.getLogger(DefaultManipulatorImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
