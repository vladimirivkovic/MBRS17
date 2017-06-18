using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace WebApplication1.Models
{
	public class Korisnik
	{  
	
		public String Password { get; set; }
	    public String Username { get; set; }
	    
	}
}
