/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.xml.xq;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author lendle
 */
public class DefaultFinderImpl implements Finder{

    public List<Node> findByNodeName(Node contextNode, String tagName) {
        final List<Element> elements=this.findElementsByNodeName(contextNode, tagName);
        final List<Attr> attrs=this.findAttributesByNodeName(contextNode, tagName);
        final List<Node> ret=new ArrayList<Node>(elements.size()+attrs.size());
        ret.addAll(elements);
        ret.addAll(attrs);
        return ret;
    }

    public List<Node> findByNodeName(Node contextNode, QName qName) {
        final List<Element> elements=this.findElementsByNodeName(contextNode, qName);
        final List<Attr> attrs=this.findAttributesByNodeName(contextNode, qName);
        final List<Node> ret=new ArrayList<Node>(elements.size()+attrs.size());
        ret.addAll(elements);
        ret.addAll(attrs);
        return ret;
    }

    public List<Element> findElementsByNodeName(Node contextNode, String tagName) {
        try {
            return this.convert2ElementList(
                    this.evaluateAsNodeList(contextNode, "descendant-or-self::*[local-name()='"+tagName+"']"));
        } catch (XPathExpressionException ex) {
            return new ArrayList<Element>();
        }
    }

    public List<Element> findElementsByNodeName(Node contextNode, QName qName) {
        try {
            if (qName.getNamespaceURI() == null) {
                //for null namespace-uri, omit the namespace-uri part
                return this.findElementsByNodeName(contextNode, qName.getLocalPart());
            }
            return this.convert2ElementList(
                    this.evaluateAsNodeList(contextNode, "descendant-or-self::*[namespace-uri()='"+qName.getNamespaceURI()+"' and local-name()='"+qName.getLocalPart()+"']"));
        } catch (XPathExpressionException ex) {
            return new ArrayList<Element>();
        }
    }

    public List<Attr> findAttributesByNodeName(Node contextNode, String tagName) {
        try {

            return this.convert2AttributeList(
                    this.evaluateAsNodeList(contextNode, "descendant-or-self::@*[local-name()='"+tagName+"']"));
        } catch (XPathExpressionException ex) {
            return new ArrayList<Attr>();
        }
    }

    public List<Attr> findAttributesByNodeName(Node contextNode, QName qName) {
        try {
            if (qName.getNamespaceURI() == null) {
                //for null namespace-uri, omit the namespace-uri part
                return this.findAttributesByNodeName(contextNode, qName.getLocalPart());
            }
            return this.convert2AttributeList(
                    this.evaluateAsNodeList(contextNode, "descendant-or-self::@*[namespace-uri()='"+qName.getNamespaceURI()+"' and local-name()='"+qName.getLocalPart()+"']"));
        } catch (XPathExpressionException ex) {
            return new ArrayList<Attr>();
        }
    }

    public List<Element> findElementsByAttributeValue(Node contextNode, String attributeName, String attributeValue) {
        try {
            return this.convert2ElementList(
                    this.evaluateAsNodeList(contextNode, "descendant-or-self::*[@" + attributeName + "='"+attributeValue+"']")
                    /*this.evaluateAsNodeList(contextNode, "*//*[@*[local-name()='" + attributeName + "']='"+attributeValue+"']")*/);
        } catch (XPathExpressionException ex) {
            return new ArrayList<Element>();
        }
    }

    public List<Element> findElementsByAttributeValue(Node contextNode, QName attributeName, String attributeValue) {
        try {
            if (attributeName.getNamespaceURI() == null) {
                //for null namespace-uri, omit the namespace-uri part
                return this.findElementsByAttributeValue(contextNode, attributeName.getLocalPart(), attributeValue);
            }
            return this.convert2ElementList(this.evaluateAsNodeList(contextNode, "descendant-or-self::*[@*[namespace-uri()='" +
                    attributeName.getNamespaceURI() + "' and local-name()='" + attributeName.getLocalPart() + "']='"+attributeValue+"']"));
        } catch (XPathExpressionException ex) {
            return new ArrayList<Element>();
        }
    }

    public List<Element> findElementsByAttribute(Node contextNode, String attributeName) {
        try {
            return this.convert2ElementList(this.evaluateAsNodeList(contextNode, "descendant-or-self::*[@*[local-name()='" + attributeName + "']]"));
        } catch (XPathExpressionException ex) {
            return new ArrayList<Element>();
        }
    }

    public List<Element> findElementsByAttribute(Node contextNode, QName attributeName) {
        try {
            if (attributeName.getNamespaceURI() == null) {
                //for null namespace-uri, omit the namespace-uri part
                return this.findElementsByAttribute(contextNode, attributeName.getLocalPart());
            }
            return this.convert2ElementList(this.evaluateAsNodeList(contextNode, "descendant-or-self::*[@*[namespace-uri()='" + attributeName.getNamespaceURI() + "' and local-name()='" + attributeName.getLocalPart() + "']]"));
        } catch (XPathExpressionException ex) {
            return new ArrayList<Element>();
        }
    }

    public List<Element> findElementsByTextValue(Node contextNode, String value){
        try {
            return this.convert2ElementList(this.evaluateAsNodeList(contextNode, "descendant-or-self::*[text()='"+value+"']"));
        } catch (XPathExpressionException ex) {
            return new ArrayList<Element>();
        }
    }

    public Node evaluateAsNode(Node contextNode, String xpath) throws XPathExpressionException{
        return (Node) this.evaluateXPath(contextNode, xpath, XPathConstants.NODE);
    }

    public List<Node> evaluateAsNodeList(Node contextNode, String xpath) throws XPathExpressionException{
        final NodeList nodeList=(NodeList) this.evaluateXPath(contextNode, xpath, XPathConstants.NODESET);
        return CommonUtil.nodeList2List(nodeList);
    }

    public String evaluateAsValue(Node contextNode, String xpath) throws XPathExpressionException{
        return (String) this.evaluateXPath(contextNode, xpath, XPathConstants.STRING);
    }

    private Object evaluateXPath(Node contextNode, String xpath, QName returnType) throws XPathExpressionException{
        if(contextNode==null){
            return null;
        }
        final XPathFactory factory=XPathFactory.newInstance();
        final XPath _xpath=factory.newXPath();
        return _xpath.evaluate(xpath, contextNode, returnType);
    }
    /**
     * convert a list of nodes to a list of elements,
     * drop unmatched nodes
     * @param nodes
     * @return
     */
    private List<Element> convert2ElementList(List<Node> nodes){
        if(nodes==null){
            return new ArrayList<Element>();
        }
        final List<Element> elements=new ArrayList<Element>(nodes.size());
        for(Node node : nodes){
            if(node.getNodeType()==Node.ELEMENT_NODE){
                elements.add((Element)node);
            }
        }
        return elements;
    }

    /**
     * convert a list of nodes to a list of attributes,
     * drop unmatched nodes
     * @param nodes
     * @return
     */
    private List<Attr> convert2AttributeList(List<Node> nodes){
        if(nodes==null){
            return new ArrayList<Attr>();
        }
        final List<Attr> attrs=new ArrayList<Attr>(nodes.size());
        for(Node node : nodes){
            if(node.getNodeType()==Node.ATTRIBUTE_NODE){
                attrs.add((Attr)node);
            }
        }
        return attrs;
    }

}
