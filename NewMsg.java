import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import java.awt.Font;

public class NewMsg extends JFrame implements ActionListener{

	
    private JFrame mainchatwindow;
    private JTextArea textArea;
    private JTextField textField;
    private String MyName = "Christian";
	private InetAddress IPaddress = null;
    private int portnumber;
	private static UniqueWindows wind;
    private static TransmitMessage Input_Output;
    	
	public void settinginstance(UniqueWindows window, TransmitMessage newinput, JFrame beginChat, int portNumber, InetAddress address) {
		wind = window;
		Input_Output = newinput;
		mainchatwindow = beginChat;
		IPaddress = address;
		portnumber = portNumber;
		
	}
	
	public int getPortNumber() {
		return portnumber;
	}
	
	public InetAddress getIPAddress() {
		return IPaddress;
	}
	
	public NewMsg() {
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		JPanel contentPane = new JPanel();
		contentPane.setBackground(new Color(124, 252, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textArea = new JTextArea();
		textArea.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		textArea.setCaretColor(Color.BLACK);
		textArea.setForeground(Color.BLACK);
		textArea.setLineWrap(false);
		textArea.setEnabled(false);
		textArea.setEditable(false);
		textArea.setBounds(122, 244, 407, 153);
		textArea.setDisabledTextColor(new Color(139, 0, 0));
		contentPane.add(textArea);
		
		JButton	ExitButton = new JButton("Exit");
		ExitButton.setToolTipText("Exit Chat");
		ExitButton.setFont(new Font("Times New Roman", Font.BOLD, 13));
		ExitButton.setBounds(315, 242, 117, 29);
		contentPane.add(ExitButton);
		ExitButton.addActionListener(this);
		
		JButton SendButton = new JButton("Send");
		SendButton.setToolTipText("Send Message");
		SendButton.setFont(new Font("Times New Roman", Font.BOLD, 13));
		SendButton.setBounds(25, 242, 117, 29);
		contentPane.add(SendButton);
		SendButton.addActionListener(this);
		contentPane.getRootPane().setDefaultButton(SendButton);
		SendButton.setFocusable(true);

		textField = new JTextField();
		textField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		textField.setBounds(25, 194, 407, 36);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(25, 29, 407, 153);		
		contentPane.add(scrollPane);
		
		JButton NewChatButton = new JButton("New Chat");
		NewChatButton.setToolTipText("Open New Chat Window");
		NewChatButton.setFont(new Font("Times New Roman", Font.BOLD, 13));
		NewChatButton.setBounds(171, 242, 117, 29);
		contentPane.add(NewChatButton);
				
	}

	public void addmessagetoTextArea(String text) {
		if (textArea.getText().trim().length() == 0) {
			textArea.append(text);
		} else {
		textArea.append("\n" + text);
		}
	}
	
	
	@Override 
	public void actionPerformed(ActionEvent e) {
    
		JButton buttonClicked = (JButton) e.getSource();
		String btn = buttonClicked.getText();
		
		switch (btn) {
		case "Send":
			 String text = textField.getText();
			 textField.setText("");
			 Input_Output.SendMessage(text, IPaddress);
			 addmessagetoTextArea(MyName + ": " + text);
			 
			break;
			
		 case "Exit":
			 
			setVisible(false);
			
			
			if(wind.isInWindow(portnumber, IPaddress)) {
				System.out.println("Window is in array");
				System.out.println();
			wind.removeIpAddress(portnumber, IPaddress);
		
			} else {
				System.out.println("Window not found");
			}

			break;
			
		 case "New Chat":
			mainchatwindow.setVisible(true);
		}				
		
	 }
	}

