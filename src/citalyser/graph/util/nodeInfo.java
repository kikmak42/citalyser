/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.graph.util;

/**
 *
 * @author sahil
 */
public class nodeInfo {
    public int id;
    public String citationurl;
    public String EntireInfo;
    public String Title;
    public int nocitation;

    public nodeInfo() {
        this.id = -1;
        this.nocitation=0;
        this.Title = "";
        this.EntireInfo="";
        this.citationurl = "";
    }
}
