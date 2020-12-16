namespace weather_app.Models.Dtos
{
    public class LoginDto
    {
        public string Email { get; set; }
        public string Password{ get; set; }
        public string ErrorMessage { get; set; }
    }
}