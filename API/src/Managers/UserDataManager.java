package Managers;

import DB.DatabaseInteraction;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDataManager {
    private DatabaseInteraction database;
    public UserDataManager(){
        this.database = new DatabaseInteraction();
    }
    public int getUserCount(String username){
        String isUserValidSql = "SELECT uid FROM Users WHERE username = ?";
        PreparedStatement isUserValidStmt = database.prepareStatement(isUserValidSql);
        int userCount = 0;
        try{
            isUserValidStmt.setString(1, username);
            ResultSet getUserResults = database.query(isUserValidStmt);
            while(getUserResults.next()){
                userCount ++;
            }
        } catch(SQLException sqlEx){
            sqlEx.printStackTrace();
            userCount = -1;
        }
        return userCount;
    }

    public long getUid(String username){
        long uid;
        String getUidSql = "SELECT uid FROM Users WHERE username = ?";
        PreparedStatement getUidStmt = database.prepareStatement(getUidSql);
        try{
            getUidStmt.setString(1, username);
            ResultSet getUidResults = database.query(getUidStmt);
            getUidResults.next();
            uid = getUidResults.getLong("uid");
        } catch (SQLException sqlEx){
            uid = -1;
        }
        return uid;
    }

    public boolean checkPasswordMatch(long uid, String password){
        MessageDigest messageDigestSHA;
        boolean isMatch;
        try{
            messageDigestSHA = MessageDigest.getInstance("SHA-256");
        } catch(Exception ex){
            System.out.println("Could not get message digest");
            return false;
        }
        String isPasswordMatchSql = "SELECT password from Users WHERE uid = ?";
        PreparedStatement matchPassStmt = database.prepareStatement(isPasswordMatchSql);
        try{
            matchPassStmt.setString(1, ""+uid);
            ResultSet matchPassResult = database.query(matchPassStmt);
            matchPassResult.next();
            String gotPass = matchPassResult.getString("password").toLowerCase();
            String thisPass = byteArrayToString(messageDigestSHA.digest(password.getBytes(StandardCharsets.UTF_8))).toLowerCase();
            isMatch = gotPass.equals(thisPass);
        } catch(SQLException sqlEx) {
            sqlEx.printStackTrace();
            isMatch = false;
        }
        return isMatch;
    }

    private static String byteArrayToString(byte[] hash){
        StringBuffer outString = new StringBuffer();
        //Convert to hex string and append
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) outString.append('0');
            outString.append(hex);
        }

        return outString.toString();
    }
}
