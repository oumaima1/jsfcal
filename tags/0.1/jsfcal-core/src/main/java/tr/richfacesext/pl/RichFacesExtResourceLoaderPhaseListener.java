package tr.richfacesext.pl;

import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.richfacesext.components.ComponentConstants;
import tr.richfacesext.components.ComponentUtils;

/**
 * 
 * @author mert
 *
 */
@SuppressWarnings("serial")
public class RichFacesExtResourceLoaderPhaseListener implements PhaseListener {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private long lastModificationTime = constructLastModifiedTime();
	
	public void beforePhase(PhaseEvent event) {
		// No-op
	}

	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

	@SuppressWarnings("unchecked")
	public void afterPhase(PhaseEvent event) {
		FacesContext facesContext = event.getFacesContext();
		String viewRootId = facesContext.getViewRoot().getViewId();

		if (ComponentUtils.viewRootContainsPLKey(ComponentConstants.RICHFACESEXT_RESOURCE_LOADER_VIEW_ID, viewRootId)) {
			try {
				serveResource(facesContext, viewRootId);
			}
			catch (StringIndexOutOfBoundsException e) {
				logger.error("resource request is not a proper one! viewRootId: " + viewRootId, e);
			}
		}
	}

	private void serveResource(FacesContext facesContext, String viewRootId) throws StringIndexOutOfBoundsException {
		String restStr = extractRestStr(viewRootId);  
		String resourceName = getResourceName(restStr);
		String resourceFolder = getResourceFolder(restStr);
		
		String resourceType = getResourceType(resourceName);

		String resourcePath = populateResourcePath(resourceFolder, resourceName, resourceType);
		serveResource(this.getClass(), facesContext, resourcePath, resourceType);
	}

	private String extractRestStr(String viewRootId) {
		return StringUtils.substringAfter(viewRootId, ComponentConstants.RICHFACESEXT_RESOURCE_LOADER_VIEW_ID);
	}

	private String populateResourcePath(String resourceFolder, String resourceName, String resourceType) {
		StringBuffer resourcePath = new StringBuffer();
		
		resourcePath.append(ComponentConstants.RICHFACES_RESOURCE_FOLDER).append("/");
		resourcePath.append(determineAssetsSubFolder(resourceType)).append("/");
		resourcePath.append(resourceFolder).append("/");
		resourcePath.append(resourceName);
		
		logger.debug("serving requested resource " + resourcePath);

		return resourcePath.toString();
	}
	
	public void serveResource(Class clazz, FacesContext facesContext, String resourcePath, String resourceType) {

		int indice, tempIndice;
		byte tempArr[];
		byte mainArr[] = new byte[0];
		byte byteArr[] = new byte[65535];

		HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
		String contentType = getContentType(resourceType);

		try {
			URL url = clazz.getResource(resourcePath);
			if (url == null) {
				logger.warn("Panic! Requested resource not found: " + resourcePath); 

				facesContext.responseComplete();
				return;
			}

			InputStream inputStream = clazz.getResourceAsStream(resourcePath);
			ServletOutputStream outputStream = response.getOutputStream();
			
			response.setContentType(contentType);
			response.setStatus(200);
			response.setDateHeader("Last-Modified", lastModificationTime);
			response.setDateHeader("Expires", getCacheInfo());

			for (indice = 0; (indice = inputStream.read(byteArr)) > 0;) {
				tempIndice = mainArr.length + indice;
				tempArr = new byte[tempIndice];
				System.arraycopy(mainArr, 0, tempArr, 0, mainArr.length);
				System.arraycopy(byteArr, 0, tempArr, mainArr.length, indice);
				mainArr = tempArr;
			}

			outputStream.write(mainArr);
			outputStream.flush();
			outputStream.close();

			facesContext.responseComplete();
		} 
		catch (Exception e) {
			logger.error("Error occured while serving resource.", e);
		}
	}	
	
	private long getCacheInfo() {
		Calendar expires = Calendar.getInstance();
		expires.add(Calendar.DAY_OF_WEEK, 7);
		
		return expires.getTimeInMillis();
	}
	
	private long constructLastModifiedTime() {
		long timeVal = 0;
		try {
			String dateStr = SimpleDateFormat.getInstance().format(new Date());
			timeVal = SimpleDateFormat.getInstance().parse(dateStr).getTime();
		} 
		catch (ParseException e) {
			logger.error("Unparseable last modified time", e);
		}
		return timeVal;
	}	
	
	private static String getContentType(String resourceType) {
		String contentType = null;
		
		if (resourceType.equals("js"))
			contentType = "text/javascript";
		else if (resourceType.equals("css"))
			contentType = "text/css";
		else if (resourceType.equals("jpg"))
			contentType = "image/jpeg";
		else if (resourceType.equals("gif"))
			contentType = "image/gif";
		else if (resourceType.equals("png"))
			contentType = "image/png";
		else if (resourceType.equals("html"))
			contentType = "text/html";
		else if (resourceType.equals("swf"))
			contentType = "application/x-shockwave-flash";

		return contentType;
	}		

	private String determineAssetsSubFolder(String resourceType) {
		String resourceSubFolder = "";
		
		if ("gif".equals(resourceType) || "jpg".equals(resourceType) || "png".equals(resourceType))
			resourceSubFolder = ComponentConstants.SUBFOLDER_IMG; 
		else if ("css".equals(resourceType))
			resourceSubFolder = ComponentConstants.SUBFOLDER_CSS;
		else if ("js".equals(resourceType))
			resourceSubFolder = ComponentConstants.SUBFOLDER_JS;
		else if ("swf".equals(resourceType)) 
			resourceSubFolder = ComponentConstants.SUBFOLDER_FLASH;
		
		return resourceSubFolder;
	}

	@SuppressWarnings("unchecked")
	private String getResourceName(String restStr) {
		return restStr.substring(restStr.lastIndexOf("/") + 1, restStr.length());
	}
	
	@SuppressWarnings("unchecked")
	private String getResourceFolder(String restStr) {
		return restStr.substring(restStr.indexOf("/") + 1, restStr.lastIndexOf("/"));
	}
	
	private String getResourceType(String resourceName) {
		return resourceName.substring(resourceName.lastIndexOf('.') + 1, resourceName.length());
	}		
}