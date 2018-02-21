package com.zjzmjr.core.common.biz.weixin;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * XML工具类
 * @author Administrator
 *
 */
public class XMLUtil {
	
	/**
	 * 将XML对象转化为xml字符串
	 * @param obj
	 * @return
	 */
	public static String convertToXML(Object obj){
		StringWriter stringWriter=new StringWriter();
		try {
			JAXBContext context=JAXBContext.newInstance(obj.getClass());
			Marshaller marshaller=context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.marshal(obj, stringWriter);
			return stringWriter.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				stringWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 将xml字符串转化为xml对象
	 * @param t
	 * @param xmlStr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T convertToObj(Class<T> t,String xmlStr){
		StringReader reader=new StringReader(xmlStr);
		try {
			JAXBContext context=JAXBContext.newInstance(t);
			Unmarshaller unmarshaller=context.createUnmarshaller();
			return (T) unmarshaller.unmarshal(reader);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			reader.close();
		}
		return null;
	}

}
