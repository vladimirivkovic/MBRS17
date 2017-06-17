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
    
    <#list methods as method>
    <#if method.transaction??>
    <div class="btn-group clearfix">
        <button type="button" ng-click="${method.name}Click()" class="btn btn-default">
            <i class="glyphicon glyphicon-calculator"></i>&nbsp;${method.originName}
        </button>
    </div>
    </#if>
    <#if method.report??>
    <div class="btn-group clearfix">
        <button type="button" ng-click="${method.name}Click()" class="btn btn-default">
            <i class="glyphicon glyphicon-note"></i>&nbsp;${method.originName}
        </button>
    </div>
    </#if>
    </#list>
    
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
	            <th>
	            	<#if property.type == "int" || property.type == "double">
	            	<label>
					<input type="radio" ng-change="onChangeSearchBy${property.name}()" ng-model="${property.name}_mode" value="lt" name="${property.name}_mode">
					    <
					  </label>
					  <label>
					<input type="radio" ng-change="onChangeSearchBy${property.name}()" ng-model="${property.name}_mode" value="eq" name="${property.name}_mode">
					    =
					  </label>
					  <label>
					<input type="radio" ng-change="onChangeSearchBy${property.name}()" ng-model="${property.name}_mode" value="gt" name="${property.name}_mode">
					    >
					  </label>
	            	<#elseif property.type == "Date">
	            	dat
	            	<#elseif property.type == "Boolean">
	            	&nbsp;
	            	<#else>
	            	<label>
					<input type="radio" ng-change="onChangeSearchBy${property.name}()" ng-model="${property.name}_mode" value="li" name="${property.name}_mode">
					    like
					  </label>
					  <label>
					<input type="radio" ng-change="onChangeSearchBy${property.name}()" ng-model="${property.name}_mode" value="eq" name="${property.name}_mode">
					    =
					  </label>
	            	</#if>
	            </th>
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
            <tr>
            <#list properties as property>
			<#if property.upper == 1> 
			<#if property.primitive>
				<#if property.uIProperty.searchable>
	            <th><input style="max-width: 200px" type="text" class="form-control"
					ng-model="__search.${property.name}" ng-change="onChangeSearchBy${property.name}()"></th>
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
            
            </p>
            <tr ng-click="select${class.name}($index)" 
            	ng-class="{active: $index === selectedIndex}"
            	ng-repeat="i in page_${class.lowerName}s">
                <#list properties as property>
				<#if property.upper == 1> 
				<#if property.FMEnumeration??>
				<td>{{i.${property.originName} | ${property.FMEnumeration.name}}}</td>
				<#elseif property.type == 'boolean'>
				<td>{{i.${property.name} | yesOrNo}}</td>
				<#elseif property.type == 'DateTime'>
				<td>{{i.${property.name} | date :  "d.M.y"}}</td>
				<#elseif property.primitive>
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
	
	<#assign tabCount = 0>
	<#list properties as prop>
		<#if prop.tab??> 
		<#assign tabCount++>
		</#if>	
	</#list>

	<#if (tabCount > 0)>
	<hr/>
		
	<div class="btn-group clearfix">
        <button type="button" ng-click="childModal(false, false)" class="btn btn-default" ng-disabled="!selected" ng-hide="hideCreate">
            <i class="glyphicon glyphicon-plus"></i>&nbsp;Create
        </button>
    </div>
    
    <div class="btn-group clearfix">
        <button type="button" ng-click="childModal(true, false)" class="btn btn-default" ng-disabled="selectedChildIndex == null" ng-hide="hideEdit">
            <i class="glyphicon glyphicon-edit"></i>&nbsp;Edit
        </button>
    </div>
    
    <div class="btn-group clearfix">
        <button type="button" ng-click="removeFromTab()" class="btn btn-default" ng-disabled="selectedChildIndex == null" ng-hide="hideDelete">
            <i class="glyphicon glyphicon-remove"></i>&nbsp;Delete
        </button>
    </div>
 
    <div class="btn-group clearfix">
        <button type="button" ng-click="childModal(true, true)" class="btn btn-default" ng-disabled="selectedChildIndex == null" ng-hide="hideCopy">
            <i class="glyphicon glyphicon-copy"></i>&nbsp;Copy
        </button>
    </div>
    </#if>
	
	<uib-tabset style="margin-top: 15px;">
		<#list properties as property>
		<#if property.tab??> 
    	<uib-tab index="${property.FMClass.lowerName}" heading="${property.originName}"  select="tabSelection('${property.FMClass.lowerName}', '${property.FMClass.name}', '${property.name}')">
    		<table class="table table-hover">
	            <tr>
	            <#list property.FMClass.properties as property>
				<#if property.upper == 1 && property.primitive> 
	            	<th>${property.originName}</th>
	            </#if>
	            </#list>
	            </tr>
	            <tr ng-click="selectChild($index)" 
	            	ng-class="{active: $index === selectedChildIndex}"
	            	ng-repeat="i in ${property.name}_tab">
	                <#list property.FMClass.properties as property>
					<#if property.upper == 1 && property.primitive> 
					<#if property.FMEnumeration??>
					<td>{{i.${property.originName} | ${property.FMEnumeration.name}}}</td>
					<#elseif property.type == 'boolean'>
					<td>{{i.${property.name} | yesOrNo}}</td>
					<#elseif property.type == 'date'>
					<td>{{i.${property.name} | date :  "d.M.y"}}</td>
					<#elseif property.primitive>
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
    	</uib-tab>
    	</#if>
	    </#list>
  	</uib-tabset>
  	
  	<div style="clear: both; margin-top:100px"></div>
</div>