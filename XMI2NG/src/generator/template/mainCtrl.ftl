(function (angular) {
    angular.module('app.MainModule', ['app.AuthenticationModule'])
	   .controller('MainCtrl', function ($scope,AuthenticationService) {
       console.log("MainCtrl");
       $scope.getCurrentUser=function()
       {
         return AuthenticationService.getCurrentUser();
       }
       $scope.logout=function()
       {
         AuthenticationService.logout();
       }
	});
}(angular));
