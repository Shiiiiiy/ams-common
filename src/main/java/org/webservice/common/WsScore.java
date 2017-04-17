package org.webservice.common;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;

/**
 * This class was generated by the JAX-WS RI. JAX-WS RI 2.1.3-hudson-390-
 * Generated source version: 2.0
 * <p>
 * An example of how this class may be used:
 * 
 * <pre>
 * ws_score service = new ws_score();
 * WsScoreSoap portType = service.getWsScoreSoap();
 * portType.getStudentAverageScore(...);
 * </pre>
 * 
 * </p>
 * 
 */
@WebServiceClient(name = "ws_score", targetNamespace = "http://common.webservice.org/", wsdlLocation = "http://172.18.180.81:81/ws_score.asmx?wsdl")
public class WsScore extends Service {

	private final static URL WSSCORE_WSDL_LOCATION;
	private final static Logger logger = Logger
			.getLogger(org.webservice.common.WsScore.class.getName());

	static {
		URL url = null;
		try {
			URL baseUrl;
			baseUrl = org.webservice.common.WsScore.class.getResource(".");
			url = new URL(baseUrl,
					"http://172.18.180.81:81/ws_score.asmx?wsdl");
		} catch (MalformedURLException e) {
			logger.warning("Failed to create URL for the wsdl Location: 'http://esb.hzpt.edu.cn/esbpro/proxy/ProxyServiceJwcj?wsdl', retrying as a local file");
			logger.warning(e.getMessage());
		}
		WSSCORE_WSDL_LOCATION = url;
	}

	public WsScore(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}

	public WsScore() {
		super(WSSCORE_WSDL_LOCATION, new QName("http://common.webservice.org/",
				"ws_score"));
	}

	/**
	 * 
	 * @return returns WsScoreSoap
	 */
	@WebEndpoint(name = "ws_scoreSoap")
	public WsScoreSoap getWsScoreSoap() {
		return super.getPort(new QName("http://common.webservice.org/",
				"ws_scoreSoap"), WsScoreSoap.class);
	}

	/**
	 * 
	 * @return returns WsScoreSoap
	 */
	@WebEndpoint(name = "ws_scoreSoap12")
	public WsScoreSoap getWsScoreSoap12() {
		return super.getPort(new QName("http://common.webservice.org/",
				"ws_scoreSoap12"), WsScoreSoap.class);
	}

}