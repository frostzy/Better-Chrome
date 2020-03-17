import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import webPackages.WebNode;
import webPackages.WebPage;
import webPackages.WebTree;

public class GoogleCounting 
{
	
			public String mainUrl,content,keyword;
			public int score;
			public ArrayList<String> urls;
			public GoogleCounting(String url,String keyword) throws IOException  //輸入google後關鍵字後出現大標題網站,原關鍵字
			{
				
				this.mainUrl=url;
				this.content="";
				this.keyword = keyword;
				urls = new ArrayList<String>();
				findSubLink(); 		//找出子網站, 從大標題網站找其內部"http:"為首連結
				countUrlScore();
				
				
			}
			
			public void countUrlScore() throws IOException
			{
				WebPage rootPage = new WebPage(mainUrl); //Trees
				WebTree tree = new WebTree(rootPage);
				try 
				{
					for(int x=0;x<=urls.size()-1;x++)             //選擇子網站數量
					{
						tree.root.addChild(new WebNode(new WebPage(urls.get(x))));
					}
					tree.setPostOrderScore(keyword);
				}
				catch(Exception e) {}
				score =tree.getNodeScore();
			}
			
			
			public void findSubLink() throws IOException
			{
				
		            URL url = new URL(mainUrl);
		            URLConnection conn = url.openConnection();
		  
// 				open the stream and put it into BufferedReader
//		            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
//		            Jsoup.connect(mainUrl).timeout(30000).userAgent(TestProject.ua).validateTLSCertificates(false).get();
		            
		            /*下面網站會出現憑證問題 故丟例外處理略過*/
		            try {
		            
		        	 BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
           
		        	 String retVal = "";
		        	 String line = null;

		    		while ((line = br.readLine()) != null){retVal = retVal + line + "\n";}
		          

		    		Document docs = Jsoup.parse(retVal);	
		    		Elements lis = docs.select("div");
		    		for(Element li : lis)
		    		{
		    			String link =  li.select("a").attr("href");
		    			if(link.contains("http:"))
		    			{			
		    				urls.add(URLDecoder.decode(link, "UTF-8"));    	
		    			}
		    		}
		            }
		            catch(Exception e) {System.out.println(e);} //例外處理
		           
		    		
		    		arrayCheck();//檢查子網站是否有重複網址 並過濾

			}
		

			public void arrayCheck()
			{
				for(int x=urls.size()-1; x>=1;x--)
				{
					String value_a = urls.get(x);
				    String value_b = urls.get(x-1);
					if(value_a.equals(value_b))
					{
						urls.remove(urls.get(x));
					}
				}
			}
	
	
		

	
	
	
}
	
