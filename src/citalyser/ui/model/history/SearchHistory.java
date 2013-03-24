/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.ui.model.history;

import citalyser.model.query.Query;
import java.util.HashMap;

/**
 *
 * @author Tanmay Patil
 */
public class SearchHistory {
    private HashMap<String, Query> history;

    public SearchHistory() {
        history = new HashMap<>();
    }

    public void addQuery(Query query) {
        //TODO: Write to file
        history.put(query.name, query);
    }

    public void clearHistory() {
        //TODO: Clear from file
        history = new HashMap<>();
    }

    public String[] getSuggestions(String typedText) {
        return history.keySet().toArray(new String[0]);
    }

    public Query[] getHistory() {
        return history.values().toArray(new Query[0]);
    }


}
