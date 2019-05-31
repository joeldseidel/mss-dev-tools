package Types;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class Project {
    private int pid;
    private String name, description, projectManager, productManager;
    private boolean isActive;
    private List<Story> stories;

    public Project(int pid, String name, String description){
        this.name = name;
        this.description = description;
        this.pid = pid;
    }

    public Project(int pid){ this.pid = pid; }

    public String getName(){ return this.name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return this.description; }
    public void setDescription(String description) { this.description = description; }

    public int getProjectId(){ return this.pid; }

    public void setStories(List<Story> stories){ this.stories = stories; }

    public JSONObject convertToJson(){
        JSONObject projObj = new JSONObject();
        projObj.put("pid", pid);
        projObj.put("name", name);
        projObj.put("desc", description);
        JSONArray storiesObj = new JSONArray();
        for(Story story : stories){
            storiesObj.put(story.convertToJson());
        }
        projObj.put("stories", storiesObj);
        return projObj;
    }
}
