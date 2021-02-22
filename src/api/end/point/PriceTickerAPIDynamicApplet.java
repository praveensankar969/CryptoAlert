package api.end.point;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.AbstractAction;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.ws.rs.core.UriBuilder;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PriceTickerAPIDynamicApplet {


	public static List<ApiTemplateTicker> GetData() throws IOException, ParseException {

		List<ApiTemplateTicker> data = new ArrayList<ApiTemplateTicker>();
		URL url = new URL("https://api.wazirx.com/api/v2/tickers");
		String readLine = null;
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setRequestMethod("GET");
		int resCode = connection.getResponseCode();
		StringBuffer response = new StringBuffer();

		if(resCode == HttpURLConnection.HTTP_OK) {
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			while((readLine = in.readLine()) != null) {
				response.append(readLine);
			} in.close();
		}
		else {
			System.out.println("Error");
			System.exit(0);
		}

		String responseData = response.toString();
		String[] arrayData = responseData.split("}");

		String tempText = arrayData[0].substring(1);
		int index = tempText.indexOf("{");
		int indexNext = tempText.indexOf("open")-2;
		tempText= tempText.substring(index, indexNext)+"}";
		JSONParser parser = new JSONParser();  
		JSONObject json = (JSONObject) parser.parse(tempText); 
		ObjectMapper objectMapper = new ObjectMapper(); 
		ApiTemplateTicker obj = null;

		try { 
			obj = objectMapper.readValue(json.toJSONString(),ApiTemplateTicker.class );

		}catch(Exception e) {

			e.printStackTrace(); }

		data.add(obj);


		for(int i=1;i<arrayData.length;i++) {

			tempText = arrayData[i];
			index = tempText.indexOf("{");
			indexNext = tempText.indexOf("open")-2;
			tempText= tempText.substring(index, indexNext)+"}";
			parser = new JSONParser();  
			json = (JSONObject) parser.parse(tempText); 
			objectMapper = new ObjectMapper(); 

			try { 
				obj = objectMapper.readValue(json.toJSONString(),ApiTemplateTicker.class );

			}catch(Exception e) {

				e.printStackTrace(); }

			data.add(obj);



		}

		return data;


	}

	public static Map<String, Float> SaveInitialData(List<ApiTemplateTicker> data) {

		Map<String, Float> price = new HashMap<String, Float>();
		for(int i=0;i<data.size();i++) {
			if(data.get(i).getQuote_unit().equals("usdt")) {
				price.put(data.get(i).getBase_unit(), data.get(i).getLast());
			}
		}

		return price;
	}

	public static void Popup(float price1, float price2, String coin) {


		final JOptionPane optionPane = new JOptionPane(coin + " -Initial price: "+ price1 +" Current Price:        "+ price2, JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
		final JDialog dialog = new JDialog();
		dialog.setTitle("Alert");
		dialog.setModal(true);
		dialog.setContentPane(optionPane);
		dialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		dialog.pack();
		dialog.setBounds(300, 400, 600, 150);

		Timer timer = new Timer(5000, new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				dialog.dispose();
			}
		});
		timer.setRepeats(false);
		timer.start();
		dialog.setVisible(true);

	}

	public static void UpdatePopup() {



	}



	public static void GettingDifference(Map<String, Float> initial) throws InterruptedException, IOException, ParseException {

		List<Float> initialPrice = new ArrayList<>(initial.values());
		List<Float> currentPrice = null;
		List<Float> middlePrice = initialPrice;



		while(1>0) {

			List<ApiTemplateTicker> currentData = GetData();
			Map<String, Float> currentDataMap = SaveInitialData(currentData);
			currentPrice = new ArrayList<>(currentDataMap.values());
			System.out.println("Check Point Timeout 30 seconds at " + java.time.LocalTime.now());
			String coin;
			String setText_= "";
			float changePercentage;

			for(int i=0;i<currentPrice.size();i++) {


				if(currentPrice.get(i)- initialPrice.get(i) >0) {
					changePercentage = ((currentPrice.get(i)- initialPrice.get(i))/initialPrice.get(i))*100;
					coin  = GetKey(initial,initialPrice.get(i));
					if(changePercentage>1) {
						setText_ += "\n Change in "+coin +" Initial Price: "+initialPrice.get(i)+"  Current Price:  "+currentPrice.get(i)+"  Change%:  "+changePercentage;
					}
				}
			}

			middlePrice = currentPrice;
			SendUpdates(setText_);
			Thread.sleep(45000);
			System.out.println("---------------------------------------------------------------------------------------------------");
		}
	}


	public static String GetKey(Map<String, Float> data, float value) {

		for(Entry<String, Float> entry : data.entrySet()) {
			if(entry.getValue() == value) {
				return entry.getKey();
			}
		}

		return null;

	}


	public static int CountZero(float price) {

		int zero = 0;
		while(price<1) {
			price = price*10;
			zero++;
		}
		zero--;

		return zero;
	}

	
	public static void SendUpdates(String message) throws IOException, InterruptedException {
		
		HttpClient client = HttpClient.newBuilder()
	                .connectTimeout(Duration.ofSeconds(5))
	                .version(HttpClient.Version.HTTP_2)
	                .build();
	 
	        UriBuilder builder = UriBuilder
	                .fromUri("https://api.telegram.org")
	                .path("/{token}/sendMessage")
	                .queryParam("chat_id", "-514815556")
	                .queryParam("text", message);
		 
	        HttpRequest request = HttpRequest.newBuilder()
	                .GET()
	                .uri(builder.build("bot" + "1579392802:AAGjgAGepaNFx_5aSH8b2ZcKAj4QNRRHmPA"))
	                .timeout(Duration.ofSeconds(5))
	                .build();
		 
	        HttpResponse<String> response = client
	                .send(request, HttpResponse.BodyHandlers.ofString());
		
		
		
	}
	public static void main(String args[]) throws IOException, InterruptedException, ParseException {

		/*
		 * List<ApiTemplateTicker> initialPrice = GetData(); Map<String, Float>
		 * initialData = SaveInitialData(initialPrice); String name =
		 * SaveToExcel.SaveDataToExcelTickerAPI(initialPrice); System.out.println(name);
		 */


		Map<String, Float> initialData = SaveToExcel.GetDataToExcelTickerAPI("Data-Ticker-API22-49-21 09-49");
		GettingDifference(initialData);

		
		

	}


}
