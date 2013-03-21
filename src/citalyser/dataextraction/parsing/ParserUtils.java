package citalyser.dataextraction.parsing;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Abhishek Gupta
 */
public class ParserUtils {

    /* Parses Journal and year from the String under title in Google Scholar*/
    public static String splitjournal_year(String string, int type) {
        String[] splitarr;
        int flag = 0;

        try {
            splitarr = string.split("…, ");
            flag = 1;
            if (splitarr.length == 1) {
                splitarr = string.split(", ");
                Integer.parseInt(splitarr[1]);
                flag = 0;
            }
        } catch (Exception e) {
            splitarr = string.split(", ");
        }
        if (type == 1) {
            if (splitarr.length == 2) {
                if (flag == 1) {
                    return splitarr[0] + "…";
                } else {
                    try {
                        Integer.parseInt(splitarr[1]);
                        return splitarr[0];
                    } catch (Exception e) {
                        return splitarr[0] + splitarr[1];
                    }
                }
            }

            if (splitarr.length == 1) {
                try {
                    Integer.parseInt(splitarr[0]);
                    return "";
                } catch (Exception e) {
                    return splitarr[0];
                }
            }
        }

        if (type == 2) 
        {
            {
                try {
                    Integer.parseInt(splitarr[splitarr.length - 1]);
                    return splitarr[splitarr.length - 1];
                } catch (Exception e) {
                    return "0";
                }

            }

        }
        return null;
    }
  
    //This function has been deprecated.  
 /*   public void extractProfileInfo(String file) {
        doc = Jsoup.parse(file, "UTF-8");
        Elements items = doc.select("div.gs_r");

        if (!items.isEmpty()) {

            for (Element item : items) {

                Elements author_section = item.select("h4.gs_rt2");
                if (!author_section.isEmpty()) {
                    for (Element section : author_section) {

                        Elements author_tags = section.select("a");
                        for (Element author_tag : author_tags) {
                            String author_name = author_tag.text();
                            String url = "http://scholar.google.com";
                            String author_link = url + author_tag.attr("href");
                        }
                    }


                }


            }


        }



    }
*/
}
