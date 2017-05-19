(function (angular) {
    var ${class.lowerName}sControllerModule = angular.module('app.${class.name}.controller', 
    	['app.${class.name}.resource']);

    var ${class.lowerName}sController = ['$scope', '${class.name}', '$uibModal', //, '$stateParams'
    	<#list properties as property>
		<#if property.tab??>'${property.FMClass.name}',</#if>
    	</#list>  
    	function ($scope, ${class.name}, $uibModal,
    	<#list properties as property>
		<#if property.tab??>${property.FMClass.name},</#if>
    	</#list>
    	) { //, $stateParams
    	
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
                
                <#list properties as property>
				<#if property.tab??>
				$scope.${property.name}_tab = []
				$scope.${property.name}_all = ${property.FMClass.name}.query(function () {
					for(i in $scope.${property.name}_all) {
						if($scope.${property.name}_all[i].${property.inverseProperty.name}_ID 
								== $scope.selected.Id)
	                		$scope.${property.name}_tab.push($scope.${property.name}_all[i]);
	                }
	            });
				</#if>
		    	</#list>
                
                // retrieve data for tabs
            } else {
                $scope.unselect();
            }
        }

		$scope.openModal = function (update, copy) {
	        var modalInstance = $uibModal.open({
	            templateUrl: 'app/${class.lowerName}/${class.lowerName}ModalView.html',
	            controller: '${class.name}sModalCtrl',
	            resolve: {
	                _rec : function() {
	                	return update ? $scope.selected : null;
	                },
	                copy : function() {
	                	return copy;
	                }
	            }
			});
	        modalInstance.result.then(function (result) {
	            if (result !== 'No' && result !== 'Error') {
	                if (!update) {
	                    $scope.${class.lowerName}s.push(result);
	                } else if (copy) {
	                    $scope.${class.lowerName}s.push(result);
	                } else {
	                	$scope.${class.lowerName}s[$scope.selectedIndex] = result;
	                }
	            }
	        }); 
        }
        
        $scope.remove = function() {
        	//console.log($scope.selectedIndex);
        	${class.name}.delete({Id: $scope.selected.Id},
	        	function() { 
		        	$scope.${class.lowerName}s.splice($scope.selectedIndex,1);
		        	$scope.selected = null;
		        	$scope.selectedIndex = null;
        	})
        }
        
        $scope.init = function () {
            var ${class.lowerName}s = ${class.name}.query(function () {
                $scope.${class.lowerName}s = ${class.lowerName}s;
            });
            
            <#list properties as property>
			<#if property.tab??>
			$scope.${property.name}_tab = []
			</#if>
	    	</#list>
			
        }

        $scope.status = {
            isopen: false
        };
        
		$scope.init();
    }];
    ${class.lowerName}sControllerModule.controller('${class.name}sCtrl', ${class.lowerName}sController);
}(angular));