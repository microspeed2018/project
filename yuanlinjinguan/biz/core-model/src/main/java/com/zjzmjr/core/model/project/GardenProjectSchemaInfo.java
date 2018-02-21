package com.zjzmjr.core.model.project;

import java.util.List;

/**
 * 项目及项目方案信息
 * 
 * @author oms
 * @version $Id: GardenProjectSchemaInfo.java, v 0.1 2017-8-21 上午9:29:44 oms Exp
 *          $
 */
public class GardenProjectSchemaInfo extends GardenProject {

    /**  */
    private static final long serialVersionUID = -6491399404563446629L;

    /**
     * 项目方案
     */
    private List<ProjectSchemaUserInfo> schema;

    /**
     * 方案内容
     */
    private String schemaContent;

    public List<ProjectSchemaUserInfo> getSchema() {
        return schema;
    }

    public void setSchema(List<ProjectSchemaUserInfo> schema) {
        this.schema = schema;
    }

    public String getSchemaContent() {
        return schemaContent;
    }

    public void setSchemaContent(String schemaContent) {
        this.schemaContent = schemaContent;
    }

    @Override
    public String toString() {
        return "GardenProjectSchemaInfo [schema=" + schema + ", schemaContent=" + schemaContent + "]";
    }

}
