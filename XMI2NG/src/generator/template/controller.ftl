using System;

namespace ${class.namespace} {
	${class.visibility} class ${class.name}Controller : ApiController {  
		private ApplicationDBContext db = new ApplicationDBContext();
		
		// GET: api/${class.name}s
        public async Task<IHttpActionResult> Get${class.name}s()
        {
        	return Ok(db.${class.name}s);
		}
		
		// GET: api/${class.name}/5
        [ResponseType(typeof(${class.name}))]
        public async Task<IHttpActionResult> Get${class.name}(int id)
        {
            ${class.name} e = await db.${class.name}.SingleOrDefaultAsync(t => t.${class.name}ID == id);
            if (e == null)
            {
                return NotFound();
            }

            return Ok(e);
		}
		
		// PUT: api/${class.name}s/5
		[ResponseType(typeof(void))]
		public async Task<IHttpActionResult> Put${class.name}(int id, ${class.name} e)
		{
			if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
			}
			
			db.Entry(e).State = EntityState.Modified;

            try
            {
                await db.SaveChangesAsync();
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

			return Ok(e);
		}
		
		// POST: api/${class.name}s
        [ResponseType(typeof(${class.name}))]
        public async Task<IHttpActionResult> Post${class.name}(${class.name} e)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.${class.name}s.Add(e);
            await db.SaveChangesAsync();

            return CreatedAtRoute("DefaultApi", new { id = e.${class.name}ID }, e);
		}
		
		// DELETE: api/${class.name}s/5
        [ResponseType(typeof(${class.name}))]
        public async Task<IHttpActionResult> Delete${class.name}(int id)
        {
            ${class.name} e = await db.${class.name}s.FindAsync(id);
            if (e == null)
            {
                return NotFound();
            }

            db.Projects.Remove(e);
            await db.SaveChangesAsync();

            return Ok(e);
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
            return db.${class.name}s.Count(e => e.${class.name}ID == id) > 0;
		}

	}
}