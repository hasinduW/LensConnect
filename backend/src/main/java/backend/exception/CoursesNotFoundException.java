package backend.exception;

public class CoursesNotFoundException extends RuntimeException {
    public CoursesNotFoundException (Long id){
        super("could not find id"+id);
    }
    public CoursesNotFoundException(String message){
        super(message);
    }
}
