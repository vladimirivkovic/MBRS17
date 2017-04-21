

<div>
	<div class="modal-header">
		<h3 class="modal-title" id="modal-title">New/Edit ${class.name}</h3>
	</div>
	<div class="modal-body" id="modal-body">
		
		<table class="table">
			<#list properties as property>
			<#if (property.upper == 1) && property.primitive> 
			<tr>
				<td>${property.originName}</td>
				<td><input type="text" class="form-control"
					ng-model="${property.name}"></td>
			</tr>
			</#if>
			<#if (property.upper == 1) && !property.primitive> 
			<tr>
				<td>${property.originName}</td>
				<td><button class="btn btn-default" type="button" ng-click="${property.name}Choose()">Choose...</button></td>
			</tr>
			</#if>
			</#list>
		</table>

	</div>
	<div class="modal-footer">
		<button class="btn btn-primary" type="button" ng-click="save()">OK</button>
		<button class="btn btn-warning" type="button" ng-click="cancel()">Cancel</button>
	</div>
</div>