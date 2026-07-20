package ec.edu.espe.lab6ci.dto;

public class WalletResponse {
    private final String loanId;
    private final String approvalCode;

    public WalletResponse(String loanId, String approvalCode) {
        this.loanId = loanId;
        this.approvalCode = approvalCode;
    }

    public String getLoanId() {
        return loanId;
    }

    public String getApprovalCode() {
        return approvalCode;
    }
}
