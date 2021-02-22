 package api.end.point;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiTemplate {

	String baseMarket;
	String quoteMarket;
	float low;
	float high;
	float last;
	float sell;
	float buy;
	public String getBaseMarket() {
		return baseMarket;
	}
	public void setBaseMarket(String baseMarket) {
		this.baseMarket = baseMarket;
	}
	public String getQuoteMarket() {
		return quoteMarket;
	}
	public void setQuoteMarket(String quoteMarket) {
		this.quoteMarket = quoteMarket;
	}
	public float getLow() {
		return low;
	}
	public void setLow(float low) {
		this.low = low;
	}
	public float getHigh() {
		return high;
	}
	public void setHigh(float high) {
		this.high = high;
	}
	public float getLast() {
		return last;
	}
	public void setLast(float last) {
		this.last = last;
	}
	public float getSell() {
		return sell;
	}
	public void setSell(float sell) {
		this.sell = sell;
	}
	public float getBuy() {
		return buy;
	}
	public void setBuy(float buy) {
		this.buy = buy;
	}



}
