<div class="users">
    <h1>${class.name}s</h1>
    <div class="btn-group clearfix">
        <button type="button" ng-click="openModal(false)" class="btn btn-default">
            <i class="glyphicon glyphicon-plus"></i>Create
        </button>
    </div>
    <div class="btn-group clearfix">
        <button type="button" ng-click="openModal(true)" class="btn btn-default" ng-disabled="!selected">
            <i class="glyphicon glyphicon-edit"></i>Edit
        </button>
    </div>
    
    <div>
        <table class="table table-hover">
            <tr>
            <#list properties as property>
			<#if property.upper == 1> 
            	<th>${property.originName}</th>
            </#if>
            </#list>
            </tr>
            <tr ng-click="select${class.name}($index)" 
            	ng-class="{active: $index === selectedIndex}"
            	ng-repeat="i in ${class.lowerName}s">
                <#list properties as property>
				<#if property.upper == 1> 
	            <td>{{i.${property.name}}}</td>
	            </#if>
	            </#list>
            </tr>
        </table>
	</div>
	
	<p> A sada tabovi </p>
	
	<uib-tabset>
		<#list properties as property>
		<#if property.tab> 
    	<uib-tab index="0" heading="${property.name}">Table content</uib-tab>
    	</#if>
	    </#list>
  	</uib-tabset>
</div>