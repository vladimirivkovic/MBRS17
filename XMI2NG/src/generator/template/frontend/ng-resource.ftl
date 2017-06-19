(function (angular) {
    angular.module('app.${class.name}.resource', ['ngResource'])
	.factory('${class.name}', function ($resource, $rootScope) {
	    var ${class.name} = $resource($rootScope.host + 'odata/${class.name}/:Id',
			{
			    Id : '@Id'
			},
			{
				query: {method: 'GET', isArray: false, params: {'$expand' : '<#list properties as property><#if property.getFMClass()??>${property.name},</#if></#list>'} },
			    update: { method: 'PUT', url : $rootScope.host + 'odata/${class.name}(:Id)' }
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
