/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.parsing;

import citalyser.api.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Abhishek Gupta
 */
public class Extractdata {

    static public String source;
    static Document doc;
    static private PaperCollection extractedPapers;
    static private ArrayList<Paper> papers;
    static public ArrayList<String> citedbyList;
    static private String names;

    private static String splitjournal_year(String string, int type) {
        String[] splitarr;
        int flag = 0;

        try {
            splitarr = string.split("…, ");
            flag = 1;
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

        if (type == 2) {
            {
                if (splitarr.length == 2) {
//                    if (flag == 1) {
//                        return splitarr[0] + "…";
//                    } else {
//                        try {
//                            Integer.parseInt(splitarr[1]);
//                            return splitarr[0];
//                        } catch (Exception e) {
//                            return splitarr[0] + splitarr[1];
//                        }
//                    }
                    return splitarr[1];
                }

                if (splitarr.length == 1) {
                    try {
                        Integer.parseInt(splitarr[0]);
                        return splitarr[0];
                    } catch (Exception e) {
                        return "0";
                    }
                }
            }

        }
        return null;
    }

    public Extractdata(String source) {
        this.source = source;
        extractedPapers = new PaperCollection();
        papers = new ArrayList<Paper>();

    }
    //Author a= new Author("dvc");

    public static void main(String args[]) {

        String returnValue = "";
        FileReader file = null;

        try {
            file = new FileReader("/home/sahil/roughos/indentedrespose.html");
            BufferedReader reader = new BufferedReader(file);
            String line = "";
            while ((line = reader.readLine()) != null) {
                returnValue += line + "\n";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (file != null) {
                try {
                    file.close();
                } catch (IOException e) {
                    // Ignore issues during closing 
                }
            }
        }



        //Extractdata exd = new Extractdata(returnValue);
        extractInfo(returnValue);


    }

    //this function takes the title of a paper and the source string and returns an arraylist of authors of that paper
    public static PaperCollection extractInfo(String source) {
        Extractdata.source = source;
        extractedPapers = new PaperCollection();
        papers = new ArrayList<Paper>();
        citedbyList = new ArrayList<String>();
        Paper insertInextractedpapers = new Paper();
        ArrayList<Author> authorsinPaper = new ArrayList<Author>();
        Author authorinpaper = new Author(null);
        ArrayList<Journal> journalsinPaper = new ArrayList<Journal>();
        Journal journalinpaper = new Journal(null);
        String jrnl;
        String year;
        int yearint;
        doc = Jsoup.parse(source, "UTF-8");
        Elements items = doc.select(".gs_ri");//select all items 
        for (Element item : items) {
            //extracting title section
            Elements title_section = item.select("h3>a");
            if (!title_section.isEmpty()) {
                Element section = title_section.get(0);//getting the first element of this array
                String title = section.text();
                String href = section.attr("href");
                insertInextractedpapers.setTitle(title);

            }
            //extracting pdf section
            Elements pdf_section = item.select(".gs_ggs > a");
            if (!pdf_section.isEmpty()) {
                Element section = pdf_section.get(0);
                String pdf = section.attr("href");
                System.out.println("pdf link is" + pdf + "\n");


            }

            //extracting the authors
            Elements author_section_a = item.select(".gs_a > a");
            for (Element author : author_section_a) {
                String author_name = author.text();//getting the author name
                System.out.println(author_name);
                String url = "http://scholar.google.com";
                String citations_link = url + author.attr("href");
                System.out.println("author href:" + author.attr("href"));
                System.out.println(citations_link);
            }
////////////////////////////////////////////////////////////////////////////////////////////////////
            Elements author_section_b = item.select(".gs_a");
            if (!author_section_b.isEmpty()) {
                Element section = author_section_b.get(0);
                String section_text = section.text();
                String[] list = section_text.split(" - … ,? | - ");
                int len = list.length;
                System.out.println("no. of substr(-):" + len);

                if (len == 3) {

                    System.out.println("section text :" + section_text);
                    System.out.println(Arrays.toString(list));
                    names = list[0];
                    System.out.println("list 0 :" + list[0]);
                    System.out.println("list 1 :" + list[1]);
                    // = list[1].split(", ")[0];
                    jrnl = splitjournal_year(list[1], 1);
//                    try {
//                        jrnl = list[1].split("…, ")[0] + "…";
//                    } catch (Exception e) {
//                        jrnl = list[1].split(", ")[0];
//                    }
                    System.out.println("journal:" + jrnl);
                    year = splitjournal_year(list[1], 2);
                    yearint = Integer.parseInt(year);
                    System.out.println("year is:" + year + ":");
//                    try {
//                        year = list[1].split("…, ")[1];
//                        System.out.println("year is:" + year + ":");
//                        yearint = Integer.parseInt(year);
//                    } catch (Exception e) {
//                        try {
//                            year = list[1].split(", ")[1];
//                            System.out.println("year is:" + year + ":");
//                            yearint = Integer.parseInt(year);
//
//                        } catch (Exception e1) {
//                            year = list[1];
//                            try {
//                                yearint = Integer.parseInt(year);
//                                journalinpaper.setName("");
//                            } catch (Exception e2) {
//                                yearint = 0;
//
//                            }
//                            System.out.println("year is:" + year + ":");
//
//                        }
//                    }
                    String[] author_names = names.split(",|…");
                    for (String nameinarray : author_names) {

                        authorinpaper.setName(nameinarray);
                        authorsinPaper.add(authorinpaper);
                    }

                    System.out.println("Authors:  " + Arrays.toString(author_names));


                } /////////////////////////////////////////////////////////////////////////////
                else {//len ==2
                    System.out.println("section text :" + section_text);
                    System.out.println(Arrays.toString(list));
                    String names = list[0];
                    System.out.println("list 0 :" + list[0]);
                    String[] author_names = names.split(",|…");
                    jrnl = "";
                    yearint = 0;
                    for (String nameinarray : author_names) {

                        authorinpaper.setName(nameinarray);
                        authorsinPaper.add(authorinpaper);
                    }
                    System.out.println("Authors:  " + Arrays.toString(author_names));
                }
                journalinpaper.setName(jrnl);
                journalsinPaper.add(journalinpaper);
                insertInextractedpapers.setJournals(journalsinPaper);
                insertInextractedpapers.setYear(yearint);
                insertInextractedpapers.setAuthors(authorsinPaper);


            }

            //extracting the abstract
            Elements abstract_section = item.select(".gs_rs");
            if (!abstract_section.isEmpty()) {
                Element section = abstract_section.get(0);
                String abstractstr = section.text();
                System.out.println("abstract string:" + abstractstr);
                insertInextractedpapers.setAbstract(abstractstr);
            }

            //extracting the citation
            Elements citation_section = item.select(".gs_fl > a");
            if (!author_section_b.isEmpty()) {
                Element section = citation_section.get(0);
                String citation_count;
                try {
                    citation_count = section.text().split(" ")[2];
                } catch (Exception e) {
                    citation_count = "0";
                }
                System.out.println("citation count:" + citation_count);
                insertInextractedpapers.setNumCites(Integer.parseInt(citation_count));

            }

            papers.add(insertInextractedpapers);

        }
        extractedPapers.setPapers(papers);
        return extractedPapers;

    }

    public static ArrayList<String> extractCitedbyLinks(String source) {
        Extractdata.source = source;
        citedbyList = new ArrayList<String>();
        doc = Jsoup.parse(source, "UTF-8");
        Elements items = doc.select(".gs_ri");
        //select all items 
        for (Element item : items) {
            Elements author_section_b = item.select(".gs_a");
            //extracting the citation
            Elements citation_section = item.select(".gs_fl > a");
            if (!author_section_b.isEmpty()) {
                Element section = citation_section.get(0);
                String url = "http://scholar.google.com";
                String citations_link;
                String citation_count;
                try {
                    citation_count = section.text().split(" ")[2];
                    citations_link = url + section.attr("href");
                    citedbyList.add(citations_link);
                } catch (Exception e) {
                    citation_count = "0";
                    citations_link = "";

                }
                System.out.println("citation count:" + citation_count);
                System.out.println("citation link:" + citations_link);


            }




        }
        return citedbyList;
    }

    int checkparsednumbermid(String inp) {
        int ret = 0;
        try {
            ret = inp.split("").length;
        } catch (Exception e) {
        }
        return ret;
    }
}
