package org.webservice.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="getStudentAverageScoreResult" type="{http://common.webservice.org/}ArrayOfString" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "getStudentAverageScoreResult" })
@XmlRootElement(name = "getStudentAverageScoreResponse")
public class GetStudentAverageScoreResponse {

	protected ArrayOfString getStudentAverageScoreResult;

	/**
	 * Gets the value of the getStudentAverageScoreResult property.
	 * 
	 * @return possible object is {@link ArrayOfString }
	 * 
	 */
	public ArrayOfString getGetStudentAverageScoreResult() {
		return getStudentAverageScoreResult;
	}

	/**
	 * Sets the value of the getStudentAverageScoreResult property.
	 * 
	 * @param value
	 *            allowed object is {@link ArrayOfString }
	 * 
	 */
	public void setGetStudentAverageScoreResult(ArrayOfString value) {
		this.getStudentAverageScoreResult = value;
	}

}
