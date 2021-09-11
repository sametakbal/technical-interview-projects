using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace weather_app.Models
{
    public class Report
    {
        [Key]
        public int Id { get; set; }
        [ForeignKey("User")]
        public int UserId { get; set; }
        public User User { get; set; }
        public DateTime QueryTime { get; set; }
        [ForeignKey("Location")]
        public int LocationId { get; set; }
        public Location Location { get; set; }
        [StringLength(45)]
        public string IpAddress { get; set; }
        public string QueryResult { get; set; }
        public double QueryFetchTime { get; set; }
        public bool isSuccess { get; set; }

    }
}
