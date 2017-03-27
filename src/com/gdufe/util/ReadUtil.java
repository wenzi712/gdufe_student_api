package com.gdufe.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;

/*
 *	���ڶ�ȡ�����е����ݣ�������ָ������
 * */
public class ReadUtil {
	/*
	 * ��ȡentity�е����ݷ����ַ�����ʽ
	 * */
	public static String read2Str(HttpEntity entity){
		InputStream in = null;
		String html = "";
		if(entity!=null){
			try {
				in = entity.getContent();
				byte[] buf = new byte[1024];
				int len;
				while((len=in.read(buf))!=-1){
					html += new String(buf,0,len,"utf-8");
				}
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}finally{
				if(in!=null){
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		return html;
	}

/*	public static void read2File(HttpEntity entity,String fileName){
		FileOutputStream fos = null;
		InputStream in = null;
		try {
			fos = new FileOutputStream(fileName);
			in = entity.getContent();
			byte[] buf = new byte[1024];
			int len;
			while((len=in.read(buf))!=-1){
				fos.write(buf);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		
	}
*/	
}
