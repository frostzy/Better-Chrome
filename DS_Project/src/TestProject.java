

import java.io.IOException;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;

/**
 * Servlet implementation class TestProject
 */
@WebServlet("/TestProject")

	public class TestProject extends HttpServlet {
	private static final long serialVersionUID = 1L;
//	public static String ua;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestProject() 
    {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		

		
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		
		
		if(request.getParameter("keyword")== null) 
		{
			String requestUri = request.getRequestURI();
			request.setAttribute("requestUri", requestUri);
			request.getRequestDispatcher("Search.jsp").forward(request, response);
			return;
		}
		
		
		/*Google Search*//*Left Btn*/
		if (request.getParameter("first") != null) 
		{
//		ua = request.getHeader("User-Agent");
		GoogleQuery google = new GoogleQuery(request.getParameter("keyword"));
		HashMap<String, String> query = google.query();
		
		String[][] s = new String[query.size()][2];
		request.setAttribute("query", s);
		ArrayList<Rating> rs = new ArrayList<Rating>();
		rs=google.getRs();
		int num=0;
		for(int n=rs.size()-1;n>=0;n--)
		{
				 String key = rs.get(n).webName;
				 String value = rs.get(n).url;
				 s[num][0] = key;
				 s[num][1] = value;
				 num++;
		}
		
		ArrayList<Rating>rl = new ArrayList<Rating>();
		rl = google.getRl();
		int index=0;
		String[][] t = new String[rl.size()][2];
		request.setAttribute("reval", t);
		for(int x=0;x<=rl.size()-1;x++)
		{
				 String key = rl.get(x).webName;
				 String value = rl.get(x).url;
				 t[index][0] = key;
				 t[index][1] = value;
				 index++;
		}
		
		request.getRequestDispatcher("googleitem.jsp").forward(request, response); 
		}
		
		
		
		/*NewsSearch*//*RightButton*/
		
		else if (request.getParameter("second") != null)
		{
			
			NewsSearch news = new NewsSearch(request.getParameter("keyword"));
			HashMap<String, String> query = news.query();
			
			
			String[][] s = new String[query.size()][2];
			request.setAttribute("query", s);
			
			
			ArrayList<Rating> rs = new ArrayList<Rating>();
			rs=news.getRs();
			int num=0;
			for(int n=rs.size()-1;n>=rs.size()-9;n--)
			{
					 String key = rs.get(n).webName;
					 String value = rs.get(n).url;
					 s[num][0] = key;
					 s[num][1] = value;
					 num++;
			}
			request.getRequestDispatcher("newsitem.jsp").forward(request, response); 
		}	
	}

	
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	
	
}
