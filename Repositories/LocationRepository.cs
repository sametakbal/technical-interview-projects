using System.Collections.Generic;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using weather_app.Models;
using weather_app.Repositories.Interfaces;

namespace weather_app.Repositories
{
    public class LocationRepository : ILocationRepository
    {
        private readonly DataContext _context;

        public LocationRepository(DataContext context)
        {
            _context = context;
        }

        public async Task<List<Location>> GetLocationAll(string term)
        {
            return await _context.Location.ToListAsync();
        }
        public async Task<Location> GetLocationById(int id)
        {
            return await _context.Location.FindAsync(id);
        }

        public async Task<bool> RemoveLocation(int id)
        {
            var location = await _context.Location.FindAsync(id);
            _context.Remove(location);
            int res = await _context.SaveChangesAsync();
            return res != 0;
        }

        public async Task<bool> SaveLocation(Location location)
        {
            if(location.Id == 0){
                await _context.AddAsync(location);
            }else{
                _context.Update(location);
            }
            int res = await _context.SaveChangesAsync();
            return res != 0;
        }
    }
}