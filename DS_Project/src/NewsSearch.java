import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import webPackages.CountingScore;


public class NewsSearch 
{
	
	private String news_name,content,date;
	private static ArrayList<Integer> urls;  //全新聞網址
	private static ArrayList<Rating> result;
	
	/*Construct*/
	public NewsSearch(String input) throws IOException
	{
		result = new ArrayList<Rating>();
		
		appleNews();
		udnNews();
		ltnNews();
		
		pageCalculate(input);         /* result計分 名稱處理 */
		quickSort_b(0,result.size()-1); //網站依分數排列
//		System.out.println(result);
	}
	
	
	public void pageCalculate(String searchWord) throws UnsupportedEncodingException
	{
		
		for(int x=0;x<=result.size()-1;x++)
		{
			 CountingScore counting  =new CountingScore(result.get(x).url,searchWord); //給定網址和關鍵字
			result.get(x).setScore(counting.getCount()); //設定score
			result.get(x).setWebName(counting.getName()); //設定webName
		}
		
	}
	
	 public HashMap<String, String> query() throws IOException 
		{
		  HashMap<String, String> retVal = new HashMap<String, String>();
		  for(int n=result.size()-1;n>=0;n--)
			{
				retVal.put(result.get(n).webName,result.get(n).url);	
			}
			return retVal;
		}
	
	
	
	
	
	/*-------------針對三家新聞台網站得出所有子連結----------*/
	
	public void appleNews() throws IOException
	{
		news_name="appledaily";
		getDate();
		countKeyword(date+"/1");
		getUrls();
	}
	public void udnNews() throws IOException
	{
		news_name="udn";
		countKeyword("/6656/");
		getUrls();
	}
	public void ltnNews() throws IOException
	{
		news_name="ltn";
		countKeyword("politics/breakingnews/");
		getUrls();
	}
	/*-------------------------------------*/
	
	  public int countKeyword(String keyword) throws IOException  
	  {
	    	
			content = fetchContent();
			content = content.toUpperCase();
			keyword = keyword.toUpperCase();
			int retVal = 0; 
			int pointer = 1;
			String cur = "";
			urls = new ArrayList<Integer>();
			for(int i=0;i<content.length()-keyword.length()+1;i++)
			{
				if(content.charAt(i)==keyword.charAt(0)&&keyword.length()>1){
				for(int n=1;n<keyword.length();n++){
					if(content.charAt(i+n)!=keyword.charAt(n)){break;}
					while(n==keyword.length()-1)
					{retVal++;break;}}}
				else if(content.charAt(i)==keyword.charAt(0)) {retVal++;}	
				
			
				if(retVal==pointer&&pointer<8)  //新聞台篩選n則新聞
				{
					
					switch (news_name)
					{
					case "appledaily" :  
								{for(int n=9;n<16;n++){cur += content.charAt(i+n);}urls.add(Integer.parseInt(cur));} break;
					case "udn" : 
								{ for(int n=6;n<13;n++){cur+=content.charAt(i+n);}urls.add(Integer.parseInt(cur));} break;
					case "ltn" : 
								{for(int n=22;n<29;n++){cur+=content.charAt(i+n);}urls.add(Integer.parseInt(cur));}break;
					default: System.out.println("Unknown news_name"); break;
					}

					cur="";
					pointer++;
					
				}	
			}
			return retVal;
	  	}
	  
	  private String fetchContent() throws IOException
	    {
	    	String mainPage="";
			switch (news_name)
			{
			case "appledaily" :  { mainPage = "https://tw.news.appledaily.com/politics/realtime";} break;
			case "udn" : { mainPage = "https://udn.com/news/cate/2/6638";} break;
			case "ltn" : {mainPage = "https://news.ltn.com.tw/list/breakingnews/politics";} break;
			default: System.out.println("Unknown news_name"); break;
			}
			
			URL url = new URL(mainPage);
			URLConnection conn = url.openConnection();
			InputStream in = conn.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			String retVal = "";
			String line = null;
			
			while ((line = br.readLine()) != null)
			{retVal = retVal + line + "\n";}
			
			return retVal;
	    }
	  
	  public void getUrls() throws IOException
		{
			quickSort_a(0,urls.size()-1);	
			arrayCheck();

			for(Integer element:urls)
			{
				
				String pages = "";
				switch (news_name)
				{
				case "appledaily" :  { pages = "https://tw.news.appledaily.com/politics/realtime"+"/"+date+"/"+element;} break;
				case "udn" : { pages = "https://udn.com/news/cate/2/6638"+"/6656/"+element;} break;
				case "ltn" : {pages ="https://news.ltn.com.tw/news/politics/breakingnews/"+element;} break;
				default: System.out.println("Unknown news_name"); break;
				}
				result.add(new Rating(null,pages,0));               //得出所有新聞網站子連結
			}

		}
	  
	  
	  /*-------------------------------------*/
	  
	  
	  
	 
	  public static  void quickSort_a(int leftbound, int rightbound)
		{
			ArrayList<Integer> L = new ArrayList<>();
			ArrayList<Integer> R = new ArrayList<>();
			for(int i=leftbound;i<=rightbound;i++)
			{
				
				if(urls.get(i)<urls.get(leftbound))
				{
					L.add(urls.get(i));
					swap_a(i,leftbound+1);
					swap_a(leftbound,leftbound+1);
					leftbound=leftbound+1;	
				}
				else if(urls.get(i)>=urls.get(leftbound)){R.add(urls.get(i));}
			}
			if(R.size()>1){quickSort_a(leftbound+1,rightbound);}
			if(L.size()>1){quickSort_a(0,leftbound-1);}
		}
		public static void swap_a(int aIndex, int bIndex)//���index ab���ƭ�
		{
			int temp = urls.get(aIndex);
			urls.set(aIndex, urls.get(bIndex));
			urls.set(bIndex, temp);
		}
		public static void arrayCheck()
		{
			for(int x=urls.size()-1; x>=1;x--)
			{
				int value_a = urls.get(x);
				int value_b = urls.get(x-1);
				if(value_a==value_b)
				{
					urls.remove(urls.get(x));
				}
			}
		}
		

	  public void getDate()
	    {
			  Date Now = new Date( );
		      SimpleDateFormat ft = new SimpleDateFormat ("yyyyMMdd");
		      date =  ft.format(Now);
	    }
	  
	  
	  /*-------------------------------------*/
	  
	  
		public   void quickSort_b(int leftbound, int rightbound)
		{
			ArrayList<Rating> L = new ArrayList<>();
			ArrayList<Rating> R = new ArrayList<>();
			for(int i=leftbound;i<=rightbound;i++)
			{
				
				if(result.get(i).count<result.get(leftbound).count)
				{
					L.add(result.get(i));
					swap_b(i,leftbound+1);
					swap_b(leftbound,leftbound+1);
					leftbound=leftbound+1;	
				}
				else if(result.get(i).count>=result.get(leftbound).count){R.add(result.get(i));}
			}
			if(R.size()>1){quickSort_b(leftbound+1,rightbound);}
			if(L.size()>1){quickSort_b(0,leftbound-1);}
		}
	  
		public  void swap_b(int aIndex, int bIndex)
		{
			Rating temp = result.get(aIndex);
			result.set(aIndex, result.get(bIndex));
			result.set(bIndex, temp);
		}
	  
	  
	  public ArrayList<Rating> getRs() { return result;}
	  
	  
	  }
