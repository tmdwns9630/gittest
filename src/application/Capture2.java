package application;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.Arrays;
import java.text.SimpleDateFormat;

import javax.imageio.ImageIO;
//import com.sun.jna.Pointer;
import com.sun.jna.Native;
//import com.sun.jna.*;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.win32.*;



public class Capture2 {
	
	static String[] WinBuffer = new String[10];
	static String[] WinSave = new String[5];
	static String[] selectFilepath = new String[5];
	
	
	public interface User32 extends StdCallLibrary {
        @SuppressWarnings("deprecation")
		User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class,
                W32APIOptions.DEFAULT_OPTIONS);

        HWND FindWindow(String lpClassName, String lpWindowName);
        //������ �ڵ��� ������
        int GetWindowRect(HWND handle, int[] rect);
        //GetWindowRect: ������ ȭ�� â ��ǥ ����. ũ������ ��ǥ���� �򰥸��µ� 
        
        //HWND GetFocus();
        HWND GetForegroundWindow();
        //HWND GetActiveWindow();
        HWND GetWindowText(HWND handle, char[] buffer, int Max);
        void ShowWindow(HWND hWnd, int nCmdShow);
    }
    //--------------------
    //getRect : �ش� ��ü�� ����(ũ������)�� Rectangle �� ����
    // 2���� Exception ������ �߻��ϸ� ���ʿ��� ó���ϰ� ȣ���� ������ ����?
    
    
	//���� �޼���
    public static void main(String[] args) {    
    	ReadWriteTime();
    	HandleUp();
    	   	
    }
    
    public static void ReadWriteTime() {
    	ReadwinBuffer();
    	ReadwinSave();
    	
    	//selectFilepath[0]="D:\\Dtest\\BlueStacks\\";
  	
    	
    }
    
	
    public static void HandleUp()  {
    	HWND hwnd0 = User32.INSTANCE.GetForegroundWindow();
        User32.INSTANCE.ShowWindow(hwnd0,6);
       // ĸó���α׷� â�� �����.

    	char[] windowText = new char[512];
    	
    	HWND hwnd = User32.INSTANCE.GetForegroundWindow();
        User32.INSTANCE.GetWindowText(hwnd, windowText, 512);
        // ���� �տ� �ִ� â�� ������ �ڵ��� ã�� ������ ������ ��ȯ�Ѵ�.
        
        String windowName = Native.toString(windowText);
        //���� ������ ������ String Ÿ������ ����.
        
        newBuffer(windowName);
        //������������������ ���ۿ� �̸� �߰�.
        
        int[] rect;
        try {
            rect = Capture2.getRect(windowName);
            //������ ������ �ڵ��� ã�Ƽ� ��ǥ�� ���Ѵ�.
            System.out.printf("���� â��  \"%s\" �̸�, �ڳ� ��ǥ�� %s�Դϴ�\n",windowName, Arrays.toString(rect));
           
            
            
            Shot3(rect, windowName);
            //ĸó & ����
            System.out.println("���� �Ϸ�");
            Thread.sleep(500);
           
        } catch (Capture2.WindowNotFoundException e) {
            e.printStackTrace();
        } catch (Capture2.GetWindowRectException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        User32.INSTANCE.ShowWindow(hwnd0,9);
        //����� ĸó���α׷� â�� ������Ų��.
    	
        WritewinBuffer(); //���� ����
    }
	
	
    public static int[] getRect(String windowName) throws WindowNotFoundException,
            GetWindowRectException {
        HWND hwnd = User32.INSTANCE.FindWindow(null, windowName);
        
        if (hwnd == null) {
            throw new WindowNotFoundException("", windowName);
        }//Ȱ��ȭ �����찡 ������ ȣ���� ������ windownotfoundException ������ ������.

        int[] rect = {0, 0, 0, 0};
        User32.INSTANCE.GetWindowRect(hwnd, rect);
        //â �ڵ��� ���� ������ ��ǥ�� rect�� �����ϰ� ���̸� result�� ����
        int sum = rect[0] + rect[1] + rect[2] + rect[3];
        if (sum == 0) {
            throw new GetWindowRectException(windowName);
        }//���� 0������ ����ó���Ѵ�.
        return rect;
        //��ǥ ���� ��ȯ�Ѵ�.
    }

    
    
    @SuppressWarnings("serial")
    public static class WindowNotFoundException extends Exception {
        public WindowNotFoundException(String className, String windowName) {
            super(String.format("Window null for className: %s; windowName: %s",
                    className, windowName));
        }
    }

    @SuppressWarnings("serial")
    public static class GetWindowRectException extends Exception {
        public GetWindowRectException(String windowName) {
            super("Window Rect not found for " + windowName + "or because it's null!");
        }
    }//�� �� �޼���� ���� ó��

    @SuppressWarnings("serial")
    public static class IllegalArgumentException extends Exception {
        public IllegalArgumentException() {
            super(String.format("Window is null & Rectangle is null!"));
        }
    }// Rectangle width and height must be > 0 ó��

    
    //����
    public static void Shot3(int[] A,String windowName) {
		String saveFilePath = "C:\\SelectedCapture\\";
        String saveFileName = "ScreenShotTest";
        String saveFileExtension = "png";
        
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Calendar cal = Calendar.getInstance();
    	String time = sdf.format(cal.getTime());
        
        
        int height,width = 0;
        try {
            Robot robot = new Robot();
            
           // height = A[3]-A[1]-10; //����
           // width =A[2]-A[0]-12;  //��
            height = A[3]-A[1]; //����
            width =A[2]-A[0];  //��
            
           System.out.println("x,y ="+A[0]+","+A[1]+"  ����:"+width+"  ��:"+height);
          
           if(height<0)
            	height = A[1] - A[3];
            if(width<0)
            	width = A[0]-A[2];
            Rectangle rectangle = new Rectangle(A[0],A[1],width,height);
            //ȭ���� ũ�⸦ ��� ���� �޼ҵ�
            //�簢�� ������ �����Ѵ� 
            
            BufferedImage image = robot.createScreenCapture(rectangle);
            //BufferedImage : ��ũ�����κ��� ������ �ȼ��� ������ �̹��� ����
            image.setRGB(0,0,100);
            
            //���� ����
                  
            
            for(int i = 0; i<5; i++)
            {
           	System.out.println("WinSave ��� �� - "+WinSave[i]);
            	if(WinSave[i].equals(windowName))
            	{
            		System.out.println("�����Ϸ�");
            		saveFilePath = selectFilepath[i];
            	}
            	
            }
            System.out.println("���� : "+windowName);
            System.out.println("������ : "+saveFilePath);
            
            File file = new File(saveFilePath+saveFileName+"_"+time+"."+saveFileExtension);
            if (!file.exists()) {
            		file.mkdirs(); //������ ���� ��� ����.
        		    System.out.println("������ �����Ǿ����ϴ�.");
            }
            
            
            ImageIO.write(image, saveFileExtension, file);
            
            
            
        } catch (Exception e){
            e.printStackTrace();
        }
	}
    
   
  //��������������������������������������������������������������������������������
    
    
    
    public static void ReadwinBuffer() {
    	// Buffer.txt�� �о String 10�ٷ� �����Ѵ�.
    	
    	try{
    		BufferedReader in = new BufferedReader(new FileReader("Save/buffer.txt"));
    		
    		
    		String text;
    		int i = 0 ;
    	
    		
    		while((text = in.readLine()) != null)
    		{
    			WinBuffer[i] = text;
    			i++;
    		}
    
				in.close();
		
    	} catch (Exception e) { 
		      e.printStackTrace();
		  }
}

public static void WritewinBuffer() {
	// winBuffer String 10����Buffer.txt�� �����Ѵ�.
	
	try{
		BufferedWriter out = new BufferedWriter(new FileWriter("Save/buffer.txt"));
		//BufferedWriter out = new BufferedWriter(new FileWriter("Save/WindowName_buffer.txt"));
		
		//String text;
		//char[] Title = new char[255];
		
		for(int i=0; i<10; i++)
		{
			if(WinBuffer[i] == null)
			{
				out.write(" \n");
			}
			else
				out.write(WinBuffer[i]+"\n");
		}	
		
		out.close();
	
	} catch (Exception e) { 
	      e.printStackTrace();
	  }
	

}

public static void ReadwinSave() {
	// save.txt�� �о String 5�ٷ� �����Ѵ�.
	
	try{
		BufferedReader in = new BufferedReader(new FileReader("Save/save.txt"));
		BufferedReader in2 = new BufferedReader(new FileReader("Save/path.txt"));
		
		String text;
		
		int i =0;
		while((text = in.readLine()) != null)
		{
			WinSave[i] = text;
			i++;
		}
		i=0;
		while((text = in2.readLine()) != null)
		{
			selectFilepath[i] = text;
			i++;
		}

			in.close();
			in2.close();
	
	} catch (Exception e) { 
	      e.printStackTrace();
	  }
}

public static void WritewinSave() {
	// save String 10���� save.txt�� �����Ѵ�.+�����ε� ���� ����.
	
	try{
		BufferedWriter out = new BufferedWriter(new FileWriter("Save/save.txt"));
		BufferedWriter out2 = new BufferedWriter(new FileWriter("Save/path.txt"));
		
		for(int i=0; i<5; i++)
		{
			out.write(WinSave[i]+"\n");
			out2.write(selectFilepath[i]+"\n");
		}	
		
		out.close();
		out2.close();
	
	} catch (Exception e) { 
	      e.printStackTrace();
	  }
	

}

public static void newBuffer(String w) {
	for(int i = 9; i>0;i--)
	{
		WinBuffer[i] = WinBuffer[i-1];
	}
	WinBuffer[0] = w;
}

 
    
    
    
}