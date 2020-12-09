package guaMVC.models;

public class Message {
    public String author;
    public String message;

    public Message() {

    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        String s = String.format(
                "(author: %s, message: %s)",
                this.author,
                this.message
        );

        return s;
    }
}
