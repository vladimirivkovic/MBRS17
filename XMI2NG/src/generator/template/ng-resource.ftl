(function (angular) {
    angular.module('app.${class.name}.resource', ['ngResource'])
	.factory('${class.name}', function ($resource) {
	    var ${class.lowerName} = $resource('api/${class.lowerName}s/:${class.lowerName}ID',
			{
			    ${class.lowerName}ID: '@${class.lowerName}ID'
			},
			{
			    update: { method: 'PUT' }
			}
        );
	    return ${class.lowerName};
	})
}(angular));