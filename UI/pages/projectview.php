<!DOCTYPE html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="../style.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
    <nav class="navbar navbar-expand-md bg-dark navbar-dark">
        <a class="navbar-brand" href="#">MSS</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapse-nav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="collapse-nav">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="#">Projects</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Docs</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Tools</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Settings</a>
                </li>
            </ul>
        </div>
    </nav>
    <div class="jumbotron">
        <h1 id="project-name"></h1>
        <p id="project-desc"></p>
    </div>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-4">
                <div class="container-fluid">
                    <h3>To Do</h3>
                    <div id="to-do-container"></div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="container-fluid">
                    <h3>In Progress</h3>
                    <div id="in-progress-container"></div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="container-fluid">
                    <h3>Done</h3>
                    <div id="done-container"></div>
                </div>
            </div>
        </div>
    </div>
    <script src="../script/project-client.js"></script>
</body>