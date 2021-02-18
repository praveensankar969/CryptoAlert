package api.end.point;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiTemplateTicker {

	String base_unit;
	String quote_unit;
	float last;
	
	public String getBase_unit() {
		return base_unit;
	}
	public void setBase_unit(String base_unit) {
		this.base_unit = base_unit;
	}
	public String getQuote_unit() {
		return quote_unit;
	}
	public void setQuote_unit(String quote_unit) {
		this.quote_unit = quote_unit;
	}
	public float getLast() {
		return last;
	}
	public void setLast(float last) {
		this.last = last;
	}

	
}
