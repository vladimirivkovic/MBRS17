(function (angular) {
	var ${class.lowerName}sModalControllerModule = angular.module('app.${class.name}.modalController', 
    	['app.${class.name}.resource']);
    	
    var ${class.lowerName}sModalController = ['$scope', '$uibModalInstance', '_rec', '$uibModal', '${class.name}', //,'$stateParams'  
    	function ($scope, $uibModalInstance, _rec, $uibModal, ${class.name}) { //, $stateParams
        		<#list properties as property>
    			<#if (property.type == "date")> 
    				$scope.${property.name}Popup = { opened : false };
    			</#if>
    			</#list>
        	
        	
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
        			
        			if(!$scope._rec) {
        				var newRecord = new ${class.name}();
        			
	        			<#list properties as property>
	        			<#if property.upper == 1> 
	        				newRecord.${property.name} = $scope.${property.name};
	        				<#if !property.primitive>
	        				newRecord.${property.name}_ID = $scope.${property.name}_ID;
	        				</#if>
	        			</#if>
	        			</#list>
	
		                newRecord.$save(
		                    function () {
		                        $uibModalInstance.close(ret);
		                    },
		                    function (response) {
		                        $scope.message = response.data.message;
		                    }
		                );
		             } else {
		             	var newRecord = {};
		             
		             	<#list properties as property>
	        			<#if property.upper == 1> 
	        				newRecord.${property.name} = $scope.${property.name};
	        				<#if !property.primitive>
	        				newRecord.${property.name}_ID = $scope.${property.name}.Id;
	        				</#if>
	        			</#if>
	        			</#list>
	        			
	        			${class.name}.update({ Id: newRecord.Id }, newRecord,
		                    function () {
		                        $uibModalInstance.close(newRecord);
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
				$scope.${property.name}Choose = function () {
		            var modal${property.capName}Instance = $uibModal.open({
		                templateUrl: 'app/${class.lowerName}/modal/${property.name}ModalView.html',
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
							return $scope.${property.name} = result;
			            }
		        	});
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