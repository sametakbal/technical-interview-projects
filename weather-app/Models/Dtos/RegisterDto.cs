namespace weather_app.Models.Dtos
{
    public class RegisterDto : User
    {
        public string ErrorMessage { get; set; }
    }
}