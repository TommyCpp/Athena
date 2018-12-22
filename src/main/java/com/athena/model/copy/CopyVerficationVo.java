package com.athena.model.copy;

/**
 * Created by zhongyangwu on 12/22/18.
 * <p>
 * value object used for verify whether the returned copy is damaged or not.
 */
public class CopyVerficationVo {
    Integer copyStatus;
    String description;


    public Integer getCopyStatus() {
        return copyStatus;
    }

    public void setCopyStatus(Integer copyStatus) {
        this.copyStatus = copyStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
