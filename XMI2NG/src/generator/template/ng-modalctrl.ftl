(function (angular) {
	var ${class.lowerName}sModalControllerModule = angular.module('app.${class.name}.modalController', 
    	['app.${class.name}.resource']);
    	
    var ${class.lowerName}sModalController = ['$scope', '$uibModalInstance', '_rec', //, '$stateParams'  
    	function ($scope, $uibModalInstance, _rec) { //, $stateParams
        	
        		$scope.init = function(rec) {
        			<#list properties as property>
        			<#if property.upper == 1 && property.primitive> 
        				$scope.${property.name} = rec.${property.name};
        			</#if>
        			</#list>
        		}
        		
        		$scope.save = function() {
        			var ret = $scope.validate();
        			console.log('saving...');
        			console.log(ret);
        			$uibModalInstance.close(ret);
        		}
        		
        		$scope.cancel = function() {
        			console.log($scope.selRuk);
        			console.log('cancel...');
        			$uibModalInstance.dismiss('cancel');
        		}
        		
        		$scope.validate = function() {
        			var _x = {}
        			
        			<#list properties as property>
        			<#if property.upper == 1 && property.primitive> 
        				_x.${property.name} = $scope.${property.name};
        			</#if>
        			</#list>
        			
        			return _x;
        		}
        		
        		if (_rec) $scope.init(_rec);
        }];
        
        ${class.lowerName}sModalControllerModule.controller('${class.name}sModalCtrl', ${class.lowerName}sModalController);
}(angular));