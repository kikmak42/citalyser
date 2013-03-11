/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.parsing;
import citalyser.api.*;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 *
 * @author Abhishek Gupta
 */
public class Extractdata {
    public String source;
    public String type;
    static Document doc;
    private ArrayList<Paper> extractedpapers;
    public Extractdata(String source,String type){
        this.source=source;
        this.type=type;
    }
    //Author a= new Author("dvc");
    
    public static void main(String args[]){
         
        String source= "<div class=\"gs_ri\">\n" +
"        <h3 class=\"gs_rt\">\n" +
"         <a href=\"http://www.springerlink.com/index/L215116G22366720.pdf\" onmousedown=\"return scife_clk(this.href,'','res','0')\">\n" +
"          Investigation and modeling of the structure of texting language\n" +
"         </a>\n" +
"        </h3>\n" +
"        <div class=\"gs_a\">\n" +
"         <a href=\"/citations?user=WR1ImCMAAAAJ&amp;hl=en&amp;oi=sra\">\n" +
"          M Choudhury\n" +
"         </a>\n" +
"         , R Saraf, V Jain,\n" +
"         <a href=\"/citations?user=lf7-deEAAAAJ&amp;hl=en&amp;oi=sra\">\n" +
"          A\n" +
"          <b>\n" +
"           Mukherjee\n" +
"          </b>\n" +
"         </a>\n" +
"         … - International Journal on  …, 2007 - Springer\n" +
"        </div>\n" +
"        <div class=\"gs_rs\">\n" +
"         Abstract Language usage over computer mediated discourses, such as chats, emails and\n" +
"         <br/>\n" +
"         SMS texts, significantly differs from the standard form of the language and is referred to as\n" +
"         <br/>\n" +
"         texting language (TL). The presence of intentional misspellings significantly decrease the\n" +
"         <b>\n" +
"          ...\n" +
"         </b>\n" +
"        </div>\n" +
"        <div class=\"gs_fl\">\n" +
"         <a href=\"/scholar?cites=16427421465182510468&amp;as_sdt=2005&amp;sciodt=0,5&amp;hl=en\">\n" +
"          Cited by 63\n" +
"         </a>\n" +
"         <a href=\"/scholar?q=related:hJ3UT8ns-eMJ:scholar.google.com/&amp;hl=en&amp;as_sdt=0,5\">\n" +
"          Related articles\n" +
"         </a>\n" +
"         <a class=\"gs_nph\" href=\"http://direct.bl.uk/research/32/00/RN220411230.html?source=googlescholar\" onmousedown=\"return scife_clk(this.href,'','docdel','0')\">\n" +
"          BL Direct\n" +
"         </a>\n" +
"         <a href=\"/scholar?cluster=16427421465182510468&amp;hl=en&amp;as_sdt=0,5\">\n" +
"          All 11 versions\n" +
"         </a>\n" +
"         <a class=\"gs_nph\" href=\"#\" onclick=\"return gs_ocit(event,'hJ3UT8ns-eMJ')\">\n" +
"          Cite\n" +
"         </a>\n" +
"        </div>\n" +
"       </div>\n" +
"      </div>\n" +
"      <div class=\"gs_r\" style=\"z-index:399\">\n" +
"       <div class=\"gs_ggs gs_fl\">\n" +
"        <button class=\"gs_btnFI gs_in_ib gs_btn_half\" id=\"gs_ggsB1\" type=\"button\">\n" +
"         <span class=\"gs_wr\">\n" +
"          <span class=\"gs_bg\">\n" +
"          </span>\n" +
"          <span class=\"gs_lbl\">\n" +
"          </span>\n" +
"          <span class=\"gs_ico\">\n" +
"          </span>\n" +
"         </span>\n" +
"        </button>\n" +
"        <div class=\"gs_md_wp\" id=\"gs_ggsW1\">\n" +
"         <a href=\"http://arxiv.org/pdf/cond-mat/0703634\" onmousedown=\"return scife_clk(this.href,'gga','gga','1')\">\n" +
"          <span class=\"gs_ggsL\">\n" +
"           <span class=\"gs_ctg2\">\n" +
"            [PDF]\n" +
"           </span>\n" +
"           from arxiv.org\n" +
"          </span>\n" +
"          <span class=\"gs_ggsS\">\n" +
"           arxiv.org\n" +
"           <span class=\"gs_ctg2\">\n" +
"            [PDF]\n" +
"           </span>\n" +
"          </span>\n" +
"         </a>\n" +
"        </div>\n" +
"       </div>\n" +
"       <div class=\"gs_ri\">\n" +
"        <h3 class=\"gs_rt\">\n" +
"         <a href=\"http://iopscience.iop.org/0295-5075/79/2/28001\" onmousedown=\"return scife_clk(this.href,'','res','1')\">\n" +
"          Emergence of a non-scaling degree distribution in bipartite networks: A numerical and analytical study\n" +
"         </a>\n" +
"        </h3>\n" +
"        <div class=\"gs_a\">\n" +
"         F Peruani,\n" +
"         <a href=\"/citations?user=WR1ImCMAAAAJ&amp;hl=en&amp;oi=sra\">\n" +
"          M Choudhury\n" +
"         </a>\n" +
"         ,\n" +
"         <a href=\"/citations?user=lf7-deEAAAAJ&amp;hl=en&amp;oi=sra\">\n" +
"          A\n" +
"          <b>\n" +
"           Mukherjee\n" +
"          </b>\n" +
"         </a>\n" +
"         … - EPL (Europhysics  …, 2007 - iopscience.iop.org\n" +
"        </div>\n" +
"        <div class=\"gs_rs\">\n" +
"         Abstract. We study the growth of bipartite networks in which the number of nodes in one of\n" +
"         <br/>\n" +
"         the partitions is kept fixed while the other partition is allowed to grow. We study random and\n" +
"         <br/>\n" +
"         preferential attachment as well as combination of both. We derive the exact analytical\n" +
"         <b>\n" +
"          ...\n" +
"         </b>\n" +
"        </div>\n" +
"        <div class=\"gs_fl\">\n" +
"         <a href=\"/scholar?cites=13774284227352465688&amp;as_sdt=2005&amp;sciodt=0,5&amp;hl=en\">\n" +
"          Cited by 27\n" +
"         </a>\n" +
"         <a href=\"/scholar?q=related:GBGchoYWKL8J:scholar.google.com/&amp;hl=en&amp;as_sdt=0,5\">\n" +
"          Related articles\n" +
"         </a>\n" +
"         <a href=\"/scholar?cluster=13774284227352465688&amp;hl=en&amp;as_sdt=0,5\">\n" +
"          All 19 versions\n" +
"         </a>\n" +
"         <a class=\"gs_nph\" href=\"#\" onclick=\"return gs_ocit(event,'GBGchoYWKL8J')\">\n" +
"          Cite\n" +
"         </a>\n" +
"        </div>\n" +
"       </div>\n" +
"      </div>\n" +
"      <div class=\"gs_r\" style=\"z-index:398\">\n" +
"       <div class=\"gs_ggs gs_fl\">\n" +
"        <button class=\"gs_btnFI gs_in_ib gs_btn_half\" id=\"gs_ggsB2\" type=\"button\">\n" +
"         <span class=\"gs_wr\">\n" +
"          <span class=\"gs_bg\">\n" +
"          </span>\n" +
"          <span class=\"gs_lbl\">\n" +
"          </span>\n" +
"          <span class=\"gs_ico\">\n" +
"          </span>\n" +
"         </span>\n" +
"        </button>\n" +
"        <div class=\"gs_md_wp\" id=\"gs_ggsW2\">\n" +
"         <a href=\"http://acl.ldc.upenn.edu/P/P06/P06-2017.pdf\" onmousedown=\"return scife_clk(this.href,'gga','gga','2')\">\n" +
"          <span class=\"gs_ggsL\">\n" +
"           <span class=\"gs_ctg2\">\n" +
"            [PDF]\n" +
"           </span>\n" +
"           from upenn.edu\n" +
"          </span>\n" +
"          <span class=\"gs_ggsS\">\n" +
"           upenn.edu\n" +
"           <span class=\"gs_ctg2\">\n" +
"            [PDF]\n" +
"           </span>\n" +
"          </span>\n" +
"         </a>\n" +
"        </div>\n" +
"       </div>";
        Extractdata exd=new Extractdata(source, null);
        exd.extractInfo();
        
        
            
    }
    
    //this function takes the title of a paper and the source string and returns an arraylist of authors of that paper
    void extractInfo(){
        Paper insertInextractedpapers = new Paper();
        doc = Jsoup.parse(source,"UTF-8");
        Elements items = doc.select(".gs_r");//select all items 
        for (Element item : items){
            //extracting title section
            Elements title_section = item.select("h3>a");
            if(!title_section.isEmpty()){
                Element section= title_section.get(0);//getting the first element of this array
                String title = section.text();
                String href = section.attr("href");   
                System.out.println("title is"+title+"\n");
                System.out.println("link under title is "+href+"\n");
               
            }
            
            //extracting pdf section
            Elements pdf_section = item.select(".gs_ggs > a");
            if(!pdf_section.isEmpty()){
                Element section = pdf_section.get(0);
                String pdf = section.attr("href");
                System.out.println("pdf link is"+pdf+"\n");
                             
                
            }
            
            //extracting the authors
            Elements author_section_a = item.select(".gs_a > a");
            for(Element author : author_section_a){
                String author_name = author.text();//getting the author name
                String url = "http://scholar.google.com";
                String citations_link = url+author.attr("href");               
                
            }
            
            Elements author_section_b = item.select(".gs_a");
            if(!author_section_b.isEmpty()){
                Element section = author_section_b.get(0);
                String section_text = section.text();
                String[]list=section_text.split("-");
                String names = list[0];
                String conference = list[1];
                String publisher = list[2];
                
                
            }
            
            
        }
        
        
        
        
        
        
    }
    
    
}
