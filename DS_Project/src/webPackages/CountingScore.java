package webPackages;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class CountingScore 
{

	private String url,keyword;
	private int count;
	private  String retVal ;
	private String newsName;
	
	
	public CountingScore(String urls,String keyword) //給網址 關鍵字算次數
	{
		
	
		this.url=urls;
		this.keyword=keyword;
		this.newsName="";
		Connect();
	}
	
	private void Connect()
	{
        try {
            // get URL content

            String a=this.url;
            URL url = new URL(a);
            URLConnection conn = url.openConnection();
  
            // open the stream and put it into BufferedReader
            BufferedReader br = new BufferedReader(
                               new InputStreamReader(conn.getInputStream()));
            
           
            	
            
            retVal = "";
    		String line = null;
    		
    		while ((line = br.readLine()) != null)
    		{retVal = retVal + line + "\n";}

    		try {
    		Document doc = Jsoup.parse(retVal);
    		Elements h1Tag = doc.select("h1");
    		newsName = h1Tag.eachText().get(0);
    		}
    		catch(Exception e)
    		{}
    		
    		filter(retVal,keyword);
            br.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

	}
	
	private void filter(String text,String input)
	{

		int n=1;
		int i=0;
		for(i=0;i<text.length()-input.length()+1;i++)
		{
			if(text.charAt(i)==input.charAt(0)&&input.length()>1)
			{
			for(n=1;n<input.length();n++)
			{
				if(text.charAt(i+n)!=input.charAt(n))
				{
					break;
				}
				while(n==input.length()-1)
				{
				count++;
				break;
				}
			}
			}
			else if(text.charAt(i)==input.charAt(0)) {
				count++;
			}
		}

		
	}
	
	public String getName()
	{
		return newsName;
	}
	public int getCount() //回傳次數
	{
		return count;
	}
	
	
}
