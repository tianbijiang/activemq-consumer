package tianbi.consumer;

public class ConsumerMain {
	
	public static int NUM_OF_CONSUMERS = 3;
	
	public ConsumerMain() {
		
	}
	
	public static void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	    
    public static void thread(Runnable runnable, boolean daemon) {
        Thread myThread = new Thread(runnable);
        myThread.setDaemon(daemon);
        myThread.start();
    }

    public static class MyConsumer implements Runnable {

    	private int id = -1;
    	
    	public MyConsumer(int id) {
    		
			this.id = id;
			
		}
    	
        public void run() {
            try {
            	
            	Consumer consumer = new Consumer(this.id);
            	consumer.tryConnecting();
            	
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
	
	public static void main(String[] args) {
		
		for(int i = 0; i < NUM_OF_CONSUMERS; i++){			
			thread(new MyConsumer(i+1), false);
		}
	}
}
