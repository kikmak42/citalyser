/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.queryhandler;

/**
 *
 * @author rajkumar, Tanmay
 */
public class Query{
    public QueryType flag;
    public String name;
    public String ID;
    public int max_year;
    public int min_year;
    public int start_result;
    public int num_results;
    public boolean sort_flag;
    public boolean h_idx;
    public boolean i_idx;
    public String url;

    private Query(Builder builder) {
        flag = builder.flag;
        name = builder.name;
        ID = builder.ID;
        max_year = builder.max_year;
        min_year = builder.min_year;
        start_result = builder.start_result;
        num_results = builder.num_results;
        sort_flag = builder.sort_flag;
        h_idx = builder.h_idx;
        i_idx = builder.i_idx;
        url = builder.url;
    }
    
    public static class Builder {
        private QueryType flag;
        private String name;
        private String ID;
        private int max_year;
        private int min_year;
        private int start_result;
        private int num_results;
        private boolean sort_flag;
        private boolean h_idx;
        private boolean i_idx;
        private String url;
        
        public Builder(String name) {
            this.name = name;
        }

        public Builder flag(QueryType flag) {
            this.flag = flag;
            return this;
        }

        public Builder ID(String ID) {
            this.ID = ID;
            return this;
        }

        public Builder maxYear(int maxYear) {
            this.max_year = maxYear;
            return this;
        }

        public Builder minYear(int minYear) {
            this.min_year = minYear;
            return this;
        }

        public Builder startResult(int startResult) {
            this.start_result = startResult;
            return this;
        }

        public Builder numResult(int numResult) {
            this.num_results = numResult;
            return this;
        }

        public Builder sortFlag(boolean sortFlag) {
            this.sort_flag = sortFlag;
            return this;
        }

        public Builder hIndex(boolean hIndex) {
            this.h_idx = hIndex;
            return this;
        }

        public Builder iIndex(boolean iIndex) {
            this.i_idx = iIndex;
            return this;
        }

        public Builder Url(String url) {
            this.url = url;
            return this;
        }
        
        public Query build() {
            return new Query(this);
        }

    }
}