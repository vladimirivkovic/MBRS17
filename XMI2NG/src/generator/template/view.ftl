@model ${className}

@{
    ViewBag.Title = "Kreiranje necega";
}

<h2>Kreiranje</h2>

@using (Html.BeginForm()) 
{
    @Html.AntiForgeryToken()
    
    <div class="form-horizontal">
        <h4 style="padding-left: 3%">Mašina</h4>
        <hr />
		@Html.ValidationSummary(true, "", new { @class = "text-danger" })
		
		<#list properties as property>
		
		<div class="form-group">
            @Html.LabelFor(model => model.${property.name}, htmlAttributes: new { @class = "control-label col-md-2" })
            <div class="col-md-10">
            	<#-- TODO : check foreign key -->
                @Html.EditorFor(model => model.${property.name}, new { htmlAttributes = new { @class = "form-control" } })
                @Html.ValidationMessageFor(model => model.${property.name}, "", new { @class = "text-danger" })
            </div>
		</div>
		
		</#list>

		<div class="form-group">
            <div class="col-md-offset-2 col-md-10">
                <input type="submit" value="Kreiraj" class="btn btn-default" />
            </div>
        </div>
    </div>
}

<div>
    @Html.ActionLink("Odustani", "Index")
</div>

@section Scripts {
    @Scripts.Render("~/bundles/jqueryval")
}