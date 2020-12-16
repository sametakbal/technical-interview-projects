using System.Collections.Generic;
using System.Threading.Tasks;
using weather_app.Models;

namespace weather_app.Repositories.Interfaces
{
    public interface ILocationRepository
    {
        Task<List<Location>> GetLocationAll(string term);
        Task<Location> GetLocationById(int id);
        Task<bool> SaveLocation(Location location);
        Task<bool> RemoveLocation(int id);
    }
}