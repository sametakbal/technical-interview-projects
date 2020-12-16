using System;
using System.ComponentModel.DataAnnotations;

namespace weather_app.Models
{
    public class Location
    {
        [Key]
        public int Id { get; set; }

        [Required]
        [StringLength(255)]
        public String Name { get; set; }
    }
}
