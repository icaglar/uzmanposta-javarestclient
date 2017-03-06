package com.uzmanposta.restclient.util;

public class CharUtil {

	
	public static String returnUnicodeOfString(String word){
		char[] charArray = word.toCharArray();
		
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < charArray.length; i++) {
			
			if(charArray[i]=='ı' || charArray[i]=='İ'){
				builder.append(unicodeEscaped(charArray[i]));
			}
			else{
				builder.append(charArray[i]);
			}
		
		}
		
		return builder.toString();
	}
	
	public static String unicodeEscaped(char ch) {
	      if (ch < 0x10) {
	          return "\\u000" + Integer.toHexString(ch);
	      } else if (ch < 0x100) {
	          return "\\u00" + Integer.toHexString(ch);
	      } else if (ch < 0x1000) {
	          return "\\u0" + Integer.toHexString(ch);
	      }
	      return "\\u" + Integer.toHexString(ch);
	  }
}
