package Handlers;

import Managers.ProjectDataManager;
import Types.Project;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class GetUserProjects extends HandlerPrototype implements HttpHandler {
    public GetUserProjects(){
        this.requiredKeys = new String[] {"uid", "token"};
        this.handlerName = "Get User Projects";
    }
    @Override
    protected void fulfillRequest(JSONObject requestParams) {
        long uid = requestParams.getLong("uid");
        ProjectDataManager projectDataManager = new ProjectDataManager();
        List<Project> userProjects = projectDataManager.getUserProjects(uid);
        this.response = getProjectsArray(userProjects).toString();
    }

    private JSONObject getProjectsArray(List<Project> projects){
        JSONArray projectsArray = new JSONArray();
        for(Project project : projects){
            JSONObject projectObj = new JSONObject();
            projectObj.put("name", project.getName());
            projectObj.put("desc", project.getDescription());
            projectObj.put("pid", project.getProjectId());
            projectsArray.put(projectObj);
        }
        return new JSONObject().put("projects", projectsArray);
    }
}
