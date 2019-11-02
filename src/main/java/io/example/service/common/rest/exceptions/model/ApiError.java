package io.example.service.common.rest.exceptions.model;

public class ApiError {

    public enum Type {
        BUSINESS, TECHNICAL
    }

    private Type type;
    private String description;
    private String fieldName;
    private String originalValue;
    private String spanId;
    private Throwable throwable;

    public ApiError() {
    }

    public ApiError(Type type, String description) {
        this.type = type;
        this.description = description;
    }

    public ApiError(Type type, String description, Throwable throwable) {
        this.type = type;
        this.description = description;
        this.throwable = throwable;
    }

    public ApiError(Type type, String description, String fieldName, String originalValue) {
        this.type = type;
        this.description = description;
        this.fieldName = fieldName;
        this.originalValue = originalValue;
    }

    public ApiError(Type type, String description, String fieldName, String originalValue, Throwable throwable) {
        this.type = type;
        this.description = description;
        this.fieldName = fieldName;
        this.originalValue = originalValue;
        this.throwable = throwable;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getOriginalValue() {
        return originalValue;
    }

    public void setOriginalValue(String originalValue) {
        this.originalValue = originalValue;
    }

    public String getSpanId() {
        return spanId;
    }

    public void setSpanId(String spanId) {
        this.spanId = spanId;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    @Override
    public String toString() {
        return "ServiceError{" +
                "type=" + type +
                ", description='" + description + '\'' +
                ", fieldName='" + fieldName + '\'' +
                ", originalValue='" + originalValue + '\'' +
                ", spanId='" + spanId + '\'' +
                '}';
    }
}
