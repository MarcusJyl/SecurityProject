/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import static com.mysql.cj.conf.PropertyKey.logger;
import errorhandling.InvalidInputException;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author marcg
 */
public class InputValidator {

    public static String validateInput(String input, int minLength, int maxLength) throws InvalidInputException {
        String s = Normalizer.normalize(input, Form.NFKC);

        Pattern pattern = Pattern.compile("[<>]");
        Matcher matcher = pattern.matcher(s);
        if (s.length() >= minLength && s.length() <= maxLength) {
            if (matcher.find()) {
                throw new InvalidInputException("Dangerous input characters try again :D");
            } else {
                return s;
            }
        } else {
            throw new InvalidInputException("Invalid length of input: " + s + " length should be between " + minLength + "-" + maxLength);
        }
    }
}
