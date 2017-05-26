(function (angular) {
    var app = angular.module('app', ['ui.bootstrap', 'ngRoute', // 'ui.router',//'login', 
    	<#list classes as class>'app.${class.name}', </#list>]);

	app.config(function ($routeProvider, $locationProvider, $httpProvider) {
        $routeProvider
        <#list classes as class>
        .when('/${class.lowerName}', {
	        templateUrl: '${root}${class.lowerName}/${class.lowerName}View.html',
	        controller: '${class.name}sCtrl'
	    })
        </#list>
        ;
    })

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

    function run($rootScope, $http, $location) { //, $state) {
    	$rootScope.host = 'http://mbrs17app.azurewebsites.net/';
    
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