using System;

namespace WebApplication1.Models
{
	public enum ${enumeration.name}
	{
		<#list enumeration.literals as literal>
			${literal}<#sep>,
		</#list>
	
	};
}