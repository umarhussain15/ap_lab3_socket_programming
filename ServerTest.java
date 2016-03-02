import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by Umar on 02-Mar-16.
 */
public class ServerTest extends TestCase {

    @Test
    public void testStoreMsg() throws Exception {
        Server s= new Server(27000);
        NoteClass n= new NoteClass();

        assertFalse(s.storeMsg(n));
        n.username="check";
        n.notes="1234567890-qwertyuiopasdfghjkl";
        assertTrue(s.storeMsg(n));
        n.username="";
        n.notes="1234567890-qwertyuiopasdfghjkl";
        assertFalse(s.storeMsg(n));
    }

}