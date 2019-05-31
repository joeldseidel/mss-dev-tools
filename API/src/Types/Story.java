package Types;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

public class Story {
    private String title, description;
    private int sid, priority, status;
    private Developer reporter;
    private Date timeStamp;
    private List<Task> tasks;

    public Story(int sid, String title, String description, int priority, int status, Date timeStamp, Developer reporter){
        this.sid = sid;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.timeStamp = timeStamp;
        this.reporter = reporter;
    }

    public void setTasks(List<Task> tasks){
        this.tasks = tasks;
    }

    public JSONObject convertToJson(){
        JSONObject storyObj = new JSONObject();
        storyObj.put("sid", sid);
        storyObj.put("title", title);
        storyObj.put("description", description);
        storyObj.put("priority", priority);
        storyObj.put("status", status);
        storyObj.put("timeStamp", timeStamp.toString());
        storyObj.put("reporter", reporter.getName());
        JSONArray tasksObj = new JSONArray();
        for(Task task : tasks){
            tasksObj.put(task.convertToJson());
        }
        storyObj.put("tasks", tasksObj);
        return storyObj;
    }
}
