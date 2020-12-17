using System.Threading.Tasks;
using weather_app.Models;
using weather_app.Repositories.Interfaces;

namespace weather_app.Repositories
{
    public class ReportRepository : IReportRepository
    {
        private readonly DataContext _context;

        public ReportRepository(DataContext context)
        {
            _context = context;
        }

        public async Task<bool> SaveReport(Report report)
        {
            await _context.Report.AddAsync(report);
            int res = await _context.SaveChangesAsync();
            return res != 0;
        }
    }
}