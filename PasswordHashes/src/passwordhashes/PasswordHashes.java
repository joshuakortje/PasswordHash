/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package passwordhashes;

import com.sun.xml.internal.ws.util.StringUtils;
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
                findHashes(nextWord, md);
                count = count + 1; //keep track of hashes
                if((count % 10000) == 0){
                    System.out.println("Hashes computed: " + count);
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PasswordHashes.class.getName()).log(Level.SEVERE, null, ex);
        }
               
    }
    
    public static void checkHashes(String word, MessageDigest md){
        
        //testing the word
        String tempHash = hash(word, md);
        Vector<Hash> found = new Vector<Hash>();
        for(Hash person: victims){
            //get another hash if a Salt was used
            if(!("".equals(person.getSalt()))){
                tempHash = hash(word + person.getSalt(), md);
            }            
            
            //if they are equal Declare it and remove it
            if(person.getHash().equals(tempHash)){
                System.out.println("Found password for " + person.getUser() + ". Password is " + word);
                found.add(person);
            }
        }
        if(!found.isEmpty()){
            victims.removeAll(found);
            found.removeAllElements();
        }
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
            victims.add(new Hash(name, salt, hash.trim()));
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

    private static void findHashes(String word, MessageDigest md) {
        checkHashes(word, md);
        checkHashes(word.toLowerCase(), md);
        checkHashes(word.toUpperCase(), md);
        checkHashes(StringUtils.capitalize(word), md);
        String temp = word.replace('a', '@');
        temp = temp.replace('A', '@');
        checkHashes(temp, md);
        temp = word.replace('s', '5');
        temp = temp.replace('S', '5');
        checkHashes(temp, md);
        temp = word.replace('s', '$');
        temp = temp.replace('S', '$');
        checkHashes(temp, md);
        
        
        /*
        for(int i = 0; i < 100; i++){
            temp = word + i;
            checkHashes(temp, md);
            temp = i + word;
            checkHashes(temp, md);
        }*/
        
        checkHashes(word + "!", md);
        checkHashes(word + "@", md);
        checkHashes(word + "#", md);
        checkHashes(word + "$", md);
        checkHashes(word + "%", md);
        checkHashes(word + "&", md);
        checkHashes(word + "*", md);
        checkHashes(word + "?", md);
        
        temp = word.replace('t', '7');
        temp = temp.replace('T', '7');
        checkHashes(temp, md);
        
        //did not work
        temp = word.replace('z', 'S');
        temp = temp.replace('Z', 'S');
        checkHashes(temp, md);
        temp = word.replace('s', 'Z');
        temp = temp.replace('S', 'Z');
        checkHashes(temp, md);
        temp = word.replace('c', '>');
        temp = temp.replace('C', '>');
        checkHashes(temp, md);
        temp = word.replace('c', '<');
        temp = temp.replace('C', '<');
        checkHashes(temp, md);
        
        //these did not work
        temp = word.replace('l', '/');
        temp = temp.replace('L', '/');
        checkHashes(temp, md);
        temp = word.replace('l', '\\');
        temp = temp.replace('L', '\\');
        checkHashes(temp, md);
        
        //these did not find anything
        checkHashes("!" + word, md);
        checkHashes("@" + word, md);
        checkHashes("#" + word, md);
        checkHashes("$" + word, md);
        checkHashes("%" + word, md);
        checkHashes("&" + word, md);
        checkHashes("*" + word, md);
        checkHashes("?" + word, md);
        
        //did not work
        temp = word.replace('a', '4');
        temp = temp.replace('A', '4');
        checkHashes(temp, md);
        
        
        //did not work
        temp = word.replace('l', '1');
        temp = temp.replace('L', '1'); //these are the number 1
        checkHashes(temp, md);
        
        
        //did not work
        temp = word.replace('b', '6');
        temp = temp.replace('B', '6');
        checkHashes(temp, md);
        
        
        //did nothing
        temp = word.replace('u', 'v');
        temp = temp.replace('U', 'V');
        checkHashes(temp, md);
        temp = word.replace('v', 'u');
        temp = temp.replace('V', 'U');
        checkHashes(temp, md);
        
        //this did not work
        temp = word.replace('s', '2');
        temp = temp.replace('S', '2');
        checkHashes(temp, md);
        
        
        //this did not work
        temp = word.replace('l', '|');
        temp = temp.replace('L', '|');
        checkHashes(temp, md);
        
        //this did not work
        temp = word.replace('o', '0');
        temp = temp.replace('O', '0');
        checkHashes(temp, md);
        
        //this did not work
        temp = word.replace('i', '!');
        temp = temp.replace('I', '!');
        checkHashes(temp, md);
        
        //this did not work
        temp = word.replace('e', 'e');
        temp = temp.replace('E', '3');
        checkHashes(temp, md);
        
        //this does not work
        temp = word.replace('i', 'l'); //these are the letter L
        temp = temp.replace('I', 'l');
        checkHashes(temp, md);
        
        
        //did not find anything
        temp = word.replace('i', '|');
        temp = temp.replace('I', '|');
        checkHashes(temp, md);
        
        //does not find anything
        temp = word.substring(0, word.length()-1) + word.substring(word.length()-1).toUpperCase();
        checkHashes(temp, md);
    }
    
}
