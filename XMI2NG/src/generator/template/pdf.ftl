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
using PdfSharp.Pdf;
using System.IO;
using System.Net.Http.Headers;
using PdfSharp.Drawing;

namespace WebApplication1.Controllers
{
    public class TestPdfController : ApiController
    {
        private static AppDBContext db = new AppDBContext();

		/*public static String dataToString(Object o)
        {
            if (o.GetType()!=typeof(String) && o.GetType()!=typeof(int) && o.GetType()!=typeof(double) && o.GetType()!=typeof(DateTime))
            {
                return "";
            }
            return o.ToString();

        }
        */

        public static String generateHtmlOutOfObject(DbSet<${class.name}> tableData)
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
               	<#list properties as property>
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
            <#list properties as property>  
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


        // GET: api/Analitika_magacinske_kartice
        [Route("api/pdf")]
        [ResponseType(typeof(void))]
        [HttpGet]
        public HttpResponseMessage getPdf()
		{
            var data = db.${class.name};

            Byte[] res = null;
            var response = Request.CreateResponse(HttpStatusCode.OK);
            String html = generateHtmlOutOfObject(data);
            using (MemoryStream ms = new MemoryStream())
            {

                var statuscode = HttpStatusCode.OK;
                var pdf = TheArtOfDev.HtmlRenderer.PdfSharp.PdfGenerator.GeneratePdf(html, PdfSharp.PageSize.A4);
                pdf.Save(ms);
                var buffer = ms.GetBuffer();
                var contentLength = buffer.Length;
                res = ms.ToArray();
                response = Request.CreateResponse(statuscode);
                response.Content = new StreamContent(new MemoryStream(buffer));
                response.Content.Headers.ContentType = new MediaTypeHeaderValue("application/pdf");
                response.Content.Headers.ContentLength = contentLength;
                ms.Close();
            }
            return response;
        }
    }
}