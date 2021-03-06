
package citalyser;


/**
 *
 * @author rajkumar
 */
public class Constants {
    
    public static int SERVER_READOUT_TIME = 30000;

    public static String[] userAgents = {
//            "Mozilla/5.0 (Windows; U; Windows NT 6.1; rv:2.2) Gecko/20110201",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.17 (KHTML, like Gecko) Chrome/24.0.1312.60 Safari/537.17",
//            "Mozilla/5.0 (iPad; CPU OS 6_0 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10A5355d Safari/8536.25",
//            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/537.13+ (KHTML, like Gecko) Version/5.1.7 Safari/534.57.2",
//            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/534.55.3 (KHTML, like Gecko) Version/5.1.3 Safari/534.53.10",
//            "Mozilla/5.0 (iPad; CPU OS 5_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko ) Version/5.1 Mobile/9B176 Safari/7534.48.3",
//            "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_8; de-at) AppleWebKit/533.21.1 (KHTML, like Gecko) Version/5.0.5 Safari/533.21.1",
//            "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_7; da-dk) AppleWebKit/533.21.1 (KHTML, like Gecko) Version/5.0.5 Safari/533.21.1",
//            "Mozilla/5.0 (Windows; U; Windows NT 6.1; tr-TR) AppleWebKit/533.20.25 (KHTML, like Gecko) Version/5.0.4 Safari/533.20.27",
//            "Opera/9.80 (Windows NT 6.0) Presto/2.12.388 Version/12.14",
//            "Mozilla/5.0 (Windows NT 6.0; rv:2.0) Gecko/20100101 Firefox/4.0 Opera 12.14",
//            "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.0) Opera 12.14",
//            "Opera/12.80 (Windows NT 5.1; U; en) Presto/2.10.289 Version/12.02",
    };
    
    public enum MaxResultsNum {  
        AUTHOR_LIST(10),     // Max number of author results retrieved at one time
        AUTHOR_PAPERS(100),   // Max number of author papers retrieved at one time from Scholar
        JOURNAL_LIST(10),     // Max number of results in a journal list from Metric
        GENERAL_LIST(20),     // Max number of results in a genral paper collection result
        METRICS_JOURNAL_PAPERS(20), // Max number of results of papers for a particular journal in Metrics.
        CITATION_LIST(20);   // Max number of citations of a paper
        private final int num;
        MaxResultsNum(int id) { this.num = id; }
        public int getValue() { return num; }
        
    }

    public static int OK_Response_Code = 200;
    public static int NOT_FOUND_Code = 404;
    public static String SCHOLAR_BASE_URL = "http://scholar.google.co.in";
    public static String RESOURCE_FOLDER_PATH = "/citalyser/ui/visualization/resources/";
}


