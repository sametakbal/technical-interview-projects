using System.Threading.Tasks;
using weather_app.Models;

namespace weather_app.Repositories.Interfaces
{
    public interface IReportRepository
    {
         Task<bool> SaveReport(Report report);
    }
}