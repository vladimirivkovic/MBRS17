

<div>
	<div class="modal-header">
		<h3 class="modal-title" id="modal-title">New/Edit ${class.originName}</h3>
	</div>
	<div class="modal-body" id="modal-body">
		
		<table class="table">
			<#list properties as property>
			<#if (property.upper == 1) && property.primitive> 
			<#if !(property.id)??>
			<#if property.FMEnumeration??>
			<#if (property.FMEnumeration.literals?size >= 3) >
			<tr>
				<td>${property.originName}</td>
				<td>
					<select class="form-control" ng-model="${property.name}"> 
						<#list property.FMEnumeration.literals as literal>
						<option value="${literal}">${literal}</option>
						</#list>
					</select>
				</td>
			</tr>
			<#else>
			<tr>
				<td>${property.originName}</td>
				<td>
					<#list property.FMEnumeration.literals as literal>
					<label class="radio-inline"><input type="radio" ng-model="${property.name}" value=${literal}>${literal}</label>
					</#list>
				</td>
			</tr>
			</#if>
			<#elseif (property.type == "date")>
			<tr>
				<td>${property.originName}</td>
				<td>
				<p class="input-group">
		          <input type="text" class="form-control" uib-datepicker-popup="{{'dd-MMMM-yyyy'}}" ng-model="${property.name}" is-open="${property.name}Popup.opened"  close-text="Close"/>
		          <span class="input-group-btn">
		            <button type="button" class="btn btn-default" ng-click="open('${property.name}Popup')"><i class="glyphicon glyphicon-calendar"></i></button>
		          </span>
		        </p>
		        </td>
			</tr>
			<#elseif (property.type == "int")>
			<tr>
				<td>${property.originName}</td>
				<td><input type="number" class="form-control"
					ng-model="${property.name}"></td>
			</tr>
			<#elseif (property.type == "boolean")>
			<tr>
				<td>${property.originName}</td>
				<td><input type="checkbox" ng-model="${property.name}"></td>
			</tr>
			<#else>
			<tr>
				<td>${property.originName}</td>
				<td><input type="text" class="form-control"
					ng-model="${property.name}"></td>
			</tr>
			</#if>
			</#if>
			</#if>
			<#if ((property.upper == 1) && property.FMClass??)> 
			<tr>
				<td>${property.originName}</td>
				<td><button ng-disabled="parentType == '${property.name}' && _rec == null" class="btn btn-default" type="button" ng-click="${property.name}Choose()">Choose...</button></td>
				<#list property.FMClass.lookupProperties as lp>
	            <td>{{${property.name}.${lp.name}}}</td>
	            </#list>
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