package Practice.Hello;

import Practice.Abstraction.Client;
import Practice.Abstraction.HrManager;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IllegalAccessException {
        HrManager manager = new HrManager();
        Client client0 = new Client("tanvir",15);
        Client client1 = new Client("jobong",6);
        Client client2 = new Client("kamal",8);
        manager.addClient(client0);
        manager.addClient(client1);
        manager.addClient(client2);
        double totalPayFixed = manager.getTotalPay("Fixed");
        double totalPayHourly = manager.getTotalPay("Hourly");
        //double total = manager.getTotalPay("Daily");
        System.out.println(totalPayFixed);
        System.out.println(totalPayHourly);
    }
}