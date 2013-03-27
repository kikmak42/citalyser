
package citalyser.ui.model;

import citalyser.model.Journal;
import citalyser.model.Paper;
import citalyser.model.PaperCollection;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.apache.log4j.Logger;

/**
 *
 * @author vikas
 */
public class TableModelHandler {

    static Logger logger = Logger.getLogger(TableModelHandler.class);
    public static TableModel getTableModel(PaperCollection pc) {
        ArrayList<Paper> papers = pc.getPapers();
        final String[] columnNames = {"S.No", "Title", "Year", "# Citations", "Author/(s)", "Journal/(s)"};
        Object[][] data = new Object[papers.size()][columnNames.length];
        for (int i = 0; i < papers.size(); i++) {
            int year = papers.get(i).getYear();
            String yearstr;
            if(year == 0)
                yearstr = "Any";
            else
                yearstr = Integer.toString(papers.get(i).getYear());
            data[i][0] = new Integer(i + 1);
            data[i][1] = papers.get(i).getTitle();
            data[i][2] = yearstr;
            data[i][3] = new Integer(papers.get(i).getNumCites());
            data[i][4] = convertToString(papers.get(i).getAuthors());
            data[i][5] = papers.get(i).getJournalString();
        }
        TableModel tableModel = new DefaultTableModel(data, columnNames) {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }

            public Class getColumnClass(int c) {
                if (getRowCount() > 0) {
                    return getValueAt(0, c).getClass();
                } else {
                    return Object.class;
                }
            }
            public String getColumnName(int col) {
                return columnNames[col];
        }

        };

        return tableModel;
    }

    public static TableModel getTableModel(ArrayList<Journal> journal) {
        String[] columnNames = {"S.No", "Name Of Journal", "h5-index", "h5-median"};
        Object[][] data = new Object[journal.size()][columnNames.length];
        for (int i = 0; i < journal.size(); i++) {
            data[i][0] = i + 1;
            data[i][1] = journal.get(i).getName();
            data[i][2] = new Integer(journal.get(i).getH5index());
            data[i][3] = new Integer(journal.get(i).getH5median());
        }

        TableModel tableModel = new DefaultTableModel(data, columnNames) {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }

            public Class getColumnClass(int c) {
                if (getRowCount() > 0) {
                    return getValueAt(0, c).getClass();
                } else {
                    return Object.class;
                }
            }
        };

        return tableModel;
    }

    private static <E> String convertToString(List<E> l) {
        StringBuilder stringBuilder = new StringBuilder();
        if (l != null) {
            for (E e : l) {
                stringBuilder.append(e.toString()).append(", ");
            }
        }
        return stringBuilder.toString();
    }
}
