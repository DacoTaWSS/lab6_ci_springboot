package ec.edu.espe.lab6ci.repository;

import ec.edu.espe.lab6ci.model.Wallet;

import java.util.Optional;

public interface WalletRepository {
    Wallet save(Wallet wallet);
    Optional<Wallet> findById(String id);
    boolean existsByOwnerEmail(String id);

}
