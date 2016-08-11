package parkinGv3.retriever;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import parkinGv3.retriever.sfparktemplate.Avl;
import parkinGv3.retriever.sfparktemplate.Ophrs;
import parkinGv3.retriever.sfparktemplate.Ops;
import parkinGv3.retriever.sfparktemplate.SfpAvailability;

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
