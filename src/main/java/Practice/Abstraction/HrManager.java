package Practice.Abstraction;

import java.util.ArrayList;
import java.util.List;

public class HrManager {
    private List<Client> clients = new ArrayList<>();
    private PayCalculator calculator= new PayCalculator();
    public void addClient(Client client){
        clients.add(client);
    }
    public double getTotalPay(String method) throws IllegalAccessException {
        double total = 0;
        for(Client client : clients){
            total+=calculator.getPay(client,method);
        }
        return total;
    }
}
