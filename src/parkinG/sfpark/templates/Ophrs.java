package parkinG.sfpark.templates;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "OPHRS")
public class Ophrs {
	
	private List<Ops> ophrs;

	public List<Ops> getOphrs() {
		return ophrs;
	}
	@XmlElement(name = "OPS")
	public void setOphrs(List<Ops> ophrs) {
		this.ophrs = ophrs;
	}
}

