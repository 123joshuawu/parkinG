package parkinG.sfpark.templates;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "SFP_AVAILABILITY", namespace = "http://www.sfmta.com/xsd/availability")
@XmlType( propOrder = { "status", "errorCode", "numRecords", "message", "updatedAt", "requestReceivedAt", "avl" })
public class SfpAvailability {

	private boolean status;			// true for SUCCESS, false for other
	private int numRecords;			// Gets reset by cleanParkingData()
	private int errorCode;			// Should be 0, if not something is wrong
	private String message;			// Usually just numRecords + " records found"
									// ! is not updated by cleanParkingData()!
	private Date updatedAt; 		// When sfpark servers last updated their data
	private Date requestReceivedAt;	// When sfpark servers received our request
	private List<Avl> avl;			// List of Avl objects, each one representing a different parking garage
									// ! cleanParkingData() will leave only parking garages
	
	private boolean dataClean = false; // Indicator of whether cleanParkingData() has been run or not
									// ! make sure value is true before accessing variable VALUES
	
	/**
	 * Method "cleans" parking data
	 * When called, iterates through List avl and removes if:
	 * 		- is on-street parking
	 * 		- Avl.occ and Avl.oper values are both 0 (weird case, cause unknown)
	 * Also resets numRecords to avl.size()
	 */
	public void cleanParkingData() {
		for(int i = 0; i < avl.size(); i++)
			if(avl.get(i).typeIs() || (avl.get(i).getOcc() == 0 && avl.get(i).getOper() == 0))
				avl.remove(i--);
		numRecords = avl.size();
		dataClean = true;
	}

	//Methods for retrieving variable VALUES not string representations
	public Date retrieveUpdatedAt() {
		return updatedAt;
	}
	
	public boolean isDataClean() {
		return dataClean;
	}

	// Below: getters/setters implemented for JAXB marshalling/unmarshalling
	// ! all methods below only return string representations
	public String getStatus() { return ""+status; }	
	@XmlElement(name = "STATUS")
	public void setStatus(String status) {
		this.status = (status.equalsIgnoreCase("success")) ? true : false;
	}
	public int getNumRecords() { return numRecords; }	
	@XmlElement(name = "NUM_RECORDS")
	public void setNumRecords(int numRecords) {
		this.numRecords = numRecords;
	}
	public int getErrorCode() { return errorCode; }	
	@XmlElement(name = "ERROR_CODE")
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getMessage() {
		return message;
	}
	@XmlElement(name = "MESSAGE")
	public void setMessage(String message) {

		this.message = message;
	}
	public String getUpdatedAt() {
		return new SimpleDateFormat("EEE h:mm:ss:SS a MM/dd/yy").format(updatedAt);
	}
	@XmlElement(name = "AVAILABILITY_UPDATED_TIMESTAMP")
	public void setUpdatedAt(String updatedAt) {
		try {
			this.updatedAt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse(updatedAt);
		} catch (ParseException e) {
			System.err.println("[SfpAvailability] setUpdatedAt(): " + e.getMessage());
		}
	}
	public String getRequestReceivedAt() {
		return new SimpleDateFormat("EEE h:mm:ss:SS a MM/dd/yy").format(requestReceivedAt);
	}
	@XmlElement(name = "AVAILABILITY_REQUEST_TIMESTAMP")
	public void setRequestReceivedAt(String requestReceivedAt) {
		try {
			this.requestReceivedAt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse(requestReceivedAt);
		} catch (ParseException e) {
			System.err.println("[SfpAvailability] requestReceivedAt(): " + e.getMessage());
		}
	}
	public List<Avl> getAvl() {
		return avl;
	}
	@XmlElement(name = "AVL")
	public void setAvl(List<Avl> avl) {
		this.avl = avl;
	}

}
