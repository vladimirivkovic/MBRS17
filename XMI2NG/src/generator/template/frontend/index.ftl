<!DOCTYPE html>
<html lang="en" ng-app="app">
<head>
    <meta charset="UTF-8">

    <link rel="shortcut icon" href="favicon.ico"/>
    <title>Autogenerated Angular App</title>
    <base href="/"/>

    <!-- <css> -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
          integrity="sha256-7s5uDGW3AHqw6xtJmNNtr+OBRJUlgkNJEo78P4b0yRw= sha512-nNo+yCHEyn0smMxSswnf/OnX6/KwJuZTlNZBjauKhTK0c+zT+q5JOCx0UFhXQ6rJR9jg6Es8gPuD2uZcYDLqSw=="
          crossorigin="anonymous"/>
    <link rel="stylesheet" href="css/main.css"/>
    <link rel="stylesheet" href="css/util.css"/>
    <link rel="stylesheet" href="css/margin.css">
    <link rel="stylesheet" href="css/bootswatch/flatly/bootstrap.min.css">
		<link rel="stylesheet" href="css/padding.css">

    <!-- <libraries> -->
    <script src="https://code.jquery.com/jquery-2.2.4.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"
            integrity="sha256-KXn5puMvxCw+dAYznun+drMdG1IFl3agK0p/pqT9KAo= sha512-2e8qq0ETcfWRI4HJBzQiA3UoyFk6tbNyG+qSaIBZLyW9Xf3sWZHN/lxe9fTh1U45DpPf07yj94KsUHHWe4Yk1A=="
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.5.0/angular.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.5.0/angular-animate.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.5.0/angular-route.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.5.0/angular-resource.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/ngStorage/0.3.10/ngStorage.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/angular-ui-bootstrap/1.1.2/ui-bootstrap-tpls.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/angular-sanitize/1.5.5/angular-sanitize.min.js"></script>
    
    
    <!--<script src="https://unpkg.com/angular-ui-router@0.4.2/release/angular-ui-router.min.js"></script>-->

    <!-- <angular> -->
    <script type="application/javascript" src="${root}app.js"></script>
    
    <#list classes as class>
    <script type="application/javascript" src="${root}${class.lowerName}/${class.lowerName}.js"></script>
    <script type="application/javascript" src="${root}${class.lowerName}/${class.lowerName}Ctrl.js"></script>
    <script type="application/javascript" src="${root}${class.lowerName}/${class.lowerName}ModalCtrl.js"></script>
    <#list class.properties as property>
    <#if property.upper == 1 && !property.primitive>
    <script type="application/javascript" src="${root}${class.lowerName}/modal/${property.name}ModalCtrl.js"></script>
    </#if>
    </#list>
    <script type="application/javascript" src="${root}${class.lowerName}/${class.lowerName}Rsrc.js"></script>
    </#list>
    <script type="application/javascript" src="login/loginCtrl.js"></script>
    <script type="application/javascript" src="login/AuthenticationService.js"></script>


</head>
<body>
<nav class="navbar navbar-inverse">
	<div class="container">
		<div class="navbar-subject"><a class="navbar-brand" href="#/">Generated AngularJS App</a>
		</div>

		<ul class="nav navbar-nav navbar-left">
			<#list groups as group>
			<li uib-dropdown on-toggle="toggled(open)">
				<a href id="simple-dropdown" uib-dropdown-toggle>${group} <span class="caret"></span></a>
				<ul class="dropdown-menu" uib-dropdown-menu
					aria-labelledby="simple-dropdown">
					<#list classes as class>
					<#if class.UIClass.visible && class.UIClass.entityGroup == group>
					<li><a href="#/${class.lowerName}">${class.originName}</a></li>
					</#if>
					</#list>
				</ul>
				
			</li>
			</#list>
			
		</ul>
		<ul class="nav navbar-nav navbar-right">
       		<li ng-show="!isLoggedIn()"><a href="#/login">Log in</a></li>
       		<li ng-show="isLoggedIn()"><a href ng-click="logout()">Log out</a></li>
     	</ul>
	</div>
</nav>

<div id="Content" class="container">
    <ng-view></ng-view>
</div>
</body>
</html>