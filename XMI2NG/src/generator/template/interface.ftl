// DO NOT CHANGE THIS CODE
// TEMPLATE interface.ftl
// AUTOMATICALLY GENERATED MODEL FOR ${interface.name}
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace WebApplication1.Models
{
	public interface ${interface.name}<#if interface.parents?has_content> : <#list interface.parents as i>${i}<#sep>, </#list></#if>
	{  
	<#list methods as method>
		${method.visibility} <#if method.static>static </#if>${method.returnType} ${method.name}(<#list method.parameters as parameter><#if parameter_index != 0>, </#if><#if parameter.ref>ref </#if><#if parameter.out>out </#if>${parameter.type} ${parameter.name}</#list>);
	</#list>
	}
}