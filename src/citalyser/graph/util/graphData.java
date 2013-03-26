/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.graph.util;

import citalyser.dataextraction.parsing.Parser;
import citalyser.model.Paper;
import citalyser.model.PaperCollection;
import citalyser.model.query.queryresult.PaperCollectionResult;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author sahil
 */
public class graphData {

    static Logger logger = Logger.getLogger(graphData.class.getName());
    // static String returnValue;
    static int id = 0;

    public graphData() {
        this.id = 0;
    }

    public nodeInfo getbaseNode(Paper paper) {
        nodeInfo g = new nodeInfo();
        g.Title = paper.getTitle();
        g.EntireInfo = "<html><head></head><body><B>" + paper.getTitle() + "</B><br>" + paper.getInfo() + "</body></html>";
        g.id = this.id;
        g.citationurl = paper.getcitedByUrl();
        this.id += 1;
        g.nocitation = paper.getNumCites();
        return g;
    }

    public graphObject getNodeArray(PaperCollection p) {
        graphObject obj = new graphObject();
        ArrayList<nodeInfo> arr = new ArrayList<>();
        for (Paper paper : p.getPapers()) {
            nodeInfo g = new nodeInfo();
            g.Title = paper.getTitle();
            g.EntireInfo = "<html><head></head><body><B>" + paper.getTitle() + "</B><br>" + paper.getInfo() + "</body></html>";
            //logger.debug("@#$%:" + paper.getInfo());
            g.nocitation = paper.getNumCites();
            g.id = this.id;
            this.id += 1;
            g.citationurl = paper.getcitedByUrl();
            arr.add(g);
        }
        obj.arr = arr;
        return obj;
    }
}
