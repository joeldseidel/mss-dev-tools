var uid = sessionStorage.getItem("uid");
var token = sessionStorage.getItem("token");

var todoContainer = document.getElementById("to-do-container");
var inProgressContainer = document.getElementById("in-progress-container");
var doneContainer = document.getElementById("done-container");

if(!"WebSocket" in window){
    alert("Websockets is not supported in this browser. Sorry chief");
}

$.ajax({
    type: "POST",
    url: "https://localhost:16802/get_project",
    dataType: "json",
    data: JSON.stringify({pid : getUsernameArg(), token: token}),
    success: function(data){
        if(data){
            createProjectView(data);
        } else {
            alert("Empty response from API");
        }
    }
});

function createProjectView(project){
    $('#project-name').text(project.name);
    $('#project-desc').text(project.desc);
    project.stories.forEach(createStoryView);
}

function createStoryView(story){
    var storyListing = $('<div class="story-listing"></div>');
    var storyTitle = $('<h3 class="story-title"></h3>');
    var storyDescription = $('<p class="story-description"></p>');
    var storyPriority = $('<p class="story-priority"></p>');
    storyTitle.text(story.title);
    storyTitle.appendTo(storyListing);
    storyDescription.text(story.description);
    storyDescription.appendTo(storyListing);
    storyPriority.text(story.priority);
    storyPriority.appendTo(storyListing);
    storyListing.bind('click', {story: story.sid}, openStory);
    switch(story.status){
        case 1:
            storyListing.appendTo(todoContainer);
            break;
        case 2:
            storyListing.appendTo(inProgressContainer);
            break;
        case 3:
            storyListing.appendto(doneContainer);
            break;
    }
}

function openStory(e){
    var story = e.data.story;
    alert(story);
}

var projConn;

function openProjectConnection(){
    projConn = new WebSocket("ws://ec2-18-191-103-225.us-east-2.compute.amazonaws.com:16803/project");
}

projConn.onopen = function(){

};

projConn.onmessage = function(){

};

projConn.onclose = function(){

};

function getUsernameArg(){
    var usernameParam = window.location.search.replace("?", '');
    usernameParam = usernameParam.replace("project=", '');
    usernameParam = usernameParam.replace("%20", ' ');
    return usernameParam;
}