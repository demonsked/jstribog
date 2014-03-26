/*
    Copyright (C) 2014  sarin

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package ru.ubmb.jstribog;

import java.security.MessageDigestSpi;
import java.util.ArrayList;
import java.util.List;
import ru.ubmb.jstribog.exceptions.InvalidVectorLenException;

/**
 *
 * @author sarin
 */
abstract class Stribog extends StribogAlg{
    
    protected List<Integer> buffer = new ArrayList<Integer>(256);

    @Override
    protected void engineUpdate(byte input) {
        buffer.add(input & 0xFF);
    }

    @Override
    protected void engineUpdate(byte[] input, int offset, int len) {
        for (int i = offset; i < len; i++) {
            buffer.add(input[i] & 0xFF);
        }
    }    

    @Override
    protected void engineReset() {
        buffer.clear();
    }
    
    protected byte[] getDigest(int[] IV) {
        int[] ba = new int[buffer.size()];
        for (int i = 0; i < ba.length; i++) {
            ba[i] = buffer.get(i);            
        }
        int[] hashX = hashX(IV, ba);
        byte[] result = new byte[hashX.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = (byte) hashX[i];
        }
        return result;
    }
    
    protected final int[] hashX(int[] IV, int[] message) {
        int[] h = new int[64];
        System.arraycopy(IV, 0, h, 0, 64);
        int[] M = new int[message.length];
        System.arraycopy(message, 0, M, 0, message.length);
        int[] N = new int[64];
        int[] Sigma = new int[64];
        int[] m = new int[64];
        int l = message.length;
        while (l >= 64) {            
            System.arraycopy(M, l - 64, m, 0, 64);
            h = gN(N, h, m);
            N = add(N, Data.bv512);
            Sigma = add(Sigma, m);
            l -= 64;
        }
        for (int i = 0; i < 63 - l; i++) {
            m[i] = 0;
        }
        m[63 - l] = 0x01;
        if (l > 0) {
            System.arraycopy(M, 0, m, 63 - l + 1, l);
        }
       
        h = gN(N, h, m);        
        int[] bv = new int[64];
        bv[62] = (l * 8) >> 8;
        bv[63] = (l * 8) & 0xFF;
        N = add(N, bv);        
        Sigma = add(Sigma, m);        
        h = gN(Data.bv00, h, N);        
        h = gN(Data.bv00, h, Sigma);
        return h;
    }
    
}
