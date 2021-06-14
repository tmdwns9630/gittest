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
        //윈도우 핸들을 가져옴
        int GetWindowRect(HWND handle, int[] rect);
        //GetWindowRect: 윈도우 화면 창 좌표 연산. 크기인지 좌표인지 헷갈리는데 
        
        //HWND GetFocus();
        HWND GetForegroundWindow();
        //HWND GetActiveWindow();
        HWND GetWindowText(HWND handle, char[] buffer, int Max);
        void ShowWindow(HWND hWnd, int nCmdShow);
    }
    //--------------------
    //getRect : 해당 객체의 영역(크기정보)를 Rectangle 로 리턴
    // 2개의 Exception 오류가 발생하면 이쪽에서 처리하고 호출한 곳으로 리턴?
    
    
	//메인 메서드
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
       // 캡처프로그램 창을 숨긴다.

    	char[] windowText = new char[512];
    	
    	HWND hwnd = User32.INSTANCE.GetForegroundWindow();
        User32.INSTANCE.GetWindowText(hwnd, windowText, 512);
        // 가장 앞에 있는 창의 윈도우 핸들을 찾아 윈도우 제목을 반환한다.
        
        String windowName = Native.toString(windowText);
        //위의 윈도우 제목을 String 타입으로 저장.
        
        newBuffer(windowName);
        //ㅁㅁㅁㅁㅁㅁㅁㅁㅁ 버퍼에 이름 추가.
        
        int[] rect;
        try {
            rect = Capture2.getRect(windowName);
            //윈도우 제목의 핸들을 찾아서 좌표를 구한다.
            System.out.printf("현재 창은  \"%s\" 이며, 코너 좌표는 %s입니다\n",windowName, Arrays.toString(rect));
           
            
            
            Shot3(rect, windowName);
            //캡처 & 저장
            System.out.println("저장 완료");
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
        //숨겼던 캡처프로그램 창을 복구시킨다.
    	
        WritewinBuffer(); //버퍼 쓰기
    }
	
	
    public static int[] getRect(String windowName) throws WindowNotFoundException,
            GetWindowRectException {
        HWND hwnd = User32.INSTANCE.FindWindow(null, windowName);
        
        if (hwnd == null) {
            throw new WindowNotFoundException("", windowName);
        }//활성화 윈도우가 없으면 호출한 곳으로 windownotfoundException 에러를 보낸다.

        int[] rect = {0, 0, 0, 0};
        User32.INSTANCE.GetWindowRect(hwnd, rect);
        //창 핸들의 현재 윈도우 좌표를 rect에 저장하고 길이를 result에 저장
        int sum = rect[0] + rect[1] + rect[2] + rect[3];
        if (sum == 0) {
            throw new GetWindowRectException(windowName);
        }//값이 0나오면 오류처리한다.
        return rect;
        //좌표 값을 반환한다.
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
    }//위 두 메서드는 오류 처리

    @SuppressWarnings("serial")
    public static class IllegalArgumentException extends Exception {
        public IllegalArgumentException() {
            super(String.format("Window is null & Rectangle is null!"));
        }
    }// Rectangle width and height must be > 0 처리

    
    //저장
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
            
           // height = A[3]-A[1]-10; //높이
           // width =A[2]-A[0]-12;  //폭
            height = A[3]-A[1]; //높이
            width =A[2]-A[0];  //폭
            
           System.out.println("x,y ="+A[0]+","+A[1]+"  높이:"+width+"  폭:"+height);
          
           if(height<0)
            	height = A[1] - A[3];
            if(width<0)
            	width = A[0]-A[2];
            Rectangle rectangle = new Rectangle(A[0],A[1],width,height);
            //화면의 크기를 얻어 내는 메소드
            //사각형 공간를 저장한다 
            
            BufferedImage image = robot.createScreenCapture(rectangle);
            //BufferedImage : 스크린으로부터 읽히는 픽셀을 포함한 이미지 생성
            image.setRGB(0,0,100);
            
            //파일 저장
                  
            
            for(int i = 0; i<5; i++)
            {
           	System.out.println("WinSave 출력 중 - "+WinSave[i]);
            	if(WinSave[i].equals(windowName))
            	{
            		System.out.println("추적완료");
            		saveFilePath = selectFilepath[i];
            	}
            	
            }
            System.out.println("제목 : "+windowName);
            System.out.println("저장경로 : "+saveFilePath);
            
            File file = new File(saveFilePath+saveFileName+"_"+time+"."+saveFileExtension);
            if (!file.exists()) {
            		file.mkdirs(); //폴더가 없을 경우 생성.
        		    System.out.println("폴더가 생성되었습니다.");
            }
            
            
            ImageIO.write(image, saveFileExtension, file);
            
            
            
        } catch (Exception e){
            e.printStackTrace();
        }
	}
    
   
  //ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ
    
    
    
    public static void ReadwinBuffer() {
    	// Buffer.txt를 읽어서 String 10줄로 저장한다.
    	
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
	// winBuffer String 10줄을Buffer.txt에 저장한다.
	
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
	// save.txt를 읽어서 String 5줄로 저장한다.
	
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
	// save String 10줄을 save.txt에 저장한다.+저장경로도 따로 저장.
	
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