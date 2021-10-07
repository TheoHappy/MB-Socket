package subscriber;

import broker.BrokerSocket;

public class SubscriberMessageReaderThread implements Runnable {
    private BrokerSocket transport;

    public SubscriberMessageReaderThread(BrokerSocket transport) {
        this.transport = transport;
    }

    public void run() {
        String messageFromServer;
        while (!(messageFromServer = transport.readAsync()).equals("disconnect"))
            System.out.println(messageFromServer);
        System.out.println("Disconnected from broker");
    }
}
