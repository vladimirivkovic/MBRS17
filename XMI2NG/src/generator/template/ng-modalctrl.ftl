(function (angular) {
	var ${class.lowerName}sModalControllerModule = angular.module('app.${class.name}.modalController', 
    	['app.${class.name}.resource']);
    	
    var ${class.lowerName}sModalController = ['$scope', '$uibModalInstance', '_rec', 'copy', 'parent', 'parentType', '$uibModal', '${class.name}', //,'$stateParams'  
    	function ($scope, $uibModalInstance, _rec, copy, parent, parentType, $uibModal, ${class.name}) { //, $stateParams
        		<#list properties as property>
    			<#if (property.type == "date")> 
    				$scope.${property.name}Popup = { opened : false };
    			</#if>
    			</#list>
        	
        		$scope.parentType = parentType;
        	
        		$scope.init = function(rec) {
        			$scope._rec = _rec;
        		
        			<#list properties as property>
        			<#if property.upper == 1> 
        				$scope.${property.name} = rec.${property.name};
        			</#if>
        			</#list>
        		}
        		
        		$scope.save = function() {
        			var ret = $scope.validate();
        			console.log('saving...');
        			console.log(ret);
        			
        			if(!$scope._rec || copy) {
        				var newRecord = new ${class.name}();
        			
	        			<#list properties as property>
	        			<#if property.upper == 1>	
	        				<#if !property.primitive>
	        				newRecord.${property.name}_ID = $scope.${property.name}.Id;
	        				<#else>
	        				newRecord.${property.name} = $scope.${property.name};
	        				</#if>
	        			</#if>
	        			</#list>
	
		                newRecord.$save(
		                    function (response) {
		                        $uibModalInstance.close(response);
		                    },
		                    function (response) {
		                        $scope.message = response.data.message;
		                    }
		                );
		             } else {
		             	var newRecord = {};
		             
		             	<#list properties as property>
	        			<#if property.upper == 1>	
	        				<#if !property.primitive>
	        				newRecord.${property.name}_ID = $scope.${property.name}.Id;
	        				<#else>
	        				newRecord.${property.name} = $scope.${property.name};
	        				</#if>
	        			</#if>
	        			</#list>
	        			
	        			${class.name}.update({ Id: newRecord.Id }, newRecord,
		                    function (response) {
		                        $uibModalInstance.close(response);
		                    },
		                    function (response) {
		                        $scope.message = response.data.message;
		                    }
		                );
		             }
        			
        			
        		}
        		
        		$scope.cancel = function() {
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
        		
        		<#list properties as property>
    			<#if property.upper == 1 && !property.primitive>
    			if(parentType != '${property.name}' || _rec != null){
					$scope.${property.name}Choose = function () {
			            var modal${property.capName}Instance = $uibModal.open({
			                templateUrl: '${root}${class.lowerName}/modal/${property.name}ModalView.html',
			                controller: '${class.lowerName}__${property.name}ModalController',
			                resolve: {
					                _rec : function() {
				                		return $scope.${property.name};
				                }
			                }
			            });
			            
			            modal${property.capName}Instance.result.then(function (result) {
				            if (result !== 'No' && result !== 'Error') {
				            	$scope.${property.name}_ID = result.Id;
								$scope.${property.name} = result;
				            }
			        	});
			        }
			    }else{
			    	$scope.${property.name}_ID = parent.Id;
			    	$scope.${property.name} = parent;
			    }
			    
    			</#if>
    			</#list>
    			
    			$scope.open = function(p) {
				    $scope[p].opened = true;
				  };
        		
        		if (_rec) $scope.init(_rec);
        }];
        
        ${class.lowerName}sModalControllerModule.controller('${class.name}sModalCtrl', ${class.lowerName}sModalController);
}(angular));