<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en" ng-app="taselectfcApp">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="Tartan Army Select Football Club">
<meta name="author" content="David Johnston">
<title>TA Select FC</title>
<link rel="shortcut icon" href="/resources/images/favicon.ico" type="image/x-icon">
<link rel="icon" href="/resources/images/favicon.ico" type="image/x-icon">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
<link href="/resources/css/taselectfc.css" rel="stylesheet">
<!-- Fonts -->
<link
    href="http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800"
    rel="stylesheet" type="text/css">
<link
    href="http://fonts.googleapis.com/css?family=Josefin+Slab:100,300,400,600,700,100italic,300italic,400italic,600italic,700italic"
    rel="stylesheet" type="text/css">
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
    <div class="brand" id="banner">
        Tartan Army Select<br />Football Club
    </div>
    <nav class="navbar navbar-default navbar-static-top" role="navigation" id="topnavbar">
        <div class="container">
            <div class="navbar-header">
                <!-- small screen nav toggle -->
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="pull-left" href="#home"><img src="/resources/images/TASelectFCTransparentSmall.png" /></a> <a
                    class="navbar-brand" href="#home">TA Select FC</a>
            </div>
            <!-- nav links -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <a class="pull-left" href="#home" id="navbar-logo"><img
                    src="/resources/images/TASelectFCTransparent.png" /></a>
                <ul class="nav navbar-nav">
                    <li><a href="#home">Home</a></li>
                    <li class="dropdown"><a href="" class="dropdown-toggle" data-toggle="dropdown" role="button"
                        aria-haspopup="true" aria-expanded="false">Team <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="#players">Squad</a></li>
                            <li><a href="#stats">Stats</a></li>
                        </ul></li>
                    <li class="dropdown"><a href="" class="dropdown-toggle" data-toggle="dropdown" role="button"
                        aria-haspopup="true" aria-expanded="false">Matches <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="#fixtures">Fixtures</a></li>
                            <li><a href="#results">Results</a></li>
                        </ul></li>
                    <li><a href="#gallery">Gallery</a></li>
                    <li><a href="#contact">Contact</a></li>
                </ul>
            </div>
        </div>
    </nav>
    <div ng-view></div>
    <footer>
        <div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <p>Copyright &copy; TA Select FC 2015</p>
                </div>
            </div>
        </div>
    </footer>
    <script src="//code.jquery.com/jquery-1.11.3.min.js"></script>
    <script src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.0/angular.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.0/angular-route.min.js"></script>
    <script src="/resources/js/taselectfc.js"></script>
    <script src="/resources/js/controllers.js"></script>
</body>
</html>