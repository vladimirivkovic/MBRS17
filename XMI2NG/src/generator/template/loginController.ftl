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
        private String Username = "admin";
        private String Password = "admin";
        private static String CookieString = null;

        [Route("api/Login")]
        [HttpPost]
        public IHttpActionResult Login([FromBody] Korisnik k)
        {
            if (k.Username.Equals(Username) && k.Password.Equals(Password))
            {
                CookieString = GetRandomString(16);
                return Ok(CookieString);

            }
            else
            {
                return BadRequest();
            }

        }
        private String GetRandomString(int length = 8)
        {
            var randomString = new System.Text.StringBuilder();
            var random = new Random();
            String chars = "abcdefghijklmnopqrstuvwxyz123456789";
            for (int i = 0; i < length; i++)
                randomString.Append(chars[random.Next(chars.Length)]);

            return randomString.ToString();
        }
        public static bool CheckAuthorizationForRequest(HttpRequestMessage req)
        {
            var auth = string.Empty;
            IEnumerable<string> headerValues;
            if (req.Headers.TryGetValues("Authorization", out headerValues))
            {
                auth = headerValues.FirstOrDefault();
                if (CookieString==null)
                {
                    return false;
                }
                else if (LoginController.CookieString.Equals(auth))
                {
                    return true;
                }

                return false;
            }
            return false;
        }
    }
}