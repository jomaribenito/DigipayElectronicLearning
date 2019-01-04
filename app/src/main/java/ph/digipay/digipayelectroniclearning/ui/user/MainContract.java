package ph.digipay.digipayelectroniclearning.ui.user;

public interface MainContract {
    void showModule();
    void showContent(String moduleUid);
    void showPDFList(String moduleUid);
    void showVideoList(String moduleUid);
    void showQuestionnaire(String moduleUid);
}
