package webPackages;

import java.io.IOException;


public class WebPage 
{
	public String url;

	public int score;
	
	public WebPage(String url)
	{
		this.url = url;
	}
	
	public void setScore(String keyword) throws IOException
	{
			score = 0;
			CountingScore scoreRs = new CountingScore(url,keyword);
			score += scoreRs.getCount();
	}
	
	
	
	
}
