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

namespace WebApplication1.Controllers
{
    public class ${class.name}Controller : ODataController
    {
        private AppDBContext db = new AppDBContext();

        // GET: odata/${class.name}
        [EnableQuery]
        public IQueryable<${class.name}> Get${class.name}()
        {
            return db.${class.name};
        }

        // GET: odata/${class.name}(5)
        [EnableQuery]
        public SingleResult<${class.name}> Get${class.name}([FromODataUri] int key)
        {
            return SingleResult.Create(db.${class.name}.Where(${class.name?lower_case} => ${class.name?lower_case}.Id == key));
        }

        // PUT: odata/${class.name}(5)
        public async Task<IHttpActionResult> Put([FromODataUri] int key, Delta<${class.name}> patch)
        {
            Validate(patch.GetEntity());

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            ${class.name} ${class.name?lower_case} = await db.${class.name}.FindAsync(key);
            if (roba == null)
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
            if (!ModelState.IsValid)
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
            Validate(patch.GetEntity());

            if (!ModelState.IsValid)
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
