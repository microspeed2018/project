package com.zjzmjr.core.model.talent;

import java.util.List;

import com.zjzmjr.core.model.recruitment.Recruitment;


public class TalentInfo extends Talent{

    /**  */
    private static final long serialVersionUID = 7990682132184845062L;
    
    private String maritalName;
    
    private String fertilityName;
    
    private String politicalName;

    /**
     * 招聘表
     */
    private Recruitment recruitment;
    
    /**
     * 人才学历
     */
    private List<TalentEducation>  talentEducation;
    
    /**
     * 人才工作
     */
    private List<TalentJob>  talentJob;
    
    /**
     * 人才家属
     */
    private List<TalentFamily>  talentFamily;
    
    /**
     * 人才附件
     */
    private List<TalentDocument>  talentDocument;

    
    public Recruitment getRecruitment() {
        return recruitment;
    }

    
    public void setRecruitment(Recruitment recruitment) {
        this.recruitment = recruitment;
    }

    
    public List<TalentEducation> getTalentEducation() {
        return talentEducation;
    }

    
    public void setTalentEducation(List<TalentEducation> talentEducation) {
        this.talentEducation = talentEducation;
    }

    
    public List<TalentJob> getTalentJob() {
        return talentJob;
    }

    
    public void setTalentJob(List<TalentJob> talentJob) {
        this.talentJob = talentJob;
    }

    
    public List<TalentFamily> getTalentFamily() {
        return talentFamily;
    }

    
    public void setTalentFamily(List<TalentFamily> talentFamily) {
        this.talentFamily = talentFamily;
    }

    
    public List<TalentDocument> getTalentDocument() {
        return talentDocument;
    }

    
    public void setTalentDocument(List<TalentDocument> talentDocument) {
        this.talentDocument = talentDocument;
    }

    
    public String getMaritalName() {
        return maritalName;
    }


    
    public void setMaritalName(String maritalName) {
        this.maritalName = maritalName;
    }


    
    public String getFertilityName() {
        return fertilityName;
    }


    
    public void setFertilityName(String fertilityName) {
        this.fertilityName = fertilityName;
    }


    
    public String getPoliticalName() {
        return politicalName;
    }


    
    public void setPoliticalName(String politicalName) {
        this.politicalName = politicalName;
    }


    @Override
    public String toString() {
        return "TalentInfo [maritalName=" + maritalName + ", fertilityName=" + fertilityName + ", politicalName=" + politicalName + ", recruitment=" + recruitment + ", talentEducation=" + talentEducation + ", talentJob=" + talentJob + ", talentFamily=" + talentFamily + ", talentDocument=" + talentDocument + "]";
    }
    
    
}
