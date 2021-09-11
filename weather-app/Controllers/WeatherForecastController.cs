using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.Extensions.Logging;
using weather_app.Filters;
using weather_app.Models;
using weather_app.Models.Dtos;
using weather_app.Repositories.Interfaces;

namespace weather_app.Controllers
{
    [UserFilter]
    public class WeatherForecastController : Controller
    {
        private readonly ILogger<WeatherForecastController> _logger;
        private readonly ILocationRepository _locationRepo;
        private readonly IWeatherForecastRepository _weatherForecastRepository;
        public WeatherForecastController(ILogger<WeatherForecastController> logger, ILocationRepository locationRepo,
         IWeatherForecastRepository weatherForecastRepository)
        {
            _weatherForecastRepository = weatherForecastRepository;
            _logger = logger;
            _locationRepo = locationRepo;
        }
#nullable enable
        public async Task<IActionResult> Index(WeatherForecast? weatherForecast)
        {
            if (weatherForecast == null)
            {
                weatherForecast = new WeatherForecast();
            }
            weatherForecast.locations = await _locationRepo.GetLocationAll(null);
            return View(weatherForecast);
        }

        [HttpPost]
        public async Task<IActionResult> GetWeatherForecast(WFRequestDto wFRequestDto)
        {
            int? id = HttpContext.Session.GetInt32("id");
            wFRequestDto.UserId = id.HasValue ? id.Value : 0;
            var weatherForecast = await _weatherForecastRepository.GetWeatherForecast(wFRequestDto);

            return PartialView("Index", weatherForecast);
        }

        [ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
        public IActionResult Error()
        {
            return View(new ErrorViewModel { RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier });
        }
    }
}
