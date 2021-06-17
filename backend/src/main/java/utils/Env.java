/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author marcg
 */
public class Env {

    private static Env intance = null;

    public static boolean isDev = true;
    public static String secret = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    public static String salt = "dasdasdasdasdassaasdaasdasdasdasaasd";
    public static String recaptchaSecret;

    public Env() {
        if (System.getenv("DEPLOYED") != null) {
            recaptchaSecret = System.getenv("RECAPTCHA");
            if (System.getenv("DEPLOYED") == "PROD") {
                isDev = false;
                secret = System.getenv("SECRET");
                salt = System.getenv("SALT");
                
            }

        }
    }

    public static Env GetEnv() {
        if (intance == null) {
            intance = new Env();
        }
        return intance;
    }
}
