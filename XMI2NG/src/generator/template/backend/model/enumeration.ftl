/*

######## DO NOT CHANGE THIS CODE! ########
AUTOMATICALLY GENERATED ENUMERATION FOR -- ${enumeration.name} -- 

BASED ON TEMPLATE -- enumeration.ftl --

GENERATED ON -- ${.now} --

*/

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