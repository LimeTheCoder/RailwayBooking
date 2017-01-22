package controller.i18n;


import controller.constants.AttributesHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

/**
 * Set appropriate locale into session attribute
 *
 * @author Taras Sakahrchuk
 */
public class LocaleFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        if(req.getParameter(AttributesHolder.LANG) != null) {
            replaceUserLocale(req);
        }

        if (req.getSession().getAttribute(AttributesHolder.LOCALE) == null) {
            setUserLocale(req);
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

    private void replaceUserLocale(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String langParameter = request.getParameter(AttributesHolder.LANG);
        Locale locale = SupportedLocale.getLocaleOrDefault(langParameter);
        session.setAttribute(AttributesHolder.LOCALE, locale);
    }

    private void setUserLocale(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Locale locale = SupportedLocale.getDefault();
        session.setAttribute(AttributesHolder.LOCALE, locale);
    }
}
