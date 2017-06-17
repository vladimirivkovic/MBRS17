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
using System.IO;
using System.Net.Http.Headers;
using iTextSharp.text;
using iTextSharp.text.pdf;

namespace WebApplication1.Controllers
{
    public class OperationController : ApiController
    {
    	private AppDBContext db = new AppDBContext();
    	<#list classes as class>
			<#list class.methods as method>
				<#if method.report??>
		 public static String generateHtmlOutOf${class.name}(DbSet<${class.name}> tableData)
        {
            String html = "<body style='text-align:center;align:center'>";
            html+="<h2>Izveštaj: "+"${class.name}</h2> </br>";
            html += "<table border=1 style='width:100%;border-collapse: collapse;'>";
            int i = 0;
            foreach (var row in tableData)
            {
            if(i++==0)
            {
                html += "<tr>";
               	<#list class.properties as property>
            		<#if property.upper!=-1>
            			<#if !property.FMClass??>
		            		html+="<td>"+"${property.name}"+"</td>";
		           		<#else>
		           			<#list property.FMClass.lookupProperties as lp>
		            		html+="<td>"+"${property.name}"+"</td>";
		           			</#list>
		            	</#if>
            		</#if>
            	</#list>
                html += "</tr>";
            }
            html+="<tr>";
            <#list class.properties as property>  
            <#if property.upper!=-1>
 				<#if !property.FMClass??>
		        	html+="<td>"+row.${property.name}+"</td>";
		         <#else>
		         	<#list property.FMClass.lookupProperties as lp>
		            html+=row.${property.name}==null?"<td></td>":"<td>"+row.${property.name}.${lp.name}+"</td>";
		           	</#list>
		         </#if> 	
            </#if>
            </#list>
            html+="</tr>";
            }
            html += "</table></body>";
            return html;

        }
        [ResponseType(typeof(void))]
        [HttpGet]
        [Route("api/operations/${method.name}")]
        public HttpResponseMessage ${method.name}(<#list method.parameters as parameter><#if parameter_index != 0>, </#if><#if parameter.ref>ref </#if><#if parameter.out>out </#if>${parameter.type} ${parameter.name}</#list>)
        {
        
        	var response = Request.CreateResponse(HttpStatusCode.OK);
        	
        	using (var ms = new MemoryStream())
            {
                //Create an iTextSharp Document which is an abstraction of a PDF but **NOT** a PDF
                using (var doc = new Document())
                {
                    using (var writer = PdfWriter.GetInstance(doc, ms))
                    {
                        doc.Open();
                        var data = db.${class.name};
                        String html = generateHtmlOutOf${class.name}(data);
                        using (var htmlWorker = new iTextSharp.text.html.simpleparser.HTMLWorker(doc))
                        {
                            //HTMLWorker doesn't read a string directly but instead needs a TextReader (which StringReader subclasses)
                            using (var sr = new StringReader(html))
                            {
                                //Parse the HTML
                                htmlWorker.Parse(sr);
                            }
                        }
                        doc.Close();
                      
                        response = Request.CreateResponse(HttpStatusCode.OK);
                        Byte[] bytes;
                        bytes = ms.ToArray();
                        var msNew = new MemoryStream(bytes);
                        response.Content = new StreamContent(msNew);
                        response.Content.Headers.ContentType = new MediaTypeHeaderValue("application/pdf");
                        response.Content.Headers.ContentLength = bytes.Length;
                        ms.Close();
                        ms.Flush();
                        

                    }
                }
            }
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