/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//i have given the path of input.html in c:\input.html
package citalyser.dataextraction.parsing;

import citalyser.model.query.queryresult.*;
import citalyser.Constants;
import citalyser.model.Author;
import citalyser.model.Paper;
import citalyser.model.PaperCollection;
import citalyser.model.Journal;
import citalyser.model.query.QueryResult;
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
    public String source;
    Document doc;
    private PaperCollection extractedPapers;
    private ArrayList<Paper> papers;
    public ArrayList<String> citedbyList;
    private String names;

    //this function takes the title of a paper and the source string and returns an arraylist of authors of that paper
    /* Query Types : GEN_AUTH, GEN_JOURN */
    public QueryResult<PaperCollection> extractGeneralQuery(String source) {
        QueryResult<PaperCollection> q = new PaperCollectionResult();
        this.source = source;
        extractedPapers = new PaperCollection();
        papers = new ArrayList<>();
        citedbyList = new ArrayList<>();
        String citations_link = "";

        String jrnl;
        String year;
        int yearint;
        doc = Jsoup.parse(source, "UTF-8");
        Elements items = doc.select(".gs_ri");//select all items 
        for (Element item : items) {
            //extracting title section
            ArrayList<Author> authorsinPaper = new ArrayList<>();
            ArrayList<Journal> journalsinPaper = new ArrayList<>();

            Paper insertInextractedpapers = new Paper();
            Journal journalinpaper = new Journal("");


            Elements title_section = item.select("h3>a");
            if (!title_section.isEmpty()) {
                try{
                Element section = title_section.get(0);//getting the first element of this array
                String title = section.text();
                String href = section.attr("href");
                insertInextractedpapers.setTitle(title);
                insertInextractedpapers.setUrl(href);
                }catch(Exception ex){
                    insertInextractedpapers.setTitle("");
                    insertInextractedpapers.setUrl("");
                }
            }


/////////////////////////////////////////////////////`///////////////////////////////////////////////
            Elements author_section_b = item.select(".gs_a");
            if (!author_section_b.isEmpty()) {
                Element section = author_section_b.get(0);
                String section_text = section.text();
                insertInextractedpapers.setInfo(section_text);
                String[] list = section_text.split(" - … ,? | - ");
                int len = list.length;

                if (len == 3) {

                    names = list[0];
                    // = list[1].split(", ")[0];
                    jrnl = ParserUtils.splitjournal_year(list[1], 1);
                    journalinpaper.setName(jrnl);
                    journalsinPaper.add(journalinpaper);
                    insertInextractedpapers.setJournals(journalsinPaper);
                    year = ParserUtils.splitjournal_year(list[1], 2);
                    yearint = Integer.parseInt(year);
                    String[] author_names = names.split(",|…");
                    for (String nameinarray : author_names) {
                        Author authorinpaper = new Author(null);
                        authorinpaper.setName(nameinarray);
                        authorsinPaper.add(authorinpaper);
                    }
                } /////////////////////////////////////////////////////////////////////////////
                else {//len ==2
                    String nameList = list[0];
                    String[] author_names = nameList.split(",|…");
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

        q.setContents(extractedPapers);
        return q;

    }

    /* Query_Type : AUTH_PROF*/
    public QueryResult<Author> extractAuthorProfileInfo(String src) {
        QueryResult<Author> qr_author_result = new AuthorResult();
        //AuthorResult ar =new AuthorResult();
        Author author = new Author(null);
        PaperCollection pc = new PaperCollection();
        ArrayList<Paper> paperList = new ArrayList<>();
        String graphurl = "";
        doc = Jsoup.parse(src, "UTF-8");
        Elements it = doc.select("div.cit-lbb");
        if (!it.isEmpty()) {
            graphurl = it.select("img").get(0).attr("src");
        }
        //logger.debug("@@@##"+graphurl);
        author.setGraphurl(graphurl);
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
                        String authorNames;
                        String journal;
                        try {
                            authors_list = desc_section.get(0).text();
                            String[] author_names = authors_list.split(", ");
                            authorNames = Arrays.toString(author_names);
                            for (String name : author_names) {
                                Author paper_author = new Author(name);
                                paper_authors.add(paper_author);
                            }
                        } catch (Exception e) {
                            authorNames = "";
                            Author paper_author = new Author(authorNames);
                            paper_authors.add(paper_author);
                        }
                        try {
                            journal = desc_section.get(1).text();
                            Journal jrnl = new Journal(journal);
                            paper_jrnl.add(jrnl);
                        } catch (Exception e) {
                            journal = "";
                            Journal jrnl = new Journal(journal);
                            paper_jrnl.add(jrnl);
                        }
                        // debug("@@@@"+paper_jrnl.get(0).getName() );
                    }
                    papr.setJournals(paper_jrnl);
                    papr.setAuthors(paper_authors);

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
                    paperList.add(papr);
                }
            }
        }
        items = doc.select(".g-section");
        ArrayList<String> co_authors = new ArrayList<>();
        ArrayList<String> co_authors_links = new ArrayList<>();
        ArrayList<Author> co_author_list = new ArrayList<>();
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
                //logger.debug("Error getting author profile info");
            }


        }


        //this part extracts the hindex i index and chart src for an author
        items = doc.select("#stats");
        ArrayList<String> ar = new ArrayList<>();
        String imglink = "";
        String citations_all = "";
        String citations_since_2008 = "";
        String hindex_all = "";
        String hindex_since_2008 = "";
        String i10index_all = "";
        String i10index_since_2008 = "";
        if (!items.isEmpty()) {
            for (Element item : items) {
                Elements tds = item.select("td.cit-data");
                if (!tds.isEmpty()) {
                    //logger.debug(tds.text());
                    for (Element td : tds) {

                        //logger.debug(td.text());
                        ar.add(td.text());
                    }
                }
            }

            try {
                citations_all = ar.get(0);
                //logger.debug("Citations (All)  :"+ar.get(0));
                author.setTotalCitations(Integer.parseInt(citations_all));
            } catch (Exception e) {
                citations_all = "";
            }
            try {
                citations_since_2008 = ar.get(1);
                //logger.debug("Citations (Since 2008)  :"+ar.get(1));
            } catch (Exception e) {
                citations_since_2008 = "";
            }
            try {
                hindex_all = ar.get(2);
                //logger.debug("h-index (All)  :"+ar.get(2));
                //logger.debug("hindex from author object"+author.getHindex());
                author.setHindex(Integer.parseInt(hindex_all));
                //logger.debug("hindex from author object"+author.getHindex());
            } catch (Exception e) {
                hindex_all = "";
                //logger.debug("in exception");
            }
            try {
                hindex_since_2008 = ar.get(3);
                //logger.debug("h-index (Since 2008)  :"+ar.get(3));
            } catch (Exception e) {
                hindex_since_2008 = "";
            }
            try {
                i10index_all = ar.get(4);
                //logger.debug("i10-index (All)  :"+ar.get(4));
                author.setIIndex(Integer.parseInt(i10index_all));
                //logger.debug("Iindex from author object"+author.getIIndex());
            } catch (Exception e) {
                i10index_all = "";
            }
            try {
                i10index_since_2008 = ar.get(5);
                //logger.debug("i10-index (Since 2008)  :"+ar.get(5));
            } catch (Exception e) {
                i10index_since_2008 = "";
            }
            //logger.debug("img src  :"+imglink);

            items = doc.select("div.cit-lbb");
            if (!items.isEmpty()) {
                //logger.debug(items.isEmpty());
                Elements tds = items.select("td");
                //logger.debug(tds);
                if (!tds.isEmpty()) {
                    for (Element td : tds) {
                        Elements img_tags = td.select("img");
                        if (!img_tags.isEmpty()) {
                            try {
                                Element img_tag = img_tags.get(0);
                                imglink = img_tag.attr("src");
                                //logger.debug("img src "+imglink);
                            } catch (Exception e) {
                                imglink = "";
                                //logger.debug(imglink);
                            }
                        }

                    }


                }
            }
        }
        String email = "";
        String affiliation = "";
        String homepage_url = "";
        String interests = "";
        //this part extracts the author name and pesonal info
        items = doc.select("div.cit-user-info");
        String name = "";
        if (!items.isEmpty()) {
            for (Element item : items) {
                //for extracting the author name
                Elements name_spans = item.select("span#cit-name-read");
                if (!name_spans.isEmpty()) {
                    try {
                        name = name_spans.get(0).text();
                        //logger.debug(name);
                        author.setName(name);//setting the author name
                    } catch (Exception e) {
                        name = "";
                    }

                }

                //for extracting the affiliation
                affiliation = "";
                Elements aff_spans = item.select("span#cit-affiliation-read");
                if (!aff_spans.isEmpty()) {
                    try {
                        affiliation = aff_spans.get(0).text();
                        //logger.debug(affiliation);

                    } catch (Exception e) {
                        affiliation = "";
                    }

                }

                //for extracting the email of the author

                Elements email_spans = item.select("span#cit-domain-read");
                if (!email_spans.isEmpty()) {
                    try {
                        String[] email_str = email_spans.get(0).text().split(" ");
                        email = email_str[email_str.length - 1];//last element will be email
                        //logger.debug(email);
                        author.setEmail(email);
                    } catch (Exception e) {
                        email = "";
                    }

                }

                //for extracting the homepage url of the author

                Elements homepage_spans = item.select("span#cit-homepage-read");
                if (!homepage_spans.isEmpty()) {

                    Elements a_tags = homepage_spans.get(0).select("a");
                    if (!a_tags.isEmpty()) {
                        try {
                            homepage_url = a_tags.get(0).attr("href");
                            //logger.debug(url);
                            //author.setProfilelink(src);
                        } catch (Exception e) {
                            homepage_url = "";

                        }
                    }


                }

                //for extracting the research fields of the author
                String fields = "";
                ArrayList<String> author_interests = new ArrayList<>();
                Elements field_spans = item.select("span#cit-int-read");
                if (!field_spans.isEmpty()) {
                    try {
                        interests = field_spans.get(0).text();
                        String[] fields_str = field_spans.get(0).text().split("-");
                        for (String str : fields_str) {
                            author_interests.add(str);
                            logger.debug(str);
                        }
                        author.setAuthorAreas(author_interests);
                    } catch (Exception e) {
                        fields = "";
                    }

                }
                //extracting the author image link
                String img_src = "";
                Elements img_tags = item.select("img");
                if (!img_tags.isEmpty()) {
                    try {
                        img_src = url + img_tags.get(0).attr("src");
                        logger.debug("image source " + img_src);
                        author.setImagesrc(img_src);
                    } catch (Exception e) {
                        img_src = "";
                    }

                }

            }

        }
        String Description = "";
        if (affiliation != null && !affiliation.equals("")) {
            Description = Description + affiliation;

        }
        if (email != null && !email.equals("")) {
            Description = Description + "<br>" + email;

        }
        if (interests != null && !interests.equals("")) {
            Description = Description + "<br>" + interests;

        }
        if (homepage_url != null && !homepage_url.equals("")) {
            Description = Description + "<br>" + homepage_url;

        }
        //logger.debug("desc "+Description);
        author.setDescription(Description);


        pc.setPapers(paperList);
        author.setPaperCollection(pc);
        author.setCoAuthors(co_author_list);
        qr_author_result.setContents(author);
        //qr_author_result.setContents(ar);

        return qr_author_result;


    }

    /* Query_Type : MET_AUTH */
    public QueryResult<ArrayList<Author>> getAuthorList(String input) {

        QueryResult<ArrayList<Author>> q = new AuthorListResult();
        //AuthorResult alr = new AuthorResult();
        ArrayList<Author> authorList = new ArrayList<>();
        int citations;
        String name, imglink;
        String url, userid;
        String details = "", university = "";
        String[] parseddetails;
        doc = Jsoup.parse(input, "UTF-8");
        String URL = "http://scholar.google.com";

        //this section extracts the next link from each page
        Elements items = doc.select("div.cit-dgb");
        String next_link = "";
        if (!items.isEmpty()) {
            try {
                Element div_section = items.get(0);
                Elements a_tags = div_section.select("a");
                if (!a_tags.isEmpty()) {
                    try {
                        Element a_tag = a_tags.get(1);
                        String text = a_tag.text().split(">")[0].trim();
                        if (text.equals("Next")) {
                            next_link = URL + a_tag.attr("href");
                            //logger.debug("next link is "+next_link);
                        }
                    } catch (Exception e) {
                        try {
                            Element a_tag = a_tags.get(0);
                            //logger.debug("into next link try\n");
                            String text = a_tag.text().split(">")[0].trim();
                            //logger.debug("compareison of "+text.equals("Next"));
                            //logger.debug(a_tag.text().trim().split(">")[0]);
                            if (text.equals("Next")) {
                                //logger.debug("into if condition\n");
                                next_link = URL + a_tag.attr("href");
                                //logger.debug("next link is "+next_link);
                            } else {
                                next_link = "";
                            }
                        } catch (Exception e1) {
                            next_link = "";
                        }

                    }

                }

            } catch (Exception e) {
            }

        }

        Elements elements = doc.select("div.g-unit");
        for (Element item : elements) {
            Author author = new Author("");
            author.setNextLink(next_link);
            // logger.debug(item.html());
            university = item.html();
            university = university.split("<td")[2].split("hl=en\">")[1].split("Cited|<form|<input")[0];

            details = item.text();
            // logger.debug(university);
            // logger.debug("\n@@@@:" + item.select("a.cit-dark-large-link").outerHtml());
            imglink = Constants.SCHOLAR_BASE_URL + item.select("img").get(0).attr("src");
            Elements links = item.select("a.cit-dark-large-link");
            Element link = links.get(0);
            url = "scholar.google.co.in" + link.attr("href");
            name = link.text();
            parseddetails = details.split(" ");
            try {
                citations = Integer.parseInt(parseddetails[parseddetails.length - 1]);
                //university = details.substring(name.length(), details.length());
            } catch (Exception e) {
                citations = 0;
                //university = details.substring(details.length() - name.length());
            }
            String str;
            str = url.split("user=")[1];
            userid = str.split("&")[0];
            //logger.debug("details =" + details);
            author.setName(name);
            author.setId(userid);
            author.setUniversityAndEmail(university);
            author.setTotalCitations(citations);
            author.setImagesrc(imglink);
            author.setProfilelink(url);
            authorList.add(author);
        }


        q.setContents(authorList);
        return q;
    }

    /* Query_Type : JOURN_PROF*/
    public QueryResult<Journal> extractMetricJournalInfo(String src) {
        doc = Jsoup.parse(src, "UTF-8");
        QueryResult<Journal> qj = new JournalResult();
        Journal journal = new Journal("");
        PaperCollection pc = new PaperCollection();
        ArrayList<Paper> papcol = new ArrayList<Paper>();
        Elements title_sections = doc.select("div#gs_m_title");
        String pub_name = "";
        if (!title_sections.isEmpty()) {
            try {
                pub_name = title_sections.get(0).text();
            } catch (Exception e) {
                pub_name = "";
            }
        }
        journal.setName(pub_name);
        Elements items = doc.select("table#gs_cit_list_table");
        String url = "http://scholar.google.com";
        if (!items.isEmpty()) {
            for (Element item : items) {
                //Elements rows = item.select("tr.cit-table item");
                Elements rows = item.select("tr");  //extract all the rows in each such table
                int count = 0;

                if (!rows.isEmpty()) {
                    for (Element row : rows) {
                        count++;
                        if (count == 1) {
                            continue;   //if first row then skip because it contains just the headers
                        }
                        //extracting the paper title and link of paper
                        //creating a paper object
                        Paper papr = new Paper();
                        String title_link = "";
                        String title_name = "";
                        String authors_list = "";
                        String authorNames = "";
                        String desc = "";
                        String cited_by_count = "0";
                        String cited_by_link = "";
                        String year = "0";
                        ArrayList<Journal> jrnl = new ArrayList<>();
                        ArrayList<Author> paper_authors = new ArrayList<>();
                        //this part is for extracting the journal title and link
                        Elements title_section = row.select("td.gs_title");
                        if (!title_section.isEmpty()) {
                            Elements title_tags;
                            try {
                                title_tags = title_section.get(0).select("a");
                            } catch (Exception e) {
                                title_tags = null;
                            }
                            //this part extracts the title and the link of the publication
                            if (!title_tags.isEmpty()) {

                                try {
                                    title_link = title_tags.get(0).attr("href");
                                    title_name = title_tags.get(0).text();
                                    papr.setTitle(title_name);
                                    papr.setUrl(title_link);
                                } catch (Exception e) {
                                    title_link = "";
                                    title_name = "";
                                }
                            }
                        }
                        papr.setTitle(title_name);
                        papr.setUrl(title_link);
                        //extracting the names of the authors
                        Elements author_section = row.select("span.gs_authors");
                        if (!author_section.isEmpty()) {


                            try {
                                authors_list = author_section.get(0).text();
                                String[] author_names = authors_list.split(",");
                                authorNames = Arrays.toString(author_names);
                                for (String name : author_names) {
                                    name = name.replaceAll("\\.+", " "); //to remove all leading or trailing dots from a name
                                    Author paper_author = new Author(name);
                                    paper_authors.add(paper_author);
                                }
                            } catch (Exception e) {
                                authorNames = "";
                                Author paper_author = new Author(authorNames);
                                paper_authors.add(paper_author);
                            }
                        }
                        papr.setAuthors(paper_authors); //extracting the names of the authors
                        Elements desc_section = row.select("span.gs_pub");
                        if (!desc_section.isEmpty()) {
                            try {
                                desc = desc_section.get(0).text();

                            } catch (Exception e) {
                                desc = "";
                            }
                        }
                        Journal j = new Journal(desc);
                        jrnl.add(j);
                        papr.setJournals(jrnl);
                        //extracting the citation count and citedby link and year for the paper published in this journal
                        Elements cited_year_section = row.select("td.gs_num");
                        if (!cited_year_section.isEmpty()) {

                            Elements cited_by_tags;
                            try {
                                cited_by_count = cited_year_section.get(0).text();
                                cited_by_tags = cited_year_section.get(0).select("a");
                            } catch (Exception e) {
                                cited_by_tags = null;
                                cited_by_count = "0";
                                cited_by_link = "";
                            }
                            //this part extracts the cited by link of the paper
                            if (!cited_by_tags.isEmpty()) {
                                try {
                                    cited_by_link = url + cited_by_tags.get(0).attr("href");
                                    //papr.setTitle(title_name);
                                    //papr.setUrl(title_link);
                                } catch (Exception e) {
                                    cited_by_link = "";
                                }
                            }
                            try {
                                year = cited_year_section.get(1).text();
                            } catch (Exception e) {
                                year = "0";
                            }
                        }
                        papr.setYear(Integer.parseInt(year));
                        papr.setCitedByUrl(cited_by_link);
                        papr.setNumCites(Integer.parseInt(cited_by_count));
                        papcol.add(papr);
                    }

                }
                pc.setPapers(papcol);
            }
        }
        journal.setPaperCollection(pc);
        qj.setContents(journal);
//        for (Paper p : qj.getContents().getPaperCollection().getPapers()) {
//            logger.debug("@@@@:" + p.getTitle());
//            logger.debug("####:" + p.getAuthors().get(0).getName());
//            logger.debug("@@@:" + p.getcitedByUrl());
//            logger.debug("###:" + p.getYear());
//            logger.debug("@@:" + p.getNumCites());
//            logger.debug("##:" + p.getJournals().get(0).getName());
//        }
        return qj;
    }

    /* Query_Type : Met_JOURN*/
    public QueryResult<ArrayList<Journal>> extractJournalListFromMetric(String src) {
        QueryResult<ArrayList<Journal>> qjl = new JournalListResult();
        ArrayList<Journal> alj = new ArrayList<>();
        doc = Jsoup.parse(src, "UTF-8");
        String title = "";
        String h5link = "";
        String h5i = "0";
        String h5m = "0";
        Elements items = doc.select("table#gs_cit_list_table");
        if (!items.isEmpty()) {
            for (Element item : items) {
                //Elements rows = item.select("tr.cit-table item");
                Elements rows = item.select("tr");  //extract all the rows in each such table
                int count = 0;
                if (!rows.isEmpty()) {
                    for (Element row : rows) {
                        count++;
                        if (count == 1) {
                            continue;   //if first row then skip because it contains just the headers
                        }
                        title = row.select("td.gs_title").get(0).text();
                        try {
                            h5i = row.select("td.gs_num").get(0).text();
                        } catch (Exception e) {
                        }
                        try {
                            h5link = Constants.SCHOLAR_BASE_URL + row.select("td.gs_num").get(0).select("a").get(0).attr("href");
                        } catch (Exception e) {
                        }
                        try {
                            h5m = row.select("td.gs_num").get(1).text();
                        } catch (Exception e) {
                        }
                        Journal jour = new Journal(title);
                        jour.setH5Link(h5link);
                        jour.setH5index(Integer.parseInt(h5i));
                        jour.setH5median(Integer.parseInt(h5m));
                        alj.add(jour);
                    }

                }
            }
        }
        qjl.setContents(alj);
//        for(Journal j:alj){
//            logger.debug(j.getName());
//            logger.debug(j.getH5index()+"-"+j.getH5Link());
//            logger.debug(j.getH5median());
//        }
        return qjl;
    }

    public QueryResult<PaperCollection> extractCitationResultFromMetric(String src) {
        doc = Jsoup.parse(src, "UTF-8");
        QueryResult<PaperCollection> qj = new PaperCollectionResult();
        PaperCollection pc = new PaperCollection();
        ArrayList<Paper> papcol = new ArrayList<Paper>();


        Elements items = doc.select("table#gs_cit_list_table");
        String url = "http://scholar.google.com";
        if (!items.isEmpty()) {
            for (Element item : items) {
                //Elements rows = item.select("tr.cit-table item");
                Elements rows = item.select("tr");  //extract all the rows in each such table
                int count = 0;

                if (!rows.isEmpty()) {
                    for (Element row : rows) {
                        count++;
                        if (count == 1) {
                            continue;   //if first row then skip because it contains just the headers
                        }
                        //extracting the paper title and link of paper
                        //creating a paper object
                        Paper papr = new Paper();
                        papr.setIsFromMetric(true);
                        String title_link = "";
                        String title_name = "";
                        String authors_list = "";
                        String authorNames = "";
                        String desc = "";
                        String deta = "";
                        String cited_by_count = "0";
                        String cited_by_link = "";
                        String year = "0";
                        ArrayList<Journal> jrnl = new ArrayList<>();
                        ArrayList<Author> paper_authors = new ArrayList<>();
                        //this part is for extracting the journal title and link
                        Elements title_section = row.select("td.gs_title");
                        if (!title_section.isEmpty()) {
                            Elements title_tags;
                            try {
                                title_tags = title_section.get(0).select("a");
                            } catch (Exception e) {
                                title_tags = null;
                            }
                            //this part extracts the title and the link of the publication
                            if (!title_tags.isEmpty()) {

                                try {
                                    title_link = title_tags.get(0).attr("href");
                                    title_name = title_tags.get(0).text();
                                    papr.setTitle(title_name);
                                    papr.setUrl(title_link);
                                } catch (Exception e) {
                                    title_link = "";
                                    title_name = "";
                                }
                            }
                        }
                        papr.setTitle(title_name);
                        papr.setUrl(title_link);
                        //extracting the names of the authors
                        Elements author_section = row.select("span.gs_authors");
                        if (!author_section.isEmpty()) {


                            try {
                                authors_list = author_section.get(0).text();
                                String[] author_names = authors_list.split(",");
                                authorNames = Arrays.toString(author_names);
                                for (String name : author_names) {
                                    name = name.replaceAll("\\.+", " "); //to remove all leading or trailing dots from a name
                                    Author paper_author = new Author(name);
                                    paper_authors.add(paper_author);
                                }
                            } catch (Exception e) {
                                authorNames = "";
                                Author paper_author = new Author(authorNames);
                                paper_authors.add(paper_author);
                            }
                        }
                        deta += authors_list;
                        papr.setAuthors(paper_authors); //extracting the names of the authors
                        Elements desc_section = row.select("span.gs_pub");
                        if (!desc_section.isEmpty()) {
                            try {
                                desc = desc_section.get(0).text();

                            } catch (Exception e) {
                                desc = "";
                            }
                        }
                        Journal j = new Journal(desc);

                        jrnl.add(j);
                        papr.setJournals(jrnl);
                        //extracting the citation count and citedby link and year for the paper published in this journal
                        Elements cited_year_section = row.select("td.gs_num");
                        if (!cited_year_section.isEmpty()) {

                            Elements cited_by_tags = new Elements();
                            try {
                                cited_by_count = cited_year_section.get(0).text();
                                cited_by_tags = cited_year_section.get(0).select("a");
                                if(cited_by_tags.isEmpty())
                                    throw new Exception();
                            } catch (Exception e) {
                                cited_by_count = "0";
                                cited_by_link = "";
                            }
                            //this part extracts the cited by link of the paper
                            if (!cited_by_tags.isEmpty()) {
                                try {
                                    cited_by_link = url + cited_by_tags.get(0).attr("href");
                                    //papr.setTitle(title_name);
                                    //papr.setUrl(title_link);
                                } catch (Exception e) {
                                    cited_by_link = "";
                                }
                            }
                            try {
                                year = cited_year_section.get(1).text();
                            } catch (Exception e) {
                                year = cited_year_section.get(0).text();
                            }
                        }
                        if (!year.equals("0")) {
                            deta += "-" + year;
                        }
                        deta += "-" + desc;
                        papr.setInfo(deta);
                        papr.setYear(Integer.parseInt(year));
                        papr.setCitedByUrl(cited_by_link);
                        papr.setNumCites(Integer.parseInt(cited_by_count));
                        papcol.add(papr);
                    }

                }
                pc.setPapers(papcol);
            }
        }
        qj.setContents(pc);
        for (Paper p : qj.getContents().getPapers()) {
            logger.debug("@@@@:" + p.getTitle());
            logger.debug("####:" + p.getAuthors().get(0).getName());
            logger.debug("@@@:" + p.getcitedByUrl());
            logger.debug("###:" + p.getYear());
            logger.debug("@@:" + p.getNumCites());
            logger.debug("##:" + p.getJournals().get(0).getName());
        }
        return qj;

    }
}
