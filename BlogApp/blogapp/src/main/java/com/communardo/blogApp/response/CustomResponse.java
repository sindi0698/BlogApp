package com.communardo.blogApp.response;

public class CustomResponse<T> {
    private String message;
    private int statusCode;
    private T responseObject;

    private CustomResponse(CustomResponseBuilder<T> customResponseBuilder) {
        this.message = customResponseBuilder.message;
        this.statusCode = customResponseBuilder.statusCode;
        this.responseObject = customResponseBuilder.responseObject;

    }

    public static class CustomResponseBuilder<T> {
        private String message;
        private int statusCode;
        private T responseObject;

        public CustomResponseBuilder() {
        }

        public CustomResponseBuilder<T> setMessage(String message) {
            this.message = message;
            return this;
        }


        public CustomResponseBuilder<T> setStatusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public CustomResponseBuilder<T> setResponseObject(T responseObject) {
            this.responseObject = responseObject;
            return this;
        }

        public CustomResponse<T> build() {
            return new CustomResponse<>(this);
        }

    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public T getResponseObject() {
        return responseObject;
    }
}

