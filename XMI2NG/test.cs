using System;

namespace ordering {
	public class City {  
			[Display(Name = _Ime_)]
			[Required]
		    public String name { get; set; };
		    public String zipCode { get; set; };
		    public state state { get; set; };
		    public ICollection<Enterprise> enterprise { get; set; };
		    public Department department1 { get; set; };
		    public Department department2 { get; set; };
		    public Department department3 { get; set; };
		
			public int open(double in1, out int in2, ref String in3)
			{
				/***
				ENTER YOUR CODE HERE
				***/
			}
			public int close()
			{
				/***
				ENTER YOUR CODE HERE
				***/
			}
			private void next()
			{
				/***
				ENTER YOUR CODE HERE
				***/
			}
		
		}
	}
}
