package ec.edu.espe.lab6ci.service;

import ec.edu.espe.lab6ci.dto.WalletResponse;
import ec.edu.espe.lab6ci.model.Wallet;
import ec.edu.espe.lab6ci.repository.WalletRepository;

import java.util.Optional;

public class WalletService {
    private final WalletRepository walletRepository;
    private final EquipmentPolicyClient riskClient;


    public WalletService(WalletRepository walletRepository, EquipmentPolicyClient riskClient) {
        this.walletRepository = walletRepository;
        this.riskClient = riskClient;
    }


    public WalletResponse createLoan(String equipmentCode, String borrowerEmail, int loanDays) {
        if (equipmentCode == null || equipmentCode.isBlank()) {
            throw new IllegalArgumentException("El codigo no puede estar vacio");
        }

        if (borrowerEmail == null || !borrowerEmail.contains("@")) {
            throw new IllegalArgumentException("Invalid email");
        }

        if (loanDays < 1 || loanDays > 15) {
            throw new IllegalArgumentException("El dia debe estar entre 1 y 15");
        }

        if (riskClient.isBlocked(borrowerEmail)) {
            throw new IllegalArgumentException("User is blocked");
        }

        if (walletRepository.existsByOwnerEmail(borrowerEmail)) {
            throw new IllegalArgumentException("Equipo prestado");
        }

        Wallet wallet = new Wallet(equipmentCode, borrowerEmail, loanDays);
        Wallet save = walletRepository.save(wallet);

        return new WalletResponse(save.getId(), "APPROVED");
    }
/*
    //Crear una billetera
    public WalletResponse createWallet(String ownerEmail, double initialBalance){
        //Validaciones
        if(ownerEmail == null || !ownerEmail.contains("@")){
            throw new IllegalArgumentException("Invalid email");
        }
        if(initialBalance < 0){
            throw new IllegalArgumentException("Initial balance must be positive");
        }
        if(riskClient.isBlocked(ownerEmail)){
            throw new IllegalArgumentException("User is blocked");
        }
        //Regla de negocio: no duplicar la billetera por email
        if(walletRepository.existsByOwnerEmail(ownerEmail)){
            throw new IllegalArgumentException("Wallet already exists for this email");
        }

        Wallet wallet = new Wallet(ownerEmail, initialBalance);
        Wallet save = walletRepository.save(wallet);
        return new WalletResponse(save.getId(), save.getBalance());
    }
    //Depositar dinero en la billetera
    public double deposit(String walletId, double amount){
        if(amount <= 0){
            throw new IllegalArgumentException("Amount must be positive");
        }
        Optional<Wallet> found = walletRepository.findById(walletId);
        if(found.isEmpty()){
            throw new IllegalArgumentException("Wallet not found");
        }
        Wallet wallet = found.get();
        wallet.deposit(amount);
        walletRepository.save(wallet);
        return wallet.getBalance();
    }

    public double withdraw(String walletId, double amount){
        if(amount <= 0){
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new IllegalStateException("Wallet not found"));
        if(wallet.getBalance() < amount){
            throw new IllegalStateException("Insufficient funds");
        }
        wallet.withdraw(amount);
        walletRepository.save(wallet);
        return wallet.getBalance();
    }*/
}
