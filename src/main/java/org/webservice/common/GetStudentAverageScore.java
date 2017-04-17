package org.webservice.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="xn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="xq_m" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="xsxh" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="time" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sign" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "xn", "xqM", "xsxh", "time", "sign" })
@XmlRootElement(name = "getStudentAverageScore")
public class GetStudentAverageScore {

	protected String xn;
	@XmlElement(name = "xq_m")
	protected String xqM;
	protected String xsxh;
	protected String time;
	protected String sign;

	/**
	 * Gets the value of the xn property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getXn() {
		return xn;
	}

	/**
	 * Sets the value of the xn property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setXn(String value) {
		this.xn = value;
	}

	/**
	 * Gets the value of the xqM property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getXqM() {
		return xqM;
	}

	/**
	 * Sets the value of the xqM property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setXqM(String value) {
		this.xqM = value;
	}

	/**
	 * Gets the value of the xsxh property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getXsxh() {
		return xsxh;
	}

	/**
	 * Sets the value of the xsxh property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setXsxh(String value) {
		this.xsxh = value;
	}

	/**
	 * Gets the value of the time property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getTime() {
		return time;
	}

	/**
	 * Sets the value of the time property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setTime(String value) {
		this.time = value;
	}

	/**
	 * Gets the value of the sign property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getSign() {
		return sign;
	}

	/**
	 * Sets the value of the sign property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setSign(String value) {
		this.sign = value;
	}

}
