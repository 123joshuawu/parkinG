package parkinG.sfpark;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import parkinG.sfpark.templates.Avl;
import parkinG.sfpark.templates.Ophrs;
import parkinG.sfpark.templates.Ops;
import parkinG.sfpark.templates.SfpAvailability;

public class SfparkUtil {

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

			//sfp.cleanParkingData();

			return sfp;
			
		} catch (JAXBException e) {
			System.err.println("[XMLUtil] unmarshalSFPData(): ERROR - " + e.getMessage());
			e.printStackTrace();
		}
		return null; // Should never execute
	}
	
	/**
	 * Takes SfpAvailability object and prints in XML format
	 * @param sfp
	 */
	public static final void printSfpAvailabilityObject(SfpAvailability sfp) {
		try {
		JAXBContext jc = JAXBContext.newInstance(SfpAvailability.class, Avl.class, Ophrs.class, Ops.class);

        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(sfp, System.out);
		} catch (JAXBException e) {
			System.err.println("[XMLUtil] printSfpAvailabilityObject(): " + e.getMessage());
			e.printStackTrace();
		}
	}
}
