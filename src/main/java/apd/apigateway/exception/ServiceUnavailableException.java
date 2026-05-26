package apd.apigateway.exception;

import lombok.Getter;

@Getter
public class ServiceUnavailableException extends RuntimeException {
    private final String serviceName;
    private final String fallbackMessage;

    public ServiceUnavailableException(String message) {
        super(message);
        this.serviceName = extractServiceName(message);
        this.fallbackMessage = message;
    }

    public ServiceUnavailableException(String serviceName, String message) {
        super(message);
        this.serviceName = serviceName;
        this.fallbackMessage = message;
    }

    private String extractServiceName(String message) {
        if (message != null && message.contains("Service")) {
            return message.split("Service")[0].trim();
        }
        return "Unknown";
    }
}