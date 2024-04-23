package ru.mail.libverify.api;

import android.net.Network;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Pair;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import java.io.IOException;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import javax.inject.Inject;
import kotlin.jvm.internal.Intrinsics;
import ru.mail.libverify.AppStateModel;
import ru.mail.libverify.R;
import ru.mail.libverify.api.VerificationApi;
import ru.mail.libverify.api.i;
import ru.mail.libverify.api.n;
import ru.mail.libverify.d.b;
import ru.mail.libverify.f.f;
import ru.mail.libverify.gcm.ServerInfo;
import ru.mail.libverify.gcm.ServerNotificationMessage;
import ru.mail.libverify.i.j;
import ru.mail.libverify.i.p;
import ru.mail.libverify.l.b;
import ru.mail.libverify.platform.core.ServiceType;
import ru.mail.libverify.platform.storage.KeyValueStorage;
import ru.mail.libverify.storage.InstanceConfig;
import ru.mail.libverify.utils.permissions.a;
import ru.mail.platform.libverify.sms.SmsRetrieverService;
import ru.mail.verify.core.api.AlarmManager;
import ru.mail.verify.core.api.ApiGroup;
import ru.mail.verify.core.api.ApiManager;
import ru.mail.verify.core.api.ApiPlugin;
import ru.mail.verify.core.api.PluginListBuilder;
import ru.mail.verify.core.gcm.GcmRegistrar;
import ru.mail.verify.core.platform.TimeProvider;
import ru.mail.verify.core.requests.ActionExecutor;
import ru.mail.verify.core.requests.ConnectivityHelper;
import ru.mail.verify.core.requests.RequestBase;
import ru.mail.verify.core.requests.Utils;
import ru.mail.verify.core.requests.response.ResponseBase;
import ru.mail.verify.core.storage.BroadcastManager;
import ru.mail.verify.core.timer.TimerManager;
import ru.mail.verify.core.ui.notifications.NotificationBarManager;
import ru.mail.verify.core.ui.notifications.NotificationBase;
import ru.mail.verify.core.utils.AlarmReceiver;
import ru.mail.verify.core.utils.ClientException;
import ru.mail.verify.core.utils.DebugUtils;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.GCMTokenCheckType;
import ru.mail.verify.core.utils.NotificationUtils;
import ru.mail.verify.core.utils.ServerException;
import ru.mail.verify.core.utils.SessionIdGeneratorImpl;
import ru.mail.verify.core.utils.components.BusMessageType;
import ru.mail.verify.core.utils.components.MessageBus;
import ru.mail.verify.core.utils.components.MessageBusUtils;
import ru.mail.verify.core.utils.components.MessageHandler;
import ru.mail.verify.core.utils.json.JsonParseException;
import ru.mail.verify.core.utils.json.JsonParser;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/q.class */
public final class q implements VerificationApi, ApiGroup, ru.mail.libverify.api.i, MessageHandler {
    private final ru.mail.libverify.m.l a;
    private final n b;
    private final ru.mail.libverify.l.b f;
    private final m g;
    private final Lazy<ru.mail.libverify.e.c> h;
    private final Lazy<ru.mail.libverify.api.a> i;
    private final Lazy<ru.mail.libverify.api.f> j;
    private final CommonContext m;
    private final AtomicReference<String[]> n;
    private final ru.mail.libverify.p.e o;
    private final ApiManager p;
    private final MessageBus q;
    private final e0 r;
    private final Lazy<AlarmManager> s;
    private final TimerManager t;
    private final ru.mail.libverify.q.c u;
    private final TimeProvider v;
    private final Lazy<GcmRegistrar> w;
    private final Thread.UncaughtExceptionHandler x;
    private final RejectedExecutionHandler y;
    private final Lazy<NotificationBarManager> z;
    private final Lazy<ActionExecutor> A;
    private final ru.mail.libverify.i.e B;
    private final ru.mail.libverify.m.n C;
    private final ru.mail.libverify.a.b D;
    private ru.mail.libverify.k.m E;
    private ru.mail.libverify.k.h F;
    private ru.mail.libverify.t.a G;
    private l H;
    private ru.mail.libverify.m.o I;
    private final ru.mail.libverify.r.c J;
    private final AppStateModel.a K;
    private ru.mail.libverify.m.p L;
    private ru.mail.libverify.m.r M;
    private final AtomicReference<String> N;
    private final HashSet c = new HashSet();
    private final HashSet d = new HashSet();
    private final HashSet e = new HashSet();
    private final HashSet<String> k = new HashSet<>();
    private final h l = new h();

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/q$a.class */
    public static /* synthetic */ class a {
        static final /* synthetic */ int[] a;
        static final /* synthetic */ int[] b;
        static final /* synthetic */ int[] c;
        static final /* synthetic */ int[] d;
        static final /* synthetic */ int[] e;
        static final /* synthetic */ int[] f;
        static final /* synthetic */ int[] g;
        static final /* synthetic */ int[] h;

        static {
            int[] iArr = new int[ServiceType.values().length];
            h = iArr;
            try {
                try {
                    try {
                        iArr[ServiceType.Huawei.ordinal()] = 1;
                    } catch (NoSuchFieldError unused) {
                    }
                } catch (NoSuchFieldError unused2) {
                }
            } catch (NoSuchFieldError unused3) {
            }
            try {
                try {
                    try {
                        try {
                            h[ServiceType.Firebase.ordinal()] = 2;
                        } catch (NoSuchFieldError unused4) {
                        }
                    } catch (NoSuchFieldError unused5) {
                    }
                } catch (NoSuchFieldError unused6) {
                }
            } catch (NoSuchFieldError unused7) {
            }
            int[] iArr2 = new int[BusMessageType.values().length];
            g = iArr2;
            try {
                try {
                    try {
                        iArr2[BusMessageType.VERIFY_API_IPC_CONNECT_RESULT_RECEIVED.ordinal()] = 1;
                    } catch (NoSuchFieldError unused8) {
                    }
                } catch (NoSuchFieldError unused9) {
                }
            } catch (NoSuchFieldError unused10) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.API_INTERNAL_SILENT_EXCEPTION.ordinal()] = 2;
                        } catch (NoSuchFieldError unused11) {
                        }
                    } catch (NoSuchFieldError unused12) {
                    }
                } catch (NoSuchFieldError unused13) {
                }
            } catch (NoSuchFieldError unused14) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.API_INTERNAL_UNHANDLED_EXCEPTION.ordinal()] = 3;
                        } catch (NoSuchFieldError unused15) {
                        }
                    } catch (NoSuchFieldError unused16) {
                    }
                } catch (NoSuchFieldError unused17) {
                }
            } catch (NoSuchFieldError unused18) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.VERIFY_API_START_VERIFICATION.ordinal()] = 4;
                        } catch (NoSuchFieldError unused19) {
                        }
                    } catch (NoSuchFieldError unused20) {
                    }
                } catch (NoSuchFieldError unused21) {
                }
            } catch (NoSuchFieldError unused22) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.VERIFY_API_COMPLETE_VERIFICATION.ordinal()] = 5;
                        } catch (NoSuchFieldError unused23) {
                        }
                    } catch (NoSuchFieldError unused24) {
                    }
                } catch (NoSuchFieldError unused25) {
                }
            } catch (NoSuchFieldError unused26) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.VERIFY_API_RESET_VERIFICATION_CODE_ERROR.ordinal()] = 6;
                        } catch (NoSuchFieldError unused27) {
                        }
                    } catch (NoSuchFieldError unused28) {
                    }
                } catch (NoSuchFieldError unused29) {
                }
            } catch (NoSuchFieldError unused30) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.VERIFY_API_CANCEL_VERIFICATION.ordinal()] = 7;
                        } catch (NoSuchFieldError unused31) {
                        }
                    } catch (NoSuchFieldError unused32) {
                    }
                } catch (NoSuchFieldError unused33) {
                }
            } catch (NoSuchFieldError unused34) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.VERIFY_API_REQUEST_NEW_SMS_CODE.ordinal()] = 8;
                        } catch (NoSuchFieldError unused35) {
                        }
                    } catch (NoSuchFieldError unused36) {
                    }
                } catch (NoSuchFieldError unused37) {
                }
            } catch (NoSuchFieldError unused38) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.VERIFY_API_CHECK_PHONE_NUMBER.ordinal()] = 9;
                        } catch (NoSuchFieldError unused39) {
                        }
                    } catch (NoSuchFieldError unused40) {
                    }
                } catch (NoSuchFieldError unused41) {
                }
            } catch (NoSuchFieldError unused42) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.VERIFY_API_REQUEST_IVR.ordinal()] = 10;
                        } catch (NoSuchFieldError unused43) {
                        }
                    } catch (NoSuchFieldError unused44) {
                    }
                } catch (NoSuchFieldError unused45) {
                }
            } catch (NoSuchFieldError unused46) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.VERIFY_API_VERIFY_SMS_CODE.ordinal()] = 11;
                        } catch (NoSuchFieldError unused47) {
                        }
                    } catch (NoSuchFieldError unused48) {
                    }
                } catch (NoSuchFieldError unused49) {
                }
            } catch (NoSuchFieldError unused50) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.VERIFY_API_REQUEST_VERIFICATION_STATE.ordinal()] = 12;
                        } catch (NoSuchFieldError unused51) {
                        }
                    } catch (NoSuchFieldError unused52) {
                    }
                } catch (NoSuchFieldError unused53) {
                }
            } catch (NoSuchFieldError unused54) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.VERIFY_API_REQUEST_VERIFICATION_STATES.ordinal()] = 13;
                        } catch (NoSuchFieldError unused55) {
                        }
                    } catch (NoSuchFieldError unused56) {
                    }
                } catch (NoSuchFieldError unused57) {
                }
            } catch (NoSuchFieldError unused58) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.VERIFY_API_CHECK_ACCOUNT_VERIFICATION.ordinal()] = 14;
                        } catch (NoSuchFieldError unused59) {
                        }
                    } catch (NoSuchFieldError unused60) {
                    }
                } catch (NoSuchFieldError unused61) {
                }
            } catch (NoSuchFieldError unused62) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.VERIFY_API_SET_LOCALE.ordinal()] = 15;
                        } catch (NoSuchFieldError unused63) {
                        }
                    } catch (NoSuchFieldError unused64) {
                    }
                } catch (NoSuchFieldError unused65) {
                }
            } catch (NoSuchFieldError unused66) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.VERIFY_API_SET_DISABLE_SIM_DATA_SEND.ordinal()] = 16;
                        } catch (NoSuchFieldError unused67) {
                        }
                    } catch (NoSuchFieldError unused68) {
                    }
                } catch (NoSuchFieldError unused69) {
                }
            } catch (NoSuchFieldError unused70) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.VERIFY_API_SET_API_ENDPOINTS.ordinal()] = 17;
                        } catch (NoSuchFieldError unused71) {
                        }
                    } catch (NoSuchFieldError unused72) {
                    }
                } catch (NoSuchFieldError unused73) {
                }
            } catch (NoSuchFieldError unused74) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.VERIFY_API_SET_PROXY_ENDPOINT.ordinal()] = 18;
                        } catch (NoSuchFieldError unused75) {
                        }
                    } catch (NoSuchFieldError unused76) {
                    }
                } catch (NoSuchFieldError unused77) {
                }
            } catch (NoSuchFieldError unused78) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.VERIFY_API_REMOVE_PROXY_ENDPOINT.ordinal()] = 19;
                        } catch (NoSuchFieldError unused79) {
                        }
                    } catch (NoSuchFieldError unused80) {
                    }
                } catch (NoSuchFieldError unused81) {
                }
            } catch (NoSuchFieldError unused82) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.VERIFY_API_SEARCH_PHONE_ACCOUNTS.ordinal()] = 20;
                        } catch (NoSuchFieldError unused83) {
                        }
                    } catch (NoSuchFieldError unused84) {
                    }
                } catch (NoSuchFieldError unused85) {
                }
            } catch (NoSuchFieldError unused86) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.VERIFY_API_CHECK_NETWORK.ordinal()] = 21;
                        } catch (NoSuchFieldError unused87) {
                        }
                    } catch (NoSuchFieldError unused88) {
                    }
                } catch (NoSuchFieldError unused89) {
                }
            } catch (NoSuchFieldError unused90) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.VERIFY_API_RESET.ordinal()] = 22;
                        } catch (NoSuchFieldError unused91) {
                        }
                    } catch (NoSuchFieldError unused92) {
                    }
                } catch (NoSuchFieldError unused93) {
                }
            } catch (NoSuchFieldError unused94) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.VERIFY_API_SIGN_OUT.ordinal()] = 23;
                        } catch (NoSuchFieldError unused95) {
                        }
                    } catch (NoSuchFieldError unused96) {
                    }
                } catch (NoSuchFieldError unused97) {
                }
            } catch (NoSuchFieldError unused98) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.VERIFY_API_SOFT_SIGN_OUT.ordinal()] = 24;
                        } catch (NoSuchFieldError unused99) {
                        }
                    } catch (NoSuchFieldError unused100) {
                    }
                } catch (NoSuchFieldError unused101) {
                }
            } catch (NoSuchFieldError unused102) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.VERIFY_API_PREPARE_2FA_CHECK.ordinal()] = 25;
                        } catch (NoSuchFieldError unused103) {
                        }
                    } catch (NoSuchFieldError unused104) {
                    }
                } catch (NoSuchFieldError unused105) {
                }
            } catch (NoSuchFieldError unused106) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.VERIFY_API_CHECK_ACCOUNT_VERIFICATION_BY_SMS.ordinal()] = 26;
                        } catch (NoSuchFieldError unused107) {
                        }
                    } catch (NoSuchFieldError unused108) {
                    }
                } catch (NoSuchFieldError unused109) {
                }
            } catch (NoSuchFieldError unused110) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.VERIFY_API_REQUEST_GCM_TOKEN.ordinal()] = 27;
                        } catch (NoSuchFieldError unused111) {
                        }
                    } catch (NoSuchFieldError unused112) {
                    }
                } catch (NoSuchFieldError unused113) {
                }
            } catch (NoSuchFieldError unused114) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.GCM_MESSAGE_RECEIVED.ordinal()] = 28;
                        } catch (NoSuchFieldError unused115) {
                        }
                    } catch (NoSuchFieldError unused116) {
                    }
                } catch (NoSuchFieldError unused117) {
                }
            } catch (NoSuchFieldError unused118) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.GCM_SERVER_INFO_RECEIVED.ordinal()] = 29;
                        } catch (NoSuchFieldError unused119) {
                        }
                    } catch (NoSuchFieldError unused120) {
                    }
                } catch (NoSuchFieldError unused121) {
                }
            } catch (NoSuchFieldError unused122) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.GCM_FETCHER_INFO_RECEIVED.ordinal()] = 30;
                        } catch (NoSuchFieldError unused123) {
                        }
                    } catch (NoSuchFieldError unused124) {
                    }
                } catch (NoSuchFieldError unused125) {
                }
            } catch (NoSuchFieldError unused126) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.GCM_TOKEN_UPDATED.ordinal()] = 31;
                        } catch (NoSuchFieldError unused127) {
                        }
                    } catch (NoSuchFieldError unused128) {
                    }
                } catch (NoSuchFieldError unused129) {
                }
            } catch (NoSuchFieldError unused130) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.GCM_TOKEN_UPDATE_FAILED.ordinal()] = 32;
                        } catch (NoSuchFieldError unused131) {
                        }
                    } catch (NoSuchFieldError unused132) {
                    }
                } catch (NoSuchFieldError unused133) {
                }
            } catch (NoSuchFieldError unused134) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.GCM_TOKEN_REFRESHED.ordinal()] = 33;
                        } catch (NoSuchFieldError unused135) {
                        }
                    } catch (NoSuchFieldError unused136) {
                    }
                } catch (NoSuchFieldError unused137) {
                }
            } catch (NoSuchFieldError unused138) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.GCM_NO_GOOGLE_PLAY_SERVICES_INSTALLED.ordinal()] = 34;
                        } catch (NoSuchFieldError unused139) {
                        }
                    } catch (NoSuchFieldError unused140) {
                    }
                } catch (NoSuchFieldError unused141) {
                }
            } catch (NoSuchFieldError unused142) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.NETWORK_STATE_CHANGED.ordinal()] = 35;
                        } catch (NoSuchFieldError unused143) {
                        }
                    } catch (NoSuchFieldError unused144) {
                    }
                } catch (NoSuchFieldError unused145) {
                }
            } catch (NoSuchFieldError unused146) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.SERVICE_NOTIFICATION_CONFIRM.ordinal()] = 36;
                        } catch (NoSuchFieldError unused147) {
                        }
                    } catch (NoSuchFieldError unused148) {
                    }
                } catch (NoSuchFieldError unused149) {
                }
            } catch (NoSuchFieldError unused150) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.SERVICE_NOTIFICATION_CANCEL.ordinal()] = 37;
                        } catch (NoSuchFieldError unused151) {
                        }
                    } catch (NoSuchFieldError unused152) {
                    }
                } catch (NoSuchFieldError unused153) {
                }
            } catch (NoSuchFieldError unused154) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.SERVICE_SMS_RECEIVED.ordinal()] = 38;
                        } catch (NoSuchFieldError unused155) {
                        }
                    } catch (NoSuchFieldError unused156) {
                    }
                } catch (NoSuchFieldError unused157) {
                }
            } catch (NoSuchFieldError unused158) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.SERVICE_CALL_RECEIVED.ordinal()] = 39;
                        } catch (NoSuchFieldError unused159) {
                        }
                    } catch (NoSuchFieldError unused160) {
                    }
                } catch (NoSuchFieldError unused161) {
                }
            } catch (NoSuchFieldError unused162) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.SERVICE_SMS_RETRIEVER_SMS_RECEIVED.ordinal()] = 40;
                        } catch (NoSuchFieldError unused163) {
                        }
                    } catch (NoSuchFieldError unused164) {
                    }
                } catch (NoSuchFieldError unused165) {
                }
            } catch (NoSuchFieldError unused166) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.SMS_RETRIEVER_MANAGER_SUBSCRIBE_FAILED.ordinal()] = 41;
                        } catch (NoSuchFieldError unused167) {
                        }
                    } catch (NoSuchFieldError unused168) {
                    }
                } catch (NoSuchFieldError unused169) {
                }
            } catch (NoSuchFieldError unused170) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.SMS_RETRIEVER_MANAGER_SUBSCRIBE_SUCCEEDED.ordinal()] = 42;
                        } catch (NoSuchFieldError unused171) {
                        }
                    } catch (NoSuchFieldError unused172) {
                    }
                } catch (NoSuchFieldError unused173) {
                }
            } catch (NoSuchFieldError unused174) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.SMS_RETRIEVER_MANAGER_WAIT_TIMEOUT.ordinal()] = 43;
                        } catch (NoSuchFieldError unused175) {
                        }
                    } catch (NoSuchFieldError unused176) {
                    }
                } catch (NoSuchFieldError unused177) {
                }
            } catch (NoSuchFieldError unused178) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.SERVICE_IPC_SMS_MESSAGE_RECEIVED.ordinal()] = 44;
                        } catch (NoSuchFieldError unused179) {
                        }
                    } catch (NoSuchFieldError unused180) {
                    }
                } catch (NoSuchFieldError unused181) {
                }
            } catch (NoSuchFieldError unused182) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.SERVICE_IPC_CANCEL_NOTIFICATION_RECEIVED.ordinal()] = 45;
                        } catch (NoSuchFieldError unused183) {
                        }
                    } catch (NoSuchFieldError unused184) {
                    }
                } catch (NoSuchFieldError unused185) {
                }
            } catch (NoSuchFieldError unused186) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.SERVICE_IPC_FETCHER_STARTED_RECEIVED.ordinal()] = 46;
                        } catch (NoSuchFieldError unused187) {
                        }
                    } catch (NoSuchFieldError unused188) {
                    }
                } catch (NoSuchFieldError unused189) {
                }
            } catch (NoSuchFieldError unused190) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.SERVICE_IPC_FETCHER_STOPPED_RECEIVED.ordinal()] = 47;
                        } catch (NoSuchFieldError unused191) {
                        }
                    } catch (NoSuchFieldError unused192) {
                    }
                } catch (NoSuchFieldError unused193) {
                }
            } catch (NoSuchFieldError unused194) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.SERVICE_FETCHER_START_WITH_CHECK.ordinal()] = 48;
                        } catch (NoSuchFieldError unused195) {
                        }
                    } catch (NoSuchFieldError unused196) {
                    }
                } catch (NoSuchFieldError unused197) {
                }
            } catch (NoSuchFieldError unused198) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.SERVICE_SETTINGS_CHECK.ordinal()] = 49;
                        } catch (NoSuchFieldError unused199) {
                        }
                    } catch (NoSuchFieldError unused200) {
                    }
                } catch (NoSuchFieldError unused201) {
                }
            } catch (NoSuchFieldError unused202) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.SERVICE_SETTINGS_BATTERY_STATE_CHANGED.ordinal()] = 50;
                        } catch (NoSuchFieldError unused203) {
                        }
                    } catch (NoSuchFieldError unused204) {
                    }
                } catch (NoSuchFieldError unused205) {
                }
            } catch (NoSuchFieldError unused206) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.SERVICE_SETTINGS_NOTIFICATION_UNBLOCK.ordinal()] = 51;
                        } catch (NoSuchFieldError unused207) {
                        }
                    } catch (NoSuchFieldError unused208) {
                    }
                } catch (NoSuchFieldError unused209) {
                }
            } catch (NoSuchFieldError unused210) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.UI_NOTIFICATION_SETTINGS_SHOWN.ordinal()] = 52;
                        } catch (NoSuchFieldError unused211) {
                        }
                    } catch (NoSuchFieldError unused212) {
                    }
                } catch (NoSuchFieldError unused213) {
                }
            } catch (NoSuchFieldError unused214) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.UI_NOTIFICATION_SETTINGS_REPORT_REUSE.ordinal()] = 53;
                        } catch (NoSuchFieldError unused215) {
                        }
                    } catch (NoSuchFieldError unused216) {
                    }
                } catch (NoSuchFieldError unused217) {
                }
            } catch (NoSuchFieldError unused218) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.UI_NOTIFICATION_SETTINGS_REPORT_SPAM.ordinal()] = 54;
                        } catch (NoSuchFieldError unused219) {
                        }
                    } catch (NoSuchFieldError unused220) {
                    }
                } catch (NoSuchFieldError unused221) {
                }
            } catch (NoSuchFieldError unused222) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.UI_NOTIFICATION_SETTINGS_BLOCK.ordinal()] = 55;
                        } catch (NoSuchFieldError unused223) {
                        }
                    } catch (NoSuchFieldError unused224) {
                    }
                } catch (NoSuchFieldError unused225) {
                }
            } catch (NoSuchFieldError unused226) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.UI_NOTIFICATION_HISTORY_SHORTCUT_CREATED.ordinal()] = 56;
                        } catch (NoSuchFieldError unused227) {
                        }
                    } catch (NoSuchFieldError unused228) {
                    }
                } catch (NoSuchFieldError unused229) {
                }
            } catch (NoSuchFieldError unused230) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.UI_NOTIFICATION_HISTORY_OPENED.ordinal()] = 57;
                        } catch (NoSuchFieldError unused231) {
                        }
                    } catch (NoSuchFieldError unused232) {
                    }
                } catch (NoSuchFieldError unused233) {
                }
            } catch (NoSuchFieldError unused234) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.UI_NOTIFICATION_GET_INFO.ordinal()] = 58;
                        } catch (NoSuchFieldError unused235) {
                        }
                    } catch (NoSuchFieldError unused236) {
                    }
                } catch (NoSuchFieldError unused237) {
                }
            } catch (NoSuchFieldError unused238) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.UI_NOTIFICATION_OPENED.ordinal()] = 59;
                        } catch (NoSuchFieldError unused239) {
                        }
                    } catch (NoSuchFieldError unused240) {
                    }
                } catch (NoSuchFieldError unused241) {
                }
            } catch (NoSuchFieldError unused242) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.SMS_STORAGE_ADDED.ordinal()] = 60;
                        } catch (NoSuchFieldError unused243) {
                        }
                    } catch (NoSuchFieldError unused244) {
                    }
                } catch (NoSuchFieldError unused245) {
                }
            } catch (NoSuchFieldError unused246) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.SMS_STORAGE_CLEARED.ordinal()] = 61;
                        } catch (NoSuchFieldError unused247) {
                        }
                    } catch (NoSuchFieldError unused248) {
                    }
                } catch (NoSuchFieldError unused249) {
                }
            } catch (NoSuchFieldError unused250) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.SMS_STORAGE_SMS_DIALOG_REMOVED.ordinal()] = 62;
                        } catch (NoSuchFieldError unused251) {
                        }
                    } catch (NoSuchFieldError unused252) {
                    }
                } catch (NoSuchFieldError unused253) {
                }
            } catch (NoSuchFieldError unused254) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.SMS_STORAGE_SMS_REMOVED.ordinal()] = 63;
                        } catch (NoSuchFieldError unused255) {
                        }
                    } catch (NoSuchFieldError unused256) {
                    }
                } catch (NoSuchFieldError unused257) {
                }
            } catch (NoSuchFieldError unused258) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.SMS_STORAGE_SMS_DIALOG_REQUESTED.ordinal()] = 64;
                        } catch (NoSuchFieldError unused259) {
                        }
                    } catch (NoSuchFieldError unused260) {
                    }
                } catch (NoSuchFieldError unused261) {
                }
            } catch (NoSuchFieldError unused262) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.SMS_STORAGE_SMS_DIALOGS_REQUESTED.ordinal()] = 65;
                        } catch (NoSuchFieldError unused263) {
                        }
                    } catch (NoSuchFieldError unused264) {
                    }
                } catch (NoSuchFieldError unused265) {
                }
            } catch (NoSuchFieldError unused266) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.ACCOUNT_CHECKER_COMPLETED.ordinal()] = 66;
                        } catch (NoSuchFieldError unused267) {
                        }
                    } catch (NoSuchFieldError unused268) {
                    }
                } catch (NoSuchFieldError unused269) {
                }
            } catch (NoSuchFieldError unused270) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.ACCOUNT_CHECKER_REQUEST_SMS_INFO.ordinal()] = 67;
                        } catch (NoSuchFieldError unused271) {
                        }
                    } catch (NoSuchFieldError unused272) {
                    }
                } catch (NoSuchFieldError unused273) {
                }
            } catch (NoSuchFieldError unused274) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.ACCOUNT_CHECKER_SMS_PARSING_STARTED.ordinal()] = 68;
                        } catch (NoSuchFieldError unused275) {
                        }
                    } catch (NoSuchFieldError unused276) {
                    }
                } catch (NoSuchFieldError unused277) {
                }
            } catch (NoSuchFieldError unused278) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.ACCOUNT_CHECKER_SMS_PARSING_COMPLETED.ordinal()] = 69;
                        } catch (NoSuchFieldError unused279) {
                        }
                    } catch (NoSuchFieldError unused280) {
                    }
                } catch (NoSuchFieldError unused281) {
                }
            } catch (NoSuchFieldError unused282) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.APPLICATION_CHECKER_COMPLETED.ordinal()] = 70;
                        } catch (NoSuchFieldError unused283) {
                        }
                    } catch (NoSuchFieldError unused284) {
                    }
                } catch (NoSuchFieldError unused285) {
                }
            } catch (NoSuchFieldError unused286) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.FETCHER_MANAGER_FETCHER_STARTED.ordinal()] = 71;
                        } catch (NoSuchFieldError unused287) {
                        }
                    } catch (NoSuchFieldError unused288) {
                    }
                } catch (NoSuchFieldError unused289) {
                }
            } catch (NoSuchFieldError unused290) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.FETCHER_MANAGER_FETCHER_STOPPED.ordinal()] = 72;
                        } catch (NoSuchFieldError unused291) {
                        }
                    } catch (NoSuchFieldError unused292) {
                    }
                } catch (NoSuchFieldError unused293) {
                }
            } catch (NoSuchFieldError unused294) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.FETCHER_MANAGER_MESSAGE_RECEIVED.ordinal()] = 73;
                        } catch (NoSuchFieldError unused295) {
                        }
                    } catch (NoSuchFieldError unused296) {
                    }
                } catch (NoSuchFieldError unused297) {
                }
            } catch (NoSuchFieldError unused298) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.FETCHER_MANAGER_SERVER_INFO_RECEIVED.ordinal()] = 74;
                        } catch (NoSuchFieldError unused299) {
                        }
                    } catch (NoSuchFieldError unused300) {
                    }
                } catch (NoSuchFieldError unused301) {
                }
            } catch (NoSuchFieldError unused302) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.VERIFY_API_HANDLE_SERVER_FAILURE.ordinal()] = 75;
                        } catch (NoSuchFieldError unused303) {
                        }
                    } catch (NoSuchFieldError unused304) {
                    }
                } catch (NoSuchFieldError unused305) {
                }
            } catch (NoSuchFieldError unused306) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.VERIFY_API_HANDLE_REQUEST_FAILURE.ordinal()] = 76;
                        } catch (NoSuchFieldError unused307) {
                        }
                    } catch (NoSuchFieldError unused308) {
                    }
                } catch (NoSuchFieldError unused309) {
                }
            } catch (NoSuchFieldError unused310) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.PHONE_NUMBER_CHECKER_NEW_CHECK_STARTED.ordinal()] = 77;
                        } catch (NoSuchFieldError unused311) {
                        }
                    } catch (NoSuchFieldError unused312) {
                    }
                } catch (NoSuchFieldError unused313) {
                }
            } catch (NoSuchFieldError unused314) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.POPUP_CONTAINER_NOTIFICATION_ADDED.ordinal()] = 78;
                        } catch (NoSuchFieldError unused315) {
                        }
                    } catch (NoSuchFieldError unused316) {
                    }
                } catch (NoSuchFieldError unused317) {
                }
            } catch (NoSuchFieldError unused318) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.POPUP_CONTAINER_NOTIFICATION_REMOVED.ordinal()] = 79;
                        } catch (NoSuchFieldError unused319) {
                        }
                    } catch (NoSuchFieldError unused320) {
                    }
                } catch (NoSuchFieldError unused321) {
                }
            } catch (NoSuchFieldError unused322) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.NOTIFICATION_BAR_MANAGER_ONGOING_NOTIFICATION_SHOWN.ordinal()] = 80;
                        } catch (NoSuchFieldError unused323) {
                        }
                    } catch (NoSuchFieldError unused324) {
                    }
                } catch (NoSuchFieldError unused325) {
                }
            } catch (NoSuchFieldError unused326) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.SERVER_ACTION_RESULT.ordinal()] = 81;
                        } catch (NoSuchFieldError unused327) {
                        }
                    } catch (NoSuchFieldError unused328) {
                    }
                } catch (NoSuchFieldError unused329) {
                }
            } catch (NoSuchFieldError unused330) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.SERVER_ACTION_FAILURE.ordinal()] = 82;
                        } catch (NoSuchFieldError unused331) {
                        }
                    } catch (NoSuchFieldError unused332) {
                    }
                } catch (NoSuchFieldError unused333) {
                }
            } catch (NoSuchFieldError unused334) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.SESSION_CONTAINER_ADDED_SESSION.ordinal()] = 83;
                        } catch (NoSuchFieldError unused335) {
                        }
                    } catch (NoSuchFieldError unused336) {
                    }
                } catch (NoSuchFieldError unused337) {
                }
            } catch (NoSuchFieldError unused338) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.SESSION_CONTAINER_REMOVED_SESSION.ordinal()] = 84;
                        } catch (NoSuchFieldError unused339) {
                        }
                    } catch (NoSuchFieldError unused340) {
                    }
                } catch (NoSuchFieldError unused341) {
                }
            } catch (NoSuchFieldError unused342) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.SAFETY_NET_RESPONE_RECEIVED.ordinal()] = 85;
                        } catch (NoSuchFieldError unused343) {
                        }
                    } catch (NoSuchFieldError unused344) {
                    }
                } catch (NoSuchFieldError unused345) {
                }
            } catch (NoSuchFieldError unused346) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.VERIFICATION_SESSION_STATE_CHANGED.ordinal()] = 86;
                        } catch (NoSuchFieldError unused347) {
                        }
                    } catch (NoSuchFieldError unused348) {
                    }
                } catch (NoSuchFieldError unused349) {
                }
            } catch (NoSuchFieldError unused350) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.VERIFICATION_SESSION_FETCHER_INFO_RECEIVED.ordinal()] = 87;
                        } catch (NoSuchFieldError unused351) {
                        }
                    } catch (NoSuchFieldError unused352) {
                    }
                } catch (NoSuchFieldError unused353) {
                }
            } catch (NoSuchFieldError unused354) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.VERIFICATION_SESSION_CALL_IN_EXECUTED.ordinal()] = 88;
                        } catch (NoSuchFieldError unused355) {
                        }
                    } catch (NoSuchFieldError unused356) {
                    }
                } catch (NoSuchFieldError unused357) {
                }
            } catch (NoSuchFieldError unused358) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.VERIFICATION_SESSION_CALL_IN_SEND_STATS.ordinal()] = 89;
                        } catch (NoSuchFieldError unused359) {
                        }
                    } catch (NoSuchFieldError unused360) {
                    }
                } catch (NoSuchFieldError unused361) {
                }
            } catch (NoSuchFieldError unused362) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.VERIFICATION_SESSION_MOBILEID_RESULTS_RECEIVED.ordinal()] = 90;
                        } catch (NoSuchFieldError unused363) {
                        }
                    } catch (NoSuchFieldError unused364) {
                    }
                } catch (NoSuchFieldError unused365) {
                }
            } catch (NoSuchFieldError unused366) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.VERIFY_API_SESSION_SIGNATURE_GENERATED.ordinal()] = 91;
                        } catch (NoSuchFieldError unused367) {
                        }
                    } catch (NoSuchFieldError unused368) {
                    }
                } catch (NoSuchFieldError unused369) {
                }
            } catch (NoSuchFieldError unused370) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.API_SHUTDOWN.ordinal()] = 92;
                        } catch (NoSuchFieldError unused371) {
                        }
                    } catch (NoSuchFieldError unused372) {
                    }
                } catch (NoSuchFieldError unused373) {
                }
            } catch (NoSuchFieldError unused374) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.APP_MOVE_TO_BACKGROUND.ordinal()] = 93;
                        } catch (NoSuchFieldError unused375) {
                        }
                    } catch (NoSuchFieldError unused376) {
                    }
                } catch (NoSuchFieldError unused377) {
                }
            } catch (NoSuchFieldError unused378) {
            }
            try {
                try {
                    try {
                        try {
                            g[BusMessageType.APP_MOVE_TO_FOREGROUND.ordinal()] = 94;
                        } catch (NoSuchFieldError unused379) {
                        }
                    } catch (NoSuchFieldError unused380) {
                    }
                } catch (NoSuchFieldError unused381) {
                }
            } catch (NoSuchFieldError unused382) {
            }
            int[] iArr3 = new int[ServerInfo.NotificationInfo.Action.values().length];
            f = iArr3;
            try {
                try {
                    try {
                        iArr3[ServerInfo.NotificationInfo.Action.COMPLETED.ordinal()] = 1;
                    } catch (NoSuchFieldError unused383) {
                    }
                } catch (NoSuchFieldError unused384) {
                }
            } catch (NoSuchFieldError unused385) {
            }
            try {
                try {
                    try {
                        try {
                            f[ServerInfo.NotificationInfo.Action.UNKNOWN.ordinal()] = 2;
                        } catch (NoSuchFieldError unused386) {
                        }
                    } catch (NoSuchFieldError unused387) {
                    }
                } catch (NoSuchFieldError unused388) {
                }
            } catch (NoSuchFieldError unused389) {
            }
            int[] iArr4 = new int[i.a.values().length];
            e = iArr4;
            try {
                try {
                    try {
                        iArr4[i.a.PACKAGE_UPDATED.ordinal()] = 1;
                    } catch (NoSuchFieldError unused390) {
                    }
                } catch (NoSuchFieldError unused391) {
                }
            } catch (NoSuchFieldError unused392) {
            }
            try {
                try {
                    try {
                        try {
                            e[i.a.PACKAGE_REMOVED.ordinal()] = 2;
                        } catch (NoSuchFieldError unused393) {
                        }
                    } catch (NoSuchFieldError unused394) {
                    }
                } catch (NoSuchFieldError unused395) {
                }
            } catch (NoSuchFieldError unused396) {
            }
            try {
                try {
                    try {
                        try {
                            e[i.a.RESTART.ordinal()] = 3;
                        } catch (NoSuchFieldError unused397) {
                        }
                    } catch (NoSuchFieldError unused398) {
                    }
                } catch (NoSuchFieldError unused399) {
                }
            } catch (NoSuchFieldError unused400) {
            }
            try {
                try {
                    try {
                        try {
                            e[i.a.TIMER.ordinal()] = 4;
                        } catch (NoSuchFieldError unused401) {
                        }
                    } catch (NoSuchFieldError unused402) {
                    }
                } catch (NoSuchFieldError unused403) {
                }
            } catch (NoSuchFieldError unused404) {
            }
            try {
                try {
                    try {
                        try {
                            e[i.a.SMS_TEMPLATES_CHECK.ordinal()] = 5;
                        } catch (NoSuchFieldError unused405) {
                        }
                    } catch (NoSuchFieldError unused406) {
                    }
                } catch (NoSuchFieldError unused407) {
                }
            } catch (NoSuchFieldError unused408) {
            }
            try {
                try {
                    try {
                        try {
                            e[i.a.UNKNOWN.ordinal()] = 6;
                        } catch (NoSuchFieldError unused409) {
                        }
                    } catch (NoSuchFieldError unused410) {
                    }
                } catch (NoSuchFieldError unused411) {
                }
            } catch (NoSuchFieldError unused412) {
            }
            int[] iArr5 = new int[j.values().length];
            d = iArr5;
            try {
                try {
                    try {
                        iArr5[j.CHANGED.ordinal()] = 1;
                    } catch (NoSuchFieldError unused413) {
                    }
                } catch (NoSuchFieldError unused414) {
                }
            } catch (NoSuchFieldError unused415) {
            }
            try {
                try {
                    try {
                        try {
                            d[j.ACTUAL.ordinal()] = 2;
                        } catch (NoSuchFieldError unused416) {
                        }
                    } catch (NoSuchFieldError unused417) {
                    }
                } catch (NoSuchFieldError unused418) {
                }
            } catch (NoSuchFieldError unused419) {
            }
            int[] iArr6 = new int[j.c.values().length];
            c = iArr6;
            try {
                try {
                    try {
                        iArr6[j.c.GCM.ordinal()] = 1;
                    } catch (NoSuchFieldError unused420) {
                    }
                } catch (NoSuchFieldError unused421) {
                }
            } catch (NoSuchFieldError unused422) {
            }
            try {
                try {
                    try {
                        try {
                            c[j.c.FETCHER.ordinal()] = 2;
                        } catch (NoSuchFieldError unused423) {
                        }
                    } catch (NoSuchFieldError unused424) {
                    }
                } catch (NoSuchFieldError unused425) {
                }
            } catch (NoSuchFieldError unused426) {
            }
            try {
                try {
                    try {
                        try {
                            c[j.c.UNKNOWN.ordinal()] = 3;
                        } catch (NoSuchFieldError unused427) {
                        }
                    } catch (NoSuchFieldError unused428) {
                    }
                } catch (NoSuchFieldError unused429) {
                }
            } catch (NoSuchFieldError unused430) {
            }
            int[] iArr7 = new int[f.c.values().length];
            b = iArr7;
            try {
                try {
                    try {
                        iArr7[f.c.OK.ordinal()] = 1;
                    } catch (NoSuchFieldError unused431) {
                    }
                } catch (NoSuchFieldError unused432) {
                }
            } catch (NoSuchFieldError unused433) {
            }
            try {
                try {
                    try {
                        try {
                            b[f.c.READY_SERVICE_FOUND.ordinal()] = 2;
                        } catch (NoSuchFieldError unused434) {
                        }
                    } catch (NoSuchFieldError unused435) {
                    }
                } catch (NoSuchFieldError unused436) {
                }
            } catch (NoSuchFieldError unused437) {
            }
            try {
                try {
                    try {
                        try {
                            b[f.c.CONNECTION_TIMEOUT_EXPIRED.ordinal()] = 3;
                        } catch (NoSuchFieldError unused438) {
                        }
                    } catch (NoSuchFieldError unused439) {
                    }
                } catch (NoSuchFieldError unused440) {
                }
            } catch (NoSuchFieldError unused441) {
            }
            try {
                try {
                    try {
                        try {
                            b[f.c.GENERAL_FAILURE.ordinal()] = 4;
                        } catch (NoSuchFieldError unused442) {
                        }
                    } catch (NoSuchFieldError unused443) {
                    }
                } catch (NoSuchFieldError unused444) {
                }
            } catch (NoSuchFieldError unused445) {
            }
            try {
                try {
                    try {
                        try {
                            b[f.c.FAILED_TO_FIND_READY_SERVICE.ordinal()] = 5;
                        } catch (NoSuchFieldError unused446) {
                        }
                    } catch (NoSuchFieldError unused447) {
                    }
                } catch (NoSuchFieldError unused448) {
                }
            } catch (NoSuchFieldError unused449) {
            }
            try {
                try {
                    try {
                        try {
                            b[f.c.FAILED_TO_FIND_TARGET_SESSION.ordinal()] = 6;
                        } catch (NoSuchFieldError unused450) {
                        }
                    } catch (NoSuchFieldError unused451) {
                    }
                } catch (NoSuchFieldError unused452) {
                }
            } catch (NoSuchFieldError unused453) {
            }
            int[] iArr8 = new int[ServerNotificationMessage.Message.NotificationType.values().length];
            a = iArr8;
            try {
                try {
                    try {
                        iArr8[ServerNotificationMessage.Message.NotificationType.MESSAGE.ordinal()] = 1;
                    } catch (NoSuchFieldError unused454) {
                    }
                } catch (NoSuchFieldError unused455) {
                }
            } catch (NoSuchFieldError unused456) {
            }
            try {
                try {
                    try {
                        try {
                            a[ServerNotificationMessage.Message.NotificationType.PING.ordinal()] = 2;
                        } catch (NoSuchFieldError unused457) {
                        }
                    } catch (NoSuchFieldError unused458) {
                    }
                } catch (NoSuchFieldError unused459) {
                }
            } catch (NoSuchFieldError unused460) {
            }
            try {
                try {
                    try {
                        try {
                            a[ServerNotificationMessage.Message.NotificationType.PING_V2.ordinal()] = 3;
                        } catch (NoSuchFieldError unused461) {
                        }
                    } catch (NoSuchFieldError unused462) {
                    }
                } catch (NoSuchFieldError unused463) {
                }
            } catch (NoSuchFieldError unused464) {
            }
            try {
                try {
                    try {
                        try {
                            a[ServerNotificationMessage.Message.NotificationType.VERIFIED.ordinal()] = 4;
                        } catch (NoSuchFieldError unused465) {
                        }
                    } catch (NoSuchFieldError unused466) {
                    }
                } catch (NoSuchFieldError unused467) {
                }
            } catch (NoSuchFieldError unused468) {
            }
            try {
                try {
                    try {
                        try {
                            a[ServerNotificationMessage.Message.NotificationType.UNKNOWN.ordinal()] = 5;
                        } catch (NoSuchFieldError unused469) {
                        }
                    } catch (NoSuchFieldError unused470) {
                    }
                } catch (NoSuchFieldError unused471) {
                }
            } catch (NoSuchFieldError unused472) {
            }
        }
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/q$b.class */
    class b implements AppStateModel.a {
        final /* synthetic */ ApiManager a;
        final /* synthetic */ TimeProvider b;

        b(ApiManager apiManager, TimeProvider timeProvider) {
            this.a = apiManager;
            this.b = timeProvider;
        }

        @Override // ru.mail.libverify.AppStateModel.a
        public final void a() {
            FileLog.d("VerificationApi", "lifecycleObserver: background");
            this.a.post(MessageBusUtils.createOneArg(BusMessageType.APP_MOVE_TO_BACKGROUND, Long.valueOf(this.b.getLocalTime())));
        }

        @Override // ru.mail.libverify.AppStateModel.a
        public final void onResume() {
            FileLog.d("VerificationApi", "lifecycleObserver: foreground");
            this.a.post(MessageBusUtils.createOneArg(BusMessageType.APP_MOVE_TO_FOREGROUND, Long.valueOf(this.b.getLocalTime())));
        }
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/q$c.class */
    class c implements Runnable {
        c() {
        }

        /* JADX WARN: Type inference failed for: r2v6, types: [long, java.lang.String] */
        @Override // java.lang.Runnable
        public final void run() {
            long j;
            q.this.a.prepare();
            AlarmReceiver.AlarmBuilder action = ((AlarmManager) q.this.s.get()).createBuilder().setAction(BusMessageType.SERVICE_SETTINGS_CHECK.name());
            ?? name = i.a.TIMER.name();
            AlarmReceiver.AlarmBuilder putExtra = action.putExtra("settings_action_type", name);
            q qVar = q.this;
            Long longValue = qVar.a.getSettings().getLongValue("api_settings_timeout", (Long) null);
            if (longValue == null || longValue.longValue() <= 0) {
                FileLog.d("VerificationApi", "use default timeout for settings check");
                j = 86400000;
            } else {
                j = longValue.longValue();
                Long longValue2 = qVar.a.getSettings().getLongValue("api_settings_timestamp", (Long) null);
                Long valueOf = (longValue2 != null && qVar.v.getLocalTime() - longValue2.longValue() >= 0) ? Long.valueOf((long) name) : null;
                if (valueOf != null && valueOf.longValue() > 0 && j > valueOf.longValue()) {
                    long longValue3 = j - valueOf.longValue();
                    j = name;
                    if (longValue3 <= 43200000) {
                        j = 43200000;
                    }
                }
                FileLog.d("VerificationApi", "timeout for the next settings check %d", Long.valueOf(j));
            }
            putExtra.setTimeout(j).setRepeating(true).setUp();
            ((AlarmManager) q.this.s.get()).createBuilder().setAction(BusMessageType.GCM_REFRESH_TOKEN.name()).putExtra(ApiManager.GCM_TOKEN_CHECK_TYPE, GCMTokenCheckType.PERIODIC.name()).setTimeout(172800000L).setRepeating(true).setUp();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/q$d.class */
    public class d implements f.b {
        final /* synthetic */ ServerNotificationMessage a;

        d(ServerNotificationMessage serverNotificationMessage) {
            this.a = serverNotificationMessage;
        }

        @Override // ru.mail.libverify.f.f.b
        public final void a(f.c cVar) {
            FileLog.v("VerificationApi", "post cancel notification result %s for %s ", cVar, this.a);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/q$e.class */
    public class e implements a.InterfaceC0022a {
        final /* synthetic */ String[] a;
        final /* synthetic */ Runnable b;

        e(String[] strArr, Runnable runnable) {
            this.a = strArr;
            this.b = runnable;
        }

        @Override // ru.mail.libverify.utils.permissions.a.InterfaceC0022a
        public final void a(@NonNull String str) {
            FileLog.d("VerificationApi", "permission %s granted", str);
        }

        @Override // ru.mail.libverify.utils.permissions.a.InterfaceC0022a
        public final void b(@NonNull String str) {
            FileLog.e("VerificationApi", "permission %s denied", str);
        }

        @Override // ru.mail.libverify.utils.permissions.a.InterfaceC0022a
        public final void onCompleted(boolean z) {
            FileLog.d("VerificationApi", "permission request %s completed (%s)", Arrays.toString(this.a), Boolean.valueOf(z));
            if (this.b != null) {
                q.this.p.getDispatcher().post(this.b);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/q$f.class */
    public class f implements CommonContext {
        private f() {
        }

        @Override // ru.mail.libverify.api.CommonContext
        public final MessageBus getBus() {
            return q.this.q;
        }

        @Override // ru.mail.libverify.api.CommonContext
        public final InstanceConfig getConfig() {
            return q.this.a;
        }

        @Override // ru.mail.libverify.api.CommonContext
        public final Handler getDispatcher() {
            return q.this.p.getDispatcher();
        }

        @Override // ru.mail.libverify.api.CommonContext
        public KeyValueStorage getSettings() {
            return q.this.a.getSettings();
        }

        @Override // ru.mail.libverify.api.CommonContext
        public final ExecutorService getBackgroundWorker() {
            return q.this.p.getBackgroundWorker();
        }
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/q$g.class */
    private class g extends f {
        private g() {
            super();
        }

        @Override // ru.mail.libverify.api.q.f, ru.mail.libverify.api.CommonContext
        public final KeyValueStorage getSettings() {
            return q.this.a.getSettings();
        }
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/q$h.class */
    private class h implements ru.mail.libverify.e.d {
        private volatile ThreadPoolExecutor a;
        private volatile ThreadPoolExecutor b;

        /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/q$h$a.class */
        class a implements ThreadFactory {
            a() {
            }

            @Override // java.util.concurrent.ThreadFactory
            public final Thread newThread(@NonNull Runnable runnable) {
                Thread thread = new Thread(runnable);
                thread.setName("libverify_fetcher_connection");
                thread.setUncaughtExceptionHandler(q.this.x);
                return thread;
            }
        }

        /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/q$h$b.class */
        class b implements ThreadFactory {
            b() {
            }

            @Override // java.util.concurrent.ThreadFactory
            public final Thread newThread(@NonNull Runnable runnable) {
                Thread thread = new Thread(runnable);
                thread.setName("libverify_fetcher_worker");
                thread.setUncaughtExceptionHandler(q.this.x);
                return thread;
            }
        }

        private h() {
        }

        @Override // ru.mail.libverify.e.d
        public final ExecutorService c() {
            if (this.b == null) {
                synchronized (this) {
                    if (this.b == null) {
                        this.b = new ThreadPoolExecutor(2, 2, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(), new a(), q.this.y);
                    }
                }
            }
            return this.b;
        }

        @Override // ru.mail.libverify.e.d
        public final ExecutorService a() {
            if (this.a == null) {
                synchronized (this) {
                    if (this.a == null) {
                        this.a = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(), new b(), q.this.y);
                    }
                }
            }
            return this.a;
        }

        @Override // ru.mail.libverify.e.d
        public final boolean b() {
            return q.this.b.b();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/q$i.class */
    public class i implements p {
        private i() {
        }

        public final x a(@NonNull String str) throws JsonParseException {
            q qVar = q.this;
            if (qVar.E == null) {
                qVar.E = new ru.mail.libverify.k.m(qVar.m);
            }
            ru.mail.libverify.k.m mVar = qVar.E;
            q qVar2 = q.this;
            if (qVar2.F == null) {
                qVar2.F = new ru.mail.libverify.k.h(qVar2.m, qVar2.f);
            }
            ru.mail.libverify.k.h hVar = qVar2.F;
            q qVar3 = q.this;
            if (qVar3.G == null) {
                qVar3.G = new ru.mail.libverify.t.a(qVar3.m);
            }
            ru.mail.libverify.t.a aVar = qVar3.G;
            q qVar4 = q.this;
            TimeProvider timeProvider = qVar4.v;
            CommonContext commonContext = qVar4.m;
            if (qVar4.M == null) {
                qVar4.M = new ru.mail.libverify.m.r(qVar4.a.getContext(), qVar4.p, qVar4.m);
            }
            ru.mail.libverify.m.r rVar = qVar4.M;
            q qVar5 = q.this;
            return new x(mVar, hVar, aVar, timeProvider, commonContext, rVar, str, qVar5.t, qVar5.f);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/q$j.class */
    public enum j {
        ACTUAL,
        CHANGED,
        UPDATING
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Inject
    public q(@NonNull ApiManager apiManager, @NonNull ru.mail.libverify.m.l lVar, @NonNull MessageBus messageBus, @NonNull e0 e0Var, @NonNull Lazy<AlarmManager> lazy, @NonNull Lazy<GcmRegistrar> lazy2, @NonNull Lazy<ActionExecutor> lazy3, @NonNull Lazy<ru.mail.libverify.l.d> lazy4, @NonNull Lazy<NotificationBarManager> lazy5, @NonNull Thread.UncaughtExceptionHandler uncaughtExceptionHandler, @NonNull RejectedExecutionHandler rejectedExecutionHandler, @NonNull TimerManager timerManager, @NonNull ru.mail.libverify.q.b bVar, @NonNull ru.mail.libverify.q.c cVar, @NonNull TimeProvider timeProvider, @NonNull ru.mail.libverify.a.b bVar2) {
        f fVar = new f();
        this.m = fVar;
        this.n = new AtomicReference<>();
        this.J = new ru.mail.libverify.r.c();
        this.N = new AtomicReference<>(null);
        this.p = apiManager;
        this.q = messageBus;
        this.r = e0Var;
        this.s = lazy;
        this.w = lazy2;
        this.x = uncaughtExceptionHandler;
        this.y = rejectedExecutionHandler;
        this.z = lazy5;
        this.A = lazy3;
        this.t = timerManager;
        this.D = bVar2;
        this.u = cVar;
        this.v = timeProvider;
        ru.mail.libverify.api.j.a(lVar.getContext());
        this.a = lVar;
        this.B = new ru.mail.libverify.i.e(lVar);
        ((NotificationBarManager) lazy5.get()).checkShownNotifications();
        this.f = new ru.mail.libverify.l.b(timeProvider, lazy4);
        this.g = new m(new g());
        this.b = new n(fVar, new i());
        this.i = DoubleCheck.lazy(() -> {
            return new ru.mail.libverify.api.a(this.m);
        });
        this.h = DoubleCheck.lazy(() -> {
            return new ru.mail.libverify.e.c(this.m, this.l);
        });
        this.j = DoubleCheck.lazy(() -> {
            return new ru.mail.libverify.api.f(this.m);
        });
        this.o = new ru.mail.libverify.p.e(fVar, uncaughtExceptionHandler);
        this.I = new ru.mail.libverify.m.o(fVar.getConfig());
        this.C = new ru.mail.libverify.m.n(fVar);
        bVar.a();
        apiManager.addApiGroup(this);
        this.K = new b(apiManager, timeProvider);
        ConnectivityHelper.registerCallback(lVar.getContext(), lVar.getId());
    }

    private void a(@Nullable String str, @Nullable VerificationApi.AccountCheckListener accountCheckListener) {
        if (TextUtils.isEmpty(str)) {
            FileLog.e("VerificationApi", "empty account data json");
            return;
        }
        this.f.a();
        if (this.a.c("instance_account_check_sms")) {
            ((ru.mail.libverify.api.a) this.i.get()).a(str, accountCheckListener);
            return;
        }
        String generateId = new SessionIdGeneratorImpl().generateId();
        if (this.L == null) {
            this.L = new ru.mail.libverify.m.p(this.a.getContext(), this.p, this.m);
        }
        this.L.a(generateId, str, (v2) -> {
            a(r3, v2);
        });
    }

    private boolean b() {
        Long valueOf;
        Long longValue = this.a.getSettings().getLongValue("api_settings_timestamp", (Long) null);
        if (longValue != null) {
            long localTime = this.v.getLocalTime() - longValue.longValue();
            if (localTime >= 0) {
                valueOf = Long.valueOf(localTime);
                Long l = valueOf;
                FileLog.d("VerificationApi", "elapsed time since the last settings check %s", valueOf);
                return l == null || valueOf.longValue() >= 86400000;
            }
        }
        valueOf = null;
        Long l2 = valueOf;
        FileLog.d("VerificationApi", "elapsed time since the last settings check %s", valueOf);
        if (l2 == null) {
            return true;
        }
    }

    private void j(@NonNull String str) {
        FileLog.v("VerificationApi", "received server info %s from GCM", str);
        try {
            ServerInfo serverInfo = (ServerInfo) JsonParser.fromJson(str, ServerInfo.class);
            if (str.contains("ping_v2")) {
                serverInfo.getMessageInfo().toString();
            }
            if (serverInfo.getDelayedVerifyResponse() != null) {
                serverInfo.getDelayedVerifyResponse().setOwner(this.B);
            }
            a(serverInfo, j.c.GCM);
        } catch (JsonParseException e2) {
            DebugUtils.safeThrow("VerificationApi", "failed to parse server info json", e2);
        }
    }

    @Override // ru.mail.verify.core.api.ApiGroup
    public final List<Lazy<ApiPlugin>> getPlugins() {
        return new PluginListBuilder().add(this.h).add(this.A).add(this.i).add(this.w).add(this.j).build();
    }

    @Override // ru.mail.verify.core.api.ApiGroup
    public final void initialize() {
        this.q.register(Arrays.asList(BusMessageType.VERIFY_API_IPC_CONNECT_RESULT_RECEIVED, BusMessageType.API_INTERNAL_SILENT_EXCEPTION, BusMessageType.API_INTERNAL_UNHANDLED_EXCEPTION, BusMessageType.VERIFY_API_START_VERIFICATION, BusMessageType.VERIFY_API_COMPLETE_VERIFICATION, BusMessageType.VERIFY_API_RESET_VERIFICATION_CODE_ERROR, BusMessageType.VERIFY_API_CANCEL_VERIFICATION, BusMessageType.VERIFY_API_REQUEST_NEW_SMS_CODE, BusMessageType.VERIFY_API_CHECK_PHONE_NUMBER, BusMessageType.VERIFY_API_REQUEST_IVR, BusMessageType.VERIFY_API_VERIFY_SMS_CODE, BusMessageType.VERIFY_API_REQUEST_VERIFICATION_STATE, BusMessageType.VERIFY_API_REQUEST_VERIFICATION_STATES, BusMessageType.VERIFY_API_CHECK_ACCOUNT_VERIFICATION, BusMessageType.VERIFY_API_SET_LOCALE, BusMessageType.VERIFY_API_SET_API_ENDPOINTS, BusMessageType.VERIFY_API_SET_PROXY_ENDPOINT, BusMessageType.VERIFY_API_REMOVE_PROXY_ENDPOINT, BusMessageType.VERIFY_API_SEARCH_PHONE_ACCOUNTS, BusMessageType.VERIFY_API_CHECK_NETWORK, BusMessageType.VERIFY_API_RESET, BusMessageType.VERIFY_API_SIGN_OUT, BusMessageType.VERIFY_API_SOFT_SIGN_OUT, BusMessageType.VERIFY_API_PREPARE_2FA_CHECK, BusMessageType.VERIFY_API_CHECK_ACCOUNT_VERIFICATION_BY_SMS, BusMessageType.VERIFY_API_SET_DISABLE_SIM_DATA_SEND, BusMessageType.VERIFY_API_REQUEST_GCM_TOKEN, BusMessageType.NETWORK_STATE_CHANGED, BusMessageType.SERVICE_NOTIFICATION_CONFIRM, BusMessageType.SERVICE_NOTIFICATION_CANCEL, BusMessageType.SERVICE_SMS_RECEIVED, BusMessageType.SERVICE_CALL_RECEIVED, BusMessageType.SERVICE_SMS_RETRIEVER_SMS_RECEIVED, BusMessageType.SERVICE_IPC_SMS_MESSAGE_RECEIVED, BusMessageType.SERVICE_IPC_CANCEL_NOTIFICATION_RECEIVED, BusMessageType.SERVICE_IPC_FETCHER_STARTED_RECEIVED, BusMessageType.SERVICE_IPC_FETCHER_STOPPED_RECEIVED, BusMessageType.SERVICE_FETCHER_START_WITH_CHECK, BusMessageType.SERVICE_SETTINGS_CHECK, BusMessageType.SERVICE_SETTINGS_BATTERY_STATE_CHANGED, BusMessageType.SERVICE_SETTINGS_NOTIFICATION_UNBLOCK, BusMessageType.SMS_RETRIEVER_MANAGER_SUBSCRIBE_FAILED, BusMessageType.SMS_RETRIEVER_MANAGER_SUBSCRIBE_SUCCEEDED, BusMessageType.SMS_RETRIEVER_MANAGER_WAIT_TIMEOUT, BusMessageType.UI_NOTIFICATION_SETTINGS_SHOWN, BusMessageType.UI_NOTIFICATION_SETTINGS_REPORT_REUSE, BusMessageType.UI_NOTIFICATION_SETTINGS_REPORT_SPAM, BusMessageType.UI_NOTIFICATION_SETTINGS_BLOCK, BusMessageType.UI_NOTIFICATION_HISTORY_SHORTCUT_CREATED, BusMessageType.UI_NOTIFICATION_HISTORY_OPENED, BusMessageType.UI_NOTIFICATION_GET_INFO, BusMessageType.UI_NOTIFICATION_OPENED, BusMessageType.SMS_STORAGE_ADDED, BusMessageType.SMS_STORAGE_CLEARED, BusMessageType.SMS_STORAGE_SMS_DIALOG_REMOVED, BusMessageType.SMS_STORAGE_SMS_REMOVED, BusMessageType.SMS_STORAGE_SMS_DIALOG_REQUESTED, BusMessageType.SMS_STORAGE_SMS_DIALOGS_REQUESTED, BusMessageType.ACCOUNT_CHECKER_COMPLETED, BusMessageType.ACCOUNT_CHECKER_REQUEST_SMS_INFO, BusMessageType.ACCOUNT_CHECKER_SMS_PARSING_STARTED, BusMessageType.ACCOUNT_CHECKER_SMS_PARSING_COMPLETED, BusMessageType.APPLICATION_CHECKER_COMPLETED, BusMessageType.FETCHER_MANAGER_FETCHER_STARTED, BusMessageType.FETCHER_MANAGER_FETCHER_STOPPED, BusMessageType.FETCHER_MANAGER_MESSAGE_RECEIVED, BusMessageType.FETCHER_MANAGER_SERVER_INFO_RECEIVED, BusMessageType.PHONE_NUMBER_CHECKER_NEW_CHECK_STARTED, BusMessageType.POPUP_CONTAINER_NOTIFICATION_ADDED, BusMessageType.POPUP_CONTAINER_NOTIFICATION_REMOVED, BusMessageType.NOTIFICATION_BAR_MANAGER_ONGOING_NOTIFICATION_SHOWN, BusMessageType.SAFETY_NET_RESPONE_RECEIVED, BusMessageType.SERVER_ACTION_RESULT, BusMessageType.SERVER_ACTION_FAILURE, BusMessageType.SESSION_CONTAINER_ADDED_SESSION, BusMessageType.SESSION_CONTAINER_REMOVED_SESSION, BusMessageType.VERIFICATION_SESSION_STATE_CHANGED, BusMessageType.VERIFICATION_SESSION_FETCHER_INFO_RECEIVED, BusMessageType.VERIFICATION_SESSION_MOBILEID_RESULTS_RECEIVED, BusMessageType.VERIFICATION_SESSION_CALL_IN_EXECUTED, BusMessageType.VERIFICATION_SESSION_CALL_IN_SEND_STATS, BusMessageType.GCM_TOKEN_UPDATED, BusMessageType.GCM_SERVER_INFO_RECEIVED, BusMessageType.GCM_FETCHER_INFO_RECEIVED, BusMessageType.GCM_MESSAGE_RECEIVED, BusMessageType.GCM_TOKEN_UPDATE_FAILED, BusMessageType.GCM_TOKEN_REFRESHED, BusMessageType.GCM_NO_GOOGLE_PLAY_SERVICES_INSTALLED, BusMessageType.VERIFY_API_SESSION_SIGNATURE_GENERATED, BusMessageType.APP_MOVE_TO_BACKGROUND, BusMessageType.APP_MOVE_TO_FOREGROUND, BusMessageType.API_SHUTDOWN), this);
        this.p.getBackgroundWorker().submit(new c());
        long localTime = this.v.getLocalTime();
        long j2 = 0;
        try {
            Long longValue = this.a.getSettings().getLongValue("api_last_sent_timings", 0L);
            if (longValue != null) {
                j2 = longValue.longValue();
            }
        } catch (Exception e2) {
            FileLog.e("VerificationApi", "Failed to read storage", e2);
        }
        long j3 = j2 + 86400000;
        if (j3 <= localTime) {
            HashSet hashSet = this.u.get();
            if (!hashSet.isEmpty()) {
                FileLog.d("VerificationApi", "Try to send timings: %s", hashSet.toString());
                ru.mail.libverify.i.l lVar = new ru.mail.libverify.i.l(this.a, ru.mail.libverify.i.m.a(hashSet));
                this.u.clear();
                this.a.getSettings().putValue("api_last_sent_timings", localTime);
                Utils.safeExecute("VerificationApi", (ActionExecutor) this.A.get(), lVar);
            }
            FileLog.d("VerificationApi", "StartTimeData list is empty");
        } else {
            FileLog.d("VerificationApi", "Time of the next StartTimeData list send: %d", Long.valueOf(j3));
        }
        j();
        FileLog.v("VerificationApi", "session processing started (count = %d)", Integer.valueOf(this.b.f()));
        for (x xVar : this.b.b(n.c.ALL)) {
            xVar.D();
        }
    }

    @Override // ru.mail.libverify.api.VerificationApi
    public final void requestGcmToken(@NonNull VerificationApi.GcmTokenListener gcmTokenListener) {
        this.q.post(MessageBusUtils.createOneArg(BusMessageType.VERIFY_API_REQUEST_GCM_TOKEN, gcmTokenListener));
    }

    @Override // ru.mail.libverify.api.VerificationApi
    @NonNull
    public final String loggedInWithVKConnect(@NonNull String str, @Nullable String str2) throws IllegalStateException {
        return a(str, ru.mail.libverify.d.d.b(), null, null, null, str2, null);
    }

    @Override // ru.mail.libverify.api.VerificationApi
    @NonNull
    public final String startVerificationWithVKConnect(@NonNull String str, @NonNull String str2, @Nullable String str3, @Nullable Map<String, String> map, @Nullable String str4, @Nullable VerificationParameters verificationParameters) throws IllegalStateException {
        return a(str, ru.mail.libverify.d.d.c(), str2, str3, map, str4, verificationParameters);
    }

    @Override // ru.mail.libverify.api.VerificationApi
    @NonNull
    public final String startVerification(@NonNull String str, @NonNull String str2, @Nullable String str3, @Nullable Map<String, String> map, @Nullable VerificationParameters verificationParameters) throws IllegalStateException {
        return a(str, ru.mail.libverify.d.d.a(), str2, str3, map, null, verificationParameters);
    }

    @Override // ru.mail.libverify.api.VerificationApi
    public final void cancelVerification(@NonNull String str) {
        cancelVerification(str, VerificationApi.CancelReason.OK);
    }

    @Override // ru.mail.libverify.api.VerificationApi
    public final void completeVerification(@NonNull String str) {
        if (TextUtils.isEmpty(str)) {
            FileLog.e("VerificationApi", "sessionId must be not null");
        } else {
            this.p.post(MessageBusUtils.createOneArg(BusMessageType.VERIFY_API_COMPLETE_VERIFICATION, str));
        }
    }

    @Override // ru.mail.libverify.api.VerificationApi
    public final void verifySmsCode(@NonNull String str, @NonNull String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            FileLog.e("VerificationApi", "sessionId and smsCode must be not null");
        } else {
            this.p.post(MessageBusUtils.createMultipleArgs(BusMessageType.VERIFY_API_VERIFY_SMS_CODE, str, str2));
        }
    }

    @Override // ru.mail.libverify.api.VerificationApi
    public final void requestNewSmsCode(@NonNull String str) {
        if (TextUtils.isEmpty(str)) {
            FileLog.e("VerificationApi", "sessionId must be not null");
        } else {
            this.p.post(MessageBusUtils.createOneArg(BusMessageType.VERIFY_API_REQUEST_NEW_SMS_CODE, str));
        }
    }

    @Override // ru.mail.libverify.api.VerificationApi
    public final void searchPhoneAccounts(@NonNull VerificationApi.PhoneAccountSearchListener phoneAccountSearchListener) {
        this.p.post(MessageBusUtils.createMultipleArgs(BusMessageType.VERIFY_API_SEARCH_PHONE_ACCOUNTS, phoneAccountSearchListener));
    }

    @Override // ru.mail.libverify.api.VerificationApi
    public final void setAllowedPermissions(@NonNull String[] strArr) {
        FileLog.v("VerificationApi", "allowed permissions %s", Arrays.toString(strArr));
        this.n.set(strArr);
    }

    @Override // ru.mail.libverify.api.VerificationApi
    public final void setApiEndpoints(@NonNull Map<String, String> map) {
        this.p.post(MessageBusUtils.createOneArg(BusMessageType.VERIFY_API_SET_API_ENDPOINTS, map));
    }

    @Override // ru.mail.libverify.api.VerificationApi
    public final void querySmsDialogs(@NonNull VerificationApi.SmsDialogsListener smsDialogsListener) {
        this.o.a(smsDialogsListener);
    }

    @Override // ru.mail.libverify.api.VerificationApi
    public final void querySms(@Nullable String str, @Nullable Long l, @Nullable Long l2, @Nullable Integer num, @NonNull VerificationApi.SmsListener smsListener) {
        this.o.a(str, l, l2, num, smsListener);
    }

    @Override // ru.mail.libverify.api.VerificationApi
    public final void removeSmsDialog(@Nullable String str, @Nullable Long l) {
        this.o.b(str, l);
    }

    @Override // ru.mail.libverify.api.VerificationApi
    public final void removeSms(@NonNull String str, @Nullable Long l, long j2) {
        this.o.a(str, l, j2);
    }

    @Override // ru.mail.libverify.api.VerificationApi
    public final void clearSmsDialogs() {
        this.o.a();
    }

    @Override // ru.mail.libverify.api.VerificationApi
    public final void addSmsDialogChangedListener(@NonNull VerificationApi.SmsDialogChangedListener smsDialogChangedListener) {
        this.o.a(smsDialogChangedListener);
    }

    @Override // ru.mail.libverify.api.VerificationApi
    public final void removeSmsDialogChangedListener(@NonNull VerificationApi.SmsDialogChangedListener smsDialogChangedListener) {
        this.o.b(smsDialogChangedListener);
    }

    @Override // ru.mail.libverify.api.VerificationApi
    public final void sendCallInClickStats(@NonNull String str) throws IllegalStateException, IllegalArgumentException {
        FileLog.v("VerificationApi", "request call to call_in number for %s", str);
        x a2 = this.b.a(str, n.c.NOT_TEMPORARY);
        if (a2 == null) {
            FileLog.e("VerificationApi", "Session with id %s not found", str);
            return;
        }
        VerificationApi.CallInDescriptor callInDescriptor = a2.k().getCallInDescriptor();
        if (callInDescriptor == null) {
            FileLog.e("VerificationApi", "Session doesn't have a call_in in the descriptor");
        } else {
            this.q.post(MessageBusUtils.createMultipleArgs(BusMessageType.VERIFICATION_SESSION_CALL_IN_SEND_STATS, str, callInDescriptor.getPhoneNumber()));
        }
    }

    @Override // ru.mail.libverify.api.VerificationApi
    public final void reportNetworkStateChange(boolean z) {
        this.p.post(MessageBusUtils.createOneArg(BusMessageType.VERIFY_API_CHECK_NETWORK, Boolean.valueOf(z)));
    }

    @Override // ru.mail.libverify.api.VerificationApi
    public final synchronized void addVerificationStateChangedListener(@NonNull VerificationApi.VerificationStateChangedListener verificationStateChangedListener) {
        this.c.add(verificationStateChangedListener);
    }

    @Override // ru.mail.libverify.api.VerificationApi
    public final synchronized void removeVerificationStateChangedListener(@NonNull VerificationApi.VerificationStateChangedListener verificationStateChangedListener) {
        this.c.remove(verificationStateChangedListener);
    }

    @Override // ru.mail.libverify.api.VerificationApi
    public final synchronized void addSmsCodeNotificationListener(@NonNull VerificationApi.SmsCodeNotificationListener smsCodeNotificationListener) {
        this.d.add(smsCodeNotificationListener);
    }

    @Override // ru.mail.libverify.api.VerificationApi
    public final synchronized void removeSmsCodeNotificationListener(@NonNull VerificationApi.SmsCodeNotificationListener smsCodeNotificationListener) {
        this.d.remove(smsCodeNotificationListener);
    }

    @Override // ru.mail.libverify.api.VerificationApi
    public final void setApiEndpoint(@Nullable String str) {
        if (str == null) {
            this.p.post(MessageBusUtils.createOneArg(BusMessageType.VERIFY_API_SET_PROXY_ENDPOINT, ru.mail.libverify.b.c.a()));
            return;
        }
        this.p.post(MessageBusUtils.createOneArg(BusMessageType.VERIFY_API_SET_PROXY_ENDPOINT, str));
    }

    @Override // ru.mail.libverify.api.VerificationApi
    public final void removeApiEndpoint() {
        this.p.post(MessageBusUtils.createOneArg(BusMessageType.VERIFY_API_REMOVE_PROXY_ENDPOINT, (Object) null));
    }

    @Override // ru.mail.libverify.api.VerificationApi
    public final void requestVerificationStates(@NonNull VerificationApi.VerificationStatesHandler verificationStatesHandler) {
        this.p.post(MessageBusUtils.createOneArg(BusMessageType.VERIFY_API_REQUEST_VERIFICATION_STATES, verificationStatesHandler));
    }

    @Override // ru.mail.libverify.api.VerificationApi
    public final void requestVerificationState(@NonNull String str, @NonNull VerificationApi.VerificationStateChangedListener verificationStateChangedListener) {
        this.p.post(MessageBusUtils.createMultipleArgs(BusMessageType.VERIFY_API_REQUEST_VERIFICATION_STATE, str, verificationStateChangedListener));
    }

    @Override // ru.mail.libverify.api.VerificationApi
    public final void resetVerificationCodeError(@NonNull String str) {
        this.p.post(MessageBusUtils.createOneArg(BusMessageType.VERIFY_API_RESET_VERIFICATION_CODE_ERROR, str));
    }

    @Override // ru.mail.libverify.api.VerificationApi
    public final void reset() {
        this.p.post(MessageBusUtils.createOneArg(BusMessageType.VERIFY_API_RESET, (Object) null));
    }

    @Override // ru.mail.libverify.api.VerificationApi
    public final void signOut(boolean z) {
        this.p.post(MessageBusUtils.createMultipleArgs(BusMessageType.VERIFY_API_SIGN_OUT, Boolean.valueOf(z), null));
    }

    @Override // ru.mail.libverify.api.VerificationApi
    public final void softSignOut() {
        this.p.post(MessageBusUtils.createOneArg(BusMessageType.VERIFY_API_SOFT_SIGN_OUT, (Object) null));
    }

    @Override // ru.mail.libverify.api.VerificationApi
    public final void prepare2StepAuthCheck() {
        this.p.post(MessageBusUtils.createOneArg(BusMessageType.VERIFY_API_PREPARE_2FA_CHECK, (Object) null));
    }

    @Override // ru.mail.libverify.api.VerificationApi
    public final void checkAccountVerification(@NonNull String str) {
        this.p.post(MessageBusUtils.createOneArg(BusMessageType.VERIFY_API_CHECK_ACCOUNT_VERIFICATION, str));
    }

    @Override // ru.mail.libverify.api.VerificationApi
    public final void checkAccountVerificationBySms(@NonNull String str, @Nullable VerificationApi.AccountCheckListener accountCheckListener) {
        this.p.post(MessageBusUtils.createMultipleArgs(BusMessageType.VERIFY_API_CHECK_ACCOUNT_VERIFICATION_BY_SMS, str, accountCheckListener));
    }

    @Override // ru.mail.libverify.api.VerificationApi
    public final void requestIvrPhoneCall(@NonNull String str, VerificationApi.IvrStateListener ivrStateListener) {
        this.p.post(MessageBusUtils.createMultipleArgs(BusMessageType.VERIFY_API_REQUEST_IVR, str, ivrStateListener));
    }

    @Override // ru.mail.libverify.api.VerificationApi
    public final void checkPhoneNumber(@NonNull String str, @NonNull String str2, @NonNull String str3, boolean z, @NonNull VerificationApi.PhoneCheckListener phoneCheckListener) {
        this.p.post(MessageBusUtils.createMultipleArgs(BusMessageType.VERIFY_API_CHECK_PHONE_NUMBER, str, str2, str3, Boolean.valueOf(z), phoneCheckListener));
    }

    @Override // ru.mail.libverify.api.VerificationApi
    public final void setCustomLocale(@NonNull Locale locale) {
        this.p.post(MessageBusUtils.createOneArg(BusMessageType.VERIFY_API_SET_LOCALE, locale));
    }

    @Override // ru.mail.libverify.api.VerificationApi
    public final void setSimDataSendDisabled(boolean z) {
        this.p.post(MessageBusUtils.createOneArg(BusMessageType.VERIFY_API_SET_DISABLE_SIM_DATA_SEND, Boolean.valueOf(z)));
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // ru.mail.verify.core.utils.components.MessageHandler
    public final boolean handleMessage(@NonNull Message message) {
        ru.mail.libverify.i.l lVar;
        x a2;
        x b2;
        x b3;
        BusMessageType type = MessageBusUtils.getType(message, "VerificationApi");
        switch (a.g[type.ordinal()]) {
            case BroadcastManager.FLAG_NETWORK_RECEIVER /* 1 */:
                ServerNotificationMessage serverNotificationMessage = (ServerNotificationMessage) MessageBusUtils.getArg(message, ServerNotificationMessage.class, 0);
                ((Boolean) MessageBusUtils.getArg(message, Boolean.class, 1)).booleanValue();
                boolean booleanValue = ((Boolean) MessageBusUtils.getArg(message, Boolean.class, 2)).booleanValue();
                f.c cVar = (f.c) MessageBusUtils.getArg(message, f.c.class, 3);
                FileLog.v("VerificationApi", "processIpcConnectResult state %s", cVar);
                boolean z = false;
                HashSet hashSet = new HashSet();
                switch (a.b[cVar.ordinal()]) {
                    case BroadcastManager.FLAG_NETWORK_RECEIVER /* 1 */:
                        if (!booleanValue) {
                            hashSet.add(j.d.UNABLE_TO_SHOW);
                        }
                        hashSet.add(j.d.DELIVERED);
                        break;
                    case 2:
                        if (booleanValue) {
                            z = a(serverNotificationMessage, true);
                            break;
                        }
                        break;
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        hashSet.add(j.d.IPC_ACCESS_ERROR);
                        if (booleanValue) {
                            z = a(serverNotificationMessage, true);
                            hashSet.add(j.d.DELIVERED);
                            break;
                        } else {
                            hashSet.add(j.d.UNABLE_TO_SHOW);
                            break;
                        }
                    default:
                        throw new IllegalStateException("State should be defined before calling onConnectResult() callback");
                }
                boolean z2 = z;
                a(new ArrayList(hashSet), serverNotificationMessage, (String) null);
                if (!z2) {
                    this.g.b(serverNotificationMessage.getId());
                }
                this.a.releaseLock(this);
                return true;
            case 2:
                Pair pair = (Pair) MessageBusUtils.getArg(message, Pair.class);
                this.f.b((Thread) pair.first, (Throwable) pair.second);
                return true;
            case 3:
                Pair pair2 = (Pair) MessageBusUtils.getArg(message, Pair.class);
                this.f.a((Thread) pair2.first, (Throwable) pair2.second);
                return true;
            case 4:
                x xVar = (x) MessageBusUtils.getArg(message, x.class);
                if (this.b.a(xVar.e())) {
                    a(xVar.e(), new VerificationApi.VerificationStateDescriptor(VerificationApi.VerificationState.FINAL, ru.mail.libverify.api.j.a(), false));
                    throw new IllegalStateException("Two verification sessions have same ids.");
                }
                this.b.a(xVar.e(), xVar);
                this.p.getBackgroundWorker().submit(new u(this));
                j();
                a(new t(this, xVar));
                return true;
            case 5:
                String str = (String) MessageBusUtils.getArg(message, String.class);
                x d2 = this.b.d(str);
                this.J.a(str);
                if (d2 != null) {
                    this.f.e(d2);
                    return true;
                }
                this.f.e(str);
                return true;
            case 6:
                x a3 = this.b.a((String) MessageBusUtils.getArg(message, String.class), n.c.NOT_TEMPORARY);
                if (a3 != null) {
                    a3.A();
                    return true;
                }
                return true;
            case 7:
                String str2 = (String) MessageBusUtils.getArg(message, String.class, 0);
                v vVar = new v(this, str2, (VerificationApi.CancelReason) MessageBusUtils.getArg(message, VerificationApi.CancelReason.class, 1));
                if (this.a.c("instance_background_verify") && (ru.mail.verify.core.utils.Utils.hasSelfPermission(this.a.getContext(), "android.permission.READ_PHONE_STATE") || ru.mail.verify.core.utils.Utils.hasSelfPermission(this.a.getContext(), "android.permission.RECEIVE_SMS"))) {
                    x a4 = this.b.a(str2, n.c.ALL);
                    if (a4 == null) {
                        return true;
                    }
                    if (!a4.k().completedSuccessfully()) {
                        this.b.c(str2);
                        FileLog.v("VerificationApi", "cancel verification for session %s has been delayed for %d", str2, 600000);
                        this.f.k(a4);
                        this.p.getDispatcher().postDelayed(vVar, 600000L);
                        return true;
                    }
                }
                vVar.run();
                return true;
            case 8:
                String str3 = (String) MessageBusUtils.getArg(message, String.class);
                FileLog.v("VerificationApi", "request new sms code for session %s", str3);
                x a5 = this.b.a(str3, n.c.NOT_TEMPORARY);
                if (a5 == null) {
                    a(str3, new VerificationApi.VerificationStateDescriptor(VerificationApi.VerificationState.FINAL, ru.mail.libverify.api.j.a(), false));
                    return true;
                }
                a5.z();
                this.f.h(a5);
                return true;
            case 9:
                if (this.H == null) {
                    this.H = new l(this.m);
                }
                this.H.a((String) MessageBusUtils.getArg(message, String.class, 0), (String) MessageBusUtils.getArg(message, String.class, 1), (String) MessageBusUtils.getArg(message, String.class, 2), ((Boolean) MessageBusUtils.getArg(message, Boolean.class, 3)).booleanValue(), (VerificationApi.PhoneCheckListener) MessageBusUtils.getArg(message, VerificationApi.PhoneCheckListener.class, 4));
                return true;
            case 10:
                String str4 = (String) MessageBusUtils.getArg(message, String.class, 0);
                VerificationApi.IvrStateListener ivrStateListener = (VerificationApi.IvrStateListener) MessageBusUtils.getArg(message, VerificationApi.IvrStateListener.class, 1);
                FileLog.d("VerificationApi", "ivr requested for session %s", str4);
                x a6 = this.b.a(str4, n.c.NOT_TEMPORARY);
                if (a6 != null) {
                    this.f.g(a6);
                    a6.a(ivrStateListener);
                    return true;
                }
                return true;
            case 11:
                String str5 = (String) MessageBusUtils.getArg(message, String.class, 0);
                String str6 = (String) MessageBusUtils.getArg(message, String.class, 1);
                FileLog.v("VerificationApi", "sms code %s code verification for session %s", str6, str5);
                x a7 = this.b.a(str5, n.c.NOT_TEMPORARY);
                if (a7 != null) {
                    a7.g(str6);
                    return true;
                }
                a(str5, new VerificationApi.VerificationStateDescriptor(VerificationApi.VerificationState.FINAL, ru.mail.libverify.api.j.a(), false));
                return true;
            case 12:
                String str7 = (String) MessageBusUtils.getArg(message, String.class, 0);
                VerificationApi.VerificationStateChangedListener verificationStateChangedListener = (VerificationApi.VerificationStateChangedListener) MessageBusUtils.getArg(message, VerificationApi.VerificationStateChangedListener.class, 1);
                x a8 = this.b.a(str7, n.c.NOT_TEMPORARY);
                if (a8 == null) {
                    verificationStateChangedListener.onStateChanged(str7, null);
                    return true;
                }
                VerificationApi.VerificationStateDescriptor k = a8.k();
                FileLog.v("VerificationApi", "session %s state is %s", str7, k);
                verificationStateChangedListener.onStateChanged(str7, k);
                return true;
            case 13:
                ((VerificationApi.VerificationStatesHandler) MessageBusUtils.getArg(message, VerificationApi.VerificationStatesHandler.class)).onExistingVerificationsFound(this.b.a(n.c.NOT_TEMPORARY));
                return true;
            case 14:
                this.f.b();
                String generateId = new SessionIdGeneratorImpl().generateId();
                String str8 = (String) MessageBusUtils.getArg(message, String.class);
                if (this.L == null) {
                    this.L = new ru.mail.libverify.m.p(this.a.getContext(), this.p, this.m);
                }
                this.L.a(generateId, str8, (v2) -> {
                    b(r3, v2);
                });
                return true;
            case 15:
                Locale locale = (Locale) MessageBusUtils.getArg(message, Locale.class);
                Locale currentLocale = this.a.getCurrentLocale();
                this.a.setCustomLocale(locale);
                if (locale != currentLocale) {
                    ((ru.mail.libverify.api.a) this.i.get()).c();
                    ru.mail.libverify.m.l lVar2 = this.a;
                    Utils.safeExecute("VerificationApi", (ActionExecutor) this.A.get(), new ru.mail.libverify.i.l(lVar2, ru.mail.libverify.i.m.a(lVar2.getRegistrar().getRegistrationId())));
                    return true;
                }
                return true;
            case 16:
                boolean booleanValue2 = ((Boolean) MessageBusUtils.getArg(message, Boolean.class)).booleanValue();
                Boolean isDisabledSimDataSend = this.a.isDisabledSimDataSend();
                this.a.setSimDataSendDisabled(booleanValue2);
                if (isDisabledSimDataSend.booleanValue() == booleanValue2 || booleanValue2) {
                    return true;
                }
                FileLog.d("VerificationApi", "force update sim card data to server");
                ru.mail.libverify.m.l lVar3 = this.a;
                Utils.safeExecute("VerificationApi", (ActionExecutor) this.A.get(), new ru.mail.libverify.i.l(lVar3, ru.mail.libverify.i.m.a(lVar3.getRegistrar().getRegistrationId())));
                return true;
            case 17:
                if (this.a.setApiEndpoints(MessageBusUtils.getArgMap(message, String.class, String.class))) {
                    this.f.k();
                    return true;
                }
                return true;
            case 18:
                this.a.b((String) MessageBusUtils.getArg(message, String.class));
                return true;
            case 19:
                this.a.c();
                return true;
            case 20:
                VerificationApi.PhoneAccountSearchListener phoneAccountSearchListener = (VerificationApi.PhoneAccountSearchListener) MessageBusUtils.getArg(message, VerificationApi.PhoneAccountSearchListener.class, 0);
                if (phoneAccountSearchListener != null) {
                    b(phoneAccountSearchListener);
                    return true;
                }
                return true;
            case 21:
                if (((Boolean) MessageBusUtils.getArg(message, Boolean.class)).booleanValue()) {
                    this.a.getNetwork().testNetwork();
                    return true;
                }
                return true;
            case 22:
                e(true);
                this.f.e();
                return true;
            case 23:
                boolean booleanValue3 = ((Boolean) MessageBusUtils.getArg(message, Boolean.class, 0)).booleanValue();
                SignOutCallback signOutCallback = (SignOutCallback) MessageBusUtils.getArg(message, SignOutCallback.class, 1);
                InstanceConfig e2 = this.a.e();
                e(false);
                if (booleanValue3) {
                    lVar = r0;
                    ru.mail.libverify.i.l lVar4 = new ru.mail.libverify.i.l(e2, ru.mail.libverify.i.m.d(ru.mail.verify.core.storage.InstanceConfig.DEVICE_TYPE_PHONE, e2.getRegistrar().getRegistrationId()));
                } else {
                    lVar = r0;
                    ru.mail.libverify.i.l lVar5 = new ru.mail.libverify.i.l(e2, ru.mail.libverify.i.m.d("device", e2.getRegistrar().getRegistrationId()));
                }
                a(lVar, signOutCallback);
                this.f.e();
                return true;
            case 24:
                SignOutCallback signOutCallback2 = (SignOutCallback) MessageBusUtils.getNullableArg(message, SignOutCallback.class);
                e(false);
                InstanceConfig e3 = this.a.e();
                a(new ru.mail.libverify.i.l(e3, ru.mail.libverify.i.m.a("report_soft_signout", e3.getRegistrar().getRegistrationId())), signOutCallback2);
                this.f.f();
                ((AlarmManager) this.s.get()).createBuilder().setAction(BusMessageType.GCM_REFRESH_TOKEN.name()).putExtra(ApiManager.GCM_TOKEN_CHECK_TYPE, GCMTokenCheckType.ONCE.name()).setTimeout(30000L).disableRandomShift().setUp();
                return true;
            case 25:
                d(true);
                return true;
            case 26:
                a((String) MessageBusUtils.getArg(message, String.class, 0), (VerificationApi.AccountCheckListener) MessageBusUtils.getArg(message, VerificationApi.AccountCheckListener.class, 1));
                return true;
            case 27:
                this.e.add((VerificationApi.GcmTokenListener) MessageBusUtils.getArg(message, VerificationApi.GcmTokenListener.class));
                j j2 = j();
                FileLog.v("VerificationApi", "push token requested from an application (%s)", j2);
                a(j2);
                return true;
            case 28:
                if (this.r.getServerId().equals((String) MessageBusUtils.getArg(message, String.class, 0))) {
                    c((String) MessageBusUtils.getArg(message, String.class, 1));
                    return true;
                }
                return true;
            case 29:
                if (this.r.getServerId().equals((String) MessageBusUtils.getArg(message, String.class, 0))) {
                    j((String) MessageBusUtils.getArg(message, String.class, 1));
                    return true;
                }
                return true;
            case 30:
                if (this.r.getServerId().equals((String) MessageBusUtils.getArg(message, String.class, 0))) {
                    i((String) MessageBusUtils.getArg(message, String.class, 1));
                    return true;
                }
                return true;
            case 31:
                a(j());
                return true;
            case 32:
                Exception exc = (Exception) MessageBusUtils.getArg(message, Exception.class, 0);
                boolean booleanValue4 = ((Boolean) MessageBusUtils.getArg(message, Boolean.class, 1)).booleanValue();
                FileLog.e("VerificationApi", exc, "Failed to update push token (max attempt: %s)", Boolean.valueOf(booleanValue4));
                if (booleanValue4) {
                    this.f.j();
                    return true;
                }
                this.f.h();
                return true;
            case 33:
                return true;
            case 34:
                if (this.a.getSettings().getValue("api_no_gcm_service_sent") == null) {
                    ru.mail.libverify.m.l lVar6 = this.a;
                    Utils.safeExecute("VerificationApi", (ActionExecutor) this.A.get(), new ru.mail.libverify.i.l(lVar6, ru.mail.libverify.i.m.a("report_no_gcm_service", lVar6.getRegistrar().getRegistrationId())));
                    this.a.getSettings().putValue("api_no_gcm_service_sent", Long.toString(this.v.getLocalTime())).commit();
                    return true;
                }
                return true;
            case 35:
                boolean booleanValue5 = ((Boolean) MessageBusUtils.getArg(message, Boolean.class)).booleanValue();
                FileLog.v("VerificationApi", "onNetworkStateChangedInternal %s", Boolean.valueOf(booleanValue5));
                if (!booleanValue5) {
                    if (this.b.b()) {
                        FileLog.d("VerificationApi", "no network, start connection check");
                        this.a.f();
                        return true;
                    }
                    return true;
                }
                ru.mail.libverify.i.c.e();
                ((ru.mail.libverify.e.c) this.h.get()).h();
                j();
                FileLog.v("VerificationApi", "sessions notified (count = %d) network available", Integer.valueOf(this.b.f()));
                for (x xVar2 : this.b.b(n.c.ALL)) {
                    xVar2.w();
                }
                return true;
            case 36:
                String str9 = (String) MessageBusUtils.getArg(message, String.class);
                ServerNotificationMessage b4 = this.g.b(str9);
                if (b4 == null) {
                    FileLog.e("VerificationApi", "notification with id %s doesn't exist", str9);
                    return true;
                } else if (!b4.getMessage().hasConfirmation()) {
                    FileLog.e("VerificationApi", "notification with id %s is not allowed to be confirmed", str9);
                    return true;
                } else {
                    ServerNotificationMessage.Message message2 = b4.getMessage();
                    ((NotificationBarManager) this.z.get()).cancel(b4.getId());
                    FileLog.v("VerificationApi", "notification %s confirmed", b4);
                    try {
                        Utils.safeExecute("VerificationApi", (ActionExecutor) this.A.get(), new ru.mail.libverify.i.a(this.a, message2.getVerificationUrl(), message2.getVerifyCode(), this.a.getId()));
                        this.f.a(b4);
                        return true;
                    } catch (Exception e4) {
                        DebugUtils.safeThrow("VerificationApi", e4, "Failed to prepare AttemptApiRequest for %s", b4);
                        return true;
                    }
                }
            case 37:
                a((String) MessageBusUtils.getArg(message, String.class));
                return true;
            case 38:
                d((String) MessageBusUtils.getArg(message, String.class, 0), (String) MessageBusUtils.getArg(message, String.class, 1));
                return true;
            case 39:
                String str10 = (String) message.obj;
                for (x xVar3 : this.b.b(n.c.ALL)) {
                    xVar3.f(str10);
                }
                return true;
            case 40:
                int intValue = ((Integer) MessageBusUtils.getArg(message, Integer.class, 0)).intValue();
                String str11 = (String) MessageBusUtils.getArg(message, String.class, 1);
                if (this.G == null) {
                    this.G = new ru.mail.libverify.t.a(this.m);
                }
                this.G.onSmsRetrieverSmsReceived(intValue, str11);
                return true;
            case 41:
                this.f.l();
                return true;
            case 42:
                this.f.m();
                return true;
            case 43:
                this.f.n();
                return true;
            case 44:
                c((String) MessageBusUtils.getArg(message, String.class, 0), (String) MessageBusUtils.getArg(message, String.class, 1));
                return true;
            case 45:
                a((String) MessageBusUtils.getArg(message, String.class, 0), ((Long) MessageBusUtils.getArg(message, Long.class, 1)).longValue());
                return true;
            case 46:
            case 47:
                String str12 = (String) MessageBusUtils.getArg(message, String.class);
                if (!this.a.c("instance_single_fetcher") || this.b.b()) {
                    FileLog.d("VerificationApi", "fetcher communication disabled");
                    return true;
                } else if (type == BusMessageType.SERVICE_IPC_FETCHER_STOPPED_RECEIVED) {
                    ((ru.mail.libverify.e.c) this.h.get()).b(str12);
                    return true;
                } else {
                    ((ru.mail.libverify.e.c) this.h.get()).a(str12);
                    return true;
                }
            case 48:
                ((ru.mail.libverify.e.c) this.h.get()).a();
                return true;
            case 49:
                a(i.a.valueOf(((Bundle) MessageBusUtils.getArg(message, Bundle.class)).getString("settings_action_type")));
                return true;
            case 50:
                a(((Bundle) MessageBusUtils.getArg(message, Bundle.class)).getBoolean("battery_level_low"));
                return true;
            case 51:
                String string = ((Bundle) MessageBusUtils.getArg(message, Bundle.class)).getString(NotificationBase.NOTIFICATION_ID_EXTRA);
                if (TextUtils.isEmpty(string)) {
                    return true;
                }
                d(string);
                return true;
            case 52:
                g((String) MessageBusUtils.getArg(message, String.class));
                return true;
            case 53:
                e((String) MessageBusUtils.getArg(message, String.class));
                return true;
            case 54:
                f((String) MessageBusUtils.getArg(message, String.class));
                return true;
            case 55:
                a(((Integer) MessageBusUtils.getArg(message, Integer.class, 1)).intValue(), (String) MessageBusUtils.getArg(message, String.class, 0));
                return true;
            case 56:
                this.f.a(((Boolean) MessageBusUtils.getArg(message, Boolean.class)).booleanValue());
                return true;
            case 57:
                this.f.c((String) MessageBusUtils.getNullableArg(message, String.class));
                return true;
            case 58:
                a((String) MessageBusUtils.getArg(message, String.class, 0), (i.c) MessageBusUtils.getArg(message, i.c.class, 1));
                return true;
            case 59:
                h((String) MessageBusUtils.getArg(message, String.class));
                return true;
            case 60:
                this.f.b((String) MessageBusUtils.getArg(message, String.class));
                return true;
            case 61:
                this.f.a(b.EnumC0014b.ALL, (String) null);
                return true;
            case 62:
                this.f.a(b.EnumC0014b.SMS_DIALOG, (String) MessageBusUtils.getArg(message, String.class));
                return true;
            case 63:
                this.f.a(b.EnumC0014b.SMS, (String) MessageBusUtils.getArg(message, String.class));
                return true;
            case 64:
                this.f.d((String) MessageBusUtils.getArg(message, String.class));
                return true;
            case 65:
                this.f.d((String) null);
                return true;
            case 66:
                a((String) MessageBusUtils.getArg(message, String.class, 0), (String) MessageBusUtils.getArg(message, String.class, 1), (VerificationApi.AccountCheckResult) MessageBusUtils.getArg(message, VerificationApi.AccountCheckResult.class, 2));
                return true;
            case 67:
                d(false);
                return true;
            case 68:
                this.a.acquireLock(this, false, 0);
                return true;
            case 69:
                this.a.releaseLock(this);
                return true;
            case 70:
                a((String) MessageBusUtils.getArg(message, String.class, 0), (ru.mail.libverify.api.e) MessageBusUtils.getArg(message, ru.mail.libverify.api.e.class, 1));
                return true;
            case 71:
                b(((Boolean) MessageBusUtils.getArg(message, Boolean.class)).booleanValue());
                return true;
            case 72:
                c(((Boolean) MessageBusUtils.getArg(message, Boolean.class)).booleanValue());
                return true;
            case 73:
                b((String) MessageBusUtils.getArg(message, String.class));
                return true;
            case 74:
                a((ServerInfo) MessageBusUtils.getArg(message, ServerInfo.class), j.c.FETCHER);
                return true;
            case 75:
                a((ru.mail.libverify.i.c) MessageBusUtils.getArg(message, ru.mail.libverify.i.c.class, 0), (Throwable) MessageBusUtils.getArg(message, Throwable.class, 1));
                return true;
            case 76:
                this.f.a((ru.mail.libverify.j.c) MessageBusUtils.getArg(message, ru.mail.libverify.j.c.class));
                return true;
            case 77:
                this.f.g();
                return true;
            case 78:
                this.a.acquireLock((ServerNotificationMessage) MessageBusUtils.getArg(message, ServerNotificationMessage.class), false, 12);
                return true;
            case 79:
                this.a.releaseLock((ServerNotificationMessage) MessageBusUtils.getArg(message, ServerNotificationMessage.class));
                return true;
            case 80:
                String str13 = (String) MessageBusUtils.getArg(message, String.class, 0);
                Long l = (Long) MessageBusUtils.getArg(message, Long.class, 1);
                if (this.g.a(str13) == null) {
                    FileLog.e("VerificationApi", "notification with id %s doesn't exist", str13);
                    return true;
                }
                ((AlarmManager) this.s.get()).createBuilder().setAction(BusMessageType.SERVICE_SETTINGS_NOTIFICATION_UNBLOCK.name()).putExtra(NotificationBase.NOTIFICATION_ID_EXTRA, str13).setTimeout(l.longValue()).disableRandomShift().setUp();
                return true;
            case 81:
                ResponseBase responseBase = (ResponseBase) MessageBusUtils.getArg(message, ResponseBase.class);
                if (responseBase instanceof ru.mail.libverify.j.c) {
                    a((ru.mail.libverify.j.c) responseBase);
                    return true;
                }
                return true;
            case 82:
                RequestBase requestBase = (RequestBase) MessageBusUtils.getArg(message, RequestBase.class, 0);
                if (requestBase instanceof ru.mail.libverify.i.c) {
                    a((ru.mail.libverify.i.c) requestBase, (Throwable) MessageBusUtils.getArg(message, Throwable.class, 1));
                    return true;
                }
                return true;
            case 83:
                a((x) MessageBusUtils.getArg(message, x.class));
                return true;
            case 84:
                b((x) MessageBusUtils.getArg(message, x.class));
                return true;
            case 85:
                ((ru.mail.libverify.api.f) this.j.get()).a((String) MessageBusUtils.getNullableArg(message, String.class));
                return true;
            case 86:
                a((String) MessageBusUtils.getArg(message, String.class, 0), (VerificationApi.VerificationStateDescriptor) MessageBusUtils.getArg(message, VerificationApi.VerificationStateDescriptor.class, 1));
                return true;
            case 87:
                ((ru.mail.libverify.e.c) this.h.get()).b((ru.mail.libverify.j.f) MessageBusUtils.getNullableArg(message, ru.mail.libverify.j.f.class));
                return true;
            case 88:
                String str14 = (String) MessageBusUtils.getArg(message, String.class, 0);
                String str15 = (String) MessageBusUtils.getArg(message, String.class, 1);
                if (str14 == null || str15 == null || (a2 = this.b.a(str14, n.c.ALL)) == null) {
                    return true;
                }
                this.f.b(a2);
                Utils.safeExecute("VerificationApi", (ActionExecutor) this.A.get(), new ru.mail.libverify.i.l(this.a, ru.mail.libverify.i.m.a(str15, str14, ((f) this.m).getConfig().getRegistrar().getRegistrationId(), a2.f()), null));
                return true;
            case 89:
                String str16 = (String) MessageBusUtils.getArg(message, String.class, 0);
                String str17 = (String) MessageBusUtils.getArg(message, String.class, 1);
                if (str16 == null || str17 == null || (b2 = this.b.b(str16)) == null) {
                    return true;
                }
                this.f.a(b2);
                Utils.safeExecute("VerificationApi", (ActionExecutor) this.A.get(), new ru.mail.libverify.i.l(this.a, ru.mail.libverify.i.m.b(str17, str16, ((f) this.m).getConfig().getRegistrar().getRegistrationId(), b2.f()), null));
                return true;
            case 90:
                String str18 = (String) MessageBusUtils.getArg(message, String.class, 0);
                Integer num = (Integer) MessageBusUtils.getArg(message, Integer.TYPE, 1);
                if (str18 == null || num == null || (b3 = this.b.b(str18)) == null) {
                    return true;
                }
                String f2 = b3.f();
                ArrayList<ru.mail.libverify.c.b> g2 = b3.g();
                ru.mail.libverify.i.m a9 = ru.mail.libverify.i.m.a(g2, str18, ((f) this.m).getConfig().getRegistrar().getRegistrationId(), f2);
                FileLog.d("MOBILE_ID_ROUTES", "send libverifysettings: " + g2);
                if (num.intValue() != 200) {
                    Utils.safeExecute("VerificationApi", (ActionExecutor) this.A.get(), new ru.mail.libverify.i.l(this.a, a9));
                    return true;
                }
                Network network = ConnectivityHelper.getNetwork().get();
                Object[] objArr = new Object[1];
                objArr[0] = network != null ? network.toString() : null;
                FileLog.d("ConnectivityHelper", "Result of cellular request: %s", objArr);
                if (network == null) {
                    Utils.safeExecute("VerificationApi", (ActionExecutor) this.A.get(), new ru.mail.libverify.i.l(this.a, ru.mail.libverify.i.m.a(a9, g2), null));
                    return true;
                }
                try {
                    new ru.mail.libverify.i.l(this.a, a9, network).execute();
                    FileLog.d("VerificationApi", "Request executed over cellular.");
                    return true;
                } catch (Throwable unused) {
                    FileLog.d("VerificationApi", "Failed to execute request over cellular.");
                    Utils.safeExecute("VerificationApi", (ActionExecutor) this.A.get(), new ru.mail.libverify.i.l(this.a, ru.mail.libverify.i.m.a(a9, g2)));
                    return true;
                }
            case 91:
                String str19 = (String) MessageBusUtils.getArg(message, String.class, 0);
                String str20 = (String) MessageBusUtils.getArg(message, String.class, 1);
                if (str19 == null || str20 == null || !this.b.a(str19)) {
                    return false;
                }
                this.b.b(str19).e(str20);
                this.b.b(str19).D();
                return true;
            case 92:
                ConnectivityHelper.unregisterCallback(this.a.getId());
                break;
            case 93:
                break;
            case 94:
                Long longValue = this.a.getSettings().getLongValue("settings_fetcher_timestamp_key", (Long) null);
                String value = this.a.getSettings().getValue("settings_fetcher_etag_key");
                if (longValue != null) {
                    ((ru.mail.libverify.e.c) this.h.get()).a(longValue);
                }
                if (value != null) {
                    ((ru.mail.libverify.e.c) this.h.get()).c(value);
                }
                ((ru.mail.libverify.e.c) this.h.get()).a();
                SmsRetrieverService.resendState();
                return true;
            default:
                FileLog.e("VerificationApi", "message %s is not supported", type);
                return false;
        }
        Long c2 = ((ru.mail.libverify.e.c) this.h.get()).c();
        String b5 = ((ru.mail.libverify.e.c) this.h.get()).b();
        if (c2 != null) {
            this.a.getSettings().putValue("settings_fetcher_timestamp_key", c2.longValue()).commit();
        } else {
            this.a.getSettings().removeValue("settings_fetcher_timestamp_key");
        }
        if (b5 != null) {
            this.a.getSettings().putValue("settings_fetcher_etag_key", b5).commit();
        } else {
            this.a.getSettings().removeValue("settings_fetcher_etag_key");
        }
        ((ru.mail.libverify.e.c) this.h.get()).f();
        return true;
    }

    private void i(@NonNull String str) {
        FileLog.v("VerificationApi", "received fetcher info %s", str);
        try {
            ru.mail.libverify.j.f fVar = (ru.mail.libverify.j.f) JsonParser.fromJson(str, ru.mail.libverify.j.f.class);
            try {
                FileLog.v("VerificationApi", "received fetcher info %s", fVar);
                ((ru.mail.libverify.e.c) this.h.get()).b(fVar);
            } catch (Exception e2) {
                DebugUtils.safeThrow("VerificationApi", "failed to process fetcher info", e2);
            }
        } catch (JsonParseException e3) {
            DebugUtils.safeThrow("VerificationApi", "failed to parse fetcher info json", e3);
        }
    }

    private void e(@NonNull ServerNotificationMessage serverNotificationMessage) {
        boolean z;
        ServerNotificationMessage.Message message = serverNotificationMessage.getMessage();
        if (TextUtils.isEmpty(message.getFrom()) || TextUtils.isEmpty(message.getText())) {
            FileLog.e("VerificationApi", "ether text or from field is undefined");
            return;
        }
        if (this.a.c("instance_write_history")) {
            this.o.a(message.getFrom(), message.getText(), serverNotificationMessage.getLocalTimestamp(), serverNotificationMessage.getServerTimestamp());
        }
        boolean z2 = message.getFlags().contains(ServerNotificationMessage.Message.NotificationFlags.IPC) && !TextUtils.isEmpty(message.getSessionId());
        boolean contains = message.getFlags().contains(ServerNotificationMessage.Message.NotificationFlags.SMS);
        boolean z3 = message.getFlags().contains(ServerNotificationMessage.Message.NotificationFlags.POPUP) && NotificationUtils.isNotificationEnabled(this.a.getContext(), this.a.getContext().getString(R.string.libverify_high_notification_id));
        if (!z2 && !z3 && !contains) {
            FileLog.e("VerificationApi", "all notifications blocked by flags");
            a(Arrays.asList(j.d.SMS_ACCESS_ERROR, j.d.IPC_ACCESS_ERROR), serverNotificationMessage, (String) null);
            return;
        }
        ServerNotificationMessage a2 = this.g.a(serverNotificationMessage.getId());
        if (serverNotificationMessage.equals(a2)) {
            this.f.a(a2, serverNotificationMessage);
            z = false;
        } else {
            this.g.a(serverNotificationMessage.getId(), serverNotificationMessage);
            z = true;
        }
        if (!z) {
            FileLog.d("VerificationApi", "message %s has been already registered", serverNotificationMessage);
        } else if (z2) {
            a(serverNotificationMessage, false);
            ServerNotificationMessage.Message message2 = serverNotificationMessage.getMessage();
            FileLog.v("VerificationApi", "post ipc message to session %s", message2.getSessionId());
            ru.mail.libverify.f.f fVar = new ru.mail.libverify.f.f(this.a.getContext(), this, new r(this, serverNotificationMessage, contains, z3));
            this.a.acquireLock(this, true, 0);
            fVar.a(message2.getSessionId(), message2.getText(), message2.getRequesterPackageName());
        } else if (z3) {
            a(serverNotificationMessage, false);
            a(Collections.singletonList(j.d.DELIVERED), serverNotificationMessage, (String) null);
        } else {
            FileLog.d("VerificationApi", "failed to write sms");
            a(Collections.singletonList(j.d.SMS_ACCESS_ERROR), serverNotificationMessage, (String) null);
        }
    }

    private j j() {
        String registrationId = ((GcmRegistrar) this.w.get()).getRegistrationId();
        String value = this.a.getSettings().getValue("api_last_sent_push_token");
        FileLog.v("VerificationApi", "update push token %s -> %s", value, registrationId);
        if (TextUtils.isEmpty(registrationId)) {
            return j.UPDATING;
        }
        if (TextUtils.equals(value, registrationId)) {
            return j.ACTUAL;
        }
        ru.mail.libverify.m.l lVar = this.a;
        Utils.safeExecute("VerificationApi", (ActionExecutor) this.A.get(), new ru.mail.libverify.i.l(lVar, ru.mail.libverify.i.m.a(lVar.getRegistrar().getRegistrationId())));
        if (TextUtils.isEmpty(value)) {
            this.f.i();
        }
        return j.CHANGED;
    }

    private void g(@NonNull String str) {
        ServerNotificationMessage a2 = this.g.a(str);
        if (a2 == null) {
            FileLog.e("VerificationApi", "notification id %s doesn't exist", str);
            return;
        }
        FileLog.v("VerificationApi", "notification %s show settings", a2);
        ((NotificationBarManager) this.z.get()).show(new ru.mail.libverify.notifications.k(this.a.getContext(), a2, true));
        this.f.e(a2);
    }

    private void f(@NonNull String str) {
        ServerNotificationMessage b2 = this.g.b(str);
        if (b2 == null) {
            FileLog.e("VerificationApi", "notification with id %s doesn't exist", str);
            return;
        }
        FileLog.v("VerificationApi", "notification %s execute report spam", b2);
        String from = b2.getMessage().getFrom();
        ru.mail.libverify.m.l lVar = this.a;
        Utils.safeExecute("VerificationApi", (ActionExecutor) this.A.get(), new ru.mail.libverify.i.l(lVar, ru.mail.libverify.i.m.c(from, lVar.getRegistrar().getRegistrationId())));
        ((NotificationBarManager) this.z.get()).cancel(str);
    }

    private void h(@NonNull String str) {
        ServerNotificationMessage a2 = this.g.a(str);
        if (a2 == null) {
            FileLog.e("VerificationApi", "notification with id %s doesn't exist", str);
            return;
        }
        FileLog.v("VerificationApi", "notification %s opened notification popup", a2);
        ((NotificationBarManager) this.z.get()).show(new ru.mail.libverify.notifications.k(this.a.getContext(), a2, true));
        this.f.d(a2);
    }

    private void d(@NonNull String str) {
        ServerNotificationMessage a2 = this.g.a(str);
        if (a2 == null) {
            FileLog.e("VerificationApi", "notification id %s doesn't exist", str);
        } else {
            ((NotificationBarManager) this.z.get()).show(new ru.mail.libverify.notifications.k(this.a.getContext(), a2, true));
        }
    }

    private void c(boolean z) {
        FileLog.d("VerificationApi", "fetcher stopped, publish = %s", Boolean.valueOf(z));
        this.a.a(z);
        if (!z || this.b.b()) {
            FileLog.d("VerificationApi", "fetcher started, didn't published");
            return;
        }
        if (this.a.c("instance_single_fetcher")) {
            FileLog.v("VerificationApi", "fetcher started result %s", Boolean.FALSE);
            new ru.mail.libverify.f.f(this.a.getContext(), this, new s(false)).b();
        } else {
            FileLog.d("VerificationApi", "fetcher communication disabled");
        }
        this.f.d();
    }

    @Override // ru.mail.libverify.api.VerificationApi
    @NonNull
    public final String startVerification(@NonNull String str, @NonNull String str2, @Nullable String str3, @Nullable Map<String, String> map, @Nullable VerifyRoute verifyRoute, @Nullable VerificationParameters verificationParameters) throws IllegalStateException {
        return a(str, verifyRoute == null ? ru.mail.libverify.d.d.a() : ru.mail.libverify.d.d.a(verifyRoute), str2, str3, map, null, verificationParameters);
    }

    @Override // ru.mail.libverify.api.VerificationApi
    public final void cancelVerification(@NonNull String str, VerificationApi.CancelReason cancelReason) {
        if (TextUtils.isEmpty(str)) {
            FileLog.e("VerificationApi", "session id must be not null");
        } else {
            this.p.post(MessageBusUtils.createMultipleArgs(BusMessageType.VERIFY_API_CANCEL_VERIFICATION, str, cancelReason));
        }
    }

    @Override // ru.mail.libverify.api.VerificationApi
    @Deprecated
    public final void searchPhoneAccounts(@NonNull VerificationApi.PhoneAccountSearchListener phoneAccountSearchListener, boolean z) {
        if (z) {
            this.p.post(MessageBusUtils.createMultipleArgs(BusMessageType.VERIFY_API_SEARCH_PHONE_ACCOUNTS, phoneAccountSearchListener));
        }
    }

    @Override // ru.mail.libverify.api.VerificationApi
    public final void signOut(boolean z, @Nullable SignOutCallback signOutCallback) {
        this.p.post(MessageBusUtils.createMultipleArgs(BusMessageType.VERIFY_API_SIGN_OUT, Boolean.valueOf(z), signOutCallback));
    }

    @Override // ru.mail.libverify.api.VerificationApi
    public final void softSignOut(SignOutCallback signOutCallback) {
        this.p.post(MessageBusUtils.createOneArg(BusMessageType.VERIFY_API_SOFT_SIGN_OUT, signOutCallback));
    }

    private void d(ServerNotificationMessage serverNotificationMessage) {
        FileLog.v("VerificationApi", "ping message %s", serverNotificationMessage);
        this.I.a(serverNotificationMessage.getMessage().getPushTokenId());
        Utils.safeExecute("VerificationApi", (ActionExecutor) this.A.get(), new ru.mail.libverify.i.j(this.a, Collections.singletonList(j.d.DELIVERED), serverNotificationMessage.getMessage().getSessionId(), serverNotificationMessage.getDeliveryMethod(), j.b.SERVER_INFO, null, null, serverNotificationMessage.getMessage().getApplicationId(), serverNotificationMessage.getLocalTimestamp()));
    }

    private void b(@NonNull x xVar) {
        this.a.releaseLock(xVar);
        if (!xVar.k().completedSuccessfully()) {
            ((ru.mail.libverify.e.c) this.h.get()).g();
            return;
        }
        if (this.a.c("instance_safety_net")) {
            ((ru.mail.libverify.api.f) this.j.get()).a();
        }
        ((ru.mail.libverify.e.c) this.h.get()).a();
    }

    private void c(@NonNull String str) {
        FileLog.v("VerificationApi", "gcm message received");
        try {
            ServerNotificationMessage serverNotificationMessage = (ServerNotificationMessage) JsonParser.fromJson(str, ServerNotificationMessage.class);
            serverNotificationMessage.setDeliveryMethod(j.c.GCM);
            f(serverNotificationMessage);
        } catch (JsonParseException e2) {
            DebugUtils.safeThrow("VerificationApi", "failed to process server notification with unexpected json", e2);
        }
    }

    private void a(@Nullable Runnable runnable) {
        String[] andSet = this.n.getAndSet(null);
        if (andSet == null || andSet.length == 0) {
            if (runnable != null) {
                runnable.run();
                return;
            }
            return;
        }
        q.this.a.getContext();
        HashSet hashSet = new HashSet(Arrays.asList("android.permission.READ_SMS", "android.permission.RECEIVE_SMS", "android.permission.READ_CALL_LOG", "android.permission.CALL_PHONE", "android.permission.READ_PHONE_STATE", "android.permission.READ_PHONE_NUMBERS", "android.permission.ACCESS_COARSE_LOCATION"));
        ArrayList arrayList = new ArrayList(andSet.length);
        for (String str : andSet) {
            if (hashSet.contains(str) && !this.k.contains(str)) {
                arrayList.add(str);
                this.k.add(str);
            }
        }
        String[] strArr = (String[]) arrayList.toArray(new String[arrayList.size()]);
        ru.mail.libverify.utils.permissions.a.a(this.a.getContext(), strArr, new e(strArr, runnable));
    }

    private void b(String str, String str2) {
        ru.mail.libverify.m.l lVar = this.a;
        Utils.safeExecute("VerificationApi", (ActionExecutor) this.A.get(), new ru.mail.libverify.i.l(lVar, ru.mail.libverify.i.m.a(str, lVar.getRegistrar().getRegistrationId(), str2)));
    }

    private void c(@NonNull String str, @NonNull String str2) {
        boolean z = false;
        Iterator<x> it = this.b.b(n.c.ALL).iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            x next = it.next();
            if (TextUtils.equals(ru.mail.verify.core.utils.Utils.stringToSHA256(next.e()), str)) {
                next.b(str2, true);
                z = true;
                break;
            }
        }
        if (z) {
            return;
        }
        FileLog.e("VerificationApi", "failed to find target session for ipc message");
    }

    private void f(@NonNull ServerNotificationMessage serverNotificationMessage) {
        FileLog.v("VerificationApi", "process message %s", serverNotificationMessage);
        try {
            serverNotificationMessage.setLocalTimestamp(this.v.getLocalTime());
            this.f.f(serverNotificationMessage);
            if (a(serverNotificationMessage)) {
                ServerNotificationMessage.Message message = serverNotificationMessage.getMessage();
                switch (a.a[message.getType().ordinal()]) {
                    case BroadcastManager.FLAG_NETWORK_RECEIVER /* 1 */:
                        e(serverNotificationMessage);
                        return;
                    case 2:
                        c(serverNotificationMessage);
                        return;
                    case 3:
                        d(serverNotificationMessage);
                        return;
                    case 4:
                        Object[] objArr = new Object[1];
                        objArr[0] = serverNotificationMessage;
                        FileLog.v("VerificationApi", "verified message %s", objArr);
                        x a2 = this.b.a(serverNotificationMessage.getMessage().getSessionId(), n.c.ALL);
                        if (a2 != null) {
                            a2.x();
                            return;
                        }
                        return;
                    default:
                        throw new IllegalArgumentException("unexpected message type " + message.getType());
                }
            }
        } catch (Exception e2) {
            DebugUtils.safeThrow("VerificationApi", e2, "failed to process server notification", new Object[0]);
        }
    }

    private void c(@NonNull ServerNotificationMessage serverNotificationMessage) {
        String str;
        ActionExecutor actionExecutor;
        ru.mail.libverify.i.l lVar;
        FileLog.v("VerificationApi", "ping message %s", serverNotificationMessage);
        int i2 = a.c[serverNotificationMessage.getDeliveryMethod().ordinal()];
        if (i2 == 1) {
            str = "VerificationApi";
            actionExecutor = (ActionExecutor) this.A.get();
            ru.mail.libverify.m.l lVar2 = this.a;
            String str2 = "ping_response_gcm";
            int i3 = a.h[VerificationFactory.getPlatformService(lVar2.getContext()).getServiceType().ordinal()];
            if (i3 == 1) {
                str2 = "ping_response_hms";
            } else if (i3 == 2) {
                str2 = "ping_response_gcm";
            }
            lVar = new ru.mail.libverify.i.l(lVar2, ru.mail.libverify.i.m.a(str2, lVar2.getRegistrar().getRegistrationId()));
        } else if (i2 != 2) {
            if (i2 != 3) {
                throw new IllegalArgumentException("Illegal message delivery method");
            }
            ru.mail.libverify.m.l lVar3 = this.a;
            Utils.safeExecute("VerificationApi", (ActionExecutor) this.A.get(), new ru.mail.libverify.i.l(lVar3, ru.mail.libverify.i.m.a(lVar3.getRegistrar().getRegistrationId())));
            return;
        } else {
            str = "VerificationApi";
            actionExecutor = (ActionExecutor) this.A.get();
            ru.mail.libverify.m.l lVar4 = this.a;
            lVar = r0;
            ru.mail.libverify.i.l lVar5 = new ru.mail.libverify.i.l(lVar4, ru.mail.libverify.i.m.a("ping_response_fetcher", lVar4.getRegistrar().getRegistrationId()));
        }
        Utils.safeExecute(str, actionExecutor, lVar);
    }

    private void d(@NonNull String str, @NonNull String str2) {
        for (x xVar : this.b.b(n.c.ALL)) {
            xVar.b(str2, false);
        }
        Iterator<ServerNotificationMessage> it = this.g.e().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            ServerNotificationMessage next = it.next();
            if (next.getMessage().getVerifyCode() != null && str2.contains(next.getMessage().getVerifyCode())) {
                this.f.c(next);
                break;
            }
        }
        if (this.b.c() && this.g.b() && this.a.c("instance_intercept_sms") && this.a.a(str)) {
            ((ru.mail.libverify.api.a) this.i.get()).a(new ru.mail.libverify.k.k(this.v.getLocalTime(), str, str2));
        }
    }

    private void e(@NonNull String str) {
        ServerNotificationMessage b2 = this.g.b(str);
        if (b2 == null) {
            FileLog.e("VerificationApi", "notification id %s doesn't exist", str);
            return;
        }
        FileLog.v("VerificationApi", "notification %s execute report reuse", b2);
        ru.mail.libverify.m.l lVar = this.a;
        Utils.safeExecute("VerificationApi", (ActionExecutor) this.A.get(), new ru.mail.libverify.i.l(lVar, ru.mail.libverify.i.m.a("report_reuse", lVar.getRegistrar().getRegistrationId())));
        ((NotificationBarManager) this.z.get()).cancel(str);
        this.f.h(b2);
    }

    private void d(boolean z) {
        FileLog.d("VerificationApi", "request sms info");
        if (!z && !this.a.c("instance_intercept_sms") && !this.a.c("instance_account_check_sms")) {
            FileLog.d("VerificationApi", "request sms info disabled by settings");
            return;
        }
        ru.mail.libverify.j.l b2 = this.a.b();
        if (b2 != null) {
            FileLog.d("VerificationApi", "sms info has been already downloaded");
            ((ru.mail.libverify.api.a) this.i.get()).a(b2);
            return;
        }
        ru.mail.libverify.m.l lVar = this.a;
        Utils.safeExecute("VerificationApi", (ActionExecutor) this.A.get(), new ru.mail.libverify.i.l(lVar, ru.mail.libverify.i.m.a("request_sms_info", lVar.getRegistrar().getRegistrationId())));
    }

    private void b(@NonNull String str) {
        ServerNotificationMessage[] serverNotificationMessageArr;
        FileLog.v("VerificationApi", "received message from fetcher: %s", str);
        try {
            for (ServerNotificationMessage serverNotificationMessage : (ServerNotificationMessage[]) JsonParser.fromJson(str, ServerNotificationMessage[].class)) {
                serverNotificationMessage.setDeliveryMethod(j.c.FETCHER);
                f(serverNotificationMessage);
            }
        } catch (JsonParseException e2) {
            DebugUtils.safeThrow("VerificationApi", "failed to parse fetcher json", e2);
        }
    }

    private void b(boolean z) {
        FileLog.d("VerificationApi", "fetcher started, publish = %s", Boolean.valueOf(z));
        this.a.d();
        if (!z || this.b.b()) {
            FileLog.d("VerificationApi", "fetcher started, didn't published");
            return;
        }
        if (this.a.c("instance_single_fetcher")) {
            FileLog.v("VerificationApi", "fetcher started result %s", Boolean.TRUE);
            new ru.mail.libverify.f.f(this.a.getContext(), this, new s(true)).a();
        } else {
            FileLog.d("VerificationApi", "fetcher communication disabled");
        }
        this.f.c();
    }

    private boolean a(@NonNull ServerNotificationMessage serverNotificationMessage) {
        ServerNotificationMessage.Message message = serverNotificationMessage.getMessage();
        ArrayList arrayList = new ArrayList();
        String str = null;
        String imsi = message.getImsi();
        String str2 = imsi;
        if (TextUtils.isEmpty(imsi)) {
            str2 = null;
        }
        String imei = message.getImei();
        String str3 = imei;
        if (TextUtils.isEmpty(imei)) {
            str3 = null;
        }
        boolean z = this.a.isDisabledSimDataSend().booleanValue() && str2 == null;
        if (!z && !TextUtils.equals(this.a.getSimCardData().getDoubleHashedImsi(), str2)) {
            FileLog.e("VerificationApi", "%s provided imsis are not equal to local imsi", this.a.toString());
            arrayList.add(j.d.IMSI_NOT_MATCH);
        }
        boolean z2 = this.a.isDisabledSimDataSend().booleanValue() && str3 == null;
        if (!z2 && !TextUtils.equals(this.a.getSimCardData().getDoubleHashedImei(), str3)) {
            FileLog.e("VerificationApi", "%s provided imeis are not equal to local imeis", this.a.toString());
            arrayList.add(j.d.IMEI_NOT_MATCH);
        }
        FileLog.v("VerificationApi", "Imsi ignored: %s. Imei ignored: %s", Boolean.valueOf(z), Boolean.valueOf(z2));
        if (!TextUtils.equals(ru.mail.verify.core.utils.Utils.stringToSHA256(this.a.getId()), message.getApplicationId())) {
            FileLog.e("VerificationApi", "%s provided id is not equal to local id", this.a.toString());
            arrayList.add(j.d.APPLICATION_ID_NOT_MATCH);
            str = message.getApplicationId();
        }
        if (arrayList.isEmpty()) {
            return true;
        }
        a(arrayList, serverNotificationMessage, str);
        return false;
    }

    private void b(@NonNull VerificationApi.PhoneAccountSearchListener phoneAccountSearchListener) {
        FileLog.d("VerificationApi", "search accounts requested");
        a(() -> {
            ArrayList arrayList = new ArrayList();
            ru.mail.libverify.a.a a2 = this.D.a(this.a.getSimCardData());
            if (a2 != null) {
                arrayList.add(new VerificationApi.PhoneAccountSearchItem(a2.a(), a2.b()));
            }
            phoneAccountSearchListener.onComplete(arrayList);
        });
    }

    private void e(boolean z) {
        FileLog.d("VerificationApi", "instance reset started (drop installation = %s)", Boolean.valueOf(z));
        if (z) {
            this.a.resetId();
            this.a.getSettings().removeValue("api_last_sent_push_token").removeValue("api_settings_timestamp");
            this.p.reset();
        }
        this.b.a();
        this.g.a();
        ((NotificationBarManager) this.z.get()).cancelAll();
        this.a.getSettings().commitSync();
        this.I.a();
        this.a.getRegistrar().getRegistrationId();
        FileLog.d("VerificationApi", "instance reset completed");
    }

    @Override // ru.mail.libverify.api.i
    public final ArrayList a() {
        return this.b.a(n.c.ALL);
    }

    private void a(@NonNull String str, @Nullable String str2, VerificationApi.AccountCheckResult accountCheckResult) {
        FileLog.v("VerificationApi", "account check completed with result %s for %s", accountCheckResult, str);
        if (this.L == null) {
            this.L = new ru.mail.libverify.m.p(this.a.getContext(), this.p, this.m);
        }
        this.L.a(new SessionIdGeneratorImpl().generateId(), str, (v4) -> {
            a(r2, r3, r4, v4);
        });
    }

    private void a(@Nullable String str, ru.mail.libverify.api.e eVar) {
        this.f.a(eVar.toString());
        if (TextUtils.isEmpty(str)) {
            return;
        }
        ru.mail.libverify.m.l lVar = this.a;
        Utils.safeExecute("VerificationApi", (ActionExecutor) this.A.get(), new ru.mail.libverify.i.l(lVar, ru.mail.libverify.i.m.b(str, lVar.getRegistrar().getRegistrationId())));
    }

    /*  JADX ERROR: JadxRuntimeException in pass: BlockProcessor
        jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:28:0x0052
        	at jadx.core.dex.visitors.blocks.BlockProcessor.checkForUnreachableBlocks(BlockProcessor.java:81)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.processBlocksTree(BlockProcessor.java:47)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.visit(BlockProcessor.java:39)
        */
    private void a(ru.mail.libverify.j.c r8) {
        /*
            Method dump skipped, instructions count: 1469
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: ru.mail.libverify.api.q.a(ru.mail.libverify.j.c):void");
    }

    private void a(@NonNull ru.mail.libverify.i.c cVar, @NonNull Throwable th) {
        try {
            FileLog.d("VerificationApi", "handle server failure", th);
            if (th instanceof ServerException) {
                this.f.a(cVar, (ServerException) th);
            } else if (th instanceof IOException) {
                if (this.a.getNetwork().hasNetwork() && cVar.switchToNextApiHost()) {
                    FileLog.d("VerificationApi", "switched to the next api url");
                    this.f.a(cVar, (IOException) th);
                }
            } else if (th instanceof ClientException) {
                this.f.a(cVar, (ClientException) th);
            } else {
                this.f.a(Thread.currentThread(), th);
            }
        } catch (Throwable th2) {
            FileLog.e("VerificationApi", "failed to process server failure", th2);
        }
    }

    private void a(@NonNull x xVar) {
        this.a.acquireLock(xVar, true, 13);
    }

    private void a(@NonNull String str, @NonNull VerificationApi.VerificationStateDescriptor verificationStateDescriptor) {
        ArrayList arrayList;
        x a2;
        x a3;
        if (verificationStateDescriptor.getState() == VerificationApi.VerificationState.FINAL) {
            new Handler(this.a.getContext().getMainLooper()).post(() -> {
                AppStateModel.unregister(this.K);
            });
            ConnectivityHelper.unregisterCallback(str);
        }
        if (this.b.e(str)) {
            if (verificationStateDescriptor.getState() == VerificationApi.VerificationState.SUCCEEDED && (a2 = this.b.a(str, n.c.ALL)) != null) {
                this.f.i(a2);
            }
            FileLog.v("VerificationApi", "session %s state changed to %s", str, verificationStateDescriptor);
            synchronized (this) {
                arrayList = new ArrayList(this.c);
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ((VerificationApi.VerificationStateChangedListener) it.next()).onStateChanged(str, verificationStateDescriptor);
            }
        } else if (this.a.c("instance_background_verify") && (a3 = this.b.a(str, n.c.TEMPORARY)) != null && a3.k().completedSuccessfully()) {
            this.b.d(str);
            this.J.a(str);
            a3.a();
            this.f.f(a3);
            FileLog.v("VerificationApi", "temporary session %s state has been removed after completion (%s)", str, verificationStateDescriptor);
        }
    }

    private void a(String str, String str2) {
        ru.mail.libverify.m.l lVar = this.a;
        Utils.safeExecute("VerificationApi", (ActionExecutor) this.A.get(), new ru.mail.libverify.i.l(lVar, ru.mail.libverify.i.m.a(str, lVar.getRegistrar().getRegistrationId(), str2)));
    }

    private void a(String str, String str2, VerificationApi.AccountCheckResult accountCheckResult, String str3) {
        ru.mail.libverify.m.l lVar = this.a;
        Utils.safeExecute("VerificationApi", (ActionExecutor) this.A.get(), new ru.mail.libverify.i.l(lVar, ru.mail.libverify.i.m.c(str, str2, lVar.getRegistrar().getRegistrationId(), str3)));
        this.f.a(accountCheckResult, accountCheckResult == VerificationApi.AccountCheckResult.OK && !TextUtils.isEmpty(str2));
        if (this.a.c("instance_safety_net")) {
            ((ru.mail.libverify.api.f) this.j.get()).a();
        }
    }

    private void a(j jVar) {
        String registrationId;
        FileLog.d("VerificationApi", "push token update result: %s", jVar);
        if (jVar == j.UPDATING) {
            return;
        }
        int i2 = a.d[jVar.ordinal()];
        if (i2 == 1) {
            registrationId = ((GcmRegistrar) this.w.get()).getRegistrationId();
            this.a.sendApplicationBroadcast(VerificationFactory.LIBVERIFY_GCM_TOKEN_BROADCAST_ACTION, Collections.singletonMap(VerificationFactory.LIBVERIFY_GCM_TOKEN, registrationId));
        } else if (i2 != 2) {
            throw new IllegalArgumentException();
        } else {
            registrationId = ((GcmRegistrar) this.w.get()).getRegistrationId();
        }
        if (this.e.isEmpty()) {
            return;
        }
        Iterator it = this.e.iterator();
        while (it.hasNext()) {
            ((VerificationApi.GcmTokenListener) it.next()).onReceived(registrationId);
        }
        this.e.clear();
    }

    private void a(boolean z) {
        if (this.b.b()) {
            FileLog.d("VerificationApi", "fetcher state change blocked by active sessions");
        } else if (z) {
            ((ru.mail.libverify.e.c) this.h.get()).f();
        } else {
            ((ru.mail.libverify.e.c) this.h.get()).h();
        }
    }

    private void a(@NonNull String str, long j2) {
        ArrayList arrayList = new ArrayList(this.g.e());
        FileLog.v("VerificationApi", "current messages count %d", Integer.valueOf(arrayList.size()));
        boolean z = false;
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ServerNotificationMessage serverNotificationMessage = (ServerNotificationMessage) it.next();
            if (TextUtils.equals(str, serverNotificationMessage.getId()) && serverNotificationMessage.getLocalTimestamp() < j2) {
                this.g.b(serverNotificationMessage.getId());
                z = true;
                FileLog.v("VerificationApi", "removed message timestamp %d", Long.valueOf(serverNotificationMessage.getLocalTimestamp()));
            }
        }
        if (z) {
            ((NotificationBarManager) this.z.get()).cancel(str);
        }
    }

    private void a(@NonNull String str) {
        ServerNotificationMessage b2 = this.g.b(str);
        if (b2 == null) {
            FileLog.e("VerificationApi", "notification with id %s doesn't exist", str);
            return;
        }
        FileLog.v("VerificationApi", "notification %s removed from waiting list", b2);
        ((NotificationBarManager) this.z.get()).cancel(b2.getId());
        this.f.b(b2);
    }

    private void a(i.a aVar) {
        ActionExecutor actionExecutor;
        ru.mail.libverify.i.l lVar;
        switch (a.e[aVar.ordinal()]) {
            case BroadcastManager.FLAG_NETWORK_RECEIVER /* 1 */:
            case 2:
                if (this.a.c("instance_track_package") && b()) {
                    ru.mail.libverify.m.l lVar2 = this.a;
                    Utils.safeExecute("VerificationApi", (ActionExecutor) this.A.get(), new ru.mail.libverify.i.l(lVar2, ru.mail.libverify.i.m.a("check_settings_packages_changed", lVar2.getRegistrar().getRegistrationId())));
                    return;
                }
                return;
            case 3:
                if (b()) {
                    ru.mail.libverify.m.l lVar3 = this.a;
                    Utils.safeExecute("VerificationApi", (ActionExecutor) this.A.get(), new ru.mail.libverify.i.l(lVar3, ru.mail.libverify.i.m.a("check_settings_restart", lVar3.getRegistrar().getRegistrationId())));
                    return;
                }
                return;
            case 4:
                ru.mail.libverify.m.l lVar4 = this.a;
                Utils.safeExecute("VerificationApi", (ActionExecutor) this.A.get(), new ru.mail.libverify.i.l(lVar4, ru.mail.libverify.i.m.a("check_settings_timer", lVar4.getRegistrar().getRegistrationId())));
                return;
            case 5:
                if (!this.a.c("instance_intercept_sms")) {
                    this.a.a((ru.mail.libverify.j.l) null);
                    return;
                } else if (!this.a.a()) {
                    actionExecutor = (ActionExecutor) this.A.get();
                    ru.mail.libverify.m.l lVar5 = this.a;
                    lVar = r0;
                    ru.mail.libverify.i.l lVar6 = new ru.mail.libverify.i.l(lVar5, ru.mail.libverify.i.m.a("request_sms_info", lVar5.getRegistrar().getRegistrationId()));
                    break;
                } else {
                    return;
                }
            case 6:
                if (b()) {
                    actionExecutor = (ActionExecutor) this.A.get();
                    ru.mail.libverify.m.l lVar7 = this.a;
                    lVar = r0;
                    ru.mail.libverify.i.l lVar8 = new ru.mail.libverify.i.l(lVar7, ru.mail.libverify.i.m.a(lVar7.getRegistrar().getRegistrationId()));
                    break;
                } else {
                    return;
                }
            default:
                throw new IllegalArgumentException("Illegal action type provided");
        }
        Utils.safeExecute("VerificationApi", actionExecutor, lVar);
    }

    private void a(@NonNull String str, @NonNull i.c cVar) {
        ServerNotificationMessage a2 = this.g.a(str);
        if (a2 == null) {
            FileLog.e("VerificationApi", "notification with id %s doesn't exist", str);
            cVar.a(null);
            return;
        }
        boolean z = this.a.c("instance_add_shortcut") && this.a.getSettings().getValue("api_has_shortcut") == null;
        if (z) {
            this.a.getSettings().putValue("api_has_shortcut", Boolean.toString(true)).commitSync();
        }
        boolean c2 = this.a.c("instance_write_history");
        ServerNotificationMessage.Message message = a2.getMessage();
        cVar.a(new i.b(a2.getId(), message.getText(), message.getPhone(), message.getFrom(), a2.getDeliveryMethod().toString(), message.getConfirmationText(), Boolean.valueOf(message.hasConfirmation()), message.getDescription(), message.getShortcutName(), c2, z));
    }

    private void a(int i2, @NonNull String str) {
        ServerNotificationMessage a2 = this.g.a(str);
        if (a2 == null) {
            FileLog.e("VerificationApi", "notification with id %s doesn't exist", str);
            return;
        }
        FileLog.v("VerificationApi", "notification %s execute block push for %d", a2, Integer.valueOf(i2));
        String from = a2.getMessage().getFrom();
        ru.mail.libverify.m.l lVar = this.a;
        Utils.safeExecute("VerificationApi", (ActionExecutor) this.A.get(), new ru.mail.libverify.i.l(lVar, ru.mail.libverify.i.m.a(i2, from, lVar.getRegistrar().getRegistrationId())));
        ((NotificationBarManager) this.z.get()).cancel(str);
        this.f.i(a2);
    }

    private void a(@NonNull ru.mail.libverify.j.n nVar) {
        FileLog.v("VerificationApi", "delayed verify status message %s", nVar);
        x xVar = null;
        if (nVar.l() != null) {
            xVar = this.b.a(nVar.l(), n.c.ALL_HASHED);
        }
        if (xVar == null) {
            x a2 = this.b.a(nVar.q(), n.c.ALL);
            xVar = a2;
            if (a2 != null) {
                FileLog.e("VerificationApi", "Delayed response should contain hashed session id due to security reasons (not only session_id)! Response: %s", nVar);
            }
        }
        if (xVar != null) {
            xVar.a(nVar);
        }
    }

    private void a(@NonNull ServerInfo.NotificationInfo notificationInfo) throws IllegalArgumentException {
        if (notificationInfo.getAction() == ServerInfo.NotificationInfo.Action.COMPLETED) {
            String session_id = notificationInfo.getSession_id();
            if (TextUtils.isEmpty(session_id)) {
                FileLog.v("VerificationApi", "remove all push notifications");
                ((NotificationBarManager) this.z.get()).cancelAll();
                return;
            }
            FileLog.v("VerificationApi", "remove all push notifications by sessionId: %s", session_id);
            ((NotificationBarManager) this.z.get()).cancelAllBySessionId(session_id);
        }
    }

    private void a(@NonNull ServerInfo serverInfo, j.c cVar) {
        try {
            FileLog.v("VerificationApi", "received server info %s from %s", serverInfo, cVar);
            x a2 = this.b.a(serverInfo.getHashedSessionId(), n.c.ALL_HASHED);
            boolean z = false;
            Long validUntil = serverInfo.getValidUntil();
            if (validUntil != null) {
                z = this.v.getServerTimestamp() >= validUntil.longValue();
            }
            if (serverInfo.isConfirmRequired() && a2 != null) {
                String e2 = a2.e();
                if (!TextUtils.isEmpty(e2)) {
                    Utils.safeExecute("VerificationApi", (ActionExecutor) this.A.get(), new ru.mail.libverify.i.j(this.a, Collections.singletonList(z ? j.d.TTL_EXPIRED : j.d.DELIVERED), e2, cVar, serverInfo.getType() == ServerInfo.NotificationType.MOBILEID ? j.b.MOBILEID : serverInfo.getType() == ServerInfo.NotificationType.DO_ATTEMPT ? j.b.DO_ATTEMPT : (serverInfo.getCallUiInfo() == null && serverInfo.getCallInInfo() == null && serverInfo.getSmsInfo() == null) ? j.b.SERVER_INFO : j.b.ROUTE_INFO, serverInfo.getRoute(), serverInfo.getPushId(), null, this.v.getLocalTime()));
                }
            }
            if (z) {
                FileLog.v("VerificationApi", "sessionId: serverInfo " + serverInfo.getHashedSessionId() + " rejected as TTL_EXPIRED");
                return;
            }
            if (serverInfo.getDelayedVerifyResponse() != null) {
                a(serverInfo.getDelayedVerifyResponse());
            }
            if (a2 != null) {
                if (this.J.a(a2.e(), serverInfo, cVar).booleanValue()) {
                    FileLog.v("VerificationApi", "sessionId: serverInfo " + serverInfo.getHashedSessionId() + " rejected as double");
                    return;
                } else {
                    a(a2, serverInfo, cVar);
                    return;
                }
            }
            Object[] objArr = new Object[1];
            objArr[0] = serverInfo.getHashedSessionId();
            FileLog.e("VerificationApi", "Not found session %s", objArr);
            if (serverInfo.getNotificationInfo() != null) {
                a(serverInfo.getNotificationInfo());
            }
        } catch (Exception e3) {
            DebugUtils.safeThrow("VerificationApi", "failed to process server info", e3);
        }
    }

    private void a(@NonNull x xVar, @NonNull ServerInfo serverInfo, @NonNull j.c cVar) {
        ru.mail.libverify.d.b bVar;
        ServerInfo.CallInInfo callInInfo;
        if (serverInfo.getCallInfo() != null) {
            xVar.a(serverInfo.getCallInfo());
            this.f.a(xVar, cVar);
        }
        if (serverInfo.getType() == ServerInfo.NotificationType.MOBILEID && serverInfo.getMobileId() != null) {
            if (serverInfo.getMobileId().getMaxRedirects() == 0) {
                serverInfo.getMobileId().setMaxRedirects(3);
            }
            xVar.a(serverInfo.getMobileId(), Boolean.FALSE);
            if (ru.mail.libverify.v.a.a().provideStartConfig().isDebugMode()) {
                FileLog.v("MobileId: for sessionId %s received url: %s", xVar.e(), serverInfo.getMobileId().getUrl());
            }
        }
        if (serverInfo.getType() == ServerInfo.NotificationType.DO_ATTEMPT && serverInfo.getDoAttempt() != null) {
            xVar.a(serverInfo.getDoAttempt());
            this.f.b(xVar, cVar);
            if (ru.mail.libverify.v.a.a().provideStartConfig().isDebugMode()) {
                FileLog.v("MobileId: for sessionId %s received code: %s", xVar.e(), serverInfo.getDoAttempt().getCode());
            }
        }
        if (serverInfo.getNotificationInfo() != null) {
            ServerInfo.NotificationInfo notificationInfo = serverInfo.getNotificationInfo();
            ArrayList arrayList = new ArrayList(this.g.e());
            if (a.f[notificationInfo.getAction().ordinal()] != 1) {
                throw new IllegalArgumentException("Action = " + notificationInfo.getAction());
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ServerNotificationMessage serverNotificationMessage = (ServerNotificationMessage) it.next();
                if (serverNotificationMessage.getMessage() != null && TextUtils.equals(serverNotificationMessage.getMessage().getSessionId(), xVar.e())) {
                    this.g.b(serverNotificationMessage.getId());
                    ((NotificationBarManager) this.z.get()).cancel(serverNotificationMessage.getId());
                    this.f.g(serverNotificationMessage);
                    FileLog.v("VerificationApi", "removed message %s", serverNotificationMessage.getId());
                }
            }
        }
        if (serverInfo.getRoute() != null) {
            ru.mail.libverify.d.b.Companion.getClass();
            Intrinsics.checkNotNullParameter(serverInfo, "source");
            p.b route = serverInfo.getRoute();
            int i2 = route == null ? -1 : b.c.a.a[route.ordinal()];
            if (i2 == 1) {
                ServerInfo.SmsInfo smsInfo = serverInfo.getSmsInfo();
                if (smsInfo != null) {
                    b.e.Companion.getClass();
                    Intrinsics.checkNotNullParameter(smsInfo, "source");
                    bVar = r0;
                    b.e eVar = new b.e(smsInfo.getFallbackTimeout());
                }
                bVar = null;
            } else if (i2 != 2) {
                if (i2 == 3 && (callInInfo = serverInfo.getCallInInfo()) != null) {
                    b.a.Companion.getClass();
                    Intrinsics.checkNotNullParameter(callInInfo, "source");
                    Integer fallbackTimeout = callInInfo.getFallbackTimeout();
                    Intrinsics.checkNotNullExpressionValue(fallbackTimeout, "source.fallbackTimeout");
                    int intValue = fallbackTimeout.intValue();
                    Integer totalFallbackTimeout = callInInfo.getTotalFallbackTimeout();
                    Integer num = totalFallbackTimeout;
                    if (totalFallbackTimeout == null) {
                        num = 0;
                    }
                    int intValue2 = num.intValue();
                    String phone = callInInfo.getPhone();
                    Intrinsics.checkNotNullExpressionValue(phone, "source.phone");
                    bVar = new b.a(intValue, intValue2, phone, callInInfo.isDisableDirectCall(), callInInfo.isIvr());
                }
                bVar = null;
            } else {
                ServerInfo.CallUiInfo callUiInfo = serverInfo.getCallUiInfo();
                if (callUiInfo != null) {
                    b.C0004b.Companion.getClass();
                    Intrinsics.checkNotNullParameter(callUiInfo, "source");
                    Integer fallbackTimeout2 = callUiInfo.getFallbackTimeout();
                    Integer num2 = fallbackTimeout2;
                    if (fallbackTimeout2 == null) {
                        num2 = 0;
                    }
                    bVar = new b.C0004b(num2.intValue(), callUiInfo.getFragmentStart(), callUiInfo.getDescription(), callUiInfo.getDescriptionOptional());
                }
                bVar = null;
            }
            if (bVar != null) {
                xVar.a(bVar);
            }
        }
    }

    private boolean a(@NonNull ServerNotificationMessage serverNotificationMessage, boolean z) {
        ArrayList arrayList;
        if (z && this.g.a(serverNotificationMessage.getId()) == null) {
            FileLog.e("VerificationApi", "notification with id %s doesn't exist", serverNotificationMessage.getId());
            return false;
        }
        FileLog.v("VerificationApi", "show popup %s", serverNotificationMessage);
        this.p.getBackgroundWorker().execute(() -> {
            ((NotificationBarManager) this.z.get()).show(new ru.mail.libverify.notifications.k(this.a.getContext(), serverNotificationMessage, false), ru.mail.verify.core.utils.Utils.stringToSHA256(serverNotificationMessage.getMessage().getSessionId()));
        });
        new ru.mail.libverify.f.f(this.a.getContext(), this, new d(serverNotificationMessage)).a(serverNotificationMessage.getId(), serverNotificationMessage.getLocalTimestamp());
        synchronized (this) {
            arrayList = new ArrayList(this.d);
        }
        FileLog.v("VerificationApi", "notify sms listeners count %d", Integer.valueOf(arrayList.size()));
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((VerificationApi.SmsCodeNotificationListener) it.next()).onNotification(serverNotificationMessage.getMessage().getText());
        }
        return true;
    }

    private void a(ru.mail.libverify.i.l lVar, @Nullable SignOutCallback signOutCallback) {
        try {
            lVar.executeAsync(((f) this.m).getBackgroundWorker(), ((f) this.m).getDispatcher(), future -> {
                if (signOutCallback != null) {
                    boolean z = false;
                    try {
                        z = ((ru.mail.libverify.j.m) future.get()).isOk();
                    } catch (Exception e2) {
                        FileLog.e("VerificationApi", "Failed to execute signOut request: %s", e2.getMessage());
                    }
                    signOutCallback.onCompleted(z);
                }
            });
        } catch (Throwable th) {
            DebugUtils.safeThrow("VerificationApi", th, "Failed to launch request", new Object[0]);
        }
    }

    @NonNull
    private String a(@NonNull String str, @NonNull ru.mail.libverify.d.d dVar, @Nullable String str2, @Nullable String str3, @Nullable Map<String, String> map, @Nullable String str4, @Nullable VerificationParameters verificationParameters) {
        ru.mail.libverify.i.q qVar;
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Provided arguments can't be null");
        }
        if (dVar.f() != VerifyRoute.VKCLogin && TextUtils.isEmpty(str2) && TextUtils.isEmpty(str3)) {
            throw new IllegalArgumentException("Either user id or phone must be non null");
        }
        String str5 = this.N.get();
        if (str5 != null) {
            this.t.createTimer("last_session", this.p.getDispatcher(), 500L, () -> {
                this.N.set(null);
            });
            this.N.set(str5);
            return str5;
        }
        if (verificationParameters == null) {
            qVar = null;
        } else {
            ru.mail.libverify.i.q.Companion.getClass();
            Intrinsics.checkNotNullParameter(verificationParameters, "source");
            qVar = new ru.mail.libverify.i.q(verificationParameters.getCallUIEnabled(), verificationParameters.getCallInEnabled(), verificationParameters.getExternalId(), verificationParameters.getStateChangeOnErrorEnabled(), verificationParameters.getCallEnabled(), verificationParameters.getMobileIdEnabled(), verificationParameters.getPushEnabled(), verificationParameters.getSmsEnabled());
        }
        if (this.E == null) {
            this.E = new ru.mail.libverify.k.m(this.m);
        }
        ru.mail.libverify.k.m mVar = this.E;
        if (this.F == null) {
            this.F = new ru.mail.libverify.k.h(this.m, this.f);
        }
        ru.mail.libverify.k.h hVar = this.F;
        if (this.G == null) {
            this.G = new ru.mail.libverify.t.a(this.m);
        }
        ru.mail.libverify.t.a aVar = this.G;
        CommonContext commonContext = this.m;
        if (this.M == null) {
            this.M = new ru.mail.libverify.m.r(this.a.getContext(), this.p, this.m);
        }
        x xVar = new x(mVar, hVar, aVar, commonContext, this.M, str, dVar, str2, str3, this.v, map, str4, qVar, this.t, this.f);
        new Handler(this.a.getContext().getMainLooper()).post(() -> {
            AppStateModel.register(this.K);
        });
        ConnectivityHelper.registerCallback(this.a.getContext(), xVar.e());
        this.p.post(MessageBusUtils.createOneArg(BusMessageType.VERIFY_API_START_VERIFICATION, xVar));
        this.t.createTimer("last_session", this.p.getDispatcher(), 500L, () -> {
            this.N.set(null);
        });
        this.N.set(xVar.e());
        return xVar.e();
    }

    private void a(@NonNull List<j.d> list, @NonNull ServerNotificationMessage serverNotificationMessage, String str) {
        if (list.size() == 0) {
            return;
        }
        this.f.a(serverNotificationMessage, list);
        Utils.safeExecute("VerificationApi", (ActionExecutor) this.A.get(), new ru.mail.libverify.i.j(this.a, list, serverNotificationMessage.getMessage().getSessionId(), serverNotificationMessage.getDeliveryMethod(), j.b.SMS_CODE, null, null, str, serverNotificationMessage.getLocalTimestamp()));
    }
}
