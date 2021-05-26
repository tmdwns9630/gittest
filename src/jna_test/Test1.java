package jna_test;

import com.sun.jna.Library;

import com.sun.jna.Native;



public class Test1 {
	
	public interface CInterface extends Library
	{
		public int puts(String str);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CInterface ci = (CInterface) Native.loadLibrary("msvcrt", CInterface.class);
		ci.puts("Hello World!");
	
	
	}

}
