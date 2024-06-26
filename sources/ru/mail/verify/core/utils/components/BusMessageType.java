package ru.mail.verify.core.utils.components;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/components/BusMessageType.class */
public enum BusMessageType {
    EMPTY,
    API_INTERNAL_INITIALIZE,
    API_INITIALIZE_API_GROUP,
    API_INTERNAL_SILENT_EXCEPTION,
    API_INTERNAL_UNHANDLED_EXCEPTION,
    API_RESET,
    API_STOP,
    API_SHUTDOWN,
    API_APPLICATION_START_CONFIG_CHANGED,
    INSTALL_REFERRER_RECEIVED,
    GCM_TOKEN_UPDATED,
    GCM_SERVER_INFO_RECEIVED,
    GCM_FETCHER_INFO_RECEIVED,
    GCM_MESSAGE_RECEIVED,
    GCM_TOKEN_UPDATE_FAILED,
    GCM_REFRESH_TOKEN,
    GCM_TOKEN_REFRESHED,
    GCM_NO_GOOGLE_PLAY_SERVICES_INSTALLED,
    SERVER_ACTION_ADDED,
    SERVER_ACTION_RESULT,
    SERVER_ACTION_REMOVED,
    SERVER_ACTION_FAILURE,
    EVENT_STORAGE_EVENTS_COLLECTED,
    EVENT_STORAGE_COLLECT_EVENT,
    EVENT_STORAGE_QUERY_TEMP_EVENTS,
    EVENT_STORAGE_QUERY_TEMP_EVENTS_COUNT,
    EVENT_STORAGE_QUERY_PERM_EVENTS,
    EVENT_STORAGE_REMOVE_EVENTS,
    EVENT_STORAGE_CLEAR,
    EVENT_STORAGE_CONNECTION_TIMEOUT,
    EVENT_MANAGER_PERIODIC_REPORT,
    EVENT_MANAGER_CHECK_REPORT,
    EVENT_MANAGER_FIRST_REPORT,
    NETWORK_STATE_CHANGED,
    NOTIFY_API_SETTINGS_UPDATE_NOW,
    NOTIFY_API_SETTINGS_PERIODIC_UPDATE,
    NOTIFY_API_SETTINGS_UPDATE_FIRST_TIME_THROTTLED,
    NOTIFY_INAPP_FETCH_DATA,
    NOTIFY_INAPP_REMOVE,
    NOTIFY_INCORRECT_SIGNATURE,
    NOTIFY_API_COLLECT_EVENT,
    NOTIFY_API_SET_PROPERTY,
    NOTIFY_API_SET_PROPERTY_BATCH,
    NOTIFY_API_COLLECT_EVENT_BATCH,
    NOTIFY_API_SET_USER_ID,
    NOTIFY_API_CHANGE_USER_ID,
    NOTIFY_API_ALLOW_DEVICE_ID_TRACKING,
    NOTIFY_API_REQUEST_PUSH_TOKEN,
    NOTIFY_API_REQUEST_USER_ID,
    NOTIFY_API_QUERY_PERMANENT_EVENTS,
    NOTIFY_CONTENT_STATE_CHANGED,
    NOTIFY_CHECK_TIMEOUT_NOTIFICATIONS,
    NOTIFY_GCM_HANDLER_MESSAGE_RECEIVED,
    PHONE_CHECKER_REQUEST_STARTED,
    SESSION_CONTAINER_ADDED_SESSION,
    SESSION_CONTAINER_REMOVED_SESSION,
    SAFETY_NET_RESPONE_RECEIVED,
    VERIFICATION_SESSION_STATE_CHANGED,
    VERIFICATION_SESSION_FETCHER_INFO_RECEIVED,
    VERIFICATION_SESSION_CALL_IN_EXECUTED,
    VERIFICATION_SESSION_CALL_IN_SEND_STATS,
    VERIFICATION_SESSION_MOBILEID_RESULTS_RECEIVED,
    POPUP_CONTAINER_NOTIFICATION_ADDED,
    POPUP_CONTAINER_NOTIFICATION_REMOVED,
    VERIFY_API_START_VERIFICATION,
    VERIFY_API_COMPLETE_VERIFICATION,
    VERIFY_API_CANCEL_VERIFICATION,
    VERIFY_API_RESET_VERIFICATION_CODE_ERROR,
    VERIFY_API_VERIFY_SMS_CODE,
    VERIFY_API_REQUEST_NEW_SMS_CODE,
    VERIFY_API_REQUEST_IVR,
    VERIFY_API_REQUEST_VERIFICATION_STATES,
    VERIFY_API_REQUEST_VERIFICATION_STATE,
    VERIFY_API_CHECK_PHONE_NUMBER,
    VERIFY_API_CHECK_ACCOUNT_VERIFICATION,
    VERIFY_API_CHECK_ACCOUNT_VERIFICATION_BY_SMS,
    VERIFY_API_SET_LOCALE,
    VERIFY_API_SET_DISABLE_SIM_DATA_SEND,
    VERIFY_API_SET_PROXY_ENDPOINT,
    VERIFY_API_REMOVE_PROXY_ENDPOINT,
    VERIFY_API_SET_API_ENDPOINTS,
    VERIFY_API_SEARCH_PHONE_ACCOUNTS,
    VERIFY_API_CHECK_NETWORK,
    VERIFY_API_RESET,
    VERIFY_API_SIGN_OUT,
    VERIFY_API_SOFT_SIGN_OUT,
    VERIFY_API_PREPARE_2FA_CHECK,
    VERIFY_API_REQUEST_GCM_TOKEN,
    VERIFY_API_IPC_CONNECT_RESULT_RECEIVED,
    VERIFY_API_HANDLE_SERVER_FAILURE,
    VERIFY_API_HANDLE_REQUEST_FAILURE,
    VERIFY_API_SESSION_SIGNATURE_GENERATED,
    PHONE_NUMBER_CHECKER_NEW_CHECK_STARTED,
    NOTIFY_MANAGER_MESSAGE_UPDATE_DATA,
    NOTIFY_INAPP_LOGIC_EVENTS_HANDLER_FIRE,
    NOTIFY_EVENTS_EVENT_RECEIVED,
    NOTIFY_MANAGER_BUTTON_ACTION,
    NOTIFY_MANAGER_DISMISS_ACTION,
    NOTIFY_MANAGER_URL_CLICK_ACTION,
    NOTIFY_MANAGER_LANDING_CLOSED,
    NOTIFY_MANAGER_OPEN_ACTION,
    NOTIFY_MANAGER_REQUEST_DATA,
    NOTIFY_MANAGER_LANDING_RENDER_FAILED,
    NOTIFY_STATE_SWITCH,
    NOTIFY_STATE_DELAYED_MESSAGE,
    NOTIFY_DOWNLOAD_BATCH,
    APP_ON_LOW_STORAGE,
    APP_STATE_TRACKER_APP_OPENED,
    APP_STATE_TRACKER_STATE_CHANGED,
    APP_STATE_TRACKER_ACTIVITY_STARTED,
    APP_STATE_TRACKER_ACTIVITY_STOPPED,
    APP_STATE_TRACKER_CHECK_STATE,
    APP_STATE_TRACKER_CHECK_SESSION_DURATION,
    APP_ON_LOW_MEMORY,
    SMS_STORAGE_ADDED,
    SMS_STORAGE_CLEARED,
    SMS_STORAGE_SMS_DIALOG_REMOVED,
    SMS_STORAGE_SMS_REMOVED,
    SMS_STORAGE_SMS_DIALOG_REQUESTED,
    SMS_STORAGE_SMS_DIALOGS_REQUESTED,
    SMS_RETRIEVER_MANAGER_WAIT_TIMEOUT,
    SMS_RETRIEVER_MANAGER_SUBSCRIBE_SUCCEEDED,
    SMS_RETRIEVER_MANAGER_SUBSCRIBE_FAILED,
    ACCOUNT_CHECKER_COMPLETED,
    ACCOUNT_CHECKER_REQUEST_SMS_INFO,
    ACCOUNT_CHECKER_SMS_PARSING_STARTED,
    ACCOUNT_CHECKER_SMS_PARSING_COMPLETED,
    ACCOUNT_CHECKER_NO_SMS_INFO_INTERNAL,
    ACCOUNT_CHECKER_SMS_SEARCH_COMPLETED_INTERNAL,
    ACCOUNT_CHECKER_GENERAL_ERROR_INTERNAL,
    ACCOUNT_CHECKER_MAX_SMS_INFO_WAIT_TIMEOUT_INTERNAL,
    APPLICATION_CHECKER_COMPLETED,
    APPLICATION_CHECKER_REQUEST_NONCE_INTERNAL,
    APPLICATION_CHECKER_COMPLETED_INTERNAL,
    FETCHER_MANAGER_FETCHER_STOPPED,
    FETCHER_MANAGER_FETCHER_STARTED,
    FETCHER_MANAGER_MESSAGE_RECEIVED,
    FETCHER_MANAGER_SERVER_INFO_RECEIVED,
    FETCHER_MANAGER_UPDATE_FETCHER_INFO_INTERNAL,
    FETCHER_EXECUTOR_MESSAGE_RECEIVED,
    FETCHER_EXECUTOR_SERVER_INFO_RECEIVED,
    FETCHER_EXECUTOR_FETCHER_STOPPED,
    FETCHER_EXECUTOR_FETCHER_STARTED,
    FETCHER_EXECUTOR_UPDATE_CACHE_HEADERS,
    FETCHER_EXECUTOR_UPDATE_FETCHER_INFO,
    SERVICE_NOTIFICATION_CONFIRM,
    SERVICE_NOTIFICATION_CANCEL,
    SERVICE_SMS_RECEIVED,
    SERVICE_CALL_RECEIVED,
    SERVICE_SMS_RETRIEVER_SMS_RECEIVED,
    SERVICE_IPC_SMS_MESSAGE_RECEIVED,
    SERVICE_IPC_CANCEL_NOTIFICATION_RECEIVED,
    SERVICE_IPC_FETCHER_STARTED_RECEIVED,
    SERVICE_IPC_FETCHER_STOPPED_RECEIVED,
    SERVICE_FETCHER_START_WITH_CHECK,
    SERVICE_SETTINGS_CHECK,
    SERVICE_SETTINGS_BATTERY_STATE_CHANGED,
    SERVICE_SETTINGS_NOTIFICATION_UNBLOCK,
    UI_NOTIFICATION_SETTINGS_SHOWN,
    UI_NOTIFICATION_SETTINGS_REPORT_REUSE,
    UI_NOTIFICATION_SETTINGS_REPORT_SPAM,
    UI_NOTIFICATION_SETTINGS_BLOCK,
    UI_NOTIFICATION_HISTORY_SHORTCUT_CREATED,
    UI_NOTIFICATION_HISTORY_OPENED,
    UI_NOTIFICATION_GET_INFO,
    UI_NOTIFICATION_OPENED,
    SMS_STORAGE_QUERY_SMS_DIALOGS,
    SMS_STORAGE_QUERY_SMS,
    SMS_STORAGE_REMOVE_SMS_DIALOG_ID,
    SMS_STORAGE_REMOVE_SMS_DIALOG_NAME,
    SMS_STORAGE_REMOVE_SMS_ID,
    SMS_STORAGE_REMOVE_SMS_NAME,
    SMS_STORAGE_INSERT_SMS,
    SMS_STORAGE_CLEAR,
    NOTIFICATION_BAR_MANAGER_ONGOING_NOTIFICATION_SHOWN,
    NOTIFICATION_BAR_MANAGER_BLOCKED_BY_USER,
    APP_MOVE_TO_BACKGROUND,
    APP_MOVE_TO_FOREGROUND
}
