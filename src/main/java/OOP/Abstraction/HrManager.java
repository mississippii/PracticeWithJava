package OOP.Abstraction;

import java.util.ArrayList;

public class HrManager {
    private ArrayList<Client> clients = new ArrayList<>();
    private PayCalculator calculator ;

    public HrManager(PayCalculator calculator){
        this.calculator= calculator;
    }
    public void addClient(Client client) {
        clients.add(client);
    }
    public double getTotalPay(){
        double total = 0;
        for(Client client : clients){
            total+=calculator.getPay(client);
        }
        return total;
    }
}
