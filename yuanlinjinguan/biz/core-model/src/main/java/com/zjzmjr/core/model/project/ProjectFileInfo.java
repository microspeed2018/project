package com.zjzmjr.core.model.project;

import com.zjzmjr.core.model.admin.Admin;

/**
 * 上传资料及上传表信息
 * 
 * @author oms
 * @version $Id: ProjectFileInfo.java, v 0.1 2017-9-1 下午8:50:43 oms Exp $
 */
public class ProjectFileInfo extends ProjectFileUpload {

    /**  */
    private static final long serialVersionUID = -3865189797650124645L;

    private ProjectFile file;

    private GardenProject project;

    private Admin user;

    public ProjectFile getFile() {
        return file;
    }

    public void setFile(ProjectFile file) {
        this.file = file;
    }

    public GardenProject getProject() {
        return project;
    }

    public void setProject(GardenProject project) {
        this.project = project;
    }

    public Admin getUser() {
        return user;
    }

    public void setUser(Admin user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ProjectFileInfo [file=" + file + ", project=" + project + ", user=" + user + ", toString()=" + super.toString() + "]";
    }

}
