/**
 * Created by Umar on 02-Mar-16.
 */
import java.io.Serializable;
import java.util.regex.*;
public class NoteClass implements Serializable {
   public String username, notes;

    public void printmsg(){
    System.out.println(this.notes);
    }
    public void printuser(){
        System.out.println(this.username+":");
    }
}
