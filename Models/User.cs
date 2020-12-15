using System;
using System.ComponentModel.DataAnnotations;

namespace weather_app.Models
{
    public class User
    {
        [Key]
        public int Id { get; set; }

        [Required]
        [StringLength(55)]
        public string FullName { get; set; }
        [Required]
        [StringLength(55)]
        public string UserName { get; set; }
        [Required]
        [StringLength(55)]
        public string Email { get; set; }
        [Required]
        [StringLength(55)]
        public string Password { get; set; }
        public bool IsAdmin { get; set; }=false;
        public DateTime CreatedAt { get; set; } = DateTime.Now;
        public DateTime LastLogin { get; set; }
    }
}
