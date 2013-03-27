/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.graph;

/**
 *
 * @author sahil
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import citalyser.graph.util.nodeInfo;
import citalyser.model.Paper;
import java.util.ArrayList;

public class GraphHistory {

    private ArrayList<nodeInfo> basenodes;
    private int currentnodeInfoPosition;

    public GraphHistory() {
        this.currentnodeInfoPosition = 0;
        basenodes = new ArrayList<>();
    }

    public void addnodeInfo(nodeInfo paper) {
        if(currentnodeInfoPosition==basenodes.size()){
        currentnodeInfoPosition++;
        basenodes.add(paper);
        }
        else
        {
            for(int i = currentnodeInfoPosition ;i<=basenodes.size(); i++)
            {
                basenodes.remove(basenodes.size()-1);
            }
         basenodes.add(paper);
         currentnodeInfoPosition++;
        }
    }

    public nodeInfo getCurrentnodeInfo() {
        return this.basenodes.get(currentnodeInfoPosition-1);
    }

    public nodeInfo gotoNextnodeInfo() {
        if(currentnodeInfoPosition < this.basenodes.size())
        {
            currentnodeInfoPosition++;
            return this.basenodes.get(currentnodeInfoPosition-1);
        }
        return this.basenodes.get(currentnodeInfoPosition-1);
    }

    public nodeInfo gotoPreviousnodeInfo() 
    {
        if(currentnodeInfoPosition > 1)
        {
            currentnodeInfoPosition--;
            return this.basenodes.get(currentnodeInfoPosition-1);
        }
        return this.basenodes.get(currentnodeInfoPosition-1);
    }

    public nodeInfo getnodeInfoAtPosition(int position) {
        return this.basenodes.get(position-1);
    }

    public void clear() {
        this.basenodes.clear();
        currentnodeInfoPosition =0;
    }

    public String getnodeList() {
        String list = "";
        int i=0;
        while(i<=basenodes.size()-2){
            list+=((basenodes.get(i).Title.split(" ")[0])+"=>");
            i++;
        }
            list+=((basenodes.get(basenodes.size()-1).Title.split(" ")[0]));
        return list;
    }

    public int getCurrentPosition() {
        return currentnodeInfoPosition;
    }

    public boolean isCurrentPositionFirst() {
        if(currentnodeInfoPosition==1)
            return true;
        else
            return false;
    }

    public boolean isCurrentPositionLast() {
        if(currentnodeInfoPosition==basenodes.size())
            return true;
        else
            return false;
    }
}

