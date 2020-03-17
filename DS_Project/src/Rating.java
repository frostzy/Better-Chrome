
public class Rating 
{
	public String url;
	public String webName;
	public int count;
	
	public Rating(String webName,String url,int count)
	{
		this.webName=webName;
		this.url=url;
		this.count=count;
	}
	public Rating(String webName,String url) //for google search
	{
		this.webName = webName;
		this.url=url;
	}
	
	public String toString()
	{
		return "[ webName ]" + webName + "[ Page_Urls ]"+url+"[ Score ] :"+count+'\n';
	}
	public void setScore(int score)
	{
		count = score;
	}
	public void setWebName(String name)
	{
		this.webName = name;
	}
}
