using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using weather_app.Filters;
using weather_app.Models.Dtos;
using weather_app.Repositories.Interfaces;

namespace weather_app.Controllers
{
    [UserFilter]
    public class ReportController : Controller
    {
        private readonly IReportRepository _repo;
        private readonly IUserRepository _userRepo;

        public ReportController(IReportRepository repo, IUserRepository userRepo = null)
        {
            _repo = repo;
            _userRepo = userRepo;
        }

#nullable enable
        public async Task<IActionResult> Index(ReportPageDto? reportPageDto)
        {
            if (reportPageDto == null)
            {
                reportPageDto = new ReportPageDto();
            }
            reportPageDto.Users = await _userRepo.GetUsers(null);
            return View(reportPageDto);
        }

        public async Task<IActionResult> GetUserReports(ReportPageDto reportPageDto)
        {
            return PartialView("Index", await _repo.GetReports(reportPageDto));
        }
    }
}