using System;
using System.Diagnostics;
using System.Net.Http;
using System.Threading.Tasks;
using Newtonsoft.Json;
using weather_app.Models;
using weather_app.Models.Dtos;
using weather_app.Repositories.Interfaces;

namespace weather_app.Repositories
{
    public class WeatherForecastRepository : IWeatherForecastRepository
    {
        static string url = "https://api.openweathermap.org/data/2.5/weather?q=";
        static string apiKey = "&appid=010a344c63363954324cd9219f5963a5&units=metric";
        private readonly ILocationRepository _locationRepo;
        private readonly IReportRepository _reportRepo;

        public WeatherForecastRepository(ILocationRepository locationRepo, IReportRepository reportRepo)
        {
            _locationRepo = locationRepo;
            _reportRepo = reportRepo;
        }
        public async Task<WeatherForecast> GetWeatherForecast(WFRequestDto wFRequest)
        {
            string locationName = await _locationRepo.GetLocationNameById(wFRequest.Location);
            string requestString = url + locationName + apiKey;

            Stopwatch timer = new Stopwatch();
            timer.Start();
            var client = new HttpClient();
            var content = await client.GetStringAsync(requestString);
            timer.Stop();

            TimeSpan timeTaken = timer.Elapsed;
            Console.WriteLine(timeTaken.TotalMilliseconds);
            var weatherForecast = JsonConvert.DeserializeObject<WeatherForecast>(content);

            //Send Report
            await _reportRepo.SaveReport(new Report
            {
                UserId = wFRequest.UserId,
                QueryTime = DateTime.Now,
                LocationId = wFRequest.Location,
                IpAddress = wFRequest.IpAddress,
                QueryResult = content,
                QueryFetchTime = timeTaken.TotalMilliseconds,
                isSuccess = weatherForecast != null
            });
            weatherForecast.locations = await _locationRepo.GetLocationAll(null);

            return weatherForecast;
        }

        public WeatherForecast GetWeatherForecastFromJsonString(string content)
        {
            return JsonConvert.DeserializeObject<WeatherForecast>(content);
        }
    }
}