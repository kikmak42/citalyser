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

    private Logger logger = Logger.getLogger(Parser.class.getName());
    public String source;
    Document doc;
    private PaperCollection extractedPapers;
    private ArrayList<Paper> papers;
    public ArrayList<String> citedbyList;
    private String names;

    private String splitjournal_year(String string, int type) {
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

            }

        }
        return null;
    }

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


        Parser p = new Parser();
        p.extractInfo(returnValue);

    }

    //this function takes the title of a paper and the source string and returns an arraylist of authors of that paper
    public QueryResult<PaperCollection> extractInfo(String source) {
        QueryResult<PaperCollection> q = new PaperCollectionResult();
        this.source = source;
        extractedPapers = new PaperCollection();
        papers = new ArrayList<Paper>();
        citedbyList = new ArrayList<String>();
        String citations_link="";

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

            
/////////////////////////////////////////////////////`///////////////////////////////////////////////
            Elements author_section_b = item.select(".gs_a");
            if (!author_section_b.isEmpty()) {
                Element section = author_section_b.get(0);
                String section_text = section.text();
                String[] list = section_text.split(" - … ,? | - ");
                int len = list.length;

                if (len == 3) {

                    names = list[0];
                    // = list[1].split(", ")[0];
                    jrnl = splitjournal_year(list[1], 1);
                    journalinpaper.setName(jrnl);
                    journalsinPaper.add(journalinpaper);
                    insertInextractedpapers.setJournals(journalsinPaper);
                    year = splitjournal_year(list[1], 2);
                    yearint = Integer.parseInt(year);
                    String[] author_names = names.split(",|…");
                    for (String nameinarray : author_names) {
                        Author authorinpaper = new Author(null);
                        authorinpaper.setName(nameinarray);
                        authorsinPaper.add(authorinpaper);
                    }
                } /////////////////////////////////////////////////////////////////////////////
                else {//len ==2
                    String names = list[0];
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
                }


                Elements citation_section = item.select(".gs_fl > a");
                if (!author_section_b.isEmpty()) {
                    Element section_ = citation_section.get(0);
                    String url = "http://scholar.google.com";
                    try {
                        citations_link = url + section_.attr("href");
                        citedbyList.add(citations_link);
                    } catch (Exception e) {
                        citations_link = "";

                    }

                }

                insertInextractedpapers.setCitedByUrl(citations_link);
                insertInextractedpapers.setYear(yearint);
                insertInextractedpapers.setAuthors(authorsinPaper);


            }

            //extracting the abstract
            Elements abstract_section = item.select(".gs_rs");
            if (!abstract_section.isEmpty()) {
                Element section = abstract_section.get(0);
                String abstractstr = section.text();
                insertInextractedpapers.setAbstract(abstractstr);
            }

            //extracting the citation
            Elements citation_section = item.select(".gs_fl > a");
            if (!author_section_b.isEmpty()) {
                Element section = citation_section.get(0);
                String citation_count;
                try {
                    String[] str = section.text().split(" ");
                    if (str[0].equals("Cited")) {
                        citation_count = str[2];
                    } else {
                        citation_count = "0";
                    }
                } catch (Exception e) {
                    citation_count = "0";
                }
                insertInextractedpapers.setNumCites(Integer.parseInt(citation_count));

            }

            papers.add(insertInextractedpapers);

        }
        extractedPapers.setPapers(papers);
        for (Paper p : papers) {
            logger.debug(p.getcitedByUrl());
        }
        q.setContents(extractedPapers);
        return q;

    }

    public void extractProfileInfo(String file) {
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

    public QueryResult<Author> extractAuthorProfileInfo(String src) {
        QueryResult<Author> qr_author_result = new AuthorResult();
        //AuthorResult ar =new AuthorResult();
        Author author = new Author(null);
        PaperCollection pc = new PaperCollection();
        ArrayList<Paper> papers = new ArrayList<Paper>();

        doc = Jsoup.parse(src, "UTF-8");
        Elements items = doc.select("table.cit-table");
        String url = "http://scholar.google.com";

        if (!items.isEmpty()) {
            for (Element item : items) {

                //Elements rows = item.select("tr.cit-table item");
                Elements rows = item.select(".item");
                for (Element row : rows) {
                    //extracting the paper title and link of paper
                    //creating a paper object
                    Paper papr = new Paper();
                    ArrayList<Author> paper_authors = new ArrayList<>();
                    ArrayList<Journal> paper_jrnl = new ArrayList<>();
                    Elements title_section = row.select("td#col-title");
                    Elements title_tags = title_section.get(0).select("a");
                    if (!title_tags.isEmpty()) {
                        String title_link = url + title_tags.get(0).attr("href");
                        String title_name = title_tags.get(0).text();
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
                            for (String name : author_names) {
                                Author paper_author = new Author(name);
                                paper_authors.add(paper_author);
                            }

                        } catch (Exception e) {
                            names = "";
                        }

                        try {
                            journal = desc_section.get(1).text();
                            Journal jrnl = new Journal(journal);
                            paper_jrnl.add(jrnl);


                        } catch (Exception e) {
                            journal = "";
                        }




                    }

                    //extracting the citation count
                    Elements citation_section = row.select("td#col-citedby");
                    if (!citation_section.isEmpty()) {
                        String citation_count;
                        String cited_by_link;
                        try {
                            citation_count = citation_section.get(0).text();
                            papr.setNumCites(Integer.parseInt(citation_count));
                        } catch (Exception e) {
                            citation_count = "";
                        }

                        try {
                            cited_by_link = citation_section.get(0).select("a").attr("href");
                            papr.setCitedByUrl(cited_by_link);
                        } catch (Exception e) {
                            cited_by_link = "";
                        }

                    }

                    //extracting the publication year
                    Elements year_section = row.select("td#col-year");
                    if (!year_section.isEmpty()) {
                        String year;
                        try {
                            year = year_section.get(0).text();
                            papr.setYear(Integer.parseInt(year));
                        } catch (Exception e) {
                            year = "";
                        }


                    }
                    papers.add(papr);
                }

            }


        }


        items = doc.select(".g-section");
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
                        if (!a_tag.text().equals("View all co-authors")) {
                            Author authr = new Author(a_tag.text());
                            authr.setProfilelink(link);
                            co_author_list.add(authr);
                        }

                    }

                }
            } catch (Exception e) {
            }


        }



        pc.setPapers(papers);
        author.setPaperCollection(pc);
        author.setCoAuthors(co_author_list);
        qr_author_result.setContents(author);
        //qr_author_result.setContents(ar);
        return qr_author_result;


    }

    public QueryResult<ArrayList<Author>> getAuthors(String input) {

        QueryResult<ArrayList<Author>> q = new AuthorListResult();
        //AuthorResult alr = new AuthorResult();
        ArrayList<Author> authorList = new ArrayList<>();
        int citations;
        String name, imglink;
        String url, userid;
        String details, university;
        String[] parseddetails;
        doc = Jsoup.parse(input, "UTF-8");
        Elements elements = doc.select("div.g-unit");
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
            String str;
            str = url.split("user=")[1];
            userid = str.split("&")[0];
            logger.debug("Author Id = " + userid);
            author.setName(name);
            author.setId(userid);
            author.setUniversity(university);
            author.setTotalCitations(citations);
            author.setImagesrc(imglink);
            author.setProfilelink(url);
            authorList.add(author);
        }

        //alr.setContents(authorList);


        q.setContents(authorList);
        return q;
    }
}
