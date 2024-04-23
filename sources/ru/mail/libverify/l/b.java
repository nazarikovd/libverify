package ru.mail.libverify.l;

import dagger.Lazy;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.mail.libverify.api.VerificationApi;
import ru.mail.libverify.api.x;
import ru.mail.libverify.gcm.ServerNotificationMessage;
import ru.mail.libverify.i.j;
import ru.mail.verify.core.platform.TimeProvider;
import ru.mail.verify.core.utils.ClientException;
import ru.mail.verify.core.utils.ServerException;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/l/b.class */
public final class b {
    @NotNull
    private final TimeProvider a;
    @NotNull
    private final Lazy<d> b;

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/l/b$a.class */
    public enum a {
        NO_CALL_PERMISSION,
        NO_READY_SIM,
        NO_MATCHED_SIM
    }

    /* renamed from: ru.mail.libverify.l.b$b  reason: collision with other inner class name */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/l/b$b.class */
    public enum EnumC0014b {
        ALL,
        SMS,
        SMS_DIALOG
    }

    public final void i() {
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        ((d) obj).a(ru.mail.libverify.l.a.PUSHTOKEN_RECEIVED_FIRST, new e().a());
    }

    public final void h() {
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        ((d) obj).a(ru.mail.libverify.l.a.PUSHTOKEN_FAILED_TO_OBTAIN, new e().a());
    }

    public final void j() {
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        ((d) obj).a(ru.mail.libverify.l.a.PUSHTOKEN_SERVICE_ERROR, new e().a());
    }

    public final void b() {
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        ((d) obj).a(ru.mail.libverify.l.a.INITIAL_VERIFICATION_RECEIVED, new e().a());
    }

    public final void a(@NotNull ru.mail.libverify.j.c<?> cVar) {
        Intrinsics.checkNotNullParameter(cVar, "response");
        if (cVar.getOwner() == 0) {
            throw new IllegalArgumentException("Response must have non null owner".toString());
        }
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        ru.mail.libverify.l.a aVar = ru.mail.libverify.l.a.API_REQUEST_FAILURE;
        e eVar = new e();
        eVar.a("Method", ((ru.mail.libverify.i.c) cVar.getOwner()).getApiNameForStatistics());
        eVar.a("StatusCode", new StringBuilder().append(cVar.d()).append('_').append(cVar.b()).toString());
        ((d) obj).a(aVar, eVar.a());
    }

    public final void k() {
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        ((d) obj).a(ru.mail.libverify.l.a.SERVER_API_HOST_OVERRIDDEN, new e().a());
    }

    public final void e() {
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        ((d) obj).a(ru.mail.libverify.l.a.INSTANCE_RESET, new e().a());
    }

    public final void f() {
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        ((d) obj).a(ru.mail.libverify.l.a.INSTANCE_SOFT_SIGNOUT, new e().a());
    }

    public final void g() {
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        ((d) obj).a(ru.mail.libverify.l.a.PHONECHECKER_NEW_CHECK_STARTED, new e().a());
    }

    public final void c() {
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        ((d) obj).a(ru.mail.libverify.l.a.INSTANCE_FETCHER_STARTED, new e().a());
    }

    public final void d() {
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        ((d) obj).a(ru.mail.libverify.l.a.INSTANCE_FETCHER_STOPPED, new e().a());
    }

    public final void m() {
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        ((d) obj).a(ru.mail.libverify.l.a.SMS_RETRIEVER_INITIALIZED, new e().a());
    }

    public final void n() {
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        ((d) obj).a(ru.mail.libverify.l.a.SMS_RETRIEVER_TIMEOUT, new e().a());
    }

    public final void l() {
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        ((d) obj).a(ru.mail.libverify.l.a.SMS_RETRIEVER_FAILURE, new e().a());
    }

    public b(@NotNull TimeProvider timeProvider, @NotNull Lazy<d> lazy) {
        Intrinsics.checkNotNullParameter(timeProvider, "timeProvider");
        Intrinsics.checkNotNullParameter(lazy, "eventSender");
        this.a = timeProvider;
        this.b = lazy;
    }

    public final void j(@NotNull x xVar) {
        Intrinsics.checkNotNullParameter(xVar, "session");
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        ru.mail.libverify.l.a aVar = ru.mail.libverify.l.a.VERIFICATION_STARTED;
        e eVar = new e();
        eVar.a("ServiceName", xVar.i());
        ((d) obj).a(aVar, eVar.a());
    }

    public final void k(@NotNull x xVar) {
        Intrinsics.checkNotNullParameter(xVar, "session");
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        ru.mail.libverify.l.a aVar = ru.mail.libverify.l.a.VERIFICATION_SWITCHED_BACKGROUND;
        e eVar = new e();
        eVar.a("ServiceName", xVar.i());
        ((d) obj).a(aVar, eVar.a());
    }

    public final void f(@NotNull ServerNotificationMessage serverNotificationMessage) {
        Intrinsics.checkNotNullParameter(serverNotificationMessage, "message");
        ServerNotificationMessage.Message message = serverNotificationMessage.getMessage();
        if (message == null) {
            return;
        }
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        d dVar = (d) obj;
        ru.mail.libverify.l.a aVar = ru.mail.libverify.l.a.PUSH_RECEIVED;
        e eVar = new e();
        eVar.a("PushSender", serverNotificationMessage.getSender());
        eVar.a("PushType", message.getType().toString());
        eVar.a("PushDelivery", serverNotificationMessage.getDeliveryMethod().toString());
        eVar.a("PushWithConfirm", Boolean.valueOf(message.hasConfirmation()));
        Set<ServerNotificationMessage.Message.NotificationFlags> flags = message.getFlags();
        if (flags != null) {
            Intrinsics.checkNotNullExpressionValue(flags, "flags");
            eVar.a("PushFlags", flags.toString());
        }
        dVar.a(aVar, eVar.a());
    }

    public final void g(@NotNull ServerNotificationMessage serverNotificationMessage) {
        Intrinsics.checkNotNullParameter(serverNotificationMessage, "message");
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        ru.mail.libverify.l.a aVar = ru.mail.libverify.l.a.PUSH_SERVER_COMPLETED;
        e eVar = new e();
        eVar.a("PushSender", serverNotificationMessage.getSender());
        eVar.a("PushDelivery", serverNotificationMessage.getDeliveryMethod().toString());
        ((d) obj).a(aVar, eVar.a());
    }

    public final void e(@NotNull ServerNotificationMessage serverNotificationMessage) {
        Intrinsics.checkNotNullParameter(serverNotificationMessage, "message");
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        ru.mail.libverify.l.a aVar = ru.mail.libverify.l.a.POPUP_SETTINGS_OPENED;
        e eVar = new e();
        eVar.a("PushSender", serverNotificationMessage.getSender());
        ((d) obj).a(aVar, eVar.a());
    }

    public final void h(@NotNull ServerNotificationMessage serverNotificationMessage) {
        Intrinsics.checkNotNullParameter(serverNotificationMessage, "message");
        ServerNotificationMessage.Message message = serverNotificationMessage.getMessage();
        if (message == null) {
            return;
        }
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        ru.mail.libverify.l.a aVar = ru.mail.libverify.l.a.SETTINGS_REPORT_REUSE_CLICKED;
        e eVar = new e();
        eVar.a("PushSender", serverNotificationMessage.getSender());
        eVar.a("PushFlags", message.getFlags().toString());
        ((d) obj).a(aVar, eVar.a());
    }

    public final void d(@Nullable String str) {
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        d dVar = (d) obj;
        ru.mail.libverify.l.a aVar = ru.mail.libverify.l.a.NOTIFICATION_HISTORY_REQUESTED;
        e eVar = new e();
        if (str == null) {
            str = "List";
        }
        eVar.a("PushSender", str);
        dVar.a(aVar, eVar.a());
    }

    public final void c(@Nullable String str) {
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        d dVar = (d) obj;
        ru.mail.libverify.l.a aVar = ru.mail.libverify.l.a.NOTIFICATION_HISTORY_OPENED;
        e eVar = new e();
        if (str == null) {
            str = "List";
        }
        eVar.a("PushSender", str);
        dVar.a(aVar, eVar.a());
    }

    public final void b(@Nullable String str) {
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        d dVar = (d) obj;
        ru.mail.libverify.l.a aVar = ru.mail.libverify.l.a.NOTIFICATION_HISTORY_ADDED;
        e eVar = new e();
        if (str == null) {
            str = "List";
        }
        eVar.a("PushSender", str);
        dVar.a(aVar, eVar.a());
    }

    public final void i(@NotNull ServerNotificationMessage serverNotificationMessage) {
        Intrinsics.checkNotNullParameter(serverNotificationMessage, "message");
        ServerNotificationMessage.Message message = serverNotificationMessage.getMessage();
        if (message == null) {
            return;
        }
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        ru.mail.libverify.l.a aVar = ru.mail.libverify.l.a.SETTINGS_TEMPORARY_BLOCK_CLICKED;
        e eVar = new e();
        eVar.a("PushSender", serverNotificationMessage.getSender());
        eVar.a("PushFlags", message.getFlags().toString());
        ((d) obj).a(aVar, eVar.a());
    }

    public final void a(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "result");
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        ru.mail.libverify.l.a aVar = ru.mail.libverify.l.a.CHECK_APPLICATION_COMPLETED;
        e eVar = new e();
        eVar.a("Result", str);
        ((d) obj).a(aVar, eVar.a());
    }

    public final void e(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "sessionId");
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        ru.mail.libverify.l.a aVar = ru.mail.libverify.l.a.VERIFICATION_COMPLETED;
        e eVar = new e();
        eVar.a("SessionId", str);
        ((d) obj).a(aVar, eVar.a());
    }

    public final void f(@NotNull x xVar) {
        Intrinsics.checkNotNullParameter(xVar, "session");
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        ru.mail.libverify.l.a aVar = ru.mail.libverify.l.a.VERIFICATION_COMPLETED_BACKGROUND;
        e eVar = new e();
        eVar.a("ServiceName", xVar.i());
        eVar.a("VerificationSource", xVar.k().getSource().toString());
        ((d) obj).a(aVar, eVar.a());
    }

    public final void g(@NotNull x xVar) {
        Intrinsics.checkNotNullParameter(xVar, "session");
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        ru.mail.libverify.l.a aVar = ru.mail.libverify.l.a.VERIFICATION_IVR_REQUESTED;
        e eVar = new e();
        eVar.a("ServiceName", xVar.i());
        ((d) obj).a(aVar, eVar.a());
    }

    public final void h(@NotNull x xVar) {
        Intrinsics.checkNotNullParameter(xVar, "session");
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        ru.mail.libverify.l.a aVar = ru.mail.libverify.l.a.VERIFICATION_NEWSMSCODE_REQUESTED;
        e eVar = new e();
        eVar.a("ServiceName", xVar.i());
        ((d) obj).a(aVar, eVar.a());
    }

    public final void i(@NotNull x xVar) {
        Intrinsics.checkNotNullParameter(xVar, "session");
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        ru.mail.libverify.l.a aVar = ru.mail.libverify.l.a.VERIFICATION_PHONE_VALIDATED;
        e eVar = new e();
        eVar.a("ServiceName", xVar.i());
        ((d) obj).a(aVar, eVar.a());
    }

    public final void b(@NotNull ServerNotificationMessage serverNotificationMessage) {
        Intrinsics.checkNotNullParameter(serverNotificationMessage, "message");
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        ru.mail.libverify.l.a aVar = ru.mail.libverify.l.a.POPUP_DISMISSED;
        e eVar = new e();
        eVar.a("PushSender", serverNotificationMessage.getSender());
        ((d) obj).a(aVar, eVar.a());
    }

    public final void d(@NotNull ServerNotificationMessage serverNotificationMessage) {
        Intrinsics.checkNotNullParameter(serverNotificationMessage, "message");
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        ru.mail.libverify.l.a aVar = ru.mail.libverify.l.a.POPUP_FULLSCREEN_OPENED;
        e eVar = new e();
        eVar.a("PushSender", serverNotificationMessage.getSender());
        ((d) obj).a(aVar, eVar.a());
    }

    public final void c(@NotNull ServerNotificationMessage serverNotificationMessage) {
        Intrinsics.checkNotNullParameter(serverNotificationMessage, "message");
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        ru.mail.libverify.l.a aVar = ru.mail.libverify.l.a.POPUP_EQUAL_SMS_RECEIVED;
        e eVar = new e();
        eVar.a("PushSender", serverNotificationMessage.getSender());
        eVar.a("SmsTime", c.a(this.a.getLocalTime() - serverNotificationMessage.getLocalTimestamp()));
        ((d) obj).a(aVar, eVar.a());
    }

    public final void a(@NotNull VerificationApi.AccountCheckResult accountCheckResult, boolean z) {
        Intrinsics.checkNotNullParameter(accountCheckResult, "result");
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        ru.mail.libverify.l.a aVar = ru.mail.libverify.l.a.CHECK_ACCOUNT_COMPLETED;
        e eVar = new e();
        eVar.a("Result", accountCheckResult.toString());
        eVar.a("SmsFound", Boolean.valueOf(z));
        ((d) obj).a(aVar, eVar.a());
    }

    public final void e(@NotNull x xVar) {
        Intrinsics.checkNotNullParameter(xVar, "session");
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        ru.mail.libverify.l.a aVar = ru.mail.libverify.l.a.VERIFICATION_COMPLETED;
        e eVar = new e();
        eVar.a("ServiceName", xVar.i());
        eVar.a("VerificationSource", xVar.k().getSource().toString());
        eVar.a("VerificationResult", xVar.k().getReason().toString());
        eVar.a("VerificationTime", c.a(this.a.getLocalTime() - xVar.l()));
        ((d) obj).a(aVar, eVar.a());
    }

    public final void a() {
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        ((d) obj).a(ru.mail.libverify.l.a.CHECK_ACCOUNT_STARTED, new e().a());
    }

    public final void d(@NotNull x xVar) {
        Intrinsics.checkNotNullParameter(xVar, "session");
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        ru.mail.libverify.l.a aVar = ru.mail.libverify.l.a.SESSION_MOBILEID_REDIRECT_LIMIT_REACHED;
        e eVar = new e();
        eVar.a("ServiceName", xVar.i());
        eVar.a("SessionId", xVar.e());
        ((d) obj).a(aVar, eVar.a());
    }

    public final void b(@NotNull x xVar) {
        Intrinsics.checkNotNullParameter(xVar, "session");
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        ru.mail.libverify.l.a aVar = ru.mail.libverify.l.a.SESSION_CALL_IN_PROCESS;
        e eVar = new e();
        eVar.a("ServiceName", xVar.i());
        eVar.a("SessionId", xVar.e());
        ((d) obj).a(aVar, eVar.a());
    }

    public final void a(@Nullable Thread thread, @NotNull Throwable th) {
        Intrinsics.checkNotNullParameter(th, "error");
        ((d) this.b.get()).a(thread, th);
    }

    public final void b(@NotNull x xVar, @NotNull j.c cVar) {
        Intrinsics.checkNotNullParameter(xVar, "session");
        Intrinsics.checkNotNullParameter(cVar, "deliveryMethod");
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        ru.mail.libverify.l.a aVar = ru.mail.libverify.l.a.SESSION_MOBILEID_CODE_RECEIVED;
        e eVar = new e();
        eVar.a("PushDelivery", cVar.toString());
        eVar.a("PushTime", c.a(this.a.getLocalTime() - xVar.l()));
        ((d) obj).a(aVar, eVar.a());
    }

    public final void c(@NotNull x xVar) {
        Intrinsics.checkNotNullParameter(xVar, "session");
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        ru.mail.libverify.l.a aVar = ru.mail.libverify.l.a.SESSION_MOBILEID_REDIRECT_ERROR;
        e eVar = new e();
        eVar.a("ServiceName", xVar.i());
        eVar.a("SessionId", xVar.e());
        ((d) obj).a(aVar, eVar.a());
    }

    public final void a(@NotNull EnumC0014b enumC0014b, @Nullable String str) {
        Intrinsics.checkNotNullParameter(enumC0014b, "type");
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        ru.mail.libverify.l.a aVar = ru.mail.libverify.l.a.NOTIFICATION_HISTORY_ERASED;
        e eVar = new e();
        eVar.a("PushSender", str);
        eVar.a("Type", enumC0014b.toString());
        ((d) obj).a(aVar, eVar.a());
    }

    public final void a(boolean z) {
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        ru.mail.libverify.l.a aVar = ru.mail.libverify.l.a.NOTIFICATION_HISTORY_SHORTCUT_CREATED;
        e eVar = new e();
        eVar.a("Result", Boolean.valueOf(z));
        ((d) obj).a(aVar, eVar.a());
    }

    public final void b(@Nullable Thread thread, @NotNull Throwable th) {
        Intrinsics.checkNotNullParameter(th, "error");
        ((d) this.b.get()).a(thread, th);
    }

    public final void a(@NotNull ServerNotificationMessage serverNotificationMessage) {
        Intrinsics.checkNotNullParameter(serverNotificationMessage, "message");
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        ru.mail.libverify.l.a aVar = ru.mail.libverify.l.a.POPUP_CONFIRM_CLICKED;
        e eVar = new e();
        eVar.a("PushSender", serverNotificationMessage.getSender());
        ((d) obj).a(aVar, eVar.a());
    }

    public final void a(@NotNull ServerNotificationMessage serverNotificationMessage, @NotNull List<? extends j.d> list) {
        Intrinsics.checkNotNullParameter(serverNotificationMessage, "message");
        Intrinsics.checkNotNullParameter(list, "statusData");
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        ru.mail.libverify.l.a aVar = ru.mail.libverify.l.a.PUSH_COMPLETED;
        e eVar = new e();
        eVar.a("PushSender", serverNotificationMessage.getSender());
        eVar.a("PushCompletion", list.toString());
        eVar.a("PushDelivery", serverNotificationMessage.getDeliveryMethod().toString());
        ((d) obj).a(aVar, eVar.a());
    }

    public final void a(@NotNull ServerNotificationMessage serverNotificationMessage, @NotNull ServerNotificationMessage serverNotificationMessage2) {
        Intrinsics.checkNotNullParameter(serverNotificationMessage, "first");
        Intrinsics.checkNotNullParameter(serverNotificationMessage2, "second");
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        ru.mail.libverify.l.a aVar = ru.mail.libverify.l.a.PUSH_DUPLICATION;
        e eVar = new e();
        eVar.a("PushDelivery", new StringBuilder().append(serverNotificationMessage.getDeliveryMethod()).append('_').append(serverNotificationMessage2.getDeliveryMethod()).toString());
        eVar.a("PushTime", c.a(serverNotificationMessage2.getLocalTimestamp() - serverNotificationMessage.getLocalTimestamp()));
        ((d) obj).a(aVar, eVar.a());
    }

    public final void a(long j) {
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        ru.mail.libverify.l.a aVar = ru.mail.libverify.l.a.PUSH_STATUS_SUBMITTED;
        e eVar = new e();
        eVar.a("SubmitTime", c.a(this.a.getLocalTime() - j));
        ((d) obj).a(aVar, eVar.a());
    }

    public final void a(@NotNull ru.mail.libverify.i.c<?> cVar, @NotNull ClientException clientException) {
        Intrinsics.checkNotNullParameter(cVar, "request");
        Intrinsics.checkNotNullParameter(clientException, "error");
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        ru.mail.libverify.l.a aVar = ru.mail.libverify.l.a.SERVER_FAILURE;
        e eVar = new e();
        eVar.a("Code", clientException.toString());
        eVar.a("Method", cVar.getApiNameForStatistics());
        ((d) obj).a(aVar, eVar.a());
    }

    public final void a(@NotNull ru.mail.libverify.i.c<?> cVar, @NotNull ServerException serverException) {
        Intrinsics.checkNotNullParameter(cVar, "request");
        Intrinsics.checkNotNullParameter(serverException, "error");
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        ru.mail.libverify.l.a aVar = ru.mail.libverify.l.a.SERVER_FAILURE;
        e eVar = new e();
        eVar.a("Code", Integer.valueOf(serverException.getStatusCode()));
        eVar.a("Method", cVar.getApiNameForStatistics());
        ((d) obj).a(aVar, eVar.a());
    }

    public final void a(@NotNull x xVar) {
        Intrinsics.checkNotNullParameter(xVar, "session");
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        ru.mail.libverify.l.a aVar = ru.mail.libverify.l.a.SESSION_CALL_IN_CLICKED;
        e eVar = new e();
        eVar.a("ServiceName", xVar.i());
        eVar.a("SessionId", xVar.e());
        ((d) obj).a(aVar, eVar.a());
    }

    public final void a(@NotNull x xVar, @NotNull j.c cVar) {
        Intrinsics.checkNotNullParameter(xVar, "session");
        Intrinsics.checkNotNullParameter(cVar, "method");
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        ru.mail.libverify.l.a aVar = ru.mail.libverify.l.a.SESSION_CALL_INFO_RECEIVED;
        e eVar = new e();
        eVar.a("PushDelivery", cVar.toString());
        eVar.a("PushTime", c.a(this.a.getLocalTime() - xVar.l()));
        ((d) obj).a(aVar, eVar.a());
    }

    public final void a(@NotNull a aVar) {
        Intrinsics.checkNotNullParameter(aVar, "reason");
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        ru.mail.libverify.l.a aVar2 = ru.mail.libverify.l.a.SESSION_CALL_REJECTED;
        e eVar = new e();
        eVar.a("CallRejectReason", aVar.toString());
        ((d) obj).a(aVar2, eVar.a());
    }

    public final void a(@NotNull x xVar, @NotNull String str, int i) {
        Intrinsics.checkNotNullParameter(xVar, "session");
        Intrinsics.checkNotNullParameter(str, "newLocation");
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        ru.mail.libverify.l.a aVar = ru.mail.libverify.l.a.SESSION_MOBILEID_REDIRECT;
        e eVar = new e();
        eVar.a("ServiceName", xVar.i());
        eVar.a("SessionId", xVar.e());
        eVar.a("RedirectLocation", str);
        eVar.a("maxRedirectsCount", Integer.valueOf(i));
        ((d) obj).a(aVar, eVar.a());
    }

    public final void a(@NotNull String str, @NotNull x xVar) {
        Intrinsics.checkNotNullParameter(xVar, "session");
        Intrinsics.checkNotNullParameter(str, "url");
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        ru.mail.libverify.l.a aVar = ru.mail.libverify.l.a.SESSION_MOBILEID_URL_RECEIVED;
        e eVar = new e();
        eVar.a("ServiceName", xVar.i());
        eVar.a("Location", str);
        ((d) obj).a(aVar, eVar.a());
    }

    public final void a(@NotNull ru.mail.libverify.i.c<?> cVar, @NotNull IOException iOException) {
        Intrinsics.checkNotNullParameter(cVar, "request");
        Intrinsics.checkNotNullParameter(iOException, "error");
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        ru.mail.libverify.l.a aVar = ru.mail.libverify.l.a.SERVER_SWITCHED_TO_NEXT_API_HOST;
        e eVar = new e();
        eVar.a("Code", iOException.toString());
        eVar.a("Method", cVar.getApiNameForStatistics());
        ((d) obj).a(aVar, eVar.a());
    }

    public final void a(@NotNull x xVar, @NotNull VerificationApi.CancelReason cancelReason) {
        Intrinsics.checkNotNullParameter(xVar, "session");
        Intrinsics.checkNotNullParameter(cancelReason, "reason");
        Object obj = this.b.get();
        Intrinsics.checkNotNullExpressionValue(obj, "eventSender.get()");
        ru.mail.libverify.l.a aVar = ru.mail.libverify.l.a.VERIFICATION_CANCELLED;
        e eVar = new e();
        eVar.a("ServiceName", xVar.i());
        eVar.a("VerificationCancelReason", cancelReason.toString());
        eVar.a("Success", Boolean.valueOf(xVar.k().completedSuccessfully()));
        ((d) obj).a(aVar, eVar.a());
    }
}
