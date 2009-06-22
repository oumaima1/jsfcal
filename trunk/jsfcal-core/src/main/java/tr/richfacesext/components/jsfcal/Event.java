package tr.richfacesext.components.jsfcal;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author mert
 *
 */
public interface Event extends Serializable {

	Long getEventId();
	
	String getTitle();
	
	Date getStartDate();
	
	Date getEndDate();
	
	boolean isReadOnly();
}