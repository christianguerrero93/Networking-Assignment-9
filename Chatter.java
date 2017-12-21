import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.*;


public class Chatter implements ActionListener{

	private static String OtherPersonName = "";
	private static String MyName = "Christian";
	private static String ReceiverName = "";
	private static InetAddress otherPersonIPAddress = null;
    private static String nameOfRequestedIpAddress;
    private static int portnumber = 64000;
    private static JFrame BeginMessage;
	private JTextField nametextField;
	private JLabel messageLabel;
	private static DatagramSocket mysocket = null;
	private static TransmitMessage sendingInstance;
	private static UniqueWindows wind;
	private static InetAddress myIpAddress = null;
	
	
	public Chatter() {
		initializeGui();
	}

	private void initializeGui() {
		BeginMessage = new JFrame();
		BeginMessage.getContentPane().setFont(new Font("Times New Roman", Font.BOLD, 14));
		BeginMessage.getContentPane().setBackground(new Color(124, 252, 0));
		BeginMessage.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		BeginMessage.setTitle("Begin Messaging");
		BeginMessage.setBounds(100, 100, 450, 300);
		BeginMessage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		BeginMessage.getContentPane().setLayout(null);
		
		nametextField = new JTextField();
		nametextField.setToolTipText("Enter name without spaces");
		nametextField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		nametextField.setBounds(75, 111, 272, 29);
		BeginMessage.getContentPane().add(nametextField);
		nametextField.setColumns(10);
		
		JButton BroadcastButton =  new JButton("Broadcast");
		BroadcastButton.setToolTipText("Begin Chat");
		BroadcastButton.setFont(new Font("Times New Roman", Font.BOLD, 13));
		BroadcastButton.addActionListener(this);
		
		BroadcastButton.setForeground(new Color(0, 0, 0));
		BroadcastButton.setBounds(27, 182, 93, 56);
		BeginMessage.getContentPane().add(BroadcastButton);
		
		JButton ExitButton = new JButton("Exit");
		ExitButton.setToolTipText("Exit Chat");
		ExitButton.setFont(new Font("Times New Roman", Font.BOLD, 13));
		ExitButton.setForeground(new Color(0, 0, 0));
		ExitButton.setBounds(303, 182, 93, 56);
		BeginMessage.getContentPane().add(ExitButton);
		ExitButton.addActionListener(this);
		
		messageLabel = new JLabel("Enter name of person you would like to chat");
		messageLabel.setToolTipText("Person you would like to speak with");
		messageLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		messageLabel.setForeground(new Color(0, 0, 0));
		messageLabel.setBounds(65, 72, 293, 39);
	    BeginMessage.getContentPane().add(messageLabel);
	}
	
    public static void main(String[] args) {
		
		wind = new UniqueWindows();
		
			try {
				myIpAddress = InetAddress.getLocalHost();
				
				
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(-1);
			}
			
			System.out.println("This is my Ip Address " + myIpAddress);
			
			try {
					mysocket = new DatagramSocket(portnumber);
					} catch (Exception e) {
						e.printStackTrace();
						System.exit(-1);
					}
			
			sendingInstance = new TransmitMessage(mysocket);
			Thread receiveThread = new Thread(new Runnable () {
				public void run() {
					receivingMethod();
				}
			});
			
			receiveThread.setName("Receive Thread");
			receiveThread.start();
		
			
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Chatter window = new Chatter();
					window.BeginMessage.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
    
	public static void receivingMethod() {
		
		InetAddress sourceIpAddress = null;
		byte[] inBuffer = new byte[1000];
		
		DatagramPacket inPacket = new DatagramPacket(inBuffer, inBuffer.length);

		  
		do {
			for ( int i = 0 ; i < inBuffer.length ; i++ ) {
				inBuffer[i] = ' ';
			}
			
			try {
				
				System.out.println("Waiting for a message");
				mysocket.receive(inPacket);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(-1);
			}

			sourceIpAddress = inPacket.getAddress();
			System.out.println("Source Ip Address when I receive:" + sourceIpAddress);
			
			String message = new String(inPacket.getData());
			
			System.out.println("Received message was = " + message);
					
			
			if ((isResquestingIpAdress(message)) && (nameOfRequestedIpAddress.equalsIgnoreCase(MyName)))
				{
				     sendingInstance.SendMessage("##### " + MyName + " ##### " + myIpAddress.getHostAddress(), sourceIpAddress);
				
				        if(!(wind.isInWindow(portnumber, sourceIpAddress)))
				           { 
					      NewMsg chatter = new NewMsg();
					      chatter.setTitle(OtherPersonName + "    IP Address: " + sourceIpAddress.getHostAddress());
					      chatter.settinginstance(wind, sendingInstance, BeginMessage, portnumber, sourceIpAddress);
					      wind.addwindow(chatter);
					      
				         } 					
			
			} else if (isMyIpAddressRequestAnswer(message)) {
				
				
				if(!(wind.isInWindow(portnumber, otherPersonIPAddress))){ 
					NewMsg chatter = new NewMsg();
					chatter.setTitle(OtherPersonName + "    IP Address: " + otherPersonIPAddress.getHostAddress());
					chatter.settinginstance(wind, sendingInstance, BeginMessage, portnumber, otherPersonIPAddress);
					chatter.setVisible(true);
					wind.addwindow(chatter);
					
					} else {
						
					int index = wind.getWindowIndex();
						wind.getWindows()[index].setVisible(true);
						
					}
				
			} else if(wind.isInWindow(portnumber, sourceIpAddress)){
				int index = wind.getWindowIndex();
				wind.getWindows()[index].addmessagetoTextArea(OtherPersonName + ": " + message);
				wind.getWindows()[index].setVisible(true);
			  
			} 
			
		} while(true);
	}
	
	private static boolean isResquestingIpAdress(String message) {
		
		if(message.startsWith("?????")) {
			String[] splittedMessage = message.split(" ");
			
			  if(splittedMessage[2].equalsIgnoreCase("#####")){
			      nameOfRequestedIpAddress = splittedMessage[1];
				  OtherPersonName = splittedMessage[3];
			   
			for (int i = 0; i < splittedMessage.length; i++) {
				System.out.println(splittedMessage[i]);
			}
			  return true;
			  }
		}
		return false;
	}
	
	private static boolean isMyIpAddressRequestAnswer(String message) {
		if(message.startsWith("#####")) {
			
			String[] splittedMessage = message.split(" ");
			if((splittedMessage[1].equalsIgnoreCase(ReceiverName)) && splittedMessage[2].equalsIgnoreCase("#####")){
				OtherPersonName = splittedMessage[1];
			try {
				otherPersonIPAddress =  InetAddress.getByName(splittedMessage[3]);
			} catch (UnknownHostException e) {
				
				e.printStackTrace();
			}  
			return true;
			
			}
		}
		return false;
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton buttonClicked = (JButton) e.getSource();
		
		switch (buttonClicked.getText()) {
		case "Broadcast":
			InetAddress broadscastIPAddress = null;
			
			try {
				
				broadscastIPAddress = InetAddress.getByName("255.255.255.255");
				
			} catch (UnknownHostException e1) {
				
				e1.printStackTrace();
			}
			 
			ReceiverName = nametextField.getText();
			nametextField.setText("");
			sendingInstance.SendMessage("????? " + ReceiverName + " ##### " + MyName, broadscastIPAddress);
			BeginMessage.setState(Frame.ICONIFIED);
			
			break;
		    case "Exit":
		   
			System.exit(JFrame.EXIT_ON_CLOSE);
			System.exit(0);
			break;
		}
		
	}
}
