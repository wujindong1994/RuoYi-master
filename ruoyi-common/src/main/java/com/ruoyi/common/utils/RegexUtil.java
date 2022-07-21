package com.ruoyi.common.utils;

import org.springframework.stereotype.Component;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RegexUtil {
        // 手机号验证规则
        private static String REGEX_PHONE = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
        // 整数验证规则
        private static String REGEX_NUMBER = "^[0-9]*[1-9][0-9]*$";
        // 邮箱验证规则
        //private static String REGEX_EMAIL = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$";

        /**
         * 校验手机号格式是否正确
         * @param phone
         * @return
         */
        public static  Boolean regexPhone(String phone){
            Boolean b;
            if(phone.length() != 11){
                b = false;
            }else{
                Pattern p = Pattern.compile(REGEX_PHONE);
                Matcher m = p.matcher(phone);
                boolean isMatch = m.matches();
                if(isMatch){
                    b = true;
                } else {
                    b = false;
                }
            }
            return b;
        }

        /**
         * 校验数据是否是整数
         * @param number
         * @return
         */
        public static  Boolean regexNumber(String number){
            Boolean b;
            if(number.length() == 0){
                b = false;
            }else{
                Pattern p = Pattern.compile(REGEX_NUMBER);
                Matcher m = p.matcher(number);
                boolean isMatch = m.matches();
                if(isMatch){
                    b = true;
                } else {
                    b = false;
                }
            }
            return b;
        }

        /**
         * 校验邮箱格式
         * @param
         * @return
         */
//        public static Boolean regexEmail(String email){
//            Boolean b;
//            if(email.length() == 0){
//                b = false;
//            }else{
//                // 编译正则表达式
//                Pattern pattern = Pattern.compile(REGEX_EMAIL);
//                // 忽略大小写的写法
//                // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
//                Matcher matcher = pattern.matcher(email);
//                // 字符串是否与正则表达式相匹配
//                boolean isMatch = matcher.matches();
//                if(isMatch){
//                    b = true;
//                } else {
//                    b = false;
//                }
//            }
//            return b;
//        }

        public static  void main(String[] args) {
            System.out.println("校验手机号格式：" + regexPhone("17816857416"));
            System.out.println("校验是否整数：" + regexNumber("1"));
            //System.out.println("校验邮箱格式：" + regexEmail("1902952234@qq.com"));
        }

}
