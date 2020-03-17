package webPackages;



import java.io.IOException;



	public class WebTree 
	{
		public WebNode root;
	
		public WebTree(WebPage rootPage)
		{
			this.root = new WebNode(rootPage);
		}
	
		public void setPostOrderScore(String keyword) throws IOException
		{
			setPostOrderScore(root, keyword);
		}
	
		private void setPostOrderScore(WebNode startNode, String keyword) throws IOException
		{
			startNode.setNodeScore(keyword);
		}
	
		public int getNodeScore()
		{
			return getNodeScore(root);		
		}
	
		private int getNodeScore(WebNode startNode)
		{
			return startNode.nodeScore;
		}

	
}