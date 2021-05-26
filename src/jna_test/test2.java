package jna_test;

import com.sun.jna.Library;

import com.sun.jna.Native;


public class test2 {
//user32
	public interface CInterface extends Library
	{
		public boolean LockWorkStation();
		public int MessageBoxA(int handle, String message, String title, int type);
	}
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		CInterface ci = (CInterface)Native.loadLibrary("user32", CInterface.class);
		ci.MessageBoxA(0,"LockWorkStation!","¾Ë¸²",0);
		ci.LockWorkStation();
	}
}
