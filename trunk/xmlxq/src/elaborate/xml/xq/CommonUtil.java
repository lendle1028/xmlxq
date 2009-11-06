/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.xml.xq;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author lendle
 */
public class CommonUtil {
    public static List<Node> nodeList2List(NodeList nodeList){
        List<Node> nodes=new ArrayList<Node>();
        for(int i=0; nodeList!=null && i<nodeList.getLength(); i++){
            nodes.add(nodeList.item(i));
        }
        return nodes;
    }
}
