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
 *
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

    public XQ(Node domNode, Navigator navigator, Manipulator manipulator, Finder finder){
        this.init(domNode, navigator, manipulator, finder);
    }

    private void init(String xml) throws Exception{
        StringReader reader=new StringReader(xml);
        try{
            this.init(reader);
        }finally{
            if(reader!=null){
                reader.close();
            }
        }
    }

    private void init(URL url, String encoding) throws Exception{
        InputStream input=url.openStream();
        try{
            this.init(input, encoding);
        }finally{
            if(input!=null){
                input.close();
            }
        }
    }

    private void init(File file, String encoding) throws Exception{
        InputStream input=new FileInputStream(file);
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
        DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder db=dbf.newDocumentBuilder();

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
        if(this.domNode.getNamespaceURI()!=null){
            return new QName(this.domNode.getNamespaceURI(), this.domNode.getLocalName());
        }
        else{
            return new QName(this.domNode.getNodeName());
        }
    }

    public short getNodeType(){
        return this.domNode.getNodeType();
    }

    //navigator functions
    public XQ getNextNode(){
        return convert(this.getNavigator().getNextNode(domNode));
    }
    public XQ getPrevNode(){
        return convert(this.getNavigator().getParentNode(domNode));
    }
    public XQ getNextNode(short nodeType){
        return convert(this.getNavigator().getNextNode(domNode, nodeType));
    }
    public XQ getPrevNode(short nodeType){
        return convert(this.getNavigator().getPrevNode(domNode, nodeType));
    }
    public XQ getParentNode(){
        return convert(this.getNavigator().getParentNode(domNode));
    }
    public XQ getOwnerElement(){
        return convert(this.getNavigator().getOwnerElement(domNode));
    }
    public XQ getOwnerOrParentElement(){
        return convert(this.getNavigator().getOwnerOrParentElement(domNode));
    }

    public List<XQ> getChildNodes(){
        return convert(this.getNavigator().getChildNodes(domNode));
    }
    public XQ getFirstChildNode(){
        return convert(this.getNavigator().getFirstChildNode(domNode));
    }
    public List<XQ> getAttributes(){
        return convert(this.getNavigator().getAttributes(domNode));
    }

    public XQ getNextTextNode(){
        return convert(this.getNavigator().getNextTextNode(domNode));
    }
    public XQ getNextElementNode(){
        return convert(this.getNavigator().getNextElementNode(domNode));

    }
    public XQ getPrevTextNode(){
        return convert(this.getNavigator().getPrevTextNode(domNode));
    }
    public XQ getPrevElementNode(){
        return convert(this.getNavigator().getPrevElementNode(domNode));
    }

    public XQ getFirstChildElement(){
        return convert(this.getNavigator().getFirstChildElement(domNode));
    }
    public XQ getFirstChildText(){
        return convert(this.getNavigator().getFirstChildText(domNode));
    }
    public XQ getAttributeNode(String name){
        return convert(this.getNavigator().getAttribute(domNode, name));
    }
    //Finder functions
    public XQ evaluateAsNode(String xpath) throws XPathExpressionException{
        return convert(this.getFinder().evaluateAsNode(domNode, xpath));
    }
    public List<XQ> evaluateAsNodeList(String xpath) throws XPathExpressionException{
        return convert(this.getFinder().evaluateAsNodeList(domNode, xpath));
    }
    public String evaluateAsValue(String xpath) throws XPathExpressionException{
        return this.getFinder().evaluateAsValue(domNode, xpath);
    }
    public List<XQ> findByNodeName(String tagName){
        return convert(this.getFinder().findByNodeName(domNode, tagName));
    }
    public List<XQ> findByNodeName(QName qName){
        return convert(this.getFinder().findByNodeName(domNode, qName));
    }

    public List<XQ> findElementsByNodeName(String tagName){
        return convert(this.getFinder().findElementsByNodeName(domNode, tagName));
    }
    public List<XQ> findElementsByNodeName(QName qName){
        return convert(this.getFinder().findElementsByNodeName(domNode, qName));
    }

    public List<XQ> findAttributesByNodeName(String tagName){
        return convert(this.getFinder().findAttributesByNodeName(domNode, tagName));
    }
    public List<XQ> findAttributesByNodeName(QName qName){
        return convert(this.getFinder().findAttributesByNodeName(domNode, qName));
    }
    public List<XQ> findElementsByAttributeValue(String attributeName, String attributeValue){
        return convert(this.getFinder().findElementsByAttributeValue(domNode, attributeName, attributeValue));
    }
    public List<XQ> findElementsByAttributeValue(QName attributeName, String attributeValue){
        return convert(this.getFinder().findElementsByAttributeValue(domNode, attributeName, attributeValue));
    }
    public List<XQ> findElementsByAttribute(String attributeName){
        return convert(this.getFinder().findElementsByAttribute(domNode, attributeName));
    }
    public List<XQ> findElementsByAttribute(QName attributeName){
        return convert(this.getFinder().findElementsByAttribute(domNode, attributeName));
    }
    public List<XQ> findElementsByTextValue(String value){
        return convert(this.getFinder().findElementsByTextValue(domNode, value));
    }
    //manipulator functions
    public String getValue(){
        return this.manipulator.getValue(domNode);
    }
    public void setValue(String value){
        this.manipulator.setValue(domNode, value);
    }
    public String getAttributeValue(String attribute){
        return this.manipulator.getAttributeValue((Element) domNode,attribute);
    }
    public void setAttributeValue(String attribute, String value){
        this.manipulator.setAttributeValue((Element) domNode,attribute, value);
    }
    public String getAttributeValue(QName attrQName){
        return this.manipulator.getAttributeValue((Element) domNode,attrQName);
    }
    public void setAttributeValue(QName attrQName, String value){
        this.manipulator.setAttributeValue((Element) this.domNode, attrQName, value);
    }
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
        List<XQ> newList=new ArrayList<XQ>(nodes.size());
        for(Node node : nodes){
            newList.add(convert(node));
        }
        return newList;
    }
}
