package com.karzhou.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.Locale;

@WebServlet("/changeLanguage")
public class LanguageSwitchServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String lang = request.getParameter("lang"); // "en" или "ru"

        if (lang != null && (lang.equals("en") || lang.equals("ru"))) {
            Locale locale = new Locale(lang);
            HttpSession session = request.getSession();
            session.setAttribute("locale", locale);
        }

        String referer = request.getHeader("referer");
        if (referer != null) {
            response.sendRedirect(referer);
        } else {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}


