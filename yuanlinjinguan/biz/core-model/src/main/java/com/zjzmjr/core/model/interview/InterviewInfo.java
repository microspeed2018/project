package com.zjzmjr.core.model.interview;

import com.zjzmjr.core.model.admin.Admin;
import com.zjzmjr.core.model.recruitment.Recruitment;
import com.zjzmjr.core.model.talent.Talent;


public class InterviewInfo extends Interview{

    /**  */
    private static final long serialVersionUID = 4844601705903757637L;

    private Recruitment recruitment;
    
    private Talent talent;
    
    private Admin admin;

    private String resultText;
    
    private String createTimeText;
    
    public Recruitment getRecruitment() {
        return recruitment;
    }

    
    public void setRecruitment(Recruitment recruitment) {
        this.recruitment = recruitment;
    }

    
    public Talent getTalent() {
        return talent;
    }

    
    public void setTalent(Talent talent) {
        this.talent = talent;
    }

    
    public Admin getAdmin() {
        return admin;
    }

    
    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    
    public String getResultText() {
        return resultText;
    }


    
    public void setResultText(String resultText) {
        this.resultText = resultText;
    }


    
    public String getCreateTimeText() {
        return createTimeText;
    }


    
    public void setCreateTimeText(String createTimeText) {
        this.createTimeText = createTimeText;
    }


    @Override
    public String toString() {
        return "InterviewInfo [recruitment=" + recruitment + ", talent=" + talent + ", admin=" + admin + ", resultText=" + resultText + ", createTimeText=" + createTimeText + "]";
    }
    
    
}
