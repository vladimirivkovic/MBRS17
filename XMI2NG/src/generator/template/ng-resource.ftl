(function (angular) {
    angular.module('app.${class.name}.resource', ['ngResource'])
	.factory('${class.name}', function ($resource, $rootScope) {
	    var ${class.name} = $resource($rootScope.host + ':api' + '/${class.name}/:Id',
			{
			    Id : '@Id',
			    api: 'api'
			},
			{
			    update: { method: 'PUT' }
	    		<#list methods as method>
				,${method.name} : {
					url : $rootScope.host + 'api/${class.name}/:Id/${method.name}',
					method : <#if method.transaction??>'GET'<#else>'POST'</#if>
				}
				</#list>
			}
        );
	    return ${class.name};
	})
}(angular));