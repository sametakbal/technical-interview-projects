using System;
using System.Collections.Generic;

namespace weather_app.Models.Dtos
{
    public class ReportPageDto
    {
        public List<User> Users { get; set; }
        public int UserId { get; set; }
        public DateTime StartDate { get; set; }=DateTime.Now;
        public DateTime EndDate { get; set; }=DateTime.Now;
        public bool QueryState { get; set; }=true;
        public List<Report> Reports { get; set; }= new List<Report>();
    }
}