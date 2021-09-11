using System;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using weather_app.Models;
using weather_app.Models.Dtos;
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

        public async Task<Report> GetReportById(int id)
        {
            return await _context.Report.FindAsync(id);
        }

        public async Task<ReportPageDto> GetReports(ReportPageDto reportPageDto)
        {
            reportPageDto.Reports = await _context.Report.Where(r => r.UserId == reportPageDto.UserId &&
            DateTime.Compare(reportPageDto.StartDate, r.QueryTime) < 0 &&
            DateTime.Compare(reportPageDto.EndDate, r.QueryTime) > 0 &&
            reportPageDto.QueryState
            ).OrderByDescending(report => report.QueryTime).Include(rep => rep.Location).ToListAsync();
            reportPageDto.Users = await _context.User.ToListAsync();

            return reportPageDto;
        }

        public async Task<bool> SaveReport(Report report)
        {
            await _context.Report.AddAsync(report);
            int res = await _context.SaveChangesAsync();
            return res != 0;
        }
    }
}