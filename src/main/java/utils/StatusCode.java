package utils;

public enum StatusCode {
    OK(200),
    CREATED(201),
    NOT_FOUND(404),
    UNAUTHORIZED(401);

    private final int code;

    StatusCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

