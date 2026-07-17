package ec.edu.espe.lab6ci.service;

import ec.edu.espe.lab6ci.dto.WalletResponse;
import ec.edu.espe.lab6ci.model.Wallet;
import ec.edu.espe.lab6ci.repository.WalletRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.Optional;

public class WalletServiceTest {

    private WalletService walletService;
    private RiskClient riskClient;
    private WalletRepository walletRepository;

    @BeforeEach
    public void setUp() {
        walletRepository = Mockito.mock(WalletRepository.class);
        riskClient = Mockito.mock(RiskClient.class);
        walletService = new WalletService(walletRepository, riskClient);
    }

    // Crear cuenta con datos válidos, guardar y retornar respuesta
    @Test
    void createWallet_validData_shouldSaveAndReturnResponse() {
        // Arrange
        String email = "jjguerra5@espe.edu.ec";
        double balance = 150.0;

        Mockito.when(riskClient.isBlocked(email)).thenReturn(false);
        Mockito.when(walletRepository.existsByOwnerEmail(email)).thenReturn(false);
        Mockito.when(walletRepository.save(ArgumentMatchers.any(Wallet.class)))
                .thenAnswer(i -> i.getArgument(0));

        // Act
        WalletResponse response = walletService.createWallet(email, balance);

        // Assert
        Assertions.assertNotNull(response.getWalletId(), "El id del wallet no debe ser null");
        Assertions.assertEquals(balance, response.getBalance(), 0.01);

        Mockito.verify(riskClient).isBlocked(email);
        Mockito.verify(walletRepository).existsByOwnerEmail(email);
        Mockito.verify(walletRepository).save(ArgumentMatchers.any(Wallet.class));
    }

    // Crear cuenta con correo NO válido: lanzar excepción y no llamar dependencias
    @Test
    void createWallet_invalidEmail_shouldThrowException_andNotCallDependencies() {
        // Arrange
        String invalidEmail = "jjguerra5espe.edu.ec";
        double balance = 15.00;

        // Act + Assert
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> walletService.createWallet(invalidEmail, balance));

        // No debe llamar a ninguna dependencia porque falla la validación
        Mockito.verifyNoInteractions(walletRepository, riskClient);
    }

    // Depositar a una cuenta que no existe: lanzar excepción
    @Test
    void deposit_walletNotFound_shouldThrowException() {
        // Arrange
        String walletId = "123";
        double amount = 100.0;

        Mockito.when(walletRepository.findById(walletId)).thenReturn(Optional.empty());

        // Act + Assert
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> walletService.deposit(walletId, amount));

        Assertions.assertEquals("Wallet not found", ex.getMessage());
        Mockito.verify(walletRepository).findById(walletId);
        Mockito.verify(walletRepository, Mockito.never()).save(ArgumentMatchers.any(Wallet.class));
    }

    // Depositar: debe actualizar el balance y guardar (verificado con captor)
    @Test
    void deposit_shouldUpdateBalance_andSave_usingCaptor() {
        // Arrange
        Wallet wallet = new Wallet("jjguerra5@espe.edu.ec", 100.0);
        String walletId = wallet.getId();

        Mockito.when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));
        Mockito.when(walletRepository.save(ArgumentMatchers.any(Wallet.class)))
                .thenAnswer(i -> i.getArgument(0));

        ArgumentCaptor<Wallet> captor = ArgumentCaptor.forClass(Wallet.class);

        // Act
        double newBalance = walletService.deposit(walletId, 30.00);

        // Assert
        Mockito.verify(walletRepository).save(captor.capture());
        Wallet saved = captor.getValue();
        Assertions.assertEquals(newBalance, saved.getBalance());
    }
}
