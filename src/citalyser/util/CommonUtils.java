/*****************************************************************
 * @author Abhishek Choudhary
 * @Email-id : abhishek@codeblues.in
 *****************************************************************/

package citalyser.util;

import citalyser.Constants;
import citalyser.model.UrlComposer;
import citalyser.model.query.QueryType;
import citalyser.util.CProxy;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.net.InetSocketAddress;
import java.net.Proxy;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import org.apache.log4j.Logger;

public class CommonUtils {
    static Logger logger = Logger.getLogger(CommonUtils.class.getName());
    public static void setSystemProxy(CProxy proxy)
    {
        logger.debug("Setting System Proxy : ");
        System.getProperties().put("http.proxyHost", proxy.getHostName());
        System.getProperties().put("http.proxyPort", proxy.getPort());
        System.getProperties().put("http.proxyUser", proxy.getUsername());
        System.getProperties().put("http.proxyPassword", proxy.getPassword());
    }
    
    public static Proxy getJavaProxyFromCProxy(CProxy cproxy)
    {
        Proxy proxy;
        if(!cproxy.getnoProxy())
            proxy = new Proxy(Proxy.Type.HTTP, 
                                new InetSocketAddress(cproxy.getHostName(),cproxy.getPort()));
        else
            proxy = Proxy.NO_PROXY;
        return proxy;
    }
    
    public static Proxy getBestJavaProxy()
    {
        CProxy cproxy = Config.getProxylist().get(0);
        return getJavaProxyFromCProxy(cproxy);
    }
    
    public static void exportToCsv(TableModel model, File file){
	try{
		logger.info("Writing tables contents to CSV file: " + file.getAbsolutePath());
		//TableModel model = table.getModel();

		 //if(!file.canWrite()) file.setWritable(true);
		FileWriter csvFile = new FileWriter(file,false);

		for(int i = 0; i < model.getColumnCount(); i++){
				csvFile.write(model.getColumnName(i) + ",");//for CSV
				//csvFile.write(model.getColumnName(i) + ",");// for excel file
		}

		csvFile.write("\n");
		for(int i=0; i< model.getRowCount(); i++) {
			for(int j=0; j < model.getColumnCount(); j++) {
				csvFile.write("\""+model.getValueAt(i,j).toString().replaceAll("\"","\"\"") +"\",");// for CSV
				//csvFile.write(model.getValueAt(i,j).toString() +"\t");// for excel file
			}
			csvFile.write("\n");
		}
		System.out.println("Exiting write");
		csvFile.close();
	}catch(FileNotFoundException ex){
            JOptionPane.showMessageDialog(null,"Permission Denied");
        }catch(Exception e) { 
		logger.info(e); 
	}
    }
    
    public static QueryType getQueryFlagFromUrl(String url)
    {
        if(url.contains("citations?"))
            return QueryType.CITATIONS_LIST_METRIC;
        else 
            return QueryType.CITATIONS_LIST;
    }
    
    public static int getMaxResultsByQueryType(QueryType q)
    {
        switch(q)
        {
            case GEN_AUTH: 
                return Constants.MaxResultsNum.GENERAL_LIST.getValue();
            case GEN_JOURN:
                return Constants.MaxResultsNum.GENERAL_LIST.getValue();
            case MET_AUTH:
                return Constants.MaxResultsNum.AUTHOR_LIST.getValue();
            case MET_JOURN:
                return Constants.MaxResultsNum.JOURNAL_LIST.getValue();
            case AUTH_PROF:
                return Constants.MaxResultsNum.AUTHOR_PAPERS.getValue();
            case JOURN_PROF:
                return Constants.MaxResultsNum.METRICS_JOURNAL_PAPERS.getValue();
            case IMAGE_FROM_LINK:
                return 0;
            case CITATIONS_LIST:
                return Constants.MaxResultsNum.GENERAL_LIST.getValue();
            case CITATIONS_LIST_METRIC:
                return Constants.MaxResultsNum.GENERAL_LIST.getValue();
            case ADV_SRCH:
                return Constants.MaxResultsNum.GENERAL_LIST.getValue();
            default : 
                return 0;
        }
    }
}
