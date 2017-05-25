<div class="users">
	<style>
		.sortorder:after {
		  content: '\25b2';   // BLACK UP-POINTING TRIANGLE
		}
		.sortorder.reverse:after {
		  content: '\25bc';   // BLACK DOWN-POINTING TRIANGLE
		}
	</style>
    <h1>${class.originName}</h1>
    <#if class.UIClass??>
    <#if class.UIClass.create>
    <div class="btn-group clearfix">
        <button type="button" ng-click="openModal(false, false)" class="btn btn-default">
            <i class="glyphicon glyphicon-plus"></i>&nbsp;Create
        </button>
    </div>
    </#if>
    <#if class.UIClass.update>
    <div class="btn-group clearfix">
        <button type="button" ng-click="openModal(true, false)" class="btn btn-default" ng-disabled="!selected">
            <i class="glyphicon glyphicon-edit"></i>&nbsp;Edit
        </button>
    </div>
    </#if>
    <#if class.UIClass.delete>
    <div class="btn-group clearfix">
        <button type="button" ng-click="remove()" class="btn btn-default" ng-disabled="!selected">
            <i class="glyphicon glyphicon-remove"></i>&nbsp;Delete
        </button>
    </div>
    </#if>
    <#if class.UIClass.copy>
    <div class="btn-group clearfix">
        <button type="button" ng-click="openModal(true, true)" class="btn btn-default" ng-disabled="!selected">
            <i class="glyphicon glyphicon-copy"></i>&nbsp;Copy
        </button>
    </div>
    </#if>
    </#if>
    
    <div>
        <table class="table table-hover">
            <tr>
            <#list properties as property>
			<#if property.upper == 1> 
			<#if property.primitive>
	            <th> 
	            <p ng-click="sortBy('${property.name}')">
	            ${property.originName}
	            <span class="sortorder" ng-show="propertyName === '${property.name}'" ng-class="{reverse: reverse}"></span>
	            </p></th>
	        <#else>
	            <#list property.FMClass.lookupProperties as lp>
	            <th>${property.originName} -> ${lp.originName}</th>
	        </#list>
	        </#if>
            	
            </#if>
            </#list>
            </tr>
            <tr>
            <#list properties as property>
			<#if property.upper == 1> 
			<#if property.primitive>
				<#if property.uIProperty.searchable>
	            <th><input style="max-width: 200px" type="text" class="form-control"
					ng-model="__search.${property.name}"></th>
	            <#else>
	            <th>&nbsp;</th>
	            </#if>
	        <#else>
	            <#list property.FMClass.lookupProperties as lp>
	            <th>&nbsp;</th>
	            </#list>
	        </#if>
            </#if>
            </#list>
            </tr>
            <p ng-show="false">
            {{ page_${class.lowerName}s = (${class.lowerName}s | filter:__search:strict).slice((__cp-1)*__rpp,  __cp*__rpp)}}
            {{ __total_items = (${class.lowerName}s | filter:__search:strict).length }}
            </p>
            <tr ng-click="select${class.name}($index)" 
            	ng-class="{active: $index === selectedIndex}"
            	ng-repeat="i in page_${class.lowerName}s | filter:__search:strict">
                <#list properties as property>
				<#if property.upper == 1> 
				<#if property.primitive>
	            <td>{{i.${property.name}}}</td>
	            <#else>
	            <#list property.FMClass.lookupProperties as lp>
	            <td>{{i.${property.name}.${lp.name}}}</td>
	            </#list>
	            </#if>
	            </#if>
	            </#list>
            </tr>
        </table>
        <ul uib-pagination boundary-links="true" items-per-page="__rpp" 
        total-items="__total_items" ng-model="__cp" ng-change="pageChanged()"
        previous-text="&lsaquo;" next-text="&rsaquo;" first-text="&laquo;" last-text="&raquo;"></ul>
	</div>
	
	<hr/>
	
	<uib-tabset>
		<#list properties as property>
		<#if property.tab??> 
    	<uib-tab index="0" heading="${property.originName}">
    		<table class="table table-hover">
	            <tr>
	            <#list property.FMClass.properties as property>
				<#if property.upper == 1 && property.primitive> 
	            	<th>${property.originName}</th>
	            </#if>
	            </#list>
	            </tr>
	            <tr //ng-click="select${property.name}($index)" 
	            	//ng-class="{active: $index === ${property.name}selectedIndex}"
	            	ng-repeat="i in ${property.name}_tab">
	                <#list property.FMClass.properties as property>
					<#if property.upper == 1 && property.primitive> 
		            <td>{{i.${property.name}}}</td>
		            </#if>
		            </#list>
	            </tr>
	        </table> 
    	</uib-tab>
    	</#if>
	    </#list>
  	</uib-tabset>
</div>