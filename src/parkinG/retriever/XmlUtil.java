package parkinG.retriever;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import parkinG.sfparktemplate.Avl;
import parkinG.sfparktemplate.Ophrs;
import parkinG.sfparktemplate.Ops;
import parkinG.sfparktemplate.SfpAvailability;

public class XmlUtil {

	/**
	 * Takes InputStream with XML data for SfpAvailability object, unmarshalls into SfpAvailability object
	 * @param s
	 * @return new SfpAvailability object  or null if error occurs
	 */
	public static final SfpAvailability unmarshalSFPData(InputStream s) {
		try {
			
			final JAXBContext jc = JAXBContext.newInstance(SfpAvailability.class, Avl.class, Ophrs.class, Ops.class); 

			final Unmarshaller unmarshaller = jc.createUnmarshaller();
			unmarshaller.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());

			SfpAvailability sfp = (SfpAvailability) unmarshaller.unmarshal(s);

			sfp.cleanParkingData();

			return sfp;
			
		} catch (JAXBException e) {
			System.err.println("[XMLUtil] unmarshalSFPData(): ERROR - " + e.getMessage());
		}
		
		return null; // Should never execute
	}
}
