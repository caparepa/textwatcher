package com.h2g2.textwatcher;

/**
 * Created by C. Serrano (cserrano@teravisiontech.com)
 * Teravision Technologies
 * Date: 2018/02/06
 */
public interface Constants {
    String APP_ID = "D201401D-0B4F-4817-8C4B-22AC9F84A9CB";
    String TESTFLIGHT_ID = "0729ef82-1585-4247-9d74-5e8af1468c6d";
    String SDPATH = "/Android/data/com.issyapp/userContent/.webContent/";
    String SDPATH_UPGRADES = "/Android/data/com.issyapp/apk/";
    String TRACER_PATH = "/Android/data/com.issyapp/.EventLog/";
    String USER_IMAGE_DIR = "/sdcard/Android/data/com.issyapp/userContent/.planResultDetails/";
    String USER_IMAGE_DIR_PARTIAL = "/Android/data/com.issyapp/userContent/.planResultDetails/";
    String SDPATH_ROOT = "/Android/data/com.issyapp";

    String ACCOUNT_TYPE = "com.issyapp.account";
    String AUTHTOKEN_TYPE = "com.issyapp.authtoken";
    String APP_URL = "com.issyapp.appurl";

    // Dates and references
    String DATE_FORMAT = "yyyy/MM/dd";
    String DATE_START = "1900/01/01";
    String DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm zz";
    String DATABASE_FILE_NAME = "issy.db";
    String DATABASE_DATE_FORMAT = "yyyy/MM/dd HH:mm:ss Z";
    String FTP_DATE_FORMAT = "yyyy_MM_dd_HHmmss";
    String SERVER_DATE_FORMAT = "yyyy/MM/dd hh:mm:ss";
    String PRs_LIST_DATES = "MMM dd yyyy hh:mm a z";
    String EXPIRATION_DATE_FORMAT = "yyyy/MM/dd hh:mm a";
    String PREFERENCE_FILE_NAME = "com.issyapp.preferencesFile";
    String LAST_SYNC_UPLOAD_DATE_PREFERENCE_NAME = "LastUploadUpdateTime";
    String LAST_SYNC_DOWNLOAD_DATE_PREFERENCE_NAME = "LastDownladUpdateTime";
    String LAST_SYNC_IN_PROGRESS_DATE = "LastSyncInProgressDate";
    String APP_LANGUAGE_PREFERENCE = "VersionLanguage";
    String TUTORIAL_STEP_X_ENABLED_PREFERENCE_NAME = "TutorialStep";

    String TWITTER_USER_TOKEN = "TwitterUserToken";
    String TWITTER_USER_SECRET = "TwitterUserSecret";

    // Facebook
    String PREFERENCE_FACEBOOK_TOKEN = "facebookToken";
    String PREFERENCE_FACEBOOK_POST_ALL = "facebookPostAll";

    // Services Parameters
    String PARAM_TOKEN = "tk";
    String PARAM_LAST_SYNC_DATE = "lastSyncDate";
    String PARAM_APPLICATION_ID = "appId";
    String PARAM_CUSTOMER_ID = "customerId";
    String CUSTOMER_ID = "1";
    String PARAM_IS_FACEBOOK_LOGIN = "isFacebookLogin";

    // Regex List
    String REGEX_EMAIL = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$";
    String REGEX_BIRTHDATE = "^\\d{1,2}\\/\\d{1,2}\\/\\d{4}$";

    // Service Emails
    String SERVICE_REPORT_EMAIL = "support@issyapp.com";

    // Progress Service Manager
    String DATA_DOWNLOADED = "ProgressServiceManagerCheckDataDownloaded";

    // Notification Service
    String PREFERENCE_NOTIFICATION_SOUND = "prefNotificationSound";
    String PREFERENCE_NOTIFICATION_VIBRATION = "prefNotificationVibration";
    String PREFERENCE_NOTIFICATION_ENABLED = "prefNotificationEnabled";
    String PREFERENCE_ALLOW_CREATE_LOCATIONS = "prefAllowCreateLocations";
    String PREFERENCE_USE_IN_APP_CAMERA = "prefUseInAppCamera";
}
