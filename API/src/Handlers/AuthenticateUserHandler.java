package Handlers;

import Managers.UserDataManager;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;


public class AuthenticateUserHandler extends HandlerPrototype implements HttpHandler {
    public AuthenticateUserHandler(){
        requiredKeys = new String[] {"username", "password"};
        handlerName = "Authenticate User Handler";
    }

    @Override
    protected boolean isRequestValid(JSONObject requestParams){
        if(requestParams == null){
            //Request did not come with parameters and is invalid
            System.out.println("Request params null");
            return false;
        }
        for(String requiredKey : requiredKeys){
            if(!requestParams.has(requiredKey)){
                System.out.println("Request params missing key " + requiredKey);
                return false;
            }
        }
        return true;
    }

    @Override
    protected void fulfillRequest(JSONObject requestParams) {
        String username = requestParams.getString("username");
        String password = requestParams.getString("password");
        UserDataManager userDataManager = new UserDataManager();
        boolean isUserValid = isUserValid(username, password, userDataManager);
        if(isUserValid){
            JSONObject userDataObj = new JSONObject();
            userDataObj.put("uid", userDataManager.getUid(username));
            userDataObj.put("token", createToken());
            this.response = userDataObj.toString();
        } else {
            this.response = "false";
        }
    }

    private boolean isUserValid(String username, String password, UserDataManager userDataManager){
        boolean userIsValid;
        boolean userExists = userDataManager.getUserCount(username) == 1;
        if (userExists) {
            long uid = userDataManager.getUid(username);
            userIsValid = userDataManager.checkPasswordMatch(uid, password);
        } else {
            userIsValid = false;
        }
        return userIsValid;
    }

    private static String createToken(){
        String token = "";
        try {
            //Generate the user token
            Algorithm algorithm = Algorithm.HMAC256("secret");
            token = JWT.create().withIssuer("localhost:16802").sign(algorithm);
        } catch (Exception exception){
            //Invalid Signing configuration / Couldn't convert Claims.
        }
        System.out.println("Created token : " + token);
        return token;
    }
}
