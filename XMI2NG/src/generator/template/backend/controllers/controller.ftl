using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;
using System.Web.Http;
using System.Web.Http.ModelBinding;
using System.Web.Http.OData;
using System.Web.Http.OData.Routing;
using WebApplication1.Models;
using System.Web.Http.Description;

namespace WebApplication1.Controllers
{
    public class ${class.name}Controller : ODataController
    {
        private AppDBContext db = new AppDBContext();

        // GET: odata/${class.name}
        
        [EnableQuery]
        [ResponseType(typeof(IQueryable<${class.name}>))]
        public async Task<IHttpActionResult> Get${class.name}()
        {
            if (!LoginController.CheckAuthorizationForRequest(Request))
            {
                return Unauthorized();
            }
            return Ok(db.${class.name});
        }

		// GET: odata/${class.name}(5)
        [EnableQuery]
        [ResponseType(typeof(${class.name}))]
        public async Task<IHttpActionResult> Get${class.name}([FromODataUri] int key)
        {
            if (!LoginController.CheckAuthorizationForRequest(Request))
            {
                return Unauthorized();
            }
            return Ok(db.${class.name}.Where(${class.name?lower_case} => ${class.name?lower_case}.Id == key));
            //return SingleResult.Create(db.Mesto.Where(mesto => mesto.Id == key));
        }

      

        // PUT: odata/${class.name}(5)
        public async Task<IHttpActionResult> Put([FromODataUri] int key, Delta<${class.name}> patch)
        {
        	if (!LoginController.CheckAuthorizationForRequest(Request))
            {
                return Unauthorized();
            }
            Validate(patch.GetEntity());

            if (!ModelState.IsValid || !patch.GetEntity().ValidateOcl())
            {
                return BadRequest(ModelState);
            }

            ${class.name} ${class.name?lower_case} = await db.${class.name}.FindAsync(key);
            if (${class.name?lower_case} == null)
            {
                return NotFound();
            }

            patch.Put(${class.name?lower_case});

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!${class.name}Exists(key))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Updated(${class.name?lower_case});
        }

        // POST: odata/${class.name}
        public async Task<IHttpActionResult> Post(${class.name} ${class.name?lower_case})
        {
        	if (!LoginController.CheckAuthorizationForRequest(Request))
            {
                return Unauthorized();
            }
            if (!ModelState.IsValid || !${class.name?lower_case}.ValidateOcl())
            {
                return BadRequest(ModelState);
            }

            db.${class.name}.Add(${class.name?lower_case});
            await db.SaveChangesAsync();

            return Created(${class.name?lower_case});
        }

        // PATCH: odata/${class.name}(5)
        [AcceptVerbs("PATCH", "MERGE")]
        public async Task<IHttpActionResult> Patch([FromODataUri] int key, Delta<${class.name}> patch)
        {
        	if (!LoginController.CheckAuthorizationForRequest(Request))
            {
                return Unauthorized();
            }
            Validate(patch.GetEntity());

            if (!ModelState.IsValid || !patch.GetEntity().ValidateOcl())
            {
                return BadRequest(ModelState);
            }

            ${class.name} ${class.name?lower_case} = await db.${class.name}.FindAsync(key);
            if (${class.name?lower_case} == null)
            {
                return NotFound();
            }

            patch.Patch(${class.name?lower_case});

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!${class.name}Exists(key))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Updated(${class.name?lower_case});
        }

        // DELETE: odata/${class.name}(5)
        public async Task<IHttpActionResult> Delete([FromODataUri] int key)
        {
        	if (!LoginController.CheckAuthorizationForRequest(Request))
            {
                return Unauthorized();
            }
            ${class.name} ${class.name?lower_case} = await db.${class.name}.FindAsync(key);
            if (${class.name?lower_case} == null)
            {
                return NotFound();
            }

            db.${class.name}.Remove(${class.name?lower_case});
            await db.SaveChangesAsync();

            return StatusCode(HttpStatusCode.NoContent);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool ${class.name}Exists(int key)
        {
            return db.${class.name}.Count(e => e.Id == key) > 0;
        }
    }
}
