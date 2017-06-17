(function (angular) {
    var app = angular.module('app', ['ui.bootstrap', 'ngRoute', 'app.LoginModule','ngStorage',// 'ui.router',//'login', 
    	<#list classes as class>'app.${class.name}', </#list>]);

	app.config(function ($routeProvider, $locationProvider, $httpProvider) {
        $routeProvider
        .when("/", {
        	templateUrl : "main.html"
    	})
        <#list classes as class>
        .when('/${class.lowerName}', {
	        templateUrl: '${root}${class.lowerName}/${class.lowerName}View.html',
	        controller: '${class.name}sCtrl'
	    })
        </#list>
        .when('/login', {
        	templateUrl: 'login/loginView.html',
        	controller: 'LoginCtrl'
    	})
        ;
    })
    
    app.filter('yesOrNo', function() {
	return function(input) {
	  return input === true ? 'Da' : 'Ne' ;
	};
	})
	
	<#list enumerations as enum>
	app.filter('${enum.name}', function(){
	return function(input) {
	  	<#list enum.literals as literal>
	 	if (input == ${literal?index})
	 		return '${literal}';
		</#list>
	};
	
	})
	</#list>

    /*app.config(function ($stateProvider, $urlRouterProvider) {
        $urlRouterProvider.otherwise('/faktura');
        $stateProvider
        <#list classes as class>
        .state('${class.lowerName}', {
	        url: '/${class.lowerName}',
	        templateUrl: '${root}${class.lowerName}/${class.lowerName}View.html',
	        controller: '${class.name}sCtrl'
	    })
        </#list>
        ;
    })*/
    .run(run);

    function run($rootScope, $http, $location,$localStorage) { //, $state) {
    	$rootScope.host = 'http://mbrs17app.azurewebsites.net/';
    	if ($localStorage.currentUser) {
        	$http.defaults.headers.common.Authorization = $localStorage.currentUser.token;
      	}
    
        //postavljanje tokena nakon refresh
        /*if ($localStorage.currentUser) {
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.currentUser.token;
        }

        // ukoliko pokušamo da odemo na stranicu za koju nemamo prava, redirektujemo se na login
        $rootScope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
            var publicStates = ['login'];//, 'register'];
            var restrictedState = publicStates.indexOf(toState.name) === -1;
            if (restrictedState && !AuthenticationService.getCurrentUser()) {
                $state.go('login');
            }

            var adminStates = ['projects', 'users'];
            restrictedState = adminStates.indexOf(toState.name) !== -1;
            if (restrictedState && AuthenticationService.getCurrentUser() && $rootScope.getCurrentUserRole() != 'Admin') {
                $state.go('dashboard');
            }
        });

        $rootScope.logout = function () {
            AuthenticationService.logout();
        }

        $rootScope.getCurrentUserRole = function () {
            if (!AuthenticationService.getCurrentUser()) {
                return undefined;
            }
            else {
                return AuthenticationService.getCurrentUser().role;
            }
        }
        $rootScope.isLoggedIn = function () {
            if (AuthenticationService.getCurrentUser()) {
                return true;
            }
            else {
                return false;
            }
        }*/
        //$rootScope.getCurrentState = function () {
        //    return $state.current.name;
        //}
    }


}(angular));