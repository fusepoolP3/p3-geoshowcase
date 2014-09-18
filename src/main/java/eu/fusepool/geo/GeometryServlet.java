package eu.fusepool.geo;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GeometryServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		String latitude = request.getParameter("latitude");
		String longitude = request.getParameter("longitude");
		String radius = request.getParameter("radius");
		FusekiClient client = new FusekiClient();
		List<String> pointInterest = client.searchSkiRuns(latitude, longitude, radius);
		response.getWriter().println("<table>");
		Iterator<String> ipoi = pointInterest.iterator();
		while(ipoi.hasNext()) {
			String poi = ipoi.next();
			response.getWriter().println("<tr><td>" + poi + "</td></tr>");
		}
		response.getWriter().println("</table>");
		
	}

}
