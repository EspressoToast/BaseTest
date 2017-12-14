package com.example.ljw.basedemo.utils;

/**
 * Created by chendiyou on 2015/1/28.
 *
 */
public class DataCheckUtil {

    //数字正则表达式
    private static final String NUMBER = "^[0-9]*";
    //英文大写正则表达式
    private static final String LETTER = "^[A-Za-z]*";
    //密码正则 必须包含字母,数字,特殊字符三种中的两种组合,并且长度在6-16位
    private static final String PWD_REGEX = "(?!^\\d+$)(?!^[a-zA-Z]+$)(?!^[~!@#$%^&*()_+-=]+$).{6,16}";

    public enum PhoneNumberCheck{
        /**校验通过**/
        CHECK_SUCCESS,
        /**号码为空**/
        NUMBER_IS_NULL,
        /**长度不足11位**/
        NUMBER_TOO_SHORT,
        /**电话号码首位不为1**/
        NUMBER_START_NOT_ONE,
        /**电话号码不全是数字组成**/
        NUMBER_IS_NOT_NUMBER
    }

    /**
     * 手机号码有效性校验
     */
    public static PhoneNumberCheck checkPhoneNumber(String phoneNumber){
        if(null == phoneNumber || phoneNumber.length() == 0){//号码为空
            return PhoneNumberCheck.NUMBER_IS_NULL;
        }else if(phoneNumber.length() != 11){//长度不足11位
            return PhoneNumberCheck.NUMBER_TOO_SHORT;
        }else if(!phoneNumber.startsWith("1")){//首位不为1
            return PhoneNumberCheck.NUMBER_START_NOT_ONE;
        }else if(!checkIsNumber(phoneNumber)){//不是全数字组成
            return PhoneNumberCheck.NUMBER_IS_NOT_NUMBER;
        }
        return PhoneNumberCheck.CHECK_SUCCESS;
    }

    /**
     * 正则校验
     *
     * @return
     */
    public static boolean regexMatches(String str, String regex){
       return str.matches(regex);
    }

    /**
     * 密码正则校验
     *
     */
    public static boolean checkPwdRegex(String str){
        return null != str && str.matches(PWD_REGEX);
    }

    /**
     * 判断字符串是否全由数字组成
     * @param str
     * @return
     */
    public static boolean checkIsNumber(String str) {
        return null != str && str.matches(NUMBER);
    }

    /**
     * 判断字符串是否全由字母组成
     * @param str
     * @return
     */
    public static boolean checkIsLetter(String str) {
        return null != str && str.matches(LETTER);
    }
}
