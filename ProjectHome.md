The goal of the project is to provide free and open source JCA (Java Cryptography Architecture) compatible implementation of GOST (ГОСТ) R 34.11-2012 cryptography hash function.

If you want to participate in the project, or need a contact with author please create an issue here: https://code.google.com/p/jstribog/issues/list

### Usage ###
Use StribogB version for lo0ong inputs. Notice that you need to revers the input data yourself if you use StribogB256 or StribogB512.

Stribog256 and Stribog512 are not so difficult in usage, but OutOfMemoryException can occure, if you'll try to get the digest of the message which bigger (longer), than Java Heap.
```
package com.mystartup;

import java.security.*;
import ru.ubmb.jstribog.StribogProvider;

public class UsageSample {

    private static final String[] hashNames = {"Stribog256", "Stribog512"};
    private static final String[] reversedHashNames = {"StribogB256", "StribogB512"}; //Use this digests for loooong messages from streams

    private static final byte[] message = new byte[]{
        0x32, 0x31, 0x30, 0x39, 0x38, 0x37, 0x36, 0x35, 0x34, 0x33, 0x32, 0x31, 0x30, 0x39, 0x38, 0x37, 0x36, 0x35, 0x34, 0x33, 0x32, 0x31, 0x30, 0x39, 0x38, 0x37, 0x36, 0x35, 0x34, 0x33, 0x32, 0x31, 0x30, 0x39, 0x38, 0x37, 0x36, 0x35, 0x34, 0x33, 0x32, 0x31, 0x30, 0x39, 0x38, 0x37, 0x36, 0x35, 0x34, 0x33, 0x32, 0x31, 0x30, 0x39, 0x38, 0x37, 0x36, 0x35, 0x34, 0x33, 0x32, 0x31, 0x30
    };

    public static void main(String[] argv) throws NoSuchAlgorithmException, NoSuchProviderException {
        if (Security.getProvider("JStribog") == null) {
            Security.addProvider(new StribogProvider());
        }        
        for (String hashName : hashNames) {
            MessageDigest md = MessageDigest.getInstance(hashName);
            byte[] digest = md.digest(message);
            printHex(digest);
        }
        for (String hashName : reversedHashNames) {
            MessageDigest md = MessageDigest.getInstance(hashName);
            byte[] digest = md.digest(reverse(message));
            printHex(digest);
        }
    }

    private static void printHex(byte[] digest) {
        for (byte b : digest) {
            int iv = (int) b & 0xFF;
            if (iv < 0x10) {
                System.out.print('0');
            }
            System.out.print(Integer.toHexString(iv).toUpperCase());
        }
        System.out.println();
    }
    
    private static byte[] reverse(byte[] ba) {        
        byte[] result = new byte[ba.length];
        for (int i = ba.length - 1; i >= 0; i--) {
            result[ba.length - 1 - i] = ba[i];
        }
        return result;
    }

}
```
Expected output:
```
00557BE5E584FD52A449B16B0251D05D27F94AB76CBAA6DA890B59D8EF1E159D
486F64C1917879417FEF082B3381A4E211C324F074654C38823A7B76F830AD00FA1FBAE42B1285C0352F227524BC9AB16254288DD6863DCCD5B9F54A1AD0541B
00557BE5E584FD52A449B16B0251D05D27F94AB76CBAA6DA890B59D8EF1E159D
486F64C1917879417FEF082B3381A4E211C324F074654C38823A7B76F830AD00FA1FBAE42B1285C0352F227524BC9AB16254288DD6863DCCD5B9F54A1AD0541B
```