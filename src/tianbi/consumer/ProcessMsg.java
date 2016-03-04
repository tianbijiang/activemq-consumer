package tianbi.consumer;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

public class ProcessMsg {
	
	public ProcessMsg() {
		
	}
	
	public void process(Message message, int id) {
    	String msg = "";
    	
    	if (message instanceof BytesMessage) {
    		BytesMessage text = (BytesMessage) message;
    		byte[] b = null;
			try {
				int length = (int) text.getBodyLength();
				b = new byte[length];
				text.readBytes(b);
				for (int i = 0; i < length; i++) {
	                msg += (char)b[i];
	            }
			} catch (JMSException e) {
				e.printStackTrace();
			}
        }
    	else if (message instanceof TextMessage) {
            TextMessage text = (TextMessage) message;
    		try {
    			msg = text.getText();
			} catch (JMSException e) {
				e.printStackTrace();
			}
    	}
    	
    	System.out.println(msg);
		  	
    	// RANDOM SLEEP
		int time = randInt(1000,3000);
    	try {
			TimeUnit.MILLISECONDS.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	    
    public int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
}
