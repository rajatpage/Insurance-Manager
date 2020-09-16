package newLearning;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jade.content.Concept;

/*
 * This is the concept of InsuarnceOntology
 */
public class Insuarance implements Concept{

	private String insId;
	
	private String insType;

	private int insMaxPrice;
	
	private int insMinPrice;
	
	private int insPrice;
	
	private int timePeriod;
	
	private int coverage;
	
	private String insDescription;
	
	private String insCompanyName;
	
	public String getInsId() {
		return insId;
	}

	public void setInsId(String insId) {
		this.insId = insId;
	}

	public String getInsType() {
		return insType;
	}

	public void setInsType(String insType) {
		this.insType = insType;
	}

	public int getInsMaxPrice() {
		return insMaxPrice;
	}

	public void setInsMaxPrice(int insMaxPrice) {
		this.insMaxPrice = insMaxPrice;
	}

	public int getInsMinPrice() {
		return insMinPrice;
	}

	public void setInsMinPrice(int insMinPrice) {
		this.insMinPrice = insMinPrice;
	}

	public int getInsPrice() {
		return insPrice;
	}

	public void setInsPrice(int insPrice) {
		this.insPrice = insPrice;
	}

	public int getTimePeriod() {
		return timePeriod;
	}

	public void setTimePeriod(int timePeriod) {
		this.timePeriod = timePeriod;
	}

	public int getCoverage() {
		return coverage;
	}

	public void setCoverage(int coverage) {
		this.coverage = coverage;
	}

	public String getInsDescription() {
		return insDescription;
	}

	public void setInsDescription(String insDetail) {
		this.insDescription = insDetail;
	}

	public String getInsCompanyName() {
		return insCompanyName;
	}

	public void setInsCompanyName(String insCompanyName) {
		this.insCompanyName = insCompanyName;
	}
	
}
