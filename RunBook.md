# Introduction #

The goal of the project is to provide free and open source JCA (Java Cryptography Architecture) compatible implementation of GOST R 34.11-2012 cryptography hash function.

http://tk26.ru/en/GOSTR3411-2012/GOST_R_34_11-2012_eng.pdf

http://www.altell.ru/legislation/standards/gost-34.11-2012.pdf


# Installation #

To use this library
  * checkout the sources
  * build JCA project
  * rename JAR file as you wish (optional)
  * copy JAR to '${java-home}/lib/ext'
  * add Stribog provider (ru.ubmb.jstribog.StribogProvider) to java.security
  * use the hash!

Note: you can also use the dynamic installation.

Read this for details about the installation process: http://docs.oracle.com/javase/7/docs/technotes/guides/security/crypto/HowToImplAProvider.html#Step7

# Usage samples #
With dynamic installation:
```
        Security.addProvider(new StribogProvider());
        MessageDigest md = MessageDigest.getInstance("Stribog512", "JStribog");
        byte[] bytes = new byte[]{
            0x32, 0x31, 0x30, 0x39, 0x38, 0x37, 0x36, 0x35, 0x34, 0x33, 0x32, 0x31, 0x30, 0x39, 0x38, 0x37
            , 0x36, 0x35, 0x34, 0x33, 0x32, 0x31, 0x30, 0x39, 0x38, 0x37, 0x36, 0x35, 0x34, 0x33, 0x32, 0x31
            , 0x30, 0x39, 0x38, 0x37, 0x36, 0x35, 0x34, 0x33, 0x32, 0x31, 0x30, 0x39, 0x38, 0x37, 0x36, 0x35
            , 0x34, 0x33, 0x32, 0x31, 0x30, 0x39, 0x38, 0x37, 0x36, 0x35, 0x34, 0x33, 0x32, 0x31, 0x30
        };
        byte[] digest = md.digest(bytes);
        for (byte b : digest) {
            int iv = (int)b & 0xFF;
            if (iv < 10) {
                System.out.print('0');
            }
            System.out.print(Integer.toHexString(iv).toUpperCase());
            System.out.print(' ');
        }
        System.out.println();
```
256 bit version:
```
        Security.addProvider(new StribogProvider());
        MessageDigest md = MessageDigest.getInstance("Stribog256", "JStribog");
        byte[] bytes = new byte[]{
            0x32, 0x31, 0x30, 0x39, 0x38, 0x37, 0x36, 0x35, 0x34, 0x33, 0x32, 0x31, 0x30, 0x39, 0x38, 0x37
            , 0x36, 0x35, 0x34, 0x33, 0x32, 0x31, 0x30, 0x39, 0x38, 0x37, 0x36, 0x35, 0x34, 0x33, 0x32, 0x31
            , 0x30, 0x39, 0x38, 0x37, 0x36, 0x35, 0x34, 0x33, 0x32, 0x31, 0x30, 0x39, 0x38, 0x37, 0x36, 0x35
            , 0x34, 0x33, 0x32, 0x31, 0x30, 0x39, 0x38, 0x37, 0x36, 0x35, 0x34, 0x33, 0x32, 0x31, 0x30
        };
        byte[] digest = md.digest(bytes);
        for (byte b : digest) {
            int iv = (int)b & 0xFF;
            if (iv < 10) {
                System.out.print('0');
            }
            System.out.print(Integer.toHexString(iv).toUpperCase());
            System.out.print(' ');
        }
        System.out.println();
```