// DO NOT CHANGE THIS CODE
// TEMPLATE ng-controller.ftl
// AUTOMATICALLY GENERATED MODEL FOR ${class.name}
(function (angular) {
    var ${class.lowerName}sControllerModule = angular.module('app.${class.name}.controller', 
    	['app.${class.name}.resource']);

    var ${class.lowerName}sController = ['$scope', '${class.name}', '$uibModal', '$filter',//, '$stateParams'
    	<#list properties as property>
		<#if property.tab??>'${property.FMClass.name}',</#if>
    	</#list>
    	'AuthenticationService','$window',
    	function ($scope, ${class.name}, $uibModal, $filter,
    	<#list properties as property>
		<#if property.tab??>${property.FMClass.name},</#if>
    	</#list>
    	AuthenticationService,$window,
    	
    	) { //, $stateParams
    	if (AuthenticationService.getCurrentUser()==null)
        {
          $window.location.href="#/login"
          return;
        }
    	$scope.${class.lowerName}s = [];

        $scope.selected = null;
        $scope.selectedIndex = null;
        $scope.selectedTabLower = null;
        $scope.selectedTab = null;
        
        $scope.selectedChildIndex = null;
        
        $scope.tabSelection = function(lowerName, name, originName) {
   			$scope.selectedTabLower = lowerName;
   			$scope.selectedTab = name;
   			$scope.selectedTabOriginName = originName;
   			
   			if ($scope.edits.indexOf(originName) > -1){
   				$scope.hideEdit = false;
   			}else{
   				$scope.hideEdit = true;
   			}
   			
   			if ($scope.creates.indexOf(originName) > -1){
   				$scope.hideCreate = false;
   			}else{
   				$scope.hideCreate = true;
   			}
   			
   			if ($scope.copys.indexOf(originName) > -1){
   				$scope.hideCopy = false;
   			}else{
   				$scope.hideCopy = true;
   			}
   			
   			if ($scope.deletes.indexOf(originName) > -1){
   				$scope.hideDelete = false;
   			}else{
   				$scope.hideDelete = true;
   			}
 		};

        $scope.unselect = function () {
        	$scope.selected = null;
        	$scope.selectedIndex = null;
		}

        $scope.select${class.name} = function (index) {
            if (index != $scope.selectedIndex) {
                $scope.selected = $scope.page_${class.lowerName}s[index];
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
        
        $scope.selectChild = function(index){
        	if(index != $scope.selectedChildIndex){
        		$scope.selectedChildIndex = index;
        	}else{
        		$scope.selectedChildIndex = null;
        	}
        }
        
        $scope.childModal = function(update, copy){
			var templateUrl = '${root}' + $scope.selectedTabLower + '/' + $scope.selectedTabLower + 'ModalView.html';
			var ctrl = $scope.selectedTab + 'sModalCtrl';
			var selectedIndex = $scope.selectedIndex;
			
			var tabRecords = $scope.$eval($scope.selectedTabOriginName + "_tab");
			
			if (selectedIndex == null)
				return;

        	var modalInstance = $uibModal.open({
	            templateUrl: templateUrl,
	            controller: ctrl,
	            resolve: {
	                _rec : function() {
	                	return update ? tabRecords[$scope.selectedChildIndex] : null;
	                },
	                copy : function() {
	                	return copy;
	                },
	                parent : function() {
	                	return $scope.selected;
	                },
	                parentType : function(){
	                	return '${class.name}';
	                }
	            }
			});
	        modalInstance.result.then(function (result) {
	            if (result !== 'No' && result !== 'Error') {
	                $scope.unselect();
	                $scope.select${class.name}(selectedIndex);
	            }
	        }); 
        }

		$scope.openModal = function (update, copy) {
	        var modalInstance = $uibModal.open({
	            templateUrl: '${root}${class.lowerName}/${class.lowerName}ModalView.html',
	            controller: '${class.name}sModalCtrl',
	            resolve: {
	                _rec : function() {
	                	return update ? $scope.selected : null;
	                },
	                copy : function() {
	                	return copy;
	                },
	                parent : function() {
	                	return null;
	                },
	                parentType : function(){
	                	return null;
	                }
	            }
			});
	        modalInstance.result.then(function (result) {
	            if (result !== 'No' && result !== 'Error') {
/*
	                if (!update) {
	                    $scope.${class.lowerName}s.push(result);
	                } else if (copy) {
	                    $scope.${class.lowerName}s.push(result);
	                } else {
	                	$scope.${class.lowerName}s[$scope.selectedIndex] = result;
	                }
*/
					$scope.init(false);
					$scope.openModal();
	            }
	        }); 
        }
        
        $scope.remove = function() {
        	//console.log($scope.selectedIndex);
        	${class.name}.delete({Id: $scope.selected.Id},
	        	function() { 
		        	$scope.selected = null;
		        	$scope.selectedIndex = null;
		        	$scope.init(false);
        	})
        }
        
        $scope.removeFromTab = function(){
        	var tabRecords = $scope.$eval($scope.selectedTabOriginName + "_tab");
        	eval($scope.selectedTab).delete({Id: tabRecords[$scope.selectedChildIndex].Id},
	        	function() { 
	        		var selectedIndex = $scope.selectedIndex;
		        	$scope.unselect();
	                $scope.selectPreduzece(selectedIndex);
        	})
        }
          
        $scope.init = function (reset) {
        	$scope.__rpp = 5;
    		$scope.__total_items = 0;
    		if (reset) $scope.__cp = 1;
        
            var ${class.lowerName}s = ${class.name}.query(function () {
                $scope.${class.lowerName}s = ${class.lowerName}s;
                $scope.page_${class.lowerName}s = $scope.${class.lowerName}s.slice(0, $scope.__rpp);
                $scope.__total_items = $scope.${class.lowerName}s.length;
            });
            
            $scope.edits = [];
            $scope.creates = [];
            $scope.copys = [];
            $scope.deletes = [];
            
            <#list properties as property>
			<#if property.tab??>
			$scope.${property.name}_tab = []
			<#if property.FMClass.UIClass.create>
			$scope.creates.push('${property.name}');
			</#if>
			<#if property.FMClass.UIClass.update>
			$scope.edits.push('${property.name}');
			</#if>
			<#if property.FMClass.UIClass.delete>
			$scope.deletes.push('${property.name}');
			</#if>
			<#if property.FMClass.UIClass.copy>
			$scope.copys.push('${property.name}');
			</#if>

			</#if>
	    	</#list>
			
        }
        
        $scope.pageChanged = function() {
        	//$scope.page_${class.lowerName}s = 
        	//$scope.${class.lowerName}s.slice(($scope.__cp-1)*$scope.__rpp,  $scope.__cp*$scope.__rpp);
        }

        $scope.status = {
            isopen: false
        };
        
        $scope.sortBy = function(propertyName) {
		    $scope.reverse = (propertyName !== null && $scope.propertyName === propertyName)
		        ? !$scope.reverse : false;
		    $scope.propertyName = propertyName;
		    $scope.${class.lowerName}s = $filter('orderBy')($scope.${class.lowerName}s, $scope.propertyName, $scope.reverse);
		  };
        
		$scope.init(true);
		
		<#list methods as method>
		$scope.${method.name}Click = function() {
			// USER CODE STARTS HERE
<#if userCode[method.name]??>${userCode[method.name]}</#if>
			// USER CODE ENDS HERE
		}
		</#list>
    }];
    ${class.lowerName}sControllerModule.controller('${class.name}sCtrl', ${class.lowerName}sController);
}(angular));