package com.zjzmjr.admin.web.console.form;

import com.zjzmjr.core.common.biz.BaseForm;


public class QuestionAnswerQueryForm extends BaseForm{
    
    private static final long serialVersionUID = -6607382654133948147L;
    
    private Integer id;

    /**
     *   qaClassId 问答分类编号
     *   question 问题
     *   answer 答案
     *   qaClassName 问答分类名称
     */
    private Integer qaClassId;

    private String question;

    private String answer;
    
    private String qaClassName;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQaClassId() {
        return qaClassId;
    }

    public void setQaClassId(Integer qaClassId) {
        this.qaClassId = qaClassId;
    }

    public String getQuestion() {
        return question;
    }
    
    public void setQuestion(String question) {
        this.question = question;
    }
    
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQaClassName() {
        return qaClassName;
    }

    public void setQaClassName(String qaClassName) {
        this.qaClassName = qaClassName;
    }

    @Override
    public String resolveFiled(String arg0) {
        return null;
    }

    @Override
    public String toString() {
        return "QuestionAnswerQueryForm [id=" + id + ", qaClassId=" + qaClassId + ", question=" + question + ", answer=" + answer + ", qaClassName=" + qaClassName + "]";
    }

}
