/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.xml.xq;

import java.util.List;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

/**
 *
 * @author lendle
 */
public interface Navigator {
    public Node getNextNode(Node contextNode);
    public Node getPrevNode(Node contextNode);
    public Node getNextNode(Node contextNode, short nodeType);
    public Node getPrevNode(Node contextNode, short nodeType);
    public Element getParentNode(Node contextNode);
    public List<Node> getChildNodes(Node contextNode);
    public Node getFirstChildNode(Node contextNode);
    public List<org.w3c.dom.Attr> getAttributes(Node contextNode);
    public Attr getAttribute(Node contextNode, String name);

    public Element getOwnerElement(Node contextNode);
    public Element getOwnerOrParentElement(Node contextNode);

    public Text getNextTextNode(Node contextNode);
    public Element getNextElementNode(Node contextNode);
    public Text getPrevTextNode(Node contextNode);
    public Element getPrevElementNode(Node contextNode);

    public Element getFirstChildElement(Node contextNode);
    public Text getFirstChildText(Node contextNode);
}
