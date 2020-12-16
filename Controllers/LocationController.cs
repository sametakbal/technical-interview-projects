using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using weather_app.Filters;
using weather_app.Models;
using weather_app.Repositories.Interfaces;

namespace weather_app.Controllers
{
    [UserFilter]
    public class LocationController : Controller
    {
        private readonly ILogger<HomeController> _logger;
        private readonly ILocationRepository _repo;

        public LocationController(ILogger<HomeController> logger, ILocationRepository repo)
        {
            _logger = logger;
            _repo = repo;
        }
        public async Task<IActionResult> Index()
        {
            return View(await _repo.GetLocationAll(null));
        }

        [HttpPost]
        public async Task<IActionResult> SaveLocation(Location location)
        {
            await _repo.SaveLocation(location);
            return Redirect("Index");
        }

        [HttpGet]
        public async Task<IActionResult> DeleteLocation(int id)
        {
            return Ok(await _repo.RemoveLocation(id));
        }


        [HttpGet]
        public async Task<IActionResult> GetLocation(int id)
        {
            return Ok(await _repo.GetLocationById(id));
        }

        [ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
        public IActionResult Error()
        {
            return View(new ErrorViewModel { RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier });
        }
    }
}
