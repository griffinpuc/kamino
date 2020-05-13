package main.java.kamino.client;

import main.java.kamino.library.Note;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static KaminoClient kaminoClient;

    /* Main - Start here */
    /* ----------------------------------------------------- */
    public static void main(String[] args) throws IOException {

        kaminoClient = new KaminoClient();

        mainSelection();

    }

    /* Default menu selection */
    /* ----------------------------------------------------- */
    public static void mainSelection(){

        Scanner scan = new Scanner(System.in);

        System.out.println("KAMINO NOTES");
        System.out.println("=============");
        System.out.println("1: View saved notes");
        System.out.println("2: Create new note");

        String selection = scan.nextLine();

        switch(Integer.parseInt(selection)){
            case 1:
                noteSelection();
                break;
            case 2:
                newNote();
                break;
        }
    }

    /* Default note selection */
    /* ----------------------------------------------------- */
    public static void noteSelection(){
        Scanner scan = new Scanner(System.in);
        ArrayList<Note> notes = kaminoClient.getIndex().notes;

        while(true){

            System.out.println("SAVED NOTES:");
            System.out.println("Enter '..' as a selection to go back a menu)");
            System.out.println("=============");
            for(Note note : notes){
                System.out.println(note.noteId + ": " + note.noteTitle);
            }

            boolean flag = false;
            String selection = scan.nextLine();

            if(selection.equals("..")){
                mainSelection();
            }

            for(Note note : notes){
                if(note.noteId.equals(selection)){
                    flag = true;
                    System.out.println("");
                    System.out.println("Note Contents:");
                    System.out.println("--------------------------------------------------------------------");
                    System.out.println(note.noteContents);
                    System.out.println("--------------------------------------------------------------------");
                    System.out.println("");
                }
            }

            if(!flag){ System.out.println("Invalid note, try again."); }

        }
    }

    /* Create a new note selection */
    /* ----------------------------------------------------- */
    public static void newNote(){
        Scanner scan = new Scanner(System.in);

        System.out.println("CREATE NEW NOTE:");
        System.out.println("=============");

        //kaminoClient.getIndex();

        System.out.print("Note name: ");
        String noteTitle = scan.nextLine();

        System.out.println("-------------------------------------------");
        System.out.println("Editing note \"" + noteTitle + "\":");
        System.out.println("");
        String noteContent = scan.nextLine();

        kaminoClient.createNote(noteTitle, noteContent);
        mainSelection();
    }

    /* Default sub note selection */
    /* ----------------------------------------------------- */
    /*
    public static void subSelection(Note note){
        Scanner scan = new Scanner(System.in);
        System.out.println("MANAGE NOTE:");
        System.out.println("Enter '..' as a selection to go back a menu)");
        System.out.println("=============");
        System.out.println("1: View note");
        System.out.println("2: Edit note");
        System.out.println("3: Remove note");

        String selection = scan.nextLine();

        switch(Integer.parseInt(selection)){
            case 1:

                break;
            case 2:
                newNote();
                break;
        }

        return;
    }
     */

}
