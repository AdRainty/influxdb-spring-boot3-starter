package io.adrainty.boot.influxdb.exception;

import java.io.Serial;

/**
 * <p>BindingException</p>
 *
 * @author AdRainty
 * @version V1.0.0
 * @description BindingException
 * @since 2025/7/31 14:47:18
 */

@SuppressWarnings("unused")
public class BindingException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1186005320140524840L;

    public BindingException(String message) {
        super(message);
    }

    public BindingException(String message, Throwable cause) {
        super(message, cause);
    }

}
