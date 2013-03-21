/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.ui.model;

import citalyser.model.Journal;
import citalyser.model.Paper;
import citalyser.model.PaperCollection;
import citalyser.ui.visualization.panels.regulardisplaypanel.datavisualizationpanel.contentdisplaypanel.modules.TableDisplayPanel;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author vikas
 */
public class TableModelHandler {

    public static TableModel getTableModel(PaperCollection pc) {
        ArrayList<Paper> papers = pc.getPapers();
        String[] columnNames = {"S.No", "Title", "Year", "No. of Citations", "Author/(s)", "Journal/(s)"};
        Object[][] data = new Object[papers.size()][columnNames.length];
        for (int i = 0; i < papers.size(); i++) {
            data[i][0] = i + 1;
            data[i][1] = papers.get(i).getTitle();
            data[i][2] = papers.get(i).getYear();
            data[i][3] = Integer.toString(papers.get(i).getNumCites());
            data[i][4] = convertToString(papers.get(i).getAuthors());
            if (papers.get(i).getJournals() == null) {
                data[i][5] = "Empty";
            } else {
                data[i][5] = papers.get(i).getJournals().get(0).getName();
            }
        }

        TableModel tableModel = new DefaultTableModel(data, columnNames);

        return tableModel;
    }
    
    public static TableModel getTableModel(ArrayList<Journal> journal) {
        String[] columnNames = {"S.No.", "Name Of Journal", "h5-index", "h5-median"};
        Object[][] data = new Object[journal.size()][columnNames.length];
        for (int i = 0; i < journal.size(); i++) {
            data[i][0] = i + 1;
            data[i][1] = journal.get(i).getName();
            data[i][2] = journal.get(i).getH5index();
            data[i][3] = journal.get(i).getH5median();
        }

        TableModel tableModel = new DefaultTableModel(data, columnNames);

        return tableModel;
    }
    
    private static <E> String convertToString(List<E> l){
        StringBuilder stringBuilder=new StringBuilder();
        if (l != null) {
            for(E e:l){
                stringBuilder.append(e.toString()).append(", ");   
            }
        }
        return stringBuilder.toString();
    }
}
