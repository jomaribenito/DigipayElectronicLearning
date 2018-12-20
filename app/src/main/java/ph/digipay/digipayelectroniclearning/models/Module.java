package ph.digipay.digipayelectroniclearning.models;

import android.support.annotation.Nullable;

public class Module extends DatabaseObject {

    private String name;
    private String description;

    public Module() {
    }

    public Module(String name, @Nullable String description) {
        this.name = name;
        this.description = description;
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
