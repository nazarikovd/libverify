package ru.mail.libverify.a;

import io.michaelrocks.libphonenumber.android.NumberParseException;
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
import io.michaelrocks.libphonenumber.android.Phonenumber;
import javax.inject.Inject;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.mail.verify.core.accounts.SimCardData;
import ru.mail.verify.core.accounts.SimCardItem;
import ru.mail.verify.core.utils.FileLog;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/a/b.class */
public final class b {
    @NotNull
    private final PhoneNumberUtil a;

    @Inject
    public b(@NotNull PhoneNumberUtil phoneNumberUtil) {
        Intrinsics.checkNotNullParameter(phoneNumberUtil, "phoneNumberUtil");
        this.a = phoneNumberUtil;
    }

    @Nullable
    public final a a(@NotNull SimCardData simCardData) {
        Intrinsics.checkNotNullParameter(simCardData, "data");
        try {
            SimCardItem activeSim = simCardData.getActiveSim();
            if (activeSim != null) {
                String simPhoneNumber = activeSim.getSimPhoneNumber();
                if (simPhoneNumber == null || StringsKt.isBlank(simPhoneNumber)) {
                    return null;
                }
                Phonenumber.PhoneNumber parseAndKeepRawInput = this.a.parseAndKeepRawInput(activeSim.getSimPhoneNumber(), activeSim.getSimCountryIso());
                if (this.a.isValidNumber(parseAndKeepRawInput)) {
                    String rawInput = parseAndKeepRawInput.getRawInput();
                    Intrinsics.checkNotNullExpressionValue(rawInput, "simCardNumber.rawInput");
                    return new a(rawInput);
                }
                return null;
            }
            return null;
        } catch (Exception e) {
            FileLog.e("SimCardDataUtils", "error during libphonenumber usage", e);
            return null;
        } catch (NumberParseException e2) {
            FileLog.e("SimCardDataUtils", "error during phone validation process", (Throwable) e2);
            return null;
        }
    }
}
