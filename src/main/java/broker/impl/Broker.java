package broker.impl;

import broker.BrokerSocket;

public class Broker {

    public static void main(String[] args) {
        System.out.println("BROKER");

        BrokerSocket broker = new BrokerSocketImplement();
        String msg;
        while (true) {
            while (!(msg = broker.readAsync()).isEmpty())
                if (msg.equals("invalid")) {
                    System.out.println("INVALID MESSAGE");
                } else {
                    System.out.println("VALID MESSAGE");
                    broker.writeAsync(msg);
                }
        }
    }
}

