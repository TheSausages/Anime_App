package anime.app.utils;

import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.verification.VerificationMode;

public interface AdditionalVerificationModes {
    static VerificationMode once() {
        return VerificationModeFactory.times(1);
    }

    static VerificationMode twice() {
        return VerificationModeFactory.times(2);
    }
}
