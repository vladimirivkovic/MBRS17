using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ${class.namespace} {
	${class.visibility} class ${class.name}<#if class.parent??>: class.parent.name</#if> {  
		<#list properties as property>
			<#list property.annotations as annotation>
			[${annotation.name}<#if (annotation.size > 0)>(<#list annotation.parameters as parameter>${parameter.type} = ${parameter.name}</#list>)</#if>]
			</#list>
			<#if property.upper == 1 >
				<#if property.id?? >
			[Key]
				</#if>
				<#if property.uIProperty.required >
			[Required]
				</#if>
				<#if property.uIProperty.unique >
			[Index(IsUnique=true)]
				<#elseif property.uIProperty.searchable >
			[Index]	 	
				</#if>
				<#if property.type != property.name >
			public ${property.type} ${property.name} { get; set; }	
				<#else>
			[ForeignKey("${property.name}")]
					<#if property.lower == 1>
			[Required]
					</#if>
		    public int ${property.type}_ID { get; set; }
		    
		    [ForeignKey("${property.type}_ID")]
		    public virtual ${property.type} ${property.name} { get; set; };	
				</#if>
	    	
		    <#elseif property.upper == -1 > 
		    public ICollection<${property.type}> ${property.name} { get; set; };
		    <#else>   
		    	<#list 1..property.upper as i>
		    public ${property.type} ${property.name}${i} { get; set; };
				</#list>  
		    </#if>
		         
		</#list>
		
		<#list methods as method>
			${method.visibility} ${method.returnType.name} ${method.name}(<#list method.parameters as parameter><#if parameter_index != 0>, </#if><#if parameter.ref>ref </#if><#if parameter.out>out </#if>${parameter.type} ${parameter.name}</#list>)
			{
				/***
				ENTER YOUR CODE HERE
				***/
			}
		</#list>
		
		}
	}
}
