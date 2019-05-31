package Handlers;

import Managers.ProjectDataManager;
import Types.Project;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;

public class GetProjectHandler extends HandlerPrototype implements HttpHandler {
    public GetProjectHandler(){
        requiredKeys = new String[] { "pid", "token" };
        handlerName = "Get Project Handler";
    }
    @Override
    protected void fulfillRequest(JSONObject requestParams) {
        int pid = requestParams.getInt("pid");
        ProjectDataManager projectDataManager = new ProjectDataManager();
        Project thisProject = projectDataManager.getProject(pid);
        this.response = thisProject.convertToJson().toString();
    }
}
