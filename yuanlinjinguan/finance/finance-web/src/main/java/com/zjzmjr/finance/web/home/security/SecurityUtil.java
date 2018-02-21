package com.zjzmjr.finance.web.home.security;

import org.owasp.esapi.User;

import com.zjzmjr.core.base.security.ZjzmjrPasswordStrategy;
import com.zjzmjr.core.base.util.SpringContextUtil;


public class SecurityUtil {
    //默认账户冻结的失败次数
    private static final int defaultFrozenCount = 10;
    /** 登录错误多少次之后报错 */
    private static final int defaultLoginErrorCount = 6;
    /** 找回登录密码的URL */
    private static final String findLoginPassWord = "/home/login.htm";
    
    private static final ZjzmjrPasswordStrategy passwordStrategy = SpringContextUtil.getBean("passwordEncodeStrategy");
    
    /**
     * 运行时用户用户帐户是否安全。
     * <p>用于用户成功登录后检测帐户的安全性.
     * 这一般是出于在用户登录后可能用户帐户的状态变为不可用，这时出于安全考虑禁止一些操作的发生.
     * </p>
     * 
     * <p>该方法会在用户运行时更具系统设置的安全策略检测用户帐户的状态,一下情况认为帐户是安全的：
     *    <ul>
     *       <li>系统不启用运行时安全检测</li>
     *       <li>系统启用运行时安全检测，用户帐户状态为{@link UserAccStatusEnum#NORMAL 正常}</li>
     *    </ul>
     * </p>
     * 
     * @param user
     * @return
     */
    public static boolean isRuntimeUserSecure(User user){
        /*if(user!=null&&user.getAccStatus()!=null){
            if(isSecurityRuntimeCheck()){
                return true; // UserAccStatusEnum.NORMAL.getValue().equals(user.getAccStatus());
            }
            return true;
        }*/
        return false;
    }
    
    /**
     * 获取连续登录错误多少次后冻结帐户
     * 
     * @return
     */
    public static int  getDefaultFozenLoginCnt(){
        return defaultFrozenCount;
    }
    
    /**
     * 连续登录错误多少次之后提醒用户
     * 
     * @return
     */
    public static int getDefaultLoginErrorCnt() {
        return defaultLoginErrorCount;
    }

    /**
     * 找回登录密码的URL
     * 
     * @return
     */
    public static String getFindLoginPassWord() {
        return findLoginPassWord;
    }

    /**
     * 接口实名认证开关
     * 
     * @return
     */
    public static boolean nameInterfaceAuthenOn(){
        return true;
    }
    
    public static String encodePassWord(String pwd){
        return passwordStrategy.encodePassword(pwd);
    }
    
    /**
     * 是否运行时安全检查
     * 
     * @return
     */
    protected static boolean isSecurityRuntimeCheck(){
        return true;
    }
}
