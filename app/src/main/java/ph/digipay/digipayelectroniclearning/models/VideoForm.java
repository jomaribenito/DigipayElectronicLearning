package ph.digipay.digipayelectroniclearning.models;

public class VideoForm extends DatabaseObject {

    private String moduleUid;
    private String videoLink;
    private String name;
    private String description;

    public VideoForm() {
    }

    public String getModuleUid() {
        return moduleUid;
    }

    public void setModuleUid(String moduleUid) {
        this.moduleUid = moduleUid;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
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
