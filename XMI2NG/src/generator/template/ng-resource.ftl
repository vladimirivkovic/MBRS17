(function (angular) {
    angular.module('app.${class.name}.resource', ['ngResource'])
	.factory('${class.name}', function ($resource, $rootScope) {
	    var ${class.name} = $resource($rootScope.host + 'api/${class.name}/:Id',
			{
			    Id : '@Id'
			},
			{
			    update: { method: 'PUT' }
			}
        );
	    return ${class.name};
	})
}(angular));