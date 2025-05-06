package backend.exception;

//using 2 constructors find by id and normal data not found

public class PostNotFoundException extends RuntimeException{
    public PostNotFoundException (Long id) {
        super("could not find id " + id);
    }
    public PostNotFoundException(String message){
    super(message);
    }
}
