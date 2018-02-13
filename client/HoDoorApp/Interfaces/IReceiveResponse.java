package hodoorhaifa.HoDoorApp.Interfaces;

public interface IReceiveResponse {
    void serverSentResponse(Boolean allGood);

    void changeErrorMessage(String message, boolean setVisible);
}
