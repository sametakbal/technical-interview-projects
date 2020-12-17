namespace weather_app.Models.Dtos
{
    public class WFRequestDto
    {
        public int Location { get; set; }
        public string IpAddress { get; set; }
        public int UserId { get; set; }
    }
}