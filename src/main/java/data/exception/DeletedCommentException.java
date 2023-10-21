package data.exception;

public class DeletedCommentException extends RuntimeException {
    public DeletedCommentException(String message) {
        super(message);
    }
}