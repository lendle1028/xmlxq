/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.xml.xq;

import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author lendle
 */
public interface Finder {
    /**
     * include both element/attribute; ignore
     * namespace uri
     * @param contextNode
     * @param tagName
     * @return
     */
    public List<Node> findByNodeName(Node contextNode, String tagName);
    /**
     * include both element/attribute
     * @param contextNode
     * @param tagName
     * @return
     */
    public List<Node> findByNodeName(Node contextNode, QName qName);

    public List<Element> findElementsByNodeName(Node contextNode, String tagName);
    public List<Element> findElementsByNodeName(Node contextNode, QName qName);

    public List<Attr> findAttributesByNodeName(Node contextNode, String tagName);
    public List<Attr> findAttributesByNodeName(Node contextNode, QName qName);
    /**
     * test by attr value
     * @param contextNode
     * @param attributeName
     * @param attributeValue
     * @return
     */
    public List<Element> findElementsByAttributeValue(Node contextNode, String attributeName, String attributeValue);
    public List<Element> findElementsByAttributeValue(Node contextNode, QName attributeName, String attributeValue);
    /**
     * test whether the attr exists, ignore the value
     * @param contextNode
     * @param attributeName
     * @return
     */
    public List<Element> findElementsByAttribute(Node contextNode, String attributeName);
    public List<Element> findElementsByAttribute(Node contextNode, QName attributeName);

    public List<Element> findElementsByTextValue(Node contextNode, String value);

    public Node evaluateAsNode(Node contextNode, String xpath) throws XPathExpressionException;
    public List<Node> evaluateAsNodeList(Node contextNode, String xpath) throws XPathExpressionException;
    public String evaluateAsValue(Node contextNode, String xpath) throws XPathExpressionException;
}
