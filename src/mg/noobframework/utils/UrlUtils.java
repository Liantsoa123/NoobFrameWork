package mg.noobframework.utils;

import jakarta.servlet.http.HttpServletRequest;

public class UrlUtils {
    public static String getOriginalUrl(HttpServletRequest request) {
        // Vérifie si la requête est un forward
        String originalUrl = (String) request.getAttribute("javax.servlet.forward.request_uri");

        if (originalUrl == null) {
            // Vérifie le header 'Referer' et assurez-vous de n'utiliser que le chemin sans
            // le domaine
            originalUrl = request.getHeader("Referer");

            if (originalUrl != null && originalUrl.startsWith("http://") || originalUrl.startsWith("https://")) {
                // Si c'est un URL complet, ne prendre que le chemin
                originalUrl = originalUrl.substring(originalUrl.indexOf(request.getContextPath()));
            }
        }

        if (originalUrl == null) {
            // En dernier recours, retourne l'URI de la requête actuelle
            originalUrl = request.getRequestURI();
        }

        return originalUrl;
    }

}