/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.queryhandler;

import citalyser.constants.queryType;

/**
 *
 * @author rajkumar
 */
public class Query{
    public queryType flag;
    public String name;
    public String ID;
    public int max_year;
    public int min_year;
    public int start_result;
    public int num_results;
    public boolean sort_flag;
    public boolean h_idx;
    public boolean i_idx;
}