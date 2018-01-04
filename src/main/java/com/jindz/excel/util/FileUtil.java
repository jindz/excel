package com.jindz.excel.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

public class FileUtil {

	public static String SEGM_NEWLINE = "\n";
	public static String SEGM_COLUMN = ";";
	public static String SEGM_HOLDER = "---";

	/**
	 * 获取文件的InputStream
	 * 
	 * @param 文件名
	 * @return InputStream
	 * @throws ParseException
	 */
	public static InputStream getInputStream(String path) {
		return Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(path);
	}

	/**
	 * 获取文件的OutputStream
	 * 
	 * @param 文件名
	 * @param 是否追加
	 * @return InputStream
	 * @throws ParseException
	 */
	public static OutputStream getFileOutputStream(String path, boolean isAppend)
			throws FileNotFoundException {
		try {
			// URL is =
			// Thread.currentThread().getContextClassLoader().getResource(FileName);
			return new FileOutputStream(path, isAppend);
		} catch (FileNotFoundException e) {
			throw e;
		}
	}

	/**
	 * 创建文件
	 * 
	 * @param 文件路径
	 * @param 文件名
	 * @return file
	 * @throws ParseException
	 */
	public static File createFileAsFile(String filepath, String FileName)
			throws Exception {
		File file = null;
		try {
			if (!StringUtils.isEmpty(filepath.trim())) {
				String ch = filepath.substring(filepath.length() - 1,
						filepath.length());
				if (!ch.equals("/") && !ch.equals("\\")) {
					filepath = filepath + "/";
				}
				if (!new File(filepath).isDirectory()) {
					file = new File(filepath);
					file.mkdirs();
				}
			}
			if (!StringUtils.isEmpty(FileName)) {
				file = new File(filepath + FileName);
				file.createNewFile();
			}
		} catch (Exception e) {
			throw e;
		}
		return file;
	}

	/**
	 * 将字符串写入文件
	 * 
	 * @param 文件名
	 * @param 写入的字符串
	 * @param 是否追加
	 * @throws ParseException
	 */
	public static void writeFile(String path, String source, boolean isAppend)
			throws Exception {
		byte[] bytes = source.getBytes();
		OutputStream os = getFileOutputStream(path, isAppend);
		os.write(bytes);
		os.close();
	}

	/**
	 * 将字符串写入文件
	 * 
	 * @param 文件名
	 * @param 写入的字符串
	 * @throws ParseException
	 */
	public static void writeFile(String path, String source) throws Exception {
		byte[] bytes = source.getBytes();
		OutputStream os = getFileOutputStream(path, false);
		os.write(bytes);
		os.close();
	}

	/**
	 * 将输入流利用输出流写入文件
	 * 
	 * @param 文件路径
	 * @param 文件名
	 * @param 输入流
	 * @throws ParseException
	 */
	public static void writeFile(String filePath, String fileName,
			InputStream in) throws Exception {
		BufferedOutputStream outStream = null;
		BufferedInputStream iutStream = null;
		try {
			outStream = new BufferedOutputStream(new FileOutputStream(
					createFileAsFile(filePath, fileName)));
			iutStream = new BufferedInputStream(in);
			int c;
			while ((c = iutStream.read()) != -1) {
				outStream.write(c);
				outStream.flush();
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			throw exception;
		} finally {
			outStream.close();
			iutStream.close();
		}
	}

	/**
	 * 读取文件
	 * 
	 * @param 文件名
	 * @return String
	 * @author jindezhi
	 */
	public static String readFile(String path) throws Exception {
		String output = "";
		File file = new File(path);
		if (file.exists()) {
			if (file.isFile()) {
				BufferedReader input = new BufferedReader(new FileReader(file));
				StringBuffer buffer = new StringBuffer();
				String text = "";
				while ((text = input.readLine()) != null) {
					buffer.append(text).append("\n");
				}
				output = buffer.toString();
			} else if (file.isDirectory()) {
				String[] dir = file.list();
				output += "Directory contents:\n";
				for (int i = 0; i < dir.length; i++) {
					output += dir[i] + "\n";
				}
			}
		} else {
			throw new Exception("file:" + path + " Does not exist!");
		}
		return output;
	}

	/**
	 * 读取文件
	 * 
	 * @return String
	 * @author jindezhi
	 */
	public static String readFile(InputStream stream) throws Exception {
		byte b[] = new byte[1024];
		int len = 0;
		int temp = 0; // 所有读取的内容都使用temp接收
		while ((temp = stream.read()) != -1) { // 当没有读取完时，继续读取
			b[len] = (byte) temp;
			len++;
		}
		stream.close();
		String result = new String(b, 0, len);
		return result;
	}

	/**
	 * 将文件转换成base64
	 * @author jindz
	 * @date 2017年11月2日 下午1:03:26
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static String encodeBase64File(String path) throws Exception {  
		  File file = new File(path);;  
		  FileInputStream inputFile = new FileInputStream(file);  
		  byte[] buffer = new byte[(int) file.length()];  
		  inputFile.read(buffer);  
		  inputFile.close();  
		  return new Base64().encodeAsString(buffer);
		  
	 }  
	
	 /** 
	  * 将base64字符解码保存文件 
	  * @author jindz
	  * @param base64Code 
	  * @param targetPath 
	  * @throws Exception 
	  */  
	  
	 public static void decoderBase64File(String base64Code, String targetPath)  
			throws Exception {
		File file = new File(targetPath);
		if (!file.exists()) {
			new File(file.getParent()).mkdirs();
			file.createNewFile();
		}
		byte[] buffer = new org.apache.commons.codec.binary.Base64()
				.decode(base64Code);
		FileOutputStream out = new FileOutputStream(targetPath);
		out.write(buffer);
		out.close();
	} 
    
}
