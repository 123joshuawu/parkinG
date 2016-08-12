package parkinG.sfpark.templates;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "OPS")
@XmlType( propOrder = { "from", "to", "beg", "end" } )
public class Ops {
	
	private String from;
	private String to;
	private String beg;
	private String end;
	
	public String getFrom() {
		return from;
	}
	@XmlElement(name = "FROM")
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	@XmlElement(name = "TO")
	public void setTo(String to) {
		this.to = to;
	}
	public String getBeg() {
		return beg;
	}
	@XmlElement(name = "BEG")
	public void setBeg(String beg) {
		this.beg = beg;
	}
	public String getEnd() {
		return end;
	}
	@XmlElement(name = "END")
	public void setEnd(String end) {
		this.end = end;
	}
	
}
