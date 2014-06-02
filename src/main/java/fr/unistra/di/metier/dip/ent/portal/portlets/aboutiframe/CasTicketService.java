package fr.unistra.di.metier.dip.ent.portal.portlets.aboutiframe;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;

import org.apache.log4j.Logger;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.TicketValidationException;
import org.jasig.cas.client.validation.TicketValidator;
import org.springframework.beans.factory.annotation.Required;

public class CasTicketService {

    private static final String CAS_ASSERTION_SESSION_ATTRIBUTE = "CAS_ASSERTION";

    private Logger log = Logger.getLogger(CasTicketService.class);

    private String service;
    private String casLogin;
    private TicketValidator ticketValidator;
    
    @Required
    public void setServiceUrl(String service) {
        this.service = service;
    }
    @Required
    public void setCasLoginUrl(String casLogin) {
        this.casLogin = casLogin;
    }
    @Required
    public void setTicketValidator(TicketValidator ticketValidator) {
        this.ticketValidator = ticketValidator;
    }
    
    public Assertion validateCasProxy(PortletRequest request) {

        // retrieve the CAS ticket from the UserInfo map
        @SuppressWarnings("unchecked")
        Map<String,String> userinfo = (Map<String,String>) request.getAttribute(PortletRequest.USER_INFO);
        String ticket = (String) userinfo.get("casProxyTicket");
        
        if (ticket == null) {
            log.warn("No CAS ticket found in the UserInfo map");
            return null;
        }

        try {
            Assertion assertion = ticketValidator.validate(ticket, service);
            return assertion;
        } catch (TicketValidationException ex) {
            log.warn("Failed to validate proxy ticket", ex);
            return null;
        }
    }
    
    /**
     * CAS2 Authenticated URL adding a dedicated ProxyTicket
     * as additional GET parameter if possible,
     * falling back to CAS1 in case of failure.
     * @see AttributePrincipal#getProxyTicketFor(String)
     * @see CasTicketService#prefixCasLoginURL(String)
     */
    public String getCasAuthURL(PortletRequest request, final String url) {
        // get CAS Assertion, either from Session, or from CAS / UserInfo attribute
        PortletSession session = request.getPortletSession(true);
        Assertion assertion = (Assertion) session.getAttribute(CAS_ASSERTION_SESSION_ATTRIBUTE);
        if (assertion == null) {
            assertion = validateCasProxy(request);
            session.setAttribute(CAS_ASSERTION_SESSION_ATTRIBUTE, assertion);
        }
        
        if (assertion == null) {
            log.warn("Failed to CAS2 Proxy Authenticate (no Assertion), falling back to CAS1");
            return prefixCasLoginURL(url);
        }
        
        String ticket = assertion.getPrincipal().getProxyTicketFor(url);
        if (ticket == null) {
            log.warn("Failed to CAS2 Proxy Authenticate (got no proxy ticket), falling back to CAS1");
            // remove CAS ASSERTION from session as it doesn't seem to work properly
            log.debug("Remove CAS Assertion from PortletSession as it failed providing ProxyTickets");
            session.removeAttribute(CAS_ASSERTION_SESSION_ATTRIBUTE);
            return prefixCasLoginURL(url);
        }
        
        return new StringBuilder()
            .append(url)
            .append(url.contains("?") ? "&" : "?")
            .append("ticket=").append(ticket)
            .toString();
    }

    /**
     * CAS1 Style Authenticated URL
     * @param url to be prefixed and encoded
     * @return prefixed url like https://cas.serveur.example.org/cas/login?service=https://client.service.example.org
     */
    public String prefixCasLoginURL(final String url) {
        String encodedURL; 
        try {
            encodedURL = URLEncoder.encode(url,"UTF-8");
        } catch (UnsupportedEncodingException ex) {
            log.error(ex);
            encodedURL = url;
        }
        return new StringBuilder()
        .append(casLogin)
        .append("?service=")
        .append(encodedURL)
        .toString();
    }
}
