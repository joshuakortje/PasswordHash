/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package passwordhashes;

/**
 *
 * @author jkort
 */
public class Hash {
    private String user;
    private String salt;
    private String hash;
    
    public Hash(String newUser, String newSalt, String newHash){
        user = newUser;
        salt = newSalt;
        hash = newHash;
    }
    
    public String getUser(){
        return user;
    }
    
    public String getSalt(){
        return salt;
    }
    
    public String getHash(){
        return hash;
    }
}
