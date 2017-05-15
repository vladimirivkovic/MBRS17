using System;

package ${enumeration.namespace};

enum ${enumeration.name}
{
	<#list enumeration.literals as literal>
		${literal}<#sep>,
	</#list>
	
};