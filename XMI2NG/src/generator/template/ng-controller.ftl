(function (angular) {
    var ${class.lowerName}sControllerModule = angular.module('app.${class.name}.controller', 
    	['app.${class.name}.resource']);

    var ${class.lowerName}sController = ['$scope', '${class.name}', '$uibModal', //, '$stateParams'  
    	function ($scope, ${class.name}, $uibModal) { //, $stateParams
    	
    	$scope.${class.lowerName}s = [];

        $scope.selected = null;
        $scope.selectedIndex = null;

        $scope.unselect = function () {
        	$scope.selected = null;
        	$scope.selectedIndex = null;
		}

        $scope.select${class.name} = function (index) {
            if (index != $scope.selectedIndex) {
                $scope.selected = $scope.${class.lowerName}s[index];
                $scope.selectedIndex = index;
            } else {
                $scope.unselect();
            }
        }

		$scope.openModal = function (update) {
	        var modalInstance = $uibModal.open({
	            templateUrl: 'app/${class.lowerName}/${class.lowerName}ModalView.html',
	            controller: '${class.name}sModalCtrl',
	            resolve: {
	                _rec : function() {
	                	return update ? $scope.selected : null;
	                }
	            }
			});
	        modalInstance.result.then(function (result) {
	            if (result !== 'No' && result !== 'Error') {
	                if (!update) {
	                    $scope.${class.lowerName}s.push(result);
	                } else {
	                    $scope.${class.lowerName}s[$scope.selectedIndex] = result;
	                }
	            }
	        }); 
        }
	<#list properties as property>
	<#if (property.upper == -1 || property.upper < 1) && !property.primitive> 
		$scope.open${property.capName}sModal = function (${class.lowerName}) {
            var modal${property.capName}Instance = $uibModal.open({
                templateUrl: 'app/${class.lowerName}/modal/${property.name}Modal.html',
                controller: '${property.name}ModalController',
                resolve: {
                    ${class.lowerName} : ${class.lowerName}
                }
            });
            
            modal${property.capName}Instance.result.then(function (result) {
	            if (result !== 'No' && result !== 'Error') {
					// TODO : implement
	            }
        	});
        }
	</#if>
	</#list>
        
        $scope.init = function () {
            var ${class.lowerName}s = ${class.name}.query(function () {
                $scope.${class.lowerName}s = ${class.lowerName}s;
            });
        }

        $scope.status = {
            isopen: false
        };
        
		$scope.init();
    }];
    ${class.lowerName}sControllerModule.controller('${class.name}sCtrl', ${class.lowerName}sController);
}(angular));