/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.xml.xq;

import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 *
 * @author lendle
 */
public class DefaultNavigatorImpl implements Navigator{
    public Node getNextNode(Node contextNode) {
        if(contextNode==null){
            return null;
        }
        return contextNode.getNextSibling();
    }

    public Node getPrevNode(Node contextNode) {
        if(contextNode==null){
            return null;
        }
        return contextNode.getPreviousSibling();
    }

    public Node getNextNode(Node contextNode, short nodeType) {
        Node node=this.getNextNode(contextNode);
        while(node!=null && node.getNodeType()!=nodeType){
            node=node.getNextSibling();
        }
        return node;
    }

    public Node getPrevNode(Node contextNode, short nodeType) {
        Node node=this.getPrevNode(contextNode);
        while(node!=null && node.getNodeType()!=nodeType){
            node=node.getPreviousSibling();
        }
        return node;
    }

    public Element getParentNode(Node contextNode) {
        if(contextNode==null){
            return null;
        }
        return (Element) contextNode.getParentNode();
    }

    public List<Node> getChildNodes(Node contextNode) {
        if(contextNode==null){
            return null;
        }
        return CommonUtil.nodeList2List(contextNode.getChildNodes());
    }

    public Node getFirstChildNode(Node contextNode) {
        if(contextNode==null){
            return null;
        }
        return contextNode.getFirstChild();
    }

    public List<Attr> getAttributes(Node contextNode) {
        if(contextNode==null){
            return null;
        }
        if(contextNode.getNodeType()!=Node.ELEMENT_NODE){
            return new ArrayList<Attr>();
        }
        NamedNodeMap map=contextNode.getAttributes();
        List<Attr> attrs=new ArrayList<Attr>();
        for(int i=0; map!=null && i<map.getLength(); i++){
            attrs.add((Attr)map.item(i));
        }
        return attrs;
    }

    public Text getNextTextNode(Node contextNode) {
        return (Text) this.getNextNode(contextNode, Node.TEXT_NODE);
    }

    public Element getNextElementNode(Node contextNode) {
        return (Element) this.getNextNode(contextNode, Node.ELEMENT_NODE);
    }

    public Text getPrevTextNode(Node contextNode) {
        return (Text) this.getPrevNode(contextNode, Node.TEXT_NODE);
    }

    public Element getPrevElementNode(Node contextNode) {
        return (Element) this.getPrevNode(contextNode, Node.ELEMENT_NODE);
    }

    public Element getFirstChildElement(Node contextNode) {
        if(contextNode==null){
            return null;
        }
        Node node=contextNode.getFirstChild();
        while(node!=null && node.getNodeType()!=Node.ELEMENT_NODE){
            node=node.getNextSibling();
        }
        return (Element) node;
    }

    public Text getFirstChildText(Node contextNode) {
        if(contextNode==null){
            return null;
        }
        Node node=contextNode.getFirstChild();
        while(node!=null && node.getNodeType()!=Node.TEXT_NODE){
            node=node.getNextSibling();
        }
        return (Text) node;
    }


    public Element getOwnerElement(Node contextNode) {
        if(contextNode==null || contextNode.getNodeType()!=Node.ATTRIBUTE_NODE){
            return null;
        }
        return ((Attr)contextNode).getOwnerElement();
    }

    public Element getOwnerOrParentElement(Node contextNode) {
        if(contextNode==null){
            return null;
        }
        if(contextNode.getNodeType()==Node.ELEMENT_NODE){
            return this.getParentNode(contextNode);
        }
        else{
            return this.getOwnerElement(contextNode);
        }
    }

    public Attr getAttribute(Node contextNode, String name) {
        if(contextNode==null){
            return null;
        }
        if(contextNode.getNodeType()!=Node.ELEMENT_NODE){
            return null;
        }
        return ((Element)contextNode).getAttributeNode(name);
    }

}
