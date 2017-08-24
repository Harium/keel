package com.harium.keel.core.mask;

public class DynamicPixel {

    public static final int UNKNOWN = 0;
    public static final int VALID = 2;
    public static final int VALID_TOUCHED = 3;
    public static final int INVALID = 4;
    public static final int INVALID_TOUCHED = 5;

    public static boolean isTouched(int status) {
        return status == VALID_TOUCHED || status == INVALID_TOUCHED;
    }

    public static boolean isValid(int status) {
        return status == VALID || status == VALID_TOUCHED;
    }

    public static boolean isUnknown(int status) {
        return status == UNKNOWN;
    }

    public static int setTouched(int status) {
        if (status == VALID) {
            return VALID_TOUCHED;
        } else if (status == INVALID) {
            return INVALID_TOUCHED;
        } else if (status == UNKNOWN) {
            return INVALID_TOUCHED;
        }

        return status;
    }

    public static int setValid(int status) {
        if (isTouched(status)) {
            return VALID_TOUCHED;
        } else {
            return VALID;
        }
    }

    public static int setInvalid(int status) {
        if (isTouched(status)) {
            return INVALID_TOUCHED;
        } else {
            return INVALID;
        }
    }

}
