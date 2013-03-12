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

            Elements author_section_b = item.select(".gs_a");
            if (!author_section_b.isEmpty()) {
                Element section = author_section_b.get(0);
                String section_text = section.text();
                String[] list = section_text.split(" - … ,? | - ");
                System.out.println("section text :" + section_text);
                System.out.println(Arrays.toString(list));
                String names = list[0];
                System.out.println("list 0 :" + list[0]);
                System.out.println("list 1 :" + list[1]);
                String jrnl = list[1].split(", ")[0];
                journalinpaper.setName(jrnl);
                journalsinPaper.add(journalinpaper);
                System.out.println("journal:" + jrnl);

                insertInextractedpapers.setJournals(journalsinPaper);

                try {
                    String year = list[1].split("…, ")[1];
                    System.out.println("year is:" + year + ":");
                    insertInextractedpapers.setYear(Integer.parseInt(year));
                } catch (Exception e) {
                    String year = list[1].split(", ")[1];
                    System.out.println("year is:" + year + ":");
                }
                String publisher = list[2];

                System.out.println("Here:" + list[2]);
                String[] author_names = names.split(",|…");
                for (String nameinarray : author_names) {

                    authorinpaper.setName(nameinarray);
                    authorsinPaper.add(authorinpaper);
                }
                insertInextractedpapers.setAuthors(authorsinPaper);
                System.out.println("Authors:  " + Arrays.toString(author_names));


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
                try{
                    citation_count = section.text().split(" ")[2];
                }
                catch(Exception e){
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
                String citations_link = url + section.attr("href");
                String citation_count = section.text().split(" ")[2];
                System.out.println("citation count:" + citation_count);
                System.out.println("citation link:" + citations_link);
                citedbyList.add(citations_link);

            }
        }
        return citedbyList;
    }
}
