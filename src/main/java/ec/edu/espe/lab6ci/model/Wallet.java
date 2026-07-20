package ec.edu.espe.lab6ci.model;

import java.util.UUID;

public class Wallet {
    private final String id;
    private final String equipmentCode;
    private final String borrowerEmail;
    private final int loanDays;
    private final String status;

    public Wallet(String equipmentCode, String borrowerEmail, int loanDays) {
        this.id = UUID.randomUUID().toString();
        this.equipmentCode = equipmentCode;
        this.borrowerEmail = borrowerEmail;
        this.loanDays = loanDays;
        this.status = "CREATED";
    }

    public String getId() {
        return id;
    }

    public String getEquipmentCode() {
        return equipmentCode;
    }

    public String getBorrowerEmail() {
        return borrowerEmail;
    }

    public int getLoanDays() {
        return loanDays;
    }

    public String getStatus() {
        return status;
    }
}
