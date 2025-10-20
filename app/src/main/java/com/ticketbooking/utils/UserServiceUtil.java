package com.ticketbooking.utils;

import org.mindrot.jbcrypt.BCrypt;

public class UserServiceUtil {

    public static String hashPassword(String plainpassword)
    {
        return BCrypt.hashpw(plainpassword, BCrypt.gensalt());
    }

    public static boolean checkPassword(String plainpassword, String hashpassword)
    {
        return BCrypt.checkpw(plainpassword, hashpassword);
    }
}
