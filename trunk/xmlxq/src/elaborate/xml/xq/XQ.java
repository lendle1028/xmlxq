/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.xml.xq;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

/**
 * <pre>
 * The main entry point for navigating and manipulating an
 * xml dom tree.
 *
 * At first, use provided constructors for creating an instance
 * of XQ, e.g.:
 *
 * XQ xq=new XQ(new File("test.xml"), "utf-8");
 *
 * Then, invoke the various functions, e.g.:
 * 
 * XQ nextNode=xq.getNextNode();
 * </pre>
 * @author lendle
 */
public class XQ {
    /**
     * initialize an XQ object by parsing xml defined as a string
     * @param reader
     */
    public XQ(String xml) throws Exception{
        this.init(xml);
    }
    /**
     * initialize an XQ object by parsing xml defined in the url
     * @param reader
     */
    public XQ(URL url, String encoding) throws Exception{
        this.init(url, encoding);
    }
    /**
     * initialize an XQ object by parsing xml defined in the file
     * @param reader
     */
    public XQ(File file, String encoding) throws Exception{
        this.init(file, encoding);
    }
    /**
     * initialize an XQ object by parsing xml defined in the inputStream
     * @param reader
     */
    public XQ(InputStream input, String encoding) throws Exception{
        this.init(input, encoding);
    }
    /**
     * initialize an XQ object by parsing xml defined in the reader
     * @param reader
     */
    public XQ(Reader reader) throws Exception{
        this.init(reader);
    }

    public XQ(Node domNode) {
        this.init(domNode);
    }
    /**
     * initialize XQ with customized navigator, manipulator, and finder
     * @param domNode
     * @param navigator
     * @param manipulator
     * @param finder
     */
    public XQ(Node domNode, Navigator navigator, Manipulator manipulator, Finder finder){
        this.init(domNode, navigator, manipulator, finder);
    }

    private void init(String xml) throws Exception{
        final StringReader reader=new StringReader(xml);
        try{
            this.init(reader);
        }finally{
            if(reader!=null){
                reader.close();
            }
        }
    }

    private void init(URL url, String encoding) throws Exception{
        final InputStream input=url.openStream();
        try{
            this.init(input, encoding);
        }finally{
            if(input!=null){
                input.close();
            }
        }
    }

    private void init(File file, String encoding) throws Exception{
        final InputStream input=new FileInputStream(file);
        try{
            this.init(input, encoding);
        }finally{
            if(input!=null){
                input.close();
            }
        }
    }

    private void init(InputStream input, String encoding) throws Exception{
        this.init(new InputStreamReader(input, encoding));
    }

    private void init(Reader reader) throws Exception{
        final DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        final DocumentBuilder db=dbf.newDocumentBuilder();

        this.init(db.parse(new InputSource(reader)).getDocumentElement(), new DefaultNavigatorImpl(), new DefaultManipulatorImpl(), new DefaultFinderImpl());
    }

    private void init(Node domNode){
        this.init(domNode, new DefaultNavigatorImpl(), new DefaultManipulatorImpl(), new DefaultFinderImpl());
    }

    private void init(Node domNode, Navigator navigator, Manipulator manipulator, Finder finder){
        this.domNode=domNode;
        this.navigator=navigator;
        this.manipulator=manipulator;
        this.finder=finder;
    }

    protected Node domNode;
    protected Navigator navigator;
    protected Manipulator manipulator;
    protected Finder finder;

    /**
     * Get the value of finder
     *
     * @return the value of finder
     */
    public Finder getFinder() {
        return finder;
    }

    /**
     * Set the value of finder
     *
     * @param finder new value of finder
     */
    public void setFinder(Finder finder) {
        this.finder = finder;
    }


    /**
     * Get the value of manipulator
     *
     * @return the value of manipulator
     */
    public Manipulator getManipulator() {
        return manipulator;
    }

    /**
     * Set the value of manipulator
     *
     * @param manipulator new value of manipulator
     */
    public void setManipulator(Manipulator manipulator) {
        this.manipulator = manipulator;
    }


    /**
     * Get the value of navigator
     *
     * @return the value of navigator
     */
    public Navigator getNavigator() {
        return navigator;
    }

    /**
     * Set the value of navigator
     *
     * @param navigator new value of navigator
     */
    public void setNavigator(Navigator navigator) {
        this.navigator = navigator;
    }


    /**
     * Get the value of domNode
     *
     * @return the value of domNode
     */
    public Node getDomNode() {
        return domNode;
    }

    /**
     * Set the value of domNode
     *
     * @param domNode new value of domNode
     */
    public void setDomNode(Node domNode) {
        this.domNode = domNode;
    }

    public QName getQName(){
        if(this.domNode.getNamespaceURI()==null){
            return new QName(this.domNode.getNodeName());
        }
        else{
            return new QName(this.domNode.getNamespaceURI(), this.domNode.getLocalName());
        }
    }

    public short getNodeType(){
        return this.domNode.getNodeType();
    }

    //navigator functions
    /**
     * return the next sibling node
     * @return
     */
    public XQ getNextNode(){
        return convert(this.getNavigator().getNextNode(domNode));
    }
    /**
     * return the previous sibling node
     * @return
     */
    public XQ getPrevNode(){
        return convert(this.getNavigator().getParentNode(domNode));
    }
    /**
     * return the next sibling node that is of the given nodeType
     * @see org.w3c.dom.Node
     * @param nodeType
     * @return
     */
    public XQ getNextNode(short nodeType){
        return convert(this.getNavigator().getNextNode(domNode, nodeType));
    }
    /**
     * return the previous sibling node that is of the given nodeType
     * @see org.w3c.dom.Node
     * @param nodeType
     * @return
     */
    public XQ getPrevNode(short nodeType){
        return convert(this.getNavigator().getPrevNode(domNode, nodeType));
    }
    /**
     * return the parent node
     * for element node only, return null for attribute node
     * @return
     */
    public XQ getParentNode(){
        return convert(this.getNavigator().getParentNode(domNode));
    }
    /**
     * return the owner element
     * for attribute node node, return null for element node
     * @return
     */
    public XQ getOwnerElement(){
        return convert(this.getNavigator().getOwnerElement(domNode));
    }
    /**
     * return parent element for element node;
     * return owner element for attribute node
     * @return
     */
    public XQ getOwnerOrParentElement(){
        return convert(this.getNavigator().getOwnerOrParentElement(domNode));
    }
    /**
     * return all child nodes
     * @return
     */
    public List<XQ> getChildNodes(){
        return convert(this.getNavigator().getChildNodes(domNode));
    }
    /**
     * return the first child node
     * @return
     */
    public XQ getFirstChildNode(){
        return convert(this.getNavigator().getFirstChildNode(domNode));
    }
    /**
     * return all attributes
     * @return
     */
    public List<XQ> getAttributes(){
        return convert(this.getNavigator().getAttributes(domNode));
    }
    /**
     * return the next sibling node that is a text node
     * @return
     */
    public XQ getNextTextNode(){
        return convert(this.getNavigator().getNextTextNode(domNode));
    }
    /**
     * return the next sibling node that is an element node
     * @return
     */
    public XQ getNextElementNode(){
        return convert(this.getNavigator().getNextElementNode(domNode));

    }
    /**
     * return the previous sibling node that is a text node
     * @return
     */
    public XQ getPrevTextNode(){
        return convert(this.getNavigator().getPrevTextNode(domNode));
    }
    /**
     * return the previous sibling node that is an element node
     * @return
     */
    public XQ getPrevElementNode(){
        return convert(this.getNavigator().getPrevElementNode(domNode));
    }
    /**
     * return the first child node that is an element node
     * @return
     */
    public XQ getFirstChildElement(){
        return convert(this.getNavigator().getFirstChildElement(domNode));
    }
    /**
     * return the first child node that is a text node
     * @return
     */
    public XQ getFirstChildText(){
        return convert(this.getNavigator().getFirstChildText(domNode));
    }
    /**
     * return the attribute node with the given attribute name
     * @param name
     * @return
     */
    public XQ getAttributeNode(String name){
        return convert(this.getNavigator().getAttribute(domNode, name));
    }
    //Finder functions
    /**
     * evaluate the given xpath expression, return
     * an node
     * @param xpath
     * @return
     * @throws javax.xml.xpath.XPathExpressionException
     */
    public XQ evaluateAsNode(String xpath) throws XPathExpressionException{
        return convert(this.getFinder().evaluateAsNode(domNode, xpath));
    }
    /**
     * evaluate the given xpath expression, return
     * a list of node
     * @param xpath
     * @return
     * @throws javax.xml.xpath.XPathExpressionException
     */
    public List<XQ> evaluateAsNodeList(String xpath) throws XPathExpressionException{
        return convert(this.getFinder().evaluateAsNodeList(domNode, xpath));
    }
    /**
     * evaluate the given xpath expression, return
     * a String
     * @param xpath
     * @return
     * @throws javax.xml.xpath.XPathExpressionException
     */
    public String evaluateAsValue(String xpath) throws XPathExpressionException{
        return this.getFinder().evaluateAsValue(domNode, xpath);
    }
    /**
     * find nodes with the given tag name
     * @param tagName
     * @return
     */
    public List<XQ> findByNodeName(String tagName){
        return convert(this.getFinder().findByNodeName(domNode, tagName));
    }
    /**
     * find nodes with the given qualified name
     * @param tagName
     * @return
     */
    public List<XQ> findByNodeName(QName qName){
        return convert(this.getFinder().findByNodeName(domNode, qName));
    }
    /**
     * find element nodes with the given tag name
     * @param tagName
     * @return
     */
    public List<XQ> findElementsByNodeName(String tagName){
        return convert(this.getFinder().findElementsByNodeName(domNode, tagName));
    }
    /**
     * find element nodes with the given qualified name
     * @param tagName
     * @return
     */
    public List<XQ> findElementsByNodeName(QName qName){
        return convert(this.getFinder().findElementsByNodeName(domNode, qName));
    }
    /**
     * find attribute nodes with the given tag name
     * @param tagName
     * @return
     */
    public List<XQ> findAttributesByNodeName(String tagName){
        return convert(this.getFinder().findAttributesByNodeName(domNode, tagName));
    }
    /**
     * find attribute nodes with the given qualified name
     * @param tagName
     * @return
     */
    public List<XQ> findAttributesByNodeName(QName qName){
        return convert(this.getFinder().findAttributesByNodeName(domNode, qName));
    }
    /**
     * find element nodes with the given value for the given attribute
     * @param attributeName
     * @param attributeValue
     * @return
     */
    public List<XQ> findElementsByAttributeValue(String attributeName, String attributeValue){
        return convert(this.getFinder().findElementsByAttributeValue(domNode, attributeName, attributeValue));
    }
    /**
     * find element nodes with the given value for the given attribute
     * @param attributeName
     * @param attributeValue
     * @return
     */
    public List<XQ> findElementsByAttributeValue(QName attributeName, String attributeValue){
        return convert(this.getFinder().findElementsByAttributeValue(domNode, attributeName, attributeValue));
    }
    /**
     * find element nodes with the given attribute
     * @param attributeName
     * @param attributeValue
     * @return
     */
    public List<XQ> findElementsByAttribute(String attributeName){
        return convert(this.getFinder().findElementsByAttribute(domNode, attributeName));
    }
    /**
     * find element nodes with the given attribute
     * @param attributeName
     * @param attributeValue
     * @return
     */
    public List<XQ> findElementsByAttribute(QName attributeName){
        return convert(this.getFinder().findElementsByAttribute(domNode, attributeName));
    }
    /**
     * find element nodes with text node with the given value as child
     * @param attributeName
     * @param attributeValue
     * @return
     */
    public List<XQ> findElementsByTextValue(String value){
        return convert(this.getFinder().findElementsByTextValue(domNode, value));
    }
    //manipulator functions
    /**
     * return the String value of the node
     * textContent for element nodes and attribute value for attribute nodes
     * @return
     */
    public String getValue(){
        return this.manipulator.getValue(domNode);
    }
    /**
     * set the String value of the node
     * textContent for element nodes and attribute value for attribute nodes
     * @param value
     */
    public void setValue(String value){
        this.manipulator.setValue(domNode, value);
    }
    /**
     * return attribute value
     * @param attribute
     * @return
     */
    public String getAttributeValue(String attribute){
        return this.manipulator.getAttributeValue((Element) domNode,attribute);
    }
    /**
     * set attribute value
     * @param attribute
     * @param value
     */
    public void setAttributeValue(String attribute, String value){
        this.manipulator.setAttributeValue((Element) domNode,attribute, value);
    }
    /**
     * get attribute value
     * @param attrQName
     * @return
     */
    public String getAttributeValue(QName attrQName){
        return this.manipulator.getAttributeValue((Element) domNode,attrQName);
    }
    /**
     * set attribute value
     * @param attrQName
     * @param value
     */
    public void setAttributeValue(QName attrQName, String value){
        this.manipulator.setAttributeValue((Element) this.domNode, attrQName, value);
    }
    /**
     * output the node as an string in xml format
     * @return
     */
    @Override
    public String toString(){
        return this.manipulator.toString(domNode);
    }
    //conversion functions
    private static XQ convert(Node node){
        if(node==null){
            return null;
        }
        return new XQ(node);
    }

    private static List<XQ> convert(List<? extends Node> nodes){
        final List<XQ> newList=new ArrayList<XQ>(nodes.size());
        for(Node node : nodes){
            newList.add(convert(node));
        }
        return newList;
    }
}
