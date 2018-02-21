package com.zjzmjr.core.enums.project;

/**
 * 各个项目阶段的枚举
 * 
 * @author oms
 * @version $Id: GardenProjectStepEnum.java, v 0.1 2017-8-23 上午10:35:18 oms Exp $
 */
public enum GardenProjectStepEnum {

    P_10(10,"项目录入"),
    P_20(20,"待项目立项"),
    P_30(30,"待负责人反馈"),
    P_40(40,"待确立负责人"),
    P_50(50,"待录入招标公告"),
    P_60(60,"待上传报名拟定"),
    P_70(70,"待经营审核报名"),
    P_80(80,"待技术审核报名"),
    P_90(90,"待报名行政盖章"),
    P_100(100,"待上传报名文件"),
    P_110(110,"待上传招标文件"),
    P_120(120,"已上传招标文件"),
    P_130(130,"待经营审核保证金"),
    P_140(140,"待技术审核保证金"),
    P_150(150,"待财务审核保证金"),
    P_160(160,"待院长审核保证金"),
    P_170(170,"待出纳保证金打款"),
    P_180(180,"已上传招标文件"),
    P_190(190,"待上传商务标拟定"),
    P_200(200,"待经营审核商务标"),
    P_210(210,"待技术审核商务标"),
    P_220(220,"待院长审核商务标"),
    P_230(230,"待商务标行政盖章"),
    P_240(240,"待上传技术标拟定"),
    P_250(250,"待总工审核技术标"),
    P_260(260,"待院长审核技术标"),
    P_270(270,"待技术标行政盖章"),
    P_280(280,"待上传投标文件"),
    P_290(290,"待录入开标记录"),
    P_300(300,"待上传合同拟定"),
    P_310(310,"待经营审核合同"),
    P_315(315,"待总工审核合同"),
    P_320(320,"待技术审核合同"),
    P_330(330,"待财务审核合同"),
    P_340(340,"待法务审核合同"),
    P_350(350,"待院长审核合同"),
    P_360(360,"待合同行政盖章"),
    P_370(370,"待上传合同文件"),
    P_380(380,"待确认分包团队"),
    P_390(390,"待院长审核分包"),
    P_400(400,"待上传方案初稿"),
    P_410(410,"待总工审核方案"),
    P_420(420,"待上传方案成果"),
    P_430(430,"待上传扩初初稿"),
    P_440(440,"待总工审核扩初"),
    P_450(450,"待上传扩初成果"),
    P_460(460,"待上传施工初稿"),
    P_470(470,"待总工审核施工"),
    P_480(480,"待上传施工成果"),
    P_490(490,"待上传规划初稿"),
    P_500(500,"待总工审核规划"),
    P_510(510,"待上传规划成果"),
    P_520(520,"待上传规划成果"),
    P_530(530,"待终止"),
    P_610(610,"项目中止"),
    P_620(620,"项目复活"),
    P_630(630,"项目终止"),
    P_640(640,"终止"),
    ;
    
    private Integer value;

    private String message;

    private GardenProjectStepEnum(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    public Integer getValue() {
        return this.value;
    }

    public String getMessage() {
        return this.message;
    }

    public static GardenProjectStepEnum getByValue(Integer value) {
        if (value != null) {
            for (GardenProjectStepEnum enu : values()) {
                if (enu.value.equals(value)) {
                    return enu;
                }
            }
        }
        return null;
    }

    public static GardenProjectStepEnum getByValue(String message) {
        if (message != null) {
            for (GardenProjectStepEnum enu : values()) {
                if (enu.message.equals(message)) {
                    return enu;
                }
            }
        }
        return null;
    }
    
    public String toString() {
        return this.value + "|" + this.message;
    }
    
}
