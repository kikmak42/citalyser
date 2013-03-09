package hall;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.http.ConnectionManager;
import org.htmlparser.tags.MetaTag;

public class Main {

    public static void main(String[] args) {
            //HttpConnection conn = new HttpConnection();
            
            //conn.getCookies("http://scholar.google.co.in/scholar?q=aravind");
            //conn.getUrlText("http://www.toutube.com");
            
        Parser parser = new Parser();
        
        ConnectionManager manager = Parser.getConnectionManager ();
        // set up proxying
        manager.setProxyHost ("10.3.100.212");
        manager.setProxyPort (8080);
        
        try {
            parser.setResource("http://www.youtube.com");
            //HasAttributeFilter filter = new HasAttributeFilter("name", "description");
            HasAttributeFilter filter = new HasAttributeFilter("class", "lohp-category-shelf-item context-data-item");
            NodeList list = parser.parse(filter);
            //extractAllNodesThatMatch(
            //new HasAttributeFilter("id", "title"));
            //System.out.print(test);
            //filter = new HasAttributeFilter("class", "lohp-category-shelf-item context-data-item");
            //NodeList list = parser.extractAllNodesThatMatch(filter);
            System.out.print(list);
            if(list==null) System.out.println("Error!!");
            if(list!=null) System.out.println("size = "+ list.size() );
            //Node node = test.elementAt(0);
            //System.out.println(list.elementAt(0).getText());
            /*if (node instanceof MetaTag) {
                MetaTag meta = (MetaTag) node;
                String description = meta.getText();

                //System.out.println(description);
            }*/

        } catch (ParserException e) {
            e.printStackTrace();
        }
            
           
    }
}
