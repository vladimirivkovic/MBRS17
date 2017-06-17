(function (angular) {
    angular.module('app.${class.name}', [
	    'app.${class.name}.controller',
        'app.${class.name}.modalController',
        <#list properties as property>
        <#if property.upper == 1 && !property.primitive>
        'app.${class.name}.${property.name}ModalController',	
        </#if>
        </#list>

    ]);
}(angular));