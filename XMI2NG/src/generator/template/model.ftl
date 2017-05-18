using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace WebApplication1.Models
{
	${class.visibility} class ${class.name}<#if class.parent??>: class.parent.name</#if>
	{  
	<#list properties as property>
		<#list property.annotations as annotation>
		[${annotation.name}<#if (annotation.size > 0)>(<#list annotation.parameters as parameter>${parameter.type} = ${parameter.name}</#list>)</#if>]
		</#list>
		<#if property.upper == 1 >
			<#if property.type == "String">
		[Column(TypeName = "VARCHAR")]
		[StringLength(128)]
			</#if>	
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
			<#if !property.isClass >
				<#if property.type == "date" >
		public DateTime ${property.name} { get; set; }
				<#elseif property.type == "boolean">
		public Boolean ${property.name} { get; set; }		
				<#else>
		public ${property.type} ${property.name} { get; set; }
				</#if>
			<#else>
		[ForeignKey("${property.name}")]
				<#if property.lower == 1>
		[Required]
				</#if>
	    public int ${property.name}_ID { get; set; }
	    
	    [ForeignKey("${property.name}_ID")]
	    		<#if property.inverseProperty??>
	    [InverseProperty("${property.inverseProperty.name}")]
	    		</#if>
	    public ${property.type} ${property.name} { get; set; }
			</#if>
	    <#elseif property.upper == -1 >
	    		<#if property.inverseProperty??>
	    [InverseProperty("${property.inverseProperty.name}")]
	    		</#if> 
	    public ICollection<${property.type}> ${property.name} { get; set; }
	    <#else>   
	    	<#list 1..property.upper as i>
	    public ${property.type} ${property.name}${i} { get; set; }
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
