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
			<tr ng-repeat="rec in ${prop.name}s">
			<td>
				<input type="radio" name="choose" ng-model="$parent.__chosen" ng-value="rec">
			</td>
			<#list propClass.properties as property>
			<#if property.upper == 1 && property.primitive>
				<td>{{rec.${property.name}}}</td>
			</#if>
			</#list></tr>
		</table>

	</div>
	<div class="modal-footer">
		<button class="btn btn-primary" type="button" ng-click="save()">OK</button>
		<button class="btn btn-warning" type="button" ng-click="cancel()">Cancel</button>
	</div>
</div>