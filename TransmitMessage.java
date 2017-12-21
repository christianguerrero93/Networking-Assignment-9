import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class TransmitMessage {

 static DatagramSocket mysocket = null;
  
 public TransmitMessage() {
	
 }
	
 public TransmitMessage(DatagramSocket socket) {
	
		mysocket = socket;
	
 }
 
	public void SendMessage(String message, InetAddress DestinationIpAddress) {

		byte[] buffer = new byte[1000];

			String msg = message;
			buffer = msg.getBytes();
			
			try {
				DatagramPacket Packet = new DatagramPacket(buffer, 
														   message.length(),
														   DestinationIpAddress,
														   64000);
         
				System.out.println("Sending message = " + message);
				
				mysocket.send(Packet);
				
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(-1);
			}
			
			System.out.println("Message sent");
		
	}
	 
}
