/**
 * Created by Umar on 02-Mar-16.
 */
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {
    boolean looper = true;
    public Socket connect(String serverName,int port){
        Socket client=null;
        try {
             client = new Socket(serverName, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return client;

    }
    public boolean checkinput(String input){
        switch (input){
            case "--E":
                return true;
            case "--R":
                return true;
                default:
                    return false;
        }
    }
    public boolean usernameinput(String user){
//        Scanner sc = new Scanner(System.in);
        while(checkinput(user)){
            switch (user){
                case "--E":
                   return false;
//                case "--N":
//                    System.out.println("Enter Your UserName:");
//                    user=sc.nextLine();
//                    break;
                case "--R":
                    return false;
            }
        }
        return true;
    }
    public boolean noteinput(String Note,String user){
        Scanner sc = new Scanner(System.in);
        while(checkinput(Note)){
            switch (Note){
                case "--E":
                    return false;
//                case "--N":
//                   return usernameinput(user);
                case "--R":
                return true;
            }
        }
        return true;
    }
//    public void Retrieve(String username, Socket client){
//
//
//    }
    public static void main(String [] args) {
        Scanner sc = new Scanner(System.in);
        Client c= new Client();
        String serverName = "127.0.0.1";
        int port = 27000;
        try {
            System.out.println("Connecting to " + serverName +
                    " on port " + port);
            Socket client =c.connect(serverName,port);
            System.out.println("Just connected to "
                    + client.getRemoteSocketAddress());
            OutputStream outToServer = client.getOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(outToServer);
            System.out.println("Type --E to end connection\nType --R to retrive all notes (if stored)");
            System.out.println("Enter Your UserName:");
            String user = sc.nextLine();
            if(!c.usernameinput(user)){
                if (user.equals("--R")){
                    System.out.println("Error no command in username");
                }
                NoteClass cc= new NoteClass();
                cc.username="--quit";
                out.writeObject(cc);
                client.close();
                System.exit(0);
            }
            System.out.println("Enter Your Note:");
            String note = sc.nextLine();
            while(c.noteinput(note,user)){
                if(note.equals("--R")){
                    System.out.println("Your Notes stored on server are:");
                    NoteClass Notes = new NoteClass();
                    Notes.username = note;
                    Notes.notes = user;
                    out.writeObject(Notes);
                    InputStream inFromServer = client.getInputStream();
                    ObjectInputStream input=new ObjectInputStream(inFromServer);
                    try {

                        int k=1;
                        while (true){
                            NoteClass cc=(NoteClass) input.readObject();
                            if (cc.username.equals("--quit")){
                                break;
                            }
                            System.out.println(k);
                            cc.printmsg();
                            k++;

                        }
                    }catch (EOFException g){

                    }
                    catch (ClassNotFoundException e) {
//                        e.printStackTrace();
                    }
                }
                else {
                    NoteClass Notes = new NoteClass();
                    Notes.username = user;
                    Notes.notes = note;
                    out.writeObject(Notes);
                }
                System.out.println("\n\nEnter Your Note:");
                note = sc.nextLine();
            }
            NoteClass cc= new NoteClass();
            cc.username="--quit";
            out.writeObject(cc);
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
