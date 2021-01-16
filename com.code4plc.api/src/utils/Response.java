package utils;

/**
 * Response object
 */
public class Response {
    String id;
    Boolean success;
    String message;
    String error;

    public void setId(String id) {
        this.id = id;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setError(String error) {
        this.error = error;
    }
}
