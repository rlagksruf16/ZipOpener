import java.io.*;
import java.util.*;

public class Zip{

	public static void main(String[] args) {
		try {
			Scanner input = new Scanner(System.in); //입력 받기
			
			System.out.print("압축 파일의 이름을 넣으세요! ");
			String files = input.next();
			String FILE = "[절대 주소를 넣어주세요]";
			FILE = FILE + files;
			
			FileInputStream fis = new FileInputStream(FILE); // 파일 가져오기
		
			ArrayList<Integer> arr = new ArrayList<Integer>(); // 모든 바이트 저장 배열 
			ArrayList<Integer> indexStart = new ArrayList<Integer>(); // 시작 바이트 인덱스 저장 
			ArrayList<String> fileName = new ArrayList<String>(); // 파일 이름 
			ArrayList<String> fileDate = new ArrayList<String>(); // 수정 날짜
			ArrayList<String> fileSize = new ArrayList<String>(); // 파일 크기 
			
			// 열심히 돌려주기
			int data = 0;
			int i = 0;
			while ((data = fis.read()) != -1) {
				arr.add(data);
//            	System.out.println(arr.get(i));
            	i++;
            }
			
			
			// 시작 주소 넣는 것 
			for(int j = 0; j < arr.size()-3; j++) {
				if(arr.get(j) == 80 && arr.get(j+1) == 75 && arr.get(j+2) == 3 && arr.get(j+3) == 4) {
					indexStart.add(j);
				}
			}
			
			// 파일 이름, 압축 전 용량, 수정 날짜 저장 
			for(int z = 0; z < indexStart.size(); z++) {
				int start = indexStart.get(z); // 파일의 시작점 인덱스 
				int timeIndex1 = start + 12; // 날짜 바이트 인덱스 
				int timeIndex2 = start + 13; // 날짜 바이트 인덱스 
				int sizeIndex = start + 22; // 압축 전 크기 인덱스 
				int nameSizeIn1 = start + 26; // 파일명 사이즈 인덱스 
				int nameSizeIn2 = start + 27; // 파일명 사이즈 인덱스 
				int nameIndex = start + 30; // 파일명 시작 인덱스 
				
				String tempDate = intToString(arr.get(timeIndex2)) + intToString(arr.get(timeIndex1));
				
				fileDate.add(getDate(tempDate)); // 수정 일자 저장
				
				fileSize.add(Integer.toString(arr.get(sizeIndex))); // 크기 저장 
				
				// 이름 저장용 
				String names = "";
				for(int q = nameIndex; q < (nameIndex + arr.get(nameSizeIn1) + arr.get(nameSizeIn2)); q++) {
					int l = arr.get(q);
					char tmp = (char)l;
					names += Character.toString(tmp);
				}
				fileName.add(names);
				// System.out.println(names);	
			}
			System.out.println(files + " 안에 포함된 파일은 다음과 같습니다.");
			for(int y = 1; y <= indexStart.size(); y++) {
				System.out.println(y+". "+fileName.get(y-1)+" "+fileSize.get(y-1)+" bytes "+fileDate.get(y-1));
			}
			
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
	}
	
	// 7 , 4 , 5 비트씩 날짜 생성하는 함수 
	public static String getDate(String a) {
		String t = "20";
		String x = "0";
		String year = Integer.toString(biTode(a.substring(0,7))-20);
		if(year.length() == 1)
			year = x + year;
		year = t + year;
		String month = Integer.toString(biTode(a.substring(7,11)));
		if(month.length() == 1)
			month = x + month;
		String day = Integer.toString(biTode(a.substring(11,16)));
		if(day.length() == 1)
			day = x + day;
		String result = year + "/" + month + "/" + day;
		
		return result;
	}
	
	// 십진수를 8비트 2진수로 변경해주는 것 
	public static String intToString(int x) {
		String a = Integer.toString(new Integer(x), 2);
		String t = "0";
		if(a.length() < 8) {
			while(a.length() != 8) {
				a = t + a;
			}
		}
		return a;
	}
	// 2진수를 10진수 정수 
	public static int biTode(String x) {
		int decimal=Integer.parseInt(x,2);  
		return decimal; 
	}
			
}
