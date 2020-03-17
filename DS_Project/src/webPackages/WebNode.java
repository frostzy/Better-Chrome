package webPackages;



import java.io.IOException;
import java.util.ArrayList;

public class WebNode {
	public WebNode parent;
	public ArrayList<WebNode> children;
	public WebPage webPage;	
	public int nodeScore;
	
	public WebNode(WebPage webPage)
	{
		this.webPage = webPage;
		this.children = new ArrayList<WebNode>();
	}
	
	public void reset()	{nodeScore=0;}
	public void setNodeScore(String keyword) throws IOException{
		reset();
		webPage.setScore(keyword);
		nodeScore += webPage.score;
		for(WebNode child : children)
		{
			child.setNodeScore(keyword);
			nodeScore +=child.webPage.score;
		}
	}
	
	public void addChild(WebNode child){this.children.add(child);}

}
