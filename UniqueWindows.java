import java.net.InetAddress;

public class UniqueWindows {

	private NewMsg[] displaywindow = new NewMsg[0];
	private int index;
	
public void addwindow(NewMsg brandnewwindow) {
		
		if(brandnewwindow instanceof NewMsg) {
	        
			if (displaywindow.length == 0) {
	         	displaywindow = new NewMsg[2];
	        } else if (displaywindow[displaywindow.length -1] != null) {
	         	resizeWindow();
	        }
				
		    for (int i = 0; i < displaywindow.length; i++) {
			   if(displaywindow[i] == null) {
				  displaywindow[i] = brandnewwindow;
				  index = i;
				
				System.out.println(displaywindow[i].getTitle() + " Window has been Added");
				
				break;
			    }
			
		    } 
		 
		 }
		
	} 


public NewMsg[] getWindows() {
	return displaywindow;
  }

public int getWindowIndex() {
	return index;
}

	private void resizeWindow() {
		
		NewMsg[] wind = new NewMsg[displaywindow.length * 2];
		
		for(int i = 0; i < displaywindow.length; i++) {
			wind[i] = displaywindow[i]; 
		}
		displaywindow = wind;
	}

public void removeIpAddress(int portnumber, InetAddress Ipaddress) {

		System.out.println("Searching for a window");
		
		displaywindow[index] = null;
		System.out.println( "Window has been found");
		System.out.println("Window has been removed ");
	
		int i = index;
	 if (index != displaywindow.length -1 )  {
	 while ((i < displaywindow.length - 1)  &&  (displaywindow[i + 1] != null)){
		 displaywindow[i] = displaywindow[i +1];
		 displaywindow[i + 1] = null;
		 i++;
	    }
	     } else {
		   displaywindow[i] = null;  
	    }
	  
		
}
	
	public int numberOfOpenWindows() {
		int num = 0;
		for(int i = 0; i < displaywindow.length; i++) {
			while (displaywindow[i] != null) {
				num++;
			}
		}
		System.out.println("Windows " + num);
		return num;
	}
	
	public boolean isInWindow(int portnumber, InetAddress Ipaddress) {
			
		for (int i = 0; i < displaywindow.length; i++) {
			
			if(displaywindow[i] == null) {
				return false;
				
			} else if((displaywindow[i].getPortNumber() == portnumber) && (displaywindow[i].getIPAddress().equals(Ipaddress))){
					
					index = i;
					
					System.out.println(displaywindow[i].getPortNumber() + " add " + displaywindow[i].getIPAddress());
				return true;
		      
			}
		  }
		
		return false;
	}
	
}
