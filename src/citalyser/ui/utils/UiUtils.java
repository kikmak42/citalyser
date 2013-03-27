/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.ui.utils;

import citalyser.Main;
import citalyser.model.query.Query;
import citalyser.model.query.QueryHandler;
import citalyser.model.query.QueryResult;
import citalyser.model.query.QueryType;
import citalyser.model.query.queryresult.ImageResult;
import citalyser.ui.model.ContentRenderer;
import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import org.apache.log4j.Logger;
import sun.misc.JavaxSecurityAuthKerberosAccess;

/**
 *
 * @author abhishek
 */
public class UiUtils {

    static Logger logger = Logger.getLogger(UiUtils.class);
    /* jLabel : Label to display the message
     * message : Message to display
     * time : Time to display the message in milliseconds.
     */

    public static void displayLabelMessageTemp(JLabel jLabel, String message, int time) {
        final JLabel label = jLabel;
        final String m = message;
        final int t = time;
        try {
            new Thread() {

                public void run() {
                    label.setText(m);
                    try {
                        Thread.sleep(t);
                        label.setText("");
                    } catch (InterruptedException ex) {
                        logger.debug("Error in thread while sleeping : " + ex.getMessage());
                    }
                }
            }.start();
        } catch (Exception ex) {
            logger.debug("Error in thread for displaying Message : " + ex.getMessage());
        }
    }

    public static void displayImage(final javax.swing.JLabel myLabel, final String myImgSource, final int myWidth, final int myHeight) {

        try {
            //new Thread() {
            Main.getDisplayController().getExecutorServeice().submit(new Runnable() {
                @Override
                public void run() {
                    myLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/citalyser/ui/visualization/resources/loadingImage.gif")));
                    QueryResult<?> q = (QueryResult<?>) QueryHandler.getInstance().getQueryResult(
                            new Query.Builder("").flag(QueryType.IMAGE_FROM_LINK).Url(myImgSource).build());
                    if (Thread.interrupted()) {
                        return;
                    }
                    ImageIcon img;
                    if (q instanceof ImageResult) {
                        int imageStart = 0;
                        double scale = 1;
                        img = (ImageIcon) q.getContents();
                        if (img == null) {
                            //img = new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource(
                            //                                Constants.RESOURCE_FOLDER_PATH + "user.jpg")));
                            img = new ImageIcon("user.jpg");
                        }
                        BufferedImage img1 = new BufferedImage(myWidth, myHeight, BufferedImage.TYPE_INT_ARGB);
                        Graphics2D g = img1.createGraphics();
                        if (((double) myWidth) / myHeight < ((double) img.getIconWidth()) / img.getIconHeight()) {
                            scale = ((double) myWidth) / img.getIconWidth();
                            imageStart = (myHeight - ((int) (scale * img.getIconHeight()))) / 2;
                            g.drawImage(img.getImage(), 0, imageStart, myWidth, ((int) (scale * img.getIconHeight())), myLabel);
                        } else {
                            scale = ((double) myHeight) / img.getIconHeight();
                            imageStart = (myWidth - ((int) (scale * img.getIconWidth()))) / 2;
                            g.drawImage(img.getImage(), imageStart, 0, ((int) (scale * img.getIconWidth())), myHeight, myLabel);
                        }
                        g.dispose();
                        logger.debug("Setting image url : " + myImgSource + " at Label : " + myLabel.getToolTipText());
                        myLabel.setIcon(new ImageIcon(img1));
                    }
                }
            });//.start();
        } catch (Exception ex) {
            logger.error("Error creating thread for getting image... : " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void openInBrowser(String link) {
        try {
            URI uri = new URL(link).toURI();
            Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
            if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                desktop.browse(uri);
            }
        } catch (MalformedURLException ex) {
            Main.getDisplayController().displayErrorMessage("Link unavailable");
        } catch (URISyntaxException | IOException e) {
            logger.error("Error opening Link in Browser.");
            e.printStackTrace();
        }

    }

    public static void displayQueryCompleteInfoMessage(QueryType qtype, int count, String searchQuery) {
        String result;
        switch (qtype) {
            case GEN_AUTH:
                result = "Displaying " + count + " papers with authors matching '" + searchQuery + "' from Google Scholar...";
                break;
            case GEN_JOURN:
                result = "Displaying " + count + " papers with publications matching '" + searchQuery + "' from Google Scholar...";
                break;
            case MET_AUTH:
                result = "Displaying " + count + " results for Authors matching '" + searchQuery + "'...";
                break;
            case MET_JOURN:
                result = "Displaying Top " + count + " publications matching '" + searchQuery + " '...";
                break;
            case AUTH_PROF:
                result = "Displaying " + count + " papers of Author : " + searchQuery + "...";
                break;
            case JOURN_PROF:
                result = "Displaying " + count + " papers of Journal : " + searchQuery + "...";
                break;
            case CITATIONS_LIST:
            case CITATIONS_LIST_METRIC:
                Main.getDisplayController().displayStatusMessage("");
                return;
            default:
                result = "";
        }
        Main.getDisplayController().displayInfoMessage(result);
    }

    public static void displayQueryStartInfoMessage(QueryType qtype, String searchQuery) {
        String result;
        switch (qtype) {
            case GEN_AUTH:
                result = "Fetching results for papers of Authors with name matching '" + searchQuery + "' from Google Scholar";
                break;
            case GEN_JOURN:
                result = "Fetching results for papers in publications with name matching '" + searchQuery + "'...";
                break;
            case MET_AUTH:
                result = "Fetching results for Authors' name matching '" + searchQuery + "'...";
                break;
            case MET_JOURN:
                result = "Fetching results for publications with name matching '" + searchQuery + "'...";
                break;
            case AUTH_PROF:
                result = "Fetching profile of Author : " + searchQuery + "...";
                break;
            case JOURN_PROF:
                result = "Fetching papers of Journal : " + searchQuery + "...";
                break;
            case CITATIONS_LIST:
                result = "Fetching Citations for " + searchQuery + " from Google Scholar...";
                Main.getDisplayController().displayStatusMessage(result);
                return;
            case CITATIONS_LIST_METRIC:
                result = "Fetching Citations for " + searchQuery + " from Google Metrics...";
                Main.getDisplayController().displayStatusMessage(result);
                return;
            default:
                result = "";
        }
        Main.getDisplayController().displayInfoMessage(result);
    }

    public static void displayQueryEmptyMessage(ContentRenderer contentRenderer, QueryType qtype, String searchQuery) {
        String result;
        switch (qtype) {
            case GEN_AUTH:
                result = "We did not find any results for papers with Authors matching '" + searchQuery + "' from Google Scholar";
                break;
            case GEN_JOURN:
                result = "We did not find any results for papers in publications with name matching '" + searchQuery + "'...";
                break;
            case MET_AUTH:
                result = "We did not find any authors matching '" + searchQuery + "'. "
                        + "Try searching choosing the Author Papers Option for results from Google Scholar Search.";
                break;
            case MET_JOURN:
                result = "We did not find any journals matching '" + searchQuery + "' "
                        + "Try searching choosing the Google Scholar Option for results from Google Scholar Search.";
                break;
            case AUTH_PROF:
                result = "There was some error fetching profile of Author  : '" + searchQuery + "'...";
                break;
            case JOURN_PROF:
                result = "There was some error fetching publications of the journal '" + searchQuery + "'...";
                break;
            case CITATIONS_LIST:
            case CITATIONS_LIST_METRIC:
                result = "Citations Count is 0 for Paper : " + searchQuery + "";
                break;
            default:
                return;
        }
        contentRenderer.displayMessage(result);
    }

    public static void displayResultNullMessage(QueryType qtype,String searchQuery)
    {
        String result;
        switch (qtype) {
            case GEN_AUTH:
                result = "Could not fetch papers with authors matching '" + searchQuery + "' from Google Scholar...";
                break;
            case GEN_JOURN:
                result = "Could not fetch papers with publications matching '" + searchQuery + "' from Google Scholar...";
                break;
            case MET_AUTH:
                result = "Could not fetch results for Authors matching '" + searchQuery + "'...";
                break;
            case MET_JOURN:
                result = "Could not fetch publications matching '" + searchQuery + " '...";
                break;
            case AUTH_PROF:
                result = "Could not fetch papers of Author : " + searchQuery + "...";
                break;
            case JOURN_PROF:
                result = "Could not fetch papers of Journal : " + searchQuery + "...";
                break;
            case CITATIONS_LIST:
            case CITATIONS_LIST_METRIC:
                result = "Could not fetch Citations for " + searchQuery + " from Google Scholar...";
                Main.getDisplayController().displayStatusMessage(result);
                return;
            default:
                result = "";
        }
        Main.getDisplayController().displayInfoMessage(result);
    }
    
    public static void displayGraph(final JLabel jLabel, final Map<Integer, Integer> map1, final int width, final int height) {
        StringBuilder yearall = new StringBuilder();
        StringBuilder citall = new StringBuilder();
        int yearmin = 99999999, yearmax = 0, counter;
        float citmax = 0;
        String url = new String();

        for (Map.Entry<Integer, Integer> entry : map1.entrySet()) {
            if (yearmin > entry.getKey()) {
                yearmin = entry.getKey();
            }
            if (yearmax < entry.getKey()) {
                yearmax = entry.getKey();
            }
        }
        counter = (yearmax - yearmin) / 4;
        for (int i = yearmin; i <= yearmax; i++) {
            if (map1.get(i) != null) {
                if (citmax < map1.get(i)) {
                    citmax = map1.get(i);
                }
            }
        }
        logger.debug("citmax=" + citmax);
        for (int i = yearmin; i <= yearmax; i++) {
            if (map1.get(i) == null) {
                citall.append(0);
            } else {
                citall.append((double) (map1.get(i) / citmax) * 100);
            }
            citall.append(",");
            yearall.append("|");
            if (i == yearmin || i == (yearmin + counter) || i == (yearmin + (2 * counter)) || i == (yearmin + (3 * counter)) || i == yearmax) {
                yearall.append(i);
            }
        }

        String cite = citall.substring(0, citall.length() - 1);
        url = "http://www.google.com/chart?chs=" + width + "x" + height + "&cht=bvs&chf=bg,s,e8f4f7&chco=1111cc&chbh=r,2.0,0.0&chxt=x,y&chxr=1,0," + citmax + "," + citmax + "&chd=t:" + cite + "&chxl=0:" + yearall;
        displayImage(jLabel, url, width, height);
    }
}
