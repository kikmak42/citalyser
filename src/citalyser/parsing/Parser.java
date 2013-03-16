/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//i have given the path of input.html in c:\input.html
package citalyser.parsing;

import citalyser.model.Author;
import citalyser.model.Paper;
import citalyser.model.PaperCollection;
import citalyser.model.Journal;
import citalyser.queryresult.*;
import citalyser.queryresult.QueryResult;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Abhishek Gupta
 */
public class Parser {

    private static Logger logger = Logger.getLogger(Parser.class.getName());
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

        if (type == 2) {
            {
                try {
                    Integer.parseInt(splitarr[splitarr.length - 1]);
                    return splitarr[splitarr.length - 1];
                } catch (Exception e) {
                    return "0";
                }

                /*
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
                 */
            }

        }
        return null;
    }

    public Parser(String source) {
        this.source = source;
        extractedPapers = new PaperCollection();
        papers = new ArrayList<Paper>();

    }
    //Author a= new Author("dvc");

    public static void main(String args[]) {
        PropertyConfigurator.configure("log4j.properties");

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



        extractInfo(returnValue);

    }

    //this function takes the title of a paper and the source string and returns an arraylist of authors of that paper
    public static QueryResult<PaperCollection> extractInfo(String source) {
        QueryResult<PaperCollection> q= new PaperCollectionResult();
        Parser.source = source;
        
        extractedPapers = new PaperCollection();
        papers = new ArrayList<Paper>();
        citedbyList = new ArrayList<String>();

        String jrnl;
        String year;
        int yearint;
        doc = Jsoup.parse(source, "UTF-8");
        Elements items = doc.select(".gs_ri");//select all items 
        for (Element item : items) {
            //extracting title section
            ArrayList<Author> authorsinPaper = new ArrayList<Author>();
            ArrayList<Journal> journalsinPaper = new ArrayList<Journal>();

            Paper insertInextractedpapers = new Paper();
            Journal journalinpaper = new Journal(null);


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
                    journalinpaper.setName(jrnl);
                    journalsinPaper.add(journalinpaper);
                    insertInextractedpapers.setJournals(journalsinPaper);


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
                        Author authorinpaper = new Author(null);
                        authorinpaper.setName(nameinarray);
                        authorsinPaper.add(authorinpaper);
                    }

                    System.out.println("Authors:  " + Arrays.toString(author_names));


                } /////////////////////////////////////////////////////////////////////////////
                else {//len ==2
                    System.out.println("section text:" + section_text);
                    System.out.println(Arrays.toString(list));
                    String names = list[0];
                    System.out.println("list 0 :" + list[0]);
                    String[] author_names = names.split(",|…");
                    jrnl = "";
                    journalinpaper.setName(jrnl);
                    journalsinPaper.add(journalinpaper);
                    insertInextractedpapers.setJournals(journalsinPaper);

                    yearint = 0;
                    for (String nameinarray : author_names) {
                        Author authorinpaper = new Author(null);
                        authorinpaper.setName(nameinarray);
                        authorsinPaper.add(authorinpaper);
                    }
                    System.out.println("Authors:  " + Arrays.toString(author_names));
                }
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
                    if (section.text().split(" ")[0] == "Cited") {
                        citation_count = section.text().split(" ")[2];
                    } else {
                        citation_count = "0";
                    }
                } catch (Exception e) {
                    citation_count = "0";
                }
                System.out.println("citation count:" + citation_count);
                insertInextractedpapers.setNumCites(Integer.parseInt(citation_count));

            }

            papers.add(insertInextractedpapers);

        }
        extractedPapers.setPapers(papers);
        for (Paper p : papers) {
            logger.debug(p.getAuthors());
        }
        q.setContents(extractedPapers);
        return q;

    }

    public static void extractProfileInfo(String file) {
        doc = Jsoup.parse(file, "UTF-8");
        Elements items = doc.select("div.gs_r");

        if (!items.isEmpty()) {

            for (Element item : items) {
                /*
                 Elements title_section = item.select("h3.gs_rt>a");
                 if (!title_section.isEmpty()) {
                 Element section = title_section.get(0);//getting the first element of this array
                 String title = section.text();
                 String href = section.attr("href");
                 //insertInextractedpapers.setTitle(title);
                 System.out.println(title);
                 System.out.println(href);
                       

                 }
                 */
                //System.out.println("after ist printf");
                Elements author_section = item.select("h4.gs_rt2");
                System.out.println(author_section.isEmpty());
                if (!author_section.isEmpty()) {
                    for (Element section : author_section) {

                        Elements author_tags = section.select("a");
                        for (Element author_tag : author_tags) {
                            String author_name = author_tag.text();
                            String url = "http://scholar.google.com";
                            String author_link = url + author_tag.attr("href");
                            System.out.println(author_name);
                            System.out.println(author_link);
                        }
                    }


                }


            }


        }



    }

    public static ArrayList<String> extractCitedbyLinks(String source) {
        Parser.source = source;
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

    public static QueryResult<Author> extractAuthorProfileInfo(String src) {
        QueryResult<Author> qr_author_result = new AuthorResult();
        //AuthorResult ar =new AuthorResult();
        Author author = new Author(null);
        PaperCollection pc = new PaperCollection();
        ArrayList<Paper> papers = new ArrayList<Paper>();
        
        doc = Jsoup.parse(src, "UTF-8");
        Elements items = doc.select("table.cit-table");
        System.out.println(items.isEmpty());
        String url = "http://scholar.google.com";

        if (!items.isEmpty()) {
            for (Element item : items) {
                
                //Elements rows = item.select("tr.cit-table item");
                System.out.println("inside for loop");
                Elements rows = item.select(".item");
                logger.debug("rows are" + rows.isEmpty());
                for (Element row : rows) {
                    //extracting the paper title and link of paper
                    //creating a paper object
                    Paper papr = new Paper();
                    ArrayList<Author> paper_authors = new ArrayList<>();
                    ArrayList<Journal> paper_jrnl = new ArrayList<>();
                    Elements title_section = row.select("td#col-title");
                    logger.debug("titlesection" + title_section.isEmpty());
                    Elements title_tags = title_section.get(0).select("a");
                    if (!title_tags.isEmpty()) {
                        String title_link = url + title_tags.get(0).attr("href");
                        String title_name = title_tags.get(0).text();
                        //logger.debug("title link" + title_link);
                        //logger.debug("title_name" + title_name);
                        papr.setTitle(title_name);
                        papr.setUrl(title_link);
                        
                    }
                    //extracting the names of the authors
                    Elements desc_section = row.select("span.cit-gray");
                    if (!desc_section.isEmpty()) {
                        String authors_list;
                        String names;
                        String journal;
                        try {
                            authors_list = desc_section.get(0).text();
                            String[] author_names = authors_list.split(",");
                            names = Arrays.toString(author_names);
                            for(String name : author_names){
                                Author paper_author = new Author(name);
                                paper_authors.add(paper_author);
                            }
                            //logger.debug("author names" + names);

                        } catch (Exception e) {
                            names = "";
                            //logger.debug("author names" + names);
                        }

                        try {
                            journal = desc_section.get(1).text();
                            Journal jrnl = new Journal(journal);
                            paper_jrnl.add(jrnl);
                            
                            //logger.debug("journal names" + journal);

                        } catch (Exception e) {
                            journal = "";
                            //logger.debug("journal names" + journal);
                        }




                    }

                    //extracting the citation count
                    Elements citation_section = row.select("td#col-citedby");
                    if (!citation_section.isEmpty()) {
                        String citation_count;
                        String cited_by_link;
                        try {
                            citation_count = citation_section.get(0).text();
                            //logger.debug("citation_count " + citation_count);
                            papr.setNumCites(Integer.parseInt(citation_count));
                        } catch (Exception e) {
                            citation_count = "";
                            //logger.debug("citation_count " + citation_count);
                        }

                        try {
                            cited_by_link = citation_section.get(0).select("a").attr("href");
                            //logger.debug("cited_by_link " + cited_by_link);
                            papr.setCitedByUrl(cited_by_link);
                        } catch (Exception e) {
                            cited_by_link = "";
                            //logger.debug("cited_by_link " + cited_by_link);
                        }

                    }

                    //extracting the publication year
                    Elements year_section = row.select("td#col-year");
                    if (!year_section.isEmpty()) {
                        String year;
                        try {
                            year = year_section.get(0).text();
                            //logger.debug("year " + year);
                            papr.setYear(Integer.parseInt(year));
                        } catch (Exception e) {
                            year = "";
                            logger.debug("year " + year);
                        }


                    }
                    logger.debug(papr.getUrl());
                    logger.debug(papr.getTitle());
                    logger.debug(papr.getNumCites());
                    logger.debug(papr.getYear());
                    logger.debug(papr.getcitedByUrl());
                    logger.debug(papr.getAbstract());
                    papers.add(papr);
                }
                
            }


        }
        

        items = doc.select(".g-section");
        logger.debug("items " + items.isEmpty());
        ArrayList<String> co_authors = new ArrayList<String>();
        ArrayList<String> co_authors_links = new ArrayList<String>();
        ArrayList<Author> co_author_list = new ArrayList<Author>();
        if (!items.isEmpty()) {
            try {
                Element co_author_section = items.get(2);
                Elements a_tags = co_author_section.select("a");
                if (!a_tags.isEmpty()) {
                    for (Element a_tag : a_tags) {
                        
                        String link = url + a_tag.attr("href");
                        co_authors_links.add(link);
                        co_authors.add(a_tag.text());
                        //to prevent view all co-authors link to be returned in result
                        if(!a_tag.text().equals("View all co-authors")){
                            Author authr = new Author(a_tag.text());
                            authr.setProfilelink(link);
                            co_author_list.add(authr);
                        }
                        
                    }

                }
            } catch (Exception e) {
                System.out.println("exception occured in co_author section");

            }


        }
        
        //logger.debug(co_authors_links);
        //logger.debug(co_authors);
        
        for(Author co_author : co_author_list){
            logger.debug(co_author.getName());
            logger.debug(co_author.getProfileLink());
        }
        
        pc.setPapers(papers);
        author.setPaperCollection(pc);
        author.setCoAuthors(co_author_list);
        qr_author_result.setContents(author);
        //qr_author_result.setContents(ar);
        return qr_author_result;


    }

    public static QueryResult<Author> getAuthors(String input) {

        QueryResult<ArrayList<Author>> q = new AuthorListResult(); 
        logger.debug("############## " + q.getContents());
        //AuthorResult alr = new AuthorResult();
        ArrayList<Author> authorList = new ArrayList<>();
        int citations;
        String name, imglink;
        String url,userid;
        String details, university;
        String[] parseddetails;
        doc = Jsoup.parse(input, "UTF-8");
        Elements elements = doc.select("div.g-unit");
        logger.debug(elements.size());
        for (Element item : elements) {
            Author author = new Author("");

            details = item.text();
            imglink = "scholar.google.co.in" + item.select("img").get(0).attr("src");
            Elements links = item.select("a.cit-dark-large-link");
            Element link = links.get(0);
            url = "scholar.google.co.in" + link.attr("href");
            name = link.text();
            parseddetails = details.split(" ");
            try {
                citations = Integer.parseInt(parseddetails[parseddetails.length - 1]);
                university = details.substring(name.length(), details.length() - 9 - Integer.toString(citations).length());
            } catch (Exception e) {
                citations = 0;
                university = details.substring(details.length() - name.length());
            }
            String str ;
            str= url.split("user=")[0];
            //userid = url.substring(str.length()+5,str.split("&")[]);
            author.setName(name);
            author.setUniversity(university);
            author.setTotalCitations(citations);
            author.setImagesrc(imglink);
            author.setProfilelink(url);
            authorList.add(author);
        }

        //alr.setContents(authorList);

        for (Author author : authorList) {
            logger.debug("img src:" + author.getImageSrc());
            logger.debug("url:" + author.getProfileLink());
            logger.debug("name:" + author.getName());//name
            logger.debug("univ:" + author.getUniversity());
            logger.debug("citations:" + author.getTotalCitations());
        }
        q.setContents(authorList);
        return null; 
    }
}
