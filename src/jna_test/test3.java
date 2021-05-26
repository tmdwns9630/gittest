package jna_test;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinBase.SYSTEMTIME;



public class test3 {
	public static void main(String[] args) {
		SYSTEMTIME timeStructure = new SYSTEMTIME();
		Kernel32.INSTANCE.GetSystemTime(timeStructure);
		System.out.println("현재시간 : "
				+timeStructure.wYear
				+"-"+timeStructure.wMonth
				+"-"+timeStructure.wDay
				+" "+timeStructure.wHour
				+":"+timeStructure.wMinute
				);
	}

}
