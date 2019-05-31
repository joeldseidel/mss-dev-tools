var uid = sessionStorage.getItem("uid");
var token = sessionStorage.getItem("token");

var projectsContainer = document.getElementById("my-projects-container");
var activityContainer = document.getElementById("recent-activity-container");

$(document).ready(function(){
    var getProjectsData = {
        uid: uid,
        token: token
    };
    $.ajax({
        type: "POST",
        url: "https://localhost:16802/get_user_projects",
        dataType: "json",
        data: JSON.stringify(getProjectsData),
        success: function(data){
            if(data){
                var projectsArray = data.projects;
                if(projectsArray.length === 0){
                    var noProjectLabel = $('<p class="light-lbl"></p>');
                    noProjectLabel.text("You are not a part of any projects yet :(");
                }
                projectsArray.forEach(function(project){
                    createProjectListing(project.name, project.desc, project.pid);
                });
            } else {
                alert("MSS API Connect Failed. Are you connected to the internet?")
            }
        },
        error: function(data){
            alert("MSS API Connect Failed. Is the API on?");
        }
    });
});

function openProject(e){
    var project = e.data.project;
    window.location.href = "../pages/projectview.php?project=" + project;
}

function createProjectListing(name, desc, pid){
    var projectListing = $('<div class="project-listing"></div>');
    var projectTitle = $('<h4 class="project-title"></h4>');
    var projectDescription = $('<p class="project-desc"></p>');
    projectTitle.text(name);
    projectDescription.text(desc);
    projectTitle.appendTo(projectListing);
    projectDescription.appendTo(projectListing);
    projectListing.appendTo(projectsContainer);
    projectListing.bind('click', {project: pid}, openProject);
}