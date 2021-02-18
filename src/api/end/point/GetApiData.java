package api.end.point;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;





public class GetApiData {

	public static List<ApiTemplate> GetData() throws IOException {

		List<ApiTemplate> data = new ArrayList<ApiTemplate>();
		URL url = new URL("https://api.wazirx.com/api/v2/market-status");
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
		}

		String responseData = response.toString();
		responseData = responseData.substring(responseData.indexOf("["), responseData.indexOf(",\"assets\":"));
		System.out.println(responseData);

		try { data = new ObjectMapper().readValue(responseData, new TypeReference<List<ApiTemplate>>() {});

		}catch(Exception e) {

			e.printStackTrace(); }

		return data;


	}

	public static Map<String, Float> SaveInitialData(List<ApiTemplate> data) {

		Map<String, Float> price = new HashMap<String, Float>();
		for(int i=0;i<data.size();i++) {
			if(data.get(i).getQuoteMarket().equals("usdt")) {
				price.put(data.get(i).getBaseMarket(), data.get(i).getLast());
			}
		}

		return price;
	}

	public static void Popup(float price1, float price2, String coin) {
		
		JFrame parent = new JFrame();
		JOptionPane.showMessageDialog(parent, coin + " -Initial price: "+ price1 +" Current Price: "+ price2);
		 
	}
	
	public static void GettingDifference(Map<String, Float> initial) throws InterruptedException, IOException {

		List<Float> initialPrice = new ArrayList<>(initial.values());
		List<Float> currentPrice;

		while(1>0) {
			
			List<ApiTemplate> currentData = GetData();
			Map<String, Float> currentDataMap = SaveInitialData(currentData);
			currentPrice = new ArrayList<>(currentDataMap.values());
			System.out.println("Check Point Timeout 1 minute at " + java.time.LocalTime.now());
			String coin;
			for(int i=0;i<currentPrice.size();i++) {
				
				if(currentPrice.get(i)>=500) {
					if(currentPrice.get(i)-initialPrice.get(i)>100) {
						coin  = GetKey(initial,initialPrice.get(i));
						System.out.println("Change in: "+coin + "   Initial Price: "+ initialPrice.get(i) + "   Current Price: "+currentPrice.get(i));
						Popup(initialPrice.get(i), currentPrice.get(i), coin);
					}
				}
				else if(currentPrice.get(i)>=200) {
					if(currentPrice.get(i)-initialPrice.get(i)>5) {coin  = GetKey(initial,initialPrice.get(i));
					System.out.println("Change in: "+coin + "   Initial Price: "+ initialPrice.get(i) + "   Current Price: "+currentPrice.get(i));
					Popup(initialPrice.get(i), currentPrice.get(i), coin);}
				}
				else if(currentPrice.get(i)>=100) {
					if(currentPrice.get(i)-initialPrice.get(i)>3) {coin  = GetKey(initial,initialPrice.get(i));
					System.out.println("Change in: "+coin + "   Initial Price: "+ initialPrice.get(i) + "   Current Price: "+currentPrice.get(i));
					Popup(initialPrice.get(i), currentPrice.get(i), coin);}
				}
				else if(currentPrice.get(i)>=50) {
					if(currentPrice.get(i)-initialPrice.get(i)>1.5) {coin  = GetKey(initial,initialPrice.get(i));
					System.out.println("Change in: "+coin + "   Initial Price: "+ initialPrice.get(i) + "   Current Price: "+currentPrice.get(i));
					Popup(initialPrice.get(i), currentPrice.get(i), coin);}
				}
				else if(currentPrice.get(i)>=20) {
					if(currentPrice.get(i)-initialPrice.get(i)>1) {coin  = GetKey(initial,initialPrice.get(i));
					System.out.println("Change in: "+coin + "   Initial Price: "+ initialPrice.get(i) + "   Current Price: "+currentPrice.get(i));
					Popup(initialPrice.get(i), currentPrice.get(i), coin);}
				}
				else if(currentPrice.get(i)>=10) {
					if(currentPrice.get(i)-initialPrice.get(i)>0.8) {coin  = GetKey(initial,initialPrice.get(i));
					System.out.println("Change in: "+coin + "   Initial Price: "+ initialPrice.get(i) + "   Current Price: "+currentPrice.get(i));
					Popup(initialPrice.get(i), currentPrice.get(i), coin);}
				}
				else if(currentPrice.get(i)>=5) {
					if(currentPrice.get(i)-initialPrice.get(i)>0.25) {coin  = GetKey(initial,initialPrice.get(i));
					System.out.println("Change in: "+coin + "   Initial Price: "+ initialPrice.get(i) + "   Current Price: "+currentPrice.get(i));
					Popup(initialPrice.get(i), currentPrice.get(i), coin);}
				}
				else if(currentPrice.get(i)>=0) {
					if(currentPrice.get(i)-initialPrice.get(i)>0.2) {coin  = GetKey(initial,initialPrice.get(i));
					System.out.println("Change in: "+coin + "   Initial Price: "+ initialPrice.get(i) + "   Current Price: "+currentPrice.get(i));
					Popup(initialPrice.get(i), currentPrice.get(i), coin);}
				}
				else if(currentPrice.get(i)<0) {
					int zero = CountZero(currentPrice.get(i));
					
					if(zero==0) {
						if(currentPrice.get(i)-initialPrice.get(i)>0.05) {coin  = GetKey(initial,initialPrice.get(i));
						System.out.println("Change in: "+coin + "   Initial Price: "+ initialPrice.get(i) + "   Current Price: "+currentPrice.get(i));
						Popup(initialPrice.get(i), currentPrice.get(i), coin);}
					}
					if(zero==1) {
						if(currentPrice.get(i)-initialPrice.get(i)>0.001) {coin  = GetKey(initial,initialPrice.get(i));
						System.out.println("Change in: "+coin + "   Initial Price: "+ initialPrice.get(i) + "   Current Price: "+currentPrice.get(i));
						Popup(initialPrice.get(i), currentPrice.get(i), coin);}
					}
					else if(zero==2) {
						if(currentPrice.get(i)-initialPrice.get(i)>0.0002) {coin  = GetKey(initial,initialPrice.get(i));
						System.out.println("Change in: "+coin + "   Initial Price: "+ initialPrice.get(i) + "   Current Price: "+currentPrice.get(i));
						Popup(initialPrice.get(i), currentPrice.get(i), coin);}
					}
					else if(zero==3) {
						if(currentPrice.get(i)-initialPrice.get(i)>0.00001) {coin  = GetKey(initial,initialPrice.get(i));
						System.out.println("Change in: "+coin + "   Initial Price: "+ initialPrice.get(i) + "   Current Price: "+currentPrice.get(i));
						Popup(initialPrice.get(i), currentPrice.get(i), coin);}
					}
					
				}
				else {
					System.out.println("Searching for change");
				}
				
			}

			Thread.sleep(60000);
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

	public static void main(String args[]) throws IOException, InterruptedException {

		List<ApiTemplate> initialPrice = GetData();
		Map<String, Float> initialData = SaveInitialData(initialPrice);
		SaveToExcel.SaveDataToExcel(initialPrice);
		
		GettingDifference(initialData);
		
	}

}
