package parkinG.retriever.sfparktemplate;

import java.awt.geom.Point2D;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "AVL")
@XmlType(propOrder = { "type", "bfid", "ospid", "name", "desc", "inter", "tel", "ophrs", "occ", "oper", "pts", "loc" })
public class Avl {
	
	// Descriptions are assuming SfpAvailability.cleanParkingData() has been run
	private boolean type;		// true for on-street parking, false for off-street parking
								// ! should all be false
	private String name;		// Name of parking garage
	private String desc;		// Street address of parking garage
	private String inter;		// Intersection which parking garage is located at
	private String tel;			// Telephone number of parking garage
	private int ospid;			// Unique SMFTA identifier for off-street parking
	private int bfid;			// Unique SMFTA identifier for on-street parking
								// ! should have no value
	private int occ;			// Number of occupied parking spots
	private int oper;			// Total number of parking spots
	private int pts; 			// Number of location points returned for loc
								// ! should be 1 for off-street parking, 2 for on-street parking
	private Point2D.Double loc;	// Longitude and latitude values for this location
								// ! Do not use for locating garage, use desc (street address)
	
	private List<Ophrs> ophrs;
	
	public String getType() {
		return ""+type;
	}
	@XmlElement(name = "TYPE")
	public void setType(String type) {
		this.type = (type.equalsIgnoreCase("on")) ? true : false;
	}
	public int getOspid() {
		return ospid;
	}
	@XmlElement(name = "OSPID")
	public void setOspid(int ospid) {
		this.ospid = ospid;
	}
	public String getName() {
		return name;
	}
	@XmlElement(name = "NAME")
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	@XmlElement(name = "DESC")
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getInter() {
		return inter;
	}
	@XmlElement(name = "INTER")
	public void setInter(String inter) {
		this.inter = inter;
	}
	public String getTel() {
		return tel;
	}
	@XmlElement(name = "TEL")
	public void setTel(String tel) {
		this.tel = tel;
	}
	public List<Ophrs> getOphrs() {
		return ophrs;
	}
	@XmlElement(name = "OPHRS")
	public void setOphrs(List<Ophrs> ophrs) {
		this.ophrs = ophrs;
	}
	public int getOcc() {
		return occ;
	}
	@XmlElement(name = "OCC")
	public void setOcc(int occ) {
		this.occ = occ;
	}
	public int getOper() {
		return oper;
	}
	@XmlElement(name = "OPER")
	public void setOper(int oper) {
		this.oper = oper;
	}
	public int getPts() {
		return pts;
	}
	@XmlElement(name = "PTS")
	public void setPts(int pts) {
		this.pts = pts;
	}
	public String getLoc() {
		return loc.toString();
	}
	@XmlElement(name = "LOC")
	public void setLoc(String loc) {
		String[] coords = loc.split(",");
		this.loc = new Point2D.Double(Double.parseDouble(coords[0]), Double.parseDouble(coords[1]));
	}
	public int getBfid() {
		return bfid;
	}
	@XmlElement(name = "BFID")
	public void setBfid(int bfid) {
		this.bfid = bfid;
	}
	
	//Extra methods to get value not string representation
	public boolean typeIs() {
		return type;
	}
}
