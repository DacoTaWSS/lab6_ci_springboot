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
    private EquipmentPolicyClient riskClient;
    private WalletRepository walletRepository;

    @BeforeEach
    public void setUp() {
        walletRepository = Mockito.mock(WalletRepository.class);
        riskClient = Mockito.mock(EquipmentPolicyClient.class);
        walletService = new WalletService(walletRepository, riskClient);
    }

    /*@Test
    void createLoant_validData_shouldSaveAndReturnResponse() {
        // Arrange
        String codigo_equipo = "JJGG001";
        String email = "jjguerra5@espe.edu.ec";
        int dias = 10;

        Mockito.when(riskClient.isBlocked(email)).thenReturn(false);
        Mockito.when(walletRepository.existsByOwnerEmail(email)).thenReturn(false);
        Mockito.when(walletRepository.save(ArgumentMatchers.any(Wallet.class)))
                .thenAnswer(i -> i.getArgument(0));

        // Act
        WalletResponse response = walletService.createLoan(codigo_equipo,email,dias);

        // Assert
        Assertions.assertNotNull(response.getApprovalCode(), "El id del loan no debe ser null");

        Mockito.verify(riskClient).isBlocked(email);
        Mockito.verify(walletRepository).existsByOwnerEmail(email);
        Mockito.verify(walletRepository).save(ArgumentMatchers.any(Wallet.class));
    }*/

    @Test
    void createWallet_invalidEmail_shouldThrowException_andNotCallDependencies() {
        // Arrange
        String codigo_equipo = "JJGG001";
        String email = "jjguerra5espe.edu.ec";
        int dias = 10;

        // Act + Assert
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> walletService.createLoan(codigo_equipo,email, dias));

        // No debe llamar a ninguna dependencia porque falla la validación
        Mockito.verifyNoInteractions(walletRepository, riskClient);
    }

    @Test
    void createLoan_invalidDias_shouldThrowException_andNotCallDependencies() {
        // Arrange
        String codigo_equipo = "JJGG001";
        String email = "jjguerra5@espe.edu.ec";
        int dias = 20;

        // Act + Assert
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> walletService.createLoan(codigo_equipo,email, dias));

        // No debe llamar a ninguna dependencia porque falla la validación
        Mockito.verifyNoInteractions(walletRepository, riskClient);
    }

    /*@Test
    void equipo_ya_prestado(){
        //Arrange
        Wallet wallet = new Wallet("jjguerra5", "jjguerra5@espe.edu.ec",10);
        String walletId = wallet.getId();

        Mockito.when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));

        //Act + Assert
        IllegalStateException ex = Assertions.assertThrows(IllegalStateException.class,
                () -> walletService.createLoan("jjguerra5", "jjguerra5@espe.edu.ec",10));

        Assertions.assertEquals("Equipo prestado", ex.getMessage());
        Mockito.verify(walletRepository, Mockito.never()).save(ArgumentMatchers.any(Wallet.class));
    }*/









/*
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
    //deposito
    @Test
    void withdraw_insufficientFunds_shouldThrowException_andNotSave(){
        //Arrange
        Wallet wallet = new Wallet("jjguerra5@espe.edu.ec", 250.0);
        String walletId = wallet.getId();

        Mockito.when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));

        //Act + Assert
        IllegalStateException ex = Assertions.assertThrows(IllegalStateException.class,
                () -> walletService.withdraw(walletId, 1000.0));

        Assertions.assertEquals("Insufficient funds", ex.getMessage());
        Mockito.verify(walletRepository, Mockito.never()).save(ArgumentMatchers.any(Wallet.class));
    }

    // Saldo inicial negativo -> excepción, y NO llama a riskClient ni repo
    @Test
    void createWallet_negativeBalance_shouldThrowException() {
        String email = "jjguerra5@espe.edu.ec";
        double balance = -50.0;

        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> walletService.createWallet(email, balance));

        Assertions.assertEquals("Initial balance must be positive", ex.getMessage());
        Mockito.verifyNoInteractions(walletRepository, riskClient);
    }

    // Usuario bloqueado por riskClient -> excepción, y NO se guarda
    @Test
    void createWallet_blockedUser_shouldThrowException() {
        String email = "jjguerra5@espe.edu.ec";
        double balance = 100.0;

        Mockito.when(riskClient.isBlocked(email)).thenReturn(true);

        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> walletService.createWallet(email, balance));

        Assertions.assertEquals("User is blocked", ex.getMessage());
        Mockito.verify(riskClient).isBlocked(email);
        Mockito.verify(walletRepository, Mockito.never()).save(ArgumentMatchers.any(Wallet.class));
    }

    // Ya existe una billetera con ese email -> excepción
    @Test
    void createWallet_walletAlreadyExists_shouldThrowException() {
        String email = "jjguerra5@espe.edu.ec";
        double balance = 100.0;

        Mockito.when(riskClient.isBlocked(email)).thenReturn(false);
        Mockito.when(walletRepository.existsByOwnerEmail(email)).thenReturn(true);

        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> walletService.createWallet(email, balance));

        Assertions.assertEquals("Wallet already exists for this email", ex.getMessage());
        Mockito.verify(walletRepository, Mockito.never()).save(ArgumentMatchers.any(Wallet.class));
    }

    // Email null -> excepción (por la condición ownerEmail == null)
    @Test
    void createWallet_nullEmail_shouldThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> walletService.createWallet(null, 100.0));

        Mockito.verifyNoInteractions(walletRepository, riskClient);
    }

    // Monto <= 0 -> excepción "Amount must be positive"
    @Test
    void deposit_invalidAmount_shouldThrowException() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> walletService.deposit("123", 0.0));

        Assertions.assertEquals("Amount must be positive", ex.getMessage());
        Mockito.verifyNoInteractions(walletRepository);
    }

    // Caso feliz: deposita y retorna el balance actualizado
    @Test
    void deposit_validAmount_shouldReturnUpdatedBalance() {
        Wallet wallet = new Wallet("jjguerra5@espe.edu.ec", 100.0);
        String walletId = wallet.getId();

        Mockito.when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));
        Mockito.when(walletRepository.save(ArgumentMatchers.any(Wallet.class)))
                .thenAnswer(i -> i.getArgument(0));

        double newBalance = walletService.deposit(walletId, 50.0);

        Assertions.assertEquals(150.0, newBalance, 0.01);
        Mockito.verify(walletRepository).save(wallet);
    }

    // Billetera no encontrada -> OJO: aquí es IllegalStateException, no Argument
    @Test
    void withdraw_walletNotFound_shouldThrowIllegalState() {
        String walletId = "999";
        Mockito.when(walletRepository.findById(walletId)).thenReturn(Optional.empty());

        IllegalStateException ex = Assertions.assertThrows(IllegalStateException.class,
                () -> walletService.withdraw(walletId, 50.0));

        Assertions.assertEquals("Wallet not found", ex.getMessage());
        Mockito.verify(walletRepository, Mockito.never()).save(ArgumentMatchers.any(Wallet.class));
    }

    // Monto <= 0 -> excepción "Withdrawal amount must be positive"
    @Test
    void withdraw_invalidAmount_shouldThrowException() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> walletService.withdraw("123", -10.0));

        Assertions.assertEquals("Withdrawal amount must be positive", ex.getMessage());
        Mockito.verifyNoInteractions(walletRepository);
    }

    // Caso feliz: retira y retorna balance actualizado (con captor)
    @Test
    void withdraw_validAmount_shouldUpdateBalance_andSave() {
        Wallet wallet = new Wallet("jjguerra5@espe.edu.ec", 200.0);
        String walletId = wallet.getId();

        Mockito.when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));
        Mockito.when(walletRepository.save(ArgumentMatchers.any(Wallet.class)))
                .thenAnswer(i -> i.getArgument(0));

        ArgumentCaptor<Wallet> captor = ArgumentCaptor.forClass(Wallet.class);

        double newBalance = walletService.withdraw(walletId, 80.0);

        Mockito.verify(walletRepository).save(captor.capture());
        Assertions.assertEquals(120.0, captor.getValue().getBalance(), 0.01);
        Assertions.assertEquals(120.0, newBalance, 0.01);
    }

//https://github.com/DacoTaWSS/lab6_ci_springboot/compare/main...feature/withdraw?expand=1

*/

}
