package citalyser.ui.control;

import citalyser.model.Paper;
import citalyser.ui.control.masters.NavigationMaster;
import citalyser.ui.control.masters.RenderMaster;
import citalyser.ui.control.masters.SearchMaster;
import citalyser.ui.control.masters.SettingsMaster;
import citalyser.ui.control.switchers.QueryResultRenderingHandler;
import citalyser.ui.model.CitationListHistory;

import citalyser.ui.visualization.MainFrame;
import citalyser.ui.visualization.panels.ExternalPanel;
import citalyser.ui.visualization.panels.external.AbstractDisplayPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JDialog;
import org.apache.log4j.Logger;

/**
 *
 * @author Tanmay Patil
 */
public class DisplayMaster {

    private MainFrame mainFrame;
    private javax.swing.JDialog settingsDialog, abstractDialog;
    private ExternalPanel extraPanel;
    private AbstractDisplayPanel abstractDisplayPanel;
    private SettingsMaster settingsMaster;
    private SearchMaster searchMaster;
    private RenderMaster renderMaster;
    private NavigationMaster navigationMaster;
    private QueryResultRenderingHandler queryResultRenderingHandler;
    private static Logger logger = Logger.getLogger(DisplayMaster.class.getName());
    private CitationListHistory citationListHistory;
    private int numberOfResults = 100;
    private final Vector<Thread> threads = new Vector<>();
    private boolean showPaperPreview = true;
    private String queryName;
    private ExecutorService executorService = Executors.newCachedThreadPool();

    public DisplayMaster() {
        mainFrame = new MainFrame();
        mainFrame.setDisplayMaster(this);
        mainFrame.setVisible(true);
        extraPanel = new ExternalPanel();
        abstractDisplayPanel = new AbstractDisplayPanel();
        extraPanel.setDisplayMaster(this);
        settingsMaster = new SettingsMaster(extraPanel);
        searchMaster = new SearchMaster(this);
        navigationMaster = new NavigationMaster(mainFrame, this);
        renderMaster = new RenderMaster(mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getContentDisplayPanel());
        abstractDialog = new JDialog(mainFrame);
        abstractDialog.setUndecorated(true);
        abstractDialog.setFocusable(false);
        abstractDialog.setModal(false);
        abstractDialog.setBackground(new Color(0, 0, 0, 0));
        abstractDialog.getContentPane().setLayout(new BorderLayout());
        abstractDialog.getContentPane().add(abstractDisplayPanel);
        abstractDialog.pack();
        settingsDialog = new javax.swing.JDialog(mainFrame);
        settingsDialog.setUndecorated(true);
        settingsDialog.setLocation(mainFrame.getRootPane().getX(), mainFrame.getRootPane().getY());
        settingsDialog.getContentPane().setLayout(new java.awt.BorderLayout());
        settingsDialog.getContentPane().add(extraPanel);
        settingsDialog.setPreferredSize(new java.awt.Dimension(1349, 692));
        settingsDialog.setBackground(new Color(0, 0, 0, 0));
        settingsDialog.pack();
        queryResultRenderingHandler = new QueryResultRenderingHandler(this);
        citationListHistory = new CitationListHistory();
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public SettingsMaster getSettingsMaster() {
        return settingsMaster;
    }
    
    public SearchMaster getSearchMaster() {
        return searchMaster;
    }

    public RenderMaster getRenderMaster() {
        return renderMaster;
    }

    public NavigationMaster getNavigationMaster() {
        return navigationMaster;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public Vector<Thread> getThreads() {
        return threads;
    }

    public QueryResultRenderingHandler getQueryResultRenderingHandler() {
        return queryResultRenderingHandler;
    }

    public void settingsButtonPressed() {
        settingsDialog.setVisible(true);
    }

    public void mainFrameChanged(int x, int y, int width, int height) {
        if (settingsDialog != null) {
            settingsDialog.setLocation(x, y);
            settingsDialog.setPreferredSize(new Dimension(width, height));
            settingsDialog.pack();
        }
    }

    public void setNumberOfResults(int numberOfResults) {
        this.numberOfResults = numberOfResults;
    }

    public int getNumberOfResults() {
        return numberOfResults;
    }

    public String getQueryName() {
        return queryName;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    public void authorModeClicked() {
        ((java.awt.CardLayout) mainFrame.getContentPane().getLayout()).last(mainFrame.getContentPane());
        mainFrame.getRegularDisplayPanel().getHeaderPanel().getSearchPanel().requestSearchFieldFocus();
        mainFrame.getRegularDisplayPanel().getHeaderPanel().setAuthorSearchMode(true);
    }

    public void journalModeClicked() {
        ((java.awt.CardLayout) mainFrame.getContentPane().getLayout()).last(mainFrame.getContentPane());
        mainFrame.getRegularDisplayPanel().getHeaderPanel().getSearchPanel().requestSearchFieldFocus();
        mainFrame.getRegularDisplayPanel().getHeaderPanel().setAuthorSearchMode(false);
    }

    public void settingsSaveAndClose() {
        settingsDialog.setVisible(false);
    }

    public void displayErrorMessage(String message) {
        javax.swing.JOptionPane.showMessageDialog(mainFrame, message, "Error", javax.swing.JOptionPane.PLAIN_MESSAGE);
        //mainFrame.getRegularDisplayPanel().getToolsPanel().displayErrorMessage(message);
    }

    public void displayStatusMessage(String status) {
        if (status != null) {
            mainFrame.getRegularDisplayPanel().getToolsPanel().displayStatusMessage(status);
        }
    }

    public void displayInfoMessage(String info) {
        if (info != null) {
            mainFrame.getRegularDisplayPanel().getToolsPanel().displayInfoMessage(info);
        }
    }

    public void clearStatusPanel() {
        mainFrame.getRegularDisplayPanel().getStatusDisplayPanel().displayStatus("");
        mainFrame.getRegularDisplayPanel().getStatusDisplayPanel().displayError("");
    }

    public boolean checkAuthorMode() {
        return mainFrame.getRegularDisplayPanel().getHeaderPanel().isAuthorSearchMode();
    }

    public void showContentDisplyPanel() {
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().flipToContentDisplayPanel();
    }

    public void displayGraph(Paper selectedPaper) {
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().getGraphViewPanel().setPaper(selectedPaper);
        mainFrame.getRegularDisplayPanel().getDataVisualizationPanel().flipToGraphDisplayPanel();
    }

    public void showArticleSearch(boolean value) {
        mainFrame.getRegularDisplayPanel().getSidebarPanel().showArticleSearch(value);
    }

    public void showPaperInfo(Paper paper, Point point) {
        /*final Paper myPaper = paper;
         final Point myPoint = point;
         final Thread thread = new Thread() {
        
         @Override
         public void run() {
         Query q = new Query.Builder("").flag(QueryType.CITATIONS_LIST).Url(myPaper.getcitedByUrl()).build();
         QueryResult queryResult = QueryHandler.getInstance().getQueryResult(q);
         if (queryResult != null) {
         PaperCollection pc = (PaperCollection) queryResult.getContents();
         if (myPaper != null) {
         abstractDisplayPanel.setPaper(pc.maximumCitedPaper());
         abstractDialog.setLocation(myPoint);
         abstractDialog.setVisible(true);
         abstractDialog.repaint();
         }
         } else {
         abstractDialog.setVisible(false);
         }
         }
        
         };
         thread.start();
         new Thread() {
        
         @Override
         public void run() {
         try {
         Thread.sleep(100);
         } catch (InterruptedException ex) {
         ex.printStackTrace();
         }
         if (thread.isAlive()) {
         thread.stop();
         }
         }
        
         }.start();*/
        if (showPaperPreview) {
            abstractDisplayPanel.setPaper(paper);
            abstractDialog.setLocation(point);
            abstractDialog.setVisible(true);
            abstractDialog.repaint();
        }
    }

    public void hidePaperInfo() {
        abstractDialog.setVisible(false);
    }

    public void movePaperInfoTo(Point point) {
        if (showPaperPreview) {
            abstractDialog.setLocation(point);
            abstractDialog.repaint();
        }
    }

    public CitationListHistory getCitationListHistory() {
        return citationListHistory;
    }

    public void clearCitationHistory() {
        //  citationListHistory.clear();
    }

    public void setDisplayPreview(boolean selected) {
        showPaperPreview = selected;
    }
}
