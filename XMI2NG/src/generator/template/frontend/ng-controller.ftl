// DO NOT CHANGE THIS CODE
// TEMPLATE ng-controller.ftl
// AUTOMATICALLY GENERATED MODEL FOR ${class.name}
(function (angular) {
    var ${class.lowerName}sControllerModule = angular.module('app.${class.name}.controller', 
    	['app.${class.name}.resource']);

    var ${class.lowerName}sController = ['$scope', '${class.name}', '$uibModal', '$filter', '$location',//, '$stateParams'
    	<#list properties as property><#if property.tab??>'${property.FMClass.name}',</#if></#list>
    	'AuthenticationService','$window',
    	function ($scope, ${class.name}, $uibModal, $filter, $location,
    	<#list properties as property><#if property.tab??>${property.FMClass.name},</#if></#list>
    	AuthenticationService,$window) { //, $stateParams
    	
    	if (AuthenticationService.getCurrentUser()==null)
        {
          console.log("Not logged in!");
          $window.location.href="#/login"
          return;
        }
    	$scope.${class.lowerName}s = [];

        $scope.selected = null;
        $scope.selectedIndex = null;
        $scope.selectedTabLower = null;
        $scope.selectedTab = null;
        
        $scope.selectedChildIndex = null;
        
        <#list properties as property>
		<#if (property.type == "DateTime" && property.uIProperty.searchable)> 
			$scope.${property.name}Popup = { opened : false };
			$scope.${property.name}Popup2 = { opened : false };
		</#if>
		</#list>
        
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
		
		$scope.open = function(p) {
	    	$scope[p].opened = true;
	  	};
		
		$scope.updateFilter = function () {
			$scope.filter = "";
			<#list properties as property>
			<#if property.upper == 1> 
			<#if property.primitive>
			<#if property.uIProperty.searchable>
			if ($scope.filter_${property.name}) $scope.filter += ($scope.filter ? " and " : "" ) + $scope.filter_${property.name};
			</#if>
			</#if>
			</#if>
			</#list>
		}
		
		$scope.__search = {};
		<#list properties as property>
		<#if property.uIProperty.searchable>

		$scope.__search.${property.name} = "";
		if ("${property.type}" == "String") {$scope.${property.name}_mode = "li";} else {$scope.${property.name}_mode = "lt";}
		$scope.onChangeSearchBy${property.name} = function(fieldType){
			$scope.__cp = 1;
			if (fieldType == "String") {
				switch ($scope.${property.name}_mode) {
				case "li":
					$scope.filter_${property.name} = "substringof('" + $scope.__search.${property.name} + "', ${property.name} )";
	           		break;
	            case "starts":
	            	$scope.filter_${property.name} = "startswith(${property.name}, '" + $scope.__search.${property.name} + "')";
	            	break;
	            case "ends":
	            	$scope.filter_${property.name} = "endswith(${property.name}, '" + $scope.__search.${property.name} + "')";
	            	break;
	            default:
	            	$scope.filter_${property.name} = "'" + $scope.__search.${property.name} + "' eq ${property.name}";
	            }
	        } else if (fieldType == "int" || fieldType == "double") {
	        	if ($scope.${property.name}_mode == "between") {
	        		$scope.filter_${property.name} = "${property.name} gt " + Number($scope.__search.${property.name});
	        		if ($scope.__search.${property.name}_2) 
	        			$scope.filter_${property.name} += " and ${property.name} lt " + Number($scope.__search.${property.name}_2);
	        	} else {
	        		$scope.filter_${property.name} = "${property.name} " + $scope.${property.name}_mode + " " + Number($scope.__search.${property.name});
	        	}
	        } else if (fieldType == "DateTime") {
	        	if ($scope.${property.name}_mode == "between") {
	        		$scope.filter_${property.name} = "${property.name} gt datetime'" + 
	        			$scope.__search.${property.name}.addHours(2).toISOString().substring(0,10) + "'";
	        		if ($scope.__search.${property.name}_2) 
	        			$scope.filter_${property.name} += " and ${property.name} lt datetime'" + 
	        				$scope.__search.${property.name}_2.addHours(2).toISOString().substring(0,10) + "'";
	        	} else {
	        		$scope.filter_${property.name} = "${property.name} " + $scope.${property.name}_mode + " datetime'" + 
	        			$scope.__search.${property.name}.addHours(2).toISOString().substring(0,10) + "'";
	        	}
	        }
	        
	        if (!$scope.__search.${property.name}) $scope.filter_${property.name} = "";
            
            $scope.updateFilter();
            $scope.pageChanged();
		}
		</#if>
		</#list>

        $scope.select${class.name} = function (index) {
            if (index != $scope.selectedIndex) {
                $scope.selected = $scope.page_${class.lowerName}s[index];
                $scope.selectedIndex = index;
                
                <#list properties as property>
				<#if property.tab??>
				$scope.${property.name}_tab = []
				$scope.${property.name}_all = ${property.FMClass.name}.query(function () {
					for(i in $scope.${property.name}_all.value) {
						if($scope.${property.name}_all.value[i].${property.inverseProperty.name}_ID 
								== $scope.selected.Id)
	                		$scope.${property.name}_tab.push($scope.${property.name}_all.value[i]);
	                }
	            });
				</#if>
		    	</#list>
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
					$scope.pageChanged();
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
		        	$scope.pageChanged();
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
        	$scope.filter = "";
        	<#list properties as property>
			<#if property.upper == 1> 
			<#if property.primitive>
			<#if property.uIProperty.searchable>
			$scope.filter_${property.name} = "";
			</#if>
			</#if>
			</#if>
			</#list>
        	$scope.__rpp = ${class.UIClass.rowsPerPage};
    		$scope.__total_items = 0;
    		if (reset) $scope.__cp = 1;
            
            var ${class.lowerName}s = ${class.name}.query({'$top': $scope.__rpp, '$inlinecount': 'allpages'}, function () {
            	console.log(${class.lowerName}s);
	            $scope.${class.lowerName}s = ${class.lowerName}s.value;
	            $scope.page_${class.lowerName}s = $scope.${class.lowerName}s;
	            $scope.__total_items = ${class.lowerName}s['odata.count'];
            }, function(response) {
            	if (response.status === 401)
            		$location.path("login");
            	else 
            		alert("Error " + response.status);
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
        
        	if ($scope.filter != ""){
        		var ${class.lowerName}s = ${class.name}.query({'$filter': $scope.filter, '$skip': ($scope.__cp-1)*$scope.__rpp ,'$top': $scope.__rpp, '$inlinecount': 'allpages'}, function () {
	           
		            $scope.${class.lowerName}s = ${class.lowerName}s.value;
		            $scope.page_${class.lowerName}s = $scope.${class.lowerName}s;
		            $scope.__total_items = ${class.lowerName}s['odata.count'];
	            }, function(response) {
	            	if (response.status === 401)
	            		$location.path("login");
	            	else 
	            		alert("Error " + response.status);
	            });
        	}else{
        		var ${class.lowerName}s = ${class.name}.query({'$skip': ($scope.__cp-1)*$scope.__rpp ,'$top': $scope.__rpp, '$inlinecount': 'allpages'}, function () {
           
		            $scope.${class.lowerName}s = ${class.lowerName}s.value;
		            $scope.page_${class.lowerName}s = $scope.${class.lowerName}s;
		            $scope.__total_items = ${class.lowerName}s['odata.count'];
	            }, function(response) {
	            	if (response.status === 401)
	            		$location.path("login");
	            	else 
	            		alert("Error " + response.status);
	            });
        	
        	}
        	
        }

        $scope.status = {
            isopen: false
        };
        
        $scope.sortBy = function(propertyName) {
		    $scope.reverse = (propertyName !== null && $scope.propertyName === propertyName)
		        ? !$scope.reverse : false;
		    $scope.propertyName = propertyName;
		    var sortType = $scope.reverse ? " desc" : " asc"; 
		    
		    if ($scope.filter != ""){
		    	var ${class.lowerName}s = ${class.name}.query({'$filter': $scope.filter, '$skip': ($scope.__cp-1)*$scope.__rpp ,'$top': $scope.__rpp, '$inlinecount': 'allpages', '$orderby': $scope.propertyName + sortType}, function () {
	            	console.log(${class.lowerName}s);
		            $scope.${class.lowerName}s = ${class.lowerName}s.value;
		            $scope.page_${class.lowerName}s = $scope.${class.lowerName}s;
		            $scope.__total_items = ${class.lowerName}s['odata.count'];
	            }, function(response) {
	            	if (response.status === 401)
	            		$location.path("login");
	            	else 
	            		alert("Error " + response.status);
	            });
		    } else {
			    var ${class.lowerName}s = ${class.name}.query({'$skip': ($scope.__cp-1)*$scope.__rpp ,'$top': $scope.__rpp, '$inlinecount': 'allpages', '$orderby': $scope.propertyName + sortType}, function () {
	            	console.log(${class.lowerName}s);
		            $scope.${class.lowerName}s = ${class.lowerName}s.value;
		            $scope.page_${class.lowerName}s = $scope.${class.lowerName}s;
		            $scope.__total_items = ${class.lowerName}s['odata.count'];
	            }, function(response) {
	            	if (response.status === 401)
	            		$location.path("login");
	            	else 
	            		alert("Error " + response.status);
	            });
	        }
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