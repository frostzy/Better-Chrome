import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;



import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;

import org.jsoup.nodes.Element;

import org.jsoup.select.Elements;




public class GoogleQuery 

{

	private String searchKeyword,url,content;
	public ArrayList<Rating> result;
	public ArrayList<Rating> rl;
	

	public GoogleQuery(String searchKeyword)

	{
		result = new ArrayList<Rating>();
		rl = new ArrayList<Rating>();
		this.searchKeyword = searchKeyword;
		this.url = "http://www.google.com/search?q="+searchKeyword+"+q=政治&oe=utf8&num=10";
	}

	public ArrayList<Rating>	getRl()
	{

		return rl;
	}
	

	public ArrayList<Rating> getRs(){return result;}

	private String fetchContent() throws IOException

	{
		String retVal = "";

		URL u = new URL(url);

		URLConnection conn = u.openConnection();

		conn.setRequestProperty("User-agent", "Chrome/7.0.517.44");

		InputStream in = conn.getInputStream();

		InputStreamReader inReader = new InputStreamReader(in,"utf-8");

		BufferedReader bufReader = new BufferedReader(inReader);
		String line = null;

		while((line=bufReader.readLine())!=null)
		{
			retVal += line;
		}
		return retVal;
	}
	
	public HashMap<String, String> query() throws IOException
	{

		if(content==null)
		{
			content= fetchContent();
		}
		
		Document doc = Jsoup.parse(content);
		Elements lis = doc.select("div");
		lis = lis.select(".ZINbbc");
		
		
		for(Element li : lis)
		{
			try 
			{
				String title = li.select(".BNeawe").get(0).text();
				String citeUrl = filtHtmlString(li.select("a").get(0).attr("href"));
				
				
				result.add(new Rating(title,citeUrl));
				
				
				
				
			} 
			catch (IndexOutOfBoundsException e) {e.printStackTrace();

			}	
		}
		

				Elements revs = doc.select("div");
				revs =revs.select(".tHmfQe");
	
				for(Element rev : revs)
				{
					String revTitle =rev.select(".BNeawe").text() ;
					String revUrl = "http://www.google.com/search?q="+rev.select(".BNeawe").text()+"&oe=utf8&num=10";
					rl.add(new Rating(revTitle,revUrl));
				
				
				}

		
		for(int i=0;i<=result.size()-1;i++) //links counting
		{
			GoogleCounting countScore = new GoogleCounting(result.get(i).url,searchKeyword); 	//count subLinks' score
			result.get(i).setScore(countScore.score);
		}
		quickSort(0,result.size()-1);

		 HashMap<String, String> retVal = new HashMap<String, String>();
			for(int n=0;n<=result.size()-1;n++)
			{
				retVal.put(result.get(n).webName,result.get(n).url);	
			}
//		System.out.print(result);

		
		return retVal;

	}

	private String filtHtmlString(String html) throws UnsupportedEncodingException
	{
		int a =html.indexOf("https");	
		int b = html.indexOf("&");
		html = html.substring(a,b);
		html = URLDecoder.decode(html, "UTF-8"); //url decode
		
		return html;
	}


	
	public   void quickSort(int leftbound, int rightbound)
	{
		ArrayList<Rating> L = new ArrayList<>();
		ArrayList<Rating> R = new ArrayList<>();
		for(int i=leftbound;i<=rightbound;i++)
		{
			if(result.get(i).count<result.get(leftbound).count)
			{
				L.add(result.get(i));
				swap(i,leftbound+1);
				swap(leftbound,leftbound+1);
				leftbound=leftbound+1;	
			}
			else if(result.get(i).count>=result.get(leftbound).count){R.add(result.get(i));}
		}
		if(R.size()>1){quickSort(leftbound+1,rightbound);}
		if(L.size()>1){quickSort(0,leftbound-1);}
	}
	public  void swap(int aIndex, int bIndex)
	{
		Rating temp = result.get(aIndex);
		result.set(aIndex, result.get(bIndex));
		result.set(bIndex, temp);
	}
	
}