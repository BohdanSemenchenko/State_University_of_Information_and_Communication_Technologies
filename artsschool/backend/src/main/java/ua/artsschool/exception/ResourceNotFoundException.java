package ua.artsschool.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String msg) { super(msg); }
    public ResourceNotFoundException(String type, Long id) { super(type + " з ID " + id + " не знайдено"); }
}
