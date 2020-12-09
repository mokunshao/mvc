package guaMVC.models;

public class Todo {
    public Integer id;
    public String content;
    public Boolean completed;

    public Todo() {

    }

    @Override
    public String toString() {
        String s = String.format(
                "(id: %s, content: %s, completed: %s)",
                this.id,
                this.content,
                this.completed
        );

        return s;
    }
}
