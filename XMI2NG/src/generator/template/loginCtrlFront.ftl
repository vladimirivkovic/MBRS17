(function (angular) {
    angular.module('app.LoginModule', ['app.AuthenticationModule','ngRoute'])
	.controller('LoginCtrl', function ($scope,AuthenticationService,$window) {
	    $scope.user = {};
	    //$scope.alert = null;
	    $scope.login = function () {
	      AuthenticationService.login($scope.user.username, $scope.user.password, loginCbck);
	    };

	    function loginCbck(success) {
	        if (success) {
            	console.log("success");
            	$window.location.href='/';
	        }
	        else {
	            $scope.alert = { msg: 'Invalid username or password', timeout: 3000 };
	            setTimeout(function () { $scope.alert = null; }, 3000);
              	$window.location.href='#/login';
	        }
	    }
      function logout()
      {
        AuthenticationService.logout();
      }
	});
}(angular));
