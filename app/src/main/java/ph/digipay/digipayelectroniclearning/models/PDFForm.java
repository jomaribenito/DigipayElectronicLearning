package ph.digipay.digipayelectroniclearning.models;

public class PDFForm extends DatabaseObject {

    private String moduleUid;
    private String pdfLink;
    private String name;
    private String description;

    public PDFForm() {
    }

    public String getModuleUid() {
        return moduleUid;
    }

    public void setModuleUid(String moduleUid) {
        this.moduleUid = moduleUid;
    }

    public String getPdfLink() {
        return pdfLink;
    }

    public void setPdfLink(String pdfLink) {
        this.pdfLink = pdfLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
