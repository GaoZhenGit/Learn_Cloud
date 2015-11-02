package com.ibm.gz.learn_cloud;

/**
 * 常量储存处
 * Created by host on 2015/8/8.
 */
public interface Constant {
    class URL {
        //        public static final String Register="http://192.168.1.101/learncloud/index.php?m=Home&c=Index&a=register";
        public static final String Register = "http://2.learncloud.sinaapp.com/registerCheck.php";
        public static final String Login = "http://192.168.1.101/LearnCloud/index.php?m=Home&c=Index&a=login";
        //        public static final String Login="http://2.learncloud.sinaapp.com/login.php";
//        public static final String RequestCourse="http://2.learncloud.sinaapp.com/postRequest.php";
//        public static final String RequestCourse="http://1.marketonhand.sinaapp.com/requestTest.php";
        public static final String RequestCourse = "http://139.129.18.117/LearnCloud/index.php?m=Home&c=Index&a=firstpagecourse";
        public static final String SearchCourse = "http://192.168.1.101/learncloud/index.php?m=Home&c=Index&a=search";
    }

    class CODE {
        public static final String KeyValue = "key_value";
    }

    class FragmentType {
        public static final String PhoneLogin = "phone_login";
        public static final String EmailLogin = "email_login";
    }

    class DataKey {
        public static final String USER = "user";
        public static final String COURSE = "course";
        public static final String COURSE_LIST_CACHE = "course_list_cache";
        public static final String COURSE_LINE_CACHE = "course_line_cache";
        public static final String FIRSTSTART = "first_start";
        public static final String NOTE = "note";
        public static final String POPULARIZATION = "popularization";
        public static final String HISTORY = "history";
        public static final String NOTIFISTART = "notifi_start";
        public static final String SESS = "sess";
    }
}
