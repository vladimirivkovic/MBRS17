using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Http.Description;
using WebApplication1.Models;

namespace WebApplication1.Controllers
{
    public class OperacijaController : ApiController
    {
    	<#list classes as class>
			<#list class.methods as method>
		[Route("api/operations/${method.name}")]
				<#if method.report??>
        [ResponseType(typeof(void))]
        [HttpGet]
        public HttpResponseMessage ${method.name}(<#list method.parameters as parameter><#if parameter_index != 0>, </#if><#if parameter.ref>ref </#if><#if parameter.out>out </#if>${parameter.type} ${parameter.name}</#list>)
        {
        	var response = Request.CreateResponse(HttpStatusCode.OK);
        	
        	/*
        	ALEKSIN KOD
        	*/     	
        	
        	return response;
        }
        
        		<#elseif method.transaction??>
        [ResponseType(typeof(${class.name}))]
        [HttpPost]
        public IHttpActionResult ${method.name}(${class.name} ${class.name?lower_case})
        {
            /*
            TODO: IMPLEMENT METHOD
            */
            
            return Ok();
        }
        
				</#if>
			</#list>
		</#list>

        
    }
}