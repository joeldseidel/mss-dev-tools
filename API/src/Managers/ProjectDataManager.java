package Managers;

import DB.DatabaseInteraction;
import Types.Developer;
import Types.Project;
import Types.Story;
import Types.Task;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProjectDataManager {
    private DatabaseInteraction database;
    public ProjectDataManager(){
        this.database = new DatabaseInteraction();
    }
    public List<Project> getUserProjects(long uid){
        List<Project> projects = new ArrayList<>();
        String getAssignedProjectsSql = "SELECT ProjectAssignments.pid, Projects.projectname, Projects.description FROM ProjectAssignments INNER JOIN Projects ON ProjectAssignments.pid = Projects.pid WHERE ProjectAssignments.uid = ?";
        PreparedStatement getProjectsStmt = database.prepareStatement(getAssignedProjectsSql);
        try{
            getProjectsStmt.setLong(1, uid);
            ResultSet projectsResults = database.query(getProjectsStmt);
            while(projectsResults.next()){
                int pid = projectsResults.getInt("pid");
                String projectName = projectsResults.getString("projectname");
                String description = projectsResults.getString("description");
                projects.add(new Project(pid, projectName, description));
            }
        } catch(SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return projects;
    }
    public Project getProject(int pid){
        Project thisProject = new Project(pid);
        String getProjectDetailsSql = "SELECT projectname, description FROM Projects WHERE pid = ?";
        PreparedStatement getProjectDetailsStmt = database.prepareStatement(getProjectDetailsSql);
        try{
            getProjectDetailsStmt.setInt(1, pid);
            ResultSet projectDetailResults = database.query(getProjectDetailsStmt);
            if(projectDetailResults.next()){
                thisProject.setName(projectDetailResults.getString("projectname"));
                thisProject.setDescription(projectDetailResults.getString("description"));
                thisProject.setStories(getProjectStories(pid));
            }
        } catch(SQLException sqlEx){
            sqlEx.printStackTrace();
        }
        return thisProject;
    }

    private List<Story> getProjectStories(int pid){
        List<Story> projectStories = new ArrayList<>();
        String getStoriesSql = "SELECT Stories.sid AS sid, Stories.title AS title, Stories.description AS description, Stories.priority AS priority, Stories.status AS status, Stories.timeStamp AS timeStamp, Developers.firstname AS firstname, Developers.lastname AS lastname FROM Stories INNER JOIN Developers ON Stories.reporterId = Developers.uid WHERE Stories.pid = ?";
        PreparedStatement getStoriesStmt = database.prepareStatement(getStoriesSql);
        try {
            getStoriesStmt.setInt(1, pid);
            ResultSet storiesResults = database.query(getStoriesStmt);
            while (storiesResults.next()) {
                int sid = storiesResults.getInt("sid");
                String title = storiesResults.getString("title");
                String desc = storiesResults.getString("description");
                int priority = storiesResults.getInt("priority");
                int status = storiesResults.getInt("status");
                Date timeStamp = storiesResults.getTime("timeStamp");
                Developer reporter = new Developer(storiesResults.getString("firstname"), storiesResults.getString("lastname"));
                Story thisStory = new Story(sid, title, desc, priority, status, timeStamp, reporter);
                thisStory.setTasks(getStoryTasks(sid));
                projectStories.add(thisStory);
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return projectStories;
    }

    private List<Task> getStoryTasks(int sid){
        List<Task> storyTasks = new ArrayList<>();
        String getTasksSql = "SELECT Tasks.tid AS tid, Tasks.title AS title, Tasks.description AS description, Tasks.priority AS priority, Tasks.status AS status, Tasks.timeStamp AS timeStamp, Developers.firstname AS firstname, Developers.lastname AS lastname FROM Tasks INNER JOIN Developers ON Tasks.reporterId = Developers.uid WHERE Tasks.sid = ?";
        PreparedStatement getStoryTasksStmt = database.prepareStatement(getTasksSql);
        try{
            getStoryTasksStmt.setInt(1, sid);
            ResultSet taskResults = database.query(getStoryTasksStmt);
            while(taskResults.next()){
                int tid = taskResults.getInt("tid");
                String title  = taskResults.getString("title");
                String description = taskResults.getString("description");
                int priority = taskResults.getInt("priority");
                int status = taskResults.getInt("status");
                Date timeStamp = taskResults.getTime("timeStamp");
                Developer reporter = new Developer(taskResults.getString("firstname"), taskResults.getString("lastname"));
                storyTasks.add(new Task(tid, title, description, priority, status, timeStamp, reporter));
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return storyTasks;
    }
}
