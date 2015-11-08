import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class PostTV {

	
	public static void main(String[] args)  throws Exception {

		PostTV p = new PostTV();
		p.sendMsg("TEST");
	}
	
	
	
	public PostTV() {
		
	}
	
	
	public void sendMsg(String msg) {
		
		
	    msg =  "{\"text\":\"" + msg + "\", \"time\": 15000}";
		
	    System.out.println("Envoi du msg à la TV");
	    
		try {
			System.out.print("connect...");
			connect();
			Thread.sleep(500);
			System.out.print("sendMsg...");
			send(msg);
			System.out.print("disconnect...");
			disconnect();
			System.out.println("done!");
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
	}
	
	
	private void connect() throws Exception {
		 
		String url = "http://192.168.11.111:8080/ws/app/CallerID/connect";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("SLDeviceID", "CallerID");
		con.setRequestProperty("VendorID", "VenderME");
		con.setRequestProperty("DeviceName", "CallerID");
		con.setRequestProperty("GroupID", "mat");
		con.setRequestProperty("ProductID", "SMARTDev");
		
 
		String urlParameters = "";
 
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
 
		int responseCode = con.getResponseCode();
		//System.out.println("\nSending 'POST' request to URL : " + url);
		//System.out.println("Post parameters : " + urlParameters);
		//System.out.println("Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		//print result
		//System.out.println(response.toString());
 
	}

	
	private void send(String msg) throws Exception {
		 
		String url = "http://192.168.11.111:8080/ws/app/CallerID/queue";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("SLDeviceID", "CallerID");
		con.setRequestProperty("VendorID", "VenderME");
		con.setRequestProperty("ProductID", "SMARTDev");
		
 
		String urlParameters = msg;
 
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
 
		int responseCode = con.getResponseCode();
		//System.out.println("\nSending 'POST' request to URL : " + url);
		//System.out.println("Post parameters : " + urlParameters);
		//System.out.println("Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		//print result
		//System.out.println(response.toString());
 
	}
	

	
	private void disconnect() throws Exception {
		 
		String url = "http://192.168.11.111:8080/ws/app/CallerID/disconnect";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("SLDeviceID", "CallerID");

		
 
		String urlParameters = "";
 
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
 
		int responseCode = con.getResponseCode();
		//System.out.println("\nSending 'POST' request to URL : " + url);
		//System.out.println("Post parameters : " + urlParameters);
		//System.out.println("Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		//print result
		//System.out.println(response.toString());
 
	}
	
	
	
	
	
	
	
	
}
