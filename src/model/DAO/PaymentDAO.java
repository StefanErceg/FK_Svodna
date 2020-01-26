package model.DAO;

import model.DTO.Payment;

import java.util.List;

public interface PaymentDAO {

    List<Payment> payments();
    Payment getPaymentById(int id);
    boolean insert(Payment payment);
    boolean update(Payment payment);
}
