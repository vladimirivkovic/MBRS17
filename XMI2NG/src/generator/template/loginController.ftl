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
    public class LoginController : ApiController
    {
        private String Username="admin";
        private String Password = "admin";

        [Route("Login")]
        [HttpPost]
        public IHttpActionResult Login([FromBody] Korisnik k)
        {
            if (k.Username.Equals(Username) && k.Password.Equals(Password))
            {
            	return Ok(k);
            }
            else
            {
            	return BadRequest();
            }

        }
	}
}