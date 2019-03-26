/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package passwordhashes;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author jkort
 */
public class PasswordHashes {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NoSuchAlgorithmException {
        // TODO code application logic here
        String data = "mean";
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(data.getBytes());
        byte[] testing1 = md.digest();
        StringBuffer stringBuffer = new StringBuffer();
        for (byte bytes : testing1) {
            stringBuffer.append(String.format("%02x", bytes & 0xff));
        }
        
        System.out.println(stringBuffer.toString());
    }
    
}
