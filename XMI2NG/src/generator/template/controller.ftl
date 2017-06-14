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
    public class ${class.name}Controller : ApiController
    {
        private AppDBContext db = new AppDBContext();

        // GET: api/${class.name}
        public IQueryable<${class.name}> Get${class.name}()
        {
            return db.${class.name};
        }
 
        // GET: api/${class.name}/5
        [ResponseType(typeof(${class.name}))]
        public IHttpActionResult Get${class.name}(int id)
        {
            ${class.name} ${class.name?lower_case} = db. ${class.name}.Find(id);
            if (${class.name?lower_case} == null)
            {
                return NotFound();
            }

            return Ok(${class.name?lower_case});
        }

        // PUT: api/${class.name}/5
        [ResponseType(typeof(void))]
        public IHttpActionResult Put${class.name}(int id,  ${class.name} ${class.name?lower_case})
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != ${class.name?lower_case}.Id)
            {
                return BadRequest();
            }

            db.Entry(${class.name?lower_case}).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!${class.name}Exists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return StatusCode(HttpStatusCode.NoContent);
        }

        // POST: api/${class.name}
        [ResponseType(typeof(${class.name}))]
        public IHttpActionResult Post${class.name}(${class.name} ${class.name?lower_case})
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.${class.name}.Add(${class.name?lower_case});
            db.SaveChanges();

            return CreatedAtRoute("DefaultApi", new { id = ${class.name?lower_case}.Id }, ${class.name?lower_case});
        }

        // DELETE: api/${class.name}/5
        [ResponseType(typeof(${class.name}))]
        public IHttpActionResult Delete${class.name}(int id)
        {
            ${class.name} ${class.name?lower_case} = db.${class.name}.Find(id);
            if (${class.name?lower_case} == null)
            {
                return NotFound();
            }

            db.${class.name}.Remove(${class.name?lower_case});
            db.SaveChanges();

            return Ok(${class.name?lower_case});
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool ${class.name}Exists(int id)
        {
            return db.${class.name}.Count(e => e.Id == id) > 0;
        }
    }
}