(function (angular) {
	var ${class.lowerName}__${prop.name}ModalControllerModule = angular.module('app.${class.name}.${prop.name}ModalController', 
    	['app.${propClass.name}.resource']);
    	
    var ${class.lowerName}__${prop.name}ModalController = ['$scope', '$uibModalInstance', '_rec', '$uibModal', '${propClass.name}', //,'$stateParams'  
    	function ($scope, $uibModalInstance, _rec, $uibModal, ${propClass.name}) { //, $stateParams
        	
        		$scope.init = function() {
        			var ${prop.name}s = ${propClass.name}.query(function () {
		                $scope.${prop.name}s = ${prop.name}s;
		                
		                $scope._chosen = null;
		                
		                if(_rec)
		                	for(i in $scope.${prop.name}s) {
								if(_rec.Id === 	$scope.${prop.name}s[i].Id) {
									$scope._chosen = $scope.${prop.name}s[i];
								}	                	
		                	}
		            });
		            
        		}
        		
        		$scope.save = function() {
        			console.log('choosing...');
        			console.log($scope.__chosen);

	                $uibModalInstance.close($scope.__chosen);			   			
        		}
        		
        		$scope.cancel = function() {
        			console.log('cancel...');
        			$uibModalInstance.dismiss('cancel');
        		}
        		
        		$scope.init();
        }];
        
        ${class.lowerName}__${prop.name}ModalControllerModule
        	.controller('${class.lowerName}__${prop.name}ModalController', 
        				 ${class.lowerName}__${prop.name}ModalController);
}(angular));