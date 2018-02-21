package com.zjzmjr.core.enums.admin;

/**
 * 管理员事物类型,用于管理员事物表
 * 
 * @author Administrator
 * @version $Id: AdminBusinessTypeEnum.java, v 0.1 2016-10-9 下午7:29:20 Administrator Exp $
 */
public enum AdminBusinessTypeEnum {
    B_05(5,"项目保存"),
    B_10(10,"项目录入"),
    B_20(20,"项目立项"),
    B_30(30,"负责人反馈"),
    B_40(40,"确立负责人"),
    B_50(50,"录入招标公告"),
    B_60(60,"上传报名拟定"),
    B_70(70,"经营审核报名"),
    B_80(80,"技术审核报名"),
    B_90(90,"报名行政盖章"),
    B_100(100,"上传报名文件"),
    B_110(110,"上传招标文件"),
    B_120(120,"申请保证金"),
    B_130(130,"经营审核保证金"),
    B_140(140,"技术审核保证金"),
    B_150(150,"财务审核保证金"),
    B_160(160,"院长审核保证金"),
    B_170(170,"出纳保证金打款"),
    B_180(180,"录入是否热点"),
    B_190(190,"上传商务标拟定"),
    B_200(200,"经营审核商务标"),
    B_210(210,"技术审核商务标"),
    B_220(220,"院长审核商务标"),
    B_230(230,"商务标行政盖章"),
    B_240(240,"上传技术标拟定"),
    B_250(250,"总工审核技术标"),
    B_260(260,"院长审核技术标"),
    B_270(270,"技术标行政盖章"),
    B_280(280,"上传投标文件"),
    B_290(290,"录入开标记录"),
    B_300(300,"上传合同拟定"),
    B_310(310,"经营审核合同"),
    B_315(315,"总工审核合同"),
    B_320(320,"技术审核合同"),
    B_330(330,"财务审核合同"),
    B_340(340,"法务审核合同"),
    B_350(350,"院长审核合同"),
    B_360(360,"合同行政盖章"),
    B_370(370,"上传合同文件"),
    B_380(380,"申请合作比例"),
    B_390(390,"院办审核合作比例"),
    B_400(400,"上传方案初稿"),
    B_410(410,"总工审核方案"),
    B_420(420,"上传方案成果"),
    B_430(430,"上传扩初初稿"),
    B_440(440,"总工审核扩初"),
    B_450(450,"上传扩初成果"),
    B_460(460,"上传施工初稿"),
    B_470(470,"总工审核施工"),
    B_480(480,"上传施工成果"),
    B_490(490,"上传规划初稿"),
    B_500(500,"总工审核规划"),
    B_510(510,"上传规划成果"),
    B_520(520,"上传竣工报告"),
    B_610(610,"项目中止"),
    B_620(620,"项目复活"),
    B_630(630,"项目终止"),
    B_INSERT_ASSOCITED(800,"插入关联公司"),
    B_MODIFY_ASSOCITED(801,"修改关联公司"),
    B_INSERT_DEPARTMENT(802,"插入部门"),
    B_MODIFY_DEPARTMENT(803,"修改部门"),
    B_INSERT_JOB(804,"插入职位"),
    B_MODIFY_JOB(805,"修改职位"),
    B_INSERT_COMPANY(806,"插入公司信息"),
    B_MODIFY_COMPANY(807,"修改公司信息"),
    B_808(808,"项目资料上传"),
    B_809(809,"开发票申请"),
    B_810(810,"经营审核项目修改"),
    B_811(811,"经营审核合同修改"),
    B_812(812,"财务审核开票"),
    B_813(813,"出纳确认收款"),
    B_814(814,"财务审核项目支出"),
    B_815(815,"出纳确认付款"),
    B_817(817,"项目申请"),
    B_818(818,"项目修改"),
    B_819(819,"合同新增"),
    B_820(820,"合同修改"),
    B_821(821,"技术审核开票"),
    B_822(822,"院办审核项目支出"),
    B_823(823,"退保证金"),
    B_824(824,"财务审核退保证金"),
    B_825(825,"出纳确认退保证金"),

    B_IMPORT_DATA(899, "导入数据"),
    B_INSERT_BASIC(900, "插入基础数据"),
    B_MODIFY_BASIC(901, "修改基础数据"),
    B_DELETE_BASIC(902, "删除基础数据"),
    B_INSERT_USER(903,"用户添加"),
    B_MODIFY_USER_ACCOUNT(904,"修改用户账户信息"),
    B_INSERT_AUTH(905,"权限添加"),
    B_DELETE_AUTH(906,"权限删除"),
    B_INSERT_MENU(907,"新建菜单"),
    B_MODIFY_MENU(908,"修改菜单"),
    B_DELETE_MENU(909,"删除菜单"),
    B_BIND_USER_AUTH(910,"绑定用户权限"),
    B_BIND_USER_MENU(911,"绑定用户菜单"),
    B_INSERT_HOLIDAY(912,"添加节假日"),
    B_MODIFY_HOLIDAY(913,"修改节假日"),
    B_DELETE_HOLIDAY(914,"删除节假日"),
    B_INSERT_LOGO(915,"新建图标"),
    B_MODIFY_LOGO(916,"修改图标"),
    B_DELETE_LOGO(917,"删除图标"),
    B_SEND_SMS(918,"发送短信"),
    B_SEND_MESSAGE(919,"群发消息"),
    APP_RELEASE(920,"APP发布"),
    FIND_LOGINPWD(921,"找回密码"),
    RECRUITMENT_INSERT(1000,"招聘信息录入"),
    RECRUITMENT_UPDATE(1010,"招聘信息修改"),
    RECRUITMENT_EXPORT(1020,"招聘信息导出"),
    TALENT_INSERT(1030,"人才信息录入"),
    TALENT_UPDATE(1040,"人才信息修改"),
    TALENT_EXPORT(1050,"人才信息导出"),
    TALENT_INTERVIEW(1060,"人才提交面试"),
    INTERVIEW_SET(1070,"面试设置"),
    INTERVIEW_RESULT(1080,"面试结果评审"),
    ;
    
    private Integer value;

    private String message;
    
    private AdminBusinessTypeEnum(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    public Integer getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    public static AdminBusinessTypeEnum getByValue(Integer value) {
        if (value != null) {
            for (AdminBusinessTypeEnum enu : values()) {
                if (enu.value.equals(value)) {
                    return enu;
                }
            }
        }
        return null;
    }

    public String toString() {
        return value + "|" + message;
    }
    
}
