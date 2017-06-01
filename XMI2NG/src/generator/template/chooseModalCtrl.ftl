(function (angular) {
	var ${class.lowerName}__${prop.name}ModalControllerModule = angular.module('app.${class.name}.${prop.name}ModalController', 
    	['app.${propClass.name}.resource']);
    	
    var ${class.lowerName}__${prop.name}ModalController = ['$scope', '$uibModalInstance', '_rec', '$uibModal', '${propClass.name}', //,'$stateParams'  
    	function ($scope, $uibModalInstance, _rec, $uibModal, ${propClass.name}) { //, $stateParams
        		$scope._rec = _rec;
        	
        		$scope.init = function() {
        			$scope.__rpp = 5;
		    		$scope.__total_items = 0;
		    		$scope.__cp = 1;
        		
        			var ${prop.name}s = ${propClass.name}.query(function () {
		                $scope.${prop.name}s = ${prop.name}s;
		                $scope.page_${prop.name}s = $scope.${prop.name}s.slice(0, $scope.__rpp);
                		$scope.__total_items = $scope.${prop.name}s.length;
		                
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