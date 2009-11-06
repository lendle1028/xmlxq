/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.xml.xq;

import javax.xml.namespace.QName;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author lendle
 */
public interface Manipulator {
    /**
     * return the value part of the specified contextNode
     * textContent of Element
     * nodeValue of Attr and Text
     * @param contextNode
     * @return
     */
    public String getValue(Node contextNode);
    /**
     * set the value part of the specified contextNode
     * textContent of Element
     * nodeValue of Attr and Text
     * @param contextNode
     * @param value
     * @return
     */
    public void setValue(Node contextNode, String value);

    public String getAttributeValue(Element element, String attribute);
    public void setAttributeValue(Element element, String attribute, String value);
    public String getAttributeValue(Element element, QName attrQName);
    public void setAttributeValue(Element element, QName attrQName, String value);

    public String toString(Node contextNode);
}
