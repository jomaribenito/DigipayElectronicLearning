package ph.digipay.digipayelectroniclearning.models;

import java.util.List;

public class MainForm {
    private List[] list;
    private List<Module> moduleList;
    private List<PDFForm> pdfFormList;
    private List<VideoForm> videoFormList;
    private List<Questionnaire> questionnaireList;

    public MainForm() {
    }

    public MainForm(List[] list) {
        list[0] = this.moduleList;
        list[1] = this.pdfFormList;
        list[2] = this.videoFormList;
        list[3] = this.questionnaireList;
    }

    public List<Module> getModuleList() {
        return moduleList;
    }

    public void setModuleList(List<Module> moduleList) {
        this.moduleList = moduleList;
    }

    public List<PDFForm> getPdfFormList() {
        return pdfFormList;
    }

    public void setPdfFormList(List<PDFForm> pdfFormList) {
        this.pdfFormList = pdfFormList;
    }

    public List<VideoForm> getVideoFormList() {
        return videoFormList;
    }

    public void setVideoFormList(List<VideoForm> videoFormList) {
        this.videoFormList = videoFormList;
    }

    public List<Questionnaire> getQuestionnaireList() {
        return questionnaireList;
    }

    public void setQuestionnaireList(List<Questionnaire> questionnaireList) {
        this.questionnaireList = questionnaireList;
    }
}
