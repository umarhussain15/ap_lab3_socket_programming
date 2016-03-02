/**
 * Created by Umar on 02-Mar-16.
 */
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class Server extends Thread {
    private ArrayList<HashMap<String,NoteClass>> Allnotes;
    private ServerSocket serverSocket;


    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        Allnotes= new ArrayList<>();
//        serverSocket.setSoTimeout(10000);
    }
    public boolean storeMsg(NoteClass Notes){

        if (Notes==null)
            return false;
        if (Notes.username==null ){
            return false;
        }
        if (Notes.username.isEmpty()){
            return  false;
        }
        HashMap<String,NoteClass> map= new HashMap<>();
        map.put(Notes.username,Notes);
        Notes.printuser();
        Notes.printmsg();
        return Allnotes.add(map);
    }
    public boolean checkcommand(NoteClass Notes){
        if (Notes==null)
            return false;
        if (Notes.username==null ){
            return false;
        }
        if (Notes.username.isEmpty()){
            return  false;
        }
        if (Notes.username.equals("--quit")){
            return true;
        }
        else return false;
    }
    public boolean isSearch(NoteClass Notes){
        if (Notes==null)
            return false;
        if (Notes.username==null ){
            return false;
        }
        if (Notes.username.isEmpty()){
            return  false;
        }
        if(Notes.username.equals("--R")){
            return true;
        }
        return false;
    }
//    public boolean searchUser(String User){
//        for (int i=0;i<Allnotes.size();i++){
//
//        }
//    }
    public void run() {
        while (true) {
            try {
                System.out.println("Waiting for client on port " +
                        serverSocket.getLocalPort() + "...");
                Socket server = serverSocket.accept();
                System.out.println("Just connected to "
                        + server.getRemoteSocketAddress());
                InputStream in =server.getInputStream();
                ObjectInputStream input=new ObjectInputStream(in);
                try {
                    while(server.isConnected()) {
                        System.out.println("wating for data");
                        NoteClass Notes = (NoteClass) input.readObject();

                        if (checkcommand(Notes)) {
//                            if (Notes.username.equals("--quit")){
//                                break;
//                            }
                            System.out.println("Client Disconnected");
                            break;
                        } else if (isSearch(Notes)) {
                            OutputStream outToClient = server.getOutputStream();
                            ObjectOutputStream out = new ObjectOutputStream(outToClient);
                            String user=Notes.notes;
                            for(int j=0;j<Allnotes.size();j++){
                                NoteClass temp= Allnotes.get(j).get(user);
                                if(temp!=null){
                                    out.writeObject(temp);
                                }
                            }
                            NoteClass cc= new NoteClass();
                            cc.username="--quit";
                            out.writeObject(cc);
//                            out.close();
                        }
                        else if (storeMsg(Notes)){
                            System.out.println("Message Stored");
                        }
                    }

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            } catch (SocketTimeoutException s) {
                System.out.println("Socket timed out!");
                break;
            } catch (IOException e) {
                System.out.println("Client Disconnected-");

//                e.printStackTrace();
                break;
            }
        }
    }

    public static void main(String[] args) {
        int port = 27000;
        try {
            Thread t = new Server(port);
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
