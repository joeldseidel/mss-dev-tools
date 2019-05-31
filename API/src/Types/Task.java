package Types;

import org.json.JSONObject;

import java.util.Date;

public class Task {
    private int tid, priority, status;
    private String title, description;
    private Date timeStamp;
    private Developer reporter;

    public Task(int tid, String title, String description, int priority, int status, Date timeStamp, Developer reporter){
        this.tid = tid;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.timeStamp = timeStamp;
        this.reporter = reporter;
    }

    public JSONObject convertToJson(){
        JSONObject taskObj = new JSONObject();
        taskObj.put("tid", tid);
        taskObj.put("title", title);
        taskObj.put("description", description);
        taskObj.put("priority", priority);
        taskObj.put("status", status);
        taskObj.put("timeStamp", timeStamp.toString());
        taskObj.put("reporter", reporter.getName());
        return taskObj;
    }
}
