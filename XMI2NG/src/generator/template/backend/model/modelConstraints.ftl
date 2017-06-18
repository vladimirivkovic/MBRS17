/*

######## DO NOT CHANGE THIS CODE! ########
AUTOMATICALLY GENERATED CONSTRAINTS FOR -- ${class.name} -- AS A PARTIAL CLASS

TO ADD ADDITIONAL METHODS OR PROPERTIES TO THIS CLASS, CREATE ANOTHER PARTIAL CLASS OF THE SAME NAME!

BASED ON TEMPLATE -- modelConstraints.ftl --

GENERATED ON -- ${.now} --

*/
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace WebApplication1.Models
{
	${class.visibility} partial class ${class.name}<#if class.parent??>: ${class.parent}<#list class.interfaces as i>,${i}</#list><#else><#list class.interfaces as i>${i}<#sep>,</#list></#if>
	{	
		public bool ValidateOcl()
		{	
		<#list constraints as constraint>
			//Constraint :${constraint.name} -> ${constraint.constraintExp}	
			if (!(${constraint.constraintExp?replace("self", "this")?replace(" ="," ==")?replace("at","ElementAt")?replace("toUpper","ToUpper")?replace("size()","Length")}))
			{
				return false;
			}	
		</#list>
			return true;
		}
	}
}
