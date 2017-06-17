<div>
	<div class="modal-header">
		<h3 class="modal-title" id="modal-title">Choose ${prop.name}</h3>
	</div>
	<div class="modal-body" id="modal-body">
		<table class="table">
			<tr><th>&nbsp</th>
			<#list propClass.properties as property>
			<#if property.upper == 1 && property.primitive>
				<th>${property.originName}</th>
			</#if>
			</#list></tr>
			<tr ng-if="_rec"><th>Previously<br/>chosen</th>
			<#list propClass.properties as property>
			<#if property.upper == 1 && property.primitive>
				<th>{{_rec.${property.name}}}</th>
			</#if>
			</#list></tr>
			<tr><th>&nbsp</th>
			<#list propClass.properties as property>
			<#if property.upper == 1 && property.primitive>
				<#if property.uIProperty.searchable>
				 <th><input style="max-width: 200px" type="text" class="form-control"
					ng-model="__search.${property.name}"></th>
				<#else>
	            <th>&nbsp;</th>
	            </#if>
			</#if>
			</#list></tr>
			<p ng-show="false">
            {{ page_${prop.name}s = (${prop.name}s | filter:__search:strict).slice((__cp-1)*__rpp,  __cp*__rpp)}}
            {{ __total_items = (${prop.name}s | filter:__search:strict).length }}
            </p>
			<tr ng-repeat="rec in page_${prop.name}s">
			<td>
				<input type="radio" name="choose" ng-model="$parent.__chosen" ng-value="rec">
			</td>
			<#list propClass.properties as property>
			<#if property.upper == 1 && property.primitive>
				<td>{{rec.${property.name}}}</td>
			</#if>
			</#list></tr>
		</table>
		<ul uib-pagination boundary-links="true" items-per-page="__rpp" 
        total-items="__total_items" ng-model="__cp" ng-change="pageChanged()"
        previous-text="&lsaquo;" next-text="&rsaquo;" first-text="&laquo;" last-text="&raquo;"></ul>

	</div>
	<div class="modal-footer">
		<button class="btn btn-primary" type="button" ng-click="save()">OK</button>
		<button class="btn btn-warning" type="button" ng-click="cancel()">Cancel</button>
	</div>
</div>