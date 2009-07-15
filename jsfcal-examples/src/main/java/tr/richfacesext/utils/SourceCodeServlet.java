package tr.richfacesext.utils;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class SourceCodeServlet  extends HttpServlet  {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
    
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException  {
        String webPage = req.getServletPath();
        
      	webPage = chopGivenSuffix(webPage, ".source");

        if(webPage.endsWith(".jsf"))  {
        	webPage = chopGivenSuffix(webPage, ".jsf");
            webPage += ".xhtml"; 
        }
 
        String realPath = getServletConfig().getServletContext().getRealPath(webPage);
        outputFile(res, realPath);
    }

	private String chopGivenSuffix(String webPage, String suffix)  {
      	int chopPoint = webPage.lastIndexOf(suffix);
        return webPage.substring(0, chopPoint);
	}
	    
    
    private void outputFile(HttpServletResponse res, String realPath)   throws IOException   {
        res.setContentType("text/plain");
        ServletOutputStream out = res.getOutputStream();

        String escapedHtml = readXhtmlFile(new FileInputStream(realPath));
        out.write(escapedHtml.getBytes());
    }

	public String readXhtmlFile(InputStream inputStream) {
		int indice, tempIndice;
		
		byte tempArr[];
		byte mainArr[] = new byte[0];
		byte byteArr[] = new byte[65535];
		
        try {
        	for (indice = 0; (indice = inputStream.read(byteArr)) > 0;) {
				tempIndice = mainArr.length + indice;
				tempArr = new byte[tempIndice];
				System.arraycopy(mainArr, 0, tempArr, 0, mainArr.length);
				System.arraycopy(byteArr, 0, tempArr, mainArr.length, indice);
				mainArr = tempArr;
			}
		} 
        catch (IOException e) {
			logger.error("Error occured while reading xhtml file", e);
		}
    
        return new String(mainArr);
    }
}