/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package passwordhashes;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jkort
 */
public class PasswordHashes {
    
    private static Vector<Hash> victims;

    /**
     * @param args the command line arguments
     * args[0] is the name of the file with the hashes we are trying to find
     * args[1] is the name of the file with the dictionary (ie the Bible)
     */
    public static void main(String[] args) throws NoSuchAlgorithmException {
        
        try {
            inputHashes(args[0]);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PasswordHashes.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        /* TESTING PURPOSES
        for(Hash h: victims){
            System.out.println(h.getUser() + h.getSalt() +h.getHash());
        }*/
        
        
        MessageDigest md = MessageDigest.getInstance("MD5");
        
        //counter for counting hashes
        long count = 0;
        
        //open a scanner for the dictionary
        Scanner dictionary;
        String nextWord;
        try {
            dictionary = new Scanner(new File(args[1]));
            while(dictionary.hasNext()){
                nextWord = dictionary.next();
                
                //call function to do the actual hashing and checking
                checkHashes(nextWord, md);
                count = count + 1; //keep track of hashes
                if((count % 1000) == 0){
                    System.out.println(count);
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PasswordHashes.class.getName()).log(Level.SEVERE, null, ex);
        }
               
    }
    
    public static void checkHashes(String word, MessageDigest md){
        
    }
    
    public static void inputHashes(String filename) throws FileNotFoundException{
        victims = new Vector<Hash>();
        
        Scanner victimInput = new Scanner(new File(filename));
        victimInput.useDelimiter(":|\\n");
        String name = "";
        String salt = "";
        String hash = "";
        
        //get each row
        while(victimInput.hasNext()){
            name = victimInput.next();
            salt = victimInput.next();
            hash = victimInput.next();
            
            //put into the victim vector
            victims.add(new Hash(name, salt, hash));
        }
    }
    
    //this method takes the hash of a word passed in
    public static String hash(String pass, MessageDigest md){
        md.update(pass.getBytes());
        byte[] testing1 = md.digest();
        StringBuffer stringBuffer = new StringBuffer();
        for (byte bytes : testing1) {
            stringBuffer.append(String.format("%02x", bytes & 0xff));
        }
        
        return stringBuffer.toString();
    }
    
}
