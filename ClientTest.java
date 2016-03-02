import junit.framework.TestCase;

/**
 * Created by Umar on 02-Mar-16.
 */
public class ClientTest extends TestCase {

    Client c= new Client();

    public void testCheckinput() throws Exception {
        assertTrue(c.checkinput("--E"));
        assertFalse(c.checkinput("anyotherinput"));
    }

    public void testUsernameinput() throws Exception {
        assertFalse(c.usernameinput("--E"));
        assertFalse(c.usernameinput("--R"));
        assertTrue(c.usernameinput("anyusername"));
    }

    public void testNoteinput() throws Exception {
        assertTrue(c.noteinput("hello abc","username"));

    }
}