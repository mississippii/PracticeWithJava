package oop.abstraction;

import java.util.ArrayList;

public class HrManager {
    private ArrayList<Client> clients = new ArrayList<>();
    private PayCalculator calculator;

    public HrManager(PayCalculator calculator) {
        this.calculator = calculator;
    }

    public void addClient(Client client) {
        clients.add(client);
    }

    public double getTotalPay() {
        return clients.stream().mapToDouble(client -> calculator.getPay(client)).sum();
    }
}
