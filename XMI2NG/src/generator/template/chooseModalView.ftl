<div>
	<div class="modal-header">
		<h3 class="modal-title" id="modal-title">Choose ${prop.name}</h3>
	</div>
	<div class="modal-body" id="modal-body">
		
		<table class="table">
			<tr><th>&nbsp</th>
			<#list class.properties as property>
				<th>${property.originName}</th>
			</#list></tr>
			<tr>
			<td>
				<input type="radio" ng-model="color" value="red">
			</td>
			<#list class.properties as property>
				<td>o</td>
			</#list></tr>
			<tr><td>
				<input type="radio" ng-model="color" value="blue">
			</td>
			<#list class.properties as property>
				<td>o</td>
			</#list></tr>
			<tr><td>
				<input type="radio" ng-model="color" value="green">
			</td>
			<#list class.properties as property>
				<td>o</td>
			</#list></tr>
		</table>

	</div>
	<div class="modal-footer">
		<button class="btn btn-primary" type="button" ng-click="save()">OK</button>
		<button class="btn btn-warning" type="button" ng-click="cancel()">Cancel</button>
	</div>
</div>