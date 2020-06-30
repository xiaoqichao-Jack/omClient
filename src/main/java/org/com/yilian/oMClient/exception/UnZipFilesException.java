package org.com.yilian.oMClient.exception;

public class UnZipFilesException extends RuntimeException{
    public UnZipFilesException(String message) {
        super(message);
    }

    public UnZipFilesException(String message, Throwable cause) {
        super(message, cause);
    }
}
