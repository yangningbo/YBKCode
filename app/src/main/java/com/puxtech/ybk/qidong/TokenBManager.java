package com.puxtech.ybk.qidong;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import android.content.Context;


public class TokenBManager {

	Context context;

	public TokenBManager(Context context) {
		super();
		this.context = context;
	}

	/**
	 * 取出tokenB，包含TKNB标签
	 * 
	 * @return 如果不存在就返回null
	 */
	public String getTokenB() {
		File cacheDir = context.getCacheDir();
		File fileDir = new File(cacheDir.getPath() + "/contentServer");
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		FileInputStream in = null;
		try {
			in = new FileInputStream(fileDir.getPath() + "/tokenB");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return "";
		}
		BufferedInputStream bufferin = new BufferedInputStream(in);
		byte c[] = new byte[1024];
		int n = 0;
		StringBuffer tokenb = new StringBuffer();
		try {
			while ((n = bufferin.read(c)) != -1) {
				String temp = new String(c, 0, n);
				tokenb.append(temp);
			}
			bufferin.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String tokenB = tokenb.toString();
		QiDongLogger.d("getTokenB...tokenB=" + tokenB);
		return tokenB;
	}

	/**
	 * <b>[private]</b>获取Element中某个节点的内容，不包含子节点
	 */
	private String getNodeValue(Element e, String tag) {
		if (((Element) (e.getElementsByTagName(tag).item(0))).getFirstChild() != null) {
			return ((Element) (e.getElementsByTagName(tag).item(0)))
					.getFirstChild().getNodeValue();
		}
		return "";
	}
	
	/**
	 * <b>[private]</b>获取tokenb中的某个标签里的内容
	 */
	public String getTagContentFromTokenBFile(String tagName) {
		try {
			File cacheDir = context.getCacheDir();
			File fileDir = new File(cacheDir.getPath() + "/contentServer");
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}
			File xmlFile = new File(fileDir.getPath() + "/tokenB");
			// 从本地文件读
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = null;
			document = builder.parse(xmlFile);
			// 获取根节点
			Element root = document.getDocumentElement();
			String content = getNodeValue(root, tagName);
			return content;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 从tokenb缓存文件中取得APPK
	 */
	public String getAppk() {
		return getTagContentFromTokenBFile("APPK");
	}

	/**
	 * 从tokenb缓存文件中取得DEVID
	 */
	public String getDevid() {
		return getTagContentFromTokenBFile("DEVID");
	}

	/**
	 * 从tokenb缓存文件中取得SALT
	 */
	public String getSalt() {
		return getTagContentFromTokenBFile("SALT");
	}

	/**
	 * 从tokenb缓存文件中取得DATE
	 */
	public String getDate() {
		return getTagContentFromTokenBFile("DATE");
	}

	/**
	 * 从tokenb缓存文件中取得SIGN
	 */
	public String getSign() {
		return getTagContentFromTokenBFile("SIGN");
	}

	/**
	 * 将tokenb保存到缓存文件
	 */
	public void saveTokenB(Element tokenBRootElement) {
		
		if(tokenBRootElement == null){
			return;
		}
		// 保存到本地
		File cacheDir = context.getCacheDir();
		File fileDir = new File(cacheDir.getPath() + "/contentServer");
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		try {
			TransformerFactory xformFactory = TransformerFactory.newInstance();

			Transformer idTransform = xformFactory.newTransformer();
			idTransform.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			idTransform.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
					"yes");

			Source input = new DOMSource(tokenBRootElement);

			Result output = new StreamResult(new FileOutputStream(
					fileDir.getPath() + "/tokenB"));
			idTransform.transform(input, output);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 删除tokenB
	 */
	public void deleteTokenB(){
		File cacheDir = context.getCacheDir();
		File fileDir = new File(cacheDir.getPath() + "/contentServer");
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		File tokenB = new File(fileDir.getPath() + "/tokenB");
		tokenB.delete();
	}

}
