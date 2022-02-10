package engine.util;

import java.math.BigInteger;
import java.util.Arrays;

public class Util {

	public static BigInteger convertIntegerToByteArray(long num){
		return BigInteger.valueOf(num);
	}

	private static byte[] buffered;
	public static synchronized BigInteger convertByteArrayToNumber(byte[] arr) {
		if(buffered == null)buffered = arr.clone();
		else if(buffered.length<arr.length){
			buffered = arr.clone();
		}else{
			System.arraycopy(arr,0,buffered,0,arr.length);
		}
		buffered = invertByteArray(buffered,arr.length);
    	return new BigInteger(Arrays.copyOfRange(buffered,0,arr.length));
	}

	public static byte[] convertNumberToByteArray(BigInteger number,int size) {
    	byte[] arr = number.toByteArray();
    	arr = invertByteArray(arr,arr.length);
    	byte[] aux = new byte[size];
    	for(int x=0;x<aux.length && x<arr.length;x++) {
    		aux[x]=arr[x];
    	}
    	return aux;
	}
	public static byte[] convertLongToByteArray(long num,int size) {
		byte[] arr = new byte[size];
		for(int x=0;x<size&&num>0;x++,num>>=8)
			arr[x] = (byte)num;
		return arr;
	}
	
	public final static byte[] invertByteArray(byte[] array,int size) {
		byte tmp;
		for(int x=0;x<size/2;x++) {
			tmp=array[x];
			array[x]=array[size-x-1];
			array[size-x-1]=tmp;
		}
		return array;
	}
    
}
