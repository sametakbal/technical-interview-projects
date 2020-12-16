namespace weather_app.Models.Dtos
{
    public class RegisterDto
    {
        public User User { get; set; }
        public string ErrorMessage { get; set; }
    }
}