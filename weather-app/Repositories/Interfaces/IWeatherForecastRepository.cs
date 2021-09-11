using System.Threading.Tasks;
using weather_app.Models;
using weather_app.Models.Dtos;

namespace weather_app.Repositories.Interfaces
{
    public interface IWeatherForecastRepository
    {
        Task<WeatherForecast> GetWeatherForecast(WFRequestDto wFRequest);
        WeatherForecast GetWeatherForecastFromJsonString(string content);
    }
}