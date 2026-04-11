package oop.Interface;

// Interface extending another interface.
// Refundable inherits everything from Payable AND adds refund().
// Any class implementing Refundable must implement:
//   - pay()              (from Payable)
//   - getPaymentMethod() (from Payable)
//   - refund()           (from Refundable)

public interface Refundable extends Payable {

    void refund(double amount);  // new contract added on top of Payable
}
