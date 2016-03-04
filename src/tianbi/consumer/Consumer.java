package tianbi.consumer;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

import tianbi.consumer.ProcessMsg;

public class Consumer {
	private String connectionIP = "tcp://10.250.10.140:61616";
    private ConnectionFactory factory = null;
    private Connection connection = null;
    private Session session = null;
    private Destination destination = null;
    private MessageConsumer consumer = null;
    private int id;
    private boolean availabitlity = true;

    public Consumer(int id) {
    	this.id = id;
    	this.availabitlity = true;
    }
    
    private class MyMessageListener implements MessageListener {
    	public void onMessage(Message message) {
    		if(!availabitlity) {
    			System.out.println("*ERROR: Consumer"+id+" is too busy for connection.");
    			return;
    		}
			processMessage(message);
        }
    }   
    
    public void processMessage(Message message) {
    	this.availabitlity = false;
    	
    	System.out.println("Received: by consumer"+ id);
    	    	
    	ProcessMsg processor = new ProcessMsg();
    	processor.process(message,this.id);
    	    	
    	this.availabitlity = true;	
    }
    
    public void tryConnecting() {
    	try {
	    	factory = new ActiveMQConnectionFactory(connectionIP);
	        connection = factory.createConnection();
	        connection.start();	        
	        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	        destination = session.createQueue("AUTO_ANNOTATOR");
	        consumer = session.createConsumer(destination);		        
	        consumer.setMessageListener(new MyMessageListener());		        
    	} catch (JMSException e) {
            e.printStackTrace();
    	}
    }
    
    public void cleanUp() {
    	try {
			session.close();
			connection.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}
    }


}