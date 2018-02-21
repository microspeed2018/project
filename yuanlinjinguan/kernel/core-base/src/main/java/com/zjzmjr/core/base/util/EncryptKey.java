package com.zjzmjr.core.base.util;

/**
 * 
 * 
 * @author oms
 * @version $Id: EncryptKey.java, v 0.1 2016-6-3 下午1:35:02 oms Exp $
 */
public class EncryptKey implements java.security.Key {

	/**  */
    private static final long serialVersionUID = 1484394930301929860L;

    public String getAlgorithm() {

		return "DES";
	}

	public byte[] getEncoded() {
		byte[] bb = new byte[8];
		bb[0] = 55;
		bb[1] = -62;
		bb[2] = 55;
		bb[3] = -8;
		bb[4] = 14;
		bb[5] = 93;
		bb[6] = -51;
		bb[7] = -22;

		return bb;
	}

	public String getFormat() {
		return "RAW";
	}

}