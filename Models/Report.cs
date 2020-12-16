using System;
using System.ComponentModel.DataAnnotations;

namespace weather_app.Models
{
    public class Report
    {
        [Key]
        public int Id { get; set; }
        public int UserId { get; set; }
        public DateTime QueryTime { get; set; }
        public int LocationId { get; set; }
        public string IpAddress { get; set; }
        public string QueryResult { get; set; }
        public double QueryFetchTime { get; set; }
        public bool isSuccess { get; set; }

    }
}
