package co.in.nextgencoder.Model;

public class Validation {
    public String validateUser(String mail, String pass) {

        if (mail == null || mail.isEmpty()) {
            return "Enter mail to proceed further";
        }

        if (pass == null || pass.isEmpty()) {
            return "Enter password to proceed further";
        }

        if (pass.length() < 8) {
            return "Password length should not be less than 8";
        }
        return "successful";
    }
}
