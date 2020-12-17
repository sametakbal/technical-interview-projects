using System.Threading.Tasks;
using weather_app.Models;
using weather_app.Models.Dtos;

namespace weather_app.Repositories.Interfaces
{
    public interface IReportRepository
    {
        Task<bool> SaveReport(Report report);
        Task<ReportPageDto> GetReports(ReportPageDto reportPageDto);
    }
}